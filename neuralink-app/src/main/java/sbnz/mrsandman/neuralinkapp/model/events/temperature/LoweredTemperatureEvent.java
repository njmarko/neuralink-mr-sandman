package sbnz.mrsandman.neuralinkapp.model.events.temperature;

import java.io.Serializable;

import org.kie.api.definition.type.Expires;
import org.kie.api.definition.type.Role;

@Role(Role.Type.EVENT)
@Expires("30m")
public class LoweredTemperatureEvent implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	private double value;

	public LoweredTemperatureEvent(double value) {
		super();
		this.value = value;
	}

	public LoweredTemperatureEvent() {
		super();
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
		return "LoweredTemperatureEvent [value=" + value + "]";
	}

}
