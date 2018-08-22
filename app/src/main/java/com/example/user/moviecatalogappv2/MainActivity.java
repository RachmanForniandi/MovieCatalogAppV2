package com.example.user.moviecatalogappv2;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import android.widget.Toast;
import com.example.user.moviecatalogappv2.API.APIResponder;
import com.example.user.moviecatalogappv2.MVP_Core.MainPresenter;
import com.example.user.moviecatalogappv2.MVP_Core.MainView;
import com.example.user.moviecatalogappv2.MVP_Core.model.search_data.ResultsItem;
import com.example.user.moviecatalogappv2.MVP_Core.model.search_data.SearchModel;
import com.example.user.moviecatalogappv2.adapter.SearchAdapter;
import com.example.user.moviecatalogappv2.utils.DateTime;
import com.mancj.materialsearchbar.MaterialSearchBar;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.support.v7.widget.DividerItemDecoration.VERTICAL;

public class MainActivity extends AppCompatActivity implements MainView, MaterialSearchBar.OnSearchActionListener, SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.rcView_movieListItem)
    RecyclerView rcViewMovieList;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.search_bar)
    MaterialSearchBar searchBar;

    @BindView(R.id.pull_refresh)
    SwipeRefreshLayout swipeRefresh;

    private SearchAdapter searchAdapter;
    private List<ResultsItem> list = new ArrayList<>();

    private String movie_title ="";
    private Call<SearchModel> apiCall;
    private APIResponder apiResponder;
    private int resumePage = 1;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        searchBar.setOnSearchActionListener(this);
        swipeRefresh.setOnRefreshListener(this);


        
        apiResponder = new APIResponder();

        MainPresenter presenter = new MainPresenter(this);

        buildList();
        loadData();
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        if (apiCall != null)apiCall.cancel();
    }


    @Override
    public void onSearchStateChanged(boolean enabled) {

    }

    @Override
    public void onSearchConfirmed(CharSequence text) {
        movie_title = String.valueOf(text);
        if (movie_title.equals("")) loadData();
        else loadData(String.valueOf(text));
    }

    @Override
    public void onButtonClicked(int buttonCode) {

    }

    @Override
    public void onRefresh() {
        resumePage = 1;
        if (movie_title.equals(""))loadData();
        else loadData(movie_title);
    }


    private void buildList() {
        searchAdapter = new SearchAdapter();
        rcViewMovieList.addItemDecoration(new DividerItemDecoration(this,VERTICAL));
        rcViewMovieList.setLayoutManager(new LinearLayoutManager(this));
        rcViewMovieList.setAdapter(searchAdapter);
    }


    /*private void loadFakeData() {
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
    }*/

    private void loadData() {
        getSupportActionBar().setSubtitle("");

        apiCall = apiResponder.getService().getPopularMovie(resumePage);
        apiCall.enqueue(new Callback<SearchModel>() {
            @Override
            public void onResponse(Call<SearchModel> call, Response<SearchModel> response) {
                if (response.isSuccessful()){
                    List<ResultsItem> items = response.body().getResults();

                    if (resumePage > 1) searchAdapter.updateData(items);
                    else searchAdapter.replaceAll(items);
                    stopRefreshing();
                }else loadFailed();
            }

            @Override
            public void onFailure(Call<SearchModel> call, Throwable t) {
                loadFailed();
            }
        });
    }

    private void loadData(String movie_title){
        getSupportActionBar().setSubtitle("Searching: "+ movie_title);
        searchAdapter.clearAll();
        stopRefreshing();
    }



    private void loadFailed() {
        stopRefreshing();
        Toast.makeText(MainActivity.this,"Sorry, load data failure",Toast.LENGTH_SHORT).show();
    }

    private void stopRefreshing() {
        if (swipeRefresh.isRefreshing())swipeRefresh.setRefreshing(false);
    }


}
