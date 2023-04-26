package com.example.realmattendence;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

import io.realm.Realm;

public class DisplayData extends AppCompatActivity {

    TextView values;
    Button Update;
    Realm realm;

    List<DataModel> datamodel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_data);

        Update = findViewById(R.id.update);
        Update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DisplayData.this,UpdateDelete.class));
            }
        });

        values = findViewById(R.id.tvvalues);
        realm = Realm.getDefaultInstance();

        displaydata();
    }

    private void displaydata() {
        datamodel = realm.where(DataModel.class).findAll();
        values.setText("");
        for (int i = 0; i < datamodel.size(); i++) {
            values.append("\n ID: "+datamodel.get(i).getId() +
                    "\n Name: "+datamodel.get(i).getSname() +
                    "\n Status: "+datamodel.get(i).getStatus() + "\n");
        }
    }
}