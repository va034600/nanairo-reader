package com.gmail.va034600.nreader.ui.component;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.gmail.va034600.nreader.R;
import com.gmail.va034600.nreader.business.bean.Subscription;

public 	class SubscriptionArrayAdapter extends ArrayAdapter<Subscription> {
	private LayoutInflater mInflater;

	public SubscriptionArrayAdapter(Context context, List<Subscription> objects) {
		super(context, 0, objects);
		mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	public View getView(final int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.subscription_list_row, null);
		}

		Subscription subscription = this.getItem(position);
		if (subscription != null) {
			// タイトル
			TextView mTitle = (TextView) convertView.findViewById(R.id.nameText);
			mTitle.setText(subscription.getTitle());

			// 未読数
			TextView mCount = (TextView) convertView.findViewById(R.id.midokuCountText);
			mCount.setText(Integer.toString(subscription.getMidokuCount()));

			// 最終更新日
			//TODO
			TextView mLastPublishedDate = (TextView) convertView.findViewById(R.id.lastPublishedDateText);
			mLastPublishedDate.setText(subscription.getPublishedDate());
		}
		return convertView;
	}
}
