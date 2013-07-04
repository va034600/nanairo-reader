package eu.nanairo_reader.ui.service;

import javax.inject.Inject;

import roboguice.service.RoboService;
import android.content.Intent;
import android.os.IBinder;
import android.widget.Toast;
import eu.nanairo_reader.business.service.RssService;

// Serviceクラスを拡張したクラスを作成
public class SampleService extends RoboService {
	@Inject
	private RssService rssService;

	@Override
	public IBinder onBind(Intent intent) {
		return null;
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
		Toast.makeText(this, "サービスを終了しました！", Toast.LENGTH_SHORT).show();
	}
}