package sbnz.mrsandman.neuralinkapp.controller;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import sbnz.mrsandman.neuralinkapp.dto.DeviceSendSignalRequest;
import sbnz.mrsandman.neuralinkapp.dto.SignalReceivedResponse;

@Controller
public class DeviceSignalController {
	private final SimpMessagingTemplate simpMessagingTemplate;

	@Autowired
	public DeviceSignalController(SimpMessagingTemplate simpMessagingTemplate) {
		super();
		this.simpMessagingTemplate = simpMessagingTemplate;
	}
	
	@MessageMapping({"send"})
	public void onSignalReceived(DeviceSendSignalRequest request) {
		System.out.println("GOT SIGNAL FROM DEVICE " + request.getDeviceId() + " FOR TYPE " + request.getSignalType() + " WITH VALUE " + request.getValue());
		SignalReceivedResponse response = new SignalReceivedResponse(request.getSignalType(), request.getValue(), LocalDateTime.now());
		this.simpMessagingTemplate.convertAndSend("/live-signals", response);
	}
}
