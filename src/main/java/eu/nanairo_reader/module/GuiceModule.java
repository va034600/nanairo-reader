package eu.nanairo_reader.module;

import com.google.inject.AbstractModule;

import eu.nanairo_reader.dao.ItemDao;
import eu.nanairo_reader.dao.ItemDaoMock;
import eu.nanairo_reader.dao.SubscriptionDao;
import eu.nanairo_reader.dao.SubscriptionDaoMock;
import eu.nanairo_reader.service.RssService;
import eu.nanairo_reader.service.RssServiceImpl;

public class GuiceModule extends AbstractModule {
	@Override
	protected void configure() {
		// service
		bind(RssService.class).to(RssServiceImpl.class);

		// dao
		bind(SubscriptionDao.class).to(SubscriptionDaoMock.class);
		bind(ItemDao.class).to(ItemDaoMock.class);
	}
}
