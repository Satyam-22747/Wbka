package com.satdroid.wkap;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class PostListAdapter extends RecyclerView.Adapter<PostListAdapter.ViewHolder> {

    private List<UserPostModal> userPostList;
    Context context;
    OnClickListener onClickListener;

    public PostListAdapter(List<UserPostModal> userPostList, Context context) {
        this.userPostList = userPostList;
        this.context = context;
    }

    public void filterList(List<UserPostModal> filterlist) {
        userPostList = filterlist;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public PostListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.users_post_rcv_design,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PostListAdapter.ViewHolder holder, int position) {
        UserPostModal userPostModal=userPostList.get(position);
        holder.userid.setText("userId:"+userPostModal.getUserId());
        holder.id.setText("Post id:"+userPostModal.getId());
        holder.title.setText("title:"+userPostModal.getTitle());
        holder.body.setText("body:"+userPostModal.getBody());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(onClickListener!=null)
                {
                    onClickListener.onClick(position,userPostModal);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return userPostList.size();
    }

    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }
    public interface OnClickListener {
        void onClick(int position, UserPostModal userPostModal);
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView userid, id, title, body;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            userid=itemView.findViewById(R.id.uid);
            id=itemView.findViewById(R.id.upid);
            title=itemView.findViewById(R.id.uptitle);
            body=itemView.findViewById(R.id.upbody);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(onClickListener!=null)
                    {
                        onClickListener.onClick(getAdapterPosition(),userPostList.get(getAdapterPosition()));
                    }
                }
            });
        }
    }
}
