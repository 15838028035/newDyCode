package com.weixindev.micro.serv.common.bean.report;
import com.weixindev.micro.serv.common.base.entity.BaseEntity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;


/**
*WeixinFansAllCount
*/
@ApiModel(value = "WeixinFansAllCount")
public class WeixinFansAllCount extends BaseEntity{
	
	/**
	 *   id
	 */
	@ApiModelProperty(value = "")
	private java.lang.Integer id;
	
	/**
	 * 粉丝数总计  count
	 */
	@ApiModelProperty(value = "粉丝数总计")
	private java.lang.Integer count;
	
	/**
	 * 男性粉丝  male
	 */
	@ApiModelProperty(value = "男性粉丝")
	private java.lang.Integer male;
	
	/**
	 * 女性粉丝  female
	 */
	@ApiModelProperty(value = "女性粉丝")
	private java.lang.Integer female;
	
	/**
	 * 国内粉丝  chinese
	 */
	@ApiModelProperty(value = "国内粉丝")
	private java.lang.Integer chinese;
	
	/**
	 * 国外粉丝  not_chinese
	 */
	@ApiModelProperty(value = "国外粉丝")
	private java.lang.Integer notChinese;
	
	/**
	 * 取消粉丝  cancel
	 */
	@ApiModelProperty(value = "取消粉丝")
	private java.lang.Integer cancel;
	
	/**
	 *   lang_ch
	 */
	@ApiModelProperty(value = "")
	private java.lang.Integer langCh;
	
	/**
	 *   lang_other
	 */
	@ApiModelProperty(value = "")
	private java.lang.Integer langOther;
	
	/**
	 * 净增合计  net_growth
	 */
	@ApiModelProperty(value = "净增合计")
	private java.lang.Integer netGrowth;
	
	/**
	 *   create_time
	 */
	@ApiModelProperty(value = "")
	private java.util.Date createTime;
	
	 /**
	 * Begin
	 */
	private String  createTimeBegin;
	/**
	 * End
	 */
	private String createTimeEnd;
	/**
	 *   update_time
	 */
	@ApiModelProperty(value = "")
	private java.util.Date updateTime;
	
	 /**
	 * Begin
	 */
	private String  updateTimeBegin;
	/**
	 * End
	 */
	private String updateTimeEnd;
	/**
	 *   nike_name
	 */
	
	/**
	 *   new_fans
	 */
	@ApiModelProperty(value = "")
	private java.lang.Integer newFans;
	
	/**
	 * 公众号搜索  add_scene_search
	 */
	@ApiModelProperty(value = "公众号搜索")
	private java.lang.Integer addSceneSearch;
	
	/**
	 * 公众号文章广告  add_scene_account_migration
	 */
	@ApiModelProperty(value = "公众号文章广告")
	private java.lang.Integer addSceneAccountMigration;
	
	/**
	 * 名片分享  add_scene_profile_card
	 */
	@ApiModelProperty(value = "名片分享")
	private java.lang.Integer addSceneProfileCard;
	
	/**
	 * 扫描二维码  add_scene_qr_code
	 */
	@ApiModelProperty(value = "扫描二维码")
	private java.lang.Integer addSceneQrCode;
	
	/**
	 * 图文页内名称点击  add_sceneprofile_link
	 */
	@ApiModelProperty(value = "图文页内名称点击")
	private java.lang.Integer addSceneprofileLink;
	
	/**
	 * 图文页右上角菜单  add_scene_profile_item
	 */
	@ApiModelProperty(value = "图文页右上角菜单")
	private java.lang.Integer addSceneProfileItem;
	
	/**
	 * 支付后关注  add_scene_paid
	 */
	@ApiModelProperty(value = "支付后关注")
	private java.lang.Integer addScenePaid;
	
	/**
	 * 其他  add_scene_others
	 */
	@ApiModelProperty(value = "其他")
	private java.lang.Integer addSceneOthers;
	
	/**
	 * 朋友圈广告  add_circle_friends
	 */
	@ApiModelProperty(value = "朋友圈广告")
	private java.lang.Integer addCircleFriends;
	


