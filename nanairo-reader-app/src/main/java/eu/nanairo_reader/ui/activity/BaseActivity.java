package eu.nanairo_reader.ui.activity;

import javax.inject.Inject;

import android.app.Activity;
import android.os.Bundle;
import eu.nanairo_reader.business.service.RssService;
import eu.nanairo_reader.ui.NanairoApplication;

public abstract class BaseActivity extends Activity {
	@Inject
	RssService rssService;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		((NanairoApplication) getApplication()).inject(this);
	}
}
