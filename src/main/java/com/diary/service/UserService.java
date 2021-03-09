package com.diary.service;

import org.springframework.stereotype.Service;

import com.diary.dao.UserDAO;
import com.diary.vo.UserVO;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UserService {
	
	private final UserDAO userDAO;
	
	public int signUp(UserVO userVO) {
		int flag = userDAO.save(userVO);
		if(flag == 1) {
			userDAO.commit();
		}
		return flag;
	}//signup
	
	public int login(String userID,String userPassword) {
		int flag = -1;
		
		//현재 입력받은 id로 검색했을 때 검색 결과가 있고 비밀번호도 일치할때
		UserVO userDbVO = userDAO.login(userID);
		if(userDbVO != null && userPassword.equals(userDbVO.getUserPassword())) {
			flag = 1;
		}//end if
		
		return flag;
	}//login

}
