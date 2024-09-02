package com.satdroid.wkap;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class PostCommentAdapter extends RecyclerView.Adapter<PostCommentAdapter.ViewHolder> {

    private List<PostCommentModal> commentModalList;
    Context context;

    public PostCommentAdapter(List<PostCommentModal> commentModalList, Context context) {
        this.commentModalList = commentModalList;
        this.context = context;
    }

    @NonNull
    @Override
    public PostCommentAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.comments_rcv_design,parent,false);
        return new PostCommentAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PostCommentAdapter.ViewHolder holder, int position) {
        PostCommentModal postCommentModal=commentModalList.get(position);
        holder.pidtv.setText("post id:"+postCommentModal.getPostId());
        holder.idtv.setText("comment id:"+postCommentModal.getId());
        holder.nametv.setText("Name:"+postCommentModal.getName());
        holder.emailtv.setText("Email:"+postCommentModal.getEmail());
        holder.bodytv.setText("Body:"+postCommentModal.getBody());
    }

    @Override
    public int getItemCount() {
        return commentModalList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView pidtv,idtv,nametv,emailtv,bodytv;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            pidtv=itemView.findViewById(R.id.Pid);
            idtv=itemView.findViewById(R.id.Pcid);
            nametv=itemView.findViewById(R.id.pcname);
            emailtv=itemView.findViewById(R.id.pcemail);
            bodytv=itemView.findViewById(R.id.pcBody);
        }
    }
}
