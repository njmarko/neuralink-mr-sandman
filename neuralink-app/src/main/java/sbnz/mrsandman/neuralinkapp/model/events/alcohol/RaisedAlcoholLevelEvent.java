package sbnz.mrsandman.neuralinkapp.model.events.alcohol;

import java.io.Serializable;

import org.kie.api.definition.type.Expires;
import org.kie.api.definition.type.Role;

@Role(Role.Type.EVENT)
@Expires("24h")
public class RaisedAlcoholLevelEvent implements Serializable {
	private static final long serialVersionUID = 1L;
}
