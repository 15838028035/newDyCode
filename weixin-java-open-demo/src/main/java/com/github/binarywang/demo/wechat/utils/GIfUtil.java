package com.github.binarywang.demo.wechat.utils;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;


public class GIfUtil {

	/**
     * 获取GIF图片一帧图片 - 同步执行
     * @param src       源图片路径
     * @param target    目标图片路径
     * @param frame     获取第几帧
     * @throws Exception
     */
    public static void  saveGif(String src, String target, int frame) throws Exception {
    	 try {  
             //要操作的图片  
             FileInputStream is=new FileInputStream("http://od710rrnd.bkt.clouddn.com/biz/img/201805181519057276630.gif");  
               
             //把图片读取读取到内存的流  
             ByteArrayOutputStream bos=new ByteArrayOutputStream();  
               
             //原图保存位置  
             FileOutputStream fos=new FileOutputStream("E:\\test\\02\\t_01.gif");  
               
             byte buffer[]=new byte[1024];  
             int leng=0;  
             while((leng=is.read(buffer))!=-1){  
                 fos.write(buffer, 0, leng);  
                 bos.write(buffer, 0, leng);  
             }  
               
             is.close();  
             fos.close();  
               
         } catch (Exception e) {  
             e.printStackTrace();  
         }  
    }
    
    /**
     * 获取GIF图片一帧图片 - 同步执行
     * @param src       源图片路径
     * @param target    目标图片路径
     * @param frame     获取第几帧
     * @throws Exception
     */
    public static void  saveGif(InputStream is, String outfilePath) throws Exception {
    	 try {  
               
             //把图片读取读取到内存的流  
             ByteArrayOutputStream bos=new ByteArrayOutputStream();  
               
             //原图保存位置  
             FileOutputStream fos=new FileOutputStream(outfilePath);  
               
             byte buffer[]=new byte[1024];  
             int leng=0;  
             while((leng=is.read(buffer))!=-1){  
                 fos.write(buffer, 0, leng);  
                 bos.write(buffer, 0, leng);  
             }  
               
             is.close();  
             fos.close();  
               
         } catch (Exception e) {  
             e.printStackTrace();  
         }  
    }
    
    //InputStream
    
    public static void main(String []ags) throws Exception{
    	String url = "http://od710rrnd.bkt.clouddn.com/biz/img/201805181519057276630.gif";
    	URL fileUrl = new URL(url);
    	URLConnection rulConnection = fileUrl.openConnection();// 此处的urlConnection对象实际上是根据URL的
		HttpURLConnection httpUrlConnection = (HttpURLConnection) rulConnection;
		InputStream inputStream = httpUrlConnection.getInputStream();
		
		GIfUtil.saveGif(inputStream,"d:\\data\\11.gif");
    }
}
