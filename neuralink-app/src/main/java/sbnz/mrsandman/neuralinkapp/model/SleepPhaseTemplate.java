package sbnz.mrsandman.neuralinkapp.model;

import sbnz.mrsandman.neuralinkapp.model.enums.SleepPhase;

public class SleepPhaseTemplate {

	private int minFrequency;
	private int maxFrequency;
	private SleepPhase sleepPhase;

	public SleepPhaseTemplate() {
		super();
	}

	public SleepPhaseTemplate(int minFrequency, int maxFrequency, SleepPhase sleepPhase) {
		super();
		this.minFrequency = minFrequency;
		this.maxFrequency = maxFrequency;
		this.sleepPhase = sleepPhase;
	}

	public int getMinFrequency() {
		return minFrequency;
	}

	public void setMinFrequency(int minFrequency) {
		this.minFrequency = minFrequency;
	}

	public int getMaxFrequency() {
		return maxFrequency;
	}

	public void setMaxFrequency(int maxFrequency) {
		this.maxFrequency = maxFrequency;
	}

	public SleepPhase getSleepPhase() {
		return sleepPhase;
	}

	public void setSleepPhase(SleepPhase sleepPhase) {
		this.sleepPhase = sleepPhase;
	}

	@Override
	public String toString() {
		return "SleepPhaseTemplate [minFrequency=" + minFrequency + ", maxFrequency=" + maxFrequency + ", sleepPhase="
				+ sleepPhase + "]";
	}

}
