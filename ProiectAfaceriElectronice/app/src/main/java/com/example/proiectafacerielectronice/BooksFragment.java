package com.example.proiectafacerielectronice;

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

import com.example.proiectafacerielectronice.mysql.BookService;
import com.example.proiectafacerielectronice.mysql.RestApi;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BooksFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BooksFragment extends Fragment {

    private ListView listView;
    private List<Book> books = new ArrayList<>();
    private BookService bookService;
    private BookAdapter adapter;

    public BooksFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static BooksFragment newInstance() {
        BooksFragment fragment = new BooksFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_books, container, false);
    }

    private void init() {
        adapter = new BookAdapter(getContext(),
                R.layout.book_item, books, getLayoutInflater(), this);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        listView = getView().findViewById(R.id.fragment_my_books_listview);
        listView.setAdapter(adapter);
        bookService = RestApi.getRetrofitInstance().create(BookService.class);
        bookService.getAllBooks().enqueue(new Callback<List<Book>>() {
            @Override
            public void onResponse(Call<List<Book>> call, Response<List<Book>> response) {
                books.removeAll(books);
                if (response.body() != null && response.body().size() > 0) {
                    books.addAll(response.body());
                    notifyAdapter();
                }
            }

            @Override
            public void onFailure(Call<List<Book>> call, Throwable t) {

            }
        });

    }

    private void notifyAdapter() {
        ArrayAdapter adapter = (ArrayAdapter) listView.getAdapter();
        adapter.notifyDataSetChanged();
    }
}