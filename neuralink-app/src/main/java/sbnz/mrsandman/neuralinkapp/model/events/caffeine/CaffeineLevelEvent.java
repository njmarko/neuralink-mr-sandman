package sbnz.mrsandman.neuralinkapp.model.events.caffeine;

import java.io.Serializable;

import org.kie.api.definition.type.Expires;
import org.kie.api.definition.type.Role;

import sbnz.mrsandman.neuralinkapp.model.events.BaseEvent;

@Role(Role.Type.EVENT)
@Expires("30m")
public class CaffeineLevelEvent extends BaseEvent implements Serializable {
	private static final long serialVersionUID = 1L;

	private double milligrams;

	public CaffeineLevelEvent(double milligrams) {
		super();
		this.milligrams = milligrams;
	}

	public double getMilligrams() {
		return milligrams;
	}

	public void setMilligrams(double milligrams) {
		this.milligrams = milligrams;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		return "CaffeineLevelEvent [milligrams=" + milligrams + "]";
	}

}
