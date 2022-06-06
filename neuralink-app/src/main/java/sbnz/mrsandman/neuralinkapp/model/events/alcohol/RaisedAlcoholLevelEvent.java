package sbnz.mrsandman.neuralinkapp.model.events.alcohol;

import java.io.Serializable;

import org.kie.api.definition.type.Expires;
import org.kie.api.definition.type.Role;

@Role(Role.Type.EVENT)
@Expires("24h")
public class RaisedAlcoholLevelEvent implements Serializable {
	private static final long serialVersionUID = 1L;

	private double value;

	public RaisedAlcoholLevelEvent() {
		super();
	}

	public RaisedAlcoholLevelEvent(double value) {
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
		return "RaisedAlcoholLevelEvent [value=" + value + "]";
	}

}
