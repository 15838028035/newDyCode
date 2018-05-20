package com.lj.cloud.weixin.api;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.lj.cloud.weixin.util.FileSizeUtils;


/**
 *file
 */
@RestController
public class FileController {
	
	 protected Logger logger = LoggerFactory.getLogger(FileController.class);
    
	 @Value("${spring.http.multipart.location}")
	 private String filePathRoot ;
	
	@RequestMapping(value = "/file/upload", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> upphoto(HttpServletRequest request, @RequestParam(value="file",required = false)MultipartFile file[]){
		Map<String,Object> retMap = new HashMap<String,Object>();
		retMap.put("msg", "上传成功");
		retMap.put("flag", "1");
		
		try{
		//本地测试路径
		
		//验证地址是否存在
		File targetFile=new File(filePathRoot); 
		if(!targetFile.exists()){
			targetFile.mkdirs();
		}
		
		//遍历
			for(int i=0; i<file.length;i++){
				
				if(!file[i].isEmpty()){
					try {		
						//获取照片原始名称
						String photoName=file[i].getOriginalFilename();
						//扩展名
						String extName = photoName.substring(photoName.lastIndexOf("."));
						//防止图片名称冲突，中文乱码等问题重命名
						photoName = System.nanoTime() + extName; 
						//创建实际路径	
						File fileObj = new File(filePathRoot+photoName);
						
						
						file[i].transferTo(fileObj);
						
							long fileSize = fileObj.length();
						
						String fileSizeName = FileSizeUtils.getHumanReadableFileSize(fileSize);
						
						
						retMap.put("imgUrl", photoName);
						retMap.put("fileSize", fileSize);
						retMap.put("fileSizeName", fileSizeName);
						
					} catch (IllegalStateException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				
			}
		
		return retMap;
			}catch (Exception e) {
				logger.error("上传照片时发生错误：{}"+e.getMessage(), e);
				retMap.put("msg", "上传失败"+e.getMessage());
				retMap.put("flag", "0");
				return retMap;
				
			}

	}
	
}
