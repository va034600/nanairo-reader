package eu.nanairo_reader.module;

import dagger.Module;
import dagger.Provides;
import eu.nanairo_reader.NanairoApplication;
import eu.nanairo_reader.business.service.RssParsingService;
import eu.nanairo_reader.business.service.RssParsingServiceMock;
import eu.nanairo_reader.business.service.RssService;
import eu.nanairo_reader.business.service.RssServiceImpl;
import eu.nanairo_reader.data.dao.ArticleDao;
import eu.nanairo_reader.data.dao.ArticleDaoImpl;
import eu.nanairo_reader.data.dao.SubscriptionArticleDao;
import eu.nanairo_reader.data.dao.SubscriptionArticleDaoImpl;
import eu.nanairo_reader.data.dao.SubscriptionDao;
import eu.nanairo_reader.data.dao.SubscriptionDaoImpl;
import eu.nanairo_reader.ui.activity.ArticleActivity;
import eu.nanairo_reader.ui.activity.ArticleListActivity;
import eu.nanairo_reader.ui.activity.SubscriptionListActivity;
import eu.nanairo_reader.ui.service.SampleService;

@Module(library = true, injects = { 
		SubscriptionListActivity.class, 
		ArticleListActivity.class,
		ArticleActivity.class,

		SampleService.class, 

		//RssService.class,
		RssServiceImpl.class,
		//RssParsingService.class, 
		RssParsingServiceMock.class, 
		
		//SubscriptionDao.class, 
		SubscriptionDaoImpl.class, 
		//SubscriptionArticleDao.class,
		SubscriptionArticleDaoImpl.class,
		//ArticleDao.class, 
		ArticleDaoImpl.class 
		})
public class AndroidModule {
	private final NanairoApplication application;

	public AndroidModule(NanairoApplication application) {
		this.application = application;
	}

	@Provides
	RssService provideRssService() {
		return new RssServiceImpl();
	}

	@Provides
	RssParsingService provideRssParsingService() {
		//return new RssParsingServiceImpl();
		return new RssParsingServiceMock();
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
