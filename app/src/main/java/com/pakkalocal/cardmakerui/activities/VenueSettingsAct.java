package com.pakkalocal.cardmakerui.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.inputmethodservice.Keyboard;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.Editable;
import android.text.InputType;
import android.text.Layout;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextWatcher;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.bumptech.glide.util.Util;
import com.pakkalocal.cardmakerui.R;
import com.pakkalocal.cardmakerui.adapters.Venu_Fonts_Adapter;
import com.pakkalocal.cardmakerui.coloruse.ColorPicker;
import com.pakkalocal.cardmakerui.coloruse.ColorPickerDialog;
import com.pakkalocal.cardmakerui.keyboardv.BasicOnKeyboardActionListener;
import com.pakkalocal.cardmakerui.keyboardv.BasicOnKeyboardActionListenerName2;
import com.pakkalocal.cardmakerui.keyboardv.BasicOnKeyboardActionListenerVenue;
import com.pakkalocal.cardmakerui.keyboardv.CustomKeyboardView;
import com.pakkalocal.cardmakerui.keyboardv.CustomTypefaceSpan;
import com.pakkalocal.cardmakerui.utilsdata.ClipArt;
import com.pakkalocal.cardmakerui.utilsdata.RecyclerTouchListener;
import com.pakkalocal.cardmakerui.utilsdata.UtilsData;

import java.lang.reflect.Type;
import java.util.Calendar;

import static android.view.Gravity.CENTER;
import static android.view.Gravity.CENTER_HORIZONTAL;

/**
 * Created by 2117 on 6/18/2018.
 */

public class VenueSettingsAct extends AppCompatActivity {
    CustomKeyboardView prkeyboardViewnam1, prkeyboardViewnam2, prkeyboardViewvnu;


    int clr_nam1 = Color.BLACK, clr_nam2 = Color.BLACK, colorr = Color.BLACK;
    int pospick = 0, fnspick = 0;
    Bitmap bmname1, bmname2, bmnamewds, bmnamedttm, bmnamevnu;
    Typeface savedTypeface = Typeface.DEFAULT;

    //////////////
    RelativeLayout clor_pick_name1, clor_pick_name2;
    public EditText name1_edit, name2_edit, date_edit, time_edit, venue_edit;
    TextView txt_clr_clk, txt_font_clk;
    RelativeLayout _clraddvw, _fntsrecyl_rel;
    RecyclerView _fntsrecyl;
    ColorPicker colorPickerView;
    Venu_Fonts_Adapter fntvnu_frame;


