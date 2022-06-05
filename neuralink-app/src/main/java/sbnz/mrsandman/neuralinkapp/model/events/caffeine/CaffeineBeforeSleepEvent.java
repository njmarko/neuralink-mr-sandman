package sbnz.mrsandman.neuralinkapp.model.events.caffeine;

import java.io.Serializable;

import org.kie.api.definition.type.Expires;
import org.kie.api.definition.type.Role;

@Role(Role.Type.EVENT)
@Expires("12h")
public class CaffeineBeforeSleepEvent implements Serializable {

	private static final long serialVersionUID = -8288082012667244065L;

}