	public void setId(java.lang.Integer value) {
		this.id = value;
	}
	
	public java.lang.Integer getId() {
		return this.id;
	}
	public void setCount(java.lang.Integer value) {
		this.count = value;
	}
	
	public java.lang.Integer getCount() {
		return this.count;
	}
	public void setMale(java.lang.Integer value) {
		this.male = value;
	}
	
	public java.lang.Integer getMale() {
		return this.male;
	}
	public void setFemale(java.lang.Integer value) {
		this.female = value;
	}
	
	public java.lang.Integer getFemale() {
		return this.female;
	}
	public void setChinese(java.lang.Integer value) {
		this.chinese = value;
	}
	
	public java.lang.Integer getChinese() {
		return this.chinese;
	}
	public void setNotChinese(java.lang.Integer value) {
		this.notChinese = value;
	}
	
	public java.lang.Integer getNotChinese() {
		return this.notChinese;
	}
	public void setCancel(java.lang.Integer value) {
		this.cancel = value;
	}
	
	public java.lang.Integer getCancel() {
		return this.cancel;
	}
	public void setLangCh(java.lang.Integer value) {
		this.langCh = value;
	}
	
	public java.lang.Integer getLangCh() {
		return this.langCh;
	}
	public void setLangOther(java.lang.Integer value) {
		this.langOther = value;
	}
	
	public java.lang.Integer getLangOther() {
		return this.langOther;
	}
	public void setNetGrowth(java.lang.Integer value) {
		this.netGrowth = value;
	}
	
	public java.lang.Integer getNetGrowth() {
		return this.netGrowth;
	}
	public void setCreateTime(java.util.Date value) {
		this.createTime = value;
	}
	
	public java.util.Date getCreateTime() {
		return this.createTime;
	}
	public void setUpdateTime(java.util.Date value) {
		this.updateTime = value;
	}
	
	public java.util.Date getUpdateTime() {
		return this.updateTime;
	}
	public void setNewFans(java.lang.Integer value) {
		this.newFans = value;
	}
	
	public java.lang.Integer getNewFans() {
		return this.newFans;
	}
	public void setAddSceneSearch(java.lang.Integer value) {
		this.addSceneSearch = value;
	}
	
	public java.lang.Integer getAddSceneSearch() {
		return this.addSceneSearch;
	}
	public void setAddSceneAccountMigration(java.lang.Integer value) {
		this.addSceneAccountMigration = value;
	}
	
	public java.lang.Integer getAddSceneAccountMigration() {
		return this.addSceneAccountMigration;
	}
	public void setAddSceneProfileCard(java.lang.Integer value) {
		this.addSceneProfileCard = value;
	}
	
	public java.lang.Integer getAddSceneProfileCard() {
		return this.addSceneProfileCard;
	}
	public void setAddSceneQrCode(java.lang.Integer value) {
		this.addSceneQrCode = value;
	}
	
	public java.lang.Integer getAddSceneQrCode() {
		return this.addSceneQrCode;
	}
	public void setAddSceneprofileLink(java.lang.Integer value) {
		this.addSceneprofileLink = value;
	}
	
	public java.lang.Integer getAddSceneprofileLink() {
		return this.addSceneprofileLink;
	}
	public void setAddSceneProfileItem(java.lang.Integer value) {
		this.addSceneProfileItem = value;
	}
	
	public java.lang.Integer getAddSceneProfileItem() {
		return this.addSceneProfileItem;
	}
	public void setAddScenePaid(java.lang.Integer value) {
		this.addScenePaid = value;
	}
	
	public java.lang.Integer getAddScenePaid() {
		return this.addScenePaid;
	}
	public void setAddSceneOthers(java.lang.Integer value) {
		this.addSceneOthers = value;
	}
	
	public java.lang.Integer getAddSceneOthers() {
		return this.addSceneOthers;
	}
	public void setAddCircleFriends(java.lang.Integer value) {
		this.addCircleFriends = value;
	}
	
	public java.lang.Integer getAddCircleFriends() {
		return this.addCircleFriends;
	}

	
}

