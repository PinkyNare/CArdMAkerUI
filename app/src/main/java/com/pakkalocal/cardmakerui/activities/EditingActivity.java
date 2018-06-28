package com.pakkalocal.cardmakerui.activities;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.SpannableString;
import android.text.TextWatcher;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.pakkalocal.cardmakerui.R;
import com.pakkalocal.cardmakerui.adapters.FramesEfectsAdapter;
import com.pakkalocal.cardmakerui.coloruse.ColorPickerDialog;
import com.pakkalocal.cardmakerui.crop.CropImageActivity;
import com.pakkalocal.cardmakerui.crop.CropUtil;
import com.pakkalocal.cardmakerui.utilsdata.ClipArt;
import com.pakkalocal.cardmakerui.utilsdata.RecyclerTouchListener;
import com.pakkalocal.cardmakerui.utilsdata.UtilsData;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import static android.view.Gravity.CENTER;

/**
 * Created by 2117 on 6/14/2018.
 */

public class EditingActivity extends AppCompatActivity implements View.OnClickListener {


    public RelativeLayout saved_rellay, save_rel_ver, rel_btnparms;
    LinearLayout lin_cards, lin_addimg, lin_stickrs, lin_txtsetings, lin_text;
    TextView txt_card, txt_add_img, txt_stkr, txt_set_txt, txt_txt;
    ImageView img_card, img_add_img, img_stkr, img_set_txt, img_txt;
    ImageView userImagever;
    int[] frams_data = null;
    Toolbar toolbar_edit;
    FramesEfectsAdapter adapterframe;


    RelativeLayout rel_option_lay, textDialogLinear;
    RecyclerView stkrs_recyclvw, cards_recyclvw;
    AlertDialog.Builder opencameragallery;


    boolean chk_ver_hor;
    int frmdata, frmdatapos;
    AlertDialog alert;
    Uri outputFileUri;
    public static File sdImageMainDirectory;
    int camera_ReqCode = 121, gallery_ReqCode = 212;
    //  text_lay
    int n = 1;
    ClipArt clipart_txt;
    RelativeLayout text_innerlay;
    RelativeLayout opt_conf_lay, txtCancel_lay, txtDone_lay;
    RelativeLayout opt_edittext_lay;
    EditText txeditText;
    LinearLayout opt_txt_style_lay;
    RelativeLayout txt_bold_lay, txt_normal_lay, txt_italic_lay;
    TextView txt_bold, txt_normal, txt_italic;


    HorizontalScrollView font_scrollView, color_scrollView;
    LinearLayout font_scrolllinear, color_scrolllinear;

    RelativeLayout colorPicker;
    int colorr;
    Typeface savedTypeface;
    int savedColor, selectedfontstyle;


    public static EditingActivity activity;
    Context context;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_act);

        saved_rellay = (RelativeLayout) findViewById(R.id.saved_rellay);
        save_rel_ver = (RelativeLayout) findViewById(R.id.save_rel_ver);
        userImagever = (ImageView) findViewById(R.id.userImagever);

        context = this;
        activity = this;
        saved_rellay.getLayoutParams().height = (UtilsData.scheight_u * 95) / 100;

        RelativeLayout rel_m_hrzntl = (RelativeLayout) findViewById(R.id.rel_m_hrzntl);
        RelativeLayout rel_m_vertcl = (RelativeLayout) findViewById(R.id.rel_m_vertcl);

        chk_ver_hor = getIntent().getExtras().getBoolean("chk_hor_ver", false);

        if (chk_ver_hor) {
            frams_data = UtilsData.frams_allverdata;
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            rel_m_hrzntl.setVisibility(View.GONE);
            rel_m_vertcl.setVisibility(View.VISIBLE);
        } else {
            frams_data = UtilsData.frams_allhordata;
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
            rel_m_hrzntl.setVisibility(View.VISIBLE);
            rel_m_vertcl.setVisibility(View.GONE);
        }

        frmdatapos = getIntent().getExtras().getInt("frmposition");
        frmdata = getIntent().getExtras().getInt("frameselectdata");

        initIDs_Data();


        if (getIntent().getExtras().getInt("frameselectdata") != 0)
            userImagever.setImageResource(getIntent().getExtras().getInt("frameselectdata"));

        save_rel_ver.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                disableall();
                return false;
            }
        });

    }

    private void initIDs_Data() {
        toolbar_edit = (Toolbar) findViewById(R.id.toolbar_edit);
        setSupportActionBar(toolbar_edit);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar_edit.setTitle("Edit");
        toolbar_edit.setTitleTextColor(Color.WHITE);
        toolbar_edit.getOverflowIcon()
                .setColorFilter(Color.WHITE, PorterDuff.Mode.MULTIPLY);
        toolbar_edit.setNavigationOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                exitDialog();

            }
        });

