package sbnz.mrsandman.neuralinkapp.model.events;

import java.io.Serializable;

import org.kie.api.definition.type.Expires;
import org.kie.api.definition.type.Role;
import org.kie.api.definition.type.Role.Type;

import sbnz.mrsandman.neuralinkapp.model.enums.MuscleTone;

@Role(Type.EVENT)
@Expires("10m")
public class MuscleToneChangedEvent extends BaseEvent implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	private MuscleTone muscleTone;

	public MuscleToneChangedEvent(MuscleTone muscleTone) {
		super();
		this.muscleTone = muscleTone;
	}

	public MuscleToneChangedEvent() {
		super();
	}

	public MuscleTone getMuscleTone() {
		return muscleTone;
	}

	public void setMuscleTone(MuscleTone muscleTone) {
		this.muscleTone = muscleTone;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
