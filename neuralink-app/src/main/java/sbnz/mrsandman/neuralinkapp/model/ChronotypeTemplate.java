package sbnz.mrsandman.neuralinkapp.model;

import sbnz.mrsandman.neuralinkapp.model.enums.Chronotype;

public class ChronotypeTemplate {

	private int lowerTimeBound;
	private int upperTimeBound;
	private boolean isLightSleep;
	private Chronotype chronotype;

	public ChronotypeTemplate() {
		super();
	}

	public ChronotypeTemplate(int lowerTimeBound, int upperTimeBound, boolean isLightSleep, Chronotype chronotype) {
		super();
		this.lowerTimeBound = lowerTimeBound;
		this.upperTimeBound = upperTimeBound;
		this.isLightSleep = isLightSleep;
		this.chronotype = chronotype;
	}

	public Chronotype getChronotype() {
		return chronotype;
	}

	public void setChronotype(Chronotype chronotype) {
		this.chronotype = chronotype;
	}

	public int getLowerTimeBound() {
		return lowerTimeBound;
	}

	public void setLowerTimeBound(int lowerTimeBound) {
		this.lowerTimeBound = lowerTimeBound;
	}

	public int getUpperTimeBound() {
		return upperTimeBound;
	}

	public void setUpperTimeBound(int upperTimeBound) {
		this.upperTimeBound = upperTimeBound;
	}

	public boolean isLightSleep() {
		return isLightSleep;
	}

	public void setLightSleep(boolean isLightSleep) {
		this.isLightSleep = isLightSleep;
	}

	@Override
	public String toString() {
		return "ChronotypeTemplate [lowerTimeBound=" + lowerTimeBound + ", upperTimeBound=" + upperTimeBound
				+ ", isLightSleep=" + isLightSleep + "]";
	}

}
