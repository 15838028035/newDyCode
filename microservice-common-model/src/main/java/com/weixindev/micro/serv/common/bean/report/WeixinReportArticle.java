package com.weixindev.micro.serv.common.bean.report;
import com.weixindev.micro.serv.common.base.entity.BaseEntity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;


/**
*WeixinReportArticle
*/
@ApiModel(value = "WeixinReportArticle")
public class WeixinReportArticle extends BaseEntity{
	
	/**
	 * ID  id
	 */
	@ApiModelProperty(value = "ID")
	private java.lang.Integer id;
	
	/**
	 * msgid  msgid
	 */
	@ApiModelProperty(value = "msgid")
		private String msgid = "";
	
	/**
	 * 图文消息的标题  title
	 */
	@ApiModelProperty(value = "图文消息的标题")
		private String title = "";
	
	/**
	 * 统计的日期  stat_date
	 */
	@ApiModelProperty(value = "统计的日期")
	private java.util.Date statDate;
	
	 /**
	 * 统计的日期Begin
	 */
	private String  statDateBegin;
	/**
	 * 统计的日期End
	 */
	private String statDateEnd;
	/**
	 * 总粉丝数  target_user
	 */
	@ApiModelProperty(value = "总粉丝数")
	private java.lang.Integer targetUser;
	
	/**
	 * 图文页（点击群发图文卡片进入的页面）的阅读人数  int_page_read_user
	 */
	@ApiModelProperty(value = "图文页（点击群发图文卡片进入的页面）的阅读人数")
	private java.lang.Integer intPageReadUser;
	
	/**
	 * 图文页的阅读次数  int_page_read_count
	 */
	@ApiModelProperty(value = "图文页的阅读次数")
	private java.lang.Integer intPageReadCount;
	
	/**
	 * 原文页（点击图文页“阅读原文”进入的页面）的阅读人数，无原文页时此处数据为0  ori_page_read_user
	 */
	@ApiModelProperty(value = "原文页（点击图文页“阅读原文”进入的页面）的阅读人数，无原文页时此处数据为0")
	private java.lang.Integer oriPageReadUser;
	
	/**
	 * 原文页的阅读次数  ori_page_read_count
	 */
	@ApiModelProperty(value = "原文页的阅读次数")
	private java.lang.Integer oriPageReadCount;
	
	/**
	 * 分享的人数  share_user
	 */
	@ApiModelProperty(value = "分享的人数")
	private java.lang.Integer shareUser;
	
	/**
	 * 分享的次数  share_count
	 */
	@ApiModelProperty(value = "分享的次数")
	private java.lang.Integer shareCount;
	
	/**
	 * 收藏的人数  add_to_fav_user
	 */
	@ApiModelProperty(value = "收藏的人数")
	private java.lang.Integer addToFavUser;
	
	/**
	 * 收藏的次数  add_to_fav_count
	 */
	@ApiModelProperty(value = "收藏的次数")
	private java.lang.Integer addToFavCount;
	
	/**
	 * 公众号会话阅读人数  int_page_from_session_read_user
	 */
	@ApiModelProperty(value = "公众号会话阅读人数")
	private java.lang.Integer intPageFromSessionReadUser;
	
	/**
	 * 公众号会话阅读次数  int_page_from_session_read_count
	 */
	@ApiModelProperty(value = "公众号会话阅读次数")
	private java.lang.Integer intPageFromSessionReadCount;
	
	/**
	 * 历史消息页阅读人数  int_page_from_hist_msg_read_user
	 */
	@ApiModelProperty(value = "历史消息页阅读人数")
	private java.lang.Integer intPageFromHistMsgReadUser;
	
	/**
	 * 历史消息页阅读次数  int_page_from_hist_msg_read_count
	 */
	@ApiModelProperty(value = "历史消息页阅读次数")
	private java.lang.Integer intPageFromHistMsgReadCount;
	
	/**
	 * 朋友圈阅读人数  int_page_from_feed_read_user
	 */
	@ApiModelProperty(value = "朋友圈阅读人数")
	private java.lang.Integer intPageFromFeedReadUser;
	
	/**
	 * 朋友圈阅读次数  int_page_from_feed_read_count
	 */
	@ApiModelProperty(value = "朋友圈阅读次数")
	private java.lang.Integer intPageFromFeedReadCount;
	
	/**
	 * 好友转发阅读人数  int_page_from_friends_read_user
	 */
	@ApiModelProperty(value = "好友转发阅读人数")
	private java.lang.Integer intPageFromFriendsReadUser;
	
	/**
	 * 好友转发阅读次数  int_page_from_friends_read_count
	 */
	@ApiModelProperty(value = "好友转发阅读次数")
	private java.lang.Integer intPageFromFriendsReadCount;
	