    //////////////

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.venue_settings_lay);
        openVenueSettings();
    }


    @Override
    protected void onResume() {
        super.onResume();

        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }


        if (prkeyboardViewnam1.getVisibility() == View.VISIBLE || prkeyboardViewnam2.getVisibility() == View.VISIBLE || prkeyboardViewvnu.getVisibility() == View.VISIBLE) {
            prkeyboardViewnam1.setVisibility(View.GONE);
            prkeyboardViewnam2.setVisibility(View.GONE);
            prkeyboardViewvnu.setVisibility(View.GONE);
            name2_edit.setCursorVisible(false);
            name2_edit.setFocusable(false);
            name2_edit.setFocusableInTouchMode(false);

            name1_edit.setCursorVisible(false);
            name1_edit.setFocusable(false);
            name1_edit.setFocusableInTouchMode(false);

            venue_edit.setCursorVisible(false);
            venue_edit.setFocusable(false);
            venue_edit.setFocusableInTouchMode(false);
        }
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
    }

    @Override
    public void onBackPressed() {
        if (prkeyboardViewnam1.getVisibility() == View.VISIBLE || prkeyboardViewnam2.getVisibility() == View.VISIBLE || prkeyboardViewvnu.getVisibility() == View.VISIBLE) {
            prkeyboardViewnam1.setVisibility(View.GONE);
            prkeyboardViewnam2.setVisibility(View.GONE);
            prkeyboardViewvnu.setVisibility(View.GONE);
            name2_edit.setCursorVisible(false);
            name2_edit.setFocusable(false);
            name2_edit.setFocusableInTouchMode(false);

            name1_edit.setCursorVisible(false);
            name1_edit.setFocusable(false);
            name1_edit.setFocusableInTouchMode(false);

            venue_edit.setCursorVisible(false);
            venue_edit.setFocusable(false);
            venue_edit.setFocusableInTouchMode(false);

        } else {
            finish();
        }
    }

    void openVenueSettings() {
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }


        prkeyboardViewnam1 = (CustomKeyboardView) findViewById(R.id.prkeyboardViewnam1);
        prkeyboardViewnam2 = (CustomKeyboardView) findViewById(R.id.prkeyboardViewnam2);
        prkeyboardViewvnu = (CustomKeyboardView) findViewById(R.id.prkeyboardViewvnu);


        clor_pick_name1 = (RelativeLayout) findViewById(R.id.clor_pick_name1);
        clor_pick_name2 = (RelativeLayout) findViewById(R.id.clor_pick_name2);

        name1_edit = (EditText) findViewById(R.id.name1_edit);
        name2_edit = (EditText) findViewById(R.id.name2_edit);

        time_edit = (EditText) findViewById(R.id.time_edit);
        date_edit = (EditText) findViewById(R.id.date_edit);
        venue_edit = (EditText) findViewById(R.id.venue_edit);
        txt_clr_clk = (TextView) findViewById(R.id.txt_clr_clk);
        txt_font_clk = (TextView) findViewById(R.id.txt_font_clk);
        _fntsrecyl_rel = (RelativeLayout) findViewById(R.id._fntsrecyl_rel);
        _clraddvw = (RelativeLayout) findViewById(R.id._clraddvw);
        _fntsrecyl = (RecyclerView) findViewById(R.id._fntsrecyl);


        name1_edit.clearFocus();
        name1_edit.requestFocusFromTouch();
        name2_edit.clearFocus();
        name2_edit.requestFocusFromTouch();
        venue_edit.clearFocus();
        venue_edit.requestFocusFromTouch();


        name1_edit.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                selectKeyboardname1();
            }
        });

        name2_edit.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                selectKeyboardname2();
            }
        });

        venue_edit.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                selectKeyboardvnue();
            }
        });


        name1_edit.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                view.getParent().requestDisallowInterceptTouchEvent(true);
                switch (motionEvent.getAction() & MotionEvent.ACTION_MASK) {
                    case MotionEvent.ACTION_DOWN:
                        selectKeyboardname1();
                        break;
                    case MotionEvent.ACTION_UP:
                        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
                        view.getParent().requestDisallowInterceptTouchEvent(false);
                        break;
                }

                return false;
            }
        });
        name2_edit.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                view.getParent().requestDisallowInterceptTouchEvent(true);
                switch (motionEvent.getAction() & MotionEvent.ACTION_MASK) {
                    case MotionEvent.ACTION_DOWN:
                        selectKeyboardname2();
                        break;
                    case MotionEvent.ACTION_UP:
                        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
                        view.getParent().requestDisallowInterceptTouchEvent(false);
                        break;
                }

                return false;
            }
        });
        venue_edit.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                view.getParent().requestDisallowInterceptTouchEvent(true);
                switch (motionEvent.getAction() & MotionEvent.ACTION_MASK) {
                    case MotionEvent.ACTION_DOWN:
                        selectKeyboardvnue();
                        break;
                    case MotionEvent.ACTION_UP:
                        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
                        view.getParent().requestDisallowInterceptTouchEvent(false);
                        break;
                }

                return false;
            }
        });


        name1_edit.setClickable(true);
        name1_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectKeyboardname1();
            }
        });

        name2_edit.setClickable(true);
        name2_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectKeyboardname2();
            }
        });

        venue_edit.setClickable(true);
        venue_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectKeyboardvnue();
            }
        });


        date_edit.setInputType((InputType.TYPE_NULL));
        time_edit.setInputType((InputType.TYPE_NULL));


        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(UtilsData.scwidth_u, UtilsData.scheight_u / 4);
        layoutParams.addRule(RelativeLayout.CENTER_IN_PARENT);
        _clraddvw.setLayoutParams(new RelativeLayout.LayoutParams(UtilsData.scwidth_u, UtilsData.scheight_u / 4));
        colorPickerView = new ColorPicker(VenueSettingsAct.this);
        colorPickerView.setActdata(VenueSettingsAct.this);
        colorPickerView.setColor(getResources().getColor(R.color.appcolorclk));
        _clraddvw.addView(colorPickerView, layoutParams);

