package eu.nanairo_reader;

import java.util.List;

import javax.inject.Inject;

import roboguice.activity.RoboListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import eu.nanairo_reader.data.entity.SubscriptionEntity;
import eu.nanairo_reader.service.RssService;

public class SubscriptionListActivity extends RoboListActivity {
	@Inject
	private RssService rssService;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.subscription_list);

		List<SubscriptionEntity> list = this.rssService.getSubscriptionList();
		ListAdapter adapter = new ListAdapter(getApplicationContext(), list);

		setListAdapter(adapter);
	}

	class ListAdapter extends ArrayAdapter<SubscriptionEntity> {

		private LayoutInflater mInflater;
		private TextView mTitle;
		private Button mButton;

		public ListAdapter(Context context, List<SubscriptionEntity> objects) {
			super(context, 0, objects);
			mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		}

		public View getView(final int position, View convertView, ViewGroup parent) {
			if (convertView == null) {
				convertView = mInflater.inflate(R.layout.subscription_row, null);
			}
			final SubscriptionEntity subscription = this.getItem(position);
			if (subscription != null) {
				mTitle = (TextView) convertView.findViewById(R.id.nameText);
				mTitle.setText(subscription.getTitle());
				mButton = (Button) convertView.findViewById(R.id.detailButton);
				mButton.setOnClickListener(new OnClickListener() {
					public void onClick(View v) {
						// インテントのインスタンス生成
						Intent intent = new Intent(SubscriptionListActivity.this, ItemListActivity.class);
						intent.putExtra("subscription", subscription);
						// 次画面のアクティビティ起動
						startActivity(intent);
					}
				});
			}
			return convertView;
		}
	}
}
