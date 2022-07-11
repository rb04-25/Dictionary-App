package com.example.dictionaryapp;

import static com.example.dictionaryapp.R.color.black;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dictionaryapp.Adapters.MeaningAdapter;
import com.example.dictionaryapp.Adapters.PhoneticAdapter;
import com.example.dictionaryapp.Models.APIResponse;
import com.jpardogo.android.googleprogressbar.library.GoogleProgressBar;

public class MainActivity extends AppCompatActivity {
    SearchView search_view ;
    TextView textView_word;
    RecyclerView recycler_phonetics , recycler_meanings;
    ProgressDialog pdialog ;
    Dialog dialog ;
    PhoneticAdapter padapter ;
    MeaningAdapter madapter ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        search_view = findViewById(R.id.search_view);
        search_view.clearFocus();
        textView_word = findViewById(R.id.textView_word);
        recycler_phonetics = findViewById(R.id.recycler_phonetics);
        recycler_meanings = findViewById(R.id.recycler_meanings);
        pdialog = new ProgressDialog(this);


        GoogleProgressBar google_progress = findViewById(R.id.google_progress);
        dialog = new Dialog(MainActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.pdialog);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
        RequestApi rapi=new RequestApi(MainActivity.this);
        rapi.getWordMeaning(listener,"hello");
        search_view.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                dialog = new Dialog(MainActivity.this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.pdialog);
                dialog.setCanceledOnTouchOutside(false);
                dialog.show();
                RequestApi rapi=new RequestApi(MainActivity.this);
                rapi.getWordMeaning(listener,query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }

    private final YieldDataListener listener = new YieldDataListener() {
        @Override
        public void yielddata(APIResponse apiResponse, String message) {
            dialog.dismiss();
            if(apiResponse==null){
                Toast.makeText(MainActivity.this,"No Data Found!!",Toast.LENGTH_SHORT).show();
                return ;
            }
            showdata(apiResponse);
        }
        @Override
        public void onerror(String message) {
            dialog.dismiss();
            if(message!="")Toast.makeText(MainActivity.this,message,Toast.LENGTH_SHORT).show();
        }
    };
    private void showdata(APIResponse apiResponse){
        textView_word.setText(apiResponse.getWord());
        recycler_phonetics.setHasFixedSize(true);
        recycler_phonetics.setLayoutManager(new GridLayoutManager(this,1));
        padapter = new PhoneticAdapter(this,apiResponse.getPhonetics());
        recycler_phonetics.setAdapter(padapter);
        recycler_meanings.setHasFixedSize(true);
        recycler_meanings.setLayoutManager(new GridLayoutManager(this,1));
        madapter = new MeaningAdapter(this,apiResponse.getMeanings());
        recycler_meanings.setAdapter(madapter);
    }
}