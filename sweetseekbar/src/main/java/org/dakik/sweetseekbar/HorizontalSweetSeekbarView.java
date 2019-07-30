package org.dakik.sweetseekbar;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.RectF;
import android.os.Build;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import org.dakik.sweetseekbar.interfaces.SweetSeekbarListener;

public class HorizontalSweetSeekbarView extends RelativeLayout implements View.OnTouchListener {
    ImageView ivFront,ivBack;
    private Path path;
    float radiusTL=10,radiusTR=10, radiusBR =10, radiusBL = 10;

    private SweetSeekbarListener listener;

    private int maxValue = 100,value, viewWidth;


    public HorizontalSweetSeekbarView(Context context) {
        super(context);
        init(null);
    }

    public HorizontalSweetSeekbarView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public HorizontalSweetSeekbarView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public HorizontalSweetSeekbarView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(attrs);
    }

    private void init(@Nullable AttributeSet attrs)
    {
        inflate(getContext(),R.layout.horizontalsweetseekbarview,this);
        ivFront = findViewById(R.id.ivFront);
        ivBack = findViewById(R.id.ivBack);



        setOnTouchListener(this);

        if (attrs!=null)
        {
            initAttr(attrs);
        }

    }

    private void initAttr(AttributeSet attrs)
    {
        TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.SweetSeekbarView);

        if (a.hasValue(R.styleable.HorizontalSweetSeekbarView_radius))
        {
            radiusTL = a.getInt(R.styleable.HorizontalSweetSeekbarView_radius,10);
            radiusTR = radiusTL;
            radiusBR = radiusTL;
            radiusBL = radiusTL;
        }
        if (a.hasValue(R.styleable.HorizontalSweetSeekbarView_radiusTL))
        {
            radiusTL = a.getInt(R.styleable.HorizontalSweetSeekbarView_radiusTL,10);
        } if (a.hasValue(R.styleable.HorizontalSweetSeekbarView_radiusTR))
        {
            radiusTR = a.getInt(R.styleable.HorizontalSweetSeekbarView_radiusTR,10);
        } if (a.hasValue(R.styleable.HorizontalSweetSeekbarView_radiusBL))
        {
            radiusBL = a.getInt(R.styleable.HorizontalSweetSeekbarView_radiusBL,10);
        } if (a.hasValue(R.styleable.HorizontalSweetSeekbarView_radiusBR))
        {
            radiusBR = a.getInt(R.styleable.HorizontalSweetSeekbarView_radiusBR,10);
        }if (a.hasValue(R.styleable.HorizontalSweetSeekbarView_maxValue))
        {
            maxValue = a.getInt(R.styleable.HorizontalSweetSeekbarView_maxValue,100);
        }



        a.recycle();
    }


    @Override
    protected void onSizeChanged(int width, int height, int oldWidth, int oldHeight) {
        super.onSizeChanged(width, height, oldWidth, oldHeight);


        this.path = new Path();
        float[] radii = {radiusTL, radiusTL, radiusTR, radiusTR, radiusBR, radiusBR, radiusBL, radiusBL};
        this.path.addRoundRect(new RectF(0, 0, width, height), radii, Path.Direction.CW);

    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        if (this.path != null) {
            canvas.clipPath(this.path);
        }
        super.dispatchDraw(canvas);
    }

    public SweetSeekbarListener getListener() {
        return listener;
    }

    public void setListener(SweetSeekbarListener listener) {
        this.listener = listener;
    }

    private float touchStartPos, width;
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction())
        {
            case MotionEvent.ACTION_DOWN:
                viewWidth =ivBack.getWidth();
                scaleView(1,1.1f);
                touchStartPos = event.getX();
                width = ivFront.getLayoutParams().width;
                if (listener!=null)
                {
                    value = (int) ((maxValue* width)/ viewWidth);
                    listener.onStart(value);
                }

                break;
            case MotionEvent.ACTION_MOVE:

                ivFront.getLayoutParams().width = (int) (width + (event.getX()-touchStartPos));
                ivFront.requestLayout();
                if (ivFront.getLayoutParams().width> viewWidth)
                    ivFront.getLayoutParams().width= viewWidth;
                if (ivFront.getLayoutParams().width<0)
                    ivFront.getLayoutParams().width=0;
                if (listener!=null)
                {
                    value =  ((maxValue*ivFront.getLayoutParams().width)/ viewWidth);
                    listener.onMove(value);
                }
                break;
            case MotionEvent.ACTION_UP:
                if (listener!=null)
                {
                    value =  ((maxValue*ivFront.getLayoutParams().width)/ viewWidth);
                    listener.onEnd(value);
                }
                scaleView(1.1f,1f);
                break;
        }

        return true;
    }

    public void setMaxValue(int maxValue) {
        this.maxValue = maxValue;
    }

    public int getMaxValue() {
        return maxValue;
    }

    public int getValue() {
        return value;
    }

    public void setValue(final int value) {
        post(new Runnable() {
            @Override
            public void run() {
                viewWidth =ivBack.getWidth();
                float ratio = (float)value/(float)maxValue;
                ivFront.getLayoutParams().width = (int) (viewWidth * ratio);
                ivFront.requestLayout();
            }
        });

        this.value = value;
    }

    public void setRadius(float tl, float tr, float br, float bl)
    {
        radiusTR=tr;
        radiusBL=bl;
        radiusBR=br;
        radiusTL=tl;
    }

    public void scaleView(float startScale, float endScale) {
        Animation anim = new ScaleAnimation(
                startScale, endScale, // Start and end values for the X axis scaling
                startScale, endScale, // Start and end values for the Y axis scaling
                Animation.RELATIVE_TO_SELF, 0.5f, // Pivot point of X scaling
                Animation.RELATIVE_TO_SELF, 0.5f); // Pivot point of Y scaling
        anim.setFillAfter(true); // Needed to keep the result of the animation
        anim.setDuration(100);
        startAnimation(anim);
    }
}
