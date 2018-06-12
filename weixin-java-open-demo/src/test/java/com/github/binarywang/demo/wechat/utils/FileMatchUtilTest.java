package com.github.binarywang.demo.wechat.utils;

import java.util.List;

import org.junit.Test;

import com.weixindev.micro.serv.common.util.Encrypt;

public class FileMatchUtilTest {

	@Test
	public void test() {
		String enPwd = Encrypt.getEncrypt("BX68r192mXkhSUD6fRulCFhlLGA3SL5Q", "SHA-256");
		System.out.println(enPwd);
		String content = "<p>图文列表测试01<img src=\"http://weixin.xrtz.org:8022/1205300045792641.jpg\"/>lkjokjkjllkk<img src=\"http://weixin.xrtz.org:8022/1094050830994600.png\"/></p>";
		List<String> strList = FileMatchUtil.getImgSrcList(content);
		
		System.out.println("strList.size=" + strList.size());
		
		for(String str: strList) {
			System.out.println("str=" + str);
		}
	}

}
