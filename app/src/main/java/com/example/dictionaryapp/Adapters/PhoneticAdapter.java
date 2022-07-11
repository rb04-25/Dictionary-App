package com.example.dictionaryapp.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dictionaryapp.Models.Phonetics;
import com.example.dictionaryapp.R;
import com.example.dictionaryapp.ViewHolders.PhoneticViewHolder;

import java.io.IOException;
import java.util.List;

public class PhoneticAdapter extends RecyclerView.Adapter<PhoneticViewHolder> {
    private Context context ;
    private List<Phonetics> listphonetic ;

    public PhoneticAdapter(Context context, List<Phonetics> listphonetic) {
        this.context = context;
        this.listphonetic = listphonetic;
    }

    @NonNull
    @Override
    public PhoneticViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new PhoneticViewHolder(LayoutInflater.from(context).inflate(R.layout.phonetics_items,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull PhoneticViewHolder holder,  int position) {
        holder.textView_phonetic.setText(listphonetic.get(position).getText());
        holder.imageButton_audio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MediaPlayer player = new MediaPlayer();
                try{
                    player.setAudioStreamType(AudioManager.STREAM_MUSIC);
                    String url = listphonetic.get(holder.getAdapterPosition()).getAudio();
                    Log.d("url",url);
                    player.setDataSource(url);
                    player.prepare();
                    player.start();
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(context , "Audio Not Available" , Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return listphonetic.size();
    }
}
