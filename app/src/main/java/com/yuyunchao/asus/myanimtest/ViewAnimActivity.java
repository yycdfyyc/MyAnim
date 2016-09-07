package com.yuyunchao.asus.myanimtest;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.TextView;

public class ViewAnimActivity extends Activity{
	TextView view_tv1,view_tv2;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);// ȥ������
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,//ȫ��ģʽ
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_viewanim);
		initView();
		initAnim();
		
	}
	/**
	 * װ�ؿؼ�
	 */
	private void initView(){
		view_tv1=(TextView) findViewById(R.id.viewanim_tv1);
		view_tv2=(TextView) findViewById(R.id.viewanim_tv2);
	}
	/**
	 * װ�ض���
	 */
	private void initAnim(){
		//ͨ��XML��ʽ���ض���
		Animation anim=AnimationUtils.loadAnimation(this, R.anim.viewanim);
		//���ö�������ʱ�ļ��� ʹ��ѭ��
		anim.setAnimationListener(new AnimationListener() {
			
			@Override
			public void onAnimationStart(Animation animation) {
				
			}
			
			@Override
			public void onAnimationRepeat(Animation animation) {
				
			}
			
			@Override
			public void onAnimationEnd(Animation animation) {
				view_tv1.startAnimation(animation);
			}
		});
		view_tv1.startAnimation(anim);
		
		
		//ͨ�����뷽ʽ���ض���
		//����һ����ת����
		RotateAnimation rotateAnimation=new RotateAnimation(
				0, 360, //��ת�Ƕȿ�ʼֵ0������ֵ360
				Animation.RELATIVE_TO_SELF, //�������������x����ת
				0.5f, //����������x��50%
				Animation.RELATIVE_TO_SELF, //�������������y����ת
				0.5f  //����������y��50%
				);
		rotateAnimation.setDuration(1000);//����ʱ��
		rotateAnimation.setInterpolator(new AccelerateInterpolator());//������ֵ����Ϊ����
		//����һ��ƽ�ƶ���
		TranslateAnimation translateAnimation=new TranslateAnimation(
				0, 
				400, 
				0, 
				400);
		translateAnimation.setDuration(1000);
		translateAnimation.setInterpolator(new AccelerateInterpolator());
		translateAnimation.setStartOffset(1000);//���ö������ӳ�
		//����һ�����Ŷ���
		ScaleAnimation scaleAnimation=new ScaleAnimation(
				1.0f, 3.0f, 1.0f, 3.0f, 
				Animation.RELATIVE_TO_SELF, 0.5f, 
				Animation.RELATIVE_TO_SELF, 0.5f);
		scaleAnimation.setDuration(1000);
		scaleAnimation.setInterpolator(new AccelerateInterpolator());
		scaleAnimation.setStartOffset(1000);
		//����һ��͸���ȶ���
		AlphaAnimation alphaAnimation=new AlphaAnimation(1.0f,0.0f);
		alphaAnimation.setDuration(1000);
		alphaAnimation.setInterpolator(new AccelerateInterpolator());
		alphaAnimation.setStartOffset(1000);
		//����������������
		final AnimationSet allset=new AnimationSet(true);//�ܼ���
		AnimationSet childsSet=new AnimationSet(true);//�Ӽ���
		//�����������ŵ��Ӽ�����
		childsSet.addAnimation(scaleAnimation);
		childsSet.addAnimation(alphaAnimation);
		childsSet.addAnimation(translateAnimation);
		//��һ���������Ϻ�һ�������ŵ��ܼ�����
		allset.addAnimation(rotateAnimation);
		allset.addAnimation(childsSet);
		//���ö�������ʱ�ļ��� ʹ��ѭ��
		allset.setAnimationListener(new AnimationListener() {
			
			@Override
			public void onAnimationStart(Animation animation) {
				
			}
			
			@Override
			public void onAnimationRepeat(Animation animation) {
				
			}
			
			@Override
			public void onAnimationEnd(Animation animation) {
				view_tv2.startAnimation(allset);
//				//���������ķ�ʽ2
//				//ֹͣ���������趯����״̬
//				allSet.reset();
//				//���²��Ŷ���
//				allSet.start();
			}
		});
		//��������
		view_tv2.startAnimation(allset);
//		//����������ʽ2
//		//���ؼ����ö���
//		view_tv2.setAnimation(allset);
//		//��������
//		allset.start();
	}
	
}
