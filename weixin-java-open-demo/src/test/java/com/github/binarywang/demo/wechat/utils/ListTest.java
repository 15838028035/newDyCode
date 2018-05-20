package com.github.binarywang.demo.wechat.utils;

import java.util.ArrayList;
import java.util.List;

import com.weixindev.micro.serv.common.bean.weixin.WeixinImgtextItem;

public class ListTest {

	
	public static void main(String []args) {
		test();
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
