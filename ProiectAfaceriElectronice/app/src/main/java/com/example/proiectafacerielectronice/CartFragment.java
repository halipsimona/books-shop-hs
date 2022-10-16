package com.example.proiectafacerielectronice;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.proiectafacerielectronice.mysql.BookService;
import com.example.proiectafacerielectronice.mysql.CartService;
import com.example.proiectafacerielectronice.mysql.RestApi;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CartFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CartFragment extends Fragment {
    private ListView listView;
    private List<BooksUser> books = new ArrayList<>();
    private List<Book> listb = new ArrayList<>();
    private CartService cartService;
    private BookService bookService;
    private CartAdapter adapter;

    public CartFragment() {
        // Required empty public constructor
    }

    public static CartFragment newInstance() {
        CartFragment fragment = new CartFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adapter = new CartAdapter(getContext(),
                R.layout.cart_layout, books, getLayoutInflater(), this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_cart, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        listView = getView().findViewById(R.id.fragment_my_cart_listview);
        listView.setAdapter(adapter);
        Button fin = getView().findViewById(R.id.finaliz);
        bookService = RestApi.getRetrofitInstance().create(BookService.class);
        cartService = RestApi.getRetrofitInstance().create(CartService.class);
        SharedPreferences sp = getContext().getSharedPreferences("shared pref", Context.MODE_PRIVATE);
        String id = sp.getString("uid", "id");
        fin.setOnClickListener(deleteAll(id,view));

        cartService.getCart(id).enqueue(new Callback<List<BooksUser>>() {
            @Override
            public void onResponse(Call<List<BooksUser>> call, Response<List<BooksUser>> response) {
                if (response.body() != null && response.body().size() > 0) {
                    books.addAll(response.body());
                    renderBooks();

                    for (int i = 0; i < response.body().size(); i++) {

                        bookService.getBookById(books.get(i).getBid()).enqueue(new Callback<Book>() {
                            @Override
                            public void onResponse(Call<Book> call, Response<Book> response) {
                                listb.add(response.body());

                            }

                            @Override
                            public void onFailure(Call<Book> call, Throwable t) {

                            }
                        });

                    }
                }
            }

            @Override
            public void onFailure(Call<List<BooksUser>> call, Throwable t) {

            }
        });
    }

    private void renderBooks() {
        notifyAdapter();

    }
    private View.OnClickListener deleteAll(final String id, final View view) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CartService cartService = RestApi.getRetrofitInstance().create(CartService.class);
                cartService.deletAll(id).enqueue(new Callback<Response<Void>>() {
                    @Override
                    public void onResponse(Call<Response<Void>> call, Response<Response<Void>> response) {
                        Toast.makeText(getContext(), "Comanda a fost finalizata cu succes!", Toast.LENGTH_LONG).show();
                        Button btn = view.findViewById(R.id.finaliz);
                        btn.setText("Comanda finalizata cu succes - cos gol!");
                        books.removeAll(books);
                        notifyAdapter();
                    }

                    @Override
                    public void onFailure(Call<Response<Void>> call, Throwable t) {

                    }
                });
            }

        };
    }
    private void notifyAdapter() {
        ArrayAdapter adapter = (ArrayAdapter) listView.getAdapter();
        adapter.notifyDataSetChanged();
    }
}