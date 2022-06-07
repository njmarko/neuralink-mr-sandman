package sbnz.mrsandman.neuralinkapp.model.events;

import java.io.Serializable;

import org.kie.api.definition.type.Expires;
import org.kie.api.definition.type.Role;
import org.kie.api.definition.type.Role.Type;

@Role(Type.EVENT)
@Expires("1m")
public class UserMovementEvent implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

}
