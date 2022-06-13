package sbnz.mrsandman.neuralinkapp.model.events;

import java.io.Serializable;

import org.kie.api.definition.type.Expires;
import org.kie.api.definition.type.Role;

@Role(Role.Type.EVENT)
@Expires("12h")
public class HabitRecomendationEvent implements Serializable {

	private static final long serialVersionUID = -5202068856870562360L;
	private String message;

	public HabitRecomendationEvent() {
		super();
	}

	public HabitRecomendationEvent(String message) {
		super();
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		return "HabbitRecomendationEvent [message=" + message + "]";
	}

}
