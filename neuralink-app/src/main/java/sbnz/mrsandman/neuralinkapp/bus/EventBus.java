package sbnz.mrsandman.neuralinkapp.bus;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class EventBus {
	private final SimpMessagingTemplate simpMessagingTemplate;
	
	@Autowired
	public EventBus(SimpMessagingTemplate simpMessagingTemplate) {
		this.simpMessagingTemplate = simpMessagingTemplate;
	}

	public void broadcastSignal(Object signal) {
		this.simpMessagingTemplate.convertAndSend("/live-signals", signal);
	}


	public void logEvent(Object event) {
		this.simpMessagingTemplate.convertAndSend("/live-events", event);
	}
	
}
