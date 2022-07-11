package com.example.dictionaryapp.Adapters;

import static android.os.Build.VERSION_CODES.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dictionaryapp.Models.Definitions;
import com.example.dictionaryapp.R;
import com.example.dictionaryapp.ViewHolders.DefinitionViewHolder;
import com.example.dictionaryapp.ViewHolders.MeaningViewHolder;

import java.util.List;

public class DefinitionAdapter extends RecyclerView.Adapter<DefinitionViewHolder> {
    private Context context ;
    private List<Definitions> listdefinition ;

    public DefinitionAdapter(Context context, List<Definitions> listdefinition) {
        this.context = context;
        this.listdefinition = listdefinition;
    }

    @NonNull
    @Override
    public DefinitionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new DefinitionViewHolder(LayoutInflater.from(context).inflate(com.example.dictionaryapp.R.layout.definitions_items,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull DefinitionViewHolder holder, int position) {
        holder.textView_definition.setText("Definition: " + listdefinition.get(position).getDefinition());
        holder.textView_example.setText("Examples: " + listdefinition.get(position).getExample());
        StringBuilder synonyms = new StringBuilder();
        StringBuilder antonyms = new StringBuilder();
        synonyms.append(listdefinition.get(position).getSynonyms()) ;
        antonyms.append(listdefinition.get(position).getAntonyms()) ;
        holder.textView_synonyms.setText(synonyms);
        holder.textView_antonyms.setText(antonyms);
        holder.textView_synonyms.setSelected(true);
        holder.textView_antonyms.setSelected(true);
    }

    @Override
    public int getItemCount() {
        return listdefinition.size();
    }
}
