package com.example.user.moviecatalogappv2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.user.moviecatalogappv2.API.APIResponder;
import com.example.user.moviecatalogappv2.MVP_Core.model.detail_data.DetailModel;
import com.example.user.moviecatalogappv2.utils.DateTime;

import java.util.List;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailActivity extends AppCompatActivity {

    public static final String MOVIE_ID = "movie_id";

    @BindView(R.id.toolbar_detail)
    Toolbar toolbarDetail;

    @BindView(R.id.asBackDrop)
    ImageView backdropImg;

    @BindView(R.id.img_poster_detail_info)
    ImageView imgPosterDetailInfo;

    @BindView(R.id.txtView_release_date_detail)
    TextView txtViewReleaseDateDetail;

    @BindView(R.id.txtView_vote_score)
    TextView txtViewVoteScore;

    @BindView(R.id.txtView_overview_detail)
    TextView txtViewOverviewDetail;

    @BindViews({
            R.id.star1,
            R.id.star2,
            R.id.star3,
            R.id.star4,
            R.id.star5
    })
    List<ImageView>rating_vote;

    private Call<DetailModel> apiCall;
    private APIResponder apiResponder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        apiResponder = new APIResponder();

        String movie_id = getIntent().getStringExtra(MOVIE_ID);
        loadDataDetail(movie_id);

        ButterKnife.bind(this);
        setSupportActionBar(toolbarDetail);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void loadDataDetail(String movie_id) {
        apiCall = apiResponder.getService().getDetailMovie(movie_id);
        apiCall.enqueue(new Callback<DetailModel>() {
            @Override
            public void onResponse(Call<DetailModel> call, Response<DetailModel> response) {
                if (response.isSuccessful()){
                    DetailModel perItem = response.body();

                    getSupportActionBar().setTitle(perItem.getTitle());

                    Glide.with(DetailActivity.this)
                            .load(BuildConfig.BASE_URL_IMAGE + "w185" + perItem.getBackdropPath())
                            .apply(new RequestOptions().centerCrop())
                            .into(backdropImg);

                    Glide.with(DetailActivity.this)
                            .load(BuildConfig.BASE_URL_IMAGE + "w154" + perItem.getBackdropPath())
                            .apply(new RequestOptions()
                                    .placeholder(R.drawable.sampleholder)
                                    .centerCrop()
                            )
                            .into(backdropImg);

                    txtViewReleaseDateDetail.setText(DateTime.getLongDate(perItem.getReleaseDate()));
                    txtViewVoteScore.setText(String.valueOf(perItem.getVoteAverage()));
                    txtViewOverviewDetail.setText(perItem.getOverview());

                    double userRating = perItem.getVoteAverage() / 2;
                    int integerPart = (int)userRating;

                    for (int i = 0; i < integerPart; i++){
                        rating_vote.get(i).setImageResource(R.drawable.ic_star_black_24dp);
                    }

                    if (Math.round(userRating) > integerPart){
                        rating_vote.get(integerPart).setImageResource(R.drawable.ic_star_half_black_24dp);
                    }
                }else loadFailed();
            }

            @Override
            public void onFailure(Call<DetailModel> call, Throwable t) {
                loadFailed();
            }
        });
    }

    private void loadFailed() {
        Toast.makeText(DetailActivity.this, "Cannot fetch detail movie.\n Please check your Internet connections!", Toast.LENGTH_SHORT).show();
    }
}
