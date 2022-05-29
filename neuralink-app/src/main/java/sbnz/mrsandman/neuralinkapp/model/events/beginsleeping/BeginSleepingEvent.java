package sbnz.mrsandman.neuralinkapp.model.events.beginsleeping;

import java.io.Serializable;

import org.kie.api.definition.type.Expires;
import org.kie.api.definition.type.Role;

@Role(Role.Type.EVENT)
@Expires("10m")
public class BeginSleepingEvent implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = -2510302734020606743L;

}
