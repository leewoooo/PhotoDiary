나만의 그림일기 만들기(springboot환경에서 게시판 만들기 model2)
===

## Project 목표

1. Model2 방식을 이용하여 MVC를 이해하고 웹의 3계층에 맞춰 코드를 작성합니다.

    * Controller - Service - Repository(DAO)를 설정하고 역할에 맞게 코드를 작성(컨트롤러에 요청이 들어오면 서비스에서 비지니스 로직을 처리하고 리포지토리에서 DB와 접근을 해 데이터를 조작합니다.)

    * View에서 Java의 문법등을 제거하고 View는 화면을 보여주는 역할만 집중할 수 있도록! JSTL을 이용합니다.

    * springboot환경에 익숙해지는것을 목표로 합니다.

2. Paging처리

    * Mybatis 연동 및 활용하여 게시글의 페이지를 처리합니다.
    
    * 페이징을 처리하는 객체를 생성 후 DB를 조작합니다.

3. 파일 업로드

    * 사용자가 파일을 업로드 하면 저장을 하고 사진과 같은 경우 필요할 때 가져와 view에서 보여질 수 있도록 합니다.

4. 계층형 게시판

    * 작성된 게시글에 대한 답글을 작성할 수 있도록 합니다.


### 회원 가입,로그인 처리,view는 최대한 간소화 하였습니다. 현재 프로젝트는 파일업로드, 계층형 게시판, 페이징처리를 중점으로 다루기 때문입니다 이전 정리해 놓은 개념들을 사용하며 숙달하는 것이 목표입니다..

<Br>

## 사용기술

Java 1.8,Oracle,SpringBoot,MyBatis,Maven,bootstrap,Lombok

<br>

---

<Br>

## 프로젝트를 하면서 느낀점

강의시간에 배운 것들 및 혼자 스스로 공부한 것들에 대한 개념적인 것들을 직접 사용해보고 익숙해질 수 있는 시간들이였습니다. 물론 JSP로 게시판을 만들어본 경험이 있기 때문에 이전 게시판 구현을 했을 때 보다는 손 쉽게 구현을 할 수 있었습니다. 그 때는 순수 JDBC를 통해 DB처리를 했는데 이번에는 MyBatis를 통해 조금 더 편리한 DB접근을 할 수 있었습니다. 이 후 공부해야할 부분들은 Spring Data JPA를 조금 더 깊게 공부하여 DB와 객체를 Mapping하여 프로젝트를 진행해보겠습니다. 또한 스프링 부트의 편리한 기능들을 경험할 수 있는 프로젝트였던 것 같습니다. 무엇보다 이번에 또한 무엇인가를 만들어 보는것을 통해 기술 및 개념을 습득하는 것이 가장 빠른 방법이라는 것을 느끼게 되었습니다.

<br>

---

<br>

## 페이지 흐름도

* 흐름도

    <img src = https://user-images.githubusercontent.com/74294325/110459965-f6805f00-8110-11eb-958d-a0fe6b2e41c4.png>

<Br>

---

<br>

## ERD

* ERD

    <img src = https://user-images.githubusercontent.com/74294325/110460528-a6ee6300-8111-11eb-90ac-38f28f9e3fa3.PNG>

<br>

기존 게시판 테이블과 유사하지만 계층형 게시판을 위해 B_REF,B_STEP,B_LEVEL column이 추가되었습니다.

<br>

---

<Br>

## 구현 화면

<Strong>이미지를 클릭하면 구현 영상을 확인하실 수 있습니다:)</strong>

