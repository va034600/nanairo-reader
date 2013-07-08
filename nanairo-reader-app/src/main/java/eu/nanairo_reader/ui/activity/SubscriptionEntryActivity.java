package eu.nanairo_reader.ui.activity;

import javax.inject.Inject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import eu.nanairo_reader.NanairoApplication;
import eu.nanairo_reader.R;
import eu.nanairo_reader.business.service.RssService;
import eu.nanairo_reader.ui.service.SampleService;

public class SubscriptionEntryActivity extends Activity {
	@Inject
	RssService rssService;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.subscription_entry);

		((NanairoApplication) getApplication()).inject(this);
		((NanairoApplication) getApplication()).inject(this.rssService);

		Button entryButton = (Button) findViewById(R.id.entryButton);
		entryButton.setOnClickListener(onSave);
	}

	private View.OnClickListener onSave = new View.OnClickListener() {
		public void onClick(View v) {
			EditText urlEditText = (EditText) findViewById(R.id.urlEditText);
			boolean flag = rssService.addSubscription(urlEditText.getText().toString());

			if (!flag) {
				Toast.makeText(getApplicationContext(), "登録できませんでした。", Toast.LENGTH_SHORT).show();
				return;
			}

			Toast.makeText(getApplicationContext(), "登録しました。", Toast.LENGTH_SHORT).show();

			// サービスの起動
			Intent intent = new Intent(SubscriptionEntryActivity.this, SampleService.class);
			startService(intent);

			finish();
		}
	};
}
