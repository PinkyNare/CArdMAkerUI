package com.pakkalocal.cardmakerui.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Shader;
import android.view.MotionEvent;
import android.view.View;

import com.pakkalocal.cardmakerui.utilsdata.UtilsData;

public class FrmaCropView extends View {

    Paint paintmode;

    Context context;
    private int viewWidth;
    private int viewhHeight;
    Canvas mcCanvas;
    Bitmap bmpsetd;
    Bitmap mbitmap, sebmpfrm;


    // These matrices will be used to move and zoom image
    public Matrix matrix = new Matrix();
    public Matrix savedMatrix = new Matrix();
    Matrix inverse;
    // We can be in one of these 3 states
    static final int NONE = 0;
    static final int DRAG = 1;
    static final int ZOOM = 2;
    int mode = NONE;

    // Remember some things for zooming
    PointF start = new PointF();
    PointF mid = new PointF();
    float oldDist = 1f;
    //    private PointF midPoint = new PointF();
    float[] lastEvent = null;
    float d = 0f;
    float newRot = 0f;
    Bitmap mask;

    public FrmaCropView(Context context, Bitmap mbit, int srcW, int srcH) {
        super(context);
        // TODO Auto-generated constructor stub
        this.context = context;
        mbitmap = mbit;
        matrix = new Matrix();
        paintmode = new Paint();
        paintmode.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        if(srcW>5)
        {
            viewWidth = srcW;
            viewhHeight = srcH;
        }
        else {
            viewWidth = UtilsData.scwidth_u;
            viewhHeight = UtilsData.scheight_u;
        }

    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        // TODO Auto-generated method stub
        viewWidth = w;
        viewhHeight = h;
        bmpsetd = Bitmap.createBitmap(mbitmap.getWidth(),
                mbitmap.getHeight(), Bitmap.Config.ARGB_8888);
        float sx = (viewWidth * 1.0f) / (mbitmap.getWidth() * 1.0f);
        float sy = (viewhHeight * 1.0f) / (mbitmap.getHeight() * 1.0f);
        float scale = (sx > sy) ? sx : sy;
        matrix.setScale(scale, scale);
        mcCanvas = new Canvas(bmpsetd);
        inverse = new Matrix();
        matrix.invert(inverse);
        mcCanvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);

        mcCanvas.drawBitmap(mbitmap, 0, 0, null);
        if (sebmpfrm!=null)
        mcCanvas.drawBitmap(sebmpfrm, 0, 0, null);

        super.onSizeChanged(w, h, oldw, oldh);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        // TODO Auto-generated method stub
        if (bmpsetd != null) {
            canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);

            mcCanvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
            mcCanvas.drawBitmap(getmixpixbitmap(mbitmap, matrix), 0, 0, null);
            mcCanvas.drawBitmap(sebmpfrm, 0, 0, null);
            canvas.drawBitmap(bmpsetd, 0, 0, null);
        }


        super.onDraw(canvas);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // TODO Auto-generated method stub
        switch (event.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN:
                savedMatrix.set(matrix);
                start.set(event.getX(), event.getY());
                mode = DRAG;
                break;
            case MotionEvent.ACTION_POINTER_DOWN:
                oldDist = spacing(event);
                if (oldDist > 10f) {
                    savedMatrix.set(matrix);
                    midPoint(mid, event);
                    mode = ZOOM;
                }
                lastEvent = new float[4];
                lastEvent[0] = event.getX(0);
                lastEvent[1] = event.getX(1);
                lastEvent[2] = event.getY(0);
                lastEvent[3] = event.getY(1);
                d = rotation(event);

                break;
            case MotionEvent.ACTION_MOVE:

                if (mode == DRAG) {

                    matrix.set(savedMatrix);
                    matrix.postTranslate(event.getX() - start.x,
                            event.getY() - start.y);
                } else if (mode == ZOOM) {
                    float newDist = spacing(event);
                    if (newDist > 10f) {
                        matrix.set(savedMatrix);
                        float scale = newDist / oldDist;
                        matrix.postScale(scale, scale, mid.x, mid.y);
                    }
                    if (lastEvent != null) {
                        newRot = rotation(event);
                        float r1 = newRot - d;
                        matrix.postRotate(r1, viewWidth / 2,
                                viewhHeight / 2);
                    }

                }

                break;
            case MotionEvent.ACTION_UP:

                break;

            case MotionEvent.ACTION_POINTER_UP:
                lastEvent = null;
                mode = NONE;
                break;
        }
        invalidate();
        return true;

    }


    private float rotation(MotionEvent event) {
        double delta_x = (event.getX(0) - event.getX(1));
        double delta_y = (event.getY(0) - event.getY(1));
        double radians = Math.atan2(delta_y, delta_x);

        return (float) Math.toDegrees(radians);
    }

    /**
     * Determine the space between the first two fingers
     */
    @SuppressLint("FloatMath")
    private float spacing(MotionEvent event) {
        float x = event.getX(0) - event.getX(1);
        float y = event.getY(0) - event.getY(1);
        return (float) Math.sqrt(x * x + y * y);
    }

    /**
     * Calculate the mid point of the first two fingers
     */
    private void midPoint(PointF point, MotionEvent event) {
        float x = event.getX(0) + event.getX(1);
        float y = event.getY(0) + event.getY(1);
        point.set(x / 2, y / 2);
    }


    // ////////////////


    public Bitmap getmixpixbitmap(Bitmap srcbmp, Matrix matrix) {
        Bitmap result = Bitmap.createBitmap(mask.getWidth(), mask.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas mCanvas = new Canvas(result);
        mCanvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
//        or src_in
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.MULTIPLY));
//        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.XOR));

        mCanvas.drawBitmap(srcbmp, matrix, null);
        mCanvas.drawBitmap(mask, 0, 0, paint);
        paint.setXfermode(null);

        return result;
    }

    public Bitmap getBitmap() {

        return bmpsetd;
    }

    public Bitmap getResizedBitmap(Bitmap bitmap, int newWidth, int newHeight) {
        Bitmap resizedBitmap = Bitmap.createBitmap(newWidth, newHeight,
                Bitmap.Config.ARGB_8888);
        float scaleX = newWidth / (float) bitmap.getWidth();
        float scaleY = newHeight / (float) bitmap.getHeight();
        Matrix scaleMatrix = new Matrix();
        scaleMatrix.setScale(scaleX, scaleY, 0, 0);
        Canvas canvas = new Canvas(resizedBitmap);
        canvas.setMatrix(scaleMatrix);
        canvas.drawBitmap(bitmap, 0, 0, new Paint(Paint.FILTER_BITMAP_FLAG));

        return resizedBitmap;
    }


    public void setFrameData(Bitmap frmbmpt, Bitmap msk) {
        sebmpfrm = getResizedBitmap(frmbmpt, viewWidth, viewhHeight);
        mask = getResizedBitmap(msk, viewWidth, viewhHeight);
        if(mcCanvas!=null) {
            mcCanvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
            mcCanvas.drawBitmap(mbitmap, 0, 0, null);
            mcCanvas.drawBitmap(sebmpfrm, 0, 0, null);
        }
    }
}
