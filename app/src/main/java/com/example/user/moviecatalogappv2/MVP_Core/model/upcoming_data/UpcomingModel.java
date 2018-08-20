package com.example.user.moviecatalogappv2.MVP_Core.model.upcoming_data;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class UpcomingModel {

    @SerializedName("page")
    private int page;
    @SerializedName("total_results")
    private int totalResults;
    @SerializedName("dates")
    private Dates dates;
    @SerializedName("total_pages")
    private int totalPages;
    @SerializedName("results")
    private List<ResultsItem> results;

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(int totalResults) {
        this.totalResults = totalResults;
    }

    public Dates getDates() {
        return dates;
    }

    public void setDates(Dates dates) {
        this.dates = dates;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public List<ResultsItem> getResults() {
        return results;
    }

    public void setResults(List<ResultsItem> results) {
        this.results = results;
    }


    @Override
    public String toString() {
        return "UpcomingModel{" +
                "page=" + page +
                ", totalResults=" + totalResults +
                ", dates=" + dates +
                ", totalPages=" + totalPages +
                ", results=" + results +
                '}';
    }
}
