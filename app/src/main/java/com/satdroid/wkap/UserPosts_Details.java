package com.satdroid.wkap;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UserPosts_Details extends AppCompatActivity {
    RecyclerView recyclerView;
    List<PostCommentModal> comtList;
    PostCommentAdapter adapter;
    UserPostModal userPostModal;
    TextView useridtv,postidtv,titletv,bodytv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_user_posts_details);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        Intent intent=getIntent();
        userPostModal=(UserPostModal) intent.getSerializableExtra("User_Post_data");

        useridtv=findViewById(R.id.post_userid);
        postidtv=findViewById(R.id.postid);
        titletv=findViewById(R.id.post_titleid);
        bodytv=findViewById(R.id.post_bodyid);

        useridtv.setText("Userid:"+R.id.post_userid);
        postidtv.setText("Postid:"+userPostModal.getId());
        titletv.setText("Post title:"+userPostModal.getTitle());
        bodytv.setText("Post body"+userPostModal.getBody());

        recyclerView=findViewById(R.id.rcv_postComments);
        comtList=new ArrayList<>();
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl("https://jsonplaceholder.typicode.com/posts/"+userPostModal.getId()+"/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        JsonCommentHolder jsonCommentHolder=retrofit.create(JsonCommentHolder.class);
        Call<List<PostCommentModal>> call=jsonCommentHolder.getPostsComments();
        call.enqueue(new Callback<List<PostCommentModal>>() {
            @Override
            public void onResponse(Call<List<PostCommentModal>> call, Response<List<PostCommentModal>> response) {
                if(!response.isSuccessful()){
                    return;
                }
               comtList = response.body();
                adapter = new PostCommentAdapter(comtList, UserPosts_Details.this);
                recyclerView.setAdapter(adapter);            }

            @Override
            public void onFailure(Call<List<PostCommentModal>> call, Throwable throwable) {
                Toast.makeText(UserPosts_Details.this,"exception occured check internet",Toast.LENGTH_SHORT).show();
            }
        });
    }
}