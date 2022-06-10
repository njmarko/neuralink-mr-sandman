package sbnz.mrsandman.neuralinkapp.model.events;

public abstract class BaseEvent {
	
	public String getEventType() {
		return this.getClass().getSimpleName();
	}

}
