package eu.nanairo_reader.business.service;

import java.util.ArrayList;
import java.util.List;

import eu.nanairo_reader.business.bean.Subscription;

public class SubscriptionListManager {
	private List<Subscription> subscriptionList = new ArrayList<Subscription>();

	public List<Subscription> getSubscriptionList() {
		return subscriptionList;
	}

	public void clear(){
		this.subscriptionList.clear();
	}

	public void add(Subscription subscription){
		this.subscriptionList.add(subscription);
	}
	
	public void remove(Subscription subscription){
		this.subscriptionList.remove(subscription);
	}
	
	public void kidoku(long subscriptionId) {
		for (Subscription subscription : this.subscriptionList) {
			if (subscription.getId() == subscriptionId) {
				subscription.setMidokuCount(subscription.getMidokuCount() - 1);
				return;
			}
		}
	}

	public void kidokuAll(long subscriptionId) {
		for (Subscription subscription : this.subscriptionList) {
			if (subscription.getId() == subscriptionId) {
				subscription.setMidokuCount(0);
				break;
			}
		}
	}
}
