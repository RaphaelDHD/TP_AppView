package com.example.tp_3;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Annuaire {
    private ArrayList<Contact> contacts;

    public Contact getContact(int id){
        return contacts.get(id);
    }
}
