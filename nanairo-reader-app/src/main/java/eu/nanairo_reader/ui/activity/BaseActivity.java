package eu.nanairo_reader.ui.activity;

import android.app.Activity;
import android.os.Bundle;
import eu.nanairo_reader.NanairoApplication;

public class BaseActivity extends Activity {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		((NanairoApplication) getApplication()).inject(this);
	}
}
