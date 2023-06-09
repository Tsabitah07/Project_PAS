package com.example.projectpas;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
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
import com.bumptech.glide.Glide;

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
                            JSONArray jsonArraySports = jsonObject.getJSONArray("countries");

                            for (int i = 0; i < jsonArraySports.length(); i++) {
                                EncapField myList = new EncapField();
                                JSONObject DataSports  = jsonArraySports.getJSONObject(i);

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

        if (id == R.id.action_logout) {

            logoutUser();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void logoutUser() {
        // Clear user session or perform any other necessary logout actions

        // Example: Clearing user session using SharedPreferences
        SharedPreferences preferences = getSharedPreferences("shared_prefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.clear();
        editor.apply();

        // Navigate to the login screen or perform other required actions
        Intent loginIntent = new Intent(MainActivity.this, LoginPage.class);
        startActivity(loginIntent);
        finish(); // Optional: Close the current activity to prevent going back to it
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

    //buat nge konfir di adapter, kalo ni item mw di hapus
    @Override
    public void onDataLongClicked(EncapField mySport) {
        // Tampilkan opsi menu delete di sini
        showDeleteMenu(mySport);
    }

    //buat nge apus
    private void showDeleteMenu(EncapField mySport) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete Item");
        builder.setMessage("Are you sure you want to delete this item?");

        builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                // Lakukan operasi penghapusan item di sini
                deleteItem(mySport);
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void deleteItem(EncapField mySport) {
        // Lakukan operasi penghapusan item sesuai dengan data yang diberikan
        listSports.remove(mySport);
        AdapterList.notifyDataSetChanged();
    }
}