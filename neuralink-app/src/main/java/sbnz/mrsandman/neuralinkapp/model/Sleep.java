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

	public void addSleepPhase(SleepStage sleepStage) {
		// Terminate previous sleep phase
		if (sleepStages.size() > 0) {
			SleepStage last = sleepStages.get(sleepStages.size() - 1);
			last.setEnd(new Date());
		}
		// Append the new sleep phase
		this.sleepStages.add(sleepStage);
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
