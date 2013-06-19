package eu.nanairo_reader.dao;

import android.database.sqlite.SQLiteDatabase;
import eu.nanairo_reader.NanairoApplication;

public class BaseDaoImpl {
	/***/
	protected SQLiteDatabase db;

	public BaseDaoImpl() {
		// TODO
		this.db = NanairoApplication.db;
	}
}
