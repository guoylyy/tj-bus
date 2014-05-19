package edu.tongji.bus;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class TicketActivity extends Activity {
	
	private final int INCREMENT_STEP = 1;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ticket);
		Intent ticketIntent = getIntent();
		String name = ticketIntent.getExtras().get("name").toString();
		TextView nameView = (TextView) findViewById(R.id.nameText);

		TextView streamNumberView = (TextView) findViewById(R.id.streamNumber);
		streamNumberView.setText(this.getStreamNumber());

		nameView.setText(name);
		
		final Handler handler2 = new Handler()
		{
			@Override
			public void handleMessage(Message msg){
				ImageView imageBus = (ImageView)findViewById(R.id.imageView2);
				Animation translateAnimation = new TranslateAnimation(0, 350, 0, 0);
			    translateAnimation.setDuration(5000);
			    imageBus.startAnimation(translateAnimation);
			}
		};
		
		final Handler handler = new Handler()
		{
			@Override
			public void handleMessage(Message msg){
				super.handleMessage(msg);
				ProgressBar progressBar = (ProgressBar)findViewById(R.id.progressBar1);
				if((progressBar.getProgress() + INCREMENT_STEP) >= progressBar.getMax()){
					progressBar.setProgress(0);
					Message msg2 = new Message();
					handler2.sendMessage(msg2);
				}else{
					progressBar.incrementProgressBy(INCREMENT_STEP);
				}
			}
		};
		
		Timer timer = new Timer();
		timer.schedule(new TimerTask(){
			@Override
			public void run() {
				Message msg = new Message();
				handler.sendMessage(msg);
			}
		}, 0, 50);
		
		ImageView imageBus = (ImageView)findViewById(R.id.imageView2);
		Animation translateAnimation = new TranslateAnimation(0, 350, 0, 0);
	    translateAnimation.setDuration(5000);
	    imageBus.startAnimation(translateAnimation);
	}
	
	@SuppressLint("SimpleDateFormat")
	private String getStreamNumber() {
		Date date = new Date();
		String number = new Random().nextInt(900000) + 10000000 +"";
		
		return "流水号："
				+ (new SimpleDateFormat("yyyyMMddhhmmss")).format(date)
						.toString()+ number;
	}
}
