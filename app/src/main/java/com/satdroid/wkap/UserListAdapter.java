package com.satdroid.wkap;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class UserListAdapter extends RecyclerView.Adapter<UserListAdapter.ViewHolder> {

    private List<UsersModal> usersArrayList;
    Context context;
     OnClickListener onClickListener;

    public UserListAdapter(List<UsersModal> usersArrayList, Context context)
    {
        this.usersArrayList=usersArrayList;
        this.context=context;
    }

    public void filterList(List<UsersModal> filterlist) {
        usersArrayList = filterlist;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public UserListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.users_rcv_design,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserListAdapter.ViewHolder holder, int position) {
        UsersModal usersModal=usersArrayList.get(position);
        holder.nameTv.setText("name:"+usersModal.getName());
        holder.phonetv.setText("Phone:"+usersModal.getPhone());
        holder.emailTv.setText("Email:"+usersModal.getEmail());
        holder.webTv.setText("Website:"+usersModal.getWebsite());
        holder.comTv.setText("Company:"+
                "\nname:"+usersModal.getCompany().getName()+
                "\ncatchPhrase:"+usersModal.getCompany().getCatchPhrase()+
                "\nbs:"+usersModal.getCompany().getBs());
        holder.addTv.setText("Address:"+
                "\nstreet:"+usersModal.getAddress().getStreet()+
                "\nsuite:"+usersModal.getAddress().getSuite()+
                "\ncity:"+usersModal.getAddress().getCity()+
                "\nzipcode:"+usersModal.getAddress().getZipcode()+
                "\nGeo:"+
                "\nlat:"+usersModal.getAddress().getGeo().getLat()+
                "\nlng:"+usersModal.getAddress().getGeo().getLng());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(onClickListener!=null)
                {
                    onClickListener.onClick(position,usersModal);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return usersArrayList.size();
    }

    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }
    public interface OnClickListener {
        void onClick(int position, UsersModal model);
    }
public class ViewHolder extends RecyclerView.ViewHolder{
        TextView nameTv, emailTv, phonetv, addTv, comTv, webTv;
    public ViewHolder(@NonNull View itemView) {
        super(itemView);
        nameTv=itemView.findViewById(R.id.uname);
        emailTv=itemView.findViewById(R.id.uemail);
        phonetv=itemView.findViewById(R.id.uphone);
        addTv=itemView.findViewById(R.id.uaddress);
        comTv=itemView.findViewById(R.id.ucompany);
        webTv=itemView.findViewById(R.id.uwebsite);

        itemView.setOnClickListener(view -> {
            if(onClickListener!=null){
                onClickListener.onClick(getAdapterPosition(),usersArrayList.get(getAdapterPosition()));
            }
        });
    }
}
}