//        savedColor
//        _fntsrecyl_rel.setLayoutParams(layoutParams);

        fntvnu_frame = new Venu_Fonts_Adapter(this, UtilsData.font_styles);

        fntvnu_frame.notifyDataSetChanged();


        _fntsrecyl.setAdapter(fntvnu_frame);
        _fntsrecyl.setHasFixedSize(true);
        _fntsrecyl.setLayoutManager(new StaggeredGridLayoutManager(1,
                StaggeredGridLayoutManager.HORIZONTAL));


        date_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                name2_edit.setCursorVisible(false);
                name2_edit.setFocusable(false);
                name2_edit.setFocusableInTouchMode(false);

                name1_edit.setCursorVisible(false);
                name1_edit.setFocusable(false);
                name1_edit.setFocusableInTouchMode(false);

                venue_edit.setCursorVisible(false);
                venue_edit.setFocusable(false);
                venue_edit.setFocusableInTouchMode(false);


                Calendar mcurrentDate = Calendar.getInstance();
                int mYear = mcurrentDate.get(Calendar.YEAR);
                int mMonth = mcurrentDate.get(Calendar.MONTH);
                int mDay = mcurrentDate.get(Calendar.DAY_OF_MONTH);

                prkeyboardViewnam1.setVisibility(View.GONE);
                prkeyboardViewvnu.setVisibility(View.GONE);
                prkeyboardViewnam2.setVisibility(View.GONE);

                DatePickerDialog mDatePicker = new DatePickerDialog(VenueSettingsAct.this, new DatePickerDialog.OnDateSetListener() {
                    public void onDateSet(DatePicker datepicker, int selectedyear, int selectedmonth, int selectedday) {
                        // TODO Auto-generated method stub

                        int s = selectedmonth + 1;
                        String a = selectedday + "/" + s + "/" + selectedyear;
                        date_edit.setTextColor(colorPickerView.getColor());
                        date_edit.setText("" + a);


                    }
                }, mYear, mMonth, mDay);
                mDatePicker.setTitle("Select date");
                mDatePicker.show();


            }
        });

        time_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                name2_edit.setCursorVisible(false);
                name2_edit.setFocusable(false);
                name2_edit.setFocusableInTouchMode(false);

                name1_edit.setCursorVisible(false);
                name1_edit.setFocusable(false);
                name1_edit.setFocusableInTouchMode(false);

                venue_edit.setCursorVisible(false);
                venue_edit.setFocusable(false);
                venue_edit.setFocusableInTouchMode(false);

                prkeyboardViewnam1.setVisibility(View.GONE);
                prkeyboardViewvnu.setVisibility(View.GONE);
                prkeyboardViewnam2.setVisibility(View.GONE);

                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(VenueSettingsAct.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        time_edit.setTextColor(colorPickerView.getColor());
                        time_edit.setText(selectedHour + ":" + selectedMinute);
                    }
                }, hour, minute, false);
                //Yes 12 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();


            }
        });


        venue_edit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                venue_edit.setTextColor(colorPickerView.getColor());
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                venue_edit.setTextColor(colorPickerView.getColor());
            }

            @Override
            public void afterTextChanged(Editable editable) {
                venue_edit.setTextColor(colorPickerView.getColor());
            }
        });

        findViewById(R.id.rel_vnuclose).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               /* UtilsData.nam1_tx = "";
                UtilsData.nam2_tx = "";
                UtilsData.date_tx = "";
                UtilsData.time_tx = "";
                UtilsData.venud_tx = "";*/

                UtilsData.ven_chk = 0;

                finish();
            }
        });

        findViewById(R.id.rel_vnuok).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ////////////////
