package com.gmail.va034600.nreader.ui.activity;

import android.os.Bundle;
import android.webkit.WebView;

public class AboutActivity extends BaseActivity {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		WebView webView = new WebView(this);

		// WebView内でJavaScriptを有効化
		webView.getSettings().setJavaScriptEnabled(true);

		setContentView(webView);

		// WebView内に，アプリが保持するHTMLを表示
		webView.loadUrl("file:///android_asset/html/about.html");
	}
}
