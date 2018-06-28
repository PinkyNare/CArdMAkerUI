package com.pakkalocal.cardmakerui.activities;

import android.app.WallpaperManager;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.bumptech.glide.util.Util;
import com.pakkalocal.cardmakerui.R;
import com.pakkalocal.cardmakerui.adapters.FramesEfectsAdapter;
import com.pakkalocal.cardmakerui.adapters.FrmaCropView;
import com.pakkalocal.cardmakerui.crop.CropImageActivity;
import com.pakkalocal.cardmakerui.utilsdata.ClipArt;
import com.pakkalocal.cardmakerui.utilsdata.RecyclerTouchListener;
import com.pakkalocal.cardmakerui.utilsdata.UtilsData;

import java.io.IOException;

public class EditingImageClipAct extends AppCompatActivity {
    RecyclerView _reclfrm_imgs;
    Toolbar toolbar_ed_img;
    FramesEfectsAdapter adapterframe;
    RelativeLayout rel_imgcrpadd, rel_mid;
    FrmaCropView frm_crp_vw;
    Bitmap bitmap;
    float bitheight, layheight, laywidth, bitwidth;
    int finalwidth, finalheight;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout._edit_img_act);
        _reclfrm_imgs = (RecyclerView) findViewById(R.id._reclfrm_imgs);

        toolbar_ed_img = (Toolbar) findViewById(R.id.toolbar_ed_img);
        setSupportActionBar(toolbar_ed_img);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar_ed_img.setTitle("EMI Calculator");
        toolbar_ed_img.setTitleTextColor(Color.WHITE);
        toolbar_ed_img.getOverflowIcon()
                .setColorFilter(Color.WHITE, PorterDuff.Mode.MULTIPLY);
        toolbar_ed_img.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                EditingImageClipAct.this.finish();

            }
        });

        rel_imgcrpadd = (RelativeLayout) findViewById(R.id.rel_imgcrpadd);

        rel_mid = (RelativeLayout) findViewById(R.id.rel_mid);


        RelativeLayout recparms = (RelativeLayout) findViewById(R.id.recparms);
        recparms.setLayoutParams(new RelativeLayout.LayoutParams(UtilsData.scwidth_u, UtilsData.scheight_u / 4));
        adapterframe = new FramesEfectsAdapter(this, UtilsData.img_acrpsd);
        adapterframe.setTypeFrames(3, UtilsData.img_acrpsd);
        adapterframe.notifyDataSetChanged();

        _reclfrm_imgs.setAdapter(adapterframe);
        _reclfrm_imgs.setHasFixedSize(true);
        _reclfrm_imgs.setLayoutManager(new StaggeredGridLayoutManager(1,
                StaggeredGridLayoutManager.HORIZONTAL));


        if (UtilsData.bfcropbmp != null) {

//mid
            ViewTreeObserver observer = rel_mid.getViewTreeObserver();
            observer.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {

                @Override
                public void onGlobalLayout() {
                    layheight = rel_mid.getMeasuredHeight();
                    laywidth = UtilsData.scwidth_u;
                    rel_mid.getViewTreeObserver().removeGlobalOnLayoutListener(this);

                    if (UtilsData.bfcropbmp == null)
                        return;
                    bitheight = UtilsData.bfcropbmp.getHeight();
                    bitwidth = UtilsData.bfcropbmp.getWidth();

                    float bitHeight = UtilsData.bfcropbmp.getHeight();
                    float bitWidth = UtilsData.bfcropbmp.getWidth();
                    if (bitHeight > bitWidth) {
                        float ratio = bitHeight / bitWidth;
                        rel_imgcrpadd.getLayoutParams().height = (int) layheight;
                        rel_imgcrpadd.getLayoutParams().width = (int) (layheight / ratio);
                        finalwidth = (int) (layheight / ratio);
                        finalheight = (int) (layheight);
                    } else if (bitHeight < bitWidth) {
                        float ratio = bitWidth / bitHeight;
                        rel_imgcrpadd.getLayoutParams().height = (int) (laywidth / ratio);
                        rel_imgcrpadd.getLayoutParams().width = (int) (laywidth);
                        finalheight = (int) (laywidth / ratio);
                        finalwidth = (int) (laywidth);
                    } else if (bitHeight == bitWidth) {
                        rel_imgcrpadd.getLayoutParams().height = (int) laywidth;
                        rel_imgcrpadd.getLayoutParams().width = (int) laywidth;
                        finalwidth = (int) (laywidth);
                        finalheight = (int) (laywidth);
                    }

                    rel_imgcrpadd.getLayoutParams().width = UtilsData.scwidth_u;

                    Bitmap bitmap = Bitmap.createScaledBitmap(UtilsData.bfcropbmp, UtilsData.scwidth_u, finalheight, true);
                    UtilsData.bfcropbmp = bitmap;

                    frm_crp_vw = new FrmaCropView(EditingImageClipAct.this, UtilsData.bfcropbmp, UtilsData.scwidth_u, finalheight);

                    frm_crp_vw.setFrameData(getBitamap_options(UtilsData.img_acrpsd[0]), getBitamap_options(UtilsData.img_acrpsd_mask[0]));
                    frm_crp_vw.invalidate();
                    rel_imgcrpadd.addView(frm_crp_vw);

                }
            });

            /*
            captureGlobalLayout(rel_imgcrpadd,
                    new ViewTreeObserver.OnGlobalLayoutListener() {
                        @Override
                        public void onGlobalLayout() {
                            int srcW = rel_imgcrpadd.getMeasuredWidth();
                            int srcH = rel_imgcrpadd.getMeasuredHeight();
                            float sx = srcW * 1f / UtilsData.bfcropbmp.getWidth() * 1f;
                            float sy = srcH * 1f / UtilsData.bfcropbmp.getHeight() * 1f;
                            float scale = Math.min(sx, sy);
                            int vw = (int) (UtilsData.bfcropbmp.getWidth() * scale);
                            int vh = (int) (UtilsData.bfcropbmp.getHeight() * scale);
                            rel_imgcrpadd.getLayoutParams().width = vw;
                            rel_imgcrpadd.getLayoutParams().height = vh;
                            frm_crp_vw = new FrmaCropView(EditingImageClipAct.this, UtilsData.bfcropbmp,srcW, srcH);
                            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(
                                    RelativeLayout.LayoutParams.MATCH_PARENT,
                                    RelativeLayout.LayoutParams.MATCH_PARENT);

                            frm_crp_vw.setFrameData(getBitamap_options(UtilsData.img_acrpsd[0]), getBitamap_options(UtilsData.img_acrpsd_mask[0]));
                            frm_crp_vw.invalidate();
                            rel_imgcrpadd.addView(frm_crp_vw,layoutParams);

                        }
                    });*/
        } else {
            finish();
        }


        _reclfrm_imgs.addOnItemTouchListener(new RecyclerTouchListener(
                getApplicationContext(), _reclfrm_imgs,
                new RecyclerTouchListener.RecyclerClick_Listener() {

                    @Override
                    public void onLongClick(View view, int position) {
                        // TODO Auto-generated method stub
                    }

                    @Override
                    public void onClick(View view, int position) {
                        // TODO Auto-generated method stub
                        frm_crp_vw.setFrameData(getBitamap_options(UtilsData.img_acrpsd[position]), getBitamap_options(UtilsData.img_acrpsd_mask[position]));
                        frm_crp_vw.invalidate();

                    }
                }));


    }

    public void captureGlobalLayout(@NonNull final View view,
                                    @NonNull final ViewTreeObserver.OnGlobalLayoutListener listener) {
        view.getViewTreeObserver().addOnGlobalLayoutListener(
                new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        final ViewTreeObserver viewTreeObserver = view
                                .getViewTreeObserver();
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                            viewTreeObserver.removeOnGlobalLayoutListener(this);
                        } else {
                            viewTreeObserver.removeGlobalOnLayoutListener(this);
                        }
                        listener.onGlobalLayout();
                    }
                });
    }

    private Bitmap getBitamap_options(int datai) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        Bitmap frmbitmapz;
        try {

            frmbitmapz = BitmapFactory.decodeResource(getResources(), datai);
        } catch (Exception e) {
            e.printStackTrace();
            try {
                options.inSampleSize = 2;
                frmbitmapz = BitmapFactory.decodeResource(getResources(), datai, options);
                options.inJustDecodeBounds = false;
            } catch (OutOfMemoryError er) {
                er.printStackTrace();
                options.inSampleSize = 4;
                frmbitmapz = BitmapFactory.decodeResource(getResources(), datai, options);
                options.inJustDecodeBounds = false;

            }
        }
        return frmbitmapz;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Drawable overflowIcon = toolbar_ed_img.getOverflowIcon();
        if (overflowIcon != null) {
            Drawable newIcon = overflowIcon.mutate();
            newIcon.setColorFilter(Color.WHITE, PorterDuff.Mode.MULTIPLY);
            toolbar_ed_img.setOverflowIcon(newIcon);
        }
        getMenuInflater().inflate(R.menu.crop_menu, menu);
        return true;
    }


    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // If the navigation drawer is open, hide action items related to the
        // content
        // view
        for (int i = 0; i < menu.size(); i++) {
            MenuItem menuItem = menu.getItem(i);
            CharSequence menuTitle = menuItem.getTitle();
            SpannableString styledMenuTitle = new SpannableString(menuTitle);
            styledMenuTitle.setSpan(new ForegroundColorSpan(Color.BLACK), 0,
                    menuTitle.length(), 0);
            menuItem.setTitle(styledMenuTitle);
        }

        return super.onPrepareOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.btn_done) {
            if (frm_crp_vw != null)
                bitmap = frm_crp_vw.getBitmap();
            if (bitmap != null) {

                ClipArt clipart_txt = new ClipArt(EditingImageClipAct.this,
                        bitmap);
                clipart_txt.setId(EditingActivity.activity.n++);

                RelativeLayout.LayoutParams parms = new RelativeLayout.LayoutParams((int) (UtilsData.scwidth_u / 2), (int) (UtilsData.scheight_u / 3));
                parms.leftMargin = (int) (UtilsData.scwidth_u  / 2.5f);
                parms.topMargin = (int) ( UtilsData.scheight_u / 4) + 10;
                clipart_txt.setLayoutParams(parms);

                clipart_txt.setParamsCustom(parms);
                clipart_txt.layoutParams = parms;


                EditingActivity.activity.save_rel_ver.addView(clipart_txt);
                EditingActivity.activity.save_rel_ver.invalidate();
                clipart_txt.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        EditingActivity.activity.disableall();
                    }
                });


            } else {

                Toast.makeText(this, "image does not exist", Toast.LENGTH_SHORT).show();
            }
            finish();
            /*Intent i = new Intent(EditingImageClipAct.this, EditingActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            i.putExtra("chk_hor_ver", getIntent().getExtras().getBoolean("chk_hor_ver", false));

            i.putExtra("frameselectdata", getIntent().getExtras().getInt("frameselectdata"));
            i.putExtra("frmposition", getIntent().getExtras().getInt("frmposition"));

            startActivity(i);*/

        }

        return super.onOptionsItemSelected(item);
    }


}
