package eu.nanairo_reader.business.service;

import java.util.ArrayList;
import java.util.List;

import eu.nanairo_reader.business.bean.Subscription;

public class SubscriptionListManager {
	private List<Subscription> subscriptionList = new ArrayList<Subscription>();

	public List<Subscription> getSubscriptionList() {
		return subscriptionList;
	}
}
