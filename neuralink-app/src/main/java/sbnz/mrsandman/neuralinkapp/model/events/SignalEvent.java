package sbnz.mrsandman.neuralinkapp.model.events;

import java.io.Serializable;

import org.kie.api.definition.type.Expires;
import org.kie.api.definition.type.Role;

import sbnz.mrsandman.neuralinkapp.model.enums.SignalType;

@Role(Role.Type.EVENT)
@Expires("10s")
public class SignalEvent extends BaseEvent implements Serializable {
	private static final long serialVersionUID = 1L;

	private double value;
	private SignalType signalType;

	public SignalEvent() {
		super();
	}
	
	public SignalEvent(SignalType signalType) {
		super();
		this.value = 1.0d;
		this.signalType = signalType;
	}

	public SignalEvent(double value, SignalType signalType) {
		super();
		this.value = value;
		this.signalType = signalType;
	}

	public double getValue() {
		return value;
	}

	public void setValue(double value) {
		this.value = value;
	}

	public SignalType getSignalType() {
		return signalType;
	}

	public void setSignalType(SignalType signalType) {
		this.signalType = signalType;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		return "SignalEvent [value=" + value + ", signalType=" + signalType + "]";
	}

}
