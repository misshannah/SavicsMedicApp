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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private SharedPreferences sharedPreferences;
    int patientAge, patientCount;
    int counter = 0;
    String patientName,patientEmail,patientGender;
    EditText nameText, ageText, emailText;
    ArrayList<Patient> patientArrayList;
    ListView patientList;
    RadioGroup genderText;
    PatientsAdapter adapter;


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
        navigationView.setItemIconTintList(null);

        nameText = findViewById(R.id.full_name);
        ageText = findViewById(R.id.age);
        emailText = findViewById(R.id.email);

        genderText = findViewById(R.id.gender);

        genderText.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                 @Override
                 public void onCheckedChanged(RadioGroup group, int checkedId)
                    {
                        switch(checkedId){
                            case R.id.male_button:
                                patientGender = "M";
                                break;
                            case R.id.female_button:
                                patientGender = "F";
                                break;
                            case R.id.other_button:
                                patientGender = "0";
                                break;
                        }
                     }
                 }
        );

        patientList = findViewById(R.id.patient_list);

        loadData();


    }

    private void showList() {
        try {


            saveData();

            patientArrayList = new ArrayList<>();
            patientArrayList.add(new Patient(patientName,
                    patientEmail,patientAge,patientGender, patientCount));

            adapter = new PatientsAdapter(this,patientArrayList);

            patientList.setAdapter(adapter);

            loadData();


        }catch
        (NumberFormatException bgerror){

        }
    }

    private void loadData() {

        sharedPreferences = this.getSharedPreferences("PatientData", 0);


    }
    private void savePreferences(String key, String value) {
        sharedPreferences = this.getSharedPreferences("PatientData", 0);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.commit();


    }

    public void saveData(){
        if(counter < 5) {
            counter++;
            savePreferences("Count", String.valueOf(counter));

            savePreferences("Name", nameText.getText().toString());
            savePreferences("Age", ageText.getText().toString());
            savePreferences("Email", emailText.getText().toString());
            savePreferences("Gender", patientGender);

            patientCount = Integer.parseInt(sharedPreferences.getString("Count", ""));
            patientName = sharedPreferences.getString("Name", "");
            patientAge = Integer.parseInt(sharedPreferences.getString("Age", ""));
            patientEmail = sharedPreferences.getString("Email", "");
            patientGender = sharedPreferences.getString("Gender", "");



        } else {
            Toast.makeText(this,"Maximum number of patients reached!",Toast.LENGTH_LONG).show();
        }

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
            counter = 0;
            savePreferences("Name", "");
            savePreferences("Age", "");
            savePreferences("Email", "");
            savePreferences("Gender", "");
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
        }  else if (id == R.id.nav_share) {

            Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
            sharingIntent.setType("text/plain");
            sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Android Challenge - Patient Data");
            sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, patientArrayList);
            startActivity(Intent.createChooser(sharingIntent, "Share your results with your friends"));

        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}
