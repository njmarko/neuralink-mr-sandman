package sbnz.mrsandman.neuralinkapp.model.events.physicalactivity;

import java.io.Serializable;

import org.kie.api.definition.type.Expires;
import org.kie.api.definition.type.Role;

@Role(Role.Type.EVENT)
@Expires("12h")
public class PhysicalActivityEvent implements Serializable {

	private static final long serialVersionUID = 1L;
}
