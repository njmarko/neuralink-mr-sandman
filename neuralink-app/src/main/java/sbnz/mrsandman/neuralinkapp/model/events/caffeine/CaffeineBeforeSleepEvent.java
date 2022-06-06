package sbnz.mrsandman.neuralinkapp.model.events.caffeine;

import java.io.Serializable;
import java.time.LocalDateTime;

import org.kie.api.definition.type.Expires;
import org.kie.api.definition.type.Role;

@Role(Role.Type.EVENT)
@Expires("12h")
public class CaffeineBeforeSleepEvent implements Serializable {

	private static final long serialVersionUID = -8288082012667244065L;

	private double value;

	private LocalDateTime timeStamp;

	public CaffeineBeforeSleepEvent() {
		super();
	}

	public CaffeineBeforeSleepEvent(double value, LocalDateTime timeStamp) {
		super();
		this.value = value;
		this.timeStamp = timeStamp;
	}

	public double getValue() {
		return value;
	}

	public void setValue(double value) {
		this.value = value;
	}

	public LocalDateTime getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(LocalDateTime timeStamp) {
		this.timeStamp = timeStamp;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		return "CaffeineBeforeSleepEvent [value=" + value + ", timeStamp=" + timeStamp + "]";
	}

}
