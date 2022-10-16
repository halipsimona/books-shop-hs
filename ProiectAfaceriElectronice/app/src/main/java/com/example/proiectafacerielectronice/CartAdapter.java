package com.example.proiectafacerielectronice;

import android.content.Context;
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

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CartAdapter extends ArrayAdapter<BooksUser> {
    private Context context;
    private List<BooksUser> books;
    private LayoutInflater inflater;
    private int resource;
    private CartFragment fragment;

    public CartAdapter(@NonNull Context context, int resource,
                       @NonNull List<BooksUser> objects,
                       LayoutInflater inflater, CartFragment fragment) {
        super(context, resource, objects);
        this.context = context;
        this.books = objects;
        this.inflater = inflater;
        this.resource = resource;
        this.fragment = fragment;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView,
                        @NonNull ViewGroup parent) {
        final View view = inflater.inflate(resource, parent, false);
        final BooksUser h = books.get(position);
        BookService bookService = RestApi.getRetrofitInstance().create(BookService.class);
        if (h != null) {

            bookService.getBookById(h.getBid()).enqueue(new Callback<Book>() {
                @Override
                public void onResponse(Call<Book> call, Response<Book> response) {
                    TextView tvTitle = view.findViewById(R.id.cart_book_title);
                    TextView tvAuthor = view.findViewById(R.id.cart_book_author);
                    TextView tvPrice = view.findViewById(R.id.cart_book_price);
                    Button btn = view.findViewById(R.id.btn_delete);
                    tvTitle.setText(response.body().getTitle());
                    tvAuthor.setText(response.body().getAuthor());
                    tvPrice.setText("Pret: " + response.body().getPrice() + " lei");
                    btn.setOnClickListener(del(h));
                }

                @Override
                public void onFailure(Call<Book> call, Throwable t) {

                }
            });

        } else {
        }
        return view;
    }


    private View.OnClickListener del(final BooksUser buid) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CartService cartService = RestApi.getRetrofitInstance().create(CartService.class);
                cartService.deleteBookFromCart(buid.getBuid()).enqueue(new Callback<Response<Void>>() {
                    @Override
                    public void onResponse(Call<Response<Void>> call, Response<Response<Void>> response) {
                        Toast.makeText(context, "Produs sters!", Toast.LENGTH_LONG).show();
                        books.remove(buid);
                        notifyDataSetChanged();
                    }

                    @Override
                    public void onFailure(Call<Response<Void>> call, Throwable t) {

                    }
                });
            }

        };
    }


}