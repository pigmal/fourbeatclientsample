package com.pigmal.android.fourbeat.sample;

import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends FourBeatBaseActivity {
	protected static final String TAG = "ServiceSample";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}

	private void updateUi(final int id, final int state) {
		switch (id) {
		case 0:
			((TextView) findViewById(R.id.text_red))
					.setText("state = " + state);
			break;
		case 1:
			((TextView) findViewById(R.id.text_blue)).setText("state = "
					+ state);
			break;
		case 2:
			((TextView) findViewById(R.id.text_yellow)).setText("state = "
					+ state);
			break;
		case 3:
			((TextView) findViewById(R.id.text_green)).setText("state = "
					+ state);
			break;
		default:
			break;
		}
	}

	@Override
	protected void onFourBeatConnected() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void onFourBeatStateChange(final int id, final int state) {
		this.runOnUiThread(new Runnable() {
			@Override
			public void run() {
				updateUi(id, state);
			}
		});
	}
}
