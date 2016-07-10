package com.tutorials.hp.recyclerinsetselectshow;

import android.content.ContentValues;
import android.content.Context;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * Created by Hp on 3/18/2016.
 */
public class MyAdapter extends RecyclerView.Adapter<MyHolder> {
    Context c;
    ArrayList<Player> players;

    public MyAdapter(Context c, ArrayList<Player> players) {
        this.c = c;
        this.players = players;
    }

    //INITIALIZE VIEWHODER
    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //VIEW OBJ
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.model,null);

        //HOLDER
        MyHolder holder=new MyHolder(v);

        return holder;
    }

    //BIND VIEW TO DATA
    @Override
    public void onBindViewHolder(MyHolder holder, int position) {
        holder.img.setImageResource(R.drawable.marker);

        holder.nametxt.setText(players.get(position).getName());
        holder.posTxt.setText(players.get(position).getPosition());

        //CLICKED
        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onItemClick(View v, int pos) {
                Snackbar.make(v,players.get(pos).getName(),Snackbar.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return players.size();
    }
}
