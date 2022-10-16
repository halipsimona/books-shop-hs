package com.example.proiectafacerielectronice;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.res.ResourcesCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.navigation.ui.AppBarConfiguration;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.proiectafacerielectronice.mysql.BookService;
import com.example.proiectafacerielectronice.mysql.RestApi;
import com.example.proiectafacerielectronice.mysql.UserService;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    public static String MAIN_ACTIVITY_USER_KEY="main activity user key";
    public SharedPreferences sharedPreferences;
    private BottomNavigationView mBottomNavigationView;
    private Fragment currentFragment;
    private Intent intent;
    private User currentUser=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        intent=getIntent();
        if(currentUser==null){
            if(intent.hasExtra(MAIN_ACTIVITY_USER_KEY)){
                initComp();
                currentUser=intent.getParcelableExtra(MAIN_ACTIVITY_USER_KEY);;
                sharedPreferences.edit().putString("uid",currentUser.getId()).apply();
            }
        }
//        BookService  bookService = RestApi.getRetrofitInstance().create(BookService.class);
//        List<BookApi> books = new ArrayList<>();
//        books.add(new BookApi(1,"L","l","l",10d,""));
//        books.add(new BookApi(2,"L","l","l",10d,""));
//        books.add(new BookApi(3,"L","l","l",10d,""));
//        books.add(new BookApi(4,"L","l","l",10d,""));
//        books.add(new BookApi(5,"L","l","l",10d,""));
//        books.add(new BookApi(6,"L","l","l",10d,""));
//        books.add(new BookApi(7,"L","l","l",10d,""));
//        books.add(new BookApi(8,"L","l","l",10d,""));
//        books.add(new BookApi(9,"L","l","l",10d,""));
//        for (int i =0; i<9;i++) {
//            bookService.insertBook(books.get(i)).enqueue(new Callback<Integer>() {
//                @Override
//                public void onResponse(Call<Integer> call, Response<Integer> response) {
//
//                }
//
//                @Override
//                public void onFailure(Call<Integer> call, Throwable t) {
//
//                }
//            });
//
//        }
        mBottomNavigationView=(BottomNavigationView)findViewById(R.id.bottom_navigation);
        mBottomNavigationView.setOnItemSelectedListener(addNavigationMenuItemSelectedEvent());
        openDefaultFragment();

    }

    private NavigationBarView.OnItemSelectedListener addNavigationMenuItemSelectedEvent() {
        return new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if(item.getItemId()==R.id.profile){
                    currentFragment= ProfileFragment.newInstance();
                }else if(item.getItemId()==R.id.cart){
                    currentFragment= CartFragment.newInstance();
                }
                else  if(item.getItemId()==R.id.books){
                    currentFragment= BooksFragment.newInstance();
                }
                openFragment();
                return true;
            }
        };
    }

    private void initComp() {
       sharedPreferences = getSharedPreferences("shared pref", Context.MODE_PRIVATE);
    }


    /******************* FRAGMENTE ******************/
    private void openDefaultFragment() {


            currentFragment= BooksFragment.newInstance();
            openFragment();

    }

    private void openFragment() {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.main_frame_container, currentFragment)
                .commit();
    }
}