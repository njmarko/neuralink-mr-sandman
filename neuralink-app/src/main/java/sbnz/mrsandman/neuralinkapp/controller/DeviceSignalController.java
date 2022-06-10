package sbnz.mrsandman.neuralinkapp.controller;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

import sbnz.mrsandman.neuralinkapp.bus.EventBus;
import sbnz.mrsandman.neuralinkapp.dto.DeviceSendSignalRequest;
import sbnz.mrsandman.neuralinkapp.dto.SignalReceivedResponse;
import sbnz.mrsandman.neuralinkapp.service.DeviceSignalService;

@Controller
public class DeviceSignalController {
	private final EventBus eventBus;
	private final DeviceSignalService deviceSignalService;

	@Autowired
	public DeviceSignalController(DeviceSignalService deviceSignalService, EventBus eventBus) {
		this.eventBus = eventBus;
		this.deviceSignalService = deviceSignalService;
	}
	
	@MessageMapping({"send"})
	public void onSignalReceived(DeviceSendSignalRequest request) {
		System.out.println("GOT SIGNAL FROM DEVICE " + request.getDeviceId() + " FOR TYPE " + request.getSignalType() + " WITH VALUE " + request.getValue());
		SignalReceivedResponse response = new SignalReceivedResponse(request.getSignalType(), request.getValue(), LocalDateTime.now());
		this.deviceSignalService.onSignalReceived(request);
		this.eventBus.broadcastSignal(response);
	}
}
