package eu.nanairo_reader.ui.activity;

import static eu.nanairo_reader.ui.constant.NanairoUiConstant.SUBSCRIPTION;
import static eu.nanairo_reader.ui.service.SampleService.SAMPLE_SERVICE_ACTION;
import static eu.nanairo_reader.ui.service.SampleService.MIDOKU_COUNT;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

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
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import eu.nanairo_reader.NanairoApplication;
import eu.nanairo_reader.R;
import eu.nanairo_reader.bean.Subscription;
import eu.nanairo_reader.business.service.RssService;
import eu.nanairo_reader.ui.service.SampleService;

public class SubscriptionListActivity extends BaseActivity {
	@Inject
	RssService rssService;

	private MyServiceReceiver receiver = new MyServiceReceiver();

	private List<Subscription> subscriptionList = new ArrayList<Subscription>();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.subscription_list);

		((NanairoApplication) getApplication()).inject(this.rssService);

		// レシーバー登録
		IntentFilter filter = new IntentFilter(SAMPLE_SERVICE_ACTION);
		registerReceiver(receiver, filter);

		// ListView
		SubscriptionArrayAdapter listAdapter = new SubscriptionArrayAdapter(getApplicationContext(), this.subscriptionList);
		ListView listView = (ListView) findViewById(R.id.listView);
		listView.setAdapter(listAdapter);

		//購読一覧を構築
		rebuildSubscriptionList();

		// 更新ボタン
		Button updateButton = (Button) findViewById(R.id.updateButton);
		updateButton.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				// サービスの起動
				Intent intent = new Intent(SubscriptionListActivity.this, SampleService.class);
				startService(intent);
			}
		});

		// 登録ボタン
		Button entryButton = (Button) findViewById(R.id.entryButton);
		entryButton.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(SubscriptionListActivity.this, SubscriptionEntryActivity.class);
				startActivity(intent);
			}
		});
	}

	@Override
	protected void onResume() {
		super.onResume();
	}

	@Override
	protected void onDestroy() {
		// レシーバー解除
		unregisterReceiver(receiver);
		super.onDestroy();
	}

	class SubscriptionArrayAdapter extends ArrayAdapter<Subscription> {
		private LayoutInflater mInflater;

		public SubscriptionArrayAdapter(Context context, List<Subscription> objects) {
			super(context, 0, objects);
			mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		}

		public View getView(final int position, View convertView, ViewGroup parent) {
			if (convertView == null) {
				convertView = mInflater.inflate(R.layout.subscription_list_row, null);
			}

			final Subscription subscription = this.getItem(position);
			if (subscription != null) {
				// タイトル
				TextView mTitle = (TextView) convertView.findViewById(R.id.nameText);
				mTitle.setText(subscription.getTitle());

				// 未読数
				TextView mCount = (TextView) convertView.findViewById(R.id.midokuCountText);
				mCount.setText(Integer.toString(subscription.getMidokuCount()));

				// 詳細ボタン
				Button mButton = (Button) convertView.findViewById(R.id.detailButton);
				mButton.setOnClickListener(new OnClickListener() {
					public void onClick(View v) {
						// インテントのインスタンス生成
						Intent intent = new Intent(SubscriptionListActivity.this, ArticleListActivity.class);
						intent.putExtra(SUBSCRIPTION, subscription);
						startActivity(intent);
					}
				});
			}
			return convertView;
		}
	}

	private void rebuildSubscriptionList() {
		// TODO 未読数を更新する。要リファクタリング
		List<Subscription> subscriptionList2 = SubscriptionListActivity.this.rssService.getSubscriptionList();
		this.subscriptionList.clear();
		this.subscriptionList.addAll(subscriptionList2);
		ListView listView = (ListView) findViewById(R.id.listView);
		((SubscriptionArrayAdapter) listView.getAdapter()).notifyDataSetChanged();
	}

	// Receiverクラス
	class MyServiceReceiver extends BroadcastReceiver {
		@Override
		public void onReceive(Context context, Intent intent) {
			int count = intent.getIntExtra(MIDOKU_COUNT, 0);
			Toast.makeText(SubscriptionListActivity.this, "更新件数:" + count, Toast.LENGTH_SHORT).show();

			//購読一覧を構築
			rebuildSubscriptionList();
		}
	}
}
