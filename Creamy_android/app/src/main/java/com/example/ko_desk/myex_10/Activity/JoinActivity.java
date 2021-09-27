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

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


public class JoinActivity extends AppCompatActivity {

    EditText edtId, edtPw,edtName, edtEmail;

    EditText edtIdPhone;
    EditText editBirth;
    Button btn_selectDate, btn_Join;

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

        editBirth = (EditText) findViewById(R.id.edt_birth);
        btn_selectDate = (Button) findViewById(R.id.btn_selectDate);

        btn_Join = (Button) findViewById(R.id.btn_join);

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
        protected String doInBackground(Map... maps) {
            return null;
        }
    }

}

