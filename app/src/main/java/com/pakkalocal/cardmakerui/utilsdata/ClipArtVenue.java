package com.pakkalocal.cardmakerui.utilsdata;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.ColorMatrixColorFilter;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.pakkalocal.cardmakerui.R;
import com.pakkalocal.cardmakerui.activities.EditingActivity;
import com.pakkalocal.cardmakerui.activities.VenueSettingsAct;

import java.util.ArrayList;

/**
 * Created by 2117 on 6/14/2018.
 */

public class ClipArtVenue extends RelativeLayout {

    int baseh;
    int basew;
    int basex;
    int basey;
    public ImageButton btndel;
    ImageButton btnrot;
    ImageButton btnscl;
    ImageButton btnedit;
    Context cntx;
    boolean freeze = false;
    int i;
    public ImageView image;
    ImageView imgring;
    RelativeLayout layBg;
    RelativeLayout layGroup;
    public LayoutParams layoutParams;
    public LayoutInflater mInflater;
    int margl;
    int margt;
    Bitmap originalBitmap;
    int pivx;
    int pivy;
    float startDegree;
    int width, height;


    //EditingActivity editing;
    @SuppressWarnings("deprecation")
    public ClipArtVenue(Context paramContext, Bitmap bitmap) {
        super(paramContext);
        cntx = paramContext;
        DisplayMetrics metrics = this.getResources().getDisplayMetrics();
        width = metrics.widthPixels;
        height = metrics.heightPixels;
        layGroup = this;
        basex = 0;
        basey = 0;
        pivx = 0;
        pivy = 0;


        mInflater = ((LayoutInflater) paramContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE));
        mInflater.inflate(R.layout.clipar_textvnue, this, true);
        btndel = ((ImageButton) findViewById(R.id.delv));
        btnrot = ((ImageButton) findViewById(R.id.rotatev));
        btnscl = ((ImageButton) findViewById(R.id.saclev));
        btnedit = ((ImageButton) findViewById(R.id.edit_clipv));

        imgring = ((ImageView) findViewById(R.id.imagev));


        btndel.getLayoutParams().height = width / 8;
        btndel.getLayoutParams().width = width / 8;
        btnrot.getLayoutParams().height = width / 9;
        btnrot.getLayoutParams().width = width / 9;
        btnscl.getLayoutParams().height = width / 9;
        btnscl.getLayoutParams().width = width / 9;

        btnedit.getLayoutParams().height = width / 9;
        btnedit.getLayoutParams().width = width / 9;


        layoutParams = new LayoutParams((int) (UtilsData.scwidth_u / 2), (int) (UtilsData.scwidth_u / 3));
        layGroup.setLayoutParams(layoutParams);


        image = ((ImageView) findViewById(R.id.clipartv));

        if (bitmap.getHeight() > 3000) {
            bitmap = bitmap.createScaledBitmap(bitmap, bitmap.getWidth(), bitmap.getHeight() - bitmap.getHeight() / 8, false);
        }
        Log.e("bitmap " + "==  "  ,bitmap.toString());

        image.setImageBitmap(bitmap);

        image.setTag(Integer.valueOf(getId()));

