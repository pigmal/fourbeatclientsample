package com.pigmal.android.fourbeat.sample;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

import com.pigmal.android.fourbeat.service.IFourBeatService;
import com.pigmal.android.fourbeat.service.IFourBeatServiceListener;

public abstract class FourBeatBaseActivity extends Activity {
	static final String TAG = "FourBeatActivity";
	
	protected static final int BUTTON_COLOR_RED = 0;
	protected static final int BUTTON_COLOR_BLUE = 1;
	protected static final int BUTTON_COLOR_YELLOW = 2;
	protected static final int BUTTON_COLOR_GREEN = 3;
	protected static final int BUTTON_STATE_ON = 1;
	protected static final int BUTTON_STATE_OFF = 0;

	private Activity mActivity;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mActivity = this;
	}

	@Override
	protected void onResume() {
		super.onResume();
		bindFourBeat();
	}

	@Override
	protected void onPause() {
		unbindFourBeat();
		super.onPause();		
	}

	//
	// FourBeat binding
	//
	private IFourBeatService mServiceInterface;
	
	boolean bindFourBeat() {
		Intent intent = new Intent(IFourBeatService.class.getName());
		return mActivity.bindService(intent, mServiceConnection, BIND_AUTO_CREATE);
	}

	void unbindFourBeat() {
		if (mServiceInterface != null) {
			try {
				mServiceInterface.unregisterCallback(mServiceListener);
			} catch (RemoteException e) {
				e.printStackTrace();
			}
			mActivity.unbindService(mServiceConnection);
			mServiceInterface = null;
		}
	}

	private ServiceConnection mServiceConnection = new ServiceConnection() {
		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			Log.v(TAG, "onServiceConnected");
			mServiceInterface = IFourBeatService.Stub.asInterface(service);
			try {
				mServiceInterface.registerCallback(mServiceListener);
			} catch (RemoteException e) {
				e.printStackTrace();
			}
			onFourBeatConnected();
		}

		@Override
		public void onServiceDisconnected(ComponentName name) {
			Log.v(TAG, "onServiceDisconnected");
			mServiceInterface = null;
		}
	};

	private IFourBeatServiceListener mServiceListener = new IFourBeatServiceListener.Stub() {
		@Override
		public void onButtonStateChange(final int id, final int state) throws RemoteException {
			onFourBeatStateChange(id, state);
		}
	};
	
	protected abstract void onFourBeatConnected();
	protected abstract void onFourBeatStateChange(int id, int state);
}