//                Add 5 clip arts at a time
                final int wdthby3 = UtilsData.scwidth_u / 3;
                UtilsData.ven_chk = 1;

                if (name1_edit.getText().toString().length() > 0 && name2_edit.getText().toString().length() > 0 && time_edit.getText().toString().length() > 0
                        && venue_edit.getText().toString().length() > 0) {
                    bmname1 = UtilsData.textAsBitmap(name1_edit.getText().toString(), wdthby3, clr_nam1, name1_edit.getTypeface(), Typeface.NORMAL);
                    bmname2 = UtilsData.textAsBitmap(name2_edit.getText().toString(), wdthby3, clr_nam2, name2_edit.getTypeface(), Typeface.NORMAL);

                    bmnamewds = UtilsData.textAsBitmap("Weds", wdthby3, Color.BLACK, savedTypeface, Typeface.NORMAL);

                    bmnamedttm = UtilsData.textAsBitmap(time_edit.getText().toString() + "   " + date_edit.getText().toString(),
                            wdthby3, colorPickerView.getColor(), savedTypeface, Typeface.NORMAL);
                    bmnamevnu = UtilsData.textAsBitmap(venue_edit.getText().toString(), wdthby3, colorPickerView.getColor(), savedTypeface, Typeface.NORMAL);

                    if (bmname1 != null && bmname2 != null && bmnamewds != null && bmnamedttm != null && bmnamevnu != null) {

                        final Bitmap[] intArray = new Bitmap[]{bmname1, bmname2, bmnamewds, bmnamedttm, bmnamevnu};


                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                try {

                                    UtilsData.nam1_tx = name1_edit.getText().toString();
                                    UtilsData.nam2_tx = name2_edit.getText().toString();
                                    UtilsData.date_tx = date_edit.getText().toString() + "";
                                    UtilsData.time_tx = time_edit.getText().toString();
                                    UtilsData.venud_tx = venue_edit.getText().toString();

                                    adding_Cliparts(intArray);


                                } catch (Exception e) {
                                }

                            }
                        }, 100);

                    } else {
                        Toast.makeText(getApplicationContext(), "Could u please fill all data ", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Toast.makeText(getApplicationContext(), "Enter data ", Toast.LENGTH_SHORT).show();
                }
                finish();

            }
        });


        txt_clr_clk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                _clraddvw.setVisibility(View.VISIBLE);
                _fntsrecyl_rel.setVisibility(View.GONE);

            }
        });


        txt_font_clk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                _clraddvw.setVisibility(View.GONE);
                _fntsrecyl_rel.setVisibility(View.VISIBLE);

            }
        });


        _fntsrecyl.addOnItemTouchListener(new RecyclerTouchListener(
                getApplicationContext(), _fntsrecyl,
                new RecyclerTouchListener.RecyclerClick_Listener() {

                    @Override
                    public void onLongClick(View view, int position) {
                        // TODO Auto-generated method stub
                    }

                    @Override
                    public void onClick(View view, int position) {
                        // TODO Auto-generated method stub
                        savedTypeface = Typeface.createFromAsset(VenueSettingsAct.this.getAssets(), UtilsData.font_styles[position]);
                        venue_edit.setTypeface(savedTypeface);
                        date_edit.setTypeface(savedTypeface);
                        time_edit.setTypeface(savedTypeface);

                    }
                }));

        clor_pick_name1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                colorpicker(1);
            }
        });
        clor_pick_name2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                colorpicker(2);
            }
        });

        prkeyboardViewnam1.setVisibility(View.GONE);
        prkeyboardViewvnu.setVisibility(View.GONE);
        prkeyboardViewnam2.setVisibility(View.GONE);

        if (UtilsData.ven_chk == 0) {
            name1_edit.setText("");
            name2_edit.setText("");
            date_edit.setText("");
            time_edit.setText("");
            venue_edit.setText("");
        } else {
            if (UtilsData.nam1_tx != null && UtilsData.nam2_tx != null && UtilsData.date_tx != null && UtilsData.time_tx != null && UtilsData.venud_tx != null) {
                name1_edit.setText(UtilsData.nam1_tx + "");
                name2_edit.setText(UtilsData.nam2_tx + "");
                date_edit.setText(UtilsData.date_tx + "");
                time_edit.setText(UtilsData.time_tx + "");
                venue_edit.setText(UtilsData.venud_tx + "");
            }
        }


    }


    Keyboard getKeyboardBasedonpos() {

        Keyboard mKeyboardfrs = null;
        switch (pospick) {
            case 0:
                mKeyboardfrs = new Keyboard(VenueSettingsAct.this, R.xml.kbd_eng1);
                break;
            case 1:
                mKeyboardfrs = new Keyboard(VenueSettingsAct.this, R.xml.kbd_hin1);
                break;
            case 2:
                mKeyboardfrs = new Keyboard(VenueSettingsAct.this, R.xml.kbd_tel1);
                break;
            case 3:
                mKeyboardfrs = new Keyboard(VenueSettingsAct.this, R.xml.kbd_tam1);
                break;
            case 4:
                mKeyboardfrs = new Keyboard(VenueSettingsAct.this, R.xml.kbd_knd1);
                break;
            case 5:
                break;
            case 6:
                if (UtilsData.isLangSupported(VenueSettingsAct.this, "ગુજરાતી")) {
                    mKeyboardfrs = new Keyboard(VenueSettingsAct.this, R.xml.kbd_guj1);
                } else {
                    UtilsData.displayAlert(VenueSettingsAct.this, getResources().getString(R.string.app_name), "Gujarati keyboard is not supported "
                            + "by your device");
                    pospick = 0;
                    fnspick = 0;
                    mKeyboardfrs = new Keyboard(VenueSettingsAct.this, R.xml.kbd_eng1);

                }

                break;
            case 7:

                if (UtilsData.isLangSupported(VenueSettingsAct.this, "ਪੰਜਾਬੀ ਦੇ")) {
                    mKeyboardfrs = new Keyboard(VenueSettingsAct.this, R.xml
                            .kbd_punj1);
                } else {
                    UtilsData.displayAlert(VenueSettingsAct.this, getResources().getString(R.string.app_name), "Punjabi keyboard is not supported "
                            + "by your device");
                    pospick = 0;
                    fnspick = 0;
                    mKeyboardfrs = new Keyboard(VenueSettingsAct.this, R.xml.kbd_eng1);

                }

                break;
            default:
                mKeyboardfrs = new Keyboard(VenueSettingsAct.this, R.xml.kbd_eng1);
                break;
        }
        return mKeyboardfrs;
    }


    private void selectKeyboardname2() {
        // Do not show the preview balloons

        name2_edit.setCursorVisible(true);
        name2_edit.setFocusable(true);
        name2_edit.setFocusableInTouchMode(true);

        name1_edit.setCursorVisible(false);
        name1_edit.setFocusable(false);
        name1_edit.setFocusableInTouchMode(false);

        venue_edit.setCursorVisible(false);
        venue_edit.setFocusable(false);
        venue_edit.setFocusableInTouchMode(false);

        prkeyboardViewnam1.setVisibility(View.GONE);
        prkeyboardViewvnu.setVisibility(View.GONE);
        prkeyboardViewnam2.setVisibility(View.VISIBLE);
        prkeyboardViewnam2.setPreviewEnabled(false);
        try {
            showKeyboardWithAnimationname2();
            prkeyboardViewnam2.setVisibility(View.VISIBLE);
            prkeyboardViewnam2.setKeyboard(getKeyboardBasedonpos());
            prkeyboardViewnam2.setOnKeyboardActionListener(new BasicOnKeyboardActionListenerName2(
                    VenueSettingsAct.this,
                    name2_edit,
                    prkeyboardViewnam2));

            name2_edit.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {
                    selectKeyboardname2();
                    switch (motionEvent.getAction()) {
                        case MotionEvent.ACTION_UP:
                            Layout layout = ((EditText) view).getLayout();
                            float x = motionEvent.getX() + name2_edit.getScrollX();
                            float y = motionEvent.getY() + name2_edit.getScrollY();
                            int line = layout.getLineForVertical((int) y);

                            int offset = layout.getOffsetForHorizontal(line, x);
                            if (offset > 0)
                                if (x > layout.getLineMax(0))
                                    name2_edit
                                            .setSelection(offset);     // touch was at end of text
                                else
                                    name2_edit.setSelection(offset - 1);

                            name2_edit.setCursorVisible(true);
                            break;
                    }
                    return true;
                }
            });


        } catch (Exception e) {
            e.printStackTrace();
            Log.e("i m in errro", "");
            prkeyboardViewnam2.setVisibility(View.GONE);
            //Show Default Keyboard

            View view = this.getCurrentFocus();
            if (view != null) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }

          /*  InputMethodManager imm =
                    (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(name2_edit, 0);*/
            name2_edit.setOnTouchListener(null);
        }
    }

    private void selectKeyboardvnue() {
        name2_edit.setCursorVisible(false);
        name2_edit.setFocusable(false);
        name2_edit.setFocusableInTouchMode(false);

        name1_edit.setCursorVisible(false);
        name1_edit.setFocusable(false);
        name1_edit.setFocusableInTouchMode(false);

        venue_edit.setCursorVisible(true);
        venue_edit.setFocusable(true);
        venue_edit.setFocusableInTouchMode(true);


        prkeyboardViewnam1.setVisibility(View.GONE);
        prkeyboardViewnam2.setVisibility(View.GONE);
        prkeyboardViewvnu.setVisibility(View.VISIBLE);


        prkeyboardViewvnu.setPreviewEnabled(false);
        try {

            showKeyboardWithAnimationvenu();
            prkeyboardViewvnu.setVisibility(View.VISIBLE);
            prkeyboardViewvnu.setKeyboard(getKeyboardBasedonpos());
            prkeyboardViewvnu.setOnKeyboardActionListener(new BasicOnKeyboardActionListenerVenue(
                    VenueSettingsAct.this,
                    venue_edit,
                    prkeyboardViewvnu));

            venue_edit.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {
                    selectKeyboardvnue();
                    switch (motionEvent.getAction()) {
                        case MotionEvent.ACTION_UP:
                            Layout layout = ((EditText) view).getLayout();
                            float x = motionEvent.getX() + venue_edit.getScrollX();
                            float y = motionEvent.getY() + venue_edit.getScrollY();
                            int line = layout.getLineForVertical((int) y);

                            int offset = layout.getOffsetForHorizontal(line, x);
                            if (offset > 0)
                                if (x > layout.getLineMax(0))
                                    venue_edit
                                            .setSelection(offset);     // touch was at end of text
                                else
                                    venue_edit.setSelection(offset - 1);

                            venue_edit.setCursorVisible(true);
                            break;
                    }
                    return true;
                }
            });


        } catch (Exception e) {
            e.printStackTrace();
            Log.e("i m in errro", "");
            prkeyboardViewvnu.setVisibility(View.GONE);
            //Show Default Keyboard
            View view = this.getCurrentFocus();
            if (view != null) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }

           /* InputMethodManager imm =
                    (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(venue_edit, 0);*/
            venue_edit.setOnTouchListener(null);
        }

    }

    public void selectKeyboardname1() {
        // Do not show the preview balloons


        name2_edit.setCursorVisible(false);
        name2_edit.setFocusable(false);
        name2_edit.setFocusableInTouchMode(false);

        name1_edit.setCursorVisible(true);
        name1_edit.setFocusable(true);
        name1_edit.setFocusableInTouchMode(true);

        venue_edit.setCursorVisible(false);
        venue_edit.setFocusable(false);
        venue_edit.setFocusableInTouchMode(false);


        prkeyboardViewnam1.setVisibility(View.VISIBLE);
        prkeyboardViewvnu.setVisibility(View.GONE);
        prkeyboardViewnam2.setVisibility(View.GONE);
        prkeyboardViewnam1.setPreviewEnabled(false);
        try {
            showKeyboardWithAnimationname1();
            prkeyboardViewnam1.setVisibility(View.VISIBLE);
            prkeyboardViewnam1.setKeyboard(getKeyboardBasedonpos());
            prkeyboardViewnam1.setOnKeyboardActionListener(new BasicOnKeyboardActionListener(
                    VenueSettingsAct.this,
                    name1_edit,
                    prkeyboardViewnam1));

            name1_edit.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {
                    selectKeyboardname1();
                    switch (motionEvent.getAction()) {
                        case MotionEvent.ACTION_UP:
                            Layout layout = ((EditText) view).getLayout();
                            float x = motionEvent.getX() + name1_edit.getScrollX();
                            float y = motionEvent.getY() + name1_edit.getScrollY();
                            int line = layout.getLineForVertical((int) y);

                            int offset = layout.getOffsetForHorizontal(line, x);
                            if (offset > 0)
                                if (x > layout.getLineMax(0))
                                    name1_edit
                                            .setSelection(offset);     // touch was at end of text
                                else
                                    name1_edit.setSelection(offset - 1);

                            name1_edit.setCursorVisible(true);
                            break;
                    }
                    return true;
                }
            });


        } catch (Exception e) {
            e.printStackTrace();
            Log.e("i m in errro", "");
            prkeyboardViewnam1.setVisibility(View.GONE);
            //Show Default Keyboard

            View view = this.getCurrentFocus();
            if (view != null) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
            name1_edit.setOnTouchListener(null);
        }
    }


    private void showKeyboardWithAnimationname1() {
        if (prkeyboardViewnam1.getVisibility() == View.GONE) {
            Animation animation = AnimationUtils
                    .loadAnimation(VenueSettingsAct.this,
                            R.anim.slide_from_bottom);
            prkeyboardViewnam1.showWithAnimation(animation);
        }
    }


    private void showKeyboardWithAnimationname2() {
        if (prkeyboardViewnam2.getVisibility() == View.GONE) {
            Animation animation = AnimationUtils
                    .loadAnimation(VenueSettingsAct.this,
                            R.anim.slide_from_bottom);
            prkeyboardViewnam2.showWithAnimation(animation);
        }
    }


    private void showKeyboardWithAnimationvenu() {
        if (prkeyboardViewvnu.getVisibility() == View.GONE) {
            Animation animation = AnimationUtils
                    .loadAnimation(VenueSettingsAct.this,
                            R.anim.slide_from_bottom);
            prkeyboardViewvnu.showWithAnimation(animation);
        }
    }

    public void show_alertdialogueFonts(final EditText editTet) {
        String[] listItems = new String[UtilsData.font_styles.length];

        for (int i = 0; i < UtilsData.font_styles.length; i++) {
            Typeface font = Typeface.createFromAsset(this.getAssets(), UtilsData.font_styles[i]);
            SpannableString mNewTitle = new SpannableString("Font");
            mNewTitle.setSpan(new CustomTypefaceSpan("", font), 0, mNewTitle.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
            listItems[i] = String.valueOf(mNewTitle);
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(VenueSettingsAct.this)
                .setTitle("Choose Font")
                .setSingleChoiceItems(listItems, fnspick, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        fnspick = i;
                        if (editTet.getId() == R.id.venue_edit)
                            venue_edit.setTypeface(Typeface.createFromAsset(VenueSettingsAct.this.getAssets(), UtilsData.font_styles[i]));
                        else if (editTet.getId() == R.id.name1_edit)
                            name1_edit.setTypeface(Typeface.createFromAsset(VenueSettingsAct.this.getAssets(), UtilsData.font_styles[i]));
                        else if (editTet.getId() == R.id.name2_edit)
                            name2_edit.setTypeface(Typeface.createFromAsset(VenueSettingsAct.this.getAssets(), UtilsData.font_styles[i]));
                        dialogInterface.dismiss();
                    }
                });
        AlertDialog alertdialog = builder.create();
        alertdialog.show();
    }

    public void show_alertdialogue(final EditText editTet) {
        AlertDialog.Builder builder = new AlertDialog.Builder(VenueSettingsAct.this)
                .setTitle("Choose Language")
                .setSingleChoiceItems(R.array.switchLangArr, pospick, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        pospick = i;
                        if (editTet.getId() == R.id.venue_edit)
                            selectKeyboardvnue();
                        else if (editTet.getId() == R.id.name1_edit)
                            selectKeyboardname1();
                        else if (editTet.getId() == R.id.name2_edit)
                            selectKeyboardname2();
                        dialogInterface.dismiss();
                    }
                });
        AlertDialog alertdialog = builder.create();
        alertdialog.show();


    }

    //color picker
    private void colorpicker(final int nm) {
        // TODO Auto-generated method stub
        if (nm == 1) {
            colorr = clr_nam1;
        } else if (nm == 2) {
            colorr = clr_nam2;
        }
        ColorPickerDialog dialog = new ColorPickerDialog(this, colorr,
                new ColorPickerDialog.OnColorSelectedListener() {

                    @Override
                    public void onColorSelected(int color) {
                        // TODO Auto-generated method stub
                        if (nm == 1) {
                            colorr = color;
                            clr_nam1 = color;
                            name1_edit.setTextColor(colorr);
                        } else {
                            colorr = color;
                            clr_nam2 = color;
                            name2_edit.setTextColor(colorr);
                        }
                    }

                    @Override
                    public void onColorSelected(int color, Boolean selected) {
                        // TODO Auto-generated method stub

                    }
                });

        dialog.show();

    }

    private void adding_Cliparts(Bitmap[] bitmap) {

        int hght = UtilsData.scheight_u / 12;
        int wdth = UtilsData.scwidth_u / 2;
        for (int i = 0; i < bitmap.length; i++) {
            ClipArt clpt = new ClipArt(VenueSettingsAct.this,
                    bitmap[i]);
            clpt.setId(EditingActivity.activity.n++);

//            layoutParams = new LayoutParams(screenWidth/2, screenWidth/2);
//            layGroup.setLayoutParams(layoutParams);
//            layoutParams.leftMargin = (random.nextInt(screenWidth / 2));
//            layoutParams.topMargin = (random.nextInt(screenWidth / 2));

            RelativeLayout.LayoutParams parms = new RelativeLayout.LayoutParams((int) (wdth), (int) ((hght) + hght));
            parms.leftMargin = (int) (wdth / 2.5f);
            parms.topMargin = (int) ((i * hght) + (2 * hght));

            clpt.setParamsCustom(parms);
//            layoutParams
            clpt.layoutParams = parms;
            clpt.invalidate();
            clpt.setLayoutParams(parms);
            EditingActivity.activity.save_rel_ver.addView(clpt);
            if (clpt != null) {
                clpt.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        EditingActivity.activity.disableall();
                    }
                });

            }
            EditingActivity.activity.disableall();
        }


        /*
        for (int i = 0; i < bitmap.length; i++) {
            EditingActivity.activity.clipart_txt = new ClipArt(VenueSettingsAct.this,
                    bitmap[i] );
            EditingActivity.activity.clipart_txt.setId(EditingActivity.activity.n++);
            RelativeLayout.LayoutParams parms =   new RelativeLayout.LayoutParams((int) (wdth), (int) ((i * hght) + hght));
            EditingActivity.activity.clipart_txt.setParamsCustom(parms);
//            layoutParams
            EditingActivity.activity.clipart_txt.layoutParams =parms;
            EditingActivity.activity.clipart_txt.invalidate();
            EditingActivity.activity.clipart_txt.setLayoutParams(parms);
            EditingActivity.activity.save_rel_ver.addView(EditingActivity.activity.clipart_txt);
            if (EditingActivity.activity.clipart_txt != null) {
                EditingActivity.activity.clipart_txt.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        EditingActivity.activity.disableall();
                    }
                });
            }

        }*/

    }

}




