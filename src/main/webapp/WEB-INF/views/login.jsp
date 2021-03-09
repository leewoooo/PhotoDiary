<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>그림일기</title>
		<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
	</head>
	<body>
		<div class="container" style="margin-top: 50px">
		  <div class="jumbotron" align="center">
		    <h1>로그인 하기</h1>      
		    <p>로그인에 필요한 정보들을 입력해주세요.</p>
		  </div>
	  	</div>
	  	<div class="container">
			<div class="row justify-content-center">
	            <div class="col-md-8">
	                <div class="card">
	                    <div class="card-header">로그인</div>
	                    <div class="card-body">
	                        <form class="form-horizontal" method="post" action="signIn.do">
	                            <div class="form-group">
	                                <label for="userName" class="cols-sm-2 control-label">Your ID</label>
	                                <div class="cols-sm-10">
	                                    <div class="input-group">
	                                        <span class="input-group-addon"><i class="fa fa-user fa" aria-hidden="true"></i></span>
	                                        <input type="text" class="form-control" name="userID" id="userID" placeholder="Enter your ID" required="required" />
	                                    </div>
	                                </div>
	                            </div>
	                            <div class="form-group">
	                                <label for="userEmail" class="cols-sm-2 control-label">Your Password</label>
	                                <div class="cols-sm-10">
	                                    <div class="input-group">
	                                        <span class="input-group-addon"><i class="fa fa-envelope fa" aria-hidden="true"></i></span>
	                                        <input type="password" class="form-control" name="userPassword" id="userPassword" placeholder="Enter your Password" required="required"/>
	                                    </div>
	                                </div>
	                            </div>
	                            <div class="form-group ">
	                                <input type="submit" class="btn btn-dark btn-lg btn-block login-button" value="Login">
	                            </div>
	                        </form>
	                    </div>
	                </div>
	            </div>
	        </div>
		</div>
	</body>
</html>