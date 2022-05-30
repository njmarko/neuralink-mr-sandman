package sbnz.mrsandman.neuralinkapp.model.events.temperature;

import java.io.Serializable;

import org.kie.api.definition.type.Expires;
import org.kie.api.definition.type.Role;

@Role(Role.Type.EVENT)
@Expires("30m")
public class RaisedTemperatureEvent implements Serializable{

	private static final long serialVersionUID = 1L;

}
