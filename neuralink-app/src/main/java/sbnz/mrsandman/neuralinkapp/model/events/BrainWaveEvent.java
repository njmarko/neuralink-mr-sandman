package sbnz.mrsandman.neuralinkapp.model.events;

import org.kie.api.definition.type.Expires;
import org.kie.api.definition.type.Role;
import java.io.Serializable;

@Role(Role.Type.EVENT)
@Expires("20s")
public class BrainWaveEvent implements Serializable {

	private static final long serialVersionUID = 1L;

	private int frequency;

	public BrainWaveEvent() {
		super();
	}

	public BrainWaveEvent(int frequency) {
		super();
		this.frequency = frequency;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public int getFrequency() {
		return frequency;
	}

	@Override
	public String toString() {
		return "BrainWaveEvent [frequency=" + frequency + "]";
	}

}
