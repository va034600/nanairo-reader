package eu.nanairo_reader.business.service;

import javax.inject.Inject;

import eu.nanairo_reader.business.bean.Subscription;
import eu.nanairo_reader.business.exception.RssParsingException;
import eu.nanairo_reader.business.vo.FeedResult;
import eu.nanairo_reader.data.entity.SubscriptionEntity;
import eu.nanairo_reader.ui.NanairoApplication;

public class RssServiceImpl implements RssService {
	@Inject
	NanairoApplication nanairoApplication;

	@Inject
	SubscriptionService subscriptionService;

	@Inject
	ArticleService articleService;

	@Inject
	RssParsingService rssParsingService;

	@Override
	public void loadSubscriptionList() {
		this.subscriptionService.loadSubscriptionList();
	}

	@Override
	public void loadArticleList(long subscriptionId) {
		this.articleService.loadArticleList(subscriptionId);
	}

	@Override
	public void storeArticles() {
		for (SubscriptionEntity subscriptionEntity : this.subscriptionService.findList()) {
			parseAdding(subscriptionEntity);
		}

		this.subscriptionService.loadSubscriptionList();
	}

	@Override
	public int storeArticle(long subscriptionId) {
		SubscriptionEntity subscriptionEntity = this.subscriptionService.findByPrimaryKey(subscriptionId);
		parseAdding(subscriptionEntity);

		// TODO just one
		this.subscriptionService.loadSubscriptionList();

		// TODO 更新件数
		return 0;
	}

	protected void parseAdding(SubscriptionEntity subscriptionEntity) {
		FeedResult feed;
		try {
			feed = this.rssParsingService.getFeedResult(subscriptionEntity.getUrl());
		} catch (RssParsingException e) {
			// TODO ログ出力
			return;
		}

		long subscriptionId = subscriptionEntity.getId();
		try {
			// TODO できれば、トランザクションは明示的ではなく、暗黙的にaopで管理したい。
			this.nanairoApplication.getDb().beginTransaction();

			// 記事一覧追加
			this.articleService.addArticleListByFeed(subscriptionId, feed);

			this.nanairoApplication.getDb().setTransactionSuccessful();
		} finally {
			this.nanairoApplication.getDb().endTransaction();
		}
	}

	@Override
	public boolean addSubscription(String url) {
		FeedResult feedResult;
		try {
			feedResult = this.rssParsingService.getFeedResult(url);
		} catch (RssParsingException e) {
			// TODO ログ出力
			// feedを取得できない場合、登録しない。
			return false;
		}

		try {
			// TODO できれば、トランザクションは明示的ではなく、暗黙的にaopで管理したい。
			this.nanairoApplication.getDb().beginTransaction();

			addSubscription(url, feedResult);

			this.nanairoApplication.getDb().setTransactionSuccessful();
		} finally {
			this.nanairoApplication.getDb().endTransaction();
		}

		return true;
	}

	protected void addSubscription(String url, FeedResult feedResult) {
		Subscription subscription = this.subscriptionService.addSubscription(url, feedResult);

		// 記事一覧追加
		this.articleService.addArticleListByFeed(subscription.getId(), feedResult);

		// TODO subscriptionServiceに移動
		int midokuCount = this.articleService.getMidokuCount(subscription.getId());
		subscription.setMidokuCount(midokuCount);
	}

	@Override
	public void kidoku(long articleId) {
		Long subscriptionId = this.articleService.kidoku(articleId);
		if (subscriptionId == null) {
			return;
		}

		this.subscriptionService.kidoku(subscriptionId);
	}

	@Override
	public void kidokuAll(long subscriptionId) {
		this.articleService.kidokuAll(subscriptionId);

		this.subscriptionService.kidokuAll(subscriptionId);
	}

	@Override
	public void deleteSubscription(Subscription subscription) {
		try {
			// TODO できれば、トランザクションは明示的ではなく、暗黙的にaopで管理したい。
			this.nanairoApplication.getDb().beginTransaction();

			deleteSubscriptionCore(subscription);

			this.nanairoApplication.getDb().setTransactionSuccessful();
		} finally {
			this.nanairoApplication.getDb().endTransaction();
		}
	}

	private void deleteSubscriptionCore(Subscription subscription) {
		this.subscriptionService.deleteSubscription(subscription);

		this.articleService.deleteBySucriptionId(subscription.getId());
	}
}
