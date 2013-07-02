package eu.nanairo_reader.module;

import android.database.sqlite.SQLiteDatabase;

import com.google.inject.AbstractModule;

import eu.nanairo_reader.NanairoApplication;
import eu.nanairo_reader.business.service.RssParsing2ServiceImpl;
import eu.nanairo_reader.business.service.RssParsingService;
import eu.nanairo_reader.business.service.RssService;
import eu.nanairo_reader.business.service.RssServiceImpl;
import eu.nanairo_reader.data.dao.ItemDao;
import eu.nanairo_reader.data.dao.ItemDaoImpl;
import eu.nanairo_reader.data.dao.ItemDaoMock;
import eu.nanairo_reader.data.dao.SubscriptionDao;
import eu.nanairo_reader.data.dao.SubscriptionDaoImpl;
import eu.nanairo_reader.data.dao.SubscriptionDaoMock;
import eu.nanairo_reader.data.dao.SubscriptionItemDao;
import eu.nanairo_reader.data.dao.SubscriptionItemDaoImpl;
import eu.nanairo_reader.data.dao.SubscriptionItemDaoMock;

public class GuiceModule extends AbstractModule {
	@Override
	protected void configure() {
		// service
		bind(RssService.class).to(RssServiceImpl.class);
		//bind(RssParsingService.class).to(RssParsingServiceImpl.class);
		bind(RssParsingService.class).to(RssParsing2ServiceImpl.class);

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

			SubscriptionItemDaoImpl subscriptionItemDaoImpl = new SubscriptionItemDaoImpl();
			subscriptionItemDaoImpl.setDb(db);
			bind(SubscriptionItemDao.class).toInstance(subscriptionItemDaoImpl);
		} else {
			bind(SubscriptionDao.class).to(SubscriptionDaoMock.class);
			bind(ItemDao.class).to(ItemDaoMock.class);
			bind(SubscriptionItemDao.class).to(SubscriptionItemDaoMock.class);
		}
	}
}