        setOnTouchListener(new OnTouchListener() {

            final GestureDetector gestureDetector = new GestureDetector(cntx,
                    new GestureDetector.SimpleOnGestureListener() {
                        public boolean onDoubleTap(MotionEvent paramAnonymous2MotionEvent) {

                            return true;
                        }
                    });

            public boolean onTouch(View paramAnonymousView, MotionEvent event) {
                ClipArtVenue.this.visiball();


                Log.e("count ", "==  " + EditingActivity.activity.save_rel_ver.getChildCount());

                UtilsData.textPos = paramAnonymousView.getId();
                Log.e("textPos ", "==  " + UtilsData.textPos);

                image.setTag(Integer.valueOf(paramAnonymousView.getId()));
                if (!ClipArtVenue.this.freeze) {
                    switch (event.getAction()) {
                        case MotionEvent.ACTION_DOWN:
                            layGroup.invalidate();
                            layGroup.bringToFront();

                            layGroup.performClick();
                            basex = ((int) (event.getRawX() - layoutParams.leftMargin));
                            basey = ((int) (event.getRawY() - layoutParams.topMargin));
                            break;

                        case MotionEvent.ACTION_MOVE:
                            int i = (int) event.getRawX();
                            int j = (int) event.getRawY();
                            layBg = (RelativeLayout) (getParent());
                            if ((i - basex > -(layGroup.getWidth() * 2 / 3))
                                    && (i - basex < layBg.getWidth() - layGroup.getWidth() / 3)) {
                                layoutParams.leftMargin = (i - basex);
                            }
                            if ((j - basey > -(layGroup.getHeight() * 2 / 3))
                                    && (j - basey < layBg.getHeight() - layGroup.getHeight() / 3)) {
                                layoutParams.topMargin = (j - basey);
                            }
                            layoutParams.rightMargin = -9999999;
                            layoutParams.bottomMargin = -9999999;
                            layGroup.setLayoutParams(layoutParams);
                            break;

                    }
                    this.gestureDetector.onTouchEvent(event);
                    return true;
                }

                return true;
            }
        });

        this.btnscl.setOnTouchListener(new OnTouchListener() {
            @SuppressLint({"NewApi"})
            public boolean onTouch(View paramAnonymousView, MotionEvent event) {
                if (!ClipArtVenue.this.freeze) {
                    int j = (int) event.getRawX();
                    int i = (int) event.getRawY();
                    layoutParams = (LayoutParams) layGroup.getLayoutParams();
                    switch (event.getAction()) {

                        case MotionEvent.ACTION_DOWN:
                            layGroup.invalidate();
                            basex = j;
                            basey = i;
                            basew = layGroup.getWidth();
                            baseh = layGroup.getHeight();
                            int[] loaction = new int[2];
                            layGroup.getLocationOnScreen(loaction);
                            margl = layoutParams.leftMargin;
                            margt = layoutParams.topMargin;
                            break;
                        case MotionEvent.ACTION_MOVE:

                            float f2 = (float) Math.toDegrees(Math.atan2(i - ClipArtVenue.this.basey, j - basex));
                            float f1 = f2;
                            if (f2 < 0.0F) {
                                f1 = f2 + 360.0F;
                            }
                            j -= basex;
                            int k = i - basey;
                            i = (int) (Math.sqrt(j * j + k * k) * Math.cos(Math.toRadians(f1
                                    - layGroup.getRotation())));
                            j = (int) (Math.sqrt(i * i + k * k) * Math.sin(Math.toRadians(f1
                                    - layGroup.getRotation())));
                            k = i * 2 + basew;
                            int m = j * 2 + baseh;
                            if (k > 150) {
                                layoutParams.width = k;
                                layoutParams.leftMargin = (margl - i);
                            }
                            if (m > 150) {
                                layoutParams.height = m;
                                layoutParams.topMargin = (margt - j);
                            }
                            layGroup.setLayoutParams(layoutParams);
                            layGroup.performLongClick();
                            break;


                        case MotionEvent.ACTION_UP:
                            disableAll();
                    }
                    return true;

                }
                return freeze;
            }
        });
        this.btnrot.setOnTouchListener(new OnTouchListener() {
            @SuppressLint({"NewApi"})
            public boolean onTouch(View paramAnonymousView, MotionEvent event) {
                if (!freeze) {
                    layoutParams = (LayoutParams) layGroup.getLayoutParams();
                    layBg = ((RelativeLayout) getParent());
                    int[] arrayOfInt = new int[2];
                    layBg.getLocationOnScreen(arrayOfInt);
                    int i = (int) event.getRawX() - arrayOfInt[0];
                    int j = (int) event.getRawY() - arrayOfInt[1];
                    switch (event.getAction()) {

                        case MotionEvent.ACTION_DOWN:
                            layGroup.invalidate();
                            startDegree = layGroup.getRotation();
                            pivx = (layoutParams.leftMargin + getWidth() / 2);
                            pivy = (layoutParams.topMargin + getHeight() / 2);
                            basex = (i - pivx);
                            basey = (pivy - j);
                            break;

                        case MotionEvent.ACTION_MOVE:
                            int k = pivx;
                            int m = pivy;
                            j = (int) (Math.toDegrees(Math.atan2(basey, basex)) - Math
                                    .toDegrees(Math.atan2(m - j, i - k)));
                            i = j;
                            if (j < 0) {
                                i = j + 360;
                            }
                            layGroup.setRotation((startDegree + i) % 360.0F);
                            break;

                        case MotionEvent.ACTION_UP:
                            disableAll();
                    }

                    return true;
                }
                return freeze;
            }
        });


