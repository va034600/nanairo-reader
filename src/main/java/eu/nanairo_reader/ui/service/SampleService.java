package eu.nanairo_reader.ui.service;

import roboguice.service.RoboService;
import android.content.Intent;
import android.os.IBinder;
import android.widget.Toast;

// Serviceクラスを拡張したクラスを作成
public class SampleService extends RoboService {
	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	// 開始時 onStart メソッド
	@Override
	public void onStart(Intent intent, int startId) {
		super.onStart(intent, startId);
		// 開始時にトーストを表示
		Toast.makeText(this, "サービスを開始しました！", Toast.LENGTH_LONG).show();
		Thread t = new Thread() {
			public void run() {
				try {
					// 10秒間スレッドをスリープ
					Thread.sleep(10 * 1000);
					// 自分自身を止めてonDestroy()メソッドへ
					stopSelf();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		};
		t.start();
	}

	// 終了時 onDestroyメソッド
	@Override
	public void onDestroy() {
		super.onDestroy();
		Toast.makeText(this, "サービスを終了しました！", Toast.LENGTH_LONG).show();
	}
}