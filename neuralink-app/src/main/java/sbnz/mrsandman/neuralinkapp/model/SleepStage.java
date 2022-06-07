package sbnz.mrsandman.neuralinkapp.model;

import java.util.Date;

import sbnz.mrsandman.neuralinkapp.model.enums.SleepPhase;

public class SleepStage {
	private Sleep sleep;
	private SleepPhase sleepPhase;
	private Date startTime;
	private Date endTime;
	private double stageDuration;

	public SleepStage() {

	}

	public SleepStage(Sleep sleep, SleepPhase sleepPhase) {
		this(sleep, sleepPhase, new Date(), null);
	}
	
	public SleepStage(Sleep sleep, SleepPhase sleepPhase, double duration) {
		this(sleep, sleepPhase, new Date(), null);
		this.stageDuration = duration;
	}
	
	public SleepStage(Sleep sleep, SleepPhase sleepPhase, Date start) {
		this(sleep, sleepPhase, start, null);
	}

	public SleepStage(Sleep sleep, SleepPhase sleepPhase, Date start, Date end) {
		super();
		this.setSleep(sleep);
		this.sleepPhase = sleepPhase;
		this.startTime = start;
		this.endTime = end;
	}
	
	public void endStage() {
		this.setEndTime(new Date());
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
		long difference = endTime.getTime() - startTime.getTime();
		this.stageDuration = (difference / (1000 * 60)) % 60;
	}

	public double getStageDuration() {
		return stageDuration;

	}

	public void setStageDuration(double stageDuration) {
		this.stageDuration = stageDuration;

	}

	public Sleep getSleep() {
		return sleep;
		
	}

	public void setSleep(Sleep sleep) {
		this.sleep = sleep;
		
	}

}
