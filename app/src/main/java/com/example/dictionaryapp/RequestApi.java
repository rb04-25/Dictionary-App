package com.example.dictionaryapp;

import android.app.ProgressDialog;
import android.content.Context;
import android.widget.Toast;

import com.example.dictionaryapp.Models.APIResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;

public class RequestApi {

    Context context ;
    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://api.dictionaryapi.dev/api/v2/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    public RequestApi(Context context) {
        this.context = context;
    }

    public void getWordMeaning(YieldDataListener listener , String word){
        getdictionary getd = retrofit.create(getdictionary.class);
        Call<List<APIResponse>> call = getd.meanings(word);

        try{
            call.enqueue(new Callback<List<APIResponse>>() {
                @Override
                public void onResponse(Call<List<APIResponse>> call, Response<List<APIResponse>> response) {
                    if(!response.isSuccessful()){
                        Toast.makeText(context,"Word Not Found!!" , Toast.LENGTH_SHORT).show();
                        listener.onerror("");
                    }
                    else listener.yielddata(response.body().get(0) , response.message());
                }

                @Override
                public void onFailure(Call<List<APIResponse>> call, Throwable t) {
                    listener.onerror("Check Your Internet Connection");
                }
            });
        }catch(Exception e){
            e.printStackTrace();
            Toast.makeText(context,"An Error Occured" , Toast.LENGTH_SHORT).show();
        }
    }


    public interface getdictionary{
        @GET("entries/en/{word}")
        Call<List<APIResponse>> meanings(
                @Path("word") String word
        );

    }
}
