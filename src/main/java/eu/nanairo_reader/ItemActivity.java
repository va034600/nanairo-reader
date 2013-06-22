package eu.nanairo_reader;

import roboguice.activity.RoboActivity;
import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebView;
import android.widget.TextView;
import eu.nanairo_reader.bean.Item;

public class ItemActivity extends RoboActivity {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.item);

		Intent intent = getIntent();
		Item item = (Item) intent.getSerializableExtra("item");

		WebView contentTextView = (WebView) findViewById(R.id.content);
		contentTextView.loadDataWithBaseURL("about:blank", item.getContent(), "text/html", "utf-8", null);

		TextView linkTextView = (TextView) findViewById(R.id.link);
		linkTextView.setText(item.getLink());
	}
}
