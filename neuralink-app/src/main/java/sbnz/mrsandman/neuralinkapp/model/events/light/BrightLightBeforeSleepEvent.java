package sbnz.mrsandman.neuralinkapp.model.events.light;

import java.io.Serializable;
import java.time.LocalDateTime;

import org.kie.api.definition.type.Expires;
import org.kie.api.definition.type.Role;

@Role(Role.Type.EVENT)
@Expires("12h")
public class BrightLightBeforeSleepEvent implements Serializable {

	private static final long serialVersionUID = -2686594610495110819L;

	private double value;

	private LocalDateTime timeStamp;

	public BrightLightBeforeSleepEvent() {
		super();
	}

	public BrightLightBeforeSleepEvent(double value, LocalDateTime timeStamp) {
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
		return "BrightLightBeforeSleepEvent [value=" + value + ", timeStamp=" + timeStamp + "]";
	}

}
