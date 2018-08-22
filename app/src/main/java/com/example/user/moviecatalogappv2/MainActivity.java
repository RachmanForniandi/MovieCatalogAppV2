package com.example.user.moviecatalogappv2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.user.moviecatalogappv2.MVP_Core.MainPresenter;
import com.example.user.moviecatalogappv2.MVP_Core.MainView;
import com.example.user.moviecatalogappv2.MVP_Core.model.search_data.ResultsItem;
import com.example.user.moviecatalogappv2.adapter.SearchAdapter;
import com.example.user.moviecatalogappv2.utils.DateTime;
import com.mancj.materialsearchbar.MaterialSearchBar;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.support.v7.widget.DividerItemDecoration.*;

public class MainActivity extends AppCompatActivity implements MainView, MaterialSearchBar.OnSearchActionListener{

    @BindView(R.id.rcView_movieListItem)
    RecyclerView rcViewMovieList;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.search_bar)
    MaterialSearchBar searchBar;

    private SearchAdapter searchAdapter;
    private List<ResultsItem> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        searchBar.setOnSearchActionListener(this);

        MainPresenter presenter = new MainPresenter(this);

        buildList();
        loadFakeData();
    }

    @Override
    public void onSearchStateChanged(boolean enabled) {

    }

    @Override
    public void onSearchConfirmed(CharSequence text) {
        Toast.makeText(this,"Searching"+text,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onButtonClicked(int buttonCode) {

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
            perItem.setPosterPath("/vSNxAJTlD0r02V9sPYpOjqDZXUK.jpg");
            perItem.setTitle("This is very very very long movie title that you can read" + i);
            perItem.setOverview("This is very very very long movie overview that you can read" + i);
            perItem.setReleaseDate(DateTime.getLongDate("2017-04-27"+i));
            list.add(perItem);
        }
        searchAdapter.replaceAll(list);
    }



}
