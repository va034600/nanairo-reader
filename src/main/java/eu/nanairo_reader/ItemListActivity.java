package eu.nanairo_reader;

import java.util.List;

import javax.inject.Inject;

import roboguice.activity.RoboListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import eu.nanairo_reader.bean.Item;
import eu.nanairo_reader.bean.Subscription;
import eu.nanairo_reader.service.RssService;

public class ItemListActivity extends RoboListActivity {
	@Inject
	private RssService rssService;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.item_list);

		Intent intent = getIntent();
		Subscription subscription = (Subscription) intent.getSerializableExtra("subscription");
		List<Item> list = this.rssService.getItemList(subscription.getUrl());
		ListAdapter adapter = new ListAdapter(getApplicationContext(), list);

		setListAdapter(adapter);
	}

	class ListAdapter extends ArrayAdapter<Item> {
		private LayoutInflater mInflater;
		private TextView mTitle;
		private Button mButton;

		public ListAdapter(Context context, List<Item> objects) {
			super(context, 0, objects);
			mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		}

		public View getView(final int position, View convertView, ViewGroup parent) {
			if (convertView == null) {
				convertView = mInflater.inflate(R.layout.subscription_row, null);
			}
			final Item item = this.getItem(position);
			if (item != null) {
				mTitle = (TextView) convertView.findViewById(R.id.nameText);
				mTitle.setText(item.getTitle());
				mButton = (Button) convertView.findViewById(R.id.detailButton);
				mButton.setOnClickListener(new OnClickListener() {
					public void onClick(View v) {
						Intent intent = new Intent(ItemListActivity.this, ItemActivity.class);
						intent.putExtra("item", item);
						startActivity(intent);
					}
				});
			}
			return convertView;
		}
	}
}
