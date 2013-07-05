package eu.nanairo_reader;

import java.util.Arrays;
import java.util.List;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;
import dagger.ObjectGraph;
import eu.nanairo_reader.module.AndroidModule;

public class NanairoApplication extends Application {
	// データベースヘルパーの作成
	private DatabaseHelper helper;
	// データベースの宣言
	private SQLiteDatabase db;

	private ObjectGraph applicationGraph;

	@Override
	public void onCreate() {
		super.onCreate();
		helper = new DatabaseHelper(this);

		// データベースをオープン
		db = helper.getWritableDatabase();

		applicationGraph = ObjectGraph.create(getModules().toArray());
	}

	protected List<Object> getModules() {
		return Arrays.<Object> asList(new AndroidModule(this));
	}

	ObjectGraph getApplicationGraph() {
		return applicationGraph;
	}

	@Override
	public void onTerminate() {
		super.onTerminate();
		helper.close();
	}

	public void inject(Object object) {
		applicationGraph.inject(object);
	}

	public SQLiteDatabase getDb() {
		return db;
	}
}
