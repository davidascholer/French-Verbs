package com.AbleApps.FrenchVerbsFree;

import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;



public class Upgrade extends mainActivity{
	
	WebView mWebView;
	String url;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.upgrade);



			mWebView = (WebView) findViewById(R.id.webView1);
			mWebView.getSettings().setJavaScriptEnabled(true);
			// mWebView.loadUrl("http://www.google.com");

			mWebView.setWebViewClient(new WebViewClient());


		

					mWebView.setVisibility(View.VISIBLE);

					url = "https://market.android.com/details?id=com.AbleApps.FrenchVerbs";
					mWebView.loadUrl(url);
		
	}
	
}
