package com.github.binarywang.demo.wechat.controller;

import com.github.binarywang.demo.wechat.service.WxOpenServiceDemo;
import com.lj.cloud.secrity.service.WeixinUserinfoService;
import com.lj.cloud.secrity.service.impl.WeixinUserinfoServiceImpl;
import com.weixindev.micro.serv.common.bean.weixin.WeixinUserinfo;
import com.weixindev.micro.serv.common.util.DateUtil;

import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.open.bean.result.WxOpenAuthorizerInfoResult;
import me.chanjar.weixin.open.bean.result.WxOpenQueryAuthResult;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

/**
 * @author <a href="https://github.com/007gzs">007</a>
 */
@Controller
@RequestMapping("api")
public class WechatApiController {
	private final Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	private WxOpenServiceDemo wxOpenServiceDemo;
	@Autowired
	public WeixinUserinfoService weixinUserinfoService;
	@Value("${file_location}")
    private String file_location;
	@Autowired
	public WeixinUserinfoServiceImpl weixinUserinfoServiceImpl;

	@GetMapping("auth/goto_auth_url_show")
	public String gotoPreAuthUrlShow() {
		return "wxgotoauth";
	}

	@GetMapping("auth/goto_auth_url_show_page")
	public String gotoPreAuthUrlShowPage() {
		return "weixinUserinfoUpdate";
	}

	@GetMapping("auth/goto_auth_url")
	public void gotoPreAuthUrl(HttpServletRequest request, HttpServletResponse response) {
		String host = request.getHeader("host");
		String url = "http://" + host + "/api/auth/jump";
		try {
			url = wxOpenServiceDemo.getWxOpenComponentService().getPreAuthUrl(url);
			response.sendRedirect(url);
		} catch (WxErrorException | IOException e) {
			logger.error("gotoPreAuthUrl", e);
			throw new RuntimeException(e);
		}
	}

	@GetMapping("redis/write")
	@ResponseBody
	public String redisWrite(HttpServletRequest request, HttpServletResponse response) {
		WeixinUserinfo weixinUserinfo = weixinUserinfoService.selectByPrimaryKey(40);
		return wxOpenServiceDemo.getWxOpenConfigStorage()
				.getAuthorizerRefreshToken(weixinUserinfo.getAuthorizerAppid());
	}

