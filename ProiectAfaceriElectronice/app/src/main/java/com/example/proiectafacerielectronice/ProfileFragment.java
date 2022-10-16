package com.example.proiectafacerielectronice;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.proiectafacerielectronice.mysql.CartService;
import com.example.proiectafacerielectronice.mysql.RestApi;
import com.example.proiectafacerielectronice.mysql.UserService;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileFragment extends Fragment {

    private UserService userService;
    private String id;
    private String pass;

    public ProfileFragment() {
        // Required empty public constructor
    }

    public static ProfileFragment newInstance() {
        ProfileFragment fragment = new ProfileFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SharedPreferences sp = getContext().getSharedPreferences("shared pref", Context.MODE_PRIVATE);
        id = sp.getString("uid", "id");
        userService = RestApi.getRetrofitInstance().create(UserService.class);
        userService.getUserById(id).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                TextView tvEmail = view.findViewById(R.id.frag_prof_tv_email);
                EditText etAdd = view.findViewById(R.id.frag_prof_et_address);
                EditText etName = view.findViewById(R.id.frag_prof_et_name);
                Button saveChanges = view.findViewById(R.id.save_changes);
                pass = response.body().getPassword();
                tvEmail.setText(response.body().getEmail());
                etAdd.setText(response.body().getAddress());
                etName.setText(response.body().getName());
                saveChanges.setOnClickListener(save(view));
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {

            }
        });
    }

    private View.OnClickListener save(final View view) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userService.updateUser(id, getUserFromWidgets(view)).enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        Toast.makeText(getContext(),"Schimbarile au fost salvate!",Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {

                    }
                });
            }

        };
    }

    private User getUserFromWidgets(View view){
        TextView tvEmail = view.findViewById(R.id.frag_prof_tv_email);
        EditText etAdd = view.findViewById(R.id.frag_prof_et_address);
        EditText etName = view.findViewById(R.id.frag_prof_et_name);
        String nume = etName.getText().toString();
        String address = etAdd.getText().toString();
        String mail = tvEmail.getText().toString();
        return new User(id,nume,mail,pass,address);

    }
}