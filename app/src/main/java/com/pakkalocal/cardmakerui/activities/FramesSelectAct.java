package com.pakkalocal.cardmakerui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.pakkalocal.cardmakerui.R;
import com.pakkalocal.cardmakerui.adapters.FramesEfectsAdapter;
import com.pakkalocal.cardmakerui.utilsdata.RecyclerTouchListener;
import com.pakkalocal.cardmakerui.utilsdata.UtilsData;

/**
 * Created by 2117 on 6/14/2018.
 */

public  class FramesSelectAct extends AppCompatActivity {


    ImageView img_vrtcl, img_hrzntl;

    RecyclerView recyvw_alfrms;
    FramesEfectsAdapter mAdapter;
    boolean chk_hor_ver = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.frame_select_act);
        UtilsData.ven_chk=0;
        UtilsData.nam1_tx = "";
        UtilsData.nam2_tx = "";
        UtilsData.date_tx = "";
        UtilsData.time_tx = "";
        UtilsData.venud_tx = "";
        recyvw_alfrms = (RecyclerView) findViewById(R.id.recyvw_alfrms);


        img_hrzntl = (ImageView) findViewById(R.id.img_hrzntl);
        img_vrtcl = (ImageView) findViewById(R.id.img_vrtcl);

        img_hrzntl.setLayoutParams(new RelativeLayout.LayoutParams(UtilsData.scwidth_u / 8, UtilsData.scwidth_u / 8));
        img_vrtcl.setLayoutParams(new RelativeLayout.LayoutParams(UtilsData.scwidth_u / 8, UtilsData.scwidth_u / 8));


        recyvw_alfrms.setHasFixedSize(true);
        recyvw_alfrms.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        mAdapter = new FramesEfectsAdapter(this, UtilsData.frams_allhordata);
        recyvw_alfrms.setAdapter(mAdapter);


        findViewById(R.id.lin_vrtcl).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chk_hor_ver = true;
                mAdapter.setTypeFrames(2, UtilsData.frams_allverdata);
                mAdapter.notifyDataSetChanged();

            }
        });
        findViewById(R.id.lin_horintl).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chk_hor_ver = false;
                mAdapter.setTypeFrames(1, UtilsData.frams_allhordata);
                mAdapter.notifyDataSetChanged();
            }
        });


        recyvw_alfrms.addOnItemTouchListener(new RecyclerTouchListener(this, recyvw_alfrms, new RecyclerTouchListener.RecyclerClick_Listener() {
            @Override
            public void onClick(View view, int position) {

                try {

                    int frmdata;
                    if (chk_hor_ver) {
                        frmdata = UtilsData.frams_allverdata[position];
                    } else {
                        frmdata = UtilsData.frams_allhordata[position];
                    }
                    Intent i = new Intent(FramesSelectAct.this, EditingActivity.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    i.putExtra("frameselectdata", frmdata);
                    i.putExtra("frmposition", position);
                    i.putExtra("chk_hor_ver", chk_hor_ver);
                    startActivity(i);

                } catch (ClassCastException e) {

                } catch (Exception e) {

                }
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));


    }


    @Override
    protected void onResume() {
        super.onResume();
        mAdapter.notifyDataSetChanged();
    }
}
