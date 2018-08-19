package com.example.user.moviecatalogappv2.MVP_Core.model.detail_data;

import com.google.gson.annotations.SerializedName;

public class ProductionCompaniesItem {

    @SerializedName("id")
    private int id;

    @SerializedName("name")
    private String name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    @Override
    public String toString() {
        return "ProductionCompaniesItem{" +
                "id=" + id +'\'' +
                ", name='" + name + '\'' +
                "}";
    }
}
