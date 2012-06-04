package com.AbleApps.FrenchVerbsFree;

import com.google.ads.AdRequest;
import com.google.ads.AdView;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.speech.tts.TextToSpeech;
import android.speech.tts.TextToSpeech.OnInitListener;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window; //import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class menu extends mainActivity implements ListView.OnScrollListener {

	// public static final int TEXTBUBBLE_CLICK_ID = 0;
	// public static final int another option = 1;
	// If the user closes the bubble, it should be closed when the activity is
	// called again
	// boolean bubbleVisible = true;
	private static final int sMenuResource = R.menu.options_menu;
	private Menu mMenu;
	private boolean mShowing = false;
	private boolean scrollStart = true;
	private char mPrevLetter = Character.MIN_VALUE;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// Set fullscreen
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.menu);

		Resources res = getResources();
		LinearLayout image = (LinearLayout) findViewById(R.id.LinLayout);

		if (menuDrawable == null) {
			menuDrawable = res.getDrawable(R.drawable.france1);
		}
		image.setBackgroundDrawable(menuDrawable);

		final ListView listView = (ListView) findViewById(R.id.list);
		listView.getBackground().setAlpha(45);

		listView.setAdapter(new ArrayAdapter<String>(this,
				R.layout.list_position, deckListActual));
		// listView.setAdapter(adapt);

		// listView.setItemsCanFocus(false);
		// listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);

		listView.setOnScrollListener(this);
		// set verbDeckChecked boolean array to true for all values
		verbDeckChecked = new boolean[NUMBER_OF_VERB_DECKS];
		for (int i = 0; i < NUMBER_OF_VERB_DECKS; i++) {
			verbDeckChecked[i] = false;
		}

		// alertDialog = new AlertDialog.Builder(this).create();
		// alertDialog.setTitle("Alert 1");
		// alertDialog.setMessage("This is an alert");
		// alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
		// public void onClick(DialogInterface dialog, int which) {
		// return;
		// } });
		// TextView myMsg = new TextView(this);
		// myMsg.setText("Select a Card Deck");
		// myMsg.setGravity(Gravity.CENTER_HORIZONTAL);
		//	
		// AlertDialog.Builder builder = new AlertDialog.Builder(this);
		// builder.setView(myMsg).setCancelable(true)
		// .setPositiveButton("OK", new DialogInterface.OnClickListener() {
		// public void onClick(DialogInterface dialog, int id) {
		// return;
		// }
		// });
		// final AlertDialog alert = builder.create();

		// ImageButton goFullScreen = (ImageButton)
		// findViewById(R.id.fullScreenImageButton);
		// goFullScreen.setOnClickListener(new View.OnClickListener() {
		// public void onClick(View view) {
		//
		// boolean noneSelected = true;
		//
		// for (int i = 0; i < NUMBER_OF_VERB_DECKS; i++) {
		// if (verbDeckChecked[i] == true) {
		// noneSelected = false;
		// break;
		// } else {
		// noneSelected = true;
		// }
		// }
		//
		// if (noneSelected == true) {
		// alert.show();
		// } else {
		//				
		// Intent myIntent = new Intent(view.getContext(),
		// FullScreenMode.class);
		// startActivityForResult(myIntent, 0);
		// }
		// }
		// });

		// Create a message handling object as an anonymous class.
		OnItemClickListener mMessageClickedHandler = new OnItemClickListener() {
			public void onItemClick(AdapterView parent, View v, int position,
					long id) {
				//
				// verbDeckChecked = new boolean[NUMBER_OF_VERB_DECKS];
				// for (int i = 0; i < NUMBER_OF_VERB_DECKS; i++) {
				// verbDeckChecked[i] = false;
				// }
				// verbDeckChecked[position] = true;
				//
				CURRENT_VERB_DECK = position;
				Intent myIntent = new Intent(v.getContext(), Conjugate.class);
				startActivityForResult(myIntent, 0);
				// ///////////////////////////////////////////////////
				// for (int i = 0; i < NUMBER_OF_VERB_DECKS; i++) {
				// if (listView.isItemChecked(i) != verbDeckChecked[i]) {
				// verbDeckChecked[i] = listView.isItemChecked(i);
				// }
				// }
			}
		};
		// Now hook into our object and set its onItemClickListener member
		// to our class handler object.
		ListView lv = (ListView) findViewById(R.id.list);
		lv.setOnItemClickListener(mMessageClickedHandler);
	}

	//
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
		MenuItem sbg = menu.findItem(R.id.select_bg);
		sbg.setVisible(false);
		sbg.setEnabled(false);
		MenuItem back = menu.findItem(R.id.back);
		back.setVisible(false);
		back.setEnabled(false);
		MenuItem changeFont = menu.findItem(R.id.change_font_size);
		changeFont.setVisible(false);
		changeFont.setEnabled(false);

		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		switch (item.getItemId()) {
		case R.id.download:
			Intent myIntent = new Intent(menu.this, Upgrade.class);
			startActivityForResult(myIntent, 0);
			return true;
		case R.id.options_menu_close:
			android.os.Process.killProcess(android.os.Process.myPid());
			return true;
		case R.id.background:
			BACKGROUND_RETURN_REF = 0;

			startActivity(new Intent(menu.this, BackgroundSwitcher.class));
			menu.this.finish();
			return true;
		case R.id.vocab:
			startActivity(new Intent(menu.this, Vocab.class));
			// menu.this.finish();
			return true;
		case R.id.help:
			TextView msg = new TextView(this);
			msg.setText("Click on any word to see the conjugation.");
			msg.setGravity(Gravity.CENTER_HORIZONTAL);

			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setView(msg).setCancelable(true).setPositiveButton("OK",
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int id) {
						}
					});
			final AlertDialog alrt = builder.create();
			alrt.show();
			return true;
		default:
			// Don't toast text when a submenu is clicked
			if (!item.hasSubMenu()) {
				Toast.makeText(this, item.getTitle(), Toast.LENGTH_SHORT)
						.show();
				return true;
			}
			break;
		}

		return false;
	}

	// public void setBgImage(){
	//		
	// LinearLayout image = (LinearLayout) findViewById(R.id.LinLayout);
	// image.setBackgroundDrawable(menuDrawable);
	@Override
	public void onStart() {
		super.onStart();

		LinearLayout image = (LinearLayout) findViewById(R.id.LinLayout);
		image.setBackgroundDrawable(menuDrawable);
	}

	@Override
	public void onScroll(AbsListView arg0, int firstVisibleItem,
			int visibleItemCount, int totalItemCount) {
		// TODO Auto-generated method stub
		int lastItem = firstVisibleItem + visibleItemCount - 1;

		ListView listView = (ListView) findViewById(R.id.list);
		listView.getBackground().setAlpha(80);

		char firstLetter = deckListXmlName[firstVisibleItem].charAt(0);

		Handler myHandler = new Handler();

		TextView t = (TextView) findViewById(R.id.menuTextView);

		if (scrollStart != true) {

			if (firstLetter != mPrevLetter) {

				t.setVisibility(View.VISIBLE);
				t.setText(((Character) firstLetter).toString());
				myHandler.postDelayed(mMyRunnable, 1500);

				AdView adView = (AdView) findViewById(R.id.adView);
				adView.loadAd(new AdRequest());

			}

		}
		mPrevLetter = firstLetter;

	}

	@Override
	public void onScrollStateChanged(AbsListView arg0, int scrollState) {
		// TODO Auto-generated method stub
		scrollStart = false;
		//
		// if (scrollState == 0) {
		// Handler myHandler = new Handler();
		// myHandler.postDelayed(mMyRunnable, 1500);
		// }
	}

	private Runnable mMyRunnable = new Runnable() {
		@Override
		public void run() {
			TextView t = (TextView) findViewById(R.id.menuTextView);
			t.setVisibility(View.INVISIBLE);
		}
	};

	// ////////////////for main version
	// protected Dialog onCreateDialog(int id) {
	// Dialog dialog = null;
	// switch(id) {
	// case TEXTBUBBLE_CLICK_ID:
	//
	//
	// LayoutInflater inflater =
	// (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	// final View layout =
	// inflater.inflate(R.layout.textbubble_dialogue,
	// (ViewGroup) findViewById(R.id.root));
	// AlertDialog.Builder builder = new AlertDialog.Builder(this);
	// builder.setIcon(R.drawable.frenchandroid);
	// builder.setMessage("I'm here to help. Click me and I will help you by pronouncing the word. TRY IT OUT!")
	// .setCancelable(true)
	// .setNeutralButton("Ok", new DialogInterface.OnClickListener() {
	// public void onClick(DialogInterface dialog, int id) {
	// removeDialog(0);
	//
	// }
	// });
	// AlertDialog alert = builder.create();
	// return alert;
	// default:
	// dialog = null;
	// }
	// return dialog;
	// }

}
