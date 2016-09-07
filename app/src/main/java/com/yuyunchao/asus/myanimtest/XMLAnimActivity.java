package com.yuyunchao.asus.myanimtest;



import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ArgbEvaluator;
import android.animation.Keyframe;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.RadialGradient;
import android.graphics.Shader;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.yuyunchao.asus.myanimtest.shape.ShapeHolder;

import java.util.ArrayList;

public class XMLAnimActivity extends Activity {
	private LinearLayout ll_context;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);// ȥ������
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_xmlanim);
		initView();
	}
	private void initView() {
		ll_context = (LinearLayout) findViewById(R.id.ll_context2);
		MyAnimView myAnim = new MyAnimView(this);
		ll_context.addView(myAnim);
	}

	class MyAnimView extends View {
		int red = 0xffff8080;// ǳ��ɫ 0xʮ������ ff͸���� ff0000��ɫֵ
		int bule = 0xff8080ff;// ǳ��ɫ
		ShapeHolder shapeHolder;
		ShapeHolder colorBall;
		ArrayList<ShapeHolder> balls=new ArrayList<ShapeHolder>();
		public MyAnimView(Context context) {
			super(context);
			ValueAnimator anim = ObjectAnimator.ofInt(this, "backgroundColor",
					red, bule);
			anim.setDuration(3000);

			anim.setRepeatCount(ValueAnimator.INFINITE);
			anim.setRepeatMode(ValueAnimator.REVERSE);
			anim.setEvaluator(new ArgbEvaluator());
			anim.start();
			addColorBall();
		}
		private void addColorBall(){
			OvalShape circle=new OvalShape();
			circle.resize(100f, 100f);
			ShapeDrawable drawable=new ShapeDrawable(circle);
			colorBall=new ShapeHolder(drawable);
			colorBall.setX(0f);
			colorBall.setY(0f);
			Keyframe ky1=Keyframe.ofFloat(0f,100f);
			Keyframe ky2=Keyframe.ofFloat(0.5f,200f);
			Keyframe ky3=Keyframe.ofFloat(1f,50f);
			PropertyValuesHolder widthHolder=PropertyValuesHolder.
					ofKeyframe("width", ky1,ky2,ky3);
			Keyframe ky4=Keyframe.ofFloat(0f,100f);
			Keyframe ky5=Keyframe.ofFloat(0.5f,200f);
			Keyframe ky6=Keyframe.ofFloat(1f,50f);
			PropertyValuesHolder heightHolder=PropertyValuesHolder.
					ofKeyframe("height", ky4,ky5,ky6);
			Keyframe ky7=Keyframe.ofInt(0f,0xffff8080);
			Keyframe ky8=Keyframe.ofInt(0.5f,0xff80ff80);
			Keyframe ky9=Keyframe.ofInt(1f,0xff8080ff);
			PropertyValuesHolder colorHolder=PropertyValuesHolder.
					ofKeyframe("color", ky7,ky8,ky9);
			colorHolder.setEvaluator(new ArgbEvaluator());
			
			ObjectAnimator colorBallAnim=ObjectAnimator.ofPropertyValuesHolder(
					colorBall, widthHolder,heightHolder,colorHolder);
			colorBallAnim.setDuration(3500);
			colorBallAnim.setRepeatMode(ValueAnimator.REVERSE);
			colorBallAnim.setRepeatCount(ValueAnimator.INFINITE);
			colorBallAnim.addUpdateListener(new AnimatorUpdateListener() {
				
				@Override
				public void onAnimationUpdate(ValueAnimator animation) {
					invalidate();
				}
			});
			colorBallAnim.start();
		}
		
		@Override
		public boolean onTouchEvent(MotionEvent event) {
			if(event.getAction()==MotionEvent.ACTION_DOWN||
					event.getAction()==MotionEvent.ACTION_MOVE){
				ShapeHolder ball=addBalls(event.getX(), event.getY());
				balls.add(ball);
				AnimatorSet animatorSet=
						(AnimatorSet) AnimatorInflater
						.loadAnimator(XMLAnimActivity.this, R.animator.xmlanimset);
				animatorSet.setTarget(ball);
				AnimatorSet lastSet=
						(AnimatorSet) animatorSet.getChildAnimations().get(1);
				ObjectAnimator lastanim=
						(ObjectAnimator) lastSet.getChildAnimations().get(1);
				lastanim.addListener(new AnimatorListenerAdapter(){
					@Override
					public void onAnimationEnd(Animator animation) {
						balls.remove(((ObjectAnimator)animation).getTarget());
					}
				});
				animatorSet.start();
			}
			return true;
		}
		
		@Override
		protected void onDraw(Canvas canvas) {
			for(ShapeHolder ball : balls){
				canvas.save();
				canvas.translate(ball.getX(), ball.getY());
				ball.getShape().draw(canvas);
				canvas.restore();
			}
			
			canvas.save();
			canvas.translate(colorBall.getX(), colorBall.getY());
			colorBall.getShape().draw(canvas);
			canvas.restore();
		}
		
		
		
		
		
		
		private ShapeHolder addBalls(float x, float y) {
			int RED = (int) (Math.random() * 255) << 16;
			int GREEN = (int) (Math.random() * 255) << 8;
			int BULE = (int) (Math.random() * 255);
			int COLOR = 0xff000000 | RED | GREEN | BULE;
			int DARKCOLOR = 0xff000000 | (RED / 4) | (GREEN / 4) | (BULE / 4);

			OvalShape shape = new OvalShape();
			shape.resize(50f, 50f);
			ShapeDrawable drawable = new ShapeDrawable(shape);
			shapeHolder = new ShapeHolder(drawable);
			shapeHolder.setColor(COLOR);
			shapeHolder.setX(x);
			shapeHolder.setY(y);
			RadialGradient gradient = new RadialGradient(38f, 13f, 100, COLOR,
					DARKCOLOR, Shader.TileMode.CLAMP);
			shapeHolder.setGradient(gradient);
			return shapeHolder;

		}
		
	}
}
