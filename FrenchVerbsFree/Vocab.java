package com.AbleApps.FrenchVerbsFree;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class Vocab extends mainActivity {

	private static final int sMenuResource = R.menu.options_menu;
	private Menu mMenu;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.vocab);

		LinearLayout image = (LinearLayout) findViewById(R.id.vocabLayout);
		image.setBackgroundDrawable(menuDrawable);

		vocabDeckChecked = new boolean[vocabDecksNumber];

		final ListView listView = (ListView) findViewById(R.id.vocabListView);
		final String[] deckList = new String[vocabDecksNumber];
		deckList[0] = "A-C";
		deckList[1] = "D-G";
		deckList[2] = "H-P";
		deckList[3] = "R-V";

		listView.setAdapter(new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, deckList));
		// listView.setAdapter(adapt);

		// listView.setItemsCanFocus(false);
		// listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);

		// set cardDeckChecked boolean array to true for all values
		for (int i = 0; i < vocabDecksNumber; i++) {
			vocabDeckChecked[i] = false;
		}

		OnItemClickListener mMessageClickedHandler = new OnItemClickListener() {
			public void onItemClick(AdapterView parent, View v, int position,
					long id) {

				// So this variable will be equal to the switch statement in the
				// VocabSelection Class
				currentVocabDeck = position + 1;

				for (int i = 0; i < vocabDecksNumber; i++) {
					vocabDeckChecked[i] = false;
				}
				vocabDeckChecked[position] = true;

				Intent myIntent = new Intent(v.getContext(),
						VocabSelection.class);
				startActivityForResult(myIntent, 0);
				Vocab.this.finish();

				// for (int i = 0; i < NUMBER_OF_DECKS; i++) {
				// if (listView.isItemChecked(i) != cardDeckChecked[i]) {
				// cardDeckChecked[i] = listView.isItemChecked(i);
				// }
				// }
			}
		};
		// Now hook into our object and set its onItemClickListener member
		// to our class handler object.
		ListView lv = (ListView) findViewById(R.id.vocabListView);
		lv.setOnItemClickListener(mMessageClickedHandler);

		Button goLeft = (Button) findViewById(R.id.button1);
		goLeft.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {

				Intent myIntent = new Intent(Vocab.this, Upgrade.class);
				startActivityForResult(myIntent, 0);
			}
		});

	}

	//
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Hold on to this

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
		MenuItem vocab = menu.findItem(R.id.vocab);
		vocab.setVisible(false);
		vocab.setEnabled(false);
		MenuItem quit = menu.findItem(R.id.options_menu_close);
		quit.setVisible(false);
		quit.setEnabled(false);
		MenuItem changeFont = menu.findItem(R.id.change_font_size);
		changeFont.setVisible(false);
		changeFont.setEnabled(false);

		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		switch (item.getItemId()) {

		case R.id.download:
			Intent myIntent = new Intent(Vocab.this, Upgrade.class);
			startActivityForResult(myIntent, 0);
			return true;
		case R.id.background:
			BACKGROUND_RETURN_REF = 3;
			startActivity(new Intent(Vocab.this, BackgroundSwitcher.class));
			Vocab.this.finish();
			return true;
			// case R.id.vocab:
			// startActivity(new Intent(menu.this, BackgroundSwitcher.class));
			// menu.this.finish();
			// return true;
			// case R.id.search:
			// startActivity(new Intent(Vocab.this, Search.class));
			// Vocab.this.finish();
			// return true;

			// Generic catch all for all the other menu resources
		case R.id.help:
			TextView msg = new TextView(this);
			msg
					.setText("Click on any letter and there will show the set of all of the corresponding verbs along with their definitions.");
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
}