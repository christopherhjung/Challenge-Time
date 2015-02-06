package com.ceejay.challengetime;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import android.widget.Button;

import com.ceejay.challengetime.challenge.Challenge;
import com.ceejay.challengetime.challenge.ChallengeAdapter;
import com.ceejay.challengetime.helper.Transferor;
import com.ceejay.challengetime.slider.Slider;
import com.ceejay.challengetime.slider.SliderAdapter;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;

public class MainActivity extends FragmentActivity {
    public static final String TAG = MainActivity.class.getSimpleName();

    private GoogleMap googleMap;
    public Slider slider;
    public Button button;
    public SliderAdapter sliderAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Transferor.context = this;
        Challenge.setContext(this);
        setContentView(R.layout.activity_maps);

        slider = (Slider) findViewById(R.id.slidingDrawer);
        button = (Button) findViewById(R.id.start);

        sliderAdapter = new SliderAdapter( this , slider );
        sliderAdapter.addButton( button );

        setUpMapIfNeeded();
    }



    @Override
    protected void onResume() {
        super.onResume();
        setUpMapIfNeeded();
    }

    @Override
    public void onBackPressed() {
        if(Challenge.isActivated || Challenge.isStarted) {
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
            alertDialog.setMessage("Möchtest du die Challenge abbrechen?");
            alertDialog.setPositiveButton("OK",new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Challenge.getFocus().stop();
                    Challenge.setFocus(null);
                    ChallengeAdapter.getMapManager().refreshMarker();
                }
            });
            alertDialog.setNegativeButton("Cancel",new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                }
            });
            alertDialog.show();
        }else{
            Challenge.setFocus(null);
            if( ChallengeAdapter.getMapManager() != null ) {
                ChallengeAdapter.getMapManager().refreshMarker();
            }
        }
    }

    private void setUpMapIfNeeded() {

        if (googleMap == null) {

            googleMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map)).getMap();

            if (googleMap != null) {
                ChallengeAdapter.setMapManager( new MapManager( this , googleMap ));
            }
        }
    }
}

