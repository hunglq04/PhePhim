package com.example.service;

public class APIUtils {
    public static final String Base_Url = "http://pokebizute15.000webhostapp.com/phephim/";
    public static DataClient getData(){
        return RetrofitClient.getClient(Base_Url).create(DataClient.class);
    }
}
