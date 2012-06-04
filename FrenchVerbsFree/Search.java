package com.AbleApps.FrenchVerbsFree;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ZoomControls;
import android.widget.AdapterView.OnItemClickListener;

public class Search extends mainActivity {
	private static final int sMenuResource = R.menu.options_menu;
	private Menu mMenu;
	int deckNumber;
	ArrayList<String> list = new ArrayList<String>();
	ArrayList<Integer> num = new ArrayList<Integer>();
	boolean[] tempCardDeckChecked = new boolean[NUMBER_OF_VERB_DECKS];

	// String[] searchResult;
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.search);

		LinearLayout image = (LinearLayout) findViewById(R.id.searchLayout);
		image.setBackgroundDrawable(menuDrawable);

		ImageButton searchButton = (ImageButton) findViewById(R.id.searchButton);
		searchButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {

				list.clear();
				num.clear();
				
				int[] numResult;

				// later for (int i = 0; i < NUMBER_OF_VERB_DECKS; i++) {
				// tempCardDeckChecked[i] = false;
				// }

				EditText txtButton = (EditText) findViewById(R.id.searchText);
				Editable txt = txtButton.getText();

				for (int i = 0; i < NUMBER_OF_VERB_DECKS; i++) {
					if (deckListXmlName[i].toLowerCase().contains(
							txt.toString().toLowerCase()) == true) {
						list.add(deckListActual[i]);
						num.add(new Integer(i));


					} else {
						if (deckListActual[i].toLowerCase().contains(
								txt.toString().toLowerCase()) == true) {
							list.add(deckListActual[i]);
							num.add(new Integer(i));

						}
					}
				}
				String[] searchResult = new String[list.size()];
				searchResult = list.toArray(new String[list.size()]);
				// nuZoomControls) lv.getZoomControls();

				displayArray(searchResult);
			}
		});

		OnItemClickListener mMessageClickedHandler = new OnItemClickListener() {
			public void onItemClick(AdapterView parent, View v, int position,
					long id) {

				verbDeckChecked = new boolean[NUMBER_OF_VERB_DECKS];
				for (int i = 0; i < NUMBER_OF_VERB_DECKS; i++) {
					verbDeckChecked[i] = false;
				}

				verbDeckChecked[num.get(position).intValue()] = true;
				CURRENT_VERB_DECK = num.get(position);

				Intent myIntent = new Intent(v.getContext(), Conjugate.class);
				startActivityForResult(myIntent, 0);

				// for (int i = 0; i < NUMBER_OF_VERB_DECKS; i++) {
				// if (listView.isItemChecked(i) != verbDeckChecked[i]) {
				// verbDeckChecked[i] = listView.isItemChecked(i);
				// }
				// }
			}
		};
		// Now hook into our object and set its onItemClickListener member
		// to our class handler object.
		ListView lv = (ListView) findViewById(R.id.resultListView);
		lv.setOnItemClickListener(mMessageClickedHandler);
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
			Intent myIntent = new Intent(Search.this, Upgrade.class);
			startActivityForResult(myIntent, 0);
			return true;
		case R.id.options_menu_close:
			android.os.Process.killProcess(android.os.Process.myPid());
			return true;
		case R.id.background:
			BACKGROUND_RETURN_REF = 0;
			startActivity(new Intent(Search.this, BackgroundSwitcher.class));
			Search.this.finish();
			return true;
		case R.id.vocab:
			startActivity(new Intent(Search.this, Vocab.class));
			// menu.this.finish();
			return true;
		case R.id.help:
			TextView msg = new TextView(this);
			msg
					.setText("Type in a word or part of a word in either English or French and any containing result will show. Case and accents do not matter.");
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

	public void displayArray(String[] searchResult) {
		final ListView searchView = (ListView) findViewById(R.id.resultListView);

		searchView.setAdapter(new ArrayAdapter<String>(this,
				R.layout.list_position, searchResult));

		ListView resultLV = (ListView) findViewById(R.id.resultListView);
		resultLV.setVisibility(View.VISIBLE);
	}

}
