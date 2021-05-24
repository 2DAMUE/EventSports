package com.sai.eventsports;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.util.Log;
import android.widget.EditText;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class Util {
    public static String chageName(String email) {
        String[] array = email.split("@");
        return array[0];
    }

    public static double[] takeCoordenadas(Context context, String direc, String num, String loca) {
        double[] coords = new double[2];
        String total = direc + "," + num + "," + loca;
        Geocoder coder = new Geocoder(context);
        try {
            ArrayList<Address> adresses = (ArrayList<Address>)
                    coder.getFromLocationName(total, 50);
            for(Address add : adresses){
                coords[0] = add.getLatitude();
                coords[1] = add.getLongitude();
                //Log.d("Coordenadas"," " + coords[0] + " " + coords[1]);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return coords;
    }
}
