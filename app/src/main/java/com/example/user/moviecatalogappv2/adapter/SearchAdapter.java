package com.example.user.moviecatalogappv2.adapter;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.user.moviecatalogappv2.BuildConfig;
import com.example.user.moviecatalogappv2.DetailActivity;
import com.example.user.moviecatalogappv2.MVP_Core.model.search_data.ResultsItem;
import com.example.user.moviecatalogappv2.R;
import com.example.user.moviecatalogappv2.utils.CustomTextView;
import com.example.user.moviecatalogappv2.utils.DateTime;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.SearchViewHolder> {

    private List<ResultsItem> list = new ArrayList<>();

    public SearchAdapter(){

    }

    public void clearAll(){
        list.clear();
        notifyDataSetChanged();
    }

    public void replaceAll(List<ResultsItem> items){
        list.clear();
        list = items;
        notifyDataSetChanged();
    }

    public void updateData(List<ResultsItem> items){
        list.addAll(items);
        notifyDataSetChanged();
    }

    @Override
    public SearchViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new SearchViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_main_item,
                        parent,false)
        );
    }

    @Override
    public void onBindViewHolder(SearchViewHolder holder, int position) {
        holder.bind(list.get(position));
    }



    public class SearchViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.img_poster)
        ImageView imgPoster;

        @BindView(R.id.txtView_title)
        CustomTextView lblTextTitle;

        @BindView(R.id.txtView_overview)
        TextView lblTextOverview;

        @BindView(R.id.txtView_release_date)
        TextView lblTextReleaseDate;

        public SearchViewHolder (View itemView){
            super(itemView);

            ButterKnife.bind(this,itemView);
        }


        public void bind(ResultsItem item) {
            lblTextTitle.setText(item.getTitle());
            lblTextOverview.setText(item.getOverview());
            lblTextReleaseDate.setText(DateTime.getLongDate(item.getReleaseDate()));
            Glide.with(itemView.getContext())
                    .load(BuildConfig.BASE_URL_IMAGE + "w45" +item.getPosterPath())
                    .apply(new RequestOptions()
                        .placeholder(R.drawable.sampleholder)
                        .centerCrop())
                    .into(imgPoster);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent toDetail = new Intent(itemView.getContext(), DetailActivity.class);
                    itemView.getContext().startActivity(toDetail);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
