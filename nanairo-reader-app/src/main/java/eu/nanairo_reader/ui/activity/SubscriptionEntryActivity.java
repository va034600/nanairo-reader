package eu.nanairo_reader.ui.activity;

import javax.inject.Inject;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import eu.nanairo_reader.R;
import eu.nanairo_reader.business.service.RssService;
import eu.nanairo_reader.ui.NanairoApplication;

public class SubscriptionEntryActivity extends BaseActivity {
	@Inject
	RssService rssService;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.subscription_entry);

		((NanairoApplication) getApplication()).inject(this.rssService);

		Button entryButton = (Button) findViewById(R.id.entryButton);
		entryButton.setOnClickListener(onSave);
	}

	private View.OnClickListener onSave = new View.OnClickListener() {
		public void onClick(View v) {
			// TODO AsyncTaskにする？
			EditText urlEditText = (EditText) findViewById(R.id.urlEditText);
			boolean flag = rssService.addSubscription(urlEditText.getText().toString());

			if (!flag) {
				Toast.makeText(getApplicationContext(), "登録できませんでした。", Toast.LENGTH_SHORT).show();
				return;
			}

			Toast.makeText(getApplicationContext(), "登録しました。", Toast.LENGTH_SHORT).show();

			finish();
		}
	};
}
