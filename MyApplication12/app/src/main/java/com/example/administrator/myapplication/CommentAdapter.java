package com.example.administrator.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class CommentAdapter extends BaseAdapter {
    private Context context;
    private int Layout;
    private List<Comment> commentList;

    public CommentAdapter(Context context, int layout, List<Comment> commentList) {
        this.context = context;
        Layout = layout;
        this.commentList = commentList;
    }

    @Override
    public int getCount() {
        return commentList.size();
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
        TextView tvCommentcontent;
        TextView tvUser;
        TextView tvCommnentdate;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if(convertView == null){
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(Layout,null);
            holder.tvCommentcontent = (TextView) convertView.findViewById(R.id.tvNDComment);
            holder.tvUser = (TextView) convertView.findViewById(R.id.tvUserComment);
            holder.tvCommnentdate = (TextView) convertView.findViewById(R.id.tvTimeComment);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }
        Comment comment = commentList.get(position);
        holder.tvCommentcontent.setText(comment.getCommentcontent());
        holder.tvCommnentdate.setText(comment.getCommentdate());


        return convertView;
    }
}
