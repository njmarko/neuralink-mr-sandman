package sbnz.mrsandman.neuralinkapp.model;

import java.util.Date;

import sbnz.mrsandman.neuralinkapp.model.enums.SleepPhase;

public class SleepStage {
	private SleepPhase sleepPhase;
	private Date start;
	private Date end;

	public SleepStage() {

	}

	public SleepStage(SleepPhase sleepPhase) {
		this(sleepPhase, new Date(), null);
	}

	public SleepStage(SleepPhase sleepPhase, Date start, Date end) {
		super();
		this.sleepPhase = sleepPhase;
		this.start = start;
		this.end = end;
	}

	public SleepPhase getSleepPhase() {
		return sleepPhase;
	}

	public void setSleepPhase(SleepPhase sleepPhase) {
		this.sleepPhase = sleepPhase;
	}

	public Date getStart() {
		return start;
	}

	public void setStart(Date start) {
		this.start = start;
	}

	public Date getEnd() {
		return end;
	}

	public void setEnd(Date end) {
		this.end = end;
	}

}
