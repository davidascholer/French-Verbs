package com.AbleApps.FrenchVerbsFree;

import android.app.Activity;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.speech.tts.TextToSpeech;
import android.text.Editable;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

public class mainActivity extends Activity {
	// Game preference values
	public static final String FLASHCARD_PREFERENCES = "GamePrefs";
	public static final String FLASHCARD_PREFERENCES_CURRENT_CARD = "CurCard"; // Integer
	public static int textSize = 12;


	// XML Tag Names
	public static final String XML_TAG_CARD_BLOCK = "deck";
	public static final String XML_TAG_CARD = "card";
	public static final String XML_TAG_CARD_ATTRIBUTE_NUMBER = "number";
	public static final String XML_TAG_CARD_ATTRIBUTE_TEXT = "text";
	public static final String XML_TAG_CARD_ATTRIBUTE_IMAGEURL = "imageUrl";

	// Debugging
	public static final String DEBUG_TAG = "Flash Card Log";

	// Global Variables
	public static boolean[] verbDeckChecked;// for the verbs
	public static int NUMBER_OF_VERB_DECKS = 194;// verbs
	public static int CURRENT_VERB_DECK;// from Vocab for load Verb Selection
	// deck

	public static boolean[] vocabDeckChecked;// for the vocab
	public static int vocabDecksNumber = 4;// vocab
	public static int currentVocabDeck;// from Vocab for load VocabSelection
	// deck
	public static int BACKGROUND_RETURN_REF;

	public static Drawable menuDrawable;

	final String[] deckListActual = { "accepter - to accept",
			"acheter - to buy", "admettre - to admit",
			"administrer - to adminster", "admirer - to admire",
			"adorer - to adore", "agir - to act", "aider - to help",
			"aimer - to like, love", "aller - to go", "allumer - to light",
			"amuser - to amuse", "analyser - to analyze",
			"apercevoir - to see", "appeler - to call", "apporter - to bring",
			"apprendre - to learn", "arranger - to arrange",
			"arrêter - to stop, arrest", "arriver - to arrive",
			"asseoir - to sit down", "assister - to attend",
			"attarder - to make late", "atteindre - to attain",
			"attendre - to wait for", "avoir - to have", "avorter - to abort",
			"battre - to beat", "bavarder - to chat", "boire - to drink",
			"cacher - to hide", "calculer - to calculate", "casser - to break",
			"causer - to chat, cause", "célébrer - to celebrate",
			"changer - to change", "chanter - to sing",
			"chercher - to look for", "commander - to order",
			"commencer - to begin", "comprendre - to understand",
			"compter - to count, intend", "conclure - to conclude",
			"conduire - to drive", "connaître - to know",
			"consulter - to consult", "contenir - to contain",
			"continuer - to continue", "contribuer - to contribute",
			"convenir - to convene", "courir - to run", "coûter - to cost",
			"craindre - to fear", "créer - to create", "croire - to believe",
			"danser - to dance", "dater - to date (from), be outdated",
			"défier - to challenge", "défiler - to parade; to unwind",
			"définir - to define", "déjeuner - to have lunch",
			"demander - to ask", "dépenser - to spend",
			"descendre - to descend", "désirer - to desire",
			"dessiner - to draw", "détester - to hate", "devenir - to become",
			"devoir - to have to", "dîner - to have dinner", "dire - to say",
			"diriger - to direct", "donner - to give", "dormir - to sleep",
			"écouler - to sell", "écouter - to listen", "écrire - to write",
			"effacer - to erase", "emmener - to take", "enseigner - to teach",
			"entrer - to enter", "envoyer - to send", "épeler - to spell",
			"espérer - to hope", "essayer - to try", "établir - to establish",
			"éteindre - to extinguish", "être - to be", "étudier - to study",
			"exister - to exist", "expliquer - to explain",
			"faire - to make, do", "falloir - to be necessary",
			"fendre - to split, crack", "fermer - to close",
			"finir - to finish", "fouiner - to snoop",
			"fournir - to furnish, provide", "fumer - to smoke",
			"garantir - to guarantee", "habiter - to live",
			"hésiter - to hesitate", "inquiéter - to worry",
			"inscrire - to write down", "intéresser - to interest",
			"inviter - to invite", "jouer - to play", "laisser - to leave",
			"laver - to wash", "lever - to lift", "lire - to read",
			"manger - to eat", "mettre - to put", "montrer - to show",
			"mourir - to die", "nager - to swim", "naître - to be born",
			"obtenir - to obtain", "offrir - to offer",
			"organiser - to organize", "oublier - to forget",
			"ouvrir - to open", "paraître - to seem", "parler - to talk",
			"partager - to share", "partir - to leave", "parvenir - to reach",
			"passer - to spend (time)", "payer - to pay", "peigner - to comb",
			"penser - to think", "perdre - to lose", "plaindre - to pity",
			"plaire - to please", "plaisanter - to joke", "pleurer - to cry",
			"pleuvoir - to rain", "porter - to wear", "poser - to put",
			"posséder - to possess", "pouvoir - to be able",
			"pratiquer - to practice", "préférer - to prefer",
			"prendre - to take", "prêter - to loan", "prévoir - to foresee",
			"proposer - to suggest, propose", "recevoir - to receive",
			"réfléchir - to think", "refuser - to refuse",
			"regarder - to look at, watch", "rejoindre - to meet, rejoin",
			"remercier - to thank", "remettre - to put back (on)",
			"remplir - to fill", "remporter - to take again/back",
			"rendre - to give back", "rentrer - to return home",
			"renvoyer - to dismiss", "réparer - to repair",
			"répondre - to answer", "reprendre - to take again, to recover",
			"résoudre - to resolve", "rester - to stay",
			"retourner - to return", "réveiller - to wake up",
			"revenir - to come back", "rêver - to dream", "saisir - to seize",
			"savoir - to know", "sécher - to dry", "signer - to sign",
			"sortir - to go out", "souhaiter - to wish", "suivre - to follow",
			"taire (se) - to be quiet", "tenir - to hold", "terminer - to end",
			"tester - to test", "tomber - to fall", "tousser - to cough",
			"traduire - to translate", "transmettre - to transmit",
			"travailler - to work", "trouver - to find", "utiliser - to use",
			"vendre - to sell", "venir - to come", "visiter - to visit",
			"vivre - to live", "voir - to see", "voler - to steal, fly",
			"vouloir - to want", "voyager - to travel" };

