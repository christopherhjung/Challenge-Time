package com.ceejay.challengetime.challenge;

import android.location.Location;

import com.ceejay.challengetime.R;
import com.ceejay.challengetime.Transferor;
import com.ceejay.challengetime.helper.LatLngConvert;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.PolylineOptions;


/**
 * Created by CJay on 25.01.2015 for Challenge Time.
 *
 */
public class RunChallenge extends Challenge {

    Location startLocation;
    Location stopLocation;
    PolylineOptions track;

    private Circle startArea;
    private Circle stopArea;

    public RunChallenge( LatLng location , PolylineOptions track ) {
        super(location);
        this.track = track;
        startLocation = LatLngConvert.toLocation(track.getPoints().get(0), "Start");
        stopLocation = LatLngConvert.toLocation(track.getPoints().get(track.getPoints().size()-1),"Stop");
    }

    @Override
    public void focus() {
        super.focus();
        startArea = Transferor.mapManager.addArea(startLocation, sizeStartArea,context.getResources().getColor(R.color.notstarted));
        stopArea = Transferor.mapManager.addArea(stopLocation, sizeStopArea, context.getResources().getColor(R.color.notfinished));
        Transferor.mapManager.addPolyline(track);
    }

    @Override
    public void activate() {
        super.activate();
        if(isActivated){
            startArea.setFillColor(context.getResources().getColor(R.color.activated));
        }
    }

    @Override
    protected void start() {
        super.start();
        startArea.setFillColor(context.getResources().getColor(R.color.started));
    }

    @Override
    public void finish() {
        if( userLocation.distanceTo(stopLocation) < sizeStopArea ) {
            stopArea.setFillColor(context.getResources().getColor(R.color.finished));
            super.finish();
        }
    }
}




