package com.AbleApps.FrenchVerbsFree;

import java.io.IOException;
import java.util.Locale;

import org.xmlpull.v1.XmlPullParserException;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.XmlResourceParser;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.ZoomControls;

public class Conjugate extends mainActivity implements
		TextToSpeech.OnInitListener {

	private boolean mColumnOneCollapsed = false;
	private boolean mColumnTwoCollapsed = false;
	int deckSize = 100;
	XmlResourceParser questionBatch;
	String[] questionText;
	private Menu mMenu;
	private static final int sMenuResource = R.menu.options_menu;
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

		setContentView(R.layout.conjugate);
		LinearLayout lImage = (LinearLayout) findViewById(R.id.vocabLayoutLinearLayout);
		lImage.setBackgroundDrawable(menuDrawable);

		mTts = new TextToSpeech(this, this // TextToSpeech.OnInitListener
		);
		initiate = true;

		String xmlDeck = deckListXmlName[CURRENT_VERB_DECK];
		// This is to turn a string value of a resource to an int
		int resID = getResources().getIdentifier(xmlDeck, "xml",
				"com.AbleApps.FrenchVerbsFree");
		questionBatch = getResources().getXml(resID);

		int eventType = -1;
		int docCount = 0;

		questionText = new String[100];

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
		TableLayout table = (TableLayout) findViewById(R.id.menu);
		table.setColumnCollapsed(1, mColumnTwoCollapsed);
		table.setColumnCollapsed(2, mColumnTwoCollapsed);

		ImageButton b3 = (ImageButton) findViewById(R.id.goToFlashCardsButton);
		b3.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {

				Conjugate.this.finish();
			}
		});

		// ////////////////////////////////////////////////////////////////
	}

	public void addTables() {

		String verbTense;
		TableLayout indpresentTableLayout = new TableLayout(this);
		TableLayout pastsimpleTableLayout = new TableLayout(this);
		TableLayout presentperfectTableLayout = new TableLayout(this);
		TableLayout pastperfectTableLayout = new TableLayout(this);
		TableLayout indimperfectTableLayout = new TableLayout(this);
		TableLayout futureTableLayout = new TableLayout(this);
		TableLayout indpluperfectTableLayout = new TableLayout(this);
		TableLayout pastfutureTableLayout = new TableLayout(this);
		TableLayout subpresentTableLayout = new TableLayout(this);
		TableLayout subpastTableLayout = new TableLayout(this);
		TableLayout subimperfectTableLayout = new TableLayout(this);
		TableLayout subpluperfectTableLayout = new TableLayout(this);
		TableLayout condpresentTableLayout = new TableLayout(this);
		TableLayout condfirstpastTableLayout = new TableLayout(this);
		TableLayout condsecondpastTableLayout = new TableLayout(this);
		TableLayout imperitivepresentTableLayout = new TableLayout(this);
		TableLayout imperitivepastTableLayout = new TableLayout(this);
		TableLayout infinitivepresentTableLayout = new TableLayout(this);
		TableLayout infinitivepastTableLayout = new TableLayout(this);
		TableLayout particplepresentTableLayout = new TableLayout(this);
		TableLayout particplepastTableLayout = new TableLayout(this);
		TableLayout emptyTableLayout = new TableLayout(this);

		// presentTableLayout.getBackground().setAlpha(120);
		TextView header = new TextView(this);
		verbTense = "Present";
		header.setTextSize(textSize);
		header.setText(verbTense);
		header.setGravity(Gravity.CENTER);

		// Adding the Present Table Row

		initiate = true;
		for (int i = 1; i <= 6; i += 1) {

			final TextView text = new TextView(this);
			text.setTextSize(textSize);
			text.setText(questionText[i - 1]);
			text.setGravity(Gravity.CENTER);
			text.setPadding(10, 3, 10, 3);
			text.setClickable(true);
			text.setOnClickListener(new Button.OnClickListener() {
				public void onClick(View v) {

					sayHello(text.getText().toString());
				}
			});

			// final TextView a2 = new TextView(this);
			// a2.setText(questionText[i]);
			// a2.setPadding(3, 3, 3, 3);
			// a2.setLayoutParams(new TableRow.LayoutParams(1));
			// a2.setClickable(true);
			// a2.setOnClickListener(new Button.OnClickListener() {
			// public void onClick(View v) {
			//				
			// sayHello(a2.getText().toString());
			// }
			// });
			// b.setLayoutParams(new LayoutParams(
			// LayoutParams.FILL_PARENT,
			// LayoutParams.WRAP_CONTENT));
			/* Add Button to row. */

			// header.setPadding(3, 3, 3, 3);
			// indpresentTableRow.addView(header);
			// indpresentTableRow.addView(space);
			if (initiate == true) {
				TableRow tableRow = new TableRow(this);
				tableRow.addView(header);
				tableRow.setPadding(1, 15, 1, 10);
				tableRow.setBackgroundResource(R.color.red);
				tableRow.getBackground().setAlpha(150);

				// tr.addView(a2);
				indpresentTableLayout.addView(tableRow);

				initiate = false;
			}
			TableRow indpresentTableRow = new TableRow(this);

			indpresentTableRow.addView(text);
			// tr.addView(a2);
			indpresentTableLayout.addView(indpresentTableRow);
			indpresentTableLayout.setStretchAllColumns(true);

		}
		// ////////////////////////////////////////////////////////////////////
		verbTense = "Simple Past";
		header = new TextView(this);
		header.setTextSize(textSize);
		header.setText(verbTense);
		header.setGravity(Gravity.CENTER);

		initiate = true;
		for (int i = 7; i <= 12; i += 1) {

			final TextView text = new TextView(this);
			text.setTextSize(textSize);

			text.setText(questionText[i - 1]);
			text.setGravity(Gravity.CENTER);
			text.setPadding(10, 3, 10, 3);
			text.setClickable(true);
			text.setOnClickListener(new Button.OnClickListener() {
				public void onClick(View v) {

					sayHello(text.getText().toString());
				}
			});

			if (initiate == true) {
				TableRow tableRow = new TableRow(this);
				tableRow.addView(header);
				tableRow.setPadding(1, 15, 1, 10);
				pastsimpleTableLayout.addView(tableRow);
				tableRow.setBackgroundResource(R.color.red);
				tableRow.getBackground().setAlpha(150);

				initiate = false;
			}

			TableRow indpresentTableRow = new TableRow(this);
			indpresentTableRow.addView(text);

			pastsimpleTableLayout.addView(indpresentTableRow);
			pastsimpleTableLayout.setStretchAllColumns(true);
		}

		//
		// //////////////////////////////////////////////////////////////////////
		verbTense = "Present Perfect";
		header = new TextView(this);
		header.setTextSize(textSize);
		header.setText(verbTense);
		header.setGravity(Gravity.CENTER);

		initiate = true;
		for (int i = 13; i <= 18; i += 1) {

			final TextView text = new TextView(this);
			text.setTextSize(textSize);

			text.setText(questionText[i - 1]);
			text.setGravity(Gravity.CENTER);
			text.setPadding(10, 3, 10, 3);
			text.setClickable(true);
			text.setOnClickListener(new Button.OnClickListener() {
				public void onClick(View v) {

					sayHello(text.getText().toString());
				}
			});

			if (initiate == true) {
				TableRow tableRow = new TableRow(this);
				tableRow.addView(header);
				tableRow.setPadding(1, 15, 1, 10);
				presentperfectTableLayout.addView(tableRow);
				tableRow.setBackgroundResource(R.color.red);
				tableRow.getBackground().setAlpha(150);

				initiate = false;
			}

			TableRow indpresentTableRow = new TableRow(this);
			indpresentTableRow.addView(text);

			presentperfectTableLayout.addView(indpresentTableRow);// change
			presentperfectTableLayout.setStretchAllColumns(true);

		}

		//
		// //////////////////////////////////////////////////////////////////////
		verbTense = "Past Perfect";
		header = new TextView(this);
		header.setTextSize(textSize);
		header.setText(verbTense);
		header.setGravity(Gravity.CENTER);

		initiate = true;

		for (int i = 19; i <= 24; i += 1) {

			final TextView text = new TextView(this);
			text.setTextSize(textSize);

			text.setText(questionText[i - 1]);
			text.setGravity(Gravity.CENTER);
			text.setPadding(10, 3, 10, 3);
			text.setClickable(true);
			text.setOnClickListener(new Button.OnClickListener() {
				public void onClick(View v) {

					sayHello(text.getText().toString());
				}
			});

			if (initiate == true) {
				TableRow tableRow = new TableRow(this);
				tableRow.addView(header);
				tableRow.setPadding(1, 15, 1, 10);
				pastperfectTableLayout.addView(tableRow);
				tableRow.setBackgroundResource(R.color.red);
				tableRow.getBackground().setAlpha(150);

				initiate = false;
			}

			TableRow indpresentTableRow = new TableRow(this);
			indpresentTableRow.addView(text);

			pastperfectTableLayout.addView(indpresentTableRow);// change
			pastperfectTableLayout.setStretchAllColumns(true);

		}
		// change
		// here
		//
		// ////////////////////////////////////////////////////////////////////
		verbTense = "Imperfect";
		header = new TextView(this);
		header.setTextSize(textSize);
		header.setText(verbTense);
		header.setGravity(Gravity.CENTER);

		initiate = true;

		for (int i = 25; i <= 30; i += 1) {

			final TextView text = new TextView(this);
			text.setTextSize(textSize);

			text.setText(questionText[i - 1]);
			text.setGravity(Gravity.CENTER);
			text.setPadding(10, 3, 10, 3);
			text.setClickable(true);
			text.setOnClickListener(new Button.OnClickListener() {
				public void onClick(View v) {

					sayHello(text.getText().toString());
				}
			});
			if (initiate == true) {
				TableRow tableRow = new TableRow(this);
				tableRow.addView(header);
				tableRow.setPadding(1, 15, 1, 10);
				indimperfectTableLayout.addView(tableRow);
				tableRow.setBackgroundResource(R.color.red);
				tableRow.getBackground().setAlpha(150);

				initiate = false;
			}

			TableRow indpresentTableRow = new TableRow(this);
			indpresentTableRow.addView(text);

			indimperfectTableLayout.addView(indpresentTableRow);// change
			indimperfectTableLayout.setStretchAllColumns(true);

		}
		// change
		// here

		//
		// ////////////////////////////////////////////////////////////////////
		verbTense = "Future";
		header = new TextView(this);
		header.setTextSize(textSize);
		header.setText(verbTense);
		header.setGravity(Gravity.CENTER);

		initiate = true;

		for (int i = 31; i <= 36; i += 1) {

			final TextView text = new TextView(this);
			text.setTextSize(textSize);

			text.setText(questionText[i - 1]);
			text.setGravity(Gravity.CENTER);
			text.setPadding(10, 3, 10, 3);
			text.setClickable(true);
			text.setOnClickListener(new Button.OnClickListener() {
				public void onClick(View v) {

					sayHello(text.getText().toString());
				}
			});
			if (initiate == true) {
				TableRow tableRow = new TableRow(this);
				tableRow.addView(header);
				tableRow.setPadding(1, 15, 1, 10);
				futureTableLayout.addView(tableRow);
				tableRow.setBackgroundResource(R.color.red);
				tableRow.getBackground().setAlpha(150);

				initiate = false;
			}

			TableRow indpresentTableRow = new TableRow(this);
			indpresentTableRow.addView(text);

			futureTableLayout.addView(indpresentTableRow);// change
			futureTableLayout.setStretchAllColumns(true);

		}
		// change
		// here
		//
		// ////////////////////////////////////////////////////////////////////
		verbTense = "Pluperfect";
		header = new TextView(this);
		header.setTextSize(textSize);
		header.setText(verbTense);
		header.setGravity(Gravity.CENTER);

		initiate = true;

		for (int i = 37; i <= 42; i += 1) {

			final TextView text = new TextView(this);
			text.setTextSize(textSize);

			text.setText(questionText[i - 1]);
			text.setGravity(Gravity.CENTER);
			text.setPadding(10, 3, 10, 3);
			text.setClickable(true);
			text.setOnClickListener(new Button.OnClickListener() {
				public void onClick(View v) {

					sayHello(text.getText().toString());
				}
			});
			if (initiate == true) {
				TableRow tableRow = new TableRow(this);
				tableRow.addView(header);
				tableRow.setPadding(1, 15, 1, 10);
				indpluperfectTableLayout.addView(tableRow);
				tableRow.setBackgroundResource(R.color.red);
				tableRow.getBackground().setAlpha(150);

				initiate = false;
			}

			TableRow indpresentTableRow = new TableRow(this);
			indpresentTableRow.addView(text);

			indpluperfectTableLayout.addView(indpresentTableRow);// change
			indpluperfectTableLayout.setStretchAllColumns(true);

		}
		// change
		// here
		//
		// ////////////////////////////////////////////////////////////////////
		verbTense = "Past Future";
		header = new TextView(this);
		header.setTextSize(textSize);
		header.setText(verbTense);
		header.setGravity(Gravity.CENTER);

		initiate = true;

		for (int i = 43; i <= 48; i += 1) {

			final TextView text = new TextView(this);
			text.setTextSize(textSize);

			text.setText(questionText[i - 1]);
			text.setGravity(Gravity.CENTER);
			text.setPadding(10, 3, 10, 3);
			text.setClickable(true);
			text.setOnClickListener(new Button.OnClickListener() {
				public void onClick(View v) {

					sayHello(text.getText().toString());
				}
			});
			if (initiate == true) {
				TableRow tableRow = new TableRow(this);
				tableRow.addView(header);
				tableRow.setPadding(1, 15, 1, 10);
				pastfutureTableLayout.addView(tableRow);
				tableRow.setBackgroundResource(R.color.red);
				tableRow.getBackground().setAlpha(150);

				initiate = false;
			}

			TableRow indpresentTableRow = new TableRow(this);
			indpresentTableRow.addView(text);

			pastfutureTableLayout.addView(indpresentTableRow);// change
			pastfutureTableLayout.setStretchAllColumns(true);

		}
		// change
		// here
		//
		// ////////////////////////////////////////////////////////////////////
		verbTense = "Present";
		header = new TextView(this);
		header.setTextSize(textSize);
		header.setText(verbTense);
		header.setGravity(Gravity.CENTER);

		initiate = true;

		for (int i = 49; i <= 54; i += 1) {

			final TextView text = new TextView(this);
			text.setTextSize(textSize);

			text.setText(questionText[i - 1]);
			text.setGravity(Gravity.CENTER);
			text.setPadding(10, 3, 10, 3);
			text.setClickable(true);
			text.setOnClickListener(new Button.OnClickListener() {
				public void onClick(View v) {

					sayHello(text.getText().toString());
				}
			});
			if (initiate == true) {
				TableRow tableRow = new TableRow(this);
				tableRow.addView(header);
				tableRow.setPadding(1, 15, 1, 10);
				subpresentTableLayout.addView(tableRow);
				tableRow.setBackgroundResource(R.color.red);
				tableRow.getBackground().setAlpha(150);

				initiate = false;
			}

			TableRow indpresentTableRow = new TableRow(this);
			indpresentTableRow.addView(text);

			subpresentTableLayout.addView(indpresentTableRow);// change
			subpresentTableLayout.setStretchAllColumns(true);

		}
		// change
		// here
		//
		// ////////////////////////////////////////////////////////////////////
		verbTense = "Past";
		header = new TextView(this);
		header.setTextSize(textSize);
		header.setText(verbTense);
		header.setGravity(Gravity.CENTER);

		initiate = true;

		for (int i = 55; i <= 60; i += 1) {

			final TextView text = new TextView(this);
			text.setTextSize(textSize);

			text.setText(questionText[i - 1]);
			text.setGravity(Gravity.CENTER);
			text.setPadding(10, 3, 10, 3);
			text.setClickable(true);
			text.setOnClickListener(new Button.OnClickListener() {
				public void onClick(View v) {

					sayHello(text.getText().toString());
				}
			});
			if (initiate == true) {
				TableRow tableRow = new TableRow(this);
				tableRow.addView(header);
				tableRow.setPadding(1, 15, 1, 10);
				subpastTableLayout.addView(tableRow);
				tableRow.setBackgroundResource(R.color.red);
				tableRow.getBackground().setAlpha(150);

				initiate = false;
			}

			TableRow indpresentTableRow = new TableRow(this);
			indpresentTableRow.addView(text);

			subpastTableLayout.addView(indpresentTableRow);// change
			subpastTableLayout.setStretchAllColumns(true);

		}
		// change
		// here
		//
		// ////////////////////////////////////////////////////////////////////
		verbTense = "Past";
		header = new TextView(this);
		header.setTextSize(textSize);
		header.setText(verbTense);
		header.setGravity(Gravity.CENTER);

		initiate = true;

		for (int i = 61; i <= 66; i += 1) {

			final TextView text = new TextView(this);
			text.setTextSize(textSize);

			text.setText(questionText[i - 1]);
			text.setGravity(Gravity.CENTER);
			text.setPadding(10, 3, 10, 3);
			text.setClickable(true);
			text.setOnClickListener(new Button.OnClickListener() {
				public void onClick(View v) {

					sayHello(text.getText().toString());
				}
			});
			if (initiate == true) {
				TableRow tableRow = new TableRow(this);
				tableRow.addView(header);
				tableRow.setPadding(1, 15, 1, 10);
				subimperfectTableLayout.addView(tableRow);
				tableRow.setBackgroundResource(R.color.red);
				tableRow.getBackground().setAlpha(150);

				initiate = false;
			}

			TableRow indpresentTableRow = new TableRow(this);
			indpresentTableRow.addView(text);

			subimperfectTableLayout.addView(indpresentTableRow);// change
			subimperfectTableLayout.setStretchAllColumns(true);

		}
		// change
		// here
		//
		// ////////////////////////////////////////////////////////////////////
		verbTense = "Pluperfect";
		header = new TextView(this);
		header.setTextSize(textSize);
		header.setText(verbTense);
		header.setGravity(Gravity.CENTER);

		initiate = true;

		for (int i = 67; i <= 72; i += 1) {

			final TextView text = new TextView(this);
			text.setTextSize(textSize);

			text.setText(questionText[i - 1]);
			text.setGravity(Gravity.CENTER);
			text.setPadding(10, 3, 10, 3);
			text.setClickable(true);
			text.setOnClickListener(new Button.OnClickListener() {
				public void onClick(View v) {

					sayHello(text.getText().toString());
				}
			});
			if (initiate == true) {
				TableRow tableRow = new TableRow(this);
				tableRow.addView(header);
				tableRow.setPadding(1, 15, 1, 10);
				subpluperfectTableLayout.addView(tableRow);
				tableRow.setBackgroundResource(R.color.red);
				tableRow.getBackground().setAlpha(150);

				initiate = false;
			}

			TableRow indpresentTableRow = new TableRow(this);
			indpresentTableRow.addView(text);

			subpluperfectTableLayout.addView(indpresentTableRow);// change
			subpluperfectTableLayout.setStretchAllColumns(true);

		}
		// change
		// here
		//
		// ////////////////////////////////////////////////////////////////////
		verbTense = "Present";
		header = new TextView(this);
		header.setTextSize(textSize);
		header.setText(verbTense);
		header.setGravity(Gravity.CENTER);

		initiate = true;

		for (int i = 73; i <= 78; i += 1) {

			final TextView text = new TextView(this);
			text.setTextSize(textSize);

			text.setText(questionText[i - 1]);
			text.setGravity(Gravity.CENTER);
			text.setPadding(10, 3, 10, 3);
			text.setClickable(true);
			text.setOnClickListener(new Button.OnClickListener() {
				public void onClick(View v) {

					sayHello(text.getText().toString());
				}
			});
			if (initiate == true) {
				TableRow tableRow = new TableRow(this);
				tableRow.addView(header);
				tableRow.setPadding(1, 15, 1, 10);
				condpresentTableLayout.addView(tableRow);
				tableRow.setBackgroundResource(R.color.red);
				tableRow.getBackground().setAlpha(150);

				initiate = false;
			}

			TableRow indpresentTableRow = new TableRow(this);
			indpresentTableRow.addView(text);

			condpresentTableLayout.addView(indpresentTableRow);// change
			condpresentTableLayout.setStretchAllColumns(true);

		}
		// change
		// here
		//
		// ////////////////////////////////////////////////////////////////////
		verbTense = "First Past";
		header = new TextView(this);
		header.setTextSize(textSize);
		header.setText(verbTense);
		header.setGravity(Gravity.CENTER);

		initiate = true;

		for (int i = 79; i <= 84; i += 1) {

			final TextView text = new TextView(this);
			text.setTextSize(textSize);

			text.setText(questionText[i - 1]);
			text.setGravity(Gravity.CENTER);
			text.setPadding(10, 3, 10, 3);
			text.setClickable(true);
			text.setOnClickListener(new Button.OnClickListener() {
				public void onClick(View v) {

					sayHello(text.getText().toString());
				}
			});
			if (initiate == true) {
				TableRow tableRow = new TableRow(this);
				tableRow.addView(header);
				tableRow.setPadding(1, 15, 1, 10);
				condfirstpastTableLayout.addView(tableRow);
				tableRow.setBackgroundResource(R.color.red);
				tableRow.getBackground().setAlpha(150);

				initiate = false;
			}

			TableRow indpresentTableRow = new TableRow(this);
			indpresentTableRow.addView(text);

			condfirstpastTableLayout.addView(indpresentTableRow);// change
			condfirstpastTableLayout.setStretchAllColumns(true);

		}
		// change
		// here
		//
		// ////////////////////////////////////////////////////////////////////
		verbTense = "Second Past";
		header = new TextView(this);
		header.setTextSize(textSize);
		header.setText(verbTense);
		header.setGravity(Gravity.CENTER);

		initiate = true;

		for (int i = 85; i <= 90; i += 1) {

			final TextView text = new TextView(this);
			text.setTextSize(textSize);

			text.setText(questionText[i - 1]);
			text.setGravity(Gravity.CENTER);
			text.setPadding(10, 3, 10, 3);
			text.setClickable(true);
			text.setOnClickListener(new Button.OnClickListener() {
				public void onClick(View v) {

					sayHello(text.getText().toString());
				}
			});
			if (initiate == true) {
				TableRow tableRow = new TableRow(this);
				tableRow.addView(header);
				tableRow.setPadding(1, 15, 1, 10);
				condsecondpastTableLayout.addView(tableRow);
				tableRow.setBackgroundResource(R.color.red);
				tableRow.getBackground().setAlpha(150);

				initiate = false;
			}

			TableRow indpresentTableRow = new TableRow(this);
			indpresentTableRow.addView(text);

			condsecondpastTableLayout.addView(indpresentTableRow);// change
			condsecondpastTableLayout.setStretchAllColumns(true);

		}
		// change
		// here
		//
		// ////////////////////////////////////////////////////////////////////
		verbTense = "Present";
		header = new TextView(this);
		header.setTextSize(textSize);
		header.setText(verbTense);
		header.setGravity(Gravity.CENTER);

		initiate = true;

		for (int i = 91; i <= 93; i += 1) {

			final TextView text = new TextView(this);
			text.setTextSize(textSize);

			text.setText(questionText[i - 1]);
			text.setGravity(Gravity.CENTER);
			text.setPadding(10, 3, 10, 3);
			text.setClickable(true);
			text.setOnClickListener(new Button.OnClickListener() {
				public void onClick(View v) {

					sayHello(text.getText().toString());
				}
			});
			if (initiate == true) {
				TableRow tableRow = new TableRow(this);
				tableRow.addView(header);
				tableRow.setPadding(1, 15, 1, 10);
				imperitivepresentTableLayout.addView(tableRow);
				tableRow.setBackgroundResource(R.color.red);
				tableRow.getBackground().setAlpha(150);

				initiate = false;
			}

			TableRow indpresentTableRow = new TableRow(this);
			indpresentTableRow.addView(text);

			imperitivepresentTableLayout.addView(indpresentTableRow);// change
			imperitivepresentTableLayout.setStretchAllColumns(true);

		}
		// here
		// change
		// here
		//
		// ////////////////////////////////////////////////////////////////////
		verbTense = "Past";
		header = new TextView(this);
		header.setTextSize(textSize);
		header.setText(verbTense);
		header.setGravity(Gravity.CENTER);

		initiate = true;

		for (int i = 94; i <= 96; i += 1) {

			final TextView text = new TextView(this);
			text.setTextSize(textSize);

			text.setText(questionText[i - 1]);
			text.setGravity(Gravity.CENTER);
			text.setPadding(10, 3, 10, 3);
			text.setClickable(true);
			text.setOnClickListener(new Button.OnClickListener() {
				public void onClick(View v) {

					sayHello(text.getText().toString());
				}
			});
			if (initiate == true) {
				TableRow tableRow = new TableRow(this);
				tableRow.addView(header);
				tableRow.setPadding(1, 15, 1, 10);
				imperitivepastTableLayout.addView(tableRow);
				tableRow.setBackgroundResource(R.color.red);
				tableRow.getBackground().setAlpha(150);

				initiate = false;
			}

			TableRow indpresentTableRow = new TableRow(this);
			indpresentTableRow.addView(text);

			imperitivepastTableLayout.addView(indpresentTableRow);// change
			imperitivepastTableLayout.setStretchAllColumns(true);

		}
		// change
		// here
		//
		// ////////////////////////////////////////////////////////////////////
		verbTense = "Present";
		header = new TextView(this);
		header.setTextSize(textSize);
		header.setText(verbTense);
		header.setGravity(Gravity.CENTER);

		initiate = true;

		for (int i = 97; i <= 97; i += 1) {

			final TextView text = new TextView(this);
			text.setTextSize(textSize);

			text.setText(questionText[i - 1]);
			text.setGravity(Gravity.CENTER);
			text.setPadding(10, 3, 10, 3);
			text.setClickable(true);
			text.setOnClickListener(new Button.OnClickListener() {
				public void onClick(View v) {

					sayHello(text.getText().toString());
				}
			});
			if (initiate == true) {
				TableRow tableRow = new TableRow(this);
				tableRow.addView(header);
				tableRow.setPadding(1, 15, 1, 10);
				infinitivepresentTableLayout.addView(tableRow);
				tableRow.setBackgroundResource(R.color.red);
				tableRow.getBackground().setAlpha(150);

				initiate = false;
			}

			TableRow indpresentTableRow = new TableRow(this);
			indpresentTableRow.addView(text);

			infinitivepresentTableLayout.addView(indpresentTableRow);// change
			infinitivepresentTableLayout.setStretchAllColumns(true);

		}
		// here
		// change
		// here
		//
		// ////////////////////////////////////////////////////////////////////
		verbTense = "Past";
		header = new TextView(this);
		header.setTextSize(textSize);
		header.setText(verbTense);
		header.setGravity(Gravity.CENTER);

		initiate = true;

		for (int i = 98; i <= 98; i += 1) {

			final TextView text = new TextView(this);
			text.setTextSize(textSize);

			text.setText(questionText[i - 1]);
			text.setGravity(Gravity.CENTER);
			text.setPadding(10, 3, 10, 3);
			text.setClickable(true);
			text.setOnClickListener(new Button.OnClickListener() {
				public void onClick(View v) {

					sayHello(text.getText().toString());
				}
			});
			if (initiate == true) {
				TableRow tableRow = new TableRow(this);
				tableRow.addView(header);
				tableRow.setPadding(1, 15, 1, 10);
				infinitivepastTableLayout.addView(tableRow);
				tableRow.setBackgroundResource(R.color.red);
				tableRow.getBackground().setAlpha(150);

				initiate = false;
			}

			TableRow indpresentTableRow = new TableRow(this);
			indpresentTableRow.addView(text);

			infinitivepastTableLayout.addView(indpresentTableRow);// change
			infinitivepastTableLayout.setStretchAllColumns(true);

		}
		// change
		// here
		//
		// ////////////////////////////////////////////////////////////////////
		verbTense = "Present";
		header = new TextView(this);
		header.setTextSize(textSize);
		header.setText(verbTense);
		header.setGravity(Gravity.CENTER);

		initiate = true;

		for (int i = 99; i <= 99; i += 1) {

			final TextView text = new TextView(this);
			text.setTextSize(textSize);

			text.setText(questionText[i - 1]);
			text.setGravity(Gravity.CENTER);
			text.setPadding(10, 3, 10, 3);
			text.setClickable(true);
			text.setOnClickListener(new Button.OnClickListener() {
				public void onClick(View v) {

					sayHello(text.getText().toString());
				}
			});
			if (initiate == true) {
				TableRow tableRow = new TableRow(this);
				tableRow.addView(header);
				tableRow.setPadding(1, 15, 1, 10);
				particplepresentTableLayout.addView(tableRow);
				tableRow.setBackgroundResource(R.color.red);
				tableRow.getBackground().setAlpha(150);

				initiate = false;
			}

			TableRow indpresentTableRow = new TableRow(this);
			indpresentTableRow.addView(text);

			particplepresentTableLayout.addView(indpresentTableRow);// change
			particplepresentTableLayout.setStretchAllColumns(true);

		}
		// here
		// change
		// here
		//
		// ////////////////////////////////////////////////////////////////////
		verbTense = "Past";
		header = new TextView(this);
		header.setTextSize(textSize);
		header.setText(verbTense);
		header.setGravity(Gravity.CENTER);

		initiate = true;

		for (int i = 100; i <= 100; i += 1) {

			final TextView text = new TextView(this);
			text.setTextSize(textSize);

			text.setText(questionText[i - 1]);
			text.setGravity(Gravity.CENTER);
			text.setPadding(10, 3, 10, 3);
			text.setClickable(true);
			text.setOnClickListener(new Button.OnClickListener() {
				public void onClick(View v) {

					sayHello(text.getText().toString());
				}
			});
			if (initiate == true) {
				TableRow tableRow = new TableRow(this);
				tableRow.addView(header);
				tableRow.setPadding(1, 15, 1, 10);
				particplepastTableLayout.addView(tableRow);
				tableRow.setBackgroundResource(R.color.red);
				tableRow.getBackground().setAlpha(150);

				initiate = false;
			}

			TableRow indpresentTableRow = new TableRow(this);
			indpresentTableRow.addView(text);

			particplepastTableLayout.addView(indpresentTableRow);// change
			particplepastTableLayout.setStretchAllColumns(true);

		}

		// //////////////////////////////////////////////////////////////////////////////////////////////
		for (int i = 0; i < 6; i++) {
			TextView v = new TextView(this);
			v.setTextSize(textSize);
			v.setText("");

			TableRow indpresentTableRow = new TableRow(this);
			indpresentTableRow.addView(v);

			emptyTableLayout.addView(indpresentTableRow);// change
		}
		//			 
		// /////////////////////////////////////////////////////////////////////////////////////
		TableRow tr1 = (TableRow) findViewById(R.id.tableRow1);

		TextView tv1 = new TextView(this);// 1
		tv1.setTextSize(textSize);
		tv1.setText("Indicative");
		tv1.setGravity(Gravity.CENTER);
		tv1.setBackgroundResource(R.color.blue);
		tv1.getBackground().setAlpha(200);
		tr1.addView(tv1);
		TableRow.LayoutParams params = (TableRow.LayoutParams) tv1
				.getLayoutParams();
		params.span = 4;
		tv1.setLayoutParams(params); // causes layout update

		TableRow tr2 = (TableRow) findViewById(R.id.tableRow2);
		tr2.addView(indpresentTableLayout);
		tr2.addView(pastsimpleTableLayout);
		// tr2.addView(borderTableLayout);
		tr2.addView(presentperfectTableLayout);
		tr2.addView(pastperfectTableLayout);

		TableRow tr3 = (TableRow) findViewById(R.id.tableRow3);
		tr3.addView(indimperfectTableLayout);
		tr3.addView(futureTableLayout);
		tr3.addView(indpluperfectTableLayout);
		tr3.addView(pastfutureTableLayout);

		TableRow border1 = new TableRow(this);
		View view = new View(this);
		view.setBackgroundColor(R.color.border);
		view.setLayoutParams(new LayoutParams(view.getWidth(), 2));
		border1.addView(view);
		TableRow.LayoutParams paramsView = (TableRow.LayoutParams) view
				.getLayoutParams();
		paramsView.span = 4;
		view.setLayoutParams(paramsView); // causes layout update

		TableRow tr4 = (TableRow) findViewById(R.id.tableRow4);
		TextView tv2 = new TextView(this);
		tv2.setTextSize(textSize);
		tv2.setText("Subjunctive");
		tv2.setGravity(Gravity.CENTER);
		tv2.setBackgroundResource(R.color.blue);
		tv2.getBackground().setAlpha(150);
		tr4.addView(tv2);
		TableRow.LayoutParams params2 = (TableRow.LayoutParams) tv2
				.getLayoutParams();
		params2.span = 4;
		tv2.setLayoutParams(params2); // causes layout update

		TableRow tr5 = (TableRow) findViewById(R.id.tableRow5);
		tr5.addView(subpresentTableLayout);
		tr5.addView(subpastTableLayout);
		tr5.addView(subimperfectTableLayout);
		tr5.addView(subpluperfectTableLayout);

		TableRow border2 = new TableRow(this);
		View view2 = new View(this);
		view2.setBackgroundColor(R.color.border);
		view2.setLayoutParams(new LayoutParams(view2.getWidth(), 2));
		border2.addView(view2);
		TableRow.LayoutParams paramsView2 = (TableRow.LayoutParams) view2
				.getLayoutParams();
		paramsView2.span = 4;
		view2.setLayoutParams(paramsView2); // causes layout update

		TableRow tr6 = (TableRow) findViewById(R.id.tableRow6);
		TextView tv3 = new TextView(this);
		tv3.setTextSize(textSize);
		tv3.setText("Conditional");
		tv3.setGravity(Gravity.CENTER);
		tv3.setBackgroundResource(R.color.blue);
		tv3.getBackground().setAlpha(150);
		tr6.addView(tv3);
		TableRow.LayoutParams params3 = (TableRow.LayoutParams) tv3
				.getLayoutParams();
		params3.span = 4;
		tv3.setLayoutParams(params3); // causes layout update

		TableRow tr7 = (TableRow) findViewById(R.id.tableRow7);
		tr7.addView(condpresentTableLayout);
		tr7.addView(emptyTableLayout);
		tr7.addView(condfirstpastTableLayout);
		tr7.addView(condsecondpastTableLayout);

		TableRow border3 = new TableRow(this);
		View view3 = new View(this);
		view3.setBackgroundColor(R.color.border);
		view3.setLayoutParams(new LayoutParams(view3.getWidth(), 2));
		border3.addView(view3);
		TableRow.LayoutParams paramsView3 = (TableRow.LayoutParams) view3
				.getLayoutParams();
		paramsView3.span = 4;
		view3.setLayoutParams(paramsView3); // causes layout update

		TableRow tr8 = (TableRow) findViewById(R.id.tableRow8);
		TextView tv4 = new TextView(this);
		tv4.setTextSize(textSize);
		tv4.setText("Imperative");
		tv4.setBackgroundResource(R.color.blue);
		tv4.getBackground().setAlpha(150);
		tv4.setGravity(Gravity.CENTER);
		tr8.addView(tv4);
		TableRow.LayoutParams params4 = (TableRow.LayoutParams) tv4
				.getLayoutParams();
		params4.span = 2;
		tv4.setLayoutParams(params4); // causes layout update

		TextView tv5 = new TextView(this);
		tv5.setTextSize(textSize);
		tv5.setText("Infinitive");
		tv5.setGravity(Gravity.CENTER);
		tv5.setBackgroundResource(R.color.blue);
		tv5.getBackground().setAlpha(150);
		tr8.addView(tv5);
		TableRow.LayoutParams params5 = (TableRow.LayoutParams) tv5
				.getLayoutParams();
		params5.span = 2;
		tv5.setLayoutParams(params5); // causes layout update

		TableRow tr9 = (TableRow) findViewById(R.id.tableRow9);
		tr9.addView(imperitivepresentTableLayout);
		tr9.addView(imperitivepastTableLayout);
		tr9.addView(infinitivepresentTableLayout);
		tr9.addView(infinitivepastTableLayout);

		TableRow tr10 = (TableRow) findViewById(R.id.tableRow10);
		TextView tv6 = new TextView(this);
		tv6.setTextSize(textSize);
		tv6.setText("Participle");
		tv6.setBackgroundResource(R.color.blue);
		tv6.getBackground().setAlpha(150);
		tv6.setGravity(Gravity.CENTER);
		tv6.setPadding(1, 15, 1, 1);
		tr10.addView(tv6);
		TableRow.LayoutParams params6 = (TableRow.LayoutParams) tv6
				.getLayoutParams();
		params6.span = 2;
		tv6.setLayoutParams(params6); // causes layout update

		TableRow tr11 = (TableRow) findViewById(R.id.tableRow11);
		tr11.addView(particplepresentTableLayout);
		tr11.addView(particplepastTableLayout);

		TableRow tr12 = (TableRow) findViewById(R.id.tableRow12);

		TableLayout tbl = (TableLayout) findViewById(R.id.menu);
		tbl.setStretchAllColumns(true);
		// tbl.setBackgroundColor(R.color.black);
		tbl.getBackground().setAlpha(200);
		// /*
		// * Add row to TableLayout. *
		// */
		// tbl.addView(tr1);
		// tbl.addView(tr2);
		// tbl.addView(tr3);
		// tbl.addView(border1);
		// tbl.addView(tr4);
		// tbl.addView(tr5);
		// tbl.addView(border2);
		// tbl.addView(tr6);
		// tbl.addView(tr7);
		// tbl.addView(border3);
		// tbl.addView(tr8);
		// tbl.addView(tr9);
		// tbl.addView(tr10);
		// tbl.addView(tr11);
		// tbl.addView(tr12);

	}

	// Implements TextToSpeech.OnInitListener.
	public void onInit(int status) {
		// status can be either TextToSpeech.SUCCESS or TextToSpeech.ERROR.
		if (status == TextToSpeech.SUCCESS) {
			// Set preferred language to US english.
			// Note that a language may not be available, and the result will
			// indicate this.
			mTts.setSpeechRate((float) .8);
			mTts.setPitch((float) 1.05);
			result = mTts.setLanguage(Locale.FRENCH);

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

	// private static final Random RANDOM = new Random();
	// private static final String[] HELLOS = {
	// "Hello",
	// "Salutations",
	// "Greetings",
	// "Howdy",
	// "What's crack-a-lackin?",
	// "That explains the stench!"
	// };

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

	public void changeLayoutSize() {

		super.textSize = 4;
		startActivity(new Intent(Conjugate.this, Conjugate.class));
		Conjugate.this.finish();
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
		MenuItem quit = menu.findItem(R.id.options_menu_close);
		quit.setVisible(false);
		quit.setEnabled(false);
		MenuItem sn = menu.findItem(R.id.vocab);
		sn.setVisible(false);
		sn.setEnabled(false);
		MenuItem help = menu.findItem(R.id.help);
		help.setVisible(false);
		help.setEnabled(false);
		MenuItem background = menu.findItem(R.id.background);
		background.setVisible(false);
		background.setEnabled(false);
		MenuItem selectBackground = menu.findItem(R.id.select_bg);
		selectBackground.setVisible(false);
		selectBackground.setEnabled(false);
		
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		switch (item.getItemId()) {

		case R.id.download:
			Intent myIntent = new Intent(Conjugate.this, Upgrade.class);
			startActivityForResult(myIntent, 0);
			return true;
		case R.id.change_font_size:

			final CharSequence[] items = { "Very Small", "Small", "Medium",
					"Large", "Very Large" };

			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setTitle("Choose a Text Size");
			builder.setItems(items, new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int item) {
					switch (item) {

					case 0: {
						textSize = 7;
						startActivity(new Intent(Conjugate.this,
								Conjugate.class));
						Conjugate.this.finish();
						return;
					}
					case 1: {
						textSize = 9;
						startActivity(new Intent(Conjugate.this,
								Conjugate.class));
						Conjugate.this.finish();
						return;
					}
					case 2: {
						textSize = 12;
						startActivity(new Intent(Conjugate.this,
								Conjugate.class));
						Conjugate.this.finish();
						return;
					}
					case 3: {
						textSize = 15;
						startActivity(new Intent(Conjugate.this,
								Conjugate.class));
						Conjugate.this.finish();
						return;
					}
					case 4: {
						textSize = 18;
						startActivity(new Intent(Conjugate.this,
								Conjugate.class));
						Conjugate.this.finish();
						return;
					}
					}
				}
			});
			AlertDialog alert = builder.create();
			alert.show();

			return true;
		case R.id.back:

			startActivity(new Intent(Conjugate.this, MainScreenTabs.class));
			Conjugate.this.finish();

			return true;

		default:

			break;
		}

		return false;
	}

}
