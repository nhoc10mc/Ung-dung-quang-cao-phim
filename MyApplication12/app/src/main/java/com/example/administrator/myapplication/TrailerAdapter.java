package com.example.administrator.myapplication;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class TrailerAdapter extends RecyclerView.Adapter<TrailerAdapter.ViewHolder> {
    Context context;
    int layout;
    ArrayList<Trailer> arrayList;
    private static ItemClickListener itemClickListener;


    public TrailerAdapter(Context context, int layout, ArrayList<Trailer> arrayList) {
        this.context = context;
        this.layout = layout;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(layout,parent,false);
        return new ViewHolder(v);


    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Trailer trailer = arrayList.get(position);
        Picasso.with(context).load(trailer.getImageTrailer()).into(holder.ivTrailer);

        /*holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onClick(View view, int position, boolean isLongClick) {
                Intent intent = new Intent(context,PlayVideoTrailerActivity.class);
                intent.putExtra("keyVideoTrailer", arrayList.get(position).getKeyTrailer());

            }
        });*/
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }



    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener,View.OnLongClickListener{

        public ImageView ivTrailer;


        public ViewHolder(View v) {
            super(v);
            ivTrailer =  v.findViewById(R.id.imageViewTrailer);

            v.setOnClickListener(this);
            v.setOnLongClickListener(this);
        }


        @Override
        public void onClick(View v) {
            itemClickListener.onClick(v,getAdapterPosition(),false);
        }

        @Override
        public boolean onLongClick(View v) {
            itemClickListener.onClick(v,getAdapterPosition(),true);
            return true;
        }
    }
    public void setItemClickListener(ItemClickListener itemClickListener)
    {
        TrailerAdapter.itemClickListener = itemClickListener;
    }

    public interface ItemClickListener {
        void onClick(View view, int position,boolean isLongClick);
    }
}
