package com.example.ko_desk.myex_10.Activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ko_desk.myex_10.HttpClient;
import com.example.ko_desk.myex_10.R;
import com.example.ko_desk.myex_10.VO.AuthVO;
import com.example.ko_desk.myex_10.Web;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

public class SignInActivity extends AppCompatActivity {

    EditText edtId, edtPwd;
    Button btnSignIn;
    Button btnJoin;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);

        edtId = (EditText) findViewById(R.id.edt_id);
        edtPwd = (EditText) findViewById(R.id.edt_pwd);
        btnSignIn = (Button) findViewById(R.id.btn_signin);
        btnJoin = (Button) findViewById(R.id.btn_join);

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                InnerTask task = new InnerTask();
                Map<String, String> map = new HashMap<>();
                map.put("id", edtId.getText().toString());
                map.put("pwd", edtPwd.getText().toString());
                task.execute(map);   // doInBackground() 실행 // id, pwd 값 들고 execute 실행하라
            }
        });

        btnJoin.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){

                Intent intent = new Intent(SignInActivity.this,JoinActivity.class); // 로그인페이지에서 마이페이지로 넘어갈 때 그때 id를 넘기겠다.
                startActivity(intent);

            }
        });
        actionBar();
    }

    public void actionBar() {
        ActionBar bar = getSupportActionBar();
        bar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        bar.setCustomView(R.layout.custom_bar);

        LayoutInflater inflater = LayoutInflater.from(this);
        View v = inflater.inflate(R.layout.custom_bar, null);
        TextView tv_bar = (TextView) v.findViewById(R.id.tv_bar);
        tv_bar.setText("로그인");
        bar.setCustomView(v);
    }

    //각 Activity 마다 Task 작성 // Map : id, pwd 들어 있음
    public class InnerTask extends AsyncTask<Map, Integer, String> {

        //doInBackground 실행되기 이전에 동작 // 쓰레드 호출 전 기본 작용 필요할 때
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        //작업을 쓰레드로 처리
        @Override
        protected String doInBackground(Map... maps) {
            //HTTP 요청 준비 // 스프링 호출(자바갈 때 필요)
            HttpClient.Builder http = new HttpClient.Builder("POST", Web.servletURL + "androidSignIn"); //스프링 컨트롤러 requestMapping("androidSignIn")            //파라미터 전송
            // 스트링의 매핑 이름으로 넘김.(androidSignIn)
            http.addAllParameters(maps[0]);  // 입력한 데이터(id, 패스워드)

            //HTTP 요청 전송
            HttpClient post = http.create();
            post.request();

            // 안드로이드에서 응답받음 (body 값 받아오겠다)(줍줍)
            String body = post.getBody(); //Web의 Controller에서 리턴한 값
            Log.d("body------", body);
            return body;
        }

        //doInBackground 종료되면 동작
        /**
         * @param s : doInBackground에서 리턴한 body. JSON 데이터
         */
        @Override
        protected void onPostExecute(String s) {
            Log.d("JSON_RESULT", s);

            //JSON으로 받은 데이터를 VO Obejct로 바꿔준다.
            if(s.length() > 0) {
                Gson gson = new Gson(); // Gson 생성(값 여러개 넘길 수 있다)
                AuthVO vo = gson.fromJson(s,AuthVO.class); // 값이 존재하면 string으로 넘어온 값을 vo타입으로 바꾼다. // vo로 객체 생성

                // 멤버변수에 id가 들어있어 getter로 id 가져올 수 있음.
                //고객 페이지
                if (vo.getAuthority().equals("ROLE_USER")) {
                    // 페이지 이동 : new Intent(현재페이지, 이동페이지) (값들 가지고)
                    Intent intent = new Intent(SignInActivity.this,MyPageUserActivity.class); // 로그인페이지에서 마이페이지로 넘어갈 때 그때 id를 넘기겠다.
                    intent.putExtra("id", vo.getID());   // putExtra("key", value); ==> 매개변수 역할 // setAttribute 느낌
                    startActivity(intent);
                    
                    //사장님 & 직원페이지
                } else if(vo.getAuthority().equals("ROLE_HOST") || vo.getAuthority().equals("ROLE_EMP")) {
                    // 페이지 이동 : new Intent(현재페이지, 이동페이지) (값들 가지고)
                    Intent intent = new Intent(SignInActivity.this,MyPageMainActivity.class); // 로그인페이지에서 마이페이지로 넘어갈 때 그때 id를 넘기겠다.
                    intent.putExtra("id", vo.getID());   // putExtra("key", value); ==> 매개변수 역할 // setAttribute 느낌
                    startActivity(intent);
                }
            } else {
                Toast.makeText(getApplicationContext(), "로그인 실패", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
