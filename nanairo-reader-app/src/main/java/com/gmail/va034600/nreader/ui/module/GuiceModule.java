package com.gmail.va034600.nreader.ui.module;

import javax.inject.Singleton;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.gmail.va034600.nreader.business.domain.service.ArticleListManager;
import com.gmail.va034600.nreader.business.domain.service.RssService;
import com.gmail.va034600.nreader.business.domain.service.RssServiceImpl;
import com.gmail.va034600.nreader.business.domain.service.SubscriptionListManager;
import com.gmail.va034600.nreader.business.rss.service.RssParsingService;
import com.gmail.va034600.nreader.business.rss.service.RssParsingServiceMock;
import com.gmail.va034600.nreader.data.dao.ArticleDao;
import com.gmail.va034600.nreader.data.dao.ArticleDaoImpl;
import com.gmail.va034600.nreader.data.dao.ArticleDaoMock;
import com.gmail.va034600.nreader.data.dao.SubscriptionArticleDao;
import com.gmail.va034600.nreader.data.dao.SubscriptionArticleDaoImpl;
import com.gmail.va034600.nreader.data.dao.SubscriptionArticleDaoMock;
import com.gmail.va034600.nreader.data.dao.SubscriptionDao;
import com.gmail.va034600.nreader.data.dao.SubscriptionDaoImpl;
import com.gmail.va034600.nreader.data.dao.SubscriptionDaoMock;
import com.gmail.va034600.nreader.ui.NanairoApplication;
import com.google.inject.AbstractModule;


public class GuiceModule extends AbstractModule {
private final Context context;
	
	public GuiceModule(Context context){
		this.context = context;
	}
	
	private SubscriptionListManager subscriptionListManager = new SubscriptionListManager();
	
	private ArticleListManager articleListManager = new ArticleListManager();
	
	@Override
	protected void configure() {
		// service
		bind(RssService.class).to(RssServiceImpl.class);
		// bind(RssParsingService.class).to(RssParsingServiceImpl.class);
		bind(RssParsingService.class).to(RssParsingServiceMock.class);

		bind(SubscriptionListManager.class).to(SubscriptionListManager.class).in(Singleton.class);
		bind(ArticleListManager.class).to(ArticleListManager.class).in(Singleton.class);

		bind(SubscriptionListManager.class).toInstance(subscriptionListManager);
		bind(ArticleListManager.class).toInstance(articleListManager);

		// dao
		// TODO
		boolean flag = true;
		if (flag) {
			// TODO
			
			SQLiteDatabase db = ((NanairoApplication)context.getApplicationContext()).getDb();

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
