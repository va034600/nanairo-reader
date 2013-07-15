package eu.nanairo_reader.ui.module;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import eu.nanairo_reader.business.service.RssParsingService;
import eu.nanairo_reader.business.service.RssParsingServiceMock;
import eu.nanairo_reader.business.service.RssService;
import eu.nanairo_reader.business.service.RssServiceImpl;
import eu.nanairo_reader.business.service.SubscriptionListManager;
import eu.nanairo_reader.data.dao.ArticleDao;
import eu.nanairo_reader.data.dao.ArticleDaoImpl;
import eu.nanairo_reader.data.dao.SubscriptionArticleDao;
import eu.nanairo_reader.data.dao.SubscriptionArticleDaoImpl;
import eu.nanairo_reader.data.dao.SubscriptionDao;
import eu.nanairo_reader.data.dao.SubscriptionDaoImpl;
import eu.nanairo_reader.ui.NanairoApplication;
import eu.nanairo_reader.ui.activity.ArticleActivity;
import eu.nanairo_reader.ui.activity.ArticleListActivity;
import eu.nanairo_reader.ui.activity.SubscriptionEntryActivity;
import eu.nanairo_reader.ui.activity.SubscriptionListActivity;
import eu.nanairo_reader.ui.service.SampleService;

@Module(library = true, injects = {
//
		//
		SubscriptionListActivity.class,
		//
		ArticleListActivity.class,
		//
		ArticleActivity.class,
		//
		SubscriptionEntryActivity.class,
		//
		SampleService.class,
		// TODO implとりたいよね。
		RssServiceImpl.class,
		//
		RssParsingService.class,
		//
		SubscriptionListManager.class,
		//
		SubscriptionDao.class,
		//
		SubscriptionArticleDao.class,
		//
		ArticleDao.class })
public class DaggerModule {
	private final NanairoApplication application;

	public DaggerModule(NanairoApplication application) {
		this.application = application;
	}

	@Provides
	NanairoApplication provideNanairoApplication() {
		return this.application;
	}

	@Provides
	RssService provideRssService() {
		return new RssServiceImpl();
	}

	@Provides
	RssParsingService provideRssParsingService() {
		// TODO 今だけMock
		return new RssParsingServiceMock();
		// return new RssParsingServiceImpl();
		// return new RssParsingService2Impl();
	}

	@Provides
	@Singleton
	SubscriptionListManager provideSubscriptionListManager() {
		return new SubscriptionListManager();
	}

	@Provides
	SubscriptionDao provideSubscriptionDao() {
		SubscriptionDaoImpl subscriptionDaoImpl = new SubscriptionDaoImpl();
		subscriptionDaoImpl.setDb(application.getDb());
		return subscriptionDaoImpl;
	}

	@Provides
	ArticleDao provideArticleDao() {
		ArticleDaoImpl articleDaoImpl = new ArticleDaoImpl();
		articleDaoImpl.setDb(application.getDb());
		return articleDaoImpl;
	}

	@Provides
	SubscriptionArticleDao provideSubscriptionArticleDao() {
		SubscriptionArticleDaoImpl subscriptionArticleDaoImpl = new SubscriptionArticleDaoImpl();
		subscriptionArticleDaoImpl.setDb(application.getDb());
		return subscriptionArticleDaoImpl;
	}
}
