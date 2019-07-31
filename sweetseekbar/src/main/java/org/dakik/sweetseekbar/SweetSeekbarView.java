package org.dakik.sweetseekbar;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import org.dakik.sweetseekbar.interfaces.SweetSeekbarListener;
import org.dakik.sweetseekbar.utils.Animator;
import org.dakik.sweetseekbar.utils.Orientation;

public class SweetSeekbarView extends RelativeLayout implements View.OnTouchListener {
    private Orientation orientation = Orientation.VERTICAL;
    ImageView ivFront, ivBack;
    private Path path;
    float radiusTL = 10, radiusTR = 10, radiusBR = 10, radiusBL = 10;

    private SweetSeekbarListener listener;

    private int maxValue = 100, value, viewSize;
    private boolean enableBounceAnim = true;
    private Drawable frontResource, backResource;


    public SweetSeekbarView(Context context) {
        super(context);
        init(null);
    }

    public SweetSeekbarView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public SweetSeekbarView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public SweetSeekbarView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(attrs);
    }

    private void init(@Nullable AttributeSet attrs) {
        if (attrs != null) {
            initAttr(attrs);
        }
        if (orientation == Orientation.VERTICAL) {
            inflate(getContext(), R.layout.sweetseekbarview, this);
        } else {
            inflate(getContext(), R.layout.horizontalsweetseekbarview, this);
        }

        ivFront = findViewById(R.id.ivFront);
        ivBack = findViewById(R.id.ivBack);

        if (frontResource != null)
            ivFront.setBackground(frontResource);
        if (backResource != null)
            ivBack.setBackground(backResource);

        setOnTouchListener(this);


    }

    private void initAttr(AttributeSet attrs) {
        TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.SweetSeekbarView);

        if (a.hasValue(R.styleable.SweetSeekbarView_radius)) {
            radiusTL = a.getInt(R.styleable.SweetSeekbarView_radius, 10);
            radiusTR = radiusTL;
            radiusBR = radiusTL;
            radiusBL = radiusTL;
        }
        if (a.hasValue(R.styleable.SweetSeekbarView_radiusTL)) {
            radiusTL = a.getInt(R.styleable.SweetSeekbarView_radiusTL, 10);
        }
        if (a.hasValue(R.styleable.SweetSeekbarView_radiusTR)) {
            radiusTR = a.getInt(R.styleable.SweetSeekbarView_radiusTR, 10);
        }
        if (a.hasValue(R.styleable.SweetSeekbarView_radiusBL)) {
            radiusBL = a.getInt(R.styleable.SweetSeekbarView_radiusBL, 10);
        }
        if (a.hasValue(R.styleable.SweetSeekbarView_radiusBR)) {
            radiusBR = a.getInt(R.styleable.SweetSeekbarView_radiusBR, 10);
        }
        if (a.hasValue(R.styleable.SweetSeekbarView_maxValue)) {
            maxValue = a.getInt(R.styleable.SweetSeekbarView_maxValue, 100);
        }
        if (a.hasValue(R.styleable.SweetSeekbarView_enableBounceAnim)) {
            enableBounceAnim = a.getBoolean(R.styleable.SweetSeekbarView_enableBounceAnim, true);
        }
        if (a.hasValue(R.styleable.SweetSeekbarView_orientation)) {
            orientation = a.getInt(R.styleable.SweetSeekbarView_orientation, 0) == 0 ? Orientation.VERTICAL : Orientation.HORIZONTAL;
        }
        if (a.hasValue(R.styleable.SweetSeekbarView_frontTint)) {
            frontResource = a.getDrawable(R.styleable.SweetSeekbarView_frontTint);
        }
        if (a.hasValue(R.styleable.SweetSeekbarView_backTint)) {
            backResource = a.getDrawable(R.styleable.SweetSeekbarView_backTint);
        }


        a.recycle();
    }

    public void setFrontTintColor(int color) {
        ivFront.setBackgroundColor(color);
    }

    public void setBackTintColor(int color) {
        ivBack.setBackgroundColor(color);
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

    private float touchStartPos, size;

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (isEnableBounceAnim()) {
                    Animator.bounceToBig(this);
                }

                if (orientation == Orientation.VERTICAL) {
                    viewSize = ivBack.getHeight();

                    touchStartPos = event.getY();
                    size = ivFront.getLayoutParams().height;
                } else {
                    viewSize = ivBack.getWidth();
                    touchStartPos = event.getX();
                    size = ivFront.getLayoutParams().width;
                }

                if (listener != null) {
                    value = (int) ((maxValue * size) / viewSize);
                    listener.onStart(value);
                }

                break;
            case MotionEvent.ACTION_MOVE:
                if (orientation == Orientation.VERTICAL) {
                    ivFront.getLayoutParams().height = (int) (size + (touchStartPos - event.getY()));
                    ivFront.requestLayout();
                    if (ivFront.getLayoutParams().height > viewSize)
                        ivFront.getLayoutParams().height = viewSize;
                    if (ivFront.getLayoutParams().height < 0)
                        ivFront.getLayoutParams().height = 0;
                    value = ((maxValue * ivFront.getLayoutParams().height) / viewSize);
                } else {
                    ivFront.getLayoutParams().width = (int) (size + (event.getX() - touchStartPos));
                    ivFront.requestLayout();
                    if (ivFront.getLayoutParams().width > viewSize)
                        ivFront.getLayoutParams().width = viewSize;
                    if (ivFront.getLayoutParams().width < 0)
                        ivFront.getLayoutParams().width = 0;
                    value = ((maxValue * ivFront.getLayoutParams().width) / viewSize);
                }

                if (listener != null) {

                    listener.onMove(value);
                }
                break;
            case MotionEvent.ACTION_UP:
                if (orientation == Orientation.VERTICAL) {
                    value = ((maxValue * ivFront.getLayoutParams().height) / viewSize);
                } else {
                    value = ((maxValue * ivFront.getLayoutParams().width) / viewSize);
                }
                if (listener != null) {

                    listener.onEnd(value);
                }
                if (isEnableBounceAnim()) {
                    Animator.bounceToSmall(this);
                }
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
                viewSize = ivBack.getHeight();
                float ratio = (float) value / (float) maxValue;
                ivFront.getLayoutParams().height = (int) (viewSize * ratio);
                ivFront.requestLayout();
            }
        });

        this.value = value;
    }

    public void setRadius(float tl, float tr, float br, float bl) {
        radiusTR = tr;
        radiusBL = bl;
        radiusBR = br;
        radiusTL = tl;
    }

    public void setBounceAnim(boolean enableBounceAnim) {
        this.enableBounceAnim = enableBounceAnim;
    }

    public boolean isEnableBounceAnim() {
        return enableBounceAnim;
    }


}
