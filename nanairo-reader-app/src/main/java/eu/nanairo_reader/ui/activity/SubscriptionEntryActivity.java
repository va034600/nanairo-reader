package eu.nanairo_reader.ui.activity;

import javax.inject.Inject;

import android.os.AsyncTask;
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
			// URL
			EditText urlEditText = (EditText) findViewById(R.id.urlEditText);
			urlEditText.setEnabled(false);
			String url = urlEditText.getText().toString();

			// entryButton
			Button entryButton = (Button) findViewById(R.id.entryButton);
			entryButton.setEnabled(false);

			//
			new AsyncTask<String, Void, Boolean>() {
				@Override
				protected Boolean doInBackground(String... params) {
					boolean flag = rssService.addSubscription(params[0]);
					return flag;
				}

				@Override
				protected void onPostExecute(Boolean flag) {
					if (!flag) {
						// URL
						EditText urlEditText = (EditText) findViewById(R.id.urlEditText);
						urlEditText.setEnabled(true);

						// entryButton
						Button entryButton = (Button) findViewById(R.id.entryButton);
						entryButton.setEnabled(true);

						Toast.makeText(getApplicationContext(), "登録できませんでした。", Toast.LENGTH_SHORT).show();
						return;
					}

					Toast.makeText(getApplicationContext(), "登録しました。", Toast.LENGTH_SHORT).show();

					finish();
				};
			}.execute(url);
		}
	};
}
