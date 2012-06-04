package com.AbleApps.FrenchVerbsFree;

//import com.jetcitytechnologies.frenchdroid.ViewAnimation;
//import com.jetcitytechnologies.frenchdroid.R;


import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.Window;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;
import android.content.Intent;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.Animation.AnimationListener;

public class splash extends mainActivity {

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// Set fullscreen
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.main);
		// this.setupButton();
		this.animate();
	}

	// private void setupButton() {
	// Button b = (Button) this.findViewById(R.id.button1);
	// b.setOnClickListener(new Button.OnClickListener() {
	// public void onClick(View v) {
	// animate();
	// }
	// });
	// }

	private void animate() {
		// Fade in top title
		ImageView imgview1 = (ImageView) findViewById(R.id.imageView1);
		Animation fade = AnimationUtils.loadAnimation(this, R.anim.fade_in);
		fade.setStartOffset(2000);
		imgview1.startAnimation(fade);
		// Fade in bottom title after a built-in delay.
		ImageView able = (ImageView) findViewById(R.id.able);
		Animation cardin_left = AnimationUtils.loadAnimation(this,
				R.anim.cardin_left);
		able.startAnimation(cardin_left);
		cardin_left.setStartOffset(500);
		cardin_left.setDuration(2000);
		ImageView apps = (ImageView) findViewById(R.id.apps);
		Animation cardin_right = AnimationUtils.loadAnimation(this,
				R.anim.cardin_right);
		apps.startAnimation(cardin_right);
		cardin_right.setStartOffset(500);
		cardin_right.setDuration(2000);

		// imgview2.clearAnimation();
		// ImageView imgview3 = (ImageView) findViewById(R.id.imageView2);
		// Animation run_back = AnimationUtils.loadAnimation(this,
		// R.anim.run_back_slowly);
		// imgview3.startAnimation(run_back);
		// Transition to Main Menu when bottom title finishes animating

		fade.setAnimationListener(new AnimationListener() {
			public void onAnimationEnd(Animation animation) {
				// The animation has ended, transition to the Main Menu screen
				startActivity(new Intent(splash.this, MainScreenTabs.class));
				splash.this.finish();
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

	@Override
	protected void onPause() {
		super.onPause();
		// Stop the animation
		ImageView imgview1 = (ImageView) findViewById(R.id.imageView1);
		imgview1.clearAnimation();
		ImageView  able = (ImageView) findViewById(R.id.able);
		able.clearAnimation();
		ImageView apps = (ImageView) findViewById(R.id.apps);
		apps.clearAnimation();
	}

	@Override
	protected void onResume() {
		super.onResume();

		// Start animating at the beginning so we get the full splash screen
		// experience
		animate();
	}

}

// END OF ACTIVITY

/**
 * Optional code i couldn't get to work right. Would never return landscape.
 */

// public int getOrientation() {
// Display getOrient = getWindowManager().getDefaultDisplay();
// int orientation;
// orientation = getOrient.getOrientation();
//
// // Sometimes you may get undefined orientation Value is 0
// // simple logic solves the problem compare the screen
// // X,Y Co-ordinates and determine the Orientation in such cases
// if (orientation == Configuration.ORIENTATION_UNDEFINED) {
//
// Configuration config = getResources().getConfiguration();
// orientation = config.orientation;
//
// if (orientation == Configuration.ORIENTATION_UNDEFINED) {
// // if height and widht of screen are equal then
// // it is square orientation
// if (getOrient.getWidth() == getOrient.getHeight()) {
// return 3;
// } else { // if widht is less than height than it is portrait
// if (getOrient.getWidth() < getOrient.getHeight()) {
// return 1;
// } else { // if it is not any of the above it will defineitly
// // be landscape
// return 2;
// }
// }
// }
// }if(orientation == Configuration.ORIENTATION_SQUARE){
// return 3;
// }if(orientation == Configuration.ORIENTATION_LANDSCAPE){
// return 2;
// }else{ //if(orientation == Configuration.ORIENTATION_PORTRAIT){
// return 1;
// }
// //return orientation; // return value 1 is portrait and 2 is Landscape
// // Mode
//
// }
