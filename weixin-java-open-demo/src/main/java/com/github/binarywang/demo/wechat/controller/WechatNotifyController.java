package com.github.binarywang.demo.wechat.controller;

import java.io.File;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.github.binarywang.demo.wechat.service.WxOpenServiceDemo;
import com.lj.cloud.secrity.service.impl.WeixinUserinfoServiceImpl;
import com.weixindev.micro.serv.common.bean.weixin.WeixinUserinfo;
import com.weixindev.micro.serv.common.util.DateUtil;

import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.bean.kefu.WxMpKefuMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import me.chanjar.weixin.open.bean.message.WxOpenXmlMessage;
import me.chanjar.weixin.open.bean.result.WxOpenAuthorizerInfoResult;

/**
 * @author <a href="https://github.com/007gzs">007</a>
 */
@RestController
@RequestMapping("notify")
public class WechatNotifyController {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	protected WxOpenServiceDemo wxOpenService;
	@Autowired
	public WeixinUserinfoServiceImpl weixinUserinfoServiceImpl;
	@Value("${file_location}")
    private String file_location;

	@RequestMapping("receive_ticket")
	public Object receiveTicket(@RequestBody(required = false) String requestBody,
			@RequestParam("timestamp") String timestamp, @RequestParam("nonce") String nonce,
			@RequestParam("signature") String signature,
			@RequestParam(name = "encrypt_type", required = false) String encType,
			@RequestParam(name = "msg_signature", required = false) String msgSignature) {
		this.logger.info(
				"\n接收微信请求：[signature=[{}], encType=[{}], msgSignature=[{}],"
						+ " timestamp=[{}], nonce=[{}], requestBody=[\n{}\n] ",
				signature, encType, msgSignature, timestamp, nonce, requestBody);

		if (!StringUtils.equalsIgnoreCase("aes", encType)) {
			throw new IllegalArgumentException("非法请求，可能属于伪造的请求！");
		}

		// aes加密的消息
		WxOpenXmlMessage inMessage = WxOpenXmlMessage.fromEncryptedXml(requestBody,
				wxOpenService.getWxOpenConfigStorage(), timestamp, nonce, msgSignature);

		this.logger.debug("\n消息解密后内容为：\n{} ", inMessage.toString());

		try {
			String out = wxOpenService.getWxOpenComponentService().route(inMessage);

			WeixinUserinfo weixinUserinfo = new WeixinUserinfo();
			weixinUserinfo.setAuthorizerAppid(inMessage.getAuthorizerAppid());
			weixinUserinfo.setInfoType(inMessage.getInfoType());

			HashMap<String, Object> weixinUserinfo2 = weixinUserinfoServiceImpl
					.selectByAppId(inMessage.getAuthorizerAppid());
			this.logger.debug("\n公众号开始授权或更新授权,开始-------------------------：\n{} ", inMessage.toString());
			if (StringUtils.equalsAnyIgnoreCase(inMessage.getInfoType(), "authorized", "updateauthorized")) {
				this.logger.debug("\n公众号开始授权或更新授权,结果为-------------------------：\n{} ", inMessage.toString());
				WxOpenAuthorizerInfoResult wxOpenAuthorizerInfoResult = wxOpenService.getWxOpenComponentService()
						.getAuthorizerInfo(inMessage.getAuthorizerAppid());

				weixinUserinfo.setNickName(wxOpenAuthorizerInfoResult.getAuthorizerInfo().getNickName());
				weixinUserinfo.setHeadImg(wxOpenAuthorizerInfoResult.getAuthorizerInfo().getHeadImg());
				
				weixinUserinfo.setInfoType(inMessage.getInfoType());

				Date date = new Date(System.currentTimeMillis());  
		        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd_HHmmss");  
		        String fileName = inMessage.getAuthorizerAppid()+dateFormat.format(date) + ".jpg";  
				try {
		            URL httpurl = new URL(wxOpenAuthorizerInfoResult.getAuthorizerInfo().getQrcodeUrl());  
		            File dirfile = new File(file_location);
		            if (!dirfile.exists()) {
		                dirfile.mkdirs();
		            }
		            FileUtils.copyURLToFile(httpurl, new File(file_location+fileName));
		            weixinUserinfo.setQrcodeUrl(file_location+fileName);
		        } catch (Exception e) {
		            e.printStackTrace();
		        }
				
				weixinUserinfo.setServiceTypeInfo(
						wxOpenAuthorizerInfoResult.getAuthorizerInfo().getServiceTypeInfo().toString());
				weixinUserinfo.setVerifyTypeInfo(wxOpenAuthorizerInfoResult.getAuthorizerInfo().getVerifyTypeInfo());
				weixinUserinfo.setUserName(wxOpenAuthorizerInfoResult.getAuthorizerInfo().getUserName());
				weixinUserinfo.setPrincipalName(wxOpenAuthorizerInfoResult.getAuthorizerInfo().getPrincipalName());
				weixinUserinfo
						.setBusinessInfo(wxOpenAuthorizerInfoResult.getAuthorizerInfo().getBusinessInfo().toString());
				Integer createBy = 1;
				weixinUserinfo.setCreateBy(createBy);
				weixinUserinfo.setCreateByUname("admin01");
				weixinUserinfo.setCreateDate(DateUtil.getNowDateYYYYMMddHHMMSS());

				if (weixinUserinfo2 == null) {
					weixinUserinfoServiceImpl.insert(weixinUserinfo);
				} else {
					weixinUserinfo.setId(Integer.valueOf(weixinUserinfo2.get("id").toString()));
					weixinUserinfoServiceImpl.updateByPrimaryKeySelective(weixinUserinfo);
				}
			} else if (inMessage.getInfoType().equals("unauthorized")) {
				if (weixinUserinfo2 != null) {
					weixinUserinfo.setId(Integer.valueOf(weixinUserinfo2.get("id").toString()));
					weixinUserinfoServiceImpl.updateByPrimaryKeySelective(weixinUserinfo);
				}
			} else if (inMessage.getInfoType().equals("component_verify_ticket")) {
				this.logger.debug("\ncomponent_verify_ticket信息：{}", inMessage);
			}

			this.logger.debug("\n组装回复信息：{}", out);
		} catch (WxErrorException e) {
			this.logger.error("receive_ticket", e);
		}

		return "success";
	}

