package com.pakkalocal.cardmakerui.utilsdata;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.util.DisplayMetrics;

import com.pakkalocal.cardmakerui.R;

/**
 * Created by 2117 on 6/13/2018.
 */

public class UtilsData {
    public static int scheight_u, scwidth_u;
    public static boolean isinternet = false;
    public static int[] frams_allverdata = {R.drawable.vcard1,
            R.drawable.vcard2, R.drawable.vcard3, R.drawable.vcard4,
            R.drawable.vcard5, R.drawable.vcard6, R.drawable.vcard7,
            R.drawable.vcard8, R.drawable.vcard9,
    };

    public static int[] img_acrpsd = {R.drawable.frame_1,
            R.drawable.card02, R.drawable.card03, R.drawable.card04,
            R.drawable.card05, R.drawable.card06, R.drawable.card07,
            R.drawable.card08, R.drawable.card09,
    };
    public static int[] img_acrpsd_mask = {R.drawable.maskfrm1,
            R.drawable.card02, R.drawable.card03, R.drawable.card04,
            R.drawable.card05, R.drawable.card06, R.drawable.card07,
            R.drawable.card08, R.drawable.card09,
    };

    public static int[] frams_allhordata = {R.drawable.card01,
            R.drawable.card02, R.drawable.card03, R.drawable.card04,
            R.drawable.card05, R.drawable.card06, R.drawable.card07,
            R.drawable.card08, R.drawable.card09,
    };
    public static int[] stkrsData = {R.drawable.sticker1,
            R.drawable.sticker2, R.drawable.sticker3, R.drawable.sticker4,
            R.drawable.sticker5, R.drawable.sticker6, R.drawable.sticker7,
            R.drawable.sticker8, R.drawable.sticker9, R.drawable.sticker10,
            R.drawable.sticker11,
            R.drawable.sticker12, R.drawable.sticker13, R.drawable.sticker14,
            R.drawable.sticker15, R.drawable.sticker16, R.drawable.sticker17,
            R.drawable.sticker18,
    };

    public static int textPos;

    public static Bitmap bfcropbmp;
    public static boolean isCameraImage;
    public static int ven_chk;
    public static String nam1_tx,nam2_tx,venud_tx,time_tx,date_tx;

    public static int dpToPx(int dp, Context context) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        return Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
    }

    public static String[] colorCodes = new String[]{"#b1bc20", "#71c9ca", "#f47b1e", "#f02e25", "#ee028b", "#00a4e4", "#ffd004",
            "#ff5d04", "#ec407a", "#ab46bc", "#26c7db", "#ffc928", "#9ccc66"};

    public static String[] font_styles = {
            "fonts/Beyond Wonderland.ttf", "fonts/BrushScriptStd.otf", "fonts/FancyPantsNF.otf", "fonts/Fiddums Family.ttf",
            "fonts/Fortunaschwein_complete.ttf", "fonts/FUNDR__.TTF", "fonts/HoboStd.otf", "fonts/hotpizza.ttf",
            "fonts/micross.ttf", "fonts/NuevaStd-Bold.otf", "fonts/NuevaStd-BoldCond.otf", "fonts/NuevaStd-BoldCondItalic.otf",
            "fonts/NuevaStd-Cond.otf", "fonts/NuevaStd-CondItalic.otf", "fonts/NuevaStd-Italic.otf",
            "fonts/Road_Rage.otf", "fonts/Scratch X.ttf", "fonts/segoepr.ttf",
            "fonts/segoeprb.ttf", "fonts/TEXAT BOLD PERSONAL USE__.otf", "fonts/Trumpit.otf"
    };


    public static boolean isLangSupported(Context context, String text) {
        int sdk = android.os.Build.VERSION.SDK_INT;
        int w = 200, h = 80;
        Resources resources = context.getResources();
        float scale = resources.getDisplayMetrics().density;
        Bitmap.Config conf = Bitmap.Config.ARGB_8888;
        Bitmap bitmap = Bitmap.createBitmap(w, h, conf); // this creates a MUTABLE bitmap
        Bitmap orig = bitmap.copy(conf, false);
        Canvas canvas = new Canvas(bitmap);
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.rgb(0, 0, 0));
        paint.setTextSize((int) (14 * scale));

        // draw text to the Canvas center
        Rect bounds = new Rect();
        paint.getTextBounds(text, 0, text.length(), bounds);
        int x = (bitmap.getWidth() - bounds.width()) / 2;
        int y = (bitmap.getHeight() + bounds.height()) / 2;

        canvas.drawText(text, x, y, paint);
        boolean res = false;
        if (sdk < android.os.Build.VERSION_CODES.HONEYCOMB) {
            res = !(orig == bitmap);
        } else {
            res = !orig.sameAs(bitmap);
        }
        orig.recycle();
        bitmap.recycle();
        return res;
    }

    public static void displayAlert(Activity activity, String titleText, String messageText) {
        AlertDialog.Builder alertDialogBuilder =
                new AlertDialog.Builder(activity);
        alertDialogBuilder.setTitle(titleText)
                .setMessage(messageText)
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                }).show();
    }

    public static  Bitmap textAsBitmap(String text, float textSize, int textColor, Typeface typeface, int style) {
        Paint paint = new Paint();
        paint.setTextSize(textSize);
        paint.setColor(textColor);
        paint.setAntiAlias(true);
        paint.setTypeface(Typeface.create(typeface, style));
        paint.setTextAlign(Paint.Align.LEFT);
        float baseline = -paint.ascent(); // ascent() is negative
        int width = (int) (paint.measureText(text) + 0.5f); // round
        int height = (int) (baseline + paint.descent() + 0.5f);
        Bitmap image = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(image);
        canvas.drawText(text, 0, baseline, paint);
        return image;
    }

}
