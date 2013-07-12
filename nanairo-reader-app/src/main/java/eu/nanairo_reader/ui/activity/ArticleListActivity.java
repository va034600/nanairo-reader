package eu.nanairo_reader.ui.activity;

import static eu.nanairo_reader.ui.constant.NanairoUiConstant.ARTICLE;
import static eu.nanairo_reader.ui.constant.NanairoUiConstant.SUBSCRIPTION;

import java.util.List;

import javax.inject.Inject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;
import eu.nanairo_reader.R;
import eu.nanairo_reader.business.bean.Article;
import eu.nanairo_reader.business.bean.Subscription;
import eu.nanairo_reader.business.service.RssService;
import eu.nanairo_reader.ui.NanairoApplication;
import eu.nanairo_reader.ui.component.ArticleArrayAdapter;

public class ArticleListActivity extends BaseActivity {
	@Inject
	RssService rssService;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.article_list);

		((NanairoApplication) getApplication()).inject(this.rssService);

		// パラメータ取得
		Intent intent = getIntent();
		Subscription subscription = (Subscription) intent.getSerializableExtra(SUBSCRIPTION);

		// ListView
		ListView listView = (ListView) findViewById(R.id.listView);

		// Adapterの設定
		List<Article> list = this.rssService.getArticleList(subscription.getId());
		ListAdapter listAdapter = new ArticleArrayAdapter(getApplicationContext(), list);
		listView.setAdapter(listAdapter);

		// ListViewクリック
		listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				ListView listView = (ListView) findViewById(R.id.listView);
				Article article = (Article) listView.getItemAtPosition(position);

				// インテントのインスタンス生成
				Intent intent = new Intent(ArticleListActivity.this, ArticleActivity.class);
				intent.putExtra(ARTICLE, article);
				startActivity(intent);
			}
		});

		// 全既読ボタン
		Button updateButton = (Button) findViewById(R.id.allCheckButton);
		updateButton.setOnClickListener(new UpdateButtonOnClickListener(subscription.getId()));
	}

	class UpdateButtonOnClickListener implements View.OnClickListener {
		long subscriptionId;

		UpdateButtonOnClickListener(long subscriptionId) {
			this.subscriptionId = subscriptionId;
		}

		public void onClick(View v) {
			Toast.makeText(ArticleListActivity.this, "subscriptionId:" + this.subscriptionId, Toast.LENGTH_SHORT).show();
			//TODO 全既読処理
		}
	};
}
