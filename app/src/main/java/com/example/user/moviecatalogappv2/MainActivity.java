package com.example.user.moviecatalogappv2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;
import android.widget.EditText;

import com.example.user.moviecatalogappv2.MVP_Core.MainPresenter;
import com.example.user.moviecatalogappv2.MVP_Core.MainView;
import com.example.user.moviecatalogappv2.MVP_Core.model.search_data.ResultsItem;
import com.example.user.moviecatalogappv2.adapter.SearchAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.support.v7.widget.DividerItemDecoration.*;

public class MainActivity extends AppCompatActivity implements MainView{

    @BindView(R.id.edtText_search)
    EditText etSearch;

    @BindView(R.id.btn_search)
    Button btnSearch;

    @BindView(R.id.rcView_movieListItem)
    RecyclerView rcViewMovieList;

    private SearchAdapter searchAdapter;
    private List<ResultsItem> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        MainPresenter presenter = new MainPresenter(this);

        buildList();
        loadFakeData();

    }

    private void buildList() {
        searchAdapter = new SearchAdapter();
        rcViewMovieList.addItemDecoration(new DividerItemDecoration(this,VERTICAL));
        rcViewMovieList.setLayoutManager(new LinearLayoutManager(this));
        rcViewMovieList.setAdapter(searchAdapter);
    }


    private void loadFakeData() {
        list.clear();
        for (int i =0; i <= 10; i++){
            ResultsItem perItem = new ResultsItem();
            perItem.setTitle("Title" + i);
            perItem.setOverview("OverView" + i);
            perItem.setReleaseDate("2017-04-27");
            list.add(perItem);
        }
        searchAdapter.replaceAll(list);
    }



}
