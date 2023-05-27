package com.example.projectpas;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements Adapter.AdapterListener {

    RecyclerView rvList;
    ArrayList<EncapField> listSports;
    private Adapter AdapterList;

    public void getAPIOnline(){
        ProgressBar pbLoadBar = findViewById(R.id.pbLoadBar);

        String url = "https://www.thesportsdb.com/api/v1/json/3/search_all_leagues.php?c=England";
        AndroidNetworking.get(url)
                .setTag("test")
                .setPriority(Priority.LOW)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        pbLoadBar.setVisibility(View.GONE);

                        try {
                            JSONArray jsonArrayMovie = jsonObject.getJSONArray("countries");

                            for (int i = 0; i < jsonArrayMovie.length(); i++) {
                                EncapField myList = new EncapField();
                                JSONObject DataSports  = jsonArrayMovie.getJSONObject(i);

                                myList.setName(DataSports.getString("strLeague"));
                                myList.setFirstEvent(DataSports.getString("dateFirstEvent"));
                                myList.setImageBadge(DataSports.getString("strBadge"));
                                myList.setLeagueAlternate(DataSports.getString("strLeagueAlternate"));
                                myList.setSports(DataSports.getString("strSport"));
                                myList.setCountry(DataSports.getString("strCountry"));
                                myList.setDescEN(DataSports.getString("strDescriptionEN"));

                                listSports.add(myList);
                            }

                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }

                        rvList = findViewById(R.id.rvList);

                        AdapterList = new Adapter(getApplicationContext(), listSports, MainActivity.this);
                        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
                        rvList.setHasFixedSize(true);
                        rvList.setLayoutManager(mLayoutManager);
                        rvList.setAdapter(AdapterList);
                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.d("error", "onError: " + anError.toString());
                    }
                });
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.option_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.menu) {
            // Handle search action
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listSports = new ArrayList<>();
        getAPIOnline();
    }

    @Override
    public void onSportsSelected(EncapField myList) {
        Intent detail = new Intent(MainActivity.this, DetailPage.class);
        detail.putExtra("SportsData", myList);
        startActivity(detail);
    }
}