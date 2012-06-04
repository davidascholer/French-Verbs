package com.AbleApps.FrenchVerbsFree;

import java.io.IOException;
import java.util.Hashtable;
import java.util.Locale;

import org.xmlpull.v1.XmlPullParserException;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.XmlResourceParser;
import android.os.Bundle; //import android.view.MotionEvent;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button; //import android.widget.HorizontalScrollView;
import android.widget.RadioButton; //import android.widget.ScrollView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

public class VocabSelection extends mainActivity implements
		TextToSpeech.OnInitListener {

	private static final int sMenuResource = R.menu.options_menu;
	private Menu mMenu;
	private boolean mColumnOneCollapsed = false;
	private boolean mColumnTwoCollapsed = false;
	int deckSize;
	XmlResourceParser questionBatch;
	String[] questionText;
	// private TableLayout container;
	float firstXTouch;
	float firstYTouch;
	float currentXTouch;
	float currentYTouch;
	int x;
	int y;
	boolean initiate;
	private TextToSpeech mTts;
	int result;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.vocab_layout);
		LinearLayout lImage = (LinearLayout) findViewById(R.id.vocabLayoutLinearLayout);
		lImage.setBackgroundDrawable(menuDrawable);

		mTts = new TextToSpeech(this, this // TextToSpeech.OnInitListener
		);
		initiate = true;

		switch (currentVocabDeck) {
		case 1:
			questionBatch = getResources().getXml(R.xml.aaac);
			deckSize = 110;
			break;
		case 2:
			questionBatch = getResources().getXml(R.xml.aadg);
			deckSize = 90;
			break;
		case 3:
			questionBatch = getResources().getXml(R.xml.aahp);
			deckSize = 94;
			break;
		case 4:
			questionBatch = getResources().getXml(R.xml.aarv);
			deckSize = 94;
			break;

		default:
			questionBatch = getResources().getXml(R.xml.aaac);
			deckSize = 110;
			break;
		}
		// END MOCK QUESTIONS

		int eventType = -1;
		int docCount = 0;

		questionText = new String[deckSize];

		while (eventType != XmlResourceParser.END_DOCUMENT) {
			if (eventType == XmlResourceParser.START_TAG) {

				// Get the name of the tag (eg questions or question)
				String strName = questionBatch.getName();

				if (strName.equals(XML_TAG_CARD)) {

					questionText[docCount] = questionBatch.getAttributeValue(
							null, XML_TAG_CARD_ATTRIBUTE_TEXT);

					docCount += 1;

				}
			}
			try {
				eventType = questionBatch.next();
			} catch (XmlPullParserException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		addTables();
		// //////////////////////////////////////////////////////////////
		final TableLayout table = (TableLayout) findViewById(R.id.menu);
		table.setColumnCollapsed(1, mColumnOneCollapsed);
		table.setColumnCollapsed(2, mColumnTwoCollapsed);

		RadioButton b1 = (RadioButton) findViewById(R.id.b1);
		b1.setChecked(true);
		b1.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				RadioButton b1 = (RadioButton) findViewById(R.id.b1);
				mColumnOneCollapsed = !mColumnOneCollapsed;
				if (mColumnOneCollapsed == true && mColumnTwoCollapsed == true) {
					mColumnOneCollapsed = !mColumnOneCollapsed;
				}
				if (mColumnOneCollapsed == true) {
					b1.setChecked(false);
				} else {
					b1.setChecked(true);
				}
				table.setColumnCollapsed(0, mColumnOneCollapsed);
			}
		});
		RadioButton b2 = (RadioButton) findViewById(R.id.b2);
		b2.setChecked(true);
		b2.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				RadioButton b2 = (RadioButton) findViewById(R.id.b2);
				mColumnTwoCollapsed = !mColumnTwoCollapsed;
				if (mColumnOneCollapsed == true && mColumnTwoCollapsed == true) {
					mColumnTwoCollapsed = !mColumnTwoCollapsed;
				}
				if (mColumnTwoCollapsed == true) {
					b2.setChecked(false);
				} else {
					b2.setChecked(true);
				}
				table.setColumnCollapsed(1, mColumnTwoCollapsed);
			}
		});
		Button b3 = (Button) findViewById(R.id.goToFlashCardsButton);
		b3.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {

				Intent myIntent = new Intent(v.getContext(), FlashCards.class);
				startActivityForResult(myIntent, 0);
				VocabSelection.this.finish();
			}
		});

		// ////////////////////////////////////////////////////////////////
	}

	public void addTables() {
		//
		for (int i = 0; i < deckSize; i += 2) {
			/* Create a new row to be added. */
			TableRow tr = new TableRow(this);
			// tr.setLayoutParams(new LayoutParams(
			// LayoutParams.FILL_PARENT,
			// LayoutParams.WRAP_CONTENT));
			/* Create a Button to be the row-content. */
			final TextView b = new TextView(this);
			b.setText(questionText[i + 1]);
			b.setPadding(3, 3, 3, 3);
			// b.setLayoutParams(new TableRow.LayoutParams(0));
			b.setClickable(true);
			b.setOnClickListener(new Button.OnClickListener() {
				public void onClick(View v) {

					result = mTts.setLanguage(Locale.US);
					mTts.setSpeechRate((float) 1.08);
					mTts.setPitch((float) 1.2);

					sayHello(b.getText().toString());
				}
			});

			final TextView bb = new TextView(this);
			bb.setText(questionText[i]);
			bb.setPadding(3, 3, 3, 3);
			// bb.setLayoutParams(new TableRow.LayoutParams(1));
			bb.setClickable(true);
			bb.setOnClickListener(new Button.OnClickListener() {
				public void onClick(View v) {

					result = mTts.setLanguage(Locale.FRENCH);
					mTts.setSpeechRate((float) .8);
					mTts.setPitch((float) 1.05);

					sayHello(bb.getText().toString());
				}
			});
			// b.setLayoutParams(new LayoutParams(
			// LayoutParams.FILL_PARENT,
			// LayoutParams.WRAP_CONTENT));
			/* Add Button to row. */
			tr.addView(b);
			tr.addView(bb);
			TableLayout tbl = (TableLayout) findViewById(R.id.menu);
			tbl.getBackground().setAlpha(120);
			/* Add row to TableLayout. */
			tbl.addView(tr);

		}
	}

	// Implements TextToSpeech.OnInitListener.
	public void onInit(int status) {
		// status can be either TextToSpeech.SUCCESS or TextToSpeech.ERROR.
		if (status == TextToSpeech.SUCCESS) {
			// Set preferred language to US english.
			// Note that a language may not be available, and the result will
			// indicate this.
			result = mTts.setLanguage(Locale.US);
			// Try this someday for some interesting results.
			// int result mTts.setLanguage(Locale.FRANCE);
			if (result == TextToSpeech.LANG_MISSING_DATA
					|| result == TextToSpeech.LANG_NOT_SUPPORTED) {
				// Lanuage data is missing or the language is not supported.
				// Lanuage data is missing or the language is not supported.
				// sayHello();
				TextView msg = new TextView(this);
				msg
						.setText("You do not have text to speech installed on your phone");
				msg.setGravity(Gravity.CENTER_HORIZONTAL);

				AlertDialog.Builder builder = new AlertDialog.Builder(this);
				builder.setView(msg).setCancelable(true).setPositiveButton(
						"Get It Now For Free",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								// code to download text to speech
								Intent installIntent = new Intent();
								installIntent
										.setAction(TextToSpeech.Engine.ACTION_INSTALL_TTS_DATA);
								startActivity(installIntent);

								return;
							}
						}).setNegativeButton("Disable Text to Speech",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								ImageButton b = (ImageButton) findViewById(R.id.flipImagebutton);
								b.setVisibility(View.INVISIBLE);
								return;
							}
						});
			} else {
				// Check the documentation for other possible result codes.
				// For example, the language may be available for the locale,
				// but not for the specified country and variant.

				// Greet the user.
				// sayHello();
			}
		} else {
			// Initialization failed.
			Log.e(DEBUG_TAG, "Could not initialize TextToSpeech.");
		}
	}

	private void sayHello(String word) {
		// Select a random hello.
		// int helloLength = HELLOS.length;
		String returnString = "";
		boolean paste = true;
		String hello = word;
		for (int i = 0; i < hello.length(); i++) {
			if (hello.charAt(i) == '(') {
				paste = false;
			}
			if (hello.charAt(i) == ')') {
				paste = true;
			}
			if (paste == true) {
				returnString += hello.charAt(i);
			}

		}

		mTts.speak(returnString, TextToSpeech.QUEUE_FLUSH, // Drop all pending
				// entries
				// in the playback queue.
				null);
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
			Intent myIntent = new Intent(VocabSelection.this, Upgrade.class);
			startActivityForResult(myIntent, 0);
			return true;		case R.id.background:
			BACKGROUND_RETURN_REF = 2;
			startActivity(new Intent(VocabSelection.this,
					BackgroundSwitcher.class));
			VocabSelection.this.finish();
			return true;
		case R.id.help:
			TextView msg = new TextView(this);
			msg
					.setText("Click on any word to hear it pronounced or use the flashcards to test your knowledge.");
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
