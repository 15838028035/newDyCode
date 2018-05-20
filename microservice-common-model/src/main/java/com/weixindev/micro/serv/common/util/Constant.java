package com.weixindev.micro.serv.common.util;

public class Constant {
	
	public static final String GET_WEB_CODE="https://open.weixin.qq.com/connect/oauth2/authorize?"
			+ "appid={APPID}&redirect_uri={REDIRECT_URI}&response_type={RESPONSE_TYPE}&scope={SCOPE}&state={STATE}#wechat_redirect";
	public static final String GET_WEB_ACCESS_TOKEN="https://api.weixin.qq.com/sns/oauth2/access_token?"
			+ "appid={APPID}&secret={SECRET}&code={CODE}&grant_type={authorization_code}";
	public static final String REFRESH_WEB_ACCESS_TOKEN="https://api.weixin.qq.com/sns/oauth2/refresh_token?"
			+ "appid={APPID}&grant_type={GRANT_TYPE}&refresh_token={REFRESH_TOKEN}";
	public static final String CHECK_WEB_ACCESS_TOKEN="https://api.weixin.qq.com/sns/auth?"
			+ "access_token={ACCESS_TOKEN}&openid={OPENID}";
	public static final String GET_WEB_USERINFO="https://api.weixin.qq.com/sns/userinfo?"
			+ "access_token={ACCESS_TOKEN}&openid={OPENID}&lang={LANG}";
	public static final String GET_USERINFO="https://api.weixin.qq.com/cgi-bin/user/info?"
			+ "access_token={ACCESS_TOKEN}&openid={OPENID}&lang={LANG}";
	public static final String GET_ACCESS_TOKEN="https://api.weixin.qq.com/cgi-bin/token?"
			+ "grant_type={GRANT_TYPE}&appid={APPID}&secret={SECRET}";
	public static final String GET_JSAPI_TICKET="https://api.weixin.qq.com/cgi-bin/ticket/getticket?"
			+ "access_token={ACCESS_TOKEN}&type={TYPE}";
	
	public static final String STATE="STATE";

}
