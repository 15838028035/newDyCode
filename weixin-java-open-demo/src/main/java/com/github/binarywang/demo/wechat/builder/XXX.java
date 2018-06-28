package com.github.binarywang.demo.wechat.builder;

public class XXX {

	public static void main(String[] args) {
		for(int i=0;i<5;i++) {
			System.out.println("外层上"+i);
			for(int x=0;x<100;x++) {
				if(x==10) {
					System.out.println("---------------------");
					break;
				}
				System.out.println("内循环"+x);
			}
			System.out.println("外层下"+i);
		}
	}

}
