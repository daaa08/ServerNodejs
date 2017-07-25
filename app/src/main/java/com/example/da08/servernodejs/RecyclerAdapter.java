package com.example.da08.servernodejs;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Da08 on 2017. 7. 25..
 */

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.Holder> {

    List<Bbs> datas = new ArrayList<>();

    public RecyclerAdapter(Context context, List<Bbs> datas){
        this.datas = datas;
    }


    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item,parent,false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        Bbs bbs = datas.get(position);

        holder.textView5.setText(datas.get(position).title);
        holder.textView6.setText(datas.get(position).author);
        holder.setDate(bbs.date);

    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    class Holder extends RecyclerView.ViewHolder{
        TextView textView,textView5,textView6;

        public Holder(View itemView) {
            super(itemView);

            textView = (TextView)itemView.findViewById(R.id.textView);
            textView5 = (TextView)itemView.findViewById(R.id.textView5);
            textView6 = (TextView)itemView.findViewById(R.id.textView6);
        }

        public void setDate(String date){
            textView.setText(date);
        }
    }
}
