package com.diary.dao;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.diary.db.SqlSessionFactoryBean;
import com.diary.vo.UserVO;

@Repository
public class UserDAO {
	
	private final SqlSession session;

	public UserDAO() {
		session = SqlSessionFactoryBean.getSqlSession();
	}//end UserDAO

	//회원가입
	public int save(UserVO userVO) {
		return session.insert("userMapper.save", userVO);
	}
	
	//로그인
	public UserVO login(String userID) {
		return session.selectOne("userMapper.login", userID);
	}
	
	//커밋
	public void commit() {
		session.commit();
	}

}
