package com.yuyunchao.asus.myanimtest;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.RadialGradient;
import android.graphics.Shader;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.LinearLayout;

import com.yuyunchao.asus.myanimtest.shape.ShapeHolder;

import java.util.ArrayList;
import java.util.List;

public class PropertyAnimationActivity extends Activity implements View.OnClickListener{
    LinearLayout ll_property;
    Button btn_viewAnim,btn_xmlAnim,btn_fishAnim,btn_transition;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);// 去掉标题
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//            //透明导航栏
//            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
        setContentView(R.layout.activity_property_animation);
        initView();

    }

    private void initView() {
        ll_property = (LinearLayout) findViewById(R.id.ll_property);
        MyAnimView myAnim = new MyAnimView(this);
        btn_viewAnim = (Button) findViewById(R.id.btn_toview);
        btn_viewAnim.setOnClickListener(this);
        btn_xmlAnim = (Button) findViewById(R.id.btn_toxml);
        btn_xmlAnim.setOnClickListener(this);
        btn_fishAnim = (Button) findViewById(R.id.btn_tofishanim);
        btn_fishAnim.setOnClickListener(this);
        btn_transition = (Button) findViewById(R.id.btn_toTransitionanim);
        btn_transition.setOnClickListener(this);
        ll_property.addView(myAnim);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        switch (v.getId()){
            case R.id.btn_toview:
                intent.setClass(this, ViewAnimActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_toxml:
                intent.setClass(this, XMLAnimActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_tofishanim:
                intent.setClass(this, FishDrawableActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_toTransitionanim:
                intent.setClass(this, TransitionActivity.class);
                startActivity(intent);
                break;
        }
    }

    // 自定义的一个View
    class MyAnimView extends View {
        // 实现一个简单的默认构造器
        int red = 0xffff8080;// 浅红色 0x十六进制 ff透明度 ff0000颜色值
        int bule = 0xff8080ff;// 浅蓝色
        List<ShapeHolder> balls = new ArrayList<ShapeHolder>();
        ShapeHolder shapeHolder;
        public MyAnimView(Context context) {
            super(context);
            // 声明一个属性动画 通过ofInt实例化（对象，属性，开始的属性值，结束的属性值）
            ValueAnimator anim = ObjectAnimator.ofInt(this, "backgroundColor",
                    red, bule);
            // 动画时间长度
            anim.setDuration(3000);
            // 动画重复次数
            anim.setRepeatCount(ValueAnimator.INFINITE);
            // 动画重复模式 REVERSE反向重复（1--2--1--2）RESTART正向重复（1--2 1--2）
            anim.setRepeatMode(ValueAnimator.REVERSE);
            // 设置一个计算器 （传入一个颜色的计算器）
            anim.setEvaluator(new ArgbEvaluator());
            // 动画开始
            anim.start();

        }

        @Override
        public boolean onTouchEvent(MotionEvent event) {
            if (event.getAction() == MotionEvent.ACTION_DOWN
                    | event.getAction() == MotionEvent.ACTION_MOVE
                    && event.getY() < getHeight() && event.getX() < getWidth()) {
                ShapeHolder ball = addBalls(event.getX(), event.getY());
                balls.add(ball);
                float currX = event.getX();
                float currY = event.getY();
                float currWidth = ball.getWidth();
                float currHeight = ball.getHeight();
                float h = getHeight();
                float endY = h - 50f;
                long duration = 500;
                long dealduration = (long) (((h - currY) / h) * duration);
                // 小球掉落动画
                ValueAnimator downAnim = ObjectAnimator.ofFloat(ball, "y",
                        currY, endY);
                downAnim.setDuration(dealduration);
                downAnim.setInterpolator(new AccelerateInterpolator());
                // 小球压缩动画
                // 左移x坐标
                ValueAnimator squash1Anim = ObjectAnimator.ofFloat(ball, "x",
                        currX, currX - 15f);
                squash1Anim.setDuration(dealduration / 5);

                squash1Anim.setInterpolator(new DecelerateInterpolator());
                squash1Anim.setRepeatCount(1);
                squash1Anim.setRepeatMode(ValueAnimator.REVERSE);
                // 小球压缩动画
                // 宽度增加
                ValueAnimator squash2Anim = ObjectAnimator.ofFloat(ball,
                        "width", currWidth, currWidth + 50f);
                squash2Anim.setDuration(dealduration / 5);
                squash2Anim.setInterpolator(new DecelerateInterpolator());
                squash2Anim.setRepeatCount(1);
                squash2Anim.setRepeatMode(ValueAnimator.REVERSE);
                // 小球压缩动画
                // 下移y坐标
                ValueAnimator strech1Anim = ObjectAnimator.ofFloat(ball, "y",
                        endY, endY + 15f);
                strech1Anim.setDuration(dealduration / 5);
                strech1Anim.setInterpolator(new DecelerateInterpolator());
                strech1Anim.setRepeatCount(1);
                strech1Anim.setRepeatMode(ValueAnimator.REVERSE);
                // 小球压缩动画
                // 高度减少
                ValueAnimator strech2Anim = ObjectAnimator.ofFloat(ball,
                        "height", currHeight, currHeight - 15f);
                strech2Anim.setDuration(dealduration / 5);
                strech2Anim.setInterpolator(new DecelerateInterpolator());
                strech2Anim.setRepeatCount(1);
                strech2Anim.setRepeatMode(ValueAnimator.REVERSE);
                // 小球上升动画
                ValueAnimator upAnim = ObjectAnimator.ofFloat(ball, "y", endY,
                        currY);
                upAnim.setDuration(dealduration);
                upAnim.setInterpolator(new DecelerateInterpolator());
                // 小球消失动画
                ValueAnimator fadeAnim = ObjectAnimator.ofFloat(ball, "alpha",
                        1.0f, 0.0f);
                fadeAnim.setDuration(dealduration / 4);
                fadeAnim.setInterpolator(new LinearInterpolator());

                fadeAnim.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        // 把动画完成后的小球移出容器
                        balls.remove(((ObjectAnimator) animation).getTarget());
                    }
                });
                AnimatorSet bouncerSet = new AnimatorSet();
                bouncerSet.play(downAnim).before(strech1Anim);
                bouncerSet.play(strech1Anim).with(strech2Anim);
                bouncerSet.play(strech1Anim).with(squash2Anim);
                bouncerSet.play(strech1Anim).with(squash1Anim);
                bouncerSet.play(upAnim).after(strech1Anim);
                AnimatorSet fadeSet = new AnimatorSet();
                fadeSet.play(fadeAnim).after(bouncerSet);
                fadeSet.start();

            }
            return true;
        }

        // 绘制自定义的内容
        @Override
        protected void onDraw(Canvas canvas) {
            for (ShapeHolder ball : balls) {
                canvas.save();
                // canvas.save();到canvas.restore();之间绘制一帧的动画
                canvas.translate(ball.getX() - 50f, ball.getY() - 50f);
                ball.getShape().draw(canvas);
                canvas.restore();
            }
        }

        // 根据x.y坐标向屏幕中添加小球
        private ShapeHolder addBalls(float x, float y) {
            // 利用位运算得到随机颜色值
            int RED = (int) (Math.random() * 255) << 16;
            int GREEN = (int) (Math.random() * 255) << 8;
            int BULE = (int) (Math.random() * 255);
            int COLOR = 0xff000000 | RED | GREEN | BULE;
            int DARKCOLOR = 0xff000000 | (RED / 4) | (GREEN / 4) | (BULE / 4);

            OvalShape shape = new OvalShape();
            shape.resize(100f, 100f);
            ShapeDrawable drawable = new ShapeDrawable(shape);
            shapeHolder = new ShapeHolder(drawable);
            shapeHolder.setColor(COLOR);
            shapeHolder.setX(x);
            shapeHolder.setY(y);
            // 环形放射
            RadialGradient gradient = new RadialGradient(75f, 25f, 100, COLOR,
                    DARKCOLOR, Shader.TileMode.CLAMP);
            shapeHolder.setGradient(gradient);
            return shapeHolder;

        }
    }


}
