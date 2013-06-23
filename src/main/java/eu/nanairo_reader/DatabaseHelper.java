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
		createSubscription(db);
		createItem(db);
	}

	private void createSubscription(SQLiteDatabase db) {
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

	private void createItem(SQLiteDatabase db) {
		String sql = "CREATE TABLE ITEM (";
		sql += "ID INTEGER PRIMARY KEY AUTOINCREMENT, ";
		sql += "TITLE TEXT, ";
		sql += "CONTENT TEXT, ";
		sql += "LINK, ";
		sql += "MIDOKU";
		sql += ");";
		db.execSQL(sql);

		// TODO 今だけ
		db.execSQL("INSERT INTO ITEM (TITLE, CONTENT, LINK, MIDOKU) VALUES('6月16日(日)', 'bbb<br>aa<br>aa<br>aa<br>aa<br>aa<br>aa<br>aa<br>aa<br>aa<br>aa<br>aa<br>aa<br>aa<br>aa<br>aa<br>aa<br>aa<br>aaaa<br>aa<br>aa<br>aa<br>aa<br>aa<br>aa<br>aa<br>aa<br>aa<br>aa', 'http://tbn17.com/archives/1764915.html', 1);");
		db.execSQL("INSERT INTO ITEM (TITLE, CONTENT, LINK, MIDOKU) VALUES('6月17日(月)', 'bbb<br>aa', 'http://tbn17.com/archives/1764915.html', 0);");
	}

	@Override
	public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
	}
}