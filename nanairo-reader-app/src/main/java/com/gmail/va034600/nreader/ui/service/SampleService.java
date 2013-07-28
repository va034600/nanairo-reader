package com.gmail.va034600.nreader.ui.service;

import javax.inject.Inject;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.widget.Toast;

import com.gmail.va034600.nreader.R;
import com.gmail.va034600.nreader.business.domain.service.RssService;
import com.gmail.va034600.nreader.ui.NanairoApplication;
import com.gmail.va034600.nreader.ui.activity.SubscriptionListActivity;

// Serviceクラスを拡張したクラスを作成
public class SampleService extends Service {
	/** アクション名 */
	public static final String SAMPLE_SERVICE_ACTION = "SampleServiceAction";

	/** 未読数 */
	public static final String MIDOKU_COUNT = "MIDOKU_COUNT";

	@Inject
	RssService rssService;

	@Override
	public IBinder onBind(Intent intent) {
		return new MySampleBinder();
	}

	// Binder内部クラス
	public class MySampleBinder extends Binder {
		public SampleService getService() {
			return SampleService.this;
		}
	}

	// 開始時 onStart メソッド
	@Override
	public void onStart(Intent intent, int startId) {
		super.onStart(intent, startId);

		((NanairoApplication) getApplication()).inject(this);
		((NanairoApplication) getApplication()).inject(this.rssService);

		// 開始時にトーストを表示
		Toast.makeText(this, "記事取得を開始しました！", Toast.LENGTH_SHORT).show();
		Thread t = new Thread() {
			public void run() {
				int midokuCount = SampleService.this.rssService.storeArticles();

				Intent intent = new Intent(SAMPLE_SERVICE_ACTION);
				intent.putExtra(MIDOKU_COUNT, midokuCount);
				sendBroadcast(intent);

				// 自分自身を止めてonDestroy()メソッドへ
				stopSelf();
			}
		};
		t.start();
	}

	// 終了時 onDestroyメソッド
	@Override
	public void onDestroy() {
		super.onDestroy();

		NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
		Notification notification = new Notification();
		notification.icon = R.drawable.icon;
		notification.tickerText = "記事取得終了しました。";
		// ノーティフィケーション一覧画面での表示内容、クリック時の動作を設定
		notification.setLatestEventInfo(getApplicationContext(), "七色リーダー", "記事取得が終了しました", contentIntent());
		// Notificationを発行して表示する
		notificationManager.notify(1, notification);
	}

	private PendingIntent contentIntent() {
		// 起動するアクティビティの設定
		Intent intent = new Intent(getApplicationContext(), SubscriptionListActivity.class);
		return PendingIntent.getActivity(this, 0, intent, 0);
	}
}
