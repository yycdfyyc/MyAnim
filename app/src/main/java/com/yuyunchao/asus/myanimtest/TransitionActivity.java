package com.yuyunchao.asus.myanimtest;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.LayoutTransition;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.yuyunchao.asus.myanimtest.adapter.TransitionAdapter;

public class TransitionActivity extends Activity{
    Button button;
    LinearLayout ll_container;
    //展示item过渡动画的列表
    ListView lv_transition;
    int i_count;
    //自定义布局过渡动画
    LayoutTransition transition = new LayoutTransition();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transition);
        initView();
    }
    private void initView(){
        ll_container = (LinearLayout) findViewById(R.id.ll_container);
        lv_transition = (ListView) findViewById(R.id.lv_anim);
        lv_transition.setAdapter(new TransitionAdapter(this));
        setTransition();
    }
    private void initListener(){
    }

    /**
     * 向容器中添加按钮
     * @param view
     */
    public void addButton(View view){
        //数量递增
        i_count++;
        //实例化按钮
        button = new Button(this);
        //设置按钮文本
        button.setText("button "+i_count);
        //实例化一个布局参数
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,//width
                ViewGroup.LayoutParams.WRAP_CONTENT//height
        );
        //给待添加的按钮设置布局参数
        button.setLayoutParams(params);
        if(i_count>1){
            //添加到容器中
            ll_container.addView(button,1);
        }else {
            //添加到容器中
            ll_container.addView(button);
        }

    }

    /**
     * 从容器中删除按钮
     * @param view
     */
    public void removeButton(View view){
        //判断是否有足够的按钮
        if(i_count>0){
            //数量递减
            i_count--;
            //删除第一个按钮
            ll_container.removeViewAt(0);
        }
    }

    /**
     * 自定义过渡动画
     */
    private void setTransition(){
        //给指定容器设置过渡动画
        ll_container.setLayoutTransition(transition);

        //自定义添加View的动画
        //target参数填null，最终setAnimator的时候会自动设置
        PropertyValuesHolder appear_alphaAnim = PropertyValuesHolder.ofFloat("alpha", 0f, 1f);
        PropertyValuesHolder appear_rotationAnim = PropertyValuesHolder.ofFloat("rotationX", 90f, 0f);
        ValueAnimator appearAnim = ObjectAnimator.ofPropertyValuesHolder(button,appear_alphaAnim,appear_rotationAnim);
        //设置一个默认的播放时间(300ms)
        appearAnim.setDuration(transition.getDuration(LayoutTransition.APPEARING));
        //给过渡动画指定动画类型和动画效果
        transition.setAnimator(LayoutTransition.APPEARING, appearAnim);

        //自定义删除View的动画
        //target参数填null，最终setAnimator的时候会自动设置
        PropertyValuesHolder disappear_alphaAnim = PropertyValuesHolder.ofFloat("alpha", 1f, 0f);
        PropertyValuesHolder disappear_rotationAnim = PropertyValuesHolder.ofFloat("rotationY", 0f, 90f);
        ValueAnimator disappearAnim = ObjectAnimator.ofPropertyValuesHolder(button, disappear_alphaAnim, disappear_rotationAnim);
        //设置一个默认的播放时间(300ms)
        disappearAnim.setDuration(transition.getDuration(LayoutTransition.DISAPPEARING));
        //给过渡动画指定动画类型和动画效果
        transition.setAnimator(LayoutTransition.DISAPPEARING, disappearAnim);
        /**
         * 对于CHANGE_APPEARING和CHANGE_DISAPPEARING类型的过渡动画
         * 1. 必须使用 PropertyValuesHolder构建动画
         * 2. 必须同时指定left top right bottom属性 否则无法正确显示
         * 3. 动画结束后也需要将view改为原样，否则会出现BUG
         * 4. 构建ObjectAnimator时需要设置对象
         */
        PropertyValuesHolder pvhLeft =
                PropertyValuesHolder.ofInt("left", 0, 1);
        PropertyValuesHolder pvhRight =
                PropertyValuesHolder.ofInt("right", 0, 1);
        PropertyValuesHolder pvhBottom =
                PropertyValuesHolder.ofInt("bottom", 0, 1);
        PropertyValuesHolder pvhTop =
                PropertyValuesHolder.ofInt("top", 0, 1);

        //自定义CHANGE_APPEARING的动画
        //target参数填null，最终setAnimator的时候会自动设置
        PropertyValuesHolder change_appear_alphaAnim = PropertyValuesHolder.ofFloat("alpha", 0f, 1f);
        PropertyValuesHolder change_appear_scaleXAnim = PropertyValuesHolder.ofFloat("scaleX", 1f, 2f, 1f);
        ValueAnimator change_appearAnim = ObjectAnimator.ofPropertyValuesHolder(
                ll_container, pvhBottom, pvhLeft, pvhRight, pvhTop,
                change_appear_alphaAnim, change_appear_scaleXAnim);
        //设置一个默认的播放时间(300ms)
        change_appearAnim.setDuration(transition.getDuration(LayoutTransition.CHANGE_APPEARING));
        //为View复原
        change_appearAnim.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                ((View)((ObjectAnimator)animation).getTarget()).setScaleX(1f);
                ((View)((ObjectAnimator)animation).getTarget()).setAlpha(1f);
            }
        });
        //设置动画组之间的延迟
        transition.setStagger(LayoutTransition.CHANGE_APPEARING, 30);
        //给过渡动画指定动画类型和动画效果
        transition.setAnimator(LayoutTransition.CHANGE_APPEARING, change_appearAnim);


        //自定义CHANGE_DISAPPEARINGl的动画
        //target参数填null，最终setAnimator的时候会自动设置
        PropertyValuesHolder change_disappear_alphaAnim = PropertyValuesHolder.ofFloat("alpha", 0f, 1f);
        PropertyValuesHolder change_disappear_scaleXAnim = PropertyValuesHolder.ofFloat("scaleX", 1f, 2f, 1f);
        Animation change_disappear_rXAnim = AnimationUtils.loadAnimation(this, R.anim.right_anim);
        ValueAnimator change_disappearAnim = ObjectAnimator.ofPropertyValuesHolder(
                ll_container, pvhBottom, pvhLeft, pvhRight, pvhTop,
                change_disappear_alphaAnim, change_disappear_scaleXAnim);

        //设置一个默认的播放时间(300ms)
        change_disappearAnim.setDuration(transition.getDuration(LayoutTransition.CHANGE_DISAPPEARING));
        //为view复原
        change_disappearAnim.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                ((View)((ObjectAnimator)animation).getTarget()).setScaleX(1f);
                ((View)((ObjectAnimator)animation).getTarget()).setAlpha(1f);
            }
        });
        //设置动画组之间的延迟
        transition.setStagger(LayoutTransition.CHANGE_DISAPPEARING, 30);
        //给过渡动画指定动画类型和动画效果
        transition.setAnimator(LayoutTransition.CHANGE_DISAPPEARING, change_disappearAnim);

    }
}
