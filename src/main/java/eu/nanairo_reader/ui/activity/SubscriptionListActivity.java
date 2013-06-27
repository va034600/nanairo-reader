package eu.nanairo_reader.ui.activity;

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
import eu.nanairo_reader.R;
import eu.nanairo_reader.bean.Subscription;
import eu.nanairo_reader.business.service.RssService;
import eu.nanairo_reader.ui.service.SampleService;

public class SubscriptionListActivity extends RoboListActivity {
	@Inject
	private RssService rssService;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.subscription_list);

		List<Subscription> list = this.rssService.getSubscriptionList();
		ListAdapter adapter = new ListAdapter(getApplicationContext(), list);

		setListAdapter(adapter);

		Button mButton = (Button) findViewById(R.id.android_updateButton);
		mButton.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				// サービスクラスを指定したインテントの作成
				Intent intent = new Intent(SubscriptionListActivity.this, SampleService.class);
				// サービスの起動
				startService(intent);
			}
		});
	}

	class ListAdapter extends ArrayAdapter<Subscription> {

		private LayoutInflater mInflater;
		private TextView mTitle;
		private TextView mCount;
		private Button mButton;

		public ListAdapter(Context context, List<Subscription> objects) {
			super(context, 0, objects);
			mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		}

		public View getView(final int position, View convertView, ViewGroup parent) {
			if (convertView == null) {
				convertView = mInflater.inflate(R.layout.subscription_row, null);
			}
			final Subscription subscription = this.getItem(position);
			if (subscription != null) {
				mTitle = (TextView) convertView.findViewById(R.id.nameText);
				mTitle.setText(subscription.getTitle());
				mCount = (TextView) convertView.findViewById(R.id.midokuCountText);
				mCount.setText(Integer.toString(subscription.getMidokuCount()));
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
