package com.satdroid.wkap;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class UserDash extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    UsersModal usersModal;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_user_dash);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        Intent intent=getIntent();
        usersModal=(UsersModal)intent.getSerializableExtra("USer_data");
        bottomNavigationView=findViewById(R.id.bottom_navigation);
        replaceFrag(new UserInfo_fragment(),0);

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                int menu_id=item.getItemId();
                if(menu_id==R.id.post_menu)
                {
                    replaceFrag(new UserPost_fragment(),1);
                    return true;
                }
                else if(menu_id==R.id.user_menu)
                {
                    replaceFrag(new UserInfo_fragment(),1);
                    return true;
                }
                return true;
            }
        });
    }
    private void replaceFrag(Fragment fragment,int i)
    {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();

        Bundle mBundle = new Bundle();
        mBundle.putSerializable("UserInfo",usersModal);
        fragment.setArguments(mBundle);
if(i!=0)
        transaction.replace(R.id.frame_layout, fragment);
else transaction.add(R.id.frame_layout,fragment);
        transaction.commit();

    }
}