package eu.nanairo_reader;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
	public DatabaseHelper(Context context) {
		// ストレージ(ローカルファイル)にDBを作成
		super(context, "nanairo.db", null, 1);
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
	}

	@Override
	public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
	}
}