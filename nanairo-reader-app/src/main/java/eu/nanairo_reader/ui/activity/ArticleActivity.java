package eu.nanairo_reader.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebView;
import android.widget.TextView;
import eu.nanairo_reader.NanairoApplication;
import eu.nanairo_reader.R;
import eu.nanairo_reader.bean.Article;

public class ArticleActivity extends Activity {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.article);

		((NanairoApplication) getApplication()).inject(this);

		Intent intent = getIntent();
		Article article = (Article) intent.getSerializableExtra("article");

		WebView contentTextView = (WebView) findViewById(R.id.content);
		contentTextView.loadDataWithBaseURL("about:blank", article.getContent(), "text/html", "utf-8", null);

		TextView linkTextView = (TextView) findViewById(R.id.link);
		linkTextView.setText(article.getLink());
	}
}
