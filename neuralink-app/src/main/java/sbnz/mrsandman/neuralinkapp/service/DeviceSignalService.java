package sbnz.mrsandman.neuralinkapp.service;

import sbnz.mrsandman.neuralinkapp.dto.DeviceSendSignalRequest;

public interface DeviceSignalService {
	
	void onSignalReceived(DeviceSendSignalRequest request);

}
