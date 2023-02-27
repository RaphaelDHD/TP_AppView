package com.example.tp_3;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class CreateActivity extends AppCompatActivity {
    Contact contact;
    final Calendar myCalendar= Calendar.getInstance();
    EditText dateEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_activity);

        dateEdit = findViewById(R.id.editTextDate);
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
                new DatePickerDialog(CreateActivity.this,date,myCalendar.get(Calendar.YEAR),myCalendar.get(Calendar.MONTH),myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

    }

    public void OnclickValider(View view){
        EditText editText = findViewById(R.id.editTextNom);
        String nom = editText.getText().toString();

        editText = findViewById(R.id.editTextPrenom);
        String prenom = editText.getText().toString();

        editText = findViewById(R.id.editTextPhone);
        String phone = editText.getText().toString();

        editText = findViewById(R.id.editTextDate);
        String date = editText.getText().toString();
        contact = new Contact(nom,prenom,phone,date);
        Intent myIntent = new Intent();
        myIntent.putExtra("ContactCreer", contact);
        Log.d("test","je passe par onClick");
        setResult(10,myIntent);
        super.onBackPressed();
    }

    private void updateLabel(){
        String myFormat="MM/dd/yyyy";
        SimpleDateFormat dateFormat=new SimpleDateFormat(myFormat, Locale.FRANCE);
        dateEdit.setText(dateFormat.format(myCalendar.getTime()));
    }


    public void onClickImage(View view){
        
    }

}