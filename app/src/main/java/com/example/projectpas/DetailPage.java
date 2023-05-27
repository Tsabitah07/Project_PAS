package com.example.projectpas;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class DetailPage extends AppCompatActivity {

    Intent pindah;
    EncapField DataSports;
    TextView tvTitle, tvFirstEvent, tvSports, tvCountry, tvLeagueAlternate, tvDescEN;
    ImageView imageDetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_page);

        pindah = getIntent();
        DataSports = (EncapField) pindah.getParcelableExtra("SportsData");

        imageDetail = findViewById(R.id.imageDetail);
        Glide.with(imageDetail).load(DataSports.getImageBadge()).into(imageDetail);

        tvTitle = findViewById(R.id.tvTitle);
        tvTitle.setText(DataSports.getName());

        tvLeagueAlternate = findViewById(R.id.tvLeagueAlternate);
        tvLeagueAlternate.setText("Alternative name : " + DataSports.getLeagueAlternate());

        tvSports = findViewById(R.id.tvSports);
        tvSports.setText("Sports : " + DataSports.getSports());

        tvCountry = findViewById(R.id.tvCountry);
        tvCountry.setText("Country : " + DataSports.getCountry());

        tvFirstEvent = findViewById(R.id.tvFirstEvent);
        tvFirstEvent.setText("First event : " + DataSports.getFirstEvent());

        tvDescEN = findViewById(R.id.tvDescEN);
        tvDescEN.setText(DataSports.getDescEN());
    }
}