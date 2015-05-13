package com.ceejay.challengetime.challenge;


import android.util.Log;

/**
 * Created by CJay on 24.04.2015 for Challenge Time.
 */
public class Comparator {
    public final static String TAG = Comparator.class.getSimpleName();

    private String comparable;
    private Challenge context;

    public Comparator(String comparable, Challenge context) {
        this.context = context;
        this.comparable = comparable;
    }

    public boolean compare(){
        Log.i(TAG,comparable+""+Parser.parse(Replacer.replace(comparable, context)));
        return Boolean.parseBoolean(Parser.parse(Replacer.replace(comparable,context)));
    }
}




