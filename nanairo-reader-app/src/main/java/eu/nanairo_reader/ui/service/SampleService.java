package eu.nanairo_reader.ui.service;

import javax.inject.Inject;

import roboguice.service.RoboService;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.widget.Toast;
import eu.nanairo_reader.business.service.RssService;

// Serviceクラスを拡張したクラスを作成
public class SampleService extends RoboService {
	public static final String ACTION = "SampleService";

	@Inject
	private RssService rssService;

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
		// 開始時にトーストを表示
		Toast.makeText(this, "サービスを開始しました！", Toast.LENGTH_SHORT).show();
		Thread t = new Thread() {
			public void run() {
				SampleService.this.rssService.storeItems();

				Intent intent = new Intent(ACTION);
				intent.putExtra("count", 55);
				sendBroadcast(intent);
			}
		};
		t.start();
	}
}
