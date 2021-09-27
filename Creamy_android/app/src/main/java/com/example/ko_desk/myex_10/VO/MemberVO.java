package com.example.ko_desk.myex_10.VO;

import java.sql.Timestamp;

/**
 * Created by psn on 2018-01-18.
 */

public class MemberVO {
    // member_tbl
    private String user_code;
    private String user_password;
    private String user_name;
    private String user_gender;
    private Timestamp user_birth;
    private int user_age;
    private String user_email;
    private String zipcode;
    private String user_address;
    private String user_ph;
    private Timestamp join_date;
    private Timestamp expire_date;
    private String bank_code;
    private String user_id;


    //나중에 조인할거..있으면추가




    public String getUser_code() {
        return user_code;
    }
    public void setUser_code(String user_code) {
        this.user_code = user_code;
    }
    public String getUser_password() {
        return user_password;
    }
    public void setUser_password(String user_password) {
        this.user_password = user_password;
    }
    public String getUser_name() {
        return user_name;
    }
    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }
    public String getUser_gender() {
        return user_gender;
    }
    public void setUser_gender(String user_gender) {
        this.user_gender = user_gender;
    }
    public Timestamp getUser_birth() {
        return user_birth;
    }
    public void setUser_birth(Timestamp user_birth) {
        this.user_birth = user_birth;
    }
    public int getUser_age() {
        return user_age;
    }
    public void setUser_age(int user_age) {
        this.user_age = user_age;
    }
    public String getUser_email() {
        return user_email;
    }
    public void setUser_email(String user_email) {
        this.user_email = user_email;
    }
    public String getZipcode() {
        return zipcode;
    }
    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }
    public String getUser_address() {
        return user_address;
    }
    public void setUser_address(String user_address) {
        this.user_address = user_address;
    }
    public Timestamp getJoin_date() {
        return join_date;
    }
    public void setJoin_date(Timestamp join_date) {
        this.join_date = join_date;
    }
    public Timestamp getExpire_date() {
        return expire_date;
    }
    public void setExpire_date(Timestamp expire_date) {
        this.expire_date = expire_date;
    }
    public String getBank_code() {
        return bank_code;
    }
    public void setBank_code(String bank_code) {
        this.bank_code = bank_code;
    }
    public String getUser_id() {
        return user_id;
    }
    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }
    public String getUser_ph() {
        return user_ph;
    }
    public void setUser_ph(String user_ph) {
        this.user_ph = user_ph;
    }

}
