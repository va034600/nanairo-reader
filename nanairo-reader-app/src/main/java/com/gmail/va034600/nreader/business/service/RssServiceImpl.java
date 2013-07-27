package com.gmail.va034600.nreader.business.service;

import javax.inject.Inject;

import com.gmail.va034600.nreader.business.bean.Subscription;
import com.gmail.va034600.nreader.business.exception.RssParsingException;
import com.gmail.va034600.nreader.business.vo.FeedResult;
import com.gmail.va034600.nreader.data.entity.SubscriptionEntity;
import com.gmail.va034600.nreader.ui.NanairoApplication;


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
	public int storeArticles() {
		int newMidokuCount = 0;
		for (SubscriptionEntity subscriptionEntity : this.subscriptionService.findList()) {
			newMidokuCount += parseAdding(subscriptionEntity);
		}

		this.subscriptionService.loadSubscriptionList();

		return newMidokuCount;
	}

	@Override
	public int storeArticle(long subscriptionId) {
		SubscriptionEntity subscriptionEntity = this.subscriptionService.findByPrimaryKey(subscriptionId);
		int newMidokuCount = parseAdding(subscriptionEntity);

		// TODO just one
		this.subscriptionService.loadSubscriptionList();

		// TODO 更新件数
		return newMidokuCount;
	}

	protected int parseAdding(SubscriptionEntity subscriptionEntity) {
		int newMidokuCount = 0;
		FeedResult feedResult;
		try {
			feedResult = this.rssParsingService.getFeedResult(subscriptionEntity.getUrl());
		} catch (RssParsingException e) {
			// TODO ログ出力
			return 0;
		}

		long subscriptionId = subscriptionEntity.getId();
		try {
			// TODO できれば、トランザクションは明示的ではなく、暗黙的にaopで管理したい。
			this.nanairoApplication.getDb().beginTransaction();

			// 記事一覧追加
			newMidokuCount = this.articleService.addArticleListByFeed(subscriptionId, feedResult);

			this.nanairoApplication.getDb().setTransactionSuccessful();
		} finally {
			this.nanairoApplication.getDb().endTransaction();
		}

		return newMidokuCount;
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
		int midokuCount = this.articleService.addArticleListByFeed(subscription.getId(), feedResult);
		// TODO subscriptionServiceに移動
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

	@Override
	public boolean loadArticleListByNext(long subscriptionId) {
		// 次のsubscriptionIdを探す
		Long nextSubscriptionId = this.subscriptionService.getNextSubscriptionId(subscriptionId);
		if(nextSubscriptionId == null){
			return false;
		}

		this.articleService.loadArticleList(nextSubscriptionId);
		return true;
	}
}
