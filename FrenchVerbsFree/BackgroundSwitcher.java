package com.AbleApps.FrenchVerbsFree;

import com.google.ads.AdRequest;
import com.google.ads.AdView;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.Animation.AnimationListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class BackgroundSwitcher extends mainActivity implements OnTouchListener {

	final Drawable[] bg = new Drawable[20];
	// final ImageView[] background = new ImageView[16];

	private Menu mMenu;
	private static final int sMenuResource = R.menu.options_menu;
	private float downXValue;
	int pic = 1;
	Drawable temp; // in case user selects back button

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.background_switcher);

		Resources res = getResources();

		bg[0] = res.getDrawable(R.drawable.parisatnight);
		bg[2] = res.getDrawable(R.drawable.france1);
		bg[3] = res.getDrawable(R.drawable.france2);
		bg[12] = res.getDrawable(R.drawable.france3);
		bg[5] = res.getDrawable(R.drawable.france4);
		bg[6] = res.getDrawable(R.drawable.france5);
		bg[7] = res.getDrawable(R.drawable.france6);
		bg[9] = res.getDrawable(R.drawable.france8);
		bg[10] = res.getDrawable(R.drawable.france9);
		bg[13] = res.getDrawable(R.drawable.france10);
		bg[11] = res.getDrawable(R.drawable.france11);
		bg[14] = res.getDrawable(R.drawable.france12);
		bg[4] = res.getDrawable(R.drawable.france13);
		bg[8] = res.getDrawable(R.drawable.france14);
		bg[1] = res.getDrawable(R.drawable.france15);

		temp = menuDrawable;
		LinearLayout background = (LinearLayout) findViewById(R.id.BackgroundLayout);
		background.setBackgroundDrawable(bg[1]);

		LinearLayout image = (LinearLayout) findViewById(R.id.BackgroundLayout);
		image.setOnTouchListener((OnTouchListener) this);

		//
		ImageButton changePic = (ImageButton) findViewById(R.id.changeButton);
		changePic.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {

				goBackToMainScreen();

			}
		});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Hold on to this
		mMenu = menu;

		// Inflate the currently selected menu XML resource.
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(sMenuResource, menu);

		MenuItem em = menu.findItem(R.id.englishmode_true);
		em.setVisible(false);
		em.setEnabled(false);
		MenuItem fm = menu.findItem(R.id.englishmode_false);
		fm.setVisible(false);
		fm.setEnabled(false);
		MenuItem speak = menu.findItem(R.id.speak);
		speak.setVisible(false);
		speak.setEnabled(false);
		MenuItem shfl = menu.findItem(R.id.shuffle);
		shfl.setVisible(false);
		shfl.setEnabled(false);
		MenuItem bg = menu.findItem(R.id.background);
		bg.setVisible(false);
		bg.setEnabled(false);
		MenuItem quit = menu.findItem(R.id.options_menu_close);
		quit.setVisible(false);
		quit.setEnabled(false);
		MenuItem changeFont = menu.findItem(R.id.change_font_size);
		changeFont.setVisible(false);
		changeFont.setEnabled(false);
		MenuItem sa = menu.findItem(R.id.back);
		sa.setVisible(false);
		sa.setEnabled(false);
		MenuItem sn = menu.findItem(R.id.help);
		sn.setVisible(false);
		sn.setEnabled(false);
		MenuItem vocab = menu.findItem(R.id.vocab);
		vocab.setVisible(false);
		vocab.setEnabled(false);

		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		switch (item.getItemId()) {

		case R.id.download:
			Intent myIntent = new Intent(BackgroundSwitcher.this, Upgrade.class);
			startActivityForResult(myIntent, 0);
			return true;
		case R.id.select_bg:

			goBackToMainScreen();

			return true;
		case R.id.back:

			LinearLayout background = (LinearLayout) findViewById(R.id.BackgroundLayout);
			background.setBackgroundDrawable(temp);
			startActivity(new Intent(BackgroundSwitcher.this,
					MainScreenTabs.class));
			BackgroundSwitcher.this.finish();

			return true;

		default:

			break;
		}

		return false;
	}

	@Override
	public boolean onTouch(View arg0, MotionEvent arg1) {
		// Get the action that was done on this touch event
		switch (arg1.getAction()) {
		case MotionEvent.ACTION_DOWN: {
			// store the X value when the user's finger was pressed down
			downXValue = arg1.getX();
			break;
		}

		case MotionEvent.ACTION_UP: {
			// Get the X value when the user released his/her finger
			float currentX = arg1.getX();

			// going backwards: pushing stuff to the right
			if (downXValue < currentX) {
				prevPic();
			}

			// going forwards: pushing stuff to the left
			if (downXValue > currentX) {
				nextPic();
			}// ends first if
			break;
		}
		}

		// if you return false, these actions will not be recorded
		return true;
	}

	private void prevPic() {

		LinearLayout image = (LinearLayout) findViewById(R.id.BackgroundLayout);

		Animation cardOutLeft = AnimationUtils.loadAnimation(this,
				R.anim.cardout_right);

		final Animation cardInRight = AnimationUtils.loadAnimation(this,
				R.anim.cardin_left);

		image.startAnimation(cardOutLeft);

		AdView adView = (AdView) findViewById(R.id.adView);
		adView.loadAd(new AdRequest());

		cardOutLeft.setAnimationListener(new AnimationListener() {
			public void onAnimationEnd(Animation animation) {
				// The animation has ended. Do methods then set card_in
				LinearLayout image = (LinearLayout) findViewById(R.id.BackgroundLayout);

				pic--;
				if (pic == -1) {
					pic = 14;
				}
				image.setBackgroundDrawable(bg[pic]);
				image.startAnimation(cardInRight);
			}

			@Override
			public void onAnimationRepeat(Animation animation) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onAnimationStart(Animation animation) {
				// TODO Auto-generated method stub

			}
		});
	}

	private void nextPic() {

		LinearLayout image = (LinearLayout) findViewById(R.id.BackgroundLayout);

		Animation cardOutLeft = AnimationUtils.loadAnimation(this,
				R.anim.cardout_left);

		final Animation cardInRight = AnimationUtils.loadAnimation(this,
				R.anim.cardin_right);

		image.startAnimation(cardOutLeft);

		AdView adView = (AdView) findViewById(R.id.adView);
		adView.loadAd(new AdRequest());

		cardOutLeft.setAnimationListener(new AnimationListener() {
			public void onAnimationEnd(Animation animation) {
				// The animation has ended. Do methods then set card_in
				LinearLayout image = (LinearLayout) findViewById(R.id.BackgroundLayout);

				pic++;
				if (pic == 15) {
					pic = 0;
				}
				image.setBackgroundDrawable(bg[pic]);
				image.startAnimation(cardInRight);
			}

			@Override
			public void onAnimationRepeat(Animation animation) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onAnimationStart(Animation animation) {
				// TODO Auto-generated method stub

			}
		});
	}

	private void goBackToMainScreen() {
		menuDrawable = bg[pic];

		switch (BACKGROUND_RETURN_REF) {
		// MainScreenTabs
		case 0:
			startActivity(new Intent(BackgroundSwitcher.this,
					MainScreenTabs.class));
			break;
		// Conjugate
		case 1:
			startActivity(new Intent(BackgroundSwitcher.this, Conjugate.class));
			break;
		// Vocab Selection
		case 2:
			startActivity(new Intent(BackgroundSwitcher.this,
					VocabSelection.class));
			break;
		// Vocab
		case 3:
			startActivity(new Intent(BackgroundSwitcher.this, Vocab.class));
			break;
		}

		BackgroundSwitcher.this.finish();

	}

}
