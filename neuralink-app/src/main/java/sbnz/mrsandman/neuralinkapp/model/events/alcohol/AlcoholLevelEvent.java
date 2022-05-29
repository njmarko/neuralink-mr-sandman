package sbnz.mrsandman.neuralinkapp.model.events.alcohol;

import java.io.Serializable;

import org.kie.api.definition.type.Expires;
import org.kie.api.definition.type.Role;

@Role(Role.Type.EVENT)
@Expires("30m")
public class AlcoholLevelEvent implements Serializable {
	private static final long serialVersionUID = 1L;

	private double permille;

	public AlcoholLevelEvent() {
		super();
	}

	public AlcoholLevelEvent(double permille) {
		super();
		this.permille = permille;
	}

	public double getPermille() {
		return permille;
	}

	public void setPermille(double permille) {
		this.permille = permille;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		return "AlcoholLevelEvent [permille=" + permille + "]";
	}

}
