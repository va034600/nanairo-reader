package eu.nanairo_reader.data.dao;

import java.util.ArrayList;
import java.util.List;

import android.database.Cursor;
import eu.nanairo_reader.data.entity.SubscriptionEntity;

public class SubscriptionDaoImpl extends BaseDaoImpl implements SubscriptionDao {
	@Override
	public List<SubscriptionEntity> getList() {
		List<SubscriptionEntity> list = new ArrayList<SubscriptionEntity>();

		String[] columns = { "ID", "TITLE", "URL" };
		Cursor cursor = super.db.query("SUBSCRIPTION", columns, null, null, null, null, "ID");

		while (cursor.moveToNext()) {
			SubscriptionEntity subscription = new SubscriptionEntity();

			subscription.setId(cursor.getInt(cursor.getColumnIndex("ID")));
			subscription.setTitle(cursor.getString(cursor.getColumnIndex("TITLE")));
			subscription.setUrl(cursor.getString(cursor.getColumnIndex("URL")));

			list.add(subscription);
		}

		cursor.close();

		return list;
	}
}
