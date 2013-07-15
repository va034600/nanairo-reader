package eu.nanairo_reader.ui.component;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import eu.nanairo_reader.R;
import eu.nanairo_reader.business.bean.Article;

public class ArticleArrayAdapter extends ArrayAdapter<Article> {
	private LayoutInflater mInflater;

	public ArticleArrayAdapter(Context context, List<Article> objects) {
		super(context, 0, objects);
		mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	public View getView(final int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.article_list_row, null);
		}

		Article article = this.getItem(position);
		if (article != null) {
			// タイトル
			TextView mTitle = (TextView) convertView.findViewById(R.id.nameText);
			mTitle.setText(article.getTitle());

			// 公開日時
			TextView mPublishedDate = (TextView) convertView.findViewById(R.id.publishedDateText);
			mPublishedDate.setText(article.getPublishedDate());
		}
		return convertView;
	}
}
