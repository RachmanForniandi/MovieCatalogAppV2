package com.example.user.moviecatalogappv2.MVP_Core.model.detail_data;

import com.google.gson.annotations.SerializedName;

public class BelongsToCollection {
    @SerializedName("backdrop_path")
    private String backdropPath;

    @SerializedName("name")
    private String name;

    @SerializedName("id")
    private int id;

    @SerializedName("poster_path")
    private String posterPath;


    public String getBackdropPath() {
        return backdropPath;
    }

    public void setBackdropPath(String backdropPath) {
        this.backdropPath = backdropPath;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    @Override
    public String toString() {
        return "BelongsToCollection{" +
                "backdropPath='" + backdropPath + '\'' +
                ", name='" + name + '\'' +
                ", id=" + id +
                ", posterPath='" + posterPath + '\'' +
                '}';
    }
}
