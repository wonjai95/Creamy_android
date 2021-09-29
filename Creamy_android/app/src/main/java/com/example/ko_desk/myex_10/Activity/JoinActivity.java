package com.example.ko_desk.myex_10.Activity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.example.ko_desk.myex_10.HttpClient;
import com.example.ko_desk.myex_10.R;
import com.example.ko_desk.myex_10.VO.Data;
import com.example.ko_desk.myex_10.Web;
import com.google.gson.Gson;

import android.text.TextWatcher;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


public class JoinActivity extends AppCompatActivity {

    // 주소 요청코드 상수 requestCode
    private static final int SEARCH_ADDRESS_ACTIVITY = 10000;

    EditText edtId, edtPw,edtName, edtEmail;

    EditText edtIdPhone;
    EditText editBirth;
    EditText editzipcode, editAddress;
    TextView editsido, editgugun;
    Button btn_selectDate, btn_Join,btn_selectAddress;

    private DatePickerDialog.OnDateSetListener callbackMethod;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);

        edtId = (EditText) findViewById(R.id.edt_id);
        edtPw = (EditText) findViewById(R.id.edt_pwd);
        edtName = (EditText) findViewById(R.id.edt_name);
        edtEmail = (EditText) findViewById(R.id.edt_email);

        edtIdPhone = (EditText) findViewById(R.id.edt_ph);
        edtIdPhone.addTextChangedListener(new PhoneNumberFormattingTextWatcher());

        editzipcode = (EditText) findViewById(R.id.edt_zipcode);
        editsido = (TextView) findViewById(R.id.tv_sido);
        editgugun = (TextView) findViewById(R.id.tv_gugun);
        editAddress = (EditText) findViewById(R.id.edt_address);


        editBirth = (EditText) findViewById(R.id.edt_birth);
        btn_selectDate = (Button) findViewById(R.id.btn_selectDate);

        btn_Join = (Button) findViewById(R.id.btn_join);
        btn_selectAddress = (Button) findViewById(R.id.btn_selectAddress);

        // DatePickerDialog
        final DatePickerDialog dialog = new DatePickerDialog(this, listener, 2021, 9, 22);

        btn_selectDate.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){

                dialog.show();
            }
        });

        btn_Join.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                JoinActivity.InnerTask task = new JoinActivity.InnerTask();
                Map<String, String> map = new HashMap<>();
                map.put("id", edtId.getText().toString());
                map.put("pwd", edtPw.getText().toString());
                map.put("name",edtName.getText().toString());
                map.put("email",edtEmail.getText().toString());
                map.put("phone",edtIdPhone.getText().toString());
                map.put("birth",editBirth.getText().toString());
                task.execute(map);   // doInBackground() 실행 // id, pwd 값 들고 execute 실행하라

            }
        });

        btn_selectAddress.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(JoinActivity.this,DaumActivity.class);
                startActivityForResult(intent,SEARCH_ADDRESS_ACTIVITY);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.i("test","onActivityResult");

        switch (requestCode){
            case SEARCH_ADDRESS_ACTIVITY:
                if(resultCode == RESULT_OK){
                    String Address =data.getExtras().getString("address");
                    if(data != null){
                        Log.i("address :" ,Address);
                        editAddress.setText(Address);
                    }
                }
                break;
        }
    }

    private DatePickerDialog.OnDateSetListener listener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            int month = monthOfYear +1;
            String date = year + "-"+month + "-" + dayOfMonth;
            editBirth.setText(date);
        }
    };

    public class InnerTask extends AsyncTask<Map,Integer,String>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(Map... maps) {

            //Http 요청 준비 -> 스프링 url 호출 -> window.location같은느낌
            HttpClient.Builder http = new HttpClient.Builder("POST",Web.servletURL+"Join");
            http.addAllParameters(maps[0]);

            //스프링으로 보내버리기
            HttpClient post = http.create();
            post.request();

            //스프링 갔다왔음...컨트롤러에서 return map 한거 가져옴
            String body = post.getBody();
            Log.d("body -> ",body);
            return body;

        }

        @Override
        protected void onPostExecute(String s) {
            Log.d("JSON_RESULT : ",s);

            if(s.length() > 0){
                Gson gson = new Gson();
                Data data = gson.fromJson(s,Data.class);

                try{
                    if(data.getData3().equals("1")){
                        Toast.makeText(getApplicationContext(),"회원가입 성공",Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Toast.makeText(getApplicationContext(),"회원가입 실패, 다시시도하세요",Toast.LENGTH_SHORT).show();
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }

            //insert(회원가입) 성공하든 안하든 메인화면으로 이동
            Intent intent = new Intent(JoinActivity.this,SignInActivity.class);
            startActivity(intent);

        }
    }


}
