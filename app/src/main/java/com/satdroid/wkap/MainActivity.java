package com.satdroid.wkap;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Parcelable;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;

import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Converter;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    Toolbar toolbar;
    List<UsersModal> list;
    UserListAdapter adapter;
    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        recyclerView=findViewById(R.id.rcv_users);
        toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("Search User");
        list=new ArrayList<>();
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new UserListAdapter(list, MainActivity.this);
        recyclerView.setAdapter(adapter);

        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl("https://jsonplaceholder.typicode.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        JsonDataHolder jsonDataHolder=retrofit.create(JsonDataHolder.class);
        Call<List<UsersModal>> call=jsonDataHolder.getUsers();
        call.enqueue(new Callback<List<UsersModal>>() {
            @Override
            public void onResponse(Call<List<UsersModal>> call, Response<List<UsersModal>> response) {

                if(!response.isSuccessful()){
                    Toast.makeText(MainActivity.this,"Response failed",Toast.LENGTH_SHORT).show();
                    return;
                }


                runOnUiThread(new Runnable(){
                    @Override
                    public void run(){
                        list = response.body();
                        adapter.notifyDataSetChanged();
                    }
                });
            }
            @Override
            public void onFailure(Call<List<UsersModal>> call, Throwable throwable) {
                Toast.makeText(MainActivity.this,"exception occured check internet",Toast.LENGTH_SHORT).show();
            }
        });
//        try{
//            Response<List<UsersModal>> response=call.execute();
//            if(!response.isSuccessful())
//            {
//                return;
//            }
//            list=response.body();
//            adapter.notifyDataSetChanged();
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//        catch(RuntimeException e){
//
//        }
        adapter.setOnClickListener(new UserListAdapter.OnClickListener() {
            @Override
            public void onClick(int position, UsersModal model) {
                adapter.notifyDataSetChanged();
                Intent inext=new Intent(MainActivity.this, UserDash.class);
                inext.putExtra("USer_data",model);
                startActivity(inext);

            }
        });
    }

    public void updateRecycler()
    {
        adapter.notifyDataSetChanged();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater  menuInflater=getMenuInflater();
        menuInflater.inflate(R.menu.search_users_menu,menu);
        MenuItem searchItem=menu.findItem(R.id.actionSearch);

        SearchView searchView=(SearchView) searchItem.getActionView();
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
        return true;
    }
    private void filter(String text)
    {
        List<UsersModal> userSearchList=new ArrayList<>();
        for(UsersModal usersModal:list)
        {
            if(usersModal.getName().toLowerCase().contains(text))
                userSearchList.add(usersModal);
            else if(usersModal.getId().toLowerCase().contains(text))
                userSearchList.add(usersModal);
        }
        if(userSearchList.isEmpty())
            Toast.makeText(MainActivity.this,"No data found",Toast.LENGTH_SHORT).show();
        else
            adapter.filterList(userSearchList);
    }
}