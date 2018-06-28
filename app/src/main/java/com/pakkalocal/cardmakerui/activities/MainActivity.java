package com.pakkalocal.cardmakerui.activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.pakkalocal.cardmakerui.R;
import com.pakkalocal.cardmakerui.animause.AnimateDrawable;
import com.pakkalocal.cardmakerui.utilsdata.ConnectivityReceiver;
import com.pakkalocal.cardmakerui.utilsdata.UtilsData;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity implements ConnectivityReceiver.ConnectivityReceiverListener {

    Button startbtn;
    SnowFallView snowFallView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DisplayMetrics displaymetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        UtilsData.scheight_u = displaymetrics.heightPixels;
        UtilsData.scwidth_u = displaymetrics.widthPixels;
        UtilsData.isinternet = ConnectivityReceiver.isConnected();


        startbtn = (Button) findViewById(R.id.startbtn);
        ViewGroup.MarginLayoutParams mrgparms = (ViewGroup.MarginLayoutParams) startbtn.getLayoutParams();
        mrgparms.bottomMargin = UtilsData.scheight_u / 6;
        mrgparms.topMargin = UtilsData.scheight_u / 12;

        RelativeLayout rel_snowvw = (RelativeLayout) findViewById(R.id.rel_snowvw);


        snowFallView = new SnowFallView(this);

        snowFallView.setStop_Start(true);
        rel_snowvw.addView(snowFallView);
//        snowFallView.setBackgroundDrawable(getResources().getDrawable(R.drawable.baby8));
       /* startbtn.setShadowLayer(
                1.5f, // radius
                5.0f, // dx
                5.0f, // dy
                getResources().getColor(R.color.shodowclr));*/

        startbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                snowFallView.setStop_Start(false);
                snowFallView.invalidate();
                startActivity(new Intent(MainActivity.this,FramesSelectAct.class));

            }
        });


    }

    @Override
    public void onNetworkConnectionChanged(boolean isConnected) {
        UtilsData.isinternet = isConnected;

        /*if (isConnected) {
            displayAd();
            if (backApps != null && backApps.size() >= 4 && Utils.lastAppList != null) {
            } else {
                xmlCount = 0;
                backApps = new ArrayList<App>();
                Utils.lastAppList = new ArrayList<>();
                Utils.updateAppsList = new ArrayList<>();
                xmlParsingUsingVolley(exitXml);

            }
        }*/

    }

    @Override
    protected void onResume() {
        super.onResume();
        snowFallView.setStop_Start(true);
        snowFallView.invalidate();

    }

    private class SnowFallView extends View {
        private int snow_flake_count = 10;
        private final List<Drawable> drawables = new ArrayList<Drawable>();
        private int[][] coords;
        private final Drawable snow_flake;
        boolean chk;

        public SnowFallView(Context context) {
            super(context);
            setFocusable(true);
            setFocusableInTouchMode(true);
            snow_flake = context.getResources().getDrawable(R.drawable.draw_img_4);
            snow_flake.setBounds(0, 0, snow_flake.getIntrinsicWidth(), snow_flake
                    .getIntrinsicHeight());


        }

        @Override
        protected void onSizeChanged(int width, int height, int oldw, int oldh) {
            super.onSizeChanged(width, height, oldw, oldh);
            Random random = new Random();
            Interpolator interpolator = new LinearInterpolator();
            snow_flake_count = Math.max(width, height) / 10;
            coords = new int[snow_flake_count][];
            drawables.clear();
            for (int i = 0; i < snow_flake_count; i++) {
//                Log.e("sizeChange", " ang width = " + width + " the height = " + height);
                Animation animation = new TranslateAnimation(0, height / 10
                        - random.nextInt(height / 5), 0, height + 30);
                animation.setDuration(10 * height + random.nextInt(5 * height));
                animation.setRepeatCount(-1);
                animation.initialize(10, 10, 10, 10);
                animation.setInterpolator(interpolator);

                coords[i] = new int[]{random.nextInt(width - 30), -30};

                drawables.add(new AnimateDrawable(snow_flake, animation));
                animation.setStartOffset(random.nextInt(20 * height));
                animation.startNow();
            }
        }

        @Override
        protected void onDraw(Canvas canvas) {
            if(chk)
            {
                for (int i = 0; i < snow_flake_count; i++) {
                Drawable drawable = drawables.get(i);
                canvas.save();
                canvas.translate(coords[i][0], coords[i][1]);
                drawable.draw(canvas);
                canvas.restore();
                Log.e("sizeChange", " ang width = " + coords[i][0] + " the height = " + coords[i][0]);
            }
                invalidate();

            }

        }

        public void setStop_Start(boolean b) {
            chk=b;
        }
    }

}
