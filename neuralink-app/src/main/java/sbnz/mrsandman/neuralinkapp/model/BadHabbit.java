package sbnz.mrsandman.neuralinkapp.model;

import java.io.Serializable;
import java.time.LocalDateTime;

import org.kie.api.definition.type.Role;

import sbnz.mrsandman.neuralinkapp.model.enums.BadHabbitType;

@Role(Role.Type.EVENT)
public class BadHabbit implements Serializable {

	private static final long serialVersionUID = 5555439210840244604L;

	// habbit type
	private BadHabbitType badHabbitType;

	// habbit time
	// event with long expire time instead?
	private LocalDateTime timeStamp;

	// habbit score
	private Double score;

	// user id
	// add if multiple users are to be supported

	public BadHabbit() {
		super();
	}

	public LocalDateTime getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(LocalDateTime timeStamp) {
		this.timeStamp = timeStamp;
	}

	public BadHabbit(BadHabbitType badHabbitType, LocalDateTime timeStamp, Double score) {
		super();
		this.badHabbitType = badHabbitType;
		this.timeStamp = timeStamp;
		this.score = score;
	}

	public BadHabbitType getBadHabbitType() {
		return badHabbitType;
	}

	public void setBadHabbitType(BadHabbitType badHabbitType) {
		this.badHabbitType = badHabbitType;
	}

	public Double getScore() {
		return score;
	}

	public void setScore(Double score) {
		this.score = score;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		return "BadHabbit [badHabbitType=" + badHabbitType + ", timeStamp=" + timeStamp + ", score=" + score + "]";
	}

}