[![게시판](https://user-images.githubusercontent.com/74294325/110464076-09496280-8116-11eb-89ef-5582cde6076c.PNG)](https://www.youtube.com/watch?v=mCMRB09aPBs)

<br>

---

<br>

## Paging처리

이전 정리한 글을 참고하면 조금 더 쉽게 이해할 수 있습니다. [SpringBoot,MyBatis 환경에서 페이징 처리](https://github.com/LeeWoooo/SIST_Class/tree/master/spring/spring_MyBatis%ED%99%98%EA%B2%BD_paging%EC%B2%98%EB%A6%AC) <br>

간략하게 다시 정리를 하자면 페이징 처리를 하기 위해 PagiNation이라는 class를 작성하였습니다. 이 class를 객체로 생성할 때 생성자에 게시글의 총 갯수와 현재의 페이지 번호를 받아서 객체를 생성합니다. <br>

게시글의 갯수를 받아 총 페이지의 수를 결정하고, 페이지 번호를 받아 게시글을 보여줄 범위를 결정하게 됩니다.

* PagiNation

    ```java
    @Getter
    public class PagiNation {
        
        private int pageSize;
        private int count;
        private int totalPage;
        private int start;
        private int end;
        
        public PagiNation(int count ,int pageNumber) {
            this.count = count;
            this.pageSize = 10;
            this.totalPage = (int)Math.ceil(count/(double)pageSize);
            //만약 10개를 보여주고 현제 pageNumber가 1이라면 
            //시작값은 0
            //끝값은 11로 해서 범위를 0 < seq AND 11 > seq로 하면 10개의 게시물을 보여줄 수 있다.
            this.start = (pageSize*pageNumber) - pageSize;
            this.end = start + pageSize + 1;
        }
    }
    ```

이렇게 생성한 객체를 통해 페이징 처리를 하게 되는데 이 때 생성된 start와 end를 통해 DB를 검색하게 됩니다. 현재 프로젝트는 Oracle을 사용하고 있기 때문에 페이징 처리 query를 작성할 때 ROW_NUMBER()OVER()를 사용하여 작성하였습니다.

* query

    ```xml
    <![CDATA[
		SELECT USERID,DIARYTITLE,DIARYCONTENT,CREATEDAT,MODIFIEDAT,FNAME,DIARYNO,B_REF,B_STEP,B_LEVEL,SEQ 
		FROM ( SELECT USERID,DIARYTITLE,DIARYCONTENT,CREATEDAT,MODIFIEDAT,FNAME,DIARYNO,B_REF,B_STEP,B_LEVEL,ROW_NUMBER()OVER(ORDER BY B_REF DESC,B_STEP) SEQ 
		 		FROM DIARYBOARD 
		 		WHERE DIARYNO > 0 AND USERID=#{userID})
		WHERE SEQ > #{start} AND SEQ < #{end}
    ]]> 		
    ```

또한 PagiNation의 필드 중 totalPage를 view로 넘겨 반복문을 사용해 페이지 번호를 게시글 리스트 밑에 보여주도록 구현하였습니다.

<br>

---

<Br>

## 파일 업로드

이전 정리한 글을 참고하면 조금 더 쉽게 이해할 수 있습니다. [스프링에서 파일 업로드 하기](https://github.com/LeeWoooo/SIST_Class/tree/master/spring/Spring_FileUpload) <br>

간단하게 다시 정리하자면 스프링에서 파일업로드를 하려면 먼저 "CommonsMultipartResolver" 객체가 Bean으로 등록되어 있어야합니다. 저는 xml이 아닌 java문법으로 Config파일을 작성하여 Bean으로 등록하였습니다.

* Config

    ```java
    @Configuration
    public class SpringConfig {
        //파일 업로드를 하기위해 필요한 객체를 config파일로 Bean등록
        @Bean
        public CommonsMultipartResolver multipartResolver() {
            return new CommonsMultipartResolver();
        }
    }
    ```

이 후 두 개의 라이브러리를 사용하게 되는데 프로젝트 환경이 maven이니 pom.xml에 의존성을 추가해주겠습니다. (gradle일 경우는 build.gradle에 추가하면 됩니다.) 라이브러리는 https://mvnrepository.com 메이븐리포지토리에서 가져오면 됩니다. 

* 의존성 추가

    ```xml
    <!-- https://mvnrepository.com/artifact/commons-fileupload/commons-fileupload -->
    <dependency>
        <groupId>commons-fileupload</groupId>
        <artifactId>commons-fileupload</artifactId>
        <version>1.3.1</version>
    </dependency>
    
    <!-- https://mvnrepository.com/artifact/commons-io/commons-io -->
    <dependency>
        <groupId>commons-io</groupId>
        <artifactId>commons-io</artifactId>
        <version>2.4</version>
    </dependency>
    ```

form에서 post방식으로 업로드될 파일을 받으면 되는데 이때 enctype을 아래의 코드와 같이 받으면 됩니다.

* form

    ```html
    <form action="write.do" method="post" enctype="multipart/form-data">
    ```

upload된 파일은 MultipartFile 객체에 담기게되는데 form의 입력값들을 VO로 받을 것이기 때문에 VO에 다음과 같은 필드를 추가해주면 됩니다.

* VO

    ```java
    ,,,
    private MultipartFile uploadFile;
    ,,,
    ```

이렇게 받아온 파일은 Controller에서 처리하면 됩니다. 먼저 웹 어플리케이션의 경로를 얻고 저장할 경로르 지정후 Stream을 연결해 파일을 저장합니다! 현재 프로젝트에서는 파일 업로드가 글 작성 , 글 수정에서 발생하는데 파일업로드를 사용할 때는 파일 업로드는 성공하였지만 DB처리가 실패했을 경우를 대비하여 DB 처리 후 결과값이 성공이 아니라면 업로드 된 파일을 지워야 합니다. <br>

또한 게시글을 삭제할 때에도 게시글만 삭제되는 것이 아닌 사진도 같이 삭제되고 수정을 해도 기존 업로드 한 파일을 지우고 새로운 파일 업로드를 진행합니다. 이에 대한 코드는 첨부된 소스를 확인하시면 됩니다.

<br>

---

<br>

## 계층형 게시판

이전 정리한 글을 참고하면 조금 더 쉽게 이해할 수 있습니다. [스프링환경에서 계층형 게시판 만들기](https://github.com/LeeWoooo/SIST_Class/tree/master/spring/%EA%B3%84%EC%B8%B5%ED%98%95%EA%B2%8C%EC%8B%9C%ED%8C%90) <br> 

계층형 게시판은 기본 게시판 테이블에서 3개의 column을 추가하여 구현하였습니다. 어떠한 부모글에 대한 답글인지 알 수 있는 column, 답글 그룹에서의 순서를 알 수 있는 column, 답글의 깊이를 알 수 있는 column 이 세가지 column을 추가합니다. <br>

작성한 글에 대한 저장 요청이 올 때 부모글인지 답글인지를 판단하여 위의 세가지 column의 값을 조작하여 DB에 저장을 하게 됩니다. 이렇게 저장된 데이터를 이용하여 View에서 조건문을 사용하여 게시판 목록에서 답글형태의 View를 보여줍니다.

