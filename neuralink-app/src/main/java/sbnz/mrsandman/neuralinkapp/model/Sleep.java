package sbnz.mrsandman.neuralinkapp.model;

import java.io.Serializable;

import sbnz.mrsandman.neuralinkapp.model.enums.SleepPhase;

public class Sleep implements Serializable {
	
	private static final long serialVersionUID = 1L;

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
	
	

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public void setBrainwaveFrequency(int brainwaveFrequency) {
		this.brainwaveFrequency = brainwaveFrequency;
	}

	public void setSleepPhase(SleepPhase sleepPhase) {
		this.sleepPhase = sleepPhase;
	}

	@Override
	public String toString() {
		return "Sleep [brainwaveFrequency=" + brainwaveFrequency + ", sleepPhase=" + sleepPhase + "]";
	}

}