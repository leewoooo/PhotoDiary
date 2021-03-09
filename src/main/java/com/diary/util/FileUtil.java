package com.diary.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.springframework.stereotype.Component;

import com.diary.vo.DiaryVO;

@Component
public class FileUtil {

	//파일 이름 vaildation
	public String getFileName(String fname, String path) {
		String newFname = null;
		
		//디렉토리를 만들고
		File dir = new File(path);
		//안에 있는 디렉토리 안에 파일의 리스트를 배열로 받고
		String[] fileList = dir.list();
		boolean flag = false;
		//배열안에서 현재 사용자가 등록한 이미지 파일과 같은지 검사한다.
		for(String name : fileList) {
			if(fname.equals(name)) {
				flag = true;
				break;
			}//end if
		}//end for
		
		newFname = fname;
		//만약 같다면! 이름을 변경해주자
		if(flag) {
			String name = newFname.substring(0,newFname.indexOf("."));
			String ext = newFname.substring(newFname.indexOf("."));
			newFname = name+System.currentTimeMillis()+ext;
		}
		return newFname;
	}
	
	//파일업로드
	public String upload(DiaryVO diaryVO,String fname,String path) throws IOException{
		
		String newFname = getFileName(fname, path);
		byte[] data = diaryVO.getUploadFile().getBytes();
		try(FileOutputStream fileOutputStream = new FileOutputStream(path + "/" + newFname)){
			fileOutputStream.write(data);
		}//end try
		return newFname;
	}
}
