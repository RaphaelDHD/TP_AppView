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
    private final int GALLERY_REQUEST_CODE = 78;
    private static final int PICK_IMAGE_REQUEST = 13;


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

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        //Le code de résultat est RESULT_OK uniquement si l'utilisateur sélectionne une image.
        super.onActivityResult(requestCode, resultCode, data);
        ImageView imageView = (ImageView) findViewById(R.id.imageViewProfil);
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



    public void onClickImageProfil(View view){
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        String[] mimeTypes = {"image/jpeg", "image/png", "image/jpg"};
        intent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes);
        startActivityForResult(intent, GALLERY_REQUEST_CODE);
    }


}