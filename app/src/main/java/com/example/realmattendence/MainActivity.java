package com.example.realmattendence;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import io.realm.Realm;

public class MainActivity extends AppCompatActivity {

    EditText name;

    Realm realm;
    RadioGroup radioGroup;
    RadioButton present,absent;
    Button save, Show;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        name = findViewById(R.id.txtname);
        radioGroup = findViewById(R.id.statusgroup);
        present = findViewById(R.id.present);
        absent = findViewById(R.id.absent);
        Show = findViewById(R.id.show);
        realm = Realm.getDefaultInstance();

        save = findViewById(R.id.save);

        Show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,DisplayData.class));
            }
        });
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Name = name.getText().toString();
                String Status = radioGroup.toString();
                if(present.isChecked()){
                    Status = present.getText().toString();
                }else {
                    Status = absent.getText().toString();
                }

                inserdata(Name,Status);
                radioGroup.clearCheck();
                name.setText("");
            }
        });

    }

    private void inserdata(String name, String status) {
        DataModel dataModel = new DataModel();
        Number id = realm.where(DataModel.class).max("id");
        long nextid;

        if (id==null){
            nextid = 1;
        } else {
            nextid = id.intValue()+1;
        }

        dataModel.setId(nextid);
        dataModel.setSname(name);
        dataModel.setStatus(status);

        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.copyToRealm(dataModel);
            }
        });
        Toast.makeText(this, "Data saved successfully", Toast.LENGTH_SHORT).show();
    }
}