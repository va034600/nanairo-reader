package eu.nanairo_reader;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;

public class NanairoApplication extends Application {
	// データベースヘルパーの作成
	private DatabaseHelper helper;
	// データベースの宣言
	public static SQLiteDatabase db;

	@Override
	public void onCreate() {
		super.onCreate();
		helper = new DatabaseHelper(this);

		// データベースをオープン
		db = helper.getWritableDatabase();
	}

	@Override
	public void onTerminate() {
		super.onTerminate();
		helper.close();
	}
}
