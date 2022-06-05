package sbnz.mrsandman.neuralinkapp.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Sleep implements Serializable {

	private static final long serialVersionUID = 1L;

	private Date startTime;
	private Date endTime;
	private List<SleepStage> sleepStages;

	public Sleep() {
		this.sleepStages = new ArrayList<SleepStage>();
		this.startTime = new Date();
	}

	public Sleep(Date startTime) {
		this();
		this.startTime = startTime;
	}

	public Sleep(Date startTime, Date endTime, List<SleepStage> sleepStages) {
		super();
		this.startTime = startTime;
		this.endTime = endTime;
		this.sleepStages = sleepStages;
	}
	
	public void wakeUp() {
		this.finalizeLastStage();
		this.endTime = new Date();
	}

	public void addSleepStage(SleepStage sleepStage) {
		// Terminate previous sleep phase
		this.finalizeLastStage();
		// Append the new sleep phase
		this.sleepStages.add(sleepStage);
	}
	
	private void finalizeLastStage() {
		if (sleepStages.size() > 0) {
			SleepStage last = sleepStages.get(sleepStages.size() - 1);
			last.setEndTime(new Date());
		}
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

	public List<SleepStage> getSleepStages() {
		return sleepStages;
	}

	public void setSleepStages(List<SleepStage> sleepStages) {
		this.sleepStages = sleepStages;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
