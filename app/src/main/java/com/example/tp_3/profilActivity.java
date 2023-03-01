package com.example.tp_3;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.DatePicker;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import android.app.DatePickerDialog;


public class profilActivity extends AppCompatActivity {

    public String img = "";
    Contact contact;
    int position;
    EditText dateEdit;
    final Calendar myCalendar= Calendar.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detaileddata);

        dateEdit = findViewById(R.id.editTextDateProfil);
        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH,month);
                myCalendar.set(Calendar.DAY_OF_MONTH,day);
                updateLabel();
            }
        };
        dateEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(profilActivity.this,date,myCalendar.get(Calendar.YEAR),myCalendar.get(Calendar.MONTH),myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        Intent intentMain = getIntent();
        Contact contact = (Contact) intentMain.getSerializableExtra("Contact");
        EditText editText = findViewById(R.id.editTextPrenomProfil);
        editText.setText(contact.getPrenom());
        editText = findViewById(R.id.editTextNomProfil);
        editText.setText(contact.getNom());
        editText = findViewById(R.id.editTextNumeroProfil);
        editText.setText(contact.getNumPhone());
        editText = findViewById(R.id.editTextDateProfil);
        editText.setText(contact.getDateNaissance());
        ImageView image = findViewById(R.id.imageViewProfil);
        if (contact.getImg() != "") {
            image.setImageURI(Uri.parse(contact.getImg()));
            img = contact.getImg();
        }

        position = intentMain.getIntExtra("position",0);

    }

    public void onClickModified(View view){
            EditText editText = findViewById(R.id.editTextNomProfil);
            String nom = editText.getText().toString();

            editText = findViewById(R.id.editTextPrenomProfil);
            String prenom = editText.getText().toString();

            editText = findViewById(R.id.editTextNumeroProfil);
            String phone = editText.getText().toString();

            editText = findViewById(R.id.editTextDateProfil);
            String date = editText.getText().toString();
            contact = new Contact(img ,nom,prenom,phone,date);
            Intent myIntent = new Intent();
            myIntent.putExtra("ContactModifier", contact);
            myIntent.putExtra("position",position);
            setResult(10,myIntent);
            super.onBackPressed();
        }

    private void updateLabel(){
        String myFormat="MM/dd/yyyy";
        SimpleDateFormat dateFormat=new SimpleDateFormat(myFormat, Locale.FRANCE);
        dateEdit.setText(dateFormat.format(myCalendar.getTime()));
    }
}