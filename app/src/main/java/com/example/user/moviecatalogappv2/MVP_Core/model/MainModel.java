package com.example.user.moviecatalogappv2.MVP_Core.model;

import java.util.List;

public class MainModel {
    private int page;
    private int totalPages;
    private List<ResultsItem> results;
    private int totalResults;

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
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

    public int getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(int totalResults) {
        this.totalResults = totalResults;
    }

    @Override
    public String toString() {
        return "MainModel{" +
                "page='" + page + '\''+
                ", totalPages='" + totalPages + '\''+
                ", results='" + results + '\''+
                ", totalResults='" + totalResults + '\''+
                "}";
    }
}
