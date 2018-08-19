package com.example.user.moviecatalogappv2.MVP_Core.model.detail_data;

import com.google.gson.annotations.SerializedName;

public class SpokenLanguagesItem {

    @SerializedName("iso_639_1")
    private String iso6391;

    @SerializedName("name")
    private String name;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIso6391() {
        return iso6391;
    }

    public void setIso6391(String iso6391) {
        this.iso6391 = iso6391;
    }

    @Override
    public String toString() {
        return "SpokenLanguagesItem{" +
                "iso6391='" + iso6391 + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
