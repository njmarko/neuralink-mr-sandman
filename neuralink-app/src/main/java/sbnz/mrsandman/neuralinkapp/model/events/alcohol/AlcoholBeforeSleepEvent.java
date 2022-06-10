package sbnz.mrsandman.neuralinkapp.model.events.alcohol;

import java.io.Serializable;
import java.time.LocalDateTime;

import org.kie.api.definition.type.Expires;
import org.kie.api.definition.type.Role;

import sbnz.mrsandman.neuralinkapp.model.events.BaseEvent;

@Role(Role.Type.EVENT)
//@Timestamp("executionTime")
@Expires("6h")
public class AlcoholBeforeSleepEvent extends BaseEvent implements Serializable {

	private static final long serialVersionUID = 956194426874483654L;

//	private Date executionTime;

	private double value;

	private LocalDateTime timeStamp;

	public AlcoholBeforeSleepEvent() {
		super();
	}

	public AlcoholBeforeSleepEvent(double value) {
		super();
		this.value = value;
	}

	public AlcoholBeforeSleepEvent(double value, LocalDateTime timeStamp) {
		super();
		this.value = value;
		this.timeStamp = timeStamp;
	}

	public LocalDateTime getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(LocalDateTime timeStamp) {
		this.timeStamp = timeStamp;
	}

	public double getValue() {
		return value;
	}

	public void setValue(double value) {
		this.value = value;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		return "AlcoholBeforeSleepEvent [value=" + value + ", timeStamp=" + timeStamp + "]";
	}

}
