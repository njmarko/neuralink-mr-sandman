package sbnz.mrsandman.neuralinkapp.model;

import sbnz.mrsandman.neuralinkapp.model.enums.SleepPhase;

public class Sleep {

	private int brainwaveFrequency;
	private SleepPhase sleepPhase;

	public Sleep() {
		super();
	}

	public Sleep(int brainwaveFrequency, SleepPhase sleepPhase) {
		super();
		this.brainwaveFrequency = brainwaveFrequency;
		this.sleepPhase = sleepPhase;
	}

	public int getBrainwaveFrequency() {
		return brainwaveFrequency;
	}

	public SleepPhase getSleepPhase() {
		return sleepPhase;
	}

	@Override
	public String toString() {
		return "Sleep [brainwaveFrequency=" + brainwaveFrequency + ", sleepPhase=" + sleepPhase + "]";
	}

}
