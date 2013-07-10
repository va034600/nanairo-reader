package eu.nanairo_reader.ui.activity;

import static eu.nanairo_reader.ui.constant.NanairoUiConstant.ARTICLE;
import static eu.nanairo_reader.ui.constant.NanairoUiConstant.SUBSCRIPTION;

import java.util.List;

import javax.inject.Inject;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import eu.nanairo_reader.NanairoApplication;
import eu.nanairo_reader.R;
import eu.nanairo_reader.bean.Article;
import eu.nanairo_reader.bean.Subscription;
import eu.nanairo_reader.business.service.RssService;

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
		List<Article> list = this.rssService.getArticleList(subscription.getId());
		ListAdapter listAdapter = new ListAdapter(getApplicationContext(), list);
		ListView listView = (ListView) findViewById(R.id.listView);
		listView.setAdapter(listAdapter);
	}

	class ListAdapter extends ArrayAdapter<Article> {
		private LayoutInflater mInflater;

		public ListAdapter(Context context, List<Article> objects) {
			super(context, 0, objects);
			mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		}

		public View getView(final int position, View convertView, ViewGroup parent) {
			if (convertView == null) {
				convertView = mInflater.inflate(R.layout.subscription_list_row, null);
			}
			final Article article = this.getItem(position);
			if (article != null) {
				// タイトル
				TextView mTitle = (TextView) convertView.findViewById(R.id.nameText);
				mTitle.setText(article.getTitle());

				// 詳細ボタン
				Button mButton = (Button) convertView.findViewById(R.id.detailButton);
				mButton.setOnClickListener(new OnClickListener() {
					public void onClick(View v) {
						Intent intent = new Intent(ArticleListActivity.this, ArticleActivity.class);
						intent.putExtra(ARTICLE, article);
						startActivity(intent);
					}
				});
			}
			return convertView;
		}
	}
}
