package com.gmail.va034600.nreader.ui.module;

import javax.inject.Singleton;

import com.gmail.va034600.nreader.business.NanairoContext;
import com.gmail.va034600.nreader.business.service.ArticleListManager;
import com.gmail.va034600.nreader.business.service.ArticleService;
import com.gmail.va034600.nreader.business.service.ArticleServiceImpl;
import com.gmail.va034600.nreader.business.service.RssParsingService;
import com.gmail.va034600.nreader.business.service.RssParsingService2Impl;
import com.gmail.va034600.nreader.business.service.RssService;
import com.gmail.va034600.nreader.business.service.RssServiceImpl;
import com.gmail.va034600.nreader.business.service.SubscriptionArticleService;
import com.gmail.va034600.nreader.business.service.SubscriptionArticleServiceImpl;
import com.gmail.va034600.nreader.business.service.SubscriptionListManager;
import com.gmail.va034600.nreader.business.service.SubscriptionService;
import com.gmail.va034600.nreader.business.service.SubscriptionServiceImpl;
import com.gmail.va034600.nreader.data.dao.ArticleDao;
import com.gmail.va034600.nreader.data.dao.ArticleDaoImpl;
import com.gmail.va034600.nreader.data.dao.SubscriptionArticleDao;
import com.gmail.va034600.nreader.data.dao.SubscriptionArticleDaoImpl;
import com.gmail.va034600.nreader.data.dao.SubscriptionDao;
import com.gmail.va034600.nreader.data.dao.SubscriptionDaoImpl;
import com.gmail.va034600.nreader.ui.NanairoApplication;
import com.gmail.va034600.nreader.ui.activity.ArticleActivity;
import com.gmail.va034600.nreader.ui.activity.ArticleListActivity;
import com.gmail.va034600.nreader.ui.activity.SubscriptionEntryActivity;
import com.gmail.va034600.nreader.ui.activity.SubscriptionListActivity;
import com.gmail.va034600.nreader.ui.service.SampleService;

import dagger.Module;
import dagger.Provides;

@Module(library=true, injects = {
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
		NanairoContext.class,
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
	NanairoContext provideNanairoContext() {
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
