package sbnz.mrsandman.neuralinkapp.model.events.somnabulism;

import java.io.Serializable;

import org.kie.api.definition.type.Expires;
import org.kie.api.definition.type.Role;
import org.kie.api.definition.type.Role.Type;

import sbnz.mrsandman.neuralinkapp.model.events.BaseEvent;

@Role(Type.EVENT)
@Expires("16h")
public class SomnabulismDetectedEvent extends BaseEvent implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

}
