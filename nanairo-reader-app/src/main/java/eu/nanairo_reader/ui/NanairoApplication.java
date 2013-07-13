package eu.nanairo_reader.ui;

import java.util.Arrays;
import java.util.List;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;
import dagger.ObjectGraph;
import eu.nanairo_reader.ui.module.DaggerModule;

public class NanairoApplication extends Application {
	// データベースヘルパーの作成
	private DatabaseHelper helper;
	// データベースの宣言
	private SQLiteDatabase db;

	private ObjectGraph objectGraph;

	@Override
	public void onCreate() {
		super.onCreate();
		this.helper = new DatabaseHelper(this);

		// データベースをオープン
		db = this.helper.getWritableDatabase();

		this.objectGraph = ObjectGraph.create(getModules().toArray());
	}

	protected List<Object> getModules() {
		return Arrays.<Object> asList(new DaggerModule(this));
	}

	@Override
	public void onTerminate() {
		super.onTerminate();
		this.helper.close();
	}

	public void inject(Object object) {
		this.objectGraph.inject(object);
	}

	public SQLiteDatabase getDb() {
		return db;
	}
}