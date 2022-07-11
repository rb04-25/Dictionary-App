package com.example.dictionaryapp;

import com.example.dictionaryapp.Models.APIResponse;

public interface YieldDataListener {

    void yielddata(APIResponse apiResponse, String message);

    void onerror(String message) ;
}
