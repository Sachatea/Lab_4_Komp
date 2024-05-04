package com.example.lab_4;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {


    private Context context;
    private ArrayList _id, Performer, Name, Date;

    CustomAdapter(Context context, ArrayList _id, ArrayList Performer, ArrayList Name, ArrayList Date){
        this.context = context;
        this._id = _id;
        this.Performer = Performer;
        this.Name = Name;
        this.Date = Date;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.my_row, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder._id_txt.setText(String.valueOf(_id.get(position)));
        holder.Performer_txt.setText(String.valueOf(Performer.get(position)));
        holder.Name_txt.setText(String.valueOf(Name.get(position)));
        holder.Date_txt.setText(String.valueOf(Date.get(position)));
    }

    @Override
    public int getItemCount() {
        return _id.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView _id_txt, Performer_txt, Name_txt, Date_txt;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            _id_txt = itemView.findViewById(R.id._id);
            Performer_txt = itemView.findViewById(R.id.Performer);
            Name_txt = itemView.findViewById(R.id.Name);
            Date_txt = itemView.findViewById(R.id.Date);
        }
    }

}
