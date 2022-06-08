package sbnz.mrsandman.neuralinkapp.dto;

import java.util.UUID;

import sbnz.mrsandman.neuralinkapp.model.enums.SignalType;

public class DeviceSendSignalRequest {
	private UUID deviceId;
	private SignalType signalType;
	private Double value;

	public DeviceSendSignalRequest() {
		super();
	}

	public DeviceSendSignalRequest(UUID deviceId, SignalType signalType, Double value) {
		super();
		this.deviceId = deviceId;
		this.signalType = signalType;
		this.value = value;
	}

	public UUID getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(UUID deviceId) {
		this.deviceId = deviceId;
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

}
