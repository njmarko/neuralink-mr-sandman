package sbnz.mrsandman.neuralinkapp.model.events.light;

import java.io.Serializable;

import org.kie.api.definition.type.Expires;
import org.kie.api.definition.type.Role;

@Role(Role.Type.EVENT)
@Expires("30m")
public class BrightLightEvent implements Serializable {

	private static final long serialVersionUID = -8186195864346179721L;

}
