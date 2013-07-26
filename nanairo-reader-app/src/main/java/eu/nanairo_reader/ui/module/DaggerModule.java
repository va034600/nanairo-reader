package eu.nanairo_reader.ui.module;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import eu.nanairo_reader.business.service.ArticleListManager;
import eu.nanairo_reader.business.service.ArticleService;
import eu.nanairo_reader.business.service.ArticleServiceImpl;
import eu.nanairo_reader.business.service.RssParsingService;
import eu.nanairo_reader.business.service.RssParsingService2Impl;
import eu.nanairo_reader.business.service.RssService;
import eu.nanairo_reader.business.service.RssServiceImpl;
import eu.nanairo_reader.business.service.SubscriptionArticleService;
import eu.nanairo_reader.business.service.SubscriptionArticleServiceImpl;
import eu.nanairo_reader.business.service.SubscriptionListManager;
import eu.nanairo_reader.business.service.SubscriptionService;
import eu.nanairo_reader.business.service.SubscriptionServiceImpl;
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

@Module(injects = {
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
		//
		RssParsingService.class,
		// TODO implとりたいよね。
		RssServiceImpl.class,
		// TODO implとりたいよね。
		SubscriptionServiceImpl.class,
		// TODO implとりたいよね。
		SubscriptionArticleServiceImpl.class,
		// TODO implとりたいよね。
		ArticleServiceImpl.class,
		//
		SubscriptionListManager.class,
		//
		ArticleListManager.class,
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
		RssService rssService = new RssServiceImpl();
		application.inject(rssService);
		return rssService;
	}

	@Provides
	SubscriptionService provideSubscriptionService() {
		SubscriptionService subscriptionService = new SubscriptionServiceImpl();
		application.inject(subscriptionService);
		return subscriptionService;
	}

	@Provides
	SubscriptionArticleService provideSubscriptionArticleService() {
		SubscriptionArticleService subscriptionArticleService = new SubscriptionArticleServiceImpl();
		application.inject(subscriptionArticleService);
		return subscriptionArticleService;
	}

	@Provides
	ArticleService provideArticleService() {
		ArticleService articleService = new ArticleServiceImpl();
		application.inject(articleService);
		return articleService;
	}

	@Provides
	RssParsingService provideRssParsingService() {
		// TODO 今だけMock
		//return new RssParsingServiceMock();
		// return new RssParsingServiceImpl();
		return new RssParsingService2Impl();
	}

	@Provides
	@Singleton
	SubscriptionListManager provideSubscriptionListManager() {
		return new SubscriptionListManager();
	}

	@Provides
	@Singleton
	ArticleListManager provideArticleListManager() {
		return new ArticleListManager();
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
