package com.example.parassehgal.qdown;

import android.util.Log;

import java.util.ArrayList;

/**
 * Created by User on 1/29/2018.
 */

public class Cart {
   static ArrayList a=new ArrayList();

    public ArrayList getA() {
        return a;
    }

    public void setA(String string) {
        if(a==null){

            a.add("Maggi");
        }
        else{
            a.add(string);
        }}
}