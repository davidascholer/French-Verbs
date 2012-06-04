package com.AbleApps.FrenchVerbsFree;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Hashtable;
import java.util.Locale;
import java.util.Random;

import org.xmlpull.v1.XmlPullParserException;

import com.google.ads.AdRequest;
import com.google.ads.AdView;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.content.res.XmlResourceParser;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Animation.AnimationListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class FlashCards extends mainActivity implements OnTouchListener,
		TextToSpeech.OnInitListener {
	/** Called when the activity is first created. */

	private TextToSpeech mTts;

	SharedPreferences mGameSettings;
	Hashtable<Integer, Question> mQuestions;

	boolean shuffleBoolean = false;
	boolean englishmode = true;
	boolean initialBatchLoad = true;
	int currentQuestionNumber = 0;

	private float downXValue;
	private static final int sMenuResource = R.menu.options_menu;
	int deckSize;
	private Menu mMenu;
	// SET THE DECK NUMBERS
	int CURRENT_DECK = 1;
	int result;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.full_screen);

		mTts = new TextToSpeech(this, this // TextToSpeech.OnInitListener
		);

		//
		// RelativeLayout rl = (RelativeLayout)findViewById(R.id.relLayout);
		// ImageButton textButton =
		// (ImageButton)findViewById(R.id.flipImagebutton);
		// rl.bringChildToFront(textButton);

		// switch cards
		Button englishButton = (Button) findViewById(R.id.englishFlashCardButton);
		// Button eButton = (Button) findViewById(R.id.englishFlashCardButton);
		// eButton.setClickable(true);
		englishButton.setOnTouchListener((OnTouchListener) this);
		// eButton.setOnClickListener(new View.OnClickListener() {
		// public void onClick(View v) {
		// flipToFrench(true, true);
		// }
		// });

		// Button fButton = (Button) findViewById(R.id.frenchFlashCardButton);
		Button frenchButton = (Button) findViewById(R.id.frenchFlashCardButton);
		// eButton.setClickable(true);
		frenchButton.setOnTouchListener((OnTouchListener) this);
		// fButton.setOnClickListener(new View.OnClickListener() {
		// public void onClick(View v) {
		//
		// flipToEnglish(true, true);
		// }
		//
		// });
		// Button shuffle = (Button) findViewById(R.id.button1);
		// shuffle.setOnClickListener(new View.OnClickListener() {
		// public void onClick(View v) {
		//
		// boolean flip = false;
		// if (englishmode == true) {
		// if (currentQuestionNumber % 2 == 0) {
		// flip = true;
		// }
		// }
		// if (englishmode == false) {
		// if (currentQuestionNumber % 2 == 1) {
		// flip = true;
		// }
		// }
		// shuffleBoolean = true;
		// if (englishmode == true) {
		// currentQuestionNumber = 1;
		// } else {
		// currentQuestionNumber = 2;
		// }
		// loadQuestions();
		//
		// if (flip == true) {
		// if (englishmode == false) {
		// flipToFrench(false, true);
		// } else {
		// flipToEnglish(false, true);
		// }
		// flip = false;
		// }
		// }
		// });

		ImageButton pronounce = (ImageButton) findViewById(R.id.flipImagebutton);
		pronounce.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {

				if (currentQuestionNumber % 2 != 0) {
					result = mTts.setLanguage(Locale.US);
					mTts.setSpeechRate((float) 1);
					mTts.setPitch((float) 1.2);
				} else {
					result = mTts.setLanguage(Locale.FRENCH);
					mTts.setSpeechRate((float) .8);
					mTts.setPitch((float) 1.05);
				}
				sayHello();

			}
		});

		// Button flip = (Button) findViewById(R.id.button3);
		// flip.setOnClickListener(new View.OnClickListener() {
		// public void onClick(View v) {
		//
		// flipDeck();
		//
		// }
		// });

		// Retrieve the shared preferences
		mGameSettings = getSharedPreferences(FLASHCARD_PREFERENCES,
				Context.MODE_PRIVATE);

		// Initialize question batch
		mQuestions = new Hashtable<Integer, Question>(deckSize);

		loadQuestions();
		// flaw in code???????????????????????????
		// // Load the questions
		// int currentQuestionNumber =
		// mGameSettings.getInt(FLASHCARD_PREFERENCES_CURRENT_CARD, 0);

		// // If we're at the beginning of the quiz, initialize the Shared
		// preferences
		// if (currentQuestionNumber == 0) {
		// Editor editor = mGameSettings.edit();
		// editor.putInt(FLASHCARD_PREFERENCES_CURRENT_CARD, 1);
		// editor.commit();
		// currentQuestionNumber = 1;
		// }
		// ?????????????????????????????????????

	}

	private void loadQuestions() {

		try {
			loadQuestionBatch(currentQuestionNumber);
		} catch (Exception e) {
			Log.e(DEBUG_TAG, "Loading initial question batch failed", e);
		}
		Button flashCardButton;
		if (englishmode == true) {
			// // Set up Button
			flashCardButton = (Button) findViewById(R.id.englishFlashCardButton);

		} else {
			flashCardButton = (Button) findViewById(R.id.frenchFlashCardButton);

		}

		// If the question was loaded properly, display it
		if (mQuestions.containsKey(currentQuestionNumber) == true) {
			// Set the text of the textswitcher
			flashCardButton.setText(getQuestionText(currentQuestionNumber));

			// // Set the image of the imageswitcher
			// Drawable image = getQuestionImageDrawable(currentQuestionNumber);
			// questionImageSwitcher.setImageDrawable(image);
		} else {
			// Tell the user we don't have any new questions at this time
			handleNoQuestions();
		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Hold on to this
		mMenu = menu;

		// Inflate the currently selected menu XML resource.
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(sMenuResource, menu);
		MenuItem bg = menu.findItem(R.id.background);
		bg.setVisible(false);
		bg.setEnabled(false);

		MenuItem sbg = menu.findItem(R.id.select_bg);
		sbg.setVisible(false);
		sbg.setEnabled(false);
		MenuItem back = menu.findItem(R.id.back);
		back.setVisible(false);
		back.setEnabled(false);
		MenuItem changeFont = menu.findItem(R.id.change_font_size);
		changeFont.setVisible(false);
		changeFont.setEnabled(false);
		MenuItem vocab = menu.findItem(R.id.vocab);
		vocab.setVisible(false);
		vocab.setEnabled(false);

		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.download:
			Intent myIntent = new Intent(FlashCards.this, Upgrade.class);
			startActivityForResult(myIntent, 0);
			return true;
		case R.id.options_menu_close:
			android.os.Process.killProcess(android.os.Process.myPid());
			return true;
		case R.id.shuffle:
			shuffleBoolean = true;
			if (englishmode == true) {
				if (currentQuestionNumber % 2 == 0) {
					flipToEnglish(true, true);
				}
				currentQuestionNumber = 1;
			} else {
				if (currentQuestionNumber % 2 != 0) {
					flipToFrench(true, true);
				}
				currentQuestionNumber = 2;
			}
			loadQuestions();

			return true;
		case R.id.englishmode_true:
			englishmode = true;
			Toast.makeText(this, "English Deck Face Up", Toast.LENGTH_SHORT)
					.show();
			if (currentQuestionNumber % 2 == 0) {
				flipToEnglish(true, true);
			}
			return true;
		case R.id.englishmode_false:
			englishmode = false;
			Toast.makeText(this, "French Deck Face Up", Toast.LENGTH_SHORT)
					.show();
			if (currentQuestionNumber % 2 != 0) {
				flipToFrench(true, true);
			}
			return true;
		case R.id.speak:
			if (currentQuestionNumber % 2 != 0) {
				result = mTts.setLanguage(Locale.US);
				mTts.setSpeechRate((float) 1);
				mTts.setPitch((float) 1.2);
			} else {
				result = mTts.setLanguage(Locale.FRENCH);
				mTts.setSpeechRate((float) .8);
				mTts.setPitch((float) 1.05);
			}
			sayHello();
			return true;

		case R.id.help:
			TextView msg = new TextView(this);
			msg
					.setText("Speak Icon: Pronounces the word for you. \nShuffle: Rearranges the card deck order. \nFlip to Deck: Sets the first card to view when switching to a new card.");
			msg.setGravity(Gravity.CENTER_HORIZONTAL);

			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setView(msg).setCancelable(true).setPositiveButton("OK",
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int id) {
							return;
						}
					});
			final AlertDialog alrt = builder.create();
			alrt.show();
			return true;
			// Generic catch all for all the other menu resources
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

	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		mMenu = menu;

		super.onPrepareOptionsMenu(menu);

		if (englishmode == true) {
			MenuItem em = menu.findItem(R.id.englishmode_true);
			em.setVisible(false);
			em.setEnabled(false);
			MenuItem fm = menu.findItem(R.id.englishmode_false);
			fm.setVisible(true);
			fm.setEnabled(true);
		} else {
			MenuItem fm = menu.findItem(R.id.englishmode_false);
			fm.setVisible(false);
			fm.setEnabled(false);
			MenuItem em = menu.findItem(R.id.englishmode_true);
			em.setVisible(true);
			em.setEnabled(true);
		}

		return true;
	}

	/**
	 * 
	 * Helper method to record the answer the user gave and load up the next
	 * question.
	 */

	private void handleAnswerAndShowNextQuestion(final boolean englishFaceUp) {

		final Button englishBtn = (Button) findViewById(R.id.englishFlashCardButton);
		final Button frenchBtn = (Button) findViewById(R.id.frenchFlashCardButton);
		Animation card_out = AnimationUtils.loadAnimation(this,
				R.anim.cardout_left);
		final Animation card_in = AnimationUtils.loadAnimation(this,
				R.anim.cardin_right);
		if (currentQuestionNumber % 2 == 0) {
			frenchBtn.startAnimation(card_out);
		} else {
			englishBtn.startAnimation(card_out);
		}
		card_out.setAnimationListener(new AnimationListener() {
			public void onAnimationEnd(Animation animation) {
				// The animation has ended. Do methods then set card_in
				// int curScore = mGameSettings.getInt(GAME_PREFERENCES_SCORE,
				// 0);
				// int nextQuestionNumber =
				// mGameSettings.getInt(FLASHCARD_PREFERENCES_CURRENT_CARD, 1) +
				// 1;
				if (englishmode == true) {
					if (englishFaceUp == true) {
						currentQuestionNumber += 2;
					} else {
						currentQuestionNumber += 1;
					}
				} else {
					// in french mode
					if (englishFaceUp == true) {
						currentQuestionNumber += 3;
					} else {
						currentQuestionNumber += 2;
					}
				}
				// Editor editor = mGameSettings.edit();
				// editor.putInt(FLASHCARD_PREFERENCES_CURRENT_CARD,
				// nextQuestionNumber);
				// editor.commit();

				if (mQuestions.containsKey(currentQuestionNumber) == false) {
					// Load next batch
					try {
						loadQuestionBatch(currentQuestionNumber);
					} catch (Exception e) {
						Log.e(DEBUG_TAG,
								"Loading updated question batch failed", e);
					}
				}

				if (mQuestions.containsKey(currentQuestionNumber) == true) {
					// Update question text
					if (englishmode == true) {
						Button englishButton = (Button) findViewById(R.id.englishFlashCardButton);
						englishButton
								.setText(getQuestionText(currentQuestionNumber));
						flipToEnglish(false, false);
					} else {
						Button frenchButton = (Button) findViewById(R.id.frenchFlashCardButton);
						frenchButton
								.setText(getQuestionText(currentQuestionNumber));
						flipToFrench(false, false);
					}

				} else {
					if (englishFaceUp == true) {
						currentQuestionNumber = -1;
						if (englishmode == true) {
							if (englishFaceUp == true) {
								currentQuestionNumber += 2;
							} else {
								currentQuestionNumber += 1;
							}
						} else {
							// in french mode
							if (englishFaceUp == true) {
								currentQuestionNumber += 3;
							} else {
								currentQuestionNumber += 2;
							}
						}
						try {
							loadQuestionBatch(currentQuestionNumber);
						} catch (Exception e) {
							Log.e(DEBUG_TAG,
									"Loading updated question batch failed", e);
						}
						if (englishmode == true) {
							Button englishButton = (Button) findViewById(R.id.englishFlashCardButton);
							englishButton
									.setText(getQuestionText(currentQuestionNumber));
							flipToEnglish(false, false);
						} else {
							Button frenchButton = (Button) findViewById(R.id.frenchFlashCardButton);
							frenchButton
									.setText(getQuestionText(currentQuestionNumber));
							flipToFrench(false, false);
						}
					} else {
						currentQuestionNumber = 0;
						if (englishmode == true) {
							if (englishFaceUp == true) {
								currentQuestionNumber += 2;
							} else {
								currentQuestionNumber += 1;
							}
						} else {
							// in french mode
							if (englishFaceUp == true) {
								currentQuestionNumber += 3;
							} else {
								currentQuestionNumber += 2;
							}
						}
						try {
							loadQuestionBatch(currentQuestionNumber);
						} catch (Exception e) {
							Log.e(DEBUG_TAG,
									"Loading updated question batch failed", e);
						}
						if (englishmode == true) {
							Button englishButton = (Button) findViewById(R.id.englishFlashCardButton);
							englishButton
									.setText(getQuestionText(currentQuestionNumber));
							flipToEnglish(false, false);
						} else {
							Button frenchButton = (Button) findViewById(R.id.frenchFlashCardButton);
							frenchButton
									.setText(getQuestionText(currentQuestionNumber));
							flipToFrench(false, false);
						}
					}
				}
				if (currentQuestionNumber % 2 == 0) {
					frenchBtn.startAnimation(card_in);
				} else {
					englishBtn.startAnimation(card_in);
				}
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
		AdView adView = (AdView) findViewById(R.id.adView);
		adView.loadAd(new AdRequest());
	}

	// ////////////////////////////////
	//
	@Override
	public void onDestroy() {
		// Don't forget to shutdown!
		if (mTts != null) {
			mTts.stop();
			mTts.shutdown();
		}

		super.onDestroy();
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

	// private static final Random RANDOM = new Random();
	// private static final String[] HELLOS = {
	// "Hello",
	// "Salutations",
	// "Greetings",
	// "Howdy",
	// "What's crack-a-lackin?",
	// "That explains the stench!"
	// };

	private void sayHello() {
		// Select a random hello.
		// int helloLength = HELLOS.length;
		String returnString = "";
		boolean paste = true;
		String hello = getQuestionText(currentQuestionNumber);
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

	// /////////////////////////////////
	// /**
	// *
	// * Helper method to record the answer the user gave and load up the next
	// * question.
	// */
	//
	private void handleAnswerAndShowPreviousQuestion(final boolean englishFaceUp) {

		final Button englishBtn = (Button) findViewById(R.id.englishFlashCardButton);
		final Button frenchBtn = (Button) findViewById(R.id.frenchFlashCardButton);
		Animation card_out = AnimationUtils.loadAnimation(this,
				R.anim.cardout_right);
		final Animation card_in = AnimationUtils.loadAnimation(this,
				R.anim.cardin_left);
		if (currentQuestionNumber % 2 == 0) {
			frenchBtn.startAnimation(card_out);
		} else {
			englishBtn.startAnimation(card_out);
		}
		card_out.setAnimationListener(new AnimationListener() {
			public void onAnimationEnd(Animation animation) {
				// int curScore = mGameSettings.getInt(GAME_PREFERENCES_SCORE,
				// 0);
				// int nextQuestionNumber =
				// mGameSettings.getInt(FLASHCARD_PREFERENCES_CURRENT_CARD, 1) +
				// 1;
				if (englishmode == true) {
					if (englishFaceUp == true) {
						currentQuestionNumber -= 2;
					} else {
						currentQuestionNumber -= 3;
					}
				} else {
					// in french mode
					if (englishFaceUp == true) {
						currentQuestionNumber -= 1;
					} else {
						currentQuestionNumber -= 2;
					}
				}
				// Editor editor = mGameSettings.edit();
				// editor.putInt(FLASHCARD_PREFERENCES_CURRENT_CARD,
				// nextQuestionNumber);
				// editor.commit();

				if (mQuestions.containsKey(currentQuestionNumber) == false) {
					// Load next batch
					try {
						loadQuestionBatch(currentQuestionNumber);
					} catch (Exception e) {
						Log.e(DEBUG_TAG,
								"Loading updated question batch failed", e);
					}
				}

				if (mQuestions.containsKey(currentQuestionNumber) == true) {
					// Update question text
					if (englishmode == true) {
						Button englishButton = (Button) findViewById(R.id.englishFlashCardButton);
						englishButton
								.setText(getQuestionText(currentQuestionNumber));
						flipToEnglish(false, false);
					} else {
						Button frenchButton = (Button) findViewById(R.id.frenchFlashCardButton);
						frenchButton
								.setText(getQuestionText(currentQuestionNumber));
						flipToFrench(false, false);
					}

				}
				if (currentQuestionNumber % 2 == 0) {
					frenchBtn.startAnimation(card_in);
				} else {
					englishBtn.startAnimation(card_in);
				}
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
		AdView adView = (AdView) findViewById(R.id.adView);
		adView.loadAd(new AdRequest());
	}

	/**
	 * 
	 * Helper method to record the answer the user gave and load up the previous
	 * question.
	 */

	private void handleNoQuestions() {
		if (englishmode == true) {
			Button englishButton = (Button) findViewById(R.id.englishFlashCardButton);
			englishButton
					.setText(getResources().getText(R.string.no_questions));
		} else {
			Button frenchButton = (Button) findViewById(R.id.frenchFlashCardButton);
			frenchButton.setText(getResources().getText(R.string.no_questions));
		}

	}

	/**
	 * Returns a {@code String} representing the text for a particular question
	 * number
	 * 
	 * @param questionNumber
	 *            The question number to get the text for
	 * @return The text of the question, or null if {@code questionNumber} not
	 *         found
	 */
	private String getQuestionText(Integer questionNumber) {
		String text = null;
		Question curQuestion = (Question) mQuestions.get(questionNumber);
		if (curQuestion != null) {
			text = curQuestion.mText;
		}
		return text;
	}

	private void flipToFrench(boolean changeCurQNumber, boolean doAnimation) {
		// odd numbers for english cards
		if (changeCurQNumber == true) {
			currentQuestionNumber += 1;
		}

		/**
		 * flip
		 */
		if (doAnimation == true) {
			applyRotation(false);
		} else {
			Button englishFlashCardButton = (Button) findViewById(R.id.englishFlashCardButton);
			englishFlashCardButton.setVisibility(View.INVISIBLE);
			Button frenchFlashCardButton = (Button) findViewById(R.id.frenchFlashCardButton);
			frenchFlashCardButton
					.setText(getQuestionText(currentQuestionNumber));
			frenchFlashCardButton.setVisibility(View.VISIBLE);
		}

	}

	private void flipToEnglish(boolean changeCurQNumber, boolean doAnimation) {
		// odd numbers for english cards
		if (changeCurQNumber == true) {
			currentQuestionNumber -= 1;
		}

		if (doAnimation == true) {
			applyRotation(true);
		} else {
			Button frenchFlashCardButton = (Button) findViewById(R.id.frenchFlashCardButton);
			frenchFlashCardButton.setVisibility(View.INVISIBLE);
			Button englishFlashCardButton = (Button) findViewById(R.id.englishFlashCardButton);
			englishFlashCardButton
					.setText(getQuestionText(currentQuestionNumber));
			englishFlashCardButton.setVisibility(View.VISIBLE);
		}

	}

	/**
	 * Setup a new 3D rotation on the container view.
	 * 
	 * @param position
	 *            the item that was clicked to show a picture, or -1 to show the
	 *            list
	 * @param start
	 *            the start angle at which the rotation must begin
	 * @param end
	 *            the end angle of the rotation
	 */
	private void applyRotation(boolean flipToEnglish) {
		float start = 0;
		float end = 90;
		// Find the center of the container
		ViewGroup mButton = (ViewGroup) findViewById(R.id.buttonLayout);
		final float centerX = mButton.getWidth() / 2.0f;
		final float centerY = mButton.getHeight() / 2.0f;

		// Create a new 3D rotation with the supplied parameter
		// The animation listener is used to trigger the next animation
		Rotate3dAnimation rotation = new Rotate3dAnimation(start, end, centerX,
				centerY, 310.0f, true);
		rotation.setDuration(250);
		// rotation.setFillAfter(true);
		rotation.setInterpolator(new AccelerateInterpolator());
		rotation.setAnimationListener(new DisplayNextView(flipToEnglish));

		mButton.startAnimation(rotation);
	}

	private final class DisplayNextView implements Animation.AnimationListener {
		boolean mflipToEnglish;

		private DisplayNextView(boolean flipToEnglish) {
			mflipToEnglish = flipToEnglish;

		}

		public void onAnimationStart(Animation animation) {
		}

		public void onAnimationEnd(Animation animation) {
			// setting the card (if english, flip to french, etc)
			if (mflipToEnglish == false) {
				Button englishFlashCardButton = (Button) findViewById(R.id.englishFlashCardButton);
				englishFlashCardButton.setVisibility(View.INVISIBLE);
				Button frenchFlashCardButton = (Button) findViewById(R.id.frenchFlashCardButton);
				frenchFlashCardButton
						.setText(getQuestionText(currentQuestionNumber));
				frenchFlashCardButton.setVisibility(View.VISIBLE);
			} else {
				Button frenchFlashCardButton = (Button) findViewById(R.id.frenchFlashCardButton);
				frenchFlashCardButton.setVisibility(View.INVISIBLE);
				Button englishFlashCardButton = (Button) findViewById(R.id.englishFlashCardButton);
				englishFlashCardButton
						.setText(getQuestionText(currentQuestionNumber));
				englishFlashCardButton.setVisibility(View.VISIBLE);
			}
			SwapViews sw = new SwapViews();
			sw.run();
		}

		public void onAnimationRepeat(Animation animation) {
		}
	}

	/**
	 * This class is responsible for swapping the views and start the second
	 * half of the animation.
	 */
	private final class SwapViews implements Runnable {

		public SwapViews() {

		}

		public void run() {
			ViewGroup mButton = (ViewGroup) findViewById(R.id.buttonLayout);
			final float centerX = mButton.getWidth() / 2.0f;
			final float centerY = mButton.getHeight() / 2.0f;
			Rotate3dAnimation rotation;

			// if (mPosition > -1) {
			// mPhotosList.setVisibility(View.GONE);
			// mImageView.setVisibility(View.VISIBLE);
			// mImageView.requestFocus();
			//
			// rotation = new Rotate3dAnimation(90, 180, centerX, centerY,
			// 310.0f, false);
			// } else {
			// mImageView.setVisibility(View.GONE);
			// mPhotosList.setVisibility(View.VISIBLE);
			// mPhotosList.requestFocus();

			rotation = new Rotate3dAnimation(270, 360, centerX, centerY,
					310.0f, false);
			// }

			rotation.setDuration(250);
			rotation.setFillAfter(true);
			rotation.setInterpolator(new DecelerateInterpolator());

			mButton.startAnimation(rotation);
		}
	}

	// private void animateImageView(){
	// Button b = (Button)this.findViewById(R.id.buttonAnim);
	// b.startAnimation(new FlipAnimation());
	// }
	/**
	 * Loads the XML into the {@see mQuestions} class member variable
	 * 
	 * @param startQuestionNumber
	 *            TODO: currently unused
	 * @throws XmlPullParserException
	 *             Thrown if XML parsing errors
	 * @throws IOException
	 *             Thrown if errors loading XML
	 */
	private void loadQuestionBatch(int number) throws XmlPullParserException,
			IOException {

		int docCount = 0;

		Integer[] questionNum;
		Integer[] randomQuestionNum;
		String[] questionText;

		// Remove old batch
		mQuestions.clear();

		loadCurrentDeck(number);

		// TODO: Contact the server and retrieve a batch of question data,
		// beginning at startQuestionNumber
		XmlResourceParser questionBatch;

		// BEGIN MOCK QUESTIONS
		// make sure to keep these in the same order as the list ListView
		// (alphabetically)

		switch (CURRENT_DECK) {
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

		// SETTING THE CURRENT QUESTION NUMBER
		if (initialBatchLoad == true) {
			currentQuestionNumber = 1;
			initialBatchLoad = false;
		} else {
			if (currentQuestionNumber == 0) {
				currentQuestionNumber = deckSize;
			}
			if (currentQuestionNumber == -1) {
				currentQuestionNumber = deckSize - 1;
			}
			if (currentQuestionNumber == -2) {
				currentQuestionNumber = deckSize - 2;
			}
			if (currentQuestionNumber == deckSize + 1) {
				currentQuestionNumber = 1;
			}
			if (currentQuestionNumber == deckSize + 2) {
				currentQuestionNumber = 2;
			}
			if (currentQuestionNumber == deckSize + 3) {
				currentQuestionNumber = 3;
			}
		}
		// Parse the XML
		int eventType = -1;

		randomQuestionNum = new Integer[deckSize];
		questionNum = new Integer[deckSize];
		questionText = new String[deckSize];

		while (eventType != XmlResourceParser.END_DOCUMENT) {
			if (eventType == XmlResourceParser.START_TAG) {

				// Get the name of the tag (eg questions or question)
				String strName = questionBatch.getName();

				if (strName.equals(XML_TAG_CARD)) {

					String questionNumber = questionBatch.getAttributeValue(
							null, XML_TAG_CARD_ATTRIBUTE_NUMBER);
					questionNum[docCount] = new Integer(questionNumber);
					questionText[docCount] = questionBatch.getAttributeValue(
							null, XML_TAG_CARD_ATTRIBUTE_TEXT);

					docCount += 1;

				}
			}
			eventType = questionBatch.next();
		}

		// Arrays.sort(questionText);
		if (shuffleBoolean == false) {
			for (int i = 0; i < deckSize; i++) {
				// Save data to our hashtable
				mQuestions.put(questionNum[i], new Question(questionNum[i],
						questionText[i]));
			}
		} else {
			// here is my intense code to shuffle an array, handle duplicates,
			// and set into a hashtable.
			for (int i = 0; i < deckSize; i++) {
				if (i % 2 == 1) {
					// if random number is odd, make it one less than the one
					// before it (4,3;14,13;28,27;etc.)
					randomQuestionNum[i] = randomQuestionNum[i - 1] - 1;
				} else {
					Random r = new Random();
					int randomNumber = r.nextInt(deckSize);
					// if randomNumber is... well, you get the idea.
					if (randomNumber < 0) {
						randomNumber = 2;
					}
					// if randomNumber is odd, we want it to be even
					if (randomNumber % 2 == 1) {
						randomNumber -= 1;
					}
					// check to make sure randomNumber isnt already in
					// randomQuestionNumber
					for (int j = 1; j <= deckSize; j++) {
						if (randomQuestionNum[j] == null) {
							break;
						}
						if (randomQuestionNum[j] == randomNumber) {
							randomNumber += 2;
							if (randomNumber > deckSize) {
								randomNumber = 2;
							}// end if in while loop
							j = 0;// restart the loop
						}// end while
					}// end for
					randomQuestionNum[i] = randomNumber;
				}// end else
			}// end for
			for (int i = 0; i < deckSize; i++) {
				// Save data to our hashtable
				mQuestions.put(randomQuestionNum[i], new Question(i,
						questionText[i]));
			}// end for
			shuffleBoolean = false;
		}// end else
	}// end method

	// end intense code

	private void loadCurrentDeck(int number) {

		// SETTING THE CURRENT DECK
		if (number < 1) {
			do {
				CURRENT_DECK -= 1;
				if (CURRENT_DECK == 0) {
					CURRENT_DECK = vocabDecksNumber;
				}
			} while (vocabDeckChecked[CURRENT_DECK - 1] == false);
		}
		if (number > deckSize) {
			do {
				CURRENT_DECK += 1;
				if (CURRENT_DECK == vocabDecksNumber + 1) {
					CURRENT_DECK = 1;
				}
			} while (vocabDeckChecked[CURRENT_DECK - 1] == false);
		}

	}

	/**
	 * Object to manage the data for a single quiz question
	 * 
	 */
	class Question {
		int mNumber;
		String mText;

		// String mImageUrl;
		/**
		 * 
		 * Constructs a new question object
		 * 
		 * @param questionNum
		 *            The number of this question
		 * @param questionText
		 *            The text for this question
		 * @param questionImageUrl
		 *            A valid image Url to display with this question
		 */
		public Question(int questionNum, String questionText) {
			mNumber = questionNum;
			mText = questionText;
			// mImageUrl = questionImageUrl;
		}
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
				if (currentX - downXValue > 25) {
					if (currentQuestionNumber % 2 == 0) {
						handleAnswerAndShowPreviousQuestion(false);
					} else {
						handleAnswerAndShowPreviousQuestion(true);
					}
				} else {
					if (currentQuestionNumber % 2 == 0) {
						flipToEnglish(true, true);
					} else {
						flipToFrench(true, true);
					}
				}
			}

			// going forwards: pushing stuff to the left
			if (downXValue > currentX) {
				if (downXValue - currentX > 25) {
					if (currentQuestionNumber % 2 == 0) {
						handleAnswerAndShowNextQuestion(false);
					} else {
						handleAnswerAndShowNextQuestion(true);
					}
				} else {
					if (currentQuestionNumber % 2 == 0) {
						flipToEnglish(true, true);
					} else {
						flipToFrench(true, true);
					}
				}
			}// ends first if (downXValue > currentX)
			if (downXValue == currentX) {
				if (currentQuestionNumber % 2 == 0) {
					flipToEnglish(true, true);
				} else {
					flipToFrench(true, true);
				}
			}
			break;
		}

		}

		// if you return false, these actions will not be recorded
		return true;
	}

}