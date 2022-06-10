package sbnz.mrsandman.neuralinkapp.model.events.movement;

import java.io.Serializable;

import org.kie.api.definition.type.Expires;
import org.kie.api.definition.type.Role;
import org.kie.api.definition.type.Role.Type;

import sbnz.mrsandman.neuralinkapp.model.events.BaseEvent;

@Role(Type.EVENT)
@Expires("5m")
public class MovementDetectedEvent extends BaseEvent implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

}
