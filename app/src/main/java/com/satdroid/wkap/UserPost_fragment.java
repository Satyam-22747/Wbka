package com.satdroid.wkap;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.MenuItemCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link UserPost_fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UserPost_fragment extends Fragment {

    RecyclerView recyclerView;
    Toolbar toolbar;
    List<UserPostModal> list;
    PostListAdapter adapter;
    Context context;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public UserPost_fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment UserPost_fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static UserPost_fragment newInstance(String param1, String param2) {
        UserPost_fragment fragment = new UserPost_fragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the   layout for this fragment
        View view=inflater.inflate(R.layout.fragment_user_post_fragment, container, false);

        Bundle bundle = getArguments();
        UsersModal message = (UsersModal)bundle.getSerializable("UserInfo");
//        Toast.makeText(getContext(), "Message:"+message.getName(), Toast.LENGTH_SHORT).show();

        context=getContext();
        recyclerView=view.findViewById(R.id.rcv_posts);

//        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        list=new ArrayList<>();
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new PostListAdapter(list, context);
        recyclerView.setAdapter(adapter);

        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl("https://jsonplaceholder.typicode.com/users/"+message.getId()+"/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        JsonPostHolder jsonPostHolder=retrofit.create(JsonPostHolder.class);
        Call<List<UserPostModal>> call=jsonPostHolder.getUsersPost();
        call.enqueue(new Callback<List<UserPostModal>>() {
            @Override
            public void onResponse(Call<List<UserPostModal>> call, Response<List<UserPostModal>> response) {

                if(!response.isSuccessful()){
                    Toast.makeText(getContext(),"Response failed",Toast.LENGTH_SHORT).show();
                    return;
                }
//                Toast.makeText(getContext(),"Data fetched",Toast.LENGTH_SHORT).show();
                list = response.body();

                getActivity().runOnUiThread(new Runnable(){
                    @Override
                    public void run(){
//                        Toast.makeText(getContext(),"Ui updated",Toast.LENGTH_SHORT).show();
                        adapter.notifyDataSetChanged();
                    }
                });

//                Toast.makeText(context,"List size inside api call:"+list.size(),Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<List<UserPostModal>> call, Throwable throwable) {

            }
        });

        adapter.setOnClickListener(new PostListAdapter.OnClickListener() {
            @Override
            public void onClick(int position, UserPostModal userPostModal) {
                adapter.notifyDataSetChanged();
//                Toast.makeText(MainActivity.this,"Recycler item clicked",Toast.LENGTH_SHORT).show();
                Intent inext=new Intent(getActivity(), UserPosts_Details.class);
                inext.putExtra("User_Post_data",userPostModal);
                startActivity(inext);
            }
        });
        return view;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        toolbar=view.findViewById(R.id.toolbar_posts);
//        toolbar.inflateMenu(R.menu.search_users_menu);
        //MenuItem searchItem=view.findItem(R.id.actionSearch);
        toolbar.setTitle("Search Posts");
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                SearchView searchView=(SearchView) item.getActionView();
                searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                    @Override
                    public boolean onQueryTextSubmit(String s) {
                        return false;
                    }
                    @Override
                    public boolean onQueryTextChange(String s) {
                        filter(s);
                        return false;
                    }
                });
                return false;
            }
        });
    }

    private void filter(String text)
    {
        List<UserPostModal> userSearchList=new ArrayList<>();
        for(UserPostModal userPostModal:list)
        {
            if(userPostModal.getTitle().toLowerCase().contains(text))
                userSearchList.add(userPostModal);
            else if(userPostModal.getId().toLowerCase().contains(text))
                userSearchList.add(userPostModal);
        }
        if(userSearchList.isEmpty())
            Toast.makeText(getContext(),"No data found",Toast.LENGTH_SHORT).show();
        else
            adapter.filterList(userSearchList);
    }
}