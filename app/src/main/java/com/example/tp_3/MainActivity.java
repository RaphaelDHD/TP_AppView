package com.example.tp_3;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import android.Manifest;
import androidx.core.app.ActivityCompat;


import javax.crypto.Cipher;

public class MainActivity extends AppCompatActivity {


    ArrayList<Contact> listContact;
    contactAdapter adapter;
    public static final String FICHIER ="saveFile.txt";
    private static final int REQUEST_CODE = 12;
    private static final int PROFIL_REQUEST = 50;


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //listContact = new ArrayList<Contact>();
        listContact = LoadContact(this);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_CODE);
        }

        adapter = new contactAdapter(getApplicationContext(),listContact);
        setContentView(R.layout.activity_main);

        ListView listView = findViewById(R.id.listView);
        listView.setAdapter(adapter);

    }

    protected ArrayList<Contact> LoadContact(Context context){
        ArrayList<Contact> contacts = new ArrayList<>();
        File directory = getExternalFilesDir(null);
        File file = new File(directory, FICHIER);
        try {
            FileInputStream fis = new FileInputStream(file);
            ObjectInputStream ois = new ObjectInputStream(fis);
            contacts = (ArrayList<Contact>) ois.readObject();
            ois.close();
            fis.close();
            Log.d("test" , "load réussi");
        } catch (IOException | ClassNotFoundException e) {

            Log.d("test","fail load");
        }
        return contacts;
    }

    protected void SaveContact(){
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            File directory = getExternalFilesDir(null);
            File file = new File(directory, FICHIER);
            try {
                FileOutputStream outputStream = new FileOutputStream(file);
                ObjectOutputStream objectOut = new ObjectOutputStream(outputStream);
                objectOut.writeObject(listContact);
                objectOut.close();
                outputStream.close();
                Log.d("test" , "sauvegard réussi");
                System.out.println("Sauvegarde réussie dans le fichier " + FICHIER);
            } catch (IOException e) {
                Log.d("test","fail save");
                e.printStackTrace();
            }
        }
    }



    @Override
    protected void onResume() {
        super.onResume();
        ListView listView = findViewById(R.id.listView);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent myIntent = new Intent(MainActivity.this,profilActivity.class);
                myIntent.putExtra("Contact",listContact.get(position));
                myIntent.putExtra("position",position);
                startActivityForResult(myIntent,PROFIL_REQUEST);
            }
        });
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Toast toast = Toast.makeText(MainActivity.this, listContact.get(position).getPrenom() + " " + listContact.get(position).getNom() + " supprimé", Toast.LENGTH_SHORT);
                toast.show();
                listContact.remove(position);
                adapter.notifyDataSetChanged();
                SaveContact();
                return false;
            }
        });

    }



    public void OnClickCreate(View view){
        Intent myIntent = new Intent(MainActivity.this,CreateActivity.class);
        startActivityForResult(myIntent,250);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("test",Integer.toString(requestCode) + ' ' + Integer.toString(resultCode));
        if (requestCode == 250 && resultCode == 10) {
            Log.d("test","result success");
            listContact.add((Contact)data.getSerializableExtra("ContactCreer"));
            adapter.notifyDataSetChanged();
            SaveContact();
        }
        if (requestCode == PROFIL_REQUEST && resultCode == 10){
            Log.d("test","passage apres fin modif");
            int pos = data.getIntExtra("position",1);
            Log.d("test", String.valueOf(pos));
            Contact cnt = (Contact) data.getSerializableExtra("ContactModifier");
            listContact.set(pos,cnt);
            Log.d("test",listContact.get(pos).getNom());
            adapter.notifyDataSetChanged();
            SaveContact();
        }
    }

}