	@RequestMapping("{appId}/callback")
	public Object callback(@RequestBody(required = false) String requestBody, @PathVariable("appId") String appId,
			@RequestParam("signature") String signature, @RequestParam("timestamp") String timestamp,
			@RequestParam("nonce") String nonce, @RequestParam("openid") String openid,
			@RequestParam("encrypt_type") String encType, @RequestParam("msg_signature") String msgSignature) {
		this.logger.info(
				"\n接收微信请求：[appId=[{}], openid=[{}], signature=[{}], encType=[{}], msgSignature=[{}],"
						+ " timestamp=[{}], nonce=[{}], requestBody=[\n{}\n] ",
				appId, openid, signature, encType, msgSignature, timestamp, nonce, requestBody);
		if (!StringUtils.equalsIgnoreCase("aes", encType)
				|| !wxOpenService.getWxOpenComponentService().checkSignature(timestamp, nonce, signature)) {
			throw new IllegalArgumentException("非法请求，可能属于伪造的请求！");
		}

		String out = "";
		// aes加密的消息
		WxMpXmlMessage inMessage = WxOpenXmlMessage.fromEncryptedMpXml(requestBody,
				wxOpenService.getWxOpenConfigStorage(), timestamp, nonce, msgSignature);
		this.logger.debug("\n消息解密后内容为：\n{} ", inMessage.toString());
		// 全网发布测试用例
		if (StringUtils.equalsAnyIgnoreCase(appId, "wxd101a85aa106f53e", "wx570bc396a51b8ff8", "wx0901d417572264df")) {
			try {
				if (StringUtils.equals(inMessage.getMsgType(), "text")) {
					if (StringUtils.equals(inMessage.getContent(), "TESTCOMPONENT_MSG_TYPE_TEXT")) {
						out = WxOpenXmlMessage.wxMpOutXmlMessageToEncryptedXml(
								WxMpXmlOutMessage.TEXT().content("TESTCOMPONENT_MSG_TYPE_TEXT_callback")
										.fromUser(inMessage.getToUser()).toUser(inMessage.getFromUser()).build(),
								wxOpenService.getWxOpenConfigStorage());
					} else if (StringUtils.startsWith(inMessage.getContent(), "QUERY_AUTH_CODE:")) {
						String msg = inMessage.getContent().replace("QUERY_AUTH_CODE:", "") + "_from_api";
						WxMpKefuMessage kefuMessage = WxMpKefuMessage.TEXT().content(msg)
								.toUser(inMessage.getFromUser()).build();
						wxOpenService.getWxOpenComponentService().getWxMpServiceByAppid(appId).getKefuService()
								.sendKefuMessage(kefuMessage);
					}
				} else if (StringUtils.equals(inMessage.getMsgType(), "event")) {
					WxMpKefuMessage kefuMessage = WxMpKefuMessage.TEXT().content(inMessage.getEvent() + "from_callback")
							.toUser(inMessage.getFromUser()).build();
					wxOpenService.getWxOpenComponentService().getWxMpServiceByAppid(appId).getKefuService()
							.sendKefuMessage(kefuMessage);
				}
			} catch (WxErrorException e) {
				logger.error("callback", e);
			}
		} else {
			WxMpXmlOutMessage outMessage = wxOpenService.getWxOpenMessageRouter().route(inMessage, appId);
			if (outMessage != null) {
				out = WxOpenXmlMessage.wxMpOutXmlMessageToEncryptedXml(outMessage,
						wxOpenService.getWxOpenConfigStorage());
			}
		}
		return out;
	}
	
	/**
	 * 请求测试
	 * @param requestBody
	 * @param timestamp
	 * @param nonce
	 * @param signature
	 * @param encType
	 * @param msgSignature
	 * @return
	 */
	@RequestMapping("receive_ticket_test")
	public Object receive_ticket_test(@RequestBody(required = false) String requestBody,
			@RequestParam("timestamp") String timestamp, @RequestParam("nonce") String nonce,
			@RequestParam("signature") String signature,
			@RequestParam(name = "encrypt_type", required = false) String encType,
			@RequestParam(name = "msg_signature", required = false) String msgSignature) {

		this.logger.info(
				"\n接收微信请求：[signature=[{}], encType=[{}], msgSignature=[{}],"
						+ " timestamp=[{}], nonce=[{}], requestBody=[\n{}\n] ",
				signature, encType, msgSignature, timestamp, nonce, requestBody);

	    if (!StringUtils.equalsIgnoreCase("aes", encType)
				|| !wxOpenService.getWxOpenComponentService().checkSignature(timestamp, nonce, signature)) {
			throw new IllegalArgumentException("非法请求，可能属于伪造的请求！");
		}

	    return "合法的请求";
	  }
}
