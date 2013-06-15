package eu.nanairo_reader;

import javax.inject.Inject;

import roboguice.activity.RoboActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

public class HelloAndroidActivity extends RoboActivity {
	@Inject
	SampleService sampleService;

	private static String TAG = "sample-android-maven";

	/**
	 * Called when the activity is first created.
	 * 
	 * @param savedInstanceState
	 *            Bundle
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.i(TAG, "onCreate");
		setContentView(R.layout.main);

		String message = sampleService.getMessage();
		Toast.makeText(HelloAndroidActivity.this, message, Toast.LENGTH_SHORT).show();
	}
}
