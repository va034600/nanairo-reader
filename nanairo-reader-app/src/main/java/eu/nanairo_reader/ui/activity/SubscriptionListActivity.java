package eu.nanairo_reader.ui.activity;

import static eu.nanairo_reader.ui.constant.NanairoUiConstant.SUBSCRIPTION;
import static eu.nanairo_reader.ui.service.SampleService.MIDOKU_COUNT;
import static eu.nanairo_reader.ui.service.SampleService.SAMPLE_SERVICE_ACTION;

import java.util.List;

import javax.inject.Inject;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import eu.nanairo_reader.R;
import eu.nanairo_reader.business.bean.Subscription;
import eu.nanairo_reader.business.service.RssService;
import eu.nanairo_reader.ui.NanairoApplication;
import eu.nanairo_reader.ui.component.SubscriptionArrayAdapter;
import eu.nanairo_reader.ui.service.SampleService;

public class SubscriptionListActivity extends BaseActivity {
	/***/
	private final static int CONTEXT_ITEM_SUBSCRIPTION_DELETE = 1000;

	/***/
	private final static int CONTEXT_ITEM_ALL_KIDOKU = 1001;

	@Inject
	RssService rssService;

	private MyServiceReceiver receiver = new MyServiceReceiver();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.subscription_list);

		((NanairoApplication) getApplication()).inject(this.rssService);

		// レシーバー登録
		IntentFilter filter = new IntentFilter(SAMPLE_SERVICE_ACTION);
		registerReceiver(this.receiver, filter);

		// ListView
		ListView listView = (ListView) findViewById(R.id.listView);
		List<Subscription> subscriptionList = rssService.loadSubscriptionList();
		SubscriptionArrayAdapter listAdapter = new SubscriptionArrayAdapter(getApplicationContext(), subscriptionList);
		listView.setAdapter(listAdapter);

		// コンテキストメニュー登録
		registerForContextMenu(listView);

		// ListViewクリック
		listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				ListView listView = (ListView) findViewById(R.id.listView);
				Subscription subscription = (Subscription) listView.getItemAtPosition(position);

				// インテントのインスタンス生成
				Intent intent = new Intent(SubscriptionListActivity.this, ArticleListActivity.class);
				intent.putExtra(SUBSCRIPTION, subscription);
				startActivity(intent);
			}
		});

		// 更新ボタン
		Button updateButton = (Button) findViewById(R.id.updateButton);
		updateButton.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
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
	public void onCreateContextMenu(ContextMenu menu, View view, ContextMenuInfo info) {
		super.onCreateContextMenu(menu, view, info);
		AdapterContextMenuInfo adapterContextMenuInfo = (AdapterContextMenuInfo) info;
		ListView listView = (ListView) view;
		Subscription subscription = (Subscription) listView.getItemAtPosition(adapterContextMenuInfo.position);

		// コンテキストメニューの作成
		menu.setHeaderTitle("メニュー:" + subscription.getTitle());
		// TODO M2で。
		// menu.add(0, CONTEXT_ITEM_ALL_KIDOKU, 0, "全て既読にする");
		menu.add(0, CONTEXT_ITEM_SUBSCRIPTION_DELETE, 0, "購読を削除");
	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {
		AdapterContextMenuInfo adapterInfo = (AdapterContextMenuInfo) item.getMenuInfo();
		ListView listView = (ListView) findViewById(R.id.listView);
		Subscription subscription = (Subscription) listView.getItemAtPosition(adapterInfo.position);

		switch (item.getItemId()) {
		case CONTEXT_ITEM_ALL_KIDOKU:
			// TODO 全既読処理
			break;
		case CONTEXT_ITEM_SUBSCRIPTION_DELETE:
			// 購読削除
			this.rssService.deleteSubscription(subscription);
			((SubscriptionArrayAdapter) listView.getAdapter()).notifyDataSetChanged();
			Toast.makeText(SubscriptionListActivity.this, "削除しました。", Toast.LENGTH_SHORT).show();
			break;
		}

		return true;
	}

	@Override
	protected void onResume() {
		super.onResume();

		// 購読の再表示
		ListView listView = (ListView) findViewById(R.id.listView);
		((SubscriptionArrayAdapter) listView.getAdapter()).notifyDataSetChanged();
	}

	@Override
	protected void onDestroy() {
		// レシーバー解除
		unregisterReceiver(receiver);
		super.onDestroy();
	}

	// Receiverクラス
	class MyServiceReceiver extends BroadcastReceiver {
		@Override
		public void onReceive(Context context, Intent intent) {
			ListView listView = (ListView) findViewById(R.id.listView);
			((SubscriptionArrayAdapter) listView.getAdapter()).notifyDataSetChanged();

			int count = intent.getIntExtra(MIDOKU_COUNT, 0);
			Toast.makeText(SubscriptionListActivity.this, "更新件数:" + count, Toast.LENGTH_SHORT).show();
		}
	}
}
