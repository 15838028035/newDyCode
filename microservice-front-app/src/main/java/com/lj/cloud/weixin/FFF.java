package com.lj.cloud.weixin;

public class FFF {

	public static void main(String[] args) {
		String errorMsg = "gdddddddfdsfasdfsdfasd2fassdffsadfasdfda";
		StringBuffer mag = new StringBuffer("");
		if (errorMsg.length() > 20) {
			int beginIndex=0;
			int endIndex=20;
			while(true) {
				if(endIndex>errorMsg.length()) {
					endIndex=errorMsg.length();
					if(endIndex==beginIndex) {
						break;
					}
					mag.append(errorMsg.substring(beginIndex, endIndex) + "<br/>");
					break;
				}
				mag.append(errorMsg.substring(beginIndex, endIndex) + "<br/>");
				beginIndex+=20;
				endIndex+=20;
			}
		} else {
			mag.append(errorMsg + "<br/>");
		}
	}

}
