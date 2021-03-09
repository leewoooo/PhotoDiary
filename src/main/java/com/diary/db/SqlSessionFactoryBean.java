package com.diary.db;

import java.io.IOException;
import java.io.Reader;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

public class SqlSessionFactoryBean {
	
	private static SqlSessionFactory sessionFactory;
	
	static{
		if(sessionFactory == null) {
			try(Reader reader = Resources.getResourceAsReader("com/diary/db/dbConfig.xml")){
				sessionFactory = new SqlSessionFactoryBuilder().build(reader);
			}catch(IOException e) {
				e.printStackTrace();
			}//end catch
		}//end if
	}//end static 

	public static SqlSession getSqlSession() {
		return sessionFactory.openSession();
	}//getSqlSession
}
