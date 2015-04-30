package com.ceejay.challengetime.challenge;

import android.util.Log;

import com.ceejay.challengetime.geo.LocationObserver;
import com.ceejay.challengetime.helper.Distance;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by CJay on 24.04.2015 for Challenge Time.
 */
public class Comparator {
    public final static String TAG = Comparator.class.getSimpleName();

    private String comparable;
    private int comparator;
    private String first , second;
    private String firstType = "undefined" , secondType = "undefined";
    private Challenge context;
    private Pattern pattern = Pattern.compile("(?:(\\S+)#)?(\\S+)\\s*(<|>|=|>=|<=|->|<-])\\s*(?:(\\S+)#)?(\\S+)");

    public Comparator(String comparable, Challenge context) {
        this.context = context;
        this.comparable = comparable;

        Matcher m = pattern.matcher(comparable);
        if(m.find()){
            this.first = m.group(2);
            this.second = m.group(5);
            this.firstType = m.group(1);
            this.secondType = m.group(4);

            if(firstType == null){
                if(first.equals("true") || first.equals("false")){
                    firstType = "boolean";
                }else{
                    Matcher mN = PatternType.number.matcher(first);
                    if(mN.find()){
                        firstType = "number";
                    }
                }
            }

            if(secondType == null){
                if(second.equals("true") || second.equals("false")){
                    secondType = "boolean";
                }else{
                    Matcher mN = PatternType.number.matcher(second);
                    if(mN.find()){
                        secondType = "number";
                    }
                }
            }

            switch(m.group(3)){
                case "->": this.comparator = 3; break;
                case ">=": this.comparator = 2; break;
                case ">": this.comparator = 1; break;
                case "=": this.comparator = 0; break;
                case "<": this.comparator = -1; break;
                case "<=": this.comparator = -2; break;
                case "<-": this.comparator = -3; break;
            }
        }
    }

    public boolean compare(){
        if( firstType != null && secondType != null ) {

            int result = 10;
            if (firstType.equals("number") && secondType.equals("number")) {
                result =  Integer.valueOf(first).compareTo(Integer.valueOf(second));
            } else if (firstType.equals("number") && secondType.equals("int")) {
                result =  Integer.valueOf(first).compareTo(context.getInteger(second));
            } else if (firstType.equals("int") && secondType.equals("number")) {
                result = context.getInteger(first).compareTo(Integer.valueOf(second));
            } else if (firstType.equals("int") && secondType.equals("int")) {
                result = context.getInteger(first).compareTo(context.getInteger(second));
            } else if (firstType.equals("bool") && secondType.equals("boolean")) {
                result = context.getBooleanHolder(first).getValue().compareTo(Boolean.parseBoolean(second));
            } else if (firstType.equals("area") && secondType.equals("user")) {
                if(LocationObserver.location == null){
                    return false;
                }
                if( this.comparator == 3 ){
                    return Distance.between( LocationObserver.location , context.getArea(second).position ) >= context.getArea(second).radius;
                }else if( this.comparator == -3 ){
                    return Distance.between( LocationObserver.location , context.getArea(second).position ) < context.getArea(second).radius;
                }
            } else if (firstType.equals("user") && secondType.equals("area")) {
                if(LocationObserver.location == null){
                    return false;
                }
                if( this.comparator == 3 ){
                    return Distance.between( LocationObserver.location , context.getArea(second).position ) < context.getArea(second).radius;
                }else if( this.comparator == -3 ){
                    return Distance.between( LocationObserver.location , context.getArea(second).position ) >= context.getArea(second).radius;
                }
            } else if (firstType.equals("timer") && secondType.equals("number")){
                result = Long.valueOf(context.getTimer(first).getTime()).compareTo(Long.parseLong(second));
            } else {
                return comparable.equals("true");
            }

            return comparator == result || ( comparator == 2 && ( result == 1 || result == 0 ) ) || ( comparator == -2 && ( result == -1 || result == 0 ));

        }else{
            Log.i(TAG,"FirstType oder SecondType is null");
        }

        return false;
    }

}