	/**
	 * 其他场景阅读人数  int_page_from_other_read_user
	 */
	@ApiModelProperty(value = "其他场景阅读人数")
	private java.lang.Integer intPageFromOtherReadUser;
	
	/**
	 * 其他场景阅读次数  int_page_from_other_read_count
	 */
	@ApiModelProperty(value = "其他场景阅读次数")
	private java.lang.Integer intPageFromOtherReadCount;
	
	/**
	 * 公众号会话转发朋友圈人数  feed_share_from_session_user
	 */
	@ApiModelProperty(value = "公众号会话转发朋友圈人数")
	private java.lang.Integer feedShareFromSessionUser;
	
	/**
	 * 公众号会话转发朋友圈次数  feed_share_from_session_cnt
	 */
	@ApiModelProperty(value = "公众号会话转发朋友圈次数")
	private java.lang.Integer feedShareFromSessionCnt;
	
	/**
	 * 朋友圈转发朋友圈人数  feed_share_from_feed_user
	 */
	@ApiModelProperty(value = "朋友圈转发朋友圈人数")
	private java.lang.Integer feedShareFromFeedUser;
	
	/**
	 * 朋友圈转发朋友圈次数  feed_share_from_feed_cnt
	 */
	@ApiModelProperty(value = "朋友圈转发朋友圈次数")
	private java.lang.Integer feedShareFromFeedCnt;
	
	/**
	 * 其他场景转发朋友圈人数  feed_share_from_other_user
	 */
	@ApiModelProperty(value = "其他场景转发朋友圈人数")
	private java.lang.Integer feedShareFromOtherUser;
	
	/**
	 * 其他场景转发朋友圈次数  eed_share_from_other_cnt
	 */
	@ApiModelProperty(value = "其他场景转发朋友圈次数")
	private java.lang.Integer eedShareFromOtherCnt;
	
	/**
	 * 公众号ID  weixin_user_id
	 */
	@ApiModelProperty(value = "公众号ID")
	private java.lang.Integer weixinUserId;

	public java.lang.Integer getId() {
		return id;
	}

	public void setId(java.lang.Integer id) {
		this.id = id;
	}

	public String getMsgid() {
		return msgid;
	}

