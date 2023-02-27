package com.example.tp_3;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.crypto.Cipher;

public class MainActivity extends AppCompatActivity {


    ArrayList<Contact> listContact;
    contactAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        listContact = new ArrayList<Contact>();
        adapter = new contactAdapter(getApplicationContext(),listContact);
        setContentView(R.layout.activity_main);

        ListView listView = findViewById(R.id.listView);
        listView.setAdapter(adapter);

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
                startActivity(myIntent);
            }
        });
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                listContact.remove(position);
                adapter.notifyDataSetChanged();
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
        }
    }

}