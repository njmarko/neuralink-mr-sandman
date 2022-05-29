package sbnz.mrsandman.neuralinkapp.model.events.beginsleeping;

import java.io.Serializable;

import org.kie.api.definition.type.Expires;
import org.kie.api.definition.type.Role;
import org.kie.api.definition.type.Role.Type;

@Role(Type.EVENT)
@Expires("10m")
public class HeartRateLoweredEvent implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	
}
