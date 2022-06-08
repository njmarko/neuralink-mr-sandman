package sbnz.mrsandman.neuralinkapp.model;

import java.io.Serializable;
import java.util.Date;

public class Sleep implements Serializable {

	private static final long serialVersionUID = 1L;

	private Date startTime;
	private Date endTime;

	private Boolean efficient;
	private Double quality;

	public Sleep() {
		this.startTime = new Date();
	}

	public Sleep(Date startTime) {
		this();
		this.startTime = startTime;
	}

	public Sleep(Date startTime, Date endTime) {
		super();
		this.startTime = startTime;
		this.endTime = endTime;
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

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Double getQuality() {
		return quality;

	}

	public void setQuality(Double quality) {
		this.quality = quality;

	}

	public Boolean getEfficient() {
		return efficient;
		
	}

	public void setEfficient(Boolean efficient) {
		this.efficient = efficient;
		
	}

}
