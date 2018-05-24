package com.github.binarywang.demo.wechat.utils;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import com.weixindev.micro.serv.common.util.FileType;
import com.weixindev.micro.serv.common.util.FileTypeJudge;


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
    
    public static void main(String []ags) throws Exception {
    	String url = "http://od710rrnd.bkt.clouddn.com/biz/img/201805181519057276630.gif";
    	String url2 = "http://od710rrnd.bkt.clouddn.com/biz/img/201805211337176553178.gif";
    	/*URL fileUrl = new URL(url);
    	URLConnection rulConnection = fileUrl.openConnection();// 此处的urlConnection对象实际上是根据URL的
		HttpURLConnection httpUrlConnection = (HttpURLConnection) rulConnection;
		InputStream inputStream = httpUrlConnection.getInputStream();
		*/
		//GIfUtil.saveGif(inputStream,"d:\\data\\12.gif");
		
		List<String> strContentList = new ArrayList<String>();
		strContentList.add(url2);
		strContentList.add(url2);
		
		String file_location = "d:\\data\\";

			for (String strCon : strContentList) {
				System.out.println("图片strCon=" + strCon);
					
					 URL netUrl = new URL(strCon);   
				        URLConnection rulConnection = netUrl.openConnection();// 此处的urlConnection对象实际上是根据URL的
						HttpURLConnection httpUrlConnection = (HttpURLConnection) rulConnection;
						InputStream inputStream = httpUrlConnection.getInputStream();
						
						 ByteArrayOutputStream baos = new ByteArrayOutputStream();  
						 byte[] buffer = new byte[1024];  
						 int len;  
						 while ((len = inputStream.read(buffer)) > -1 ) {  
						     baos.write(buffer, 0, len);  
						 }  
						 baos.flush();                
						   
						 InputStream inputStream1 = new ByteArrayInputStream(baos.toByteArray());  
						 InputStream inputStream2 = new ByteArrayInputStream(baos.toByteArray());  
						
						FileType FileType = FileTypeJudge.getType(inputStream1);
						
						String extName = FileType.name().toLowerCase();//文件扩展名
						
						String fileName = System.nanoTime()+"."+extName; 
						String filePath = file_location +fileName;
				        
				        if(FileType.GIF.name().toLowerCase().equals(extName)){
							GIfUtil.saveGif(inputStream2,filePath);
						}else {
							BufferedImage image = null; 
							image = ImageIO.read(netUrl);    
					        ImageIO.write(image, extName, new File(filePath));   
						}
				        
				        if(inputStream!=null){
				        	inputStream.close();
				        }

						if(FileType.GIF.name().toLowerCase().equals(extName)){
							 System.out.println("GIF file Path = " + filePath );
								
							}else {
						
							 System.out.println(filePath );
										
							}
					
			}
    }
}
