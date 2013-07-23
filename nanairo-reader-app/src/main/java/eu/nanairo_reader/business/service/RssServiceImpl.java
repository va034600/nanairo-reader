package eu.nanairo_reader.business.service;

import javax.inject.Inject;

import eu.nanairo_reader.business.bean.Subscription;
import eu.nanairo_reader.business.exception.RssParsingException;
import eu.nanairo_reader.business.vo.FeedItem;
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
	SubscriptionArticleService subscriptionArticleService;

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
			storeArticle(subscriptionEntity);
		}

		loadSubscriptionList();
	}

	@Override
	public int storeArticle(long subscriptionId) {
		SubscriptionEntity subscriptionEntity = this.subscriptionService.findByPrimaryKey(subscriptionId);
		storeArticle(subscriptionEntity);

		// TODO just one
		loadSubscriptionList();

		// TODO 更新件数
		return 0;
	}

	protected void storeArticle(SubscriptionEntity subscriptionEntity) {
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
			addArticleListByFeed(subscriptionId, feed);

			this.nanairoApplication.getDb().setTransactionSuccessful();
		} finally {
			this.nanairoApplication.getDb().endTransaction();
		}
	}

	protected void addArticleListByFeed(long subscriptionId, FeedResult feedResult) {
		for (FeedItem feedItem : feedResult.getFeedItemList()) {
			// 登録済みの場合、登録しない。
			boolean flag = this.articleService.isDuplicated(feedItem.getLink());
			if (flag) {
				continue;
			}

			// 記事を登録する。
			long articleId = this.articleService.addArticle(feedItem);

			// 購読記事を登録する。
			this.subscriptionArticleService.addSubscriptionArticle(subscriptionId, articleId);
		}

		// TODO 件数確認
		// 古いのを削除する。
		final int MAX_ARTICLE = 100;
		this.articleService.deleteTheOld(subscriptionId, MAX_ARTICLE);
		this.subscriptionArticleService.deleteTheOld(subscriptionId, MAX_ARTICLE);

		loadArticleList(subscriptionId);
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

		// TODO subscriptionServiceに移動
		int midokuCount = this.articleService.getMidokuCount(subscription.getId());
		subscription.setMidokuCount(midokuCount);

		// 記事一覧追加
		addArticleListByFeed(subscription.getId(), feedResult);
	}

	@Override
	public void kidoku(long articleId) {
		if (this.articleService.kidoku(articleId)) {
			return;
		}

		long subscriptionId = this.subscriptionArticleService.findSubscriptionIdByArticleId(articleId);

		this.subscriptionService.kidoku(subscriptionId);
	}

	@Override
	public void kidokuAll(long subscriptionId) {
		this.subscriptionArticleService.updateKidokuBySubscriptionId(subscriptionId);

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
		long subscriptionId = subscription.getId();

		// subscription
		this.subscriptionService.deleteSubscription(subscription);

		// article
		this.articleService.deleteBySucriptionId(subscriptionId);

		// subscriptionArticle
		this.subscriptionArticleService.deleteBySubscriptionId(subscriptionId);
	}
}
