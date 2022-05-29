package sbnz.mrsandman.neuralinkapp.model.events.beginsleeping;

import java.io.Serializable;

import org.kie.api.definition.type.Expires;
import org.kie.api.definition.type.Role;

@Role(Role.Type.EVENT)
@Expires("10m")
public class BodyTemperatureChangedEvent implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 1751902038245481041L;

	private int diff;

	public int getDiff() {
		return diff;
	}

	public void setDiff(int diff) {
		this.diff = diff;
	}

	public BodyTemperatureChangedEvent(int diff) {
		super();
		this.diff = diff;
	}

	public BodyTemperatureChangedEvent() {
		super();
	}

}
