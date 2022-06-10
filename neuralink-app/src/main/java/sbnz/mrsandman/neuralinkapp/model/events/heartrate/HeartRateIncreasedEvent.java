package sbnz.mrsandman.neuralinkapp.model.events.heartrate;

import java.io.Serializable;

import org.kie.api.definition.type.Expires;
import org.kie.api.definition.type.Role;

import sbnz.mrsandman.neuralinkapp.model.events.BaseEvent;

@Role(Role.Type.EVENT)
@Expires("30m")
public class HeartRateIncreasedEvent extends BaseEvent implements Serializable {
	private static final long serialVersionUID = 1L;

	private double value;

	public HeartRateIncreasedEvent() {
		super();
	}

	public HeartRateIncreasedEvent(double value) {
		super();
		this.value = value;
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
		return "HeartRateIncreasedEvent [value=" + value + "]";
	}

}
