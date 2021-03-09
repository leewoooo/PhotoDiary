package com.diary.util;

import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

@Component
public class GoToPage {
	
	private final static int SUCCESS = 1;

	public String error(Model model, String msg) {
		model.addAttribute("msg", msg);
		return "error";
	}
	
	//db처리 결과와 errormsg를 받아 결과가 성공적이면 일기페이지로 리다이렉트
	//아니면 error msg를 가지고 view페이지로 이동
	public String redirect(int result,String msg,Model model) {
		if(result == SUCCESS) {
			return "redirect:listDiary.do";
		}//end if
		return error(model, msg);
	}
	
}
