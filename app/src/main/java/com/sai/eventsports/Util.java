package com.sai.eventsports;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.util.Log;
import android.widget.EditText;

import java.io.IOException;
import java.lang.reflect.Array;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.TimeZone;

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
    public static String[] cogerHora() {
        Timestamp stamp = new Timestamp(System.currentTimeMillis());
        Date date = new Date(stamp.getTime());
        SimpleDateFormat sdf = new SimpleDateFormat("h:mm a");
        sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
        String formattedDate = sdf.format(date);
        String timeInMiliseconds = String.valueOf(stamp.getTime());
        String[] array = {formattedDate, timeInMiliseconds};
        return array;
    }
}
