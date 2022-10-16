package com.example.proiectafacerielectronice;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.proiectafacerielectronice.mysql.BookService;
import com.example.proiectafacerielectronice.mysql.CartService;
import com.example.proiectafacerielectronice.mysql.RestApi;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseApp;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BookAdapter extends ArrayAdapter<Book> {
    private Context context;
    private List<Book> books;
    private LayoutInflater inflater;
    private int resource;
    FirebaseStorage storage;
    private BooksFragment fragment;


    public BookAdapter(@NonNull Context context, int resource,
                       @NonNull List<Book> objects,
                       LayoutInflater inflater, BooksFragment fragment) {
        super(context, resource, objects);
        this.context = context;
        this.books = objects;
        this.inflater = inflater;
        this.resource = resource;
        this.fragment=fragment;
        FirebaseApp.initializeApp(context);

         storage = FirebaseStorage.getInstance();
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView,
                        @NonNull ViewGroup parent) {
        View view = inflater.inflate(resource, parent, false);
        Book h = books.get(position);

        final ImageView iv = (ImageView) view.findViewById(R.id.book_iv);
        TextView tvTitle = view.findViewById(R.id.book_title);
        TextView tvAuthor = view.findViewById(R.id.book_author);
        TextView tvDesc = view.findViewById(R.id.book_description);
        TextView tvPrice = view.findViewById(R.id.book_price);
        Button btnAdd = view.findViewById(R.id.book_btn_add);
        if (h != null) {
            StorageReference ref = storage.getReference();
            StorageReference refChild = ref.child("books/" + h.getId()+".jpg");
            refChild.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    Picasso.get().load(uri).into(iv);
                }
            });
            tvTitle.setText(h.getTitle());
            tvAuthor.setText(h.getAuthor());
            tvDesc.setText(h.getDetails());
            tvPrice.setText("Pret: " + h.getPrice() + " lei");
            btnAdd.setOnClickListener(add(h.getId()));
        } else {
        }
        return view;
    }

    private View.OnClickListener add(final Integer bid) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               SharedPreferences sharedPreferences = context.getSharedPreferences("shared pref", Context.MODE_PRIVATE);
               String id = sharedPreferences.getString("uid","defaulwt");
               BooksUser bu= new BooksUser(bid,id);
                CartService cartService = RestApi.getRetrofitInstance().create(CartService.class);
                cartService.insertIntoCart(bu).enqueue(new Callback<Integer>() {
                    @Override
                    public void onResponse(Call<Integer> call, Response<Integer> response) {
                        Toast.makeText(context,"Carte adaugata in cos!",Toast.LENGTH_LONG).show();
                    }
                    @Override
                    public void onFailure(Call<Integer> call, Throwable t) {
                        Toast.makeText(context,"Cartea nu a putut fi adaugata in cos!",Toast.LENGTH_LONG).show();
                    }
                });

            }
        };
    }

}
