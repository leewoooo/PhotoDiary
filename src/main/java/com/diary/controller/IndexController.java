package com.diary.controller;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.diary.service.UserService;
import com.diary.util.GoToPage;
import com.diary.vo.UserVO;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class IndexController {
	
	private final UserService userService; 
	private final GoToPage goToError;
	
	//회원가입 폼으로 이동
	@GetMapping("/signUp.do")
	public String signUpForm() {
		return "signUp";
	}//signUpForm
	
	//회원가입
	@PostMapping("/signUp.do")
	public String signUp(UserVO userVO, Model model) {
		int result = userService.signUp(userVO);
		if(result == 1) {
			return "login";
		}//end if
		String msg = "회원가입에 실패하였습니다 잠시 후 다시 시도해보세요.";
		return goToError.error(model, msg);
	}//signUp

	//로그인 폼으로 이동
	@GetMapping("/signIn.do")
	public String loginForm() {
		return "login";
	}//loginForm
	
	//로그인
	@PostMapping("/signIn.do")
	public String login(String userID, String userPassword,HttpSession httpSession,Model model) {
		//사용자가 입력한 아이디와 비밀번호로 db를 검색하여 로그인 조건에 맞을 경우 session에 저장 후 일기페이지로 이동
		int result = userService.login(userID, userPassword);
		if(result == 1) {
			httpSession.setAttribute("userID", userID);
			return "redirect:listDiary.do";
		}//end if
		//로그인이 되지 않았을 경우
		String msg = "아이디나 비밀번호를 확인 후 시도해보세요.";
		return goToError.error(model, msg);
	}//login
	
	//로그아웃
	@GetMapping("/logout.do")
	public String logout(HttpSession httpSession) {
		httpSession.invalidate();
		return "index";
	}
	
}
