package com.ceejay.challengetime.generallys;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

import com.ceejay.challengetime.R;

/**
 * Created by CJay on 02.04.2015 for Challenge Time.
 */
public class Generallys extends Activity {
    public final static String TAG = Generallys.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.generallys);

        ListView list = (ListView)findViewById(R.id.generallys_list);
        GenerallysAdapter adapter = new GenerallysAdapter(this);
        list.setAdapter(adapter);


    }
}




