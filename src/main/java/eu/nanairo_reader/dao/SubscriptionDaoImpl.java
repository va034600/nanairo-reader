package eu.nanairo_reader.dao;

import java.util.ArrayList;
import java.util.List;

import android.database.Cursor;
import eu.nanairo_reader.SubscriptionListActivity;
import eu.nanairo_reader.bean.Subscription;

public class SubscriptionDaoImpl implements SubscriptionDao {
	@Override
	public List<Subscription> getList() {
		List<Subscription> list = new ArrayList<Subscription>();

		//TODO dao base クラス作成
		String[] columns = { "ID", "TITLE", "URL" };
		Cursor cursor = SubscriptionListActivity.db.query("SUBSCRIPTION", columns, null, null, null, null, "ID");

		while (cursor.moveToNext()) {
			Subscription subscription = new Subscription();

			subscription.setTitle(cursor.getString(cursor.getColumnIndex("TITLE")));
			subscription.setUrl(cursor.getString(cursor.getColumnIndex("URL")));

			list.add(subscription);
		}

		cursor.close();

		return list;
	}
}
