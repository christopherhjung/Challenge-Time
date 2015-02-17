package com.ceejay.challengetime.helper.slider;

import android.content.Context;
import android.graphics.Point;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.ceejay.challengetime.R;

import java.util.ArrayList;

/**
 * Created by CJay on 06.02.2015 for Challenge Time.
 *
 */
public class SliderAdapter implements Slider.PanelSlideListener,View.OnTouchListener{
    public final static String TAG = SliderAdapter.class.getSimpleName();

    public Context context;
    public Slider slider;
    public ArrayList<Attacher> attachers = new ArrayList<>();

    public SliderAdapter(Context context, Slider slider) {
        this.context = context;
        this.slider = slider;
        slider.setPanelSlideListener(this);
        slider.setOnTouchListener(this);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return false;
    }

    @Override
    public void onPanelSlide(View panel, float slideOffset) {
        setUpButtons(panel.getTop());
    }

    @Override
    public void onPanelCollapsed(View panel) {}

    @Override
    public void onPanelExpanded(View panel) {}

    @Override
    public void onPanelAnchored(View panel) {
    }

    @Override
    public void onPanelHidden(View panel) {}

    public void attachButton( OptionButton button , Point offset ){
        if ( attachers != null && offset != null && button != null ) {
            ((ViewGroup)slider.getParent()).addView(button);
            attachers.add(new Attacher(button,offset));
            setUpButtons(1900 - (int) context.getResources().getDimension(R.dimen.panel_size));
        }
    }

    public void setUpButtons( int offset ){
        for ( Attacher attacher : attachers ) {
            ((ViewGroup.MarginLayoutParams) attacher.getView().getLayoutParams()).setMargins(attacher.getOffset().x,offset - attacher.getOffset().y,0,0);
            attacher.getView().requestLayout();
        }
    }

    protected class Attacher{

        private View view;
        private Point offset;

        public Attacher(View view, Point offset) {
            this.view = view;
            this.offset = offset;
        }

        public View getView() {
            return view;
        }

        public void setView(OptionButton optionButton) {
            this.view = optionButton;
        }

        public Point getOffset() {
            return offset;
        }

        public void setOffset(Point offset) {
            this.offset = offset;
        }
    }

}




