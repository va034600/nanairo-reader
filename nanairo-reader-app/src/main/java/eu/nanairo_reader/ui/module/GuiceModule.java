package eu.nanairo_reader.ui.module;

import android.database.sqlite.SQLiteDatabase;

import com.google.inject.AbstractModule;

import eu.nanairo_reader.business.service.RssParsingService;
import eu.nanairo_reader.business.service.RssParsingServiceMock;
import eu.nanairo_reader.business.service.RssService;
import eu.nanairo_reader.business.service.RssServiceImpl;
import eu.nanairo_reader.data.dao.ArticleDao;
import eu.nanairo_reader.data.dao.ArticleDaoImpl;
import eu.nanairo_reader.data.dao.ArticleDaoMock;
import eu.nanairo_reader.data.dao.SubscriptionArticleDao;
import eu.nanairo_reader.data.dao.SubscriptionArticleDaoImpl;
import eu.nanairo_reader.data.dao.SubscriptionArticleDaoMock;
import eu.nanairo_reader.data.dao.SubscriptionDao;
import eu.nanairo_reader.data.dao.SubscriptionDaoImpl;
import eu.nanairo_reader.data.dao.SubscriptionDaoMock;
import eu.nanairo_reader.ui.NanairoApplication;

public class GuiceModule extends AbstractModule {
	@Override
	protected void configure() {
		// service
		bind(RssService.class).to(RssServiceImpl.class);
		// bind(RssParsingService.class).to(RssParsingServiceImpl.class);
		bind(RssParsingService.class).to(RssParsingServiceMock.class);

		// dao
		// TODO
		boolean flag = true;
		if (flag) {
			// TODO
			SQLiteDatabase db = null;//TODO NanairoApplication.db;

			SubscriptionDaoImpl subscriptionDaoImpl = new SubscriptionDaoImpl();
			subscriptionDaoImpl.setDb(db);
			bind(SubscriptionDao.class).toInstance(subscriptionDaoImpl);

			ArticleDaoImpl articleDaoImpl = new ArticleDaoImpl();
			articleDaoImpl.setDb(db);
			bind(ArticleDao.class).toInstance(articleDaoImpl);

			SubscriptionArticleDaoImpl subscriptionArticleDaoImpl = new SubscriptionArticleDaoImpl();
			subscriptionArticleDaoImpl.setDb(db);
			bind(SubscriptionArticleDao.class).toInstance(subscriptionArticleDaoImpl);
		} else {
			bind(SubscriptionDao.class).to(SubscriptionDaoMock.class);
			bind(ArticleDao.class).to(ArticleDaoMock.class);
			bind(SubscriptionArticleDao.class).to(SubscriptionArticleDaoMock.class);
		}
	}
}