	public void setMsgid(String msgid) {
		this.msgid = msgid;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public java.util.Date getStatDate() {
		return statDate;
	}

	public void setStatDate(java.util.Date statDate) {
		this.statDate = statDate;
	}

	public String getStatDateBegin() {
		return statDateBegin;
	}

	public void setStatDateBegin(String statDateBegin) {
		this.statDateBegin = statDateBegin;
	}

	public String getStatDateEnd() {
		return statDateEnd;
	}

	public void setStatDateEnd(String statDateEnd) {
		this.statDateEnd = statDateEnd;
	}

	public java.lang.Integer getTargetUser() {
		return targetUser;
	}

	public void setTargetUser(java.lang.Integer targetUser) {
		this.targetUser = targetUser;
	}

	public java.lang.Integer getIntPageReadUser() {
		return intPageReadUser;
	}

	public void setIntPageReadUser(java.lang.Integer intPageReadUser) {
		this.intPageReadUser = intPageReadUser;
	}

	public java.lang.Integer getIntPageReadCount() {
		return intPageReadCount;
	}

	public void setIntPageReadCount(java.lang.Integer intPageReadCount) {
		this.intPageReadCount = intPageReadCount;
	}

	public java.lang.Integer getOriPageReadUser() {
		return oriPageReadUser;
	}

	public void setOriPageReadUser(java.lang.Integer oriPageReadUser) {
		this.oriPageReadUser = oriPageReadUser;
	}

	public java.lang.Integer getOriPageReadCount() {
		return oriPageReadCount;
	}

	public void setOriPageReadCount(java.lang.Integer oriPageReadCount) {
		this.oriPageReadCount = oriPageReadCount;
	}

	public java.lang.Integer getShareUser() {
		return shareUser;
	}

	public void setShareUser(java.lang.Integer shareUser) {
		this.shareUser = shareUser;
	}

	public java.lang.Integer getShareCount() {
		return shareCount;
	}

	public void setShareCount(java.lang.Integer shareCount) {
		this.shareCount = shareCount;
	}

	public java.lang.Integer getAddToFavUser() {
		return addToFavUser;
	}

	public void setAddToFavUser(java.lang.Integer addToFavUser) {
		this.addToFavUser = addToFavUser;
	}

	public java.lang.Integer getAddToFavCount() {
		return addToFavCount;
	}

	public void setAddToFavCount(java.lang.Integer addToFavCount) {
		this.addToFavCount = addToFavCount;
	}

	public java.lang.Integer getIntPageFromSessionReadUser() {
		return intPageFromSessionReadUser;
	}

	public void setIntPageFromSessionReadUser(java.lang.Integer intPageFromSessionReadUser) {
		this.intPageFromSessionReadUser = intPageFromSessionReadUser;
	}

	public java.lang.Integer getIntPageFromSessionReadCount() {
		return intPageFromSessionReadCount;
	}

	public void setIntPageFromSessionReadCount(java.lang.Integer intPageFromSessionReadCount) {
		this.intPageFromSessionReadCount = intPageFromSessionReadCount;
	}

	public java.lang.Integer getIntPageFromHistMsgReadUser() {
		return intPageFromHistMsgReadUser;
	}

	public void setIntPageFromHistMsgReadUser(java.lang.Integer intPageFromHistMsgReadUser) {
		this.intPageFromHistMsgReadUser = intPageFromHistMsgReadUser;
	}

	public java.lang.Integer getIntPageFromHistMsgReadCount() {
		return intPageFromHistMsgReadCount;
	}

	public void setIntPageFromHistMsgReadCount(java.lang.Integer intPageFromHistMsgReadCount) {
		this.intPageFromHistMsgReadCount = intPageFromHistMsgReadCount;
	}

	public java.lang.Integer getIntPageFromFeedReadUser() {
		return intPageFromFeedReadUser;
	}

	public void setIntPageFromFeedReadUser(java.lang.Integer intPageFromFeedReadUser) {
		this.intPageFromFeedReadUser = intPageFromFeedReadUser;
	}

	public java.lang.Integer getIntPageFromFeedReadCount() {
		return intPageFromFeedReadCount;
	}

	public void setIntPageFromFeedReadCount(java.lang.Integer intPageFromFeedReadCount) {
		this.intPageFromFeedReadCount = intPageFromFeedReadCount;
	}

	public java.lang.Integer getIntPageFromFriendsReadUser() {
		return intPageFromFriendsReadUser;
	}

	public void setIntPageFromFriendsReadUser(java.lang.Integer intPageFromFriendsReadUser) {
		this.intPageFromFriendsReadUser = intPageFromFriendsReadUser;
	}

	public java.lang.Integer getIntPageFromFriendsReadCount() {
		return intPageFromFriendsReadCount;
	}

	public void setIntPageFromFriendsReadCount(java.lang.Integer intPageFromFriendsReadCount) {
		this.intPageFromFriendsReadCount = intPageFromFriendsReadCount;
	}

	public java.lang.Integer getIntPageFromOtherReadUser() {
		return intPageFromOtherReadUser;
	}

	public void setIntPageFromOtherReadUser(java.lang.Integer intPageFromOtherReadUser) {
		this.intPageFromOtherReadUser = intPageFromOtherReadUser;
	}

	public java.lang.Integer getIntPageFromOtherReadCount() {
		return intPageFromOtherReadCount;
	}

	public void setIntPageFromOtherReadCount(java.lang.Integer intPageFromOtherReadCount) {
		this.intPageFromOtherReadCount = intPageFromOtherReadCount;
	}

	public java.lang.Integer getFeedShareFromSessionUser() {
		return feedShareFromSessionUser;
	}

	public void setFeedShareFromSessionUser(java.lang.Integer feedShareFromSessionUser) {
		this.feedShareFromSessionUser = feedShareFromSessionUser;
	}

	public java.lang.Integer getFeedShareFromSessionCnt() {
		return feedShareFromSessionCnt;
	}

	public void setFeedShareFromSessionCnt(java.lang.Integer feedShareFromSessionCnt) {
		this.feedShareFromSessionCnt = feedShareFromSessionCnt;
	}

	public java.lang.Integer getFeedShareFromFeedUser() {
		return feedShareFromFeedUser;
	}

	public void setFeedShareFromFeedUser(java.lang.Integer feedShareFromFeedUser) {
		this.feedShareFromFeedUser = feedShareFromFeedUser;
	}

	public java.lang.Integer getFeedShareFromFeedCnt() {
		return feedShareFromFeedCnt;
	}

	public void setFeedShareFromFeedCnt(java.lang.Integer feedShareFromFeedCnt) {
		this.feedShareFromFeedCnt = feedShareFromFeedCnt;
	}

	public java.lang.Integer getFeedShareFromOtherUser() {
		return feedShareFromOtherUser;
	}

	public void setFeedShareFromOtherUser(java.lang.Integer feedShareFromOtherUser) {
		this.feedShareFromOtherUser = feedShareFromOtherUser;
	}

	public java.lang.Integer getEedShareFromOtherCnt() {
		return eedShareFromOtherCnt;
	}

	public void setEedShareFromOtherCnt(java.lang.Integer eedShareFromOtherCnt) {
		this.eedShareFromOtherCnt = eedShareFromOtherCnt;
	}

	public java.lang.Integer getWeixinUserId() {
		return weixinUserId;
	}

	public void setWeixinUserId(java.lang.Integer weixinUserId) {
		this.weixinUserId = weixinUserId;
	}
	
}

