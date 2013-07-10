package eu.nanairo_reader.ui.activity;

import static eu.nanairo_reader.ui.constant.NanairoUiConstant.ARTICLE;

import javax.inject.Inject;

import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebView;
import android.widget.TextView;
import eu.nanairo_reader.NanairoApplication;
import eu.nanairo_reader.R;
import eu.nanairo_reader.bean.Article;
import eu.nanairo_reader.business.service.RssService;

public class ArticleActivity extends BaseActivity {
	@Inject
	RssService rssService;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.article_detail);

		((NanairoApplication) getApplication()).inject(this.rssService);

		Intent intent = getIntent();
		Article article = (Article) intent.getSerializableExtra(ARTICLE);

		this.rssService.kidoku(article.getId());

		WebView contentTextView = (WebView) findViewById(R.id.content);
		contentTextView.loadDataWithBaseURL("about:blank", article.getContent(), "text/html", "utf-8", null);

		TextView linkTextView = (TextView) findViewById(R.id.link);
		linkTextView.setText(article.getLink());
	}
}
