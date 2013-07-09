package eu.nanairo_reader.ui.activity;

import android.app.ListActivity;
import android.os.Bundle;
import eu.nanairo_reader.NanairoApplication;

public class BaseListActivity extends ListActivity {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		((NanairoApplication) getApplication()).inject(this);
	}
}
