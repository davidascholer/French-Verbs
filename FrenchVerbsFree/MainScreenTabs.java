package com.AbleApps.FrenchVerbsFree;



import android.app.TabActivity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.widget.TabHost;

public class MainScreenTabs extends TabActivity {
    /** Called when the activity is first created. */
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.tabs);

	    Resources res = getResources(); // Resource object to get Drawables
	    TabHost tabHost = getTabHost();  // The activity TabHost
	    TabHost.TabSpec spec;  // Resusable TabSpec for each tab
	    Intent intent;  // Reusable Intent for each tab

	    // Create an Intent to launch an Activity for the tab (to be reused)
	    intent = new Intent().setClass(this, menu.class);

	    // Initialize a TabSpec for each tab and add it to the TabHost
	    spec = tabHost.newTabSpec("artists").setIndicator("",
	                      res.getDrawable(android.R.drawable.ic_menu_sort_alphabetically))
	                  .setContent(intent);
	    tabHost.addTab(spec);

	    // Do the same for the other tabs
	    intent = new Intent().setClass(this, Search.class);
	    spec = tabHost.newTabSpec("albums").setIndicator("",
	                      res.getDrawable(android.R.drawable.ic_menu_search))
	                  .setContent(intent);
	    tabHost.addTab(spec);


	    //tabHost.setCurrentTab(1);
	}
}