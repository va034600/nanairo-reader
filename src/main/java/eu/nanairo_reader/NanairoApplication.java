package eu.nanairo_reader;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;

public class NanairoApplication extends Application {
	private static NanairoApplication instance;
	// データベースヘルパーの作成
	private static DatabaseHelper helper;
	// データベースの宣言
	public static SQLiteDatabase db;

	public NanairoApplication() {
	}

	@Override
	public void onCreate() {
		super.onCreate();
		instance = this;
		helper = new DatabaseHelper(instance);

		// データベースをオープン
		db = helper.getWritableDatabase();
	}

	@Override
	public void onTerminate() {
		super.onTerminate();
		helper.close();
	}

	public static NanairoApplication getInstance() {
		return instance;
	}

}
