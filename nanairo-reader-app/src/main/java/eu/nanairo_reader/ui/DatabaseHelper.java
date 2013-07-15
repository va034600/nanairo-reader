package eu.nanairo_reader.ui;

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
		sql += "URL TEXT";
		sql += ");";
		db.execSQL(sql);

		// TODO 今だけ
		//db.execSQL("INSERT INTO SUBSCRIPTION (TITLE, URL) VALUES('okstate','http://www.okstate.com/headline-rss.xml');");
		db.execSQL("INSERT INTO SUBSCRIPTION (TITLE, URL) VALUES('TBN','http://tbn17.com/index.rdf');");
		//db.execSQL("INSERT INTO SUBSCRIPTION (TITLE, URL) VALUES('注目ま','http://matome.naver.jp/feed/hot');");
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
		sql += "CONTENT TEXT, ";
		sql += "LINK TEXT, ";
		sql += "PUBLISHED_DATE TEXT, ";
		sql += "MIDOKU INTEGER";
		sql += ");";
		db.execSQL(sql);
	}

	@Override
	public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
	}
}