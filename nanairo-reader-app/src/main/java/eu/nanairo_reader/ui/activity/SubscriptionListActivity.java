package eu.nanairo_reader.ui.activity;

import java.util.List;

import javax.inject.Inject;

import roboguice.activity.RoboListActivity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import eu.nanairo_reader.R;
import eu.nanairo_reader.bean.Subscription;
import eu.nanairo_reader.business.service.RssService;
import eu.nanairo_reader.ui.service.SampleService;

public class SubscriptionListActivity extends RoboListActivity {
	private MyServiceReceiver receiver = new MyServiceReceiver();

	@Inject
	private RssService rssService;

	List<Subscription> list;
	ListAdapter adapter;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.subscription_list);

		list = this.rssService.getSubscriptionList();
		adapter = new ListAdapter(getApplicationContext(), list);
		setListAdapter(adapter);

		Button mButton = (Button) findViewById(R.id.android_updateButton);
		mButton.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				// サービスの起動
				Intent intent = new Intent(SubscriptionListActivity.this, SampleService.class);
				startService(intent);

				IntentFilter filter = new IntentFilter(SampleService.ACTION);
				registerReceiver(receiver, filter);
			}
		});
	}

	@Override
	protected void onDestroy() {
		// サービス終了
		unregisterReceiver(receiver); // レシーバー解除
		super.onDestroy();
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

	// Receiverクラス
	class MyServiceReceiver extends BroadcastReceiver {
		@Override
		public void onReceive(Context context, Intent intent) {
			int count = intent.getIntExtra("count", 0);
			Toast.makeText(SubscriptionListActivity.this, "更新件数:" + count, Toast.LENGTH_SHORT).show();
			list.get(0).setMidokuCount(count);
			//ListViewを更新する。
			adapter.notifyDataSetChanged();
		}
	}
}
