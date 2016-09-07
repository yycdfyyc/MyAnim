package com.yuyunchao.asus.myanimtest;

import android.R.xml;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

public class FishDrawableActivity extends Activity{
	private ImageView iv_fish_xml,iv_fish_code;
	private AnimationDrawable xmlanim,codeanim;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);// ȥ������
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_fishdrawable);
		initView();
		initAnim();
	}
	/**
	 * 
	 */
	private void initView(){
		iv_fish_xml=(ImageView) findViewById(R.id.iv_fish_XML);
		iv_fish_code=(ImageView) findViewById(R.id.iv_fish_Code);
		
		
	}
	@SuppressLint("NewApi") 
	private void initAnim(){
		//ͨ��xml��ʽʵ��֡����
		//�Ѷ�����XML���ó�ImageView�ı�����Ĭ���������ʾ��һ֡
		iv_fish_xml.setBackgroundResource(R.drawable.fishanim);
		//�õ�Animationdrawableʵ��
		xmlanim=(AnimationDrawable) iv_fish_xml.getBackground();
		
		//ͨ�����뷽ʽʵ��֡����
		codeanim=new AnimationDrawable();
		//��һ���֡
		codeanim.addFrame(getResources().getDrawable(R.drawable.fish_01), 200);
		codeanim.addFrame(getResources().getDrawable(R.drawable.fish_02), 200);
		codeanim.addFrame(getResources().getDrawable(R.drawable.fish_03), 200);
		codeanim.addFrame(getResources().getDrawable(R.drawable.fish_04), 200);
		codeanim.addFrame(getResources().getDrawable(R.drawable.fish_05), 200);
		codeanim.addFrame(getResources().getDrawable(R.drawable.fish_06), 200);
		codeanim.addFrame(getResources().getDrawable(R.drawable.fish_07), 200);
		codeanim.addFrame(getResources().getDrawable(R.drawable.fish_08), 200);
		codeanim.addFrame(getResources().getDrawable(R.drawable.fish_09), 200);
		codeanim.addFrame(getResources().getDrawable(R.drawable.fish_10), 200);
		codeanim.addFrame(getResources().getDrawable(R.drawable.fish_11), 200);
		codeanim.addFrame(getResources().getDrawable(R.drawable.fish_12), 200);
		codeanim.addFrame(getResources().getDrawable(R.drawable.fish_13), 200);
		codeanim.addFrame(getResources().getDrawable(R.drawable.fish_14), 200);
		codeanim.addFrame(getResources().getDrawable(R.drawable.fish_15), 200);
		codeanim.addFrame(getResources().getDrawable(R.drawable.fish_16), 200);
		 //�Ѷ��������ó�ImageView�ı�����Ĭ���������ʾ��һ֡
		iv_fish_code.setBackground(codeanim);
		
	}
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if(event.getAction()==MotionEvent.ACTION_DOWN){
			xmlanim.stop();
			xmlanim.selectDrawable(0);
			xmlanim.start();
			
			codeanim.stop();
			codeanim.selectDrawable(0);
			codeanim.start();
		}
		return true;
	}
}










