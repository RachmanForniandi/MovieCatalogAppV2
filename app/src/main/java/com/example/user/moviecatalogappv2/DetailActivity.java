package com.example.user.moviecatalogappv2;

import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.user.moviecatalogappv2.API.APIResponder;
import com.example.user.moviecatalogappv2.MVP_Core.model.detail_data.DetailModel;
import com.example.user.moviecatalogappv2.MVP_Core.model.search_data.ResultsItem;
import com.example.user.moviecatalogappv2.utils.DateTime;
import com.google.gson.Gson;

import java.text.NumberFormat;
import java.util.List;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailActivity extends AppCompatActivity {

    public static final String MOVIE_ITEM = "movie_item";

    @BindView(R.id.toolbar_detail)
    Toolbar toolbarDetail;

    @BindView(R.id.collapsing_toolbar_detail)
    CollapsingToolbarLayout collapsingToolbarLayoutDetail;

    @BindView(R.id.asBackDrop)
    ImageView backdropImg;

    @BindView(R.id.img_poster_detail)
    ImageView imgPosterDetail;

    @BindView(R.id.txtView_title)
    TextView txtViewTitle;

    @BindView(R.id.txtView_release_date_detail)
    TextView txtViewReleaseDateDetail;

    @BindView(R.id.txtView_vote_score)
    TextView txtViewVoteScore;

    @BindView(R.id.txtView_genres)
    TextView txtViewGenres;

    @BindView(R.id.txtView_overview_detail)
    TextView txtViewOverViewDetail;

    @BindView(R.id.img_poster_belongs)
    ImageView imgPosterBelongs;

    @BindView(R.id.txtView_title_belongs)
    TextView txtViewTitleBelongs;

    @BindView(R.id.txtView_budget)
    TextView txtViewBudget;

    @BindView(R.id.txtView_revenue)
    TextView txtViewRevenue;

    @BindView(R.id.txtView_companies)
    TextView txtViewCompanies;

    @BindView(R.id.txtView_countries)
    TextView txtViewCountries;

    @BindViews({
            R.id.rating_star1,
            R.id.rating_star2,
            R.id.rating_star3,
            R.id.rating_star4,
            R.id.rating_star5
    })
    List<ImageView>rating_vote;

    private Call<DetailModel> apiCall;
    private APIResponder apiResponder = new APIResponder();
    private Gson gson = new Gson();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ButterKnife.bind(this);
        setSupportActionBar(toolbarDetail);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        collapsingToolbarLayoutDetail.setExpandedTitleColor(getResources().getColor(android.R.color.transparent));

        String movie_item = getIntent().getStringExtra(MOVIE_ITEM);
        loadDataDetail(movie_item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (apiCall != null)apiCall.cancel();
    }

    private void loadDataDetail(String movie_item) {
        ResultsItem perItem = gson.fromJson(movie_item, ResultsItem.class);
        loadDataDetailFromServer(String.valueOf(perItem.getId()));

        getSupportActionBar().setTitle(perItem.getTitle());
        txtViewTitle.setText(perItem.getTitle());

        Glide.with(DetailActivity.this)
                .load(BuildConfig.BASE_URL_IMAGE + "w185" + perItem.getBackdropPath())
                .into(backdropImg);

        Glide.with(DetailActivity.this)
                .load(BuildConfig.BASE_URL_IMAGE + "w154" + perItem.getPosterPath())
                .into(imgPosterDetail);

        txtViewReleaseDateDetail.setText(DateTime.getLongDate(perItem.getReleaseDate()));
        txtViewVoteScore.setText(String.valueOf(perItem.getVoteAverage()));
        txtViewOverViewDetail.setText(perItem.getOverview());

        double userRating = perItem.getVoteAverage() / 2;
        int integerPart = (int)userRating;

        for (int i = 0; i < integerPart; i++){
            rating_vote.get(i).setImageResource(R.drawable.ic_star_black_24dp);
        }

        if (Math.round(userRating) > integerPart){
            rating_vote.get(integerPart).setImageResource(R.drawable.ic_star_half_black_24dp);
        }
    }

    private void loadDataDetailFromServer(String movie_item) {
        apiCall = apiResponder.getService().getDetailMovie(movie_item);
        apiCall.enqueue(new Callback<DetailModel>() {
            @Override
            public void onResponse(Call<DetailModel> call, Response<DetailModel> response) {
                if (response.isSuccessful()){
                    DetailModel perItem = response.body();

                    int size = 0;

                    String genres = "";
                    size = perItem.getGenres().size();
                    for (int i=0; i < size; i++){
                        genres += "√ " + perItem.getGenres().get(i).getName() + (i + 1 < size ? "\n" : "");
                    }
                    txtViewGenres.setText(genres);

                    if (perItem.getBelongsToCollection()!= null){
                        Glide.with(DetailActivity.this)
                                .load(BuildConfig.BASE_URL_IMAGE + "w92" + perItem.getBelongsToCollection().getPosterPath())
                                .into(imgPosterBelongs);

                        txtViewTitleBelongs.setText(perItem.getBelongsToCollection().getName());
                    }

                    txtViewBudget.setText("$ " + NumberFormat.getIntegerInstance().format(perItem.getBudget()));
                    txtViewRevenue.setText("$ " + NumberFormat.getIntegerInstance().format(perItem.getRevenue()));

                    String companies = "";
                    size = perItem.getProductionCompanies().size();
                    for (int i = 0; i < size; i++){
                        companies += "√ " + perItem.getProductionCompanies().get(i).getName()+(i + 1 < size ? "\n" : "");
                    }
                    txtViewCompanies.setText(companies);

                    String countries = "";
                    size = perItem.getProductionCountries().size();
                    for (int i = 0; i < size; i++){
                        countries += "√ " + perItem.getProductionCountries().get(i).getName() + (i + 1 < size ? "\n" : "");
                    }
                    txtViewCountries.setText(countries);
                }else loadFailed();
            }

            @Override
            public void onFailure(Call<DetailModel> call, Throwable t) {
                loadFailed();
            }
        });
    }

    private void loadFailed() {
        Toast.makeText(DetailActivity.this, "Cannot fetch detail movie.\nPlease check your Internet connections!", Toast.LENGTH_SHORT).show();
    }
}
