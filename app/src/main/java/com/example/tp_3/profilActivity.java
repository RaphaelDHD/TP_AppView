package com.example.tp_3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class profilActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detaileddata);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Intent intentMain = getIntent();
        Contact contact = (Contact) intentMain.getSerializableExtra("Contact");
        TextView textView = findViewById(R.id.textViewPrenomProfil);
        textView.setText(contact.getPrenom());
        textView = findViewById(R.id.textViewNomProfil);
        textView.setText(contact.getNom());
        textView = findViewById(R.id.textViewNumeroProfil);
        textView.setText(contact.getNumPhone());
        textView = findViewById(R.id.textViewDateProfil);
        textView.setText(contact.getDateNaissance());

    }

}