//        rel_btm_btns = (RelativeLayout) findViewById(R.id.rel_btm_btns);
//        rel_btm_btns.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        rel_btnparms = (RelativeLayout) findViewById(R.id.rel_btnparms);
        rel_btnparms.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, UtilsData.scheight_u / 8));


        lin_cards = (LinearLayout) findViewById(R.id.lin_cards);
        lin_addimg = (LinearLayout) findViewById(R.id.lin_addimg);
        lin_stickrs = (LinearLayout) findViewById(R.id.lin_stickrs);
        lin_txtsetings = (LinearLayout) findViewById(R.id.lin_txtsetings);
        lin_text = (LinearLayout) findViewById(R.id.lin_text);

        lin_cards.setOnClickListener(this);
        lin_addimg.setOnClickListener(this);
        lin_stickrs.setOnClickListener(this);
        lin_txtsetings.setOnClickListener(this);
        lin_text.setOnClickListener(this);


        txt_card = (TextView) findViewById(R.id.txt_card);
        img_card = (ImageView) findViewById(R.id.img_card);

        txt_add_img = (TextView) findViewById(R.id.txt_add_img);
        img_add_img = (ImageView) findViewById(R.id.img_add_img);


        txt_stkr = (TextView) findViewById(R.id.txt_stkr);
        img_stkr = (ImageView) findViewById(R.id.img_stkr);


        txt_set_txt = (TextView) findViewById(R.id.txt_set_txt);
        img_set_txt = (ImageView) findViewById(R.id.img_set_txt);


        txt_txt = (TextView) findViewById(R.id.txt_txt);
        img_txt = (ImageView) findViewById(R.id.img_txt);

        rel_option_lay = (RelativeLayout) findViewById(R.id.rel_option_lay);
        rel_option_lay.setVisibility(View.GONE);


        cards_recyclvw = (RecyclerView) findViewById(R.id.cards_recyclvw);
        stkrs_recyclvw = (RecyclerView) findViewById(R.id.stkrs_recyclvw);
        textDialogLinear = (RelativeLayout) findViewById(R.id.textDialogLinear);


        File rootFile = new File(Environment.getExternalStorageDirectory()
                + File.separator);
        sdImageMainDirectory = new File(rootFile, "frame.jpg");


        dataFor_Text();

        dataFor_cards();

        dataFor_Stickers();


    }

    private void dataFor_cards() {

        adapterframe = new FramesEfectsAdapter(this, frams_data);
        adapterframe.setTypeFrames(3, frams_data);
        adapterframe.notifyDataSetChanged();

        cards_recyclvw.setAdapter(adapterframe);
        cards_recyclvw.setHasFixedSize(true);
        cards_recyclvw.setLayoutManager(new StaggeredGridLayoutManager(1,
                StaggeredGridLayoutManager.HORIZONTAL));

        cards_recyclvw.addOnItemTouchListener(new RecyclerTouchListener(
                getApplicationContext(), cards_recyclvw,
                new RecyclerTouchListener.RecyclerClick_Listener() {

                    @Override
                    public void onLongClick(View view, int position) {
                        // TODO Auto-generated method stub
                    }

                    @Override
                    public void onClick(View view, int position) {
                        // TODO Auto-generated method stub
                        userImagever.setImageResource(frams_data[position]);
                        frmdata = frams_data[position];
                        frmdatapos = position;
                    }
                }));


    }

    void dataFor_Stickers() {
        adapterframe = new FramesEfectsAdapter(this, frams_data);
        adapterframe.setTypeFrames(4, UtilsData.stkrsData);
        adapterframe.notifyDataSetChanged();

        stkrs_recyclvw.setAdapter(adapterframe);
        stkrs_recyclvw.setHasFixedSize(true);
        stkrs_recyclvw.setLayoutManager(new StaggeredGridLayoutManager(1,
                StaggeredGridLayoutManager.HORIZONTAL));

        stkrs_recyclvw.addOnItemTouchListener(new RecyclerTouchListener(
                getApplicationContext(), stkrs_recyclvw,
                new RecyclerTouchListener.RecyclerClick_Listener() {

                    @Override
                    public void onLongClick(View view, int position) {
                        // TODO Auto-generated method stub
                    }

                    @Override
                    public void onClick(View view, int position) {
                        // TODO Auto-generated method stub

                        Bitmap bitmap = null;

                        try {
                            bitmap = BitmapFactory.decodeResource(getResources(), UtilsData.stkrsData[position]);
                        } catch (Exception e) {
                            e.printStackTrace();
                            try {
                                bitmap = BitmapFactory.decodeResource(getResources(), UtilsData.stkrsData[position], optionsfun(2));

                            } catch (OutOfMemoryError er) {
                                er.printStackTrace();

                                bitmap = BitmapFactory.decodeResource(getResources(), UtilsData.stkrsData[position], optionsfun(4));

                            }
                        }

                        if (bitmap != null) {
                            clipart_txt = new ClipArt(EditingActivity.this,
                                    bitmap);
                            clipart_txt.setId(n++);
                            save_rel_ver.addView(clipart_txt);
                            clipart_txt.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    disableall();
                                }
                            });
                        }
                    }
                }));
    }


    public BitmapFactory.Options optionsfun(int samplesize) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        options.inSampleSize = samplesize;
        options.inJustDecodeBounds = false;

        return options;
    }


    private void dataFor_Text() {
        savedColor = Color.parseColor("#b1bc20");
        selectedfontstyle = Typeface.NORMAL;

        text_innerlay = (RelativeLayout) findViewById(R.id.text_innerlay);
//        text_innerlay.getLayoutParams().width = UtilsData.scwidth_u - UtilsData.scwidth_u / 10;

        opt_conf_lay = (RelativeLayout) findViewById(R.id.opt_conf_lay);
        opt_conf_lay.getLayoutParams().height = UtilsData.scheight_u / 12;

        txtCancel_lay = (RelativeLayout) findViewById(R.id.txtCancel_lay);
        txtDone_lay = (RelativeLayout) findViewById(R.id.txtDone_lay);

        txtDone_lay.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                if (txeditText.getText().toString().length() != 0) {

                    disableall();

                    Bitmap bitmap = UtilsData.textAsBitmap(txeditText.getText().toString(), UtilsData.scwidth_u / 5, savedColor, savedTypeface, selectedfontstyle);
                    int currentapiVersion = Build.VERSION.SDK_INT;
                    if (currentapiVersion > Build.VERSION_CODES.GINGERBREAD_MR1) {
                        try {
                            disableall();
                        } catch (Exception e) {

                        }

                        clipart_txt = new ClipArt(EditingActivity.this, bitmap);
                        clipart_txt.setId(n++);

                        save_rel_ver.addView(clipart_txt);

//                        keypadHide();
                        if (clipart_txt != null) {
                            clipart_txt.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    disableall();
                                }
                            });
                        }

                    } else {

                    }

                    textDialogLinear.setVisibility(View.GONE);
                    setValAlphaclicks(0);
                } else {
                    Toast.makeText(getApplicationContext(), "please enter text ", Toast.LENGTH_SHORT).show();
                }

                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(textDialogLinear.getWindowToken(), 0);


            }
        });

        txtCancel_lay.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(textDialogLinear.getWindowToken(), 0);

                textDialogLinear.setVisibility(View.GONE);
                setValAlphaclicks(0);
            }
        });

        opt_edittext_lay = (RelativeLayout) findViewById(R.id.opt_edittext_lay);
        opt_edittext_lay.getLayoutParams().height = UtilsData.scheight_u / 12;

        txeditText = (EditText) findViewById(R.id.txeditText);
        txeditText.setTextColor(savedColor);
        txeditText.setTypeface(savedTypeface);


        txeditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // TODO Auto-generated method stub
                if (s.toString().length() == 0) {
                    txeditText.setTypeface(Typeface.DEFAULT);
                } else {
                    txeditText.setTypeface(savedTypeface, selectedfontstyle);
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
                // TODO Auto-generated method stub

            }

            @Override
            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub

            }
        });

        opt_txt_style_lay = (LinearLayout) findViewById(R.id.opt_txt_style_lay);
        opt_txt_style_lay.getLayoutParams().height = UtilsData.scheight_u / 12;

        txt_bold_lay = (RelativeLayout) findViewById(R.id.txt_bold_lay);
        txt_bold_lay.setBackgroundColor(Color.TRANSPARENT);
        txt_normal_lay = (RelativeLayout) findViewById(R.id.txt_normal_lay);
        txt_normal_lay.setBackgroundColor(Color.WHITE);
        txt_italic_lay = (RelativeLayout) findViewById(R.id.txt_italic_lay);
        txt_italic_lay.setBackgroundColor(Color.TRANSPARENT);

        txt_bold = (TextView) findViewById(R.id.txt_bold);
        txt_bold.setTextColor(getResources().getColor(R.color.white));
        txt_normal = (TextView) findViewById(R.id.txt_normal);
        txt_normal.setTextColor(getResources().getColor(R.color.appcolorclk));
        txt_italic = (TextView) findViewById(R.id.txt_italic);
        txt_italic.setTextColor(getResources().getColor(R.color.white));

        txt_bold_lay.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                selectedfontstyle = Typeface.BOLD;
                txt_bold_lay.setBackgroundColor(getResources().getColor(R.color.white));
                txt_normal_lay.setBackgroundColor(Color.TRANSPARENT);
                txt_italic_lay.setBackgroundColor(Color.TRANSPARENT);

                txt_bold.setTextColor(getResources().getColor(R.color.appcolorclk));
                txt_normal.setTextColor(getResources().getColor(R.color.white));
                txt_italic.setTextColor(getResources().getColor(R.color.white));
                if (txeditText.getText().toString().length() > 0) {

                    txeditText.setTypeface(savedTypeface, selectedfontstyle);

                }
            }
        });

        txt_normal_lay.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                selectedfontstyle = Typeface.NORMAL;
                txt_bold_lay.setBackgroundColor(Color.TRANSPARENT);
                txt_normal_lay.setBackgroundColor(getResources().getColor(R.color.white));
                txt_italic_lay.setBackgroundColor(Color.TRANSPARENT);

                txt_bold.setTextColor(getResources().getColor(R.color.white));
                txt_normal.setTextColor(getResources().getColor(R.color.appcolorclk));
                txt_italic.setTextColor(getResources().getColor(R.color.white));
                if (txeditText.getText().toString().length() > 0) {
                    txeditText.setTypeface(savedTypeface, selectedfontstyle);
                }
            }
        });

        txt_italic_lay.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                selectedfontstyle = Typeface.ITALIC;
                txt_bold_lay.setBackgroundColor(Color.TRANSPARENT);
                txt_normal_lay.setBackgroundColor(Color.TRANSPARENT);
                txt_italic_lay.setBackgroundColor(getResources().getColor(R.color.white));

                txt_bold.setTextColor(getResources().getColor(R.color.white));
                txt_normal.setTextColor(getResources().getColor(R.color.white));
                txt_italic.setTextColor(getResources().getColor(R.color.appcolorclk));
                if (txeditText.getText().toString().length() > 0) {
                    txeditText.setTypeface(savedTypeface, selectedfontstyle);
                }
            }
        });

        font_scrollView = (HorizontalScrollView) findViewById(R.id.font_scrollView);
        font_scrolllinear = (LinearLayout) findViewById(R.id.font_scrolllinear);


        color_scrollView = (HorizontalScrollView) findViewById(R.id.color_scrollView);
        color_scrolllinear = (LinearLayout) findViewById(R.id.color_scrolllinear);

        colorPicker = (RelativeLayout) findViewById(R.id.colorPicker);
        colorPicker.getLayoutParams().width = UtilsData.scheight_u / 12;
        colorPicker.getLayoutParams().height = UtilsData.scheight_u / 12;

        colorPicker.setBackgroundResource(R.drawable.colorpicker);

        colorPicker.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                colorpicker();
            }
        });

        addFontGallery(UtilsData.font_styles);
        addcolorGallery(UtilsData.colorCodes);


    }

    void addFontGallery(String images[]) {
        font_scrolllinear.removeAllViews();
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);

        for (int i = 0; i < images.length; i++) {
            View view = inflater.inflate(R.layout.font_gallery, null);
            RelativeLayout relativeLayout = (RelativeLayout) view.findViewById(R.id.mainRelative);
            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, UtilsData.scheight_u / 12);
            layoutParams.setMargins(10, 3, 10, 3);
            relativeLayout.setLayoutParams(layoutParams);

            TextView textView = (TextView) view.findViewById(R.id.font_TextView);
            textView.setText("ABC");
            textView.setTextSize(15);
            textView.setTypeface(Typeface.createFromAsset(getAssets(), images[i]));

            relativeLayout.setBackgroundColor(Color.TRANSPARENT);

            fontclick(view, i);
            font_scrolllinear.addView(view);
        }

    }

    void addcolorGallery(String colorcodes[]) {
        color_scrolllinear.removeAllViews();
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);

        for (int i = 0; i < colorcodes.length; i++) {
            View view = inflater.inflate(R.layout.font_gallery, null);
            RelativeLayout relativeLayout = (RelativeLayout) view.findViewById(R.id.mainRelative);
            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(UtilsData.scheight_u / 12, UtilsData.scheight_u / 12);
            layoutParams.setMargins(3, 3, 3, 3);
            relativeLayout.setLayoutParams(layoutParams);

            TextView textView = (TextView) view.findViewById(R.id.font_TextView);
            textView.setVisibility(View.GONE);

            relativeLayout.setBackgroundColor(Color.parseColor(colorcodes[i]));

            colorclick(relativeLayout, i);
            color_scrolllinear.addView(view);
        }
    }

    private void fontclick(View colorView, final int i) {
        // TODO Auto-generated method stub
        colorView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                //Toast.makeText(context, ""+i, Toast.LENGTH_SHORT).show();

                if (txeditText.getText().toString().length() != 0) {
                    savedTypeface = Typeface.createFromAsset(getAssets(), UtilsData.font_styles[i]);
                    txeditText.setTypeface(savedTypeface, selectedfontstyle);
                }
            }
        });
    }


    public void disableall() {


        for (int i = 0; i < save_rel_ver.getChildCount(); i++) {
            if (save_rel_ver.getChildAt(i) instanceof ClipArt) {

                ((ClipArt) save_rel_ver.getChildAt(i)).disableAll();
            }
        }

    }


    //color picker
    private void colorpicker() {
        // TODO Auto-generated method stub
        ColorPickerDialog dialog = new ColorPickerDialog(this, colorr,
                new ColorPickerDialog.OnColorSelectedListener() {

                    @Override
                    public void onColorSelected(int color) {
                        // TODO Auto-generated method stub
                        colorr = color;
                        savedColor = color;
                        txeditText.setTextColor(colorr);
                    }

                    @Override
                    public void onColorSelected(int color, Boolean selected) {
                        // TODO Auto-generated method stub

                    }
                });

        dialog.show();

    }

    private void colorclick(View colorView, final int i) {
        // TODO Auto-generated method stub
        colorView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                savedColor = Color.parseColor(UtilsData.colorCodes[i]);
                txeditText.setTextColor(savedColor);
            }
        });
    }


    public void exitDialog() {
        final Dialog dialog = new Dialog(EditingActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_title);
        dialog.getWindow().setLayout((int) (UtilsData.scwidth_u / 1.1), WindowManager.LayoutParams.WRAP_CONTENT);
        dialog.show();
        dialog.setCancelable(false);
        TextView txt_title = (TextView) dialog.findViewById(R.id.txt_title);
        txt_title.setText("Are you sure you want to exit this page?");
        Button btn_cancel = (Button) dialog.findViewById(R.id.btn_cancel);
        btn_cancel.setText("No");
        Button btn_exit = (Button) dialog.findViewById(R.id.btn_exit);
        btn_exit.setText("Yes");
        btn_exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditingActivity.this.finish();
            }
        });
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.lin_cards:

                stkrs_recyclvw.setVisibility(View.GONE);
                textDialogLinear.setVisibility(View.GONE);

                if (rel_option_lay.getVisibility() == View.VISIBLE) {
                    if (cards_recyclvw.getVisibility() == View.VISIBLE) {
                        cards_recyclvw.setVisibility(View.GONE);
                        rel_option_lay.setVisibility(View.GONE);
                        setValAlphaclicks(0);
                    } else {
                        cards_recyclvw.setVisibility(View.VISIBLE);
                        rel_option_lay.setVisibility(View.VISIBLE);
                        setValAlphaclicks(1);
                    }
                } else {
                    cards_recyclvw.setVisibility(View.VISIBLE);
                    rel_option_lay.setVisibility(View.VISIBLE);
                    setValAlphaclicks(1);
                }


                break;

            case R.id.lin_addimg:
                rel_option_lay.setVisibility(View.GONE);
                stkrs_recyclvw.setVisibility(View.GONE);
                textDialogLinear.setVisibility(View.GONE);
                rel_option_lay.setVisibility(View.GONE);
                setValAlphaclicks(2);
                openCameraGallery();


                break;

            case R.id.lin_stickrs:
                cards_recyclvw.setVisibility(View.GONE);
                textDialogLinear.setVisibility(View.GONE);

                if (rel_option_lay.getVisibility() == View.VISIBLE) {
                    if (stkrs_recyclvw.getVisibility() == View.VISIBLE) {
                        cards_recyclvw.setVisibility(View.GONE);
                        rel_option_lay.setVisibility(View.GONE);
                        setValAlphaclicks(0);
                    } else {
                        stkrs_recyclvw.setVisibility(View.VISIBLE);
                        rel_option_lay.setVisibility(View.VISIBLE);
                        setValAlphaclicks(3);
                    }
                } else {
                    stkrs_recyclvw.setVisibility(View.VISIBLE);
                    rel_option_lay.setVisibility(View.VISIBLE);
                    setValAlphaclicks(3);
                }


                break;

            case R.id.lin_txtsetings:

                rel_option_lay.setVisibility(View.GONE);
                stkrs_recyclvw.setVisibility(View.GONE);
                textDialogLinear.setVisibility(View.GONE);
                rel_option_lay.setVisibility(View.GONE);
                setValAlphaclicks(4);
                UtilsData.ven_chk = 1;

                Intent venuintnt = new Intent(EditingActivity.this, VenueSettingsAct.class);
                venuintnt.putExtra("chk_hor_ver", chk_ver_hor);
                venuintnt.putExtra("frameselectdata", frmdata);
                venuintnt.putExtra("frmposition", frmdatapos);
                startActivity(venuintnt);


                break;

            case R.id.lin_text:

                cards_recyclvw.setVisibility(View.GONE);
                stkrs_recyclvw.setVisibility(View.GONE);

                if (rel_option_lay.getVisibility() == View.VISIBLE) {
                    if (textDialogLinear.getVisibility() == View.VISIBLE) {
                        textDialogLinear.setVisibility(View.GONE);
                        rel_option_lay.setVisibility(View.GONE);
                        setValAlphaclicks(0);
                    } else {
                        textDialogLinear.setVisibility(View.VISIBLE);
                        rel_option_lay.setVisibility(View.VISIBLE);
                        setValAlphaclicks(5);
                    }
                } else {
                    textDialogLinear.setVisibility(View.VISIBLE);
                    rel_option_lay.setVisibility(View.VISIBLE);
                    setValAlphaclicks(5);
                }


                break;


           /* case R.id.:
                break;

            case R.id.:
                break;*/

        }
    }


    public void setValAlphaclicks(int pos) {


        img_card.setAlpha(150);
        txt_card.setTextColor(getResources().getColor(R.color.grey45));

        img_add_img.setAlpha(150);
        txt_add_img.setTextColor(getResources().getColor(R.color.grey45));

        img_stkr.setAlpha(150);
        txt_stkr.setTextColor(getResources().getColor(R.color.grey45));

        img_set_txt.setAlpha(150);
        txt_set_txt.setTextColor(getResources().getColor(R.color.grey45));

        img_txt.setAlpha(150);
        txt_txt.setTextColor(getResources().getColor(R.color.grey45));


        switch (pos) {
            case 1:
                img_card.setAlpha(255);
                txt_card.setTextColor(getResources().getColor(R.color.gray));
                break;
            case 2:
                img_add_img.setAlpha(255);
                txt_add_img.setTextColor(getResources().getColor(R.color.gray));
                break;
            case 3:
                img_stkr.setAlpha(255);
                txt_stkr.setTextColor(getResources().getColor(R.color.gray));
                break;
            case 4:
                img_set_txt.setAlpha(255);
                txt_set_txt.setTextColor(getResources().getColor(R.color.gray));
                break;
            case 5:
                img_txt.setAlpha(255);
                txt_txt.setTextColor(getResources().getColor(R.color.gray));
                break;

        }
    }


    public void openCameraGallery() {
        disableall();

        opencameragallery = new AlertDialog.Builder(context);
        LayoutInflater lf = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View vv = lf.inflate(R.layout.opn_cam_gal_lay, null);

        opencameragallery.setView(vv);
        LinearLayout lincam = (LinearLayout) vv.findViewById(R.id.lincam);

        lincam.setLayoutParams(new RelativeLayout.LayoutParams(UtilsData.scwidth_u, (int) (UtilsData.scheight_u / 4.5)));

        LinearLayout openCamera = (LinearLayout) vv
                .findViewById(R.id.OpenCamera);
        LinearLayout openGallery = (LinearLayout) vv
                .findViewById(R.id.OpenGallery);

        lincam.setBackgroundColor(getResources().getColor(R.color.white));
        TextView cancelimagegall = (TextView) vv
                .findViewById(R.id.cancelimagegall);


        cancelimagegall.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                alert.dismiss();
                setValAlphaclicks(0);
            }
        });

        alert = opencameragallery.create();
        alert.setCanceledOnTouchOutside(false);
        alert.getWindow().setBackgroundDrawable(
                new ColorDrawable(Color.TRANSPARENT));

        alert.show();
        alert.getWindow().setBackgroundDrawable(
                new ColorDrawable(Color.TRANSPARENT));
        alert.getWindow().setGravity(CENTER);
        alert.getWindow().setLayout((UtilsData.scwidth_u - UtilsData.scwidth_u / 12), (int) (UtilsData.scheight_u / 2 - UtilsData.scheight_u / 15));
        openCamera.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                alert.dismiss();

                openCamera();
            }
        });

        openGallery.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                alert.dismiss();
                openGallery();

            }
        });
    }

    public void openCamera() {
        setValAlphaclicks(0);
        File rootFile = new File(Environment.getExternalStorageDirectory() + File.separator);
        sdImageMainDirectory = new File(rootFile, "frame.jpg");

        outputFileUri = Uri.fromFile(sdImageMainDirectory);
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
            intent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);
        } else {
            File file = new File(outputFileUri.getPath());
            Uri photoUri = FileProvider.getUriForFile(context, context.getPackageName() + ".provider", file);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
        }
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        if (intent.resolveActivity(context.getPackageManager()) != null) {
            startActivityForResult(intent, camera_ReqCode);
        }

    }

    public void openGallery() {
        setValAlphaclicks(0);
        startActivityForResult(new Intent(Intent.ACTION_PICK,
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI),
                gallery_ReqCode);

    }

    @Override
    protected void onActivityResult(int arg0, int arg1, Intent arg2) {
        // TODO Auto-generated method stub
        super.onActivityResult(arg0, arg1, arg2);

        if (arg0 == camera_ReqCode && arg1 == RESULT_OK) {
            Uri selectedImageURI = Uri.fromFile(sdImageMainDirectory);
            try {
                UtilsData.bfcropbmp = MediaStore.Images.Media.getBitmap(getContentResolver(), selectedImageURI);
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            UtilsData.isCameraImage = true;
            CropUtil.picpath = selectedImageURI;
            Intent i = new Intent(EditingActivity.this, CropImageActivity.class);
//            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            i.putExtra("chk_hor_ver", chk_ver_hor);

            i.putExtra("frameselectdata", frmdata);
            i.putExtra("frmposition", frmdatapos);

            startActivity(i);

        } else if (arg0 == gallery_ReqCode && arg1 == RESULT_OK) {

            try {
                UtilsData.isCameraImage = false;

                UtilsData.bfcropbmp = MediaStore.Images.Media.getBitmap(
                        getContentResolver(), arg2.getData());
                Intent i = new Intent(EditingActivity.this, CropImageActivity.class);
                i.putExtra("chk_hor_ver", chk_ver_hor);
                i.putExtra("frameselectdata", frmdata);
                i.putExtra("frmposition", frmdatapos);
//                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                CropUtil.picpath = arg2.getData();

                startActivity(i);

            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }


        }


    }











    /*public  void doubleclcick(Bitmap textBitmap,String text) {
        for (int i = 0; i < EditActivity.Instance.frameborder_rela.getChildCount(); i++) {

            if (EditActivity.Instance.frameborder_rela.getChildAt(i) instanceof ClipArt) {

                if (((ClipArt) EditActivity.Instance.frameborder_rela.getChildAt(i)).btndel.getVisibility() == View.VISIBLE) {

                    TextBean selectBean = beanlist.get(Utills.textPos);
                    selectBean.setText(text);
                    selectBean.setColor(Color.BLACK);
                    selectBean.setTypeface(text_qoute.getTypeface());
                    beanlist.set(Utills.textPos, selectBean);
                    ((ClipArt) EditActivity.Instance.frameborder_rela.getChildAt(i)).image
                            .setImageBitmap(textBitmap);
                    ;
                    ;
                }

            }

        }
    }
    */


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Drawable overflowIcon = toolbar_edit.getOverflowIcon();
        if (overflowIcon != null) {
            Drawable newIcon = overflowIcon.mutate();
            newIcon.setColorFilter(Color.WHITE, PorterDuff.Mode.MULTIPLY);
            toolbar_edit.setOverflowIcon(newIcon);
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

            save();
        }

        return super.onOptionsItemSelected(item);
    }

    File MyDir, distLocation;
    FileOutputStream outPutFile;
    String image_Name;

    Bitmap tempbitmap;


    public void save() {

        disableall();
        save_rel_ver.setDrawingCacheEnabled(true);
        tempbitmap = Bitmap.createBitmap(save_rel_ver.getDrawingCache());
        save_rel_ver.setDrawingCacheEnabled(false);


        image_Name = "Image-"
                + new SimpleDateFormat("ddMMyy_HHmmss").format(Calendar
                .getInstance().getTime()) + ".jpg";

        MyDir = new File(Environment.getExternalStorageDirectory()
                .getAbsolutePath() + "/" + getResources().getString(R.string.app_name));

        if (!MyDir.exists()) {
            MyDir.mkdirs();
        }
        distLocation = new File(MyDir, image_Name);

        if (distLocation.exists())
            distLocation.delete();

        outPutFile = null;


        try {
            outPutFile = new FileOutputStream(distLocation);
            tempbitmap.compress(Bitmap.CompressFormat.JPEG, 90, outPutFile);
            outPutFile.flush();
            Toast.makeText(context, "Image saved successfully",
                    Toast.LENGTH_SHORT).show();
            addImageToGallery(distLocation.getAbsolutePath(), context);
//            MyCreationsFragment.update(distLocation.getAbsolutePath(), false);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void addImageToGallery(String filePath, final Context context) {
        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.DATE_TAKEN, System.currentTimeMillis());
        values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg");
        values.put(MediaStore.MediaColumns.DATA, filePath);
        context.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                values);

    }




    /* public void startIntent() {
        Intent intent = new Intent(context, ShowScrenActivity.class);
        intent.putExtra("imgsavechk", "no");
        intent.putExtra("final_image_path", f1.getAbsolutePath());
        startActivity(intent);
    }

    private void refreshGallery(File file) {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        mediaScanIntent.setData(Uri.fromFile(file));
        sendBroadcast(mediaScanIntent);
    }*/


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        UtilsData.ven_chk = 0;
        UtilsData.nam1_tx = "";
        UtilsData.nam2_tx = "";
        UtilsData.date_tx = "";
        UtilsData.time_tx = "";
        UtilsData.venud_tx = "";

    }
}
