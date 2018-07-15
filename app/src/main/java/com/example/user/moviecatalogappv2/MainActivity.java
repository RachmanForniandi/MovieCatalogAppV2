package com.example.user.moviecatalogappv2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.user.moviecatalogappv2.MVP_Core.MainPresenter;
import com.example.user.moviecatalogappv2.MVP_Core.MainView;

public class MainActivity extends AppCompatActivity implements MainView{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MainPresenter presenter = new MainPresenter(this);
    }

}
