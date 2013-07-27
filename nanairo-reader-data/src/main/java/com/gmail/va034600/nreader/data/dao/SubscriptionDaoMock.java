package com.gmail.va034600.nreader.data.dao;

import java.util.ArrayList;
import java.util.List;

import com.gmail.va034600.nreader.data.dto.SubscriptionDto;
import com.gmail.va034600.nreader.data.entity.SubscriptionEntity;


public class SubscriptionDaoMock extends BaseDaoMock<SubscriptionEntity, Long> implements SubscriptionDao {
	@Override
	public List<SubscriptionEntity> findList(SubscriptionEntity param) {
		List<SubscriptionEntity> list = new ArrayList<SubscriptionEntity>();

		SubscriptionEntity subscription1 = new SubscriptionEntity();
		subscription1.setId(1L);
		subscription1.setTitle("注目まとめ（総合） - NAVER まとめ");
		subscription1.setUrl("http://matome.naver.jp/feed/hot");
		list.add(subscription1);

		SubscriptionEntity subscription2 = new SubscriptionEntity();
		subscription2.setId(2L);
		subscription2.setTitle("TBN　-Today's Best News-");
		subscription2.setUrl("http://tbn17.com/index.rdf");
		list.add(subscription2);

		return list;
	}

	@Override
	public List<SubscriptionDto> findListAndMidokuCount() {
		return new ArrayList<SubscriptionDto>();
	}
}
