package sbnz.mrsandman.neuralinkapp.model.events.light;

import java.io.Serializable;

import org.kie.api.definition.type.Expires;
import org.kie.api.definition.type.Role;

@Role(Role.Type.EVENT)
@Expires("12h")
public class BrightLightBeforeSleepEvent implements Serializable {

	private static final long serialVersionUID = -2686594610495110819L;

}
