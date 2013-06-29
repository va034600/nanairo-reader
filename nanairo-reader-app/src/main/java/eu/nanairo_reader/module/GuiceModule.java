package eu.nanairo_reader.module;

import android.database.sqlite.SQLiteDatabase;

import com.google.inject.AbstractModule;

import eu.nanairo_reader.NanairoApplication;
import eu.nanairo_reader.business.service.RssService;
import eu.nanairo_reader.business.service.RssServiceImpl;
import eu.nanairo_reader.data.dao.ItemDao;
import eu.nanairo_reader.data.dao.ItemDaoImpl;
import eu.nanairo_reader.data.dao.ItemDaoMock;
import eu.nanairo_reader.data.dao.SubscriptionDao;
import eu.nanairo_reader.data.dao.SubscriptionDaoImpl;
import eu.nanairo_reader.data.dao.SubscriptionDaoMock;

public class GuiceModule extends AbstractModule {
	@Override
	protected void configure() {
		// service
		bind(RssService.class).to(RssServiceImpl.class);

		// dao
		// TODO
		boolean flag = true;
		if (flag) {
			// TODO
			SQLiteDatabase db = NanairoApplication.db;

			SubscriptionDaoImpl subscriptionDaoImpl = new SubscriptionDaoImpl();
			subscriptionDaoImpl.setDb(db);
			bind(SubscriptionDao.class).toInstance(subscriptionDaoImpl);

			ItemDaoImpl itemDaoImpl = new ItemDaoImpl();
			itemDaoImpl.setDb(db);
			bind(ItemDao.class).toInstance(itemDaoImpl);
		} else {
			bind(SubscriptionDao.class).to(SubscriptionDaoMock.class);
			bind(ItemDao.class).to(ItemDaoMock.class);
		}

	}
}
