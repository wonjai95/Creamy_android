package com.example.ko_desk.myex_10;


public class Web {

    // 1. 이클립스 톰캣 실행 : Ex14 프로젝트
    // 2. servlet-context.xml
    //    <beans:property name="jndiName" value="java:comp/env/jdbc/mybatis_76" 로 수정
    //    context.xml  id: mybatis_76   /   password : tiger



    // 3. 크롬 실행 : http://localhost:8081/Creamy_CRM/android/
    // ipconfig 확인 .. 연결이 끊김 으로 되어있으면 연결불가
    public static String ip = "192.168.219.116"; //본인 IP
    // http://본인IP:8081/컨텍스트명(스프링 3번째 패키지)/   ==> 현재 포트번호가 80으로 설정했으므로 포트번호 생략
    public static String servletURL = "http://" + ip + "/Creamy_CRM/android/"; //연결할 JSP URL



}
