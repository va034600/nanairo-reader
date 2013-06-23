package eu.nanairo_reader.module;

import com.google.inject.AbstractModule;

import eu.nanairo_reader.data.dao.ItemDao;
import eu.nanairo_reader.data.dao.ItemDaoImpl;
import eu.nanairo_reader.data.dao.ItemDaoMock;
import eu.nanairo_reader.data.dao.SubscriptionDao;
import eu.nanairo_reader.data.dao.SubscriptionDaoImpl;
import eu.nanairo_reader.data.dao.SubscriptionDaoMock;
import eu.nanairo_reader.service.RssService;
import eu.nanairo_reader.service.RssServiceImpl;

public class GuiceModule extends AbstractModule {
	@Override
	protected void configure() {
		// service
		bind(RssService.class).to(RssServiceImpl.class);

		// dao
		// TODO
		boolean flag = true;
		if (flag) {
			bind(SubscriptionDao.class).to(SubscriptionDaoImpl.class);
			bind(ItemDao.class).to(ItemDaoImpl.class);
		} else {
			bind(SubscriptionDao.class).to(SubscriptionDaoMock.class);
			bind(ItemDao.class).to(ItemDaoMock.class);
		}
	}
}