/*
new ViewGroup.LayoutParams(wdthby5, hgtby8)
        adding_Cliparts(bmname2, new ViewGroup.LayoutParams(wdthby5, (hgtby8 + hgtby8)));

        adding_Cliparts(bmnamewds, new ViewGroup.LayoutParams(wdthby5, (hgtby8 + hgtby8 + hgtby8)));

        adding_Cliparts(bmnamedttm, new ViewGroup.LayoutParams(wdthby5, (hgtby8 + hgtby8 + hgtby8 + hgtby8)));

        adding_Cliparts(bmnamevnu, new ViewGroup.LayoutParams(wdthby5, (hgtby8 + hgtby8 + hgtby8 + hgtby8 + hgtby8)));
        */
















/*   View.OnTouchListener edit_touch = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            if (v.getId() == R.id.name1_edit) {
                v.getParent().requestDisallowInterceptTouchEvent(true);
                switch (event.getAction() & MotionEvent.ACTION_MASK) {
                    case MotionEvent.ACTION_DOWN:
                        selectKeyboardname1();
                        break;
                    case MotionEvent.ACTION_UP:
                        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
                        v.getParent().requestDisallowInterceptTouchEvent(false);
                        break;
                }
            } else if (v.getId() == R.id.name2_edit) {
                v.getParent().requestDisallowInterceptTouchEvent(true);
                switch (event.getAction() & MotionEvent.ACTION_MASK) {
                    case MotionEvent.ACTION_DOWN:
                        selectKeyboardname2();
                        break;
                    case MotionEvent.ACTION_UP:
                        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
                        v.getParent().requestDisallowInterceptTouchEvent(false);
                        break;
                }
            } else if (v.getId() == R.id.venue_edit) {
                v.getParent().requestDisallowInterceptTouchEvent(true);
                switch (event.getAction() & MotionEvent.ACTION_MASK) {
                    case MotionEvent.ACTION_DOWN:
                        selectKeyboardvnue();
                        break;
                    case MotionEvent.ACTION_UP:
                        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
                        v.getParent().requestDisallowInterceptTouchEvent(false);
                        break;
                }
            }


            return false;
        }
    };*/
