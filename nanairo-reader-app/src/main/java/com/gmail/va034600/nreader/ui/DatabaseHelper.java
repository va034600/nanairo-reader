package com.gmail.va034600.nreader.ui;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
	private static final String DATABASE_NAME = "nanairo.db";
	private static final int DATABASE_VERSION = 1;

	public DatabaseHelper(Context context) {
		// ストレージ(ローカルファイル)にDBを作成
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// テーブル作成処理
		createSubscription(db);
		createSubscriptionArticle(db);
		createArticle(db);
	}

	private void createSubscription(SQLiteDatabase db) {
		String sql = "CREATE TABLE SUBSCRIPTION (";
		sql += "ID INTEGER PRIMARY KEY AUTOINCREMENT, ";
		sql += "TITLE TEXT, ";
		sql += "PUBLISHED_DATE TEXT, ";
		sql += "URL TEXT";
		sql += ");";
		db.execSQL(sql);

		// TODO 今だけ
		//db.execSQL("INSERT INTO SUBSCRIPTION (TITLE, PUBLISHED_DATE, URL) VALUES('okstate',strftime('%Y/%m/%d %H:%M:%S','now', 'localtime'),'http://www.okstate.com/headline-rss.xml');");
		//db.execSQL("INSERT INTO SUBSCRIPTION (TITLE, PUBLISHED_DATE, URL) VALUES('TBN',strftime('%Y/%m/%d %H:%M:%S','now', 'localtime'),'http://tbn17.com/index.rdf');");
		//db.execSQL("INSERT INTO SUBSCRIPTION (TITLE, PUBLISHED_DATE, URL) VALUES('注目ま',strftime('%Y/%m/%d %H:%M:%S','now', 'localtime'),'http://matome.naver.jp/feed/hot');");
		//db.execSQL("INSERT INTO SUBSCRIPTION (TITLE, PUBLISHED_DATE, URL) VALUES('Top Stories - Google News',strftime('%Y/%m/%d %H:%M:%S','now', 'localtime'),'http://news.google.com/?output=rss');");
	}

	private void createSubscriptionArticle(SQLiteDatabase db) {
		String sql = "CREATE TABLE SUBSCRIPTION_ARTICLE (";
		sql += "SUBSCRIPTION_ID INTEGER, ";
		sql += "ARTICLE_ID INTEGER, ";
		sql += "PRIMARY KEY(SUBSCRIPTION_ID, ARTICLE_ID));";
		db.execSQL(sql);
	}

	private void createArticle(SQLiteDatabase db) {
		String sql = "CREATE TABLE ARTICLE (";
		sql += "ID INTEGER PRIMARY KEY AUTOINCREMENT, ";
		sql += "TITLE TEXT, ";
		sql += "PUBLISHED_DATE TEXT, ";
		sql += "CONTENT TEXT, ";
		sql += "LINK TEXT, ";
		sql += "MIDOKU INTEGER";
		sql += ");";
		db.execSQL(sql);
	}

	@Override
	public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
	}
}