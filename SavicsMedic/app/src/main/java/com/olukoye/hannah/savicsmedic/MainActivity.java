package com.olukoye.hannah.savicsmedic;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.MenuItem;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioGroup;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private SharedPreferences sharedPreferences;
    int patientAge;
    String patientName,patientEmail,patientGender;
    EditText nameText, ageText, emailText;
    ArrayList<Patient> patientArrayList;
    ListView patientList;
    RadioGroup genderText;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showList();
            }
        });
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        nameText = findViewById(R.id.full_name);
        ageText = findViewById(R.id.age);
        emailText = findViewById(R.id.email);

        genderText = findViewById(R.id.gender);
        genderText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selectedId = genderText.getCheckedRadioButtonId();
                if (selectedId == 0) {
                    patientGender = "M";
                }
                if (selectedId == 1) {
                    patientGender = "F";
                }
                if (selectedId == 2) {

                    patientGender = "O";
                }

            }
        });

        patientList = findViewById(R.id.patient_list);


        loadData();


    }

    private void showList() {
        try {


            saveData();

            PatientsAdapter adapter = new PatientsAdapter(this,patientArrayList);

            patientList.setAdapter(adapter);


        }catch
        (NumberFormatException bgerror){

        }
    }

    private void loadData() {

        sharedPreferences = this.getSharedPreferences("PatientData", 0);
        Log.d ("Values:", String.valueOf(sharedPreferences.getAll()));


    }
    private void savePreferences(String key, String value) {
        sharedPreferences = this.getSharedPreferences("PatientData", 0);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.commit();


    }

    public void saveData(){
        savePreferences("Name", nameText.getText().toString());
        savePreferences("Age", ageText.getText().toString());
        savePreferences("Email", emailText.getText().toString());
        savePreferences("Gender", genderText.toString());

        patientName = sharedPreferences.getString("Name", "");
        patientAge = Integer.parseInt(sharedPreferences.getString("Age", ""));
        patientEmail = sharedPreferences.getString("Email", "");
        patientGender = sharedPreferences.getString("Gender", "");

        patientArrayList = new ArrayList<Patient>();
        patientArrayList.add(new Patient(patientName,
                patientEmail,patientAge,patientGender.charAt(0)));


    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_reset) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
        } else if (id == R.id.nav_settings) {
            Intent openSettingsScreen = new Intent(this, SettingsActivity.class);
            startActivity(openSettingsScreen);
            finish();
        }  else if (id == R.id.nav_share) {

            Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
            sharingIntent.setType("text/plain");
            sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Android Challenge");
            sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, "Savics Medic Application");
            startActivity(Intent.createChooser(sharingIntent, "Share your results with your friends"));

        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}
