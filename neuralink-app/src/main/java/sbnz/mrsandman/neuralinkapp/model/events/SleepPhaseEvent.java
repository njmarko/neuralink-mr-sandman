package sbnz.mrsandman.neuralinkapp.model.events;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

import org.kie.api.definition.type.Expires;
import org.kie.api.definition.type.Role;
import org.kie.api.definition.type.Timestamp;

import sbnz.mrsandman.neuralinkapp.model.enums.SleepPhase;

@Role(Role.Type.EVENT)
//@Timestamp("executionTime")
@Expires("30m")
public class SleepPhaseEvent implements Serializable {
	private static final long serialVersionUID = 1L;

//	private LocalDateTime executionTime;
	private SleepPhase sleepPhase;

	public SleepPhaseEvent() {
		super();
	}

	public SleepPhaseEvent(SleepPhase sleepPhase) {
		super();
		this.sleepPhase = sleepPhase;
	}

	public SleepPhase getSleepPhase() {
		return sleepPhase;
	}

	public void setSleepPhase(SleepPhase sleepPhase) {
		this.sleepPhase = sleepPhase;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		return "SleepPhaseEvent [sleepPhase=" + sleepPhase + "]";
	}

}
