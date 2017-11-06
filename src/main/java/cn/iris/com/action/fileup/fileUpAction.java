package cn.iris.com.action.fileup;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import ch.qos.logback.classic.util.CopyOnInheritThreadLocal;

@Controller
public class fileUpAction {

		/**
		 * 文件上传
		 * @return
		 */
		@RequestMapping(value="/fileUp")
		public String fileUp(@RequestParam("file") MultipartFile file){
			 if(!file.isEmpty()){  
		            try {  
		               
		                FileUtils.copyInputStreamToFile(file.getInputStream(), new File("d:\\temp\\file\\",   
		                        System.currentTimeMillis()+ file.getOriginalFilename()));  
		            } catch (IOException e) {  
		                e.printStackTrace();  
		            }
		        }
		  
		        return "fileUp/success";  //
		}
		
		@RequestMapping(value="/fileUpbatch")
		public String fileUpbatch(@RequestParam("files") MultipartFile[] file,HttpServletRequest request){
			if(file == null){
				return "error";
			}
			for (int i = 0; i < file.length; i++) {
				fileUpSim(file[i],request);
			}
			
			return "fileUp/success";  // 
		}
		
		public void fileUpSim(MultipartFile file,HttpServletRequest request){
			  try {
//				FileUtils.copyInputStreamToFile(file.getInputStream(), new File("d:\\temp\\file\\",   
//				          System.currentTimeMillis()+ file.getOriginalFilename()));
				//copyInputStreamToFile(InputStream source, File destination)
				String path =  request.getSession().getServletContext().getRealPath("/"); 
				System.out.println(path+file.getOriginalFilename());  
				  FileUtils.copyInputStreamToFile(file.getInputStream(), new File(path+file.getOriginalFilename()));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}  
		}
}
