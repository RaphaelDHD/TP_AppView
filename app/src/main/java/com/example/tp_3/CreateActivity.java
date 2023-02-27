package com.example.tp_3;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class CreateActivity extends AppCompatActivity {
    Contact contact;
    final Calendar myCalendar= Calendar.getInstance();
    EditText dateEdit;
    private final int GALLERY_REQUEST_CODE = 24;
    private static final int PICK_IMAGE_REQUEST = 1;
    public String img = "";



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

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        //Le code de résultat est RESULT_OK uniquement si l'utilisateur sélectionne une image.
        super.onActivityResult(requestCode, resultCode, data);
        ImageView imageView = (ImageView) findViewById(R.id.imageView);
        if (resultCode == Activity.RESULT_OK)
            switch (requestCode) {
                case GALLERY_REQUEST_CODE:
                    Uri selectedImage = data.getData();
                    String[] filePathColumn = {MediaStore.Images.Media.DATA};
                    Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
                    cursor.moveToFirst();
                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    String imgDecodableString = cursor.getString(columnIndex);
                    img = cursor.getString(columnIndex);
                    cursor.close();
                    imageView.setImageBitmap(BitmapFactory.decodeFile(imgDecodableString));
                    break;
            }
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            // Obtenez l'URI de l'image sélectionnée
            Uri uri = data.getData();
            // Faites quelque chose avec l'URI de l'image, comme l'afficher dans une ImageView
            imageView.setImageURI(uri);
        }
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
        contact = new Contact(img ,nom,prenom,phone,date);
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
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        String[] mimeTypes = {"image/jpeg", "image/png", "image/jpg"};
        intent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes);
        startActivityForResult(intent, GALLERY_REQUEST_CODE);
    }

}