package com.example.chacab;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

public class Slider extends View {

    //Valeur par défaut
    final static float DEF_CERCLE1_D = 500;
    final static float DEF_CERCLE2_D = 300;
    final static float DEF_CERCLE3_D = 150;

    //Dimension min
    final static float MIN_CERCLE1_D = 500;
    final static float MIN_CERCLE2_D = 300;
    final static float MIN_CERCLE3_D = 150;

    //Float Diametre
    private float mCursor1Diameter;
    private float mCursor2Diameter;
    private float mCursor3Diameter;

    // Couleurs
    private int mCERCLE1;
    private int mCERCLE2;
    private int mCERCLE3;

    // Pinceaux
    private Paint mC1Color = null;
    private Paint mC2Color = null;
    private Paint mC3Color = null;

    // Valeur du Slider
    private float mValue = 50;

    // Valeur minimale du Slider
    private float mMin = 0;

    // Etat du Slider
    private boolean mEnabled = true;

    // Valeur maximale du Slider
    private float mMax = 100;

    // Etats spécifiques aux actions
    private boolean isDoubleClick = false;

    private boolean moveActionDisabled = false;
    /**
     * Constructeur statique (via XML)
     * @param context : contexte du slider
     */
    public Slider(Context context) {
        super(context);
        init(context,null);
    }
    /**
     * Initialisation du Slider : code mutualisé entre constructeurs
     * @param context
     * @param attributeSet
     */
    private void init(Context context, AttributeSet attributeSet){

        // Initialisation des dimensions par défaut en pixel
        mCursor1Diameter = dpToPixel(DEF_CERCLE1_D);
        mCursor2Diameter = dpToPixel(DEF_CERCLE2_D);
        mCursor3Diameter = dpToPixel(DEF_CERCLE3_D);

// pinceau
        mC1Color = new Paint();
        mC2Color = new Paint();
        mC3Color = new Paint();

        // Suppression du repliement
        mC1Color.setAntiAlias(true);
        mC2Color.setAntiAlias(true);
        mC3Color.setAntiAlias(true);

        // Application du style (plein)
        mC1Color.setStyle(Paint.Style.STROKE);
        mC2Color.setStyle(Paint.Style.STROKE);
        mC3Color.setStyle(Paint.Style.FILL_AND_STROKE);

        // Couleurs par défaut
        mCERCLE1 = ContextCompat.getColor(context, R.color.colorAccent);
        mCERCLE2 = ContextCompat.getColor(context, R.color.colorPrimary);
        mCERCLE3 = ContextCompat.getColor(context, R.color.colorSecondary);

        mC1Color.setColor(mCERCLE1);
        mC2Color.setColor(mCERCLE2);
        mC3Color.setColor(mCERCLE3);


        // Spécification des terminaisons
      //  mBarPaint.setStrokeCap(Paint.Cap.ROUND);
      //  mValueBarPaint.setStrokeCap(Paint.Cap.ROUND);



        // Fixe les largeurs
        //mBarPaint.setStrokeWidth(mBarWidth);
       // mValueBarPaint.setStrokeWidth(mBarWidth);

        // Initialisation des dimensions minimales
      //  int minWidth = (int) dpToPixel(MIN_CURSOR_DIAMETER)+ getPaddingLeft() + getPaddingRight();
       // int minHeight = (int) dpToPixel(MIN_BAR_LENGTH + MIN_CURSOR_DIAMETER) + getPaddingTop() + getPaddingBottom();

       // setMinimumHeight(minHeight );
       // setMinimumWidth(minWidth);

    }

    /**
     * Wrapper passage DIP en pixels
     * @param valueInDp dimension en DIP
     * @return dimension en pixels
     */
    private float dpToPixel(float valueInDp) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, valueInDp, getResources().getDisplayMetrics());
    }

    /**
     *  Conversion d'une valeur du Slider en une position de la poignée en pixels
     * @param value : valeur à convertir
     * @return : point associé au centre de la poignée
     */
    private Point toPos(float value) {
        int x, y;
        y = (int) ((1 - valueToRatio(value)) * 360 );
        x = (int) (Math.max(mCursor2Diameter, mCursor3Diameter) - mCursor3Diameter);
        x += getPaddingLeft();
        y += getPaddingTop();

        return new Point(x, y);
    }

    public Slider(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * Normalisation de la valeur entre 0 et 1.
     * @param value
     * @return
     */
    private float valueToRatio(float value){

        return (value - mMin) / (mMax - mMin);
    }



    /**
     * Dénormalisation vers la valeur du curseur
     * @param ratio : valeur entre 0 (min) et 1 (max)
     * @return valeur associée au Slider
     */
    private float ratioToValue(float ratio) {
        return ratio * (mMax - mMin) + mMin;
    }



    /**
     * Convertions d'une position écran en une valeur du Slider
     * @param position : position écran
     * @return valeur slider
     */
    private float toValue(Point position){
        float ratio = 1 - (position.y -getPaddingTop()-mCursor2Diameter/2);
        if(ratio < 0) ratio = 0;
        if(ratio > 1) ratio = 1;
        return ratioToValue(ratio);

    }


    /**
     * Redéfinition de la méthode de tracé du Slider
     * @param canvas : zone de tracé
     */
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Point p1, p2;
        p1 = toPos(mMin);
        p2 = toPos(mMax);

        // positionnement du curseur et de la barre d'amplitude
        Point cursorPosition = toPos(mValue);

        canvas.drawCircle(cursorPosition.x, cursorPosition.y, mCursor1Diameter , mC1Color);
        canvas.drawCircle(cursorPosition.x, cursorPosition.y, mCursor2Diameter , mC2Color);
        canvas.drawCircle(cursorPosition.x, cursorPosition.y, mCursor3Diameter , mC3Color);
    }
}