	final String[] deckListXmlName = {  "accepter", "acheter", "admettre",
			"administrer", "admirer", "adorer", "agir", "aider", "aimer",
			"aller", "allumer", "amuser", "analyser", "apercevoir", "appeler",
			"apporter", "apprendre", "arranger", "arreter", "arriver",
			"asseoir", "assister", "attarder", "atteindre", "attendre",
			"avoir", "avorter", "battre", "bavarder", "boire", "cacher",
			"calculer", "casser", "causer", "celebrer", "changer", "chanter",
			"chercher", "commander", "commencer", "comprendre", "compter",
			"conclure", "conduire", "connaitre", "consulter", "contenir",
			"continuer", "contribuer", "convenir", "courir", "couter",
			"craindre", "creer", "croire", "danser", "dater", "defier",
			"defiler", "definir", "dejeuner", "demander", "depenser",
			"descendre", "desirer", "dessiner", "detester", "devenir",
			"devoir", "diner", "dire", "diriger", "donner", "dormir",
			"ecouler", "ecouter", "ecrire", "effacer", "emmener", "enseigner",
			"entrer", "envoyer", "epeler", "esperer", "essayer", "etablir",
			"eteindre", "etre", "etudier", "exister", "expliquer", "faire",
			"falloir", "fendre", "fermer", "finir", "fouiner", "fournir",
			"fumer", "garantir", "habiter", "hesiter", "inquieter", "inscrire",
			"interesser", "inviter", "jouer", "laisser", "laver", "lever",
			"lire", "manger", "mettre", "montrer", "mourir", "nager", "naitre",
			"obtenir", "offrir", "organiser", "oublier", "ouvrir", "paraitre",
			"parler", "partager", "partir", "parvenir", "passer", "payer",
			"peigner", "penser", "perdre", "plaindre", "plaire", "plaisanter",
			"pleurer", "pleuvoir", "porter", "poser", "posseder", "pouvoir",
			"pratiquer", "preferer", "prendre", "preter", "prevoir",
			"proposer", "recevoir", "reflechir", "refuser", "regarder",
			"rejoindre", "remercier", "remettre", "remplir", "remporter",
			"rendre", "rentrer", "renvoyer", "reparer", "repondre",
			"reprendre", "resoudre", "rester", "retourner", "reveiller",
			"revenir", "rever", "saisir", "savoir", "secher", "signer",
			"sortir", "souhaiter", "suivre", "taire", "tenir", "terminer",
			"tester", "tomber", "tousser", "traduire", "transmettre",
			"travailler", "trouver", "utiliser", "vendre", "venir", "visiter",
			"vivre", "voir", "voler", "vouloir", "voyager" };
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
