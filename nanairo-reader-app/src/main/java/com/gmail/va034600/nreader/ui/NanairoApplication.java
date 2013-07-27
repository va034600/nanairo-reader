package com.gmail.va034600.nreader.ui;

import java.util.Arrays;
import java.util.List;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;

import com.gmail.va034600.nreader.business.NanairoContext;
import com.gmail.va034600.nreader.ui.module.DaggerModule;

import dagger.ObjectGraph;

public class NanairoApplication extends Application implements NanairoContext{
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
		//TODO ThreadLocalにするか、同期処理いれる？
		return db;
	}

	@Override
	public void beginTransaction() {
		this.db.beginTransaction();
	}

	@Override
	public void setTransactionSuccessful() {
		this.db.setTransactionSuccessful();
	}

	@Override
	public void endTransaction() {
		this.db.endTransaction();
	}
}