        this.btnedit.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {

                UtilsData.ven_chk = 1;
                Intent venuintnt = new Intent(cntx, VenueSettingsAct.class);
//                venuintnt.putExtra("chk_hor_ver", chk_ver_hor);
//                venuintnt.putExtra("frameselectdata", frmdata);
//                venuintnt.putExtra("frmposition", frmdatapos);
                        cntx.startActivity(venuintnt);
            }
        });


        this.btndel.setOnClickListener(new OnClickListener() {
            public void onClick(View paramAnonymousView) {
                if (!freeze) {

                    layBg = ((RelativeLayout) getParent());
                    layBg.performClick();
                    layBg.removeView(layGroup);

                }
            }
        });
    }


    public void removeviews(ArrayList<ClipArtVenue> views) {

        for (int i = 0; i < views.size(); i++) {
            layBg = (RelativeLayout) getParent();
            layBg.performClick();
            layBg.removeView(views.get(i));
        }


    }

    public void disableAll() {
        this.btndel.setVisibility(View.INVISIBLE);
        this.btnrot.setVisibility(View.INVISIBLE);
        this.btnscl.setVisibility(View.INVISIBLE);
        this.btnedit.setVisibility(INVISIBLE);

        this.imgring.setVisibility(View.INVISIBLE);

    }

    public ImageView getImageView() {
        return this.image;
    }

    @SuppressLint("NewApi")
    public float getOpacity() {
        return this.image.getAlpha();
    }

    public void resetImage() {
        this.originalBitmap = null;
        this.layGroup.performLongClick();
    }

    public void setColor(int paramInt) {
        this.image.getDrawable().setColorFilter(null);
        ColorMatrixColorFilter localColorMatrixColorFilter = new ColorMatrixColorFilter(new float[]{0.33F, 0.33F,
                0.33F, 0.0F, Color.red(paramInt), 0.33F, 0.33F, 0.33F, 0.0F, Color.green(paramInt), 0.33F, 0.33F,
                0.33F, 0.0F, Color.blue(paramInt), 0.0F, 0.0F, 0.0F, 1.0F, 0.0F});
        this.image.getDrawable().setColorFilter(localColorMatrixColorFilter);
        this.image.setTag(Integer.valueOf(paramInt));
        this.layGroup.performLongClick();
    }

    public void setFreeze(boolean paramBoolean) {
        this.freeze = paramBoolean;
    }

    public void setImageId() {
        this.image.setId(this.layGroup.getId() + this.i);
        this.i += 1;
    }

    public void setLocation() {
        this.layBg = ((RelativeLayout) getParent());
        LayoutParams localLayoutParams = (LayoutParams) this.layGroup.getLayoutParams();
        localLayoutParams.topMargin = ((int) (Math.random() * (this.layBg.getHeight() - 400)));
        localLayoutParams.leftMargin = ((int) (Math.random() * (this.layBg.getWidth() - 400)));
        this.layGroup.setLayoutParams(localLayoutParams);
    }

    public void visiball() {
        this.btndel.setVisibility(View.VISIBLE);
        this.btnrot.setVisibility(View.VISIBLE);
        this.btnscl.setVisibility(View.VISIBLE);
        this.btnedit.setVisibility(VISIBLE);
        this.imgring.setVisibility(View.VISIBLE);
    }

    public void setParamsCustom(LayoutParams parms) {
        layoutParams =parms;
//        layGroup.setLayoutParams(layoutParams);
        invalidate();

    }

    public static abstract interface DoubleTapListener {
        public abstract void onDoubleTap();
    }
}
