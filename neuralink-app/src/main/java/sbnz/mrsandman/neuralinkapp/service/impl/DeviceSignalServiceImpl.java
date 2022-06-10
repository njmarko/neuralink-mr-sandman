package sbnz.mrsandman.neuralinkapp.service.impl;

import org.kie.api.runtime.KieSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sbnz.mrsandman.neuralinkapp.dto.DeviceSendSignalRequest;
import sbnz.mrsandman.neuralinkapp.model.events.SignalEvent;
import sbnz.mrsandman.neuralinkapp.service.DeviceSignalService;

@Service
public class DeviceSignalServiceImpl implements DeviceSignalService {
	private final KieSession kieSession;
	
	@Autowired
	public DeviceSignalServiceImpl(KieSession kieSession) {
		super();
		this.kieSession = kieSession;
	}

	@Override
	public void onSignalReceived(DeviceSendSignalRequest request) {
		SignalEvent signal = new SignalEvent(request.getValue(), request.getSignalType());
		kieSession.insert(signal);
		kieSession.fireAllRules();
	}

}
