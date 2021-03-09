<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>Insert title here</title>
	</head>
	<body>
		<%@ include file="header.jsp" %>
		<div class="container" style="margin-top: 15px">
		<form action="update.do" method="post" enctype="multipart/form-data">
		  <table class="table table-bordered" style="text-align: center;">
		    <thead>
		      <tr>
		        <th colspan="2">그림일기 수정</th>
		      </tr>
		    </thead>
		    <tbody>
		      <tr>
				<td><input type="text" class="form-control" placeholder="글 제목" name="diaryTitle" maxlength="50" required="required" value="${diary.diaryTitle }"></td> 
			  </tr>
			  <tr>
				<td><textarea class="form-control" placeholder="글 내용" name="diaryContent" style="height: 400px" required="required">${diary.diaryContent}</textarea></td>
			  </tr>
			  <tr>
				<td><img src="images/${diary.fname}"></td>
			  </tr>
			  <tr>
				<td><input type="file" name="uploadFile"></td>
			  </tr>
		    </tbody>
		  </table>
		  <input type="hidden" name="diaryNO" value="${diary.diaryNO}">
		  <input type="hidden" name="fname" value="${diary.fname}">
		  <input type="submit" class="btn btn-dark pull-right" value="일기 수정">
		  <a href="listDiary.do" class="btn btn-dark">목록</a>
		</form>
		</div>
	</body>
</html>