	@GetMapping("auth/jump")
	public void jump(@RequestParam("auth_code") String authorizationCode, HttpServletRequest request,
			HttpServletResponse response) {

		WxOpenQueryAuthResult queryAuthResult;
		try {
			queryAuthResult = wxOpenServiceDemo.getWxOpenComponentService().getQueryAuth(authorizationCode);
			logger.info("getQueryAuth"+queryAuthResult);

			// WeixinUserinfo weixinUserinfo = new WeixinUserinfo();
			// weixinUserinfo.setAuthorizerAppid(queryAuthResult.getAuthorizationInfo().getAuthorizerAppid());
			// weixinUserinfo
			// .setAuthorizerAccessToken(queryAuthResult.getAuthorizationInfo().getAuthorizerAccessToken());
			// weixinUserinfo.setExpiresIn(Integer.toString(queryAuthResult.getAuthorizationInfo().getExpiresIn()));
			// weixinUserinfo
			// .setAuthorizerRefreshToken(queryAuthResult.getAuthorizationInfo().getAuthorizerRefreshToken());
			// WxOpenAuthorizerInfoResult wxOpenAuthorizerInfoResult =
			// wxOpenServiceDemo.getWxOpenComponentService()
			// .getAuthorizerInfo(queryAuthResult.getAuthorizationInfo().getAuthorizerAppid());
			// weixinUserinfo.setNickName(wxOpenAuthorizerInfoResult.getAuthorizerInfo().getNickName());
			// weixinUserinfo.setHeadImg(wxOpenAuthorizerInfoResult.getAuthorizerInfo().getHeadImg());
			// weixinUserinfo.setServiceTypeInfo(
			// wxOpenAuthorizerInfoResult.getAuthorizerInfo().getServiceTypeInfo().toString());
			// weixinUserinfo.setVerifyTypeInfo(wxOpenAuthorizerInfoResult.getAuthorizerInfo().getVerifyTypeInfo());
			// weixinUserinfo.setUserName(wxOpenAuthorizerInfoResult.getAuthorizerInfo().getUserName());
			// weixinUserinfo.setPrincipalName(wxOpenAuthorizerInfoResult.getAuthorizerInfo().getPrincipalName());
			// weixinUserinfo
			// .setBusinessInfo(wxOpenAuthorizerInfoResult.getAuthorizerInfo().getBusinessInfo().toString());
			// weixinUserinfo.setAlias(wxOpenAuthorizerInfoResult.getAuthorizerInfo().getAlias());
			// weixinUserinfo.setQrcodeUrl(wxOpenAuthorizerInfoResult.getAuthorizerInfo().getQrcodeUrl());
			// weixinUserinfo.setSignature(wxOpenAuthorizerInfoResult.getAuthorizerInfo().getSignature());
			// Integer createBy = 1;
			// weixinUserinfo.setCreateBy(createBy);
			// weixinUserinfo.setCreateByUname("admin01");
			// weixinUserinfo.setCreateDate(DateUtil.getNowDateYYYYMMddHHMMSS());
			//
			// WeixinUserinfo weixinUserinfo2 = weixinUserinfoService
			// .selectByAppId(queryAuthResult.getAuthorizationInfo().getAuthorizerAppid());
			// Integer newid = 0;
			//
			// if (weixinUserinfo2 == null) {
			// weixinUserinfoService.insertSelective(weixinUserinfo);
			// newid = weixinUserinfo.getId();
			// } else {
			// weixinUserinfo.setId(weixinUserinfo2.getId());
			// newid = weixinUserinfo2.getId();
			// weixinUserinfoService.updateByPrimaryKeySelective(weixinUserinfo);
			// }

			// System.out.println(wxOpenServiceDemo.getWxOpenConfigStorage().);

			String host = request.getHeader("host");
			String url = "";
			url = "http://" + host + ":8022/weixinUserinfoUpdate.html?authorizerAppid="
					+ queryAuthResult.getAuthorizationInfo().getAuthorizerAppid();
			logger.info(authorizationCode);
			try {
				response.sendRedirect(url);
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (WxErrorException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

	@GetMapping("testinsert")
	public void testInsertWeixininfo() {
		WeixinUserinfo weixinUserinfo = new WeixinUserinfo();
		weixinUserinfo.setAuthorizerAppid("lkjlfdjg");
		weixinUserinfo.setAuthorizerAccessToken("lkjlfdjg");
		weixinUserinfo.setAuthorizerRefreshToken("lkjlfdjg");
		weixinUserinfoService.insertSelective(weixinUserinfo);
	}

	@GetMapping("/wxopen/get_authorizer_info")
	@ResponseBody
	public WeixinUserinfo getAuthorizerInfo(@RequestParam String authorizerAppid) {
		try {
//			WeixinUserinfo wei 
			WeixinUserinfo wx=weixinUserinfoService.selectByauthorizerAppid(authorizerAppid);
			WeixinUserinfo weixinUserinfo = new WeixinUserinfo();
			if(null!=wx) {
				weixinUserinfo.setCinemaId(wx.getCinemaId());
				weixinUserinfo.setCityId(wx.getCityId());
				weixinUserinfo.setNickName(wx.getNickName());
				weixinUserinfo.setWeixinGroupsId(wx.getWeixinGroupsId());
			}
//			int status=0;
			if(null==wx.getId()) {
				weixinUserinfo.setId(-100);
				return weixinUserinfo;
//				WxOpenAuthorizerInfoResult wxOpenAuthorizerInfoResult = wxOpenServiceDemo.getWxOpenComponentService()
//						.getAuthorizerInfo(authorizerAppid);
//				weixinUserinfo.setAuthorizerAppid(authorizerAppid);
//				weixinUserinfo.setNickName(wxOpenAuthorizerInfoResult.getAuthorizerInfo().getNickName());
//				weixinUserinfo.setHeadImg(wxOpenAuthorizerInfoResult.getAuthorizerInfo().getHeadImg());
//				weixinUserinfo.setInfoType("authorized");
//				Date date = new Date(System.currentTimeMillis());  
//				 SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd_HHmmss");  
//			     String fileName = authorizerAppid+dateFormat.format(date) + ".jpg";  
//			     try {
//			            URL httpurl = new URL(wxOpenAuthorizerInfoResult.getAuthorizerInfo().getQrcodeUrl());  
//			            File dirfile = new File(file_location);
//			            if (!dirfile.exists()) {
//			                dirfile.mkdirs();
//			            }
//			            FileUtils.copyURLToFile(httpurl, new File(file_location+fileName));
//			            weixinUserinfo.setQrcodeUrl(file_location+fileName);
//			        } catch (Exception e) {
//			            e.printStackTrace();
//			        }
//			     weixinUserinfo.setServiceTypeInfo(
//							wxOpenAuthorizerInfoResult.getAuthorizerInfo().getServiceTypeInfo().toString());
//					weixinUserinfo.setVerifyTypeInfo(wxOpenAuthorizerInfoResult.getAuthorizerInfo().getVerifyTypeInfo());
//					weixinUserinfo.setUserName(wxOpenAuthorizerInfoResult.getAuthorizerInfo().getUserName());
//					weixinUserinfo.setPrincipalName(wxOpenAuthorizerInfoResult.getAuthorizerInfo().getPrincipalName());
//					weixinUserinfo
//							.setBusinessInfo(wxOpenAuthorizerInfoResult.getAuthorizerInfo().getBusinessInfo().toString());
//					Integer createBy = 1;
//					weixinUserinfo.setCreateBy(createBy);
//					weixinUserinfo.setCreateByUname("admin01");
//					weixinUserinfo.setCreateDate(DateUtil.getNowDateYYYYMMddHHMMSS());
//					status=weixinUserinfoServiceImpl.insert(weixinUserinfo);
////					wxOpenAuthorizerInfoResult.getAuthorizerInfo().getAlias()
////					wxOpenServiceDemo.getWxOpenComponentService().getWxMpServiceByAppid(authorizerAppid).getAccessToken()
			}
//			System.out.println(status+"-----------------------------------");
			WxOpenAuthorizerInfoResult wxOpenAuthorizerInfoResult = wxOpenServiceDemo.getWxOpenComponentService()
					.getAuthorizerInfo(authorizerAppid);
			
			weixinUserinfo.setNickName(wxOpenAuthorizerInfoResult.getAuthorizerInfo().getNickName());
			weixinUserinfo.setHeadImg(wxOpenAuthorizerInfoResult.getAuthorizerInfo().getHeadImg());
			weixinUserinfo.setPrincipalName(wxOpenAuthorizerInfoResult.getAuthorizerInfo().getPrincipalName());
			weixinUserinfo.setAlias(wxOpenAuthorizerInfoResult.getAuthorizerInfo().getAlias());
			weixinUserinfo.setVerifyTypeInfo(wxOpenAuthorizerInfoResult.getAuthorizerInfo().getVerifyTypeInfo());
			weixinUserinfo.setServiceTypeInfo(wxOpenAuthorizerInfoResult.getAuthorizerInfo().getServiceTypeInfo().toString());
			weixinUserinfo.setSignature(wxOpenAuthorizerInfoResult.getAuthorizerInfo().getSignature());
			weixinUserinfo.setUserName(wxOpenAuthorizerInfoResult.getAuthorizerInfo().getUserName());
			weixinUserinfo.setQrcodeUrl(wxOpenAuthorizerInfoResult.getAuthorizerInfo().getQrcodeUrl());
			return weixinUserinfo;
		} catch (Exception e) {//WxError
			logger.error("getAuthorizerInfo", e);
			throw new RuntimeException(e);
		}
	}

}
