package com.pigmal.android.fourbeat.service;

import com.pigmal.android.fourbeat.service.IFourBeatServiceListener;

interface IFourBeatService {
	void registerCallback(IFourBeatServiceListener callback);
	void unregisterCallback(IFourBeatServiceListener callback);
}
