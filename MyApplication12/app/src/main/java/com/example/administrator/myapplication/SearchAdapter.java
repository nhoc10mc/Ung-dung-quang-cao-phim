package com.example.administrator.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class SearchAdapter extends BaseAdapter {
    private Context context;
    private int layout;
    private List<Search> searchList;

    public SearchAdapter(Context context, int layout, List<Search> searchList) {
        this.context = context;
        this.layout = layout;
        this.searchList = searchList;
    }

    @Override
    public int getCount() {
        return searchList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    private class ViewHolder{
        ImageView imgSearch;
        TextView txtTitleSearch;
        TextView txtTime;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if(convertView == null){
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(layout,null);
            holder.imgSearch = (ImageView) convertView.findViewById(R.id.imgSearch);
            holder.txtTitleSearch = (TextView) convertView.findViewById(R.id.txtTitleSearch);
            holder.txtTime = (TextView) convertView.findViewById(R.id.txtTimeSearch);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }
        Search search = searchList.get(position);
        holder.txtTitleSearch.setText(search.getTitle());
        holder.txtTime.setText(search.getRelease_date());
        Picasso.with(context).load(search.getPoster_path()).into(holder.imgSearch);

        return convertView;
    }
}
