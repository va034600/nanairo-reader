package eu.nanairo_reader.ui.activity;

import static eu.nanairo_reader.ui.constant.NanairoUiConstant.ARTICLE;
import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebView;
import android.widget.TextView;
import eu.nanairo_reader.R;
import eu.nanairo_reader.business.bean.Article;

public class ArticleActivity extends BaseActivity {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.article_detail);

		// パラメータ取得
		Intent intent = getIntent();
		Article article = (Article) intent.getSerializableExtra(ARTICLE);

		// 既読処理
		this.rssService.kidoku(article.getId());

		// コンテンツ
		WebView contentTextView = (WebView) findViewById(R.id.content);
		contentTextView.loadDataWithBaseURL("about:blank", article.getContent(), "text/html", "utf-8", null);

		// リンク
		TextView linkTextView = (TextView) findViewById(R.id.link);
		linkTextView.setText(article.getLink());
	}
}
