package sbnz.mrsandman.neuralinkapp.model;

import java.util.Date;

import sbnz.mrsandman.neuralinkapp.model.enums.SleepPhase;

public class SleepStage {
	private SleepPhase sleepPhase;
	private Date startTime;
	private Date endTime;

	public SleepStage() {

	}

	public SleepStage(SleepPhase sleepPhase) {
		this(sleepPhase, new Date(), null);
	}

	public SleepStage(SleepPhase sleepPhase, Date start, Date end) {
		super();
		this.sleepPhase = sleepPhase;
		this.startTime = start;
		this.endTime = end;
	}

	public SleepPhase getSleepPhase() {
		return sleepPhase;
	}

	public void setSleepPhase(SleepPhase sleepPhase) {
		this.sleepPhase = sleepPhase;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

}
