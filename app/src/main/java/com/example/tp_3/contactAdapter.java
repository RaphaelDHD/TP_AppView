package com.example.tp_3;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import java.util.ArrayList;

class contactAdapter extends BaseAdapter {
    ArrayList<Contact> list;
    Context context;

    public contactAdapter(Context context, ArrayList<Contact> list) {
        this.list = list;
        this.context = context;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ConstraintLayout layoutItem;
        LayoutInflater mInflater = LayoutInflater.from(context);
        //(1) : Réutilisation du layout
        if (convertView == null) {
            layoutItem = (ConstraintLayout) mInflater.inflate(R.layout.contactdata, parent, false);
        } else {
            layoutItem = (ConstraintLayout) convertView;
        }
        TextView tv = (TextView) layoutItem.findViewById(R.id.textViewNom);
        tv.setText(list.get(position).getNom());

        tv = (TextView) layoutItem.findViewById(R.id.textViewPhone);
        tv.setText(list.get(position).getNumPhone());

        tv = (TextView) layoutItem.findViewById(R.id.textViewPrenom);
        tv.setText(list.get(position).getPrenom());

        return layoutItem;
    }
}