package com.olukoye.hannah.savicsmedic;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SettingsActivity extends AppCompatActivity {
    String username;
    SharedPreferences sharedPreferences2;
    Button save_button;
    EditText usernameTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        sharedPreferences2 = this.getSharedPreferences("UsernameData", 0);

        if(sharedPreferences2.getString("Username","") != null) {

            username = sharedPreferences2.getString("Username","");
            Toast.makeText(this,"“Hi again, " + username,Toast.LENGTH_LONG).show();

        }

        usernameTxt = findViewById(R.id.username);
        save_button = findViewById(R.id.save_username);

        save_button.setOnClickListener(new View.OnClickListener() {
                                           @Override
                                           public void onClick(View v) {

                                               SharedPreferences.Editor editor = sharedPreferences2.edit();
                                               editor.putString("Username", usernameTxt.getText().toString());
                                               editor.commit();
                                           }
                                       }
            );

        }

}
