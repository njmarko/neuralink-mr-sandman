package sbnz.mrsandman.neuralinkapp.bus;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import sbnz.mrsandman.neuralinkapp.dto.SignalReceivedResponse;
import sbnz.mrsandman.neuralinkapp.model.events.BaseEvent;

@Service
public class EventBus {
	private final SimpMessagingTemplate simpMessagingTemplate;
	
	@Autowired
	public EventBus(SimpMessagingTemplate simpMessagingTemplate) {
		this.simpMessagingTemplate = simpMessagingTemplate;
	}

	public void broadcastSignal(SignalReceivedResponse signal) {
		this.simpMessagingTemplate.convertAndSend("/live-signals", signal);
	}


	public void logEvent(BaseEvent event) {
		this.simpMessagingTemplate.convertAndSend("/live-events", event);
	}
	
}
