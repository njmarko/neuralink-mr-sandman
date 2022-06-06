package sbnz.mrsandman.neuralinkapp.model.events.heartrate;

import java.io.Serializable;

import org.kie.api.definition.type.Expires;
import org.kie.api.definition.type.Role;
import org.kie.api.definition.type.Role.Type;

@Role(Type.EVENT)
@Expires("5m")
public class HeartRateLoweredEvent implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	private double value;

	public HeartRateLoweredEvent() {
		super();
	}

	public HeartRateLoweredEvent(double value) {
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
		return "HeartRateLoweredEvent [value=" + value + "]";
	}

}
