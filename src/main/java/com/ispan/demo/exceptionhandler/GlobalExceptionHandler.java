package com.ispan.demo.exceptionhandler;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

//表示處理Exception的controller
//裡面的方法也是一種controller
@ControllerAdvice
public class GlobalExceptionHandler {

	//透過設定檔的spring.servlet.multipart.mafilsize設定上傳圖片大小
	@ExceptionHandler(value=MaxUploadSizeExceededException.class)
	public String imageSizeHandler(Model model) {
		model.addAttribute("errorMsg","圖片太大");
		return "house/addHousePage";
		
	}
}
