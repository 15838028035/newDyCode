package com.github.binarywang.demo.wechat.utils;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

import javax.imageio.ImageIO;

import com.weixindev.micro.serv.common.util.FileType;
import com.weixindev.micro.serv.common.util.FileTypeJudge;
import com.weixindev.micro.serv.common.util.FileUtil;

public class FileUtilTest {

	public void test()  {
		
		try{
		String file_location = "d:\\data";
		String str = "https://img14.360buyimg.com/n0/jfs/t14614/160/2379557697/66977/4d1c63d4/5a98e7beNb386d452.jpg";
		
		//网络图片地址
		URL fileUrl = new URL(str);
		URLConnection rulConnection = fileUrl.openConnection();// 此处的urlConnection对象实际上是根据URL的
		HttpURLConnection httpUrlConnection = (HttpURLConnection) rulConnection;
		InputStream inputStream = httpUrlConnection.getInputStream();
		
		String extName ="jpg";
		String fileName = System.nanoTime()+"."+extName; 
		String filePath = file_location +fileName;
		FileUtil.createFile(inputStream, filePath);
		
		
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		
	}
	
	public void test2()  {
		  BufferedImage image = null;   
		try{
		String file_location = "";
		
		  URL url = new URL("https://img14.360buyimg.com/n0/jfs/t14614/160/2379557697/66977/4d1c63d4/5a98e7beNb386d452.jpg");   
		
		 image = ImageIO.read(url);    
		    
         ImageIO.write(image, "jpg", new File("d:\\data1.jpg"));    
		
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		
	}
	
	public static  void test3()  {
		  BufferedImage image = null;   
		try{
		String file_location = "";
		
		  URL url = new URL("http://od710rrnd.bkt.clouddn.com/biz/img/201805181506432766278.gif");   
		
		 image = ImageIO.read(url);    
		    
       ImageIO.write(image, "gif", new File("d:\\data1.gif"));    
		
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		
	}
	
	public static  void test4()  {
		  BufferedImage image = null;   
		try{
			String str = "http://od710rrnd.bkt.clouddn.com/biz/img/201805181506432766278.gif";
			
			//网络图片地址
			URL fileUrl = new URL(str);
			URLConnection rulConnection = fileUrl.openConnection();
			HttpURLConnection httpUrlConnection = (HttpURLConnection) rulConnection;
			InputStream inputStream = httpUrlConnection.getInputStream();
			
			FileType FileType = FileTypeJudge.getType2(inputStream);

			String fileName = System.nanoTime() + "." + FileType.name().toLowerCase();
			String filePath = "d:\\" + fileName;
			// 创建实际路径
			OutputStream os = new FileOutputStream(filePath);
			int bytesRead = 0;
			byte[] buffer = new byte[8192];
			while ((bytesRead = inputStream.read(buffer, 0, 8192)) != -1) {
				os.write(buffer, 0, bytesRead);
			}
			os.close();  
		
		}catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
	 public static void main(String[] args){
		test4();
	}
	
}
