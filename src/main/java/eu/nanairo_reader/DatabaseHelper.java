package eu.nanairo_reader;

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
		String sql = "CREATE TABLE SUBSCRIPTION (";
		sql += "ID INTEGER PRIMARY KEY AUTOINCREMENT, ";
		sql += "TITLE TEXT, ";
		sql += "URL TEXT";
		sql += ");";
		db.execSQL(sql);

		// TODO 今だけ
		db.execSQL("INSERT INTO SUBSCRIPTION (TITLE, URL) VALUES('注目ま','http://matome.naver.jp/feed/hot');");
		db.execSQL("INSERT INTO SUBSCRIPTION (TITLE, URL) VALUES('TBN','http://tbn17.com/index.rdf');");
	}

	@Override
	public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
	}
}