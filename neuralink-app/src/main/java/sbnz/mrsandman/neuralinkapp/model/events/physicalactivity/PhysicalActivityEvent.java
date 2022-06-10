package sbnz.mrsandman.neuralinkapp.model.events.physicalactivity;

import java.io.Serializable;
import java.time.LocalDateTime;

import org.kie.api.definition.type.Expires;
import org.kie.api.definition.type.Role;

import sbnz.mrsandman.neuralinkapp.model.events.BaseEvent;

@Role(Role.Type.EVENT)
@Expires("12h")
public class PhysicalActivityEvent extends BaseEvent implements Serializable {

	private static final long serialVersionUID = 1L;

	private double value;

	private LocalDateTime timeStamp;

	public PhysicalActivityEvent() {
		super();
	}

	public PhysicalActivityEvent(double value, LocalDateTime timeStamp) {
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
		return "PhysicalActivityEvent [value=" + value + ", timeStamp=" + timeStamp + "]";
	}

}
