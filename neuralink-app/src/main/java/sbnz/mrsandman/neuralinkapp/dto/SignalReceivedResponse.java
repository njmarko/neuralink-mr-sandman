package sbnz.mrsandman.neuralinkapp.dto;

import java.time.LocalDateTime;

import sbnz.mrsandman.neuralinkapp.model.enums.SignalType;

public class SignalReceivedResponse {
	private SignalType signalType;
	private Double value;
	private LocalDateTime timestamp;

	public SignalReceivedResponse() {
		super();
	}

	public SignalReceivedResponse(SignalType signalType, Double value, LocalDateTime timestamp) {
		super();
		this.signalType = signalType;
		this.value = value;
		this.timestamp = timestamp;
	}

	public SignalType getSignalType() {
		return signalType;
	}

	public void setSignalType(SignalType signalType) {
		this.signalType = signalType;
	}

	public Double getValue() {
		return value;
	}

	public void setValue(Double value) {
		this.value = value;
	}

	public LocalDateTime getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(LocalDateTime timestamp) {
		this.timestamp = timestamp;
	}

}
