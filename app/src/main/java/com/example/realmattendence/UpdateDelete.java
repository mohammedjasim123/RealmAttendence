package com.example.realmattendence;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import io.realm.Realm;

public class UpdateDelete extends AppCompatActivity {

    EditText txtid;
    Realm realm;
    RadioGroup radioGroup;
    RadioButton Present,Absent;
    Button Update, Delete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_delete);

        realm = Realm.getDefaultInstance();

        txtid = findViewById(R.id.id);
        radioGroup = findViewById(R.id.status);
        Present = findViewById(R.id.present);
        Absent = findViewById(R.id.absent);
        Update = findViewById(R.id.update);

        Update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                long id = Long.parseLong(txtid.getText().toString());
                DataModel dataModel = realm.where(DataModel.class).equalTo("id",id).findFirst();
                update(dataModel);
            }
        });

        Delete = findViewById(R.id.delete);
        Delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                long id = Long.parseLong(txtid.getText().toString());
                DataModel dataModel = realm.where(DataModel.class).equalTo("id",id).findFirst();
                txtid.setText("");

                if (dataModel.getId()==0){
                    Toast.makeText(UpdateDelete.this, "Add student", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(UpdateDelete.this, MainActivity.class));
                } else {
                    realm.executeTransaction(new Realm.Transaction() {
                        @Override
                        public void execute(Realm realm) {
                            dataModel.deleteFromRealm();
                            Toast.makeText(UpdateDelete.this, "Student removed successfully", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }

    private void update(DataModel dataModel) {
        int button = radioGroup.getCheckedRadioButtonId();
        Present = findViewById(button);
        String Status = Present.getText().toString();

        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                dataModel.setStatus(Status);
                realm.copyToRealmOrUpdate(dataModel);
                Toast.makeText(UpdateDelete.this, "Updated Successfully", Toast.LENGTH_SHORT).show();
            }
        });
    }
}