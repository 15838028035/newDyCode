package com.github.binarywang.demo.wechat.utils;

import java.util.ArrayList;
import java.util.List;

import org.jasypt.encryption.StringEncryptor;
import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.jasypt.util.text.BasicTextEncryptor;
import org.springframework.beans.factory.annotation.Autowired;

import com.weixindev.micro.serv.common.bean.weixin.WeixinImgtextItem;
public class ListTest {
	@Autowired
	StringEncryptor stringEncryptor;
	private static final String KEY = "EbfYkitulv73I2p0mXI50JMXoaxZTKJ7"; 
	public static void main(String []args) {
		//test();
		StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();  
		encryptor.setPassword(KEY);  
		System.out.println(encryptor.decrypt("BX68r192mXkhSUD6fRulCFhlLGA3SL5Q")); 
	}
	
	public static  void test(){
		
		List<WeixinImgtextItem>  list = new ArrayList<WeixinImgtextItem>();
		
		WeixinImgtextItem WeixinImgtextItem1 = new WeixinImgtextItem();
		WeixinImgtextItem1.setHeadImg("head1");
		
		WeixinImgtextItem WeixinImgtextItem2 = new WeixinImgtextItem();
		WeixinImgtextItem2.setHeadImg("head2");
		
		list.add(WeixinImgtextItem1);
		list.add(WeixinImgtextItem2);
		
		
		for(WeixinImgtextItem WeixinImgtextItem :list){
			WeixinImgtextItem.setHeadImg("head111");
		}
		
		
		for(WeixinImgtextItem WeixinImgtextItem :list){
			String headImg = WeixinImgtextItem.getHeadImg();
			
			System.out.println("HeadImg=" + headImg);
		}
		
	}
}
