package com.zaid.tashilulquran;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Environment;

import java.util.HashMap;

public class Common {
    private static HashMap<String, Byte> pages = new HashMap<>();
    public static boolean empty = true;
    public static final String resumeSP="syedZaidHusain";

    public static void init() {

        if (Common.empty) {
            pages.put("p1", (byte) 25);
            pages.put("p2", (byte) 23);
            pages.put("p3", (byte) 24);
            pages.put("p4", (byte) 23);
            pages.put("p5", (byte) 23);
            pages.put("p6", (byte) 24);
            pages.put("p7", (byte) 26);
            pages.put("p8", (byte) 24);
            pages.put("p9", (byte) 23);
            pages.put("p10", (byte) 22);
            pages.put("p11", (byte) 24);
            pages.put("p12", (byte) 24);
            pages.put("p13", (byte) 24);
            pages.put("p14", (byte) 22);
            pages.put("p15", (byte) 24);
            pages.put("p16", (byte) 25);
            pages.put("p17", (byte) 23);
            pages.put("p18", (byte) 26);
            pages.put("p19", (byte) 26);
            pages.put("p20", (byte) 23);
            pages.put("p21", (byte) 24);
            pages.put("p22", (byte) 24);
            pages.put("p23", (byte) 26);
            pages.put("p24", (byte) 23);
            pages.put("p25", (byte) 25);
            pages.put("p26", (byte) 24);
            pages.put("p27", (byte) 25);
            pages.put("p28", (byte) 24);
            pages.put("p29", (byte) 26);
            pages.put("p30", (byte) 38);
            Common.empty = false;
        }

    }

    public static byte getPages(String s) {
        if (pages != null && pages.containsKey(s)) {
            return pages.get(s);
        } else {
            // handle the case where pages is null or s is not in the map
            return 0;
        }
    }

    public static void setResume(Context context,String para_name,int page_no){
        SharedPreferences sp=context.getSharedPreferences(resumeSP,Context.MODE_PRIVATE);
        SharedPreferences.Editor ed= sp.edit();
        ed.putString("para_name",para_name);
        ed.putInt("page_no",page_no);
        ed.apply();
    }
    public static String getResumeParaName(Context context){
        SharedPreferences sp=context.getSharedPreferences(resumeSP,Context.MODE_PRIVATE);
        return sp.getString("para_name","p1");


    }public static int getResumePageNo(Context context){
        SharedPreferences sp=context.getSharedPreferences(resumeSP,Context.MODE_PRIVATE);
        return sp.getInt("page_no",-1);

    }
    public static String getDownDir() {
        return Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
                + "/Tashilul Quran/para";
    }
    public static void mShareText(String text, Activity activity) {
        Intent myapp = new Intent(Intent.ACTION_SEND);
        myapp.setType("text/plain");
        myapp.putExtra(Intent.EXTRA_TEXT, text);
        activity.startActivity(myapp);
    }

}
