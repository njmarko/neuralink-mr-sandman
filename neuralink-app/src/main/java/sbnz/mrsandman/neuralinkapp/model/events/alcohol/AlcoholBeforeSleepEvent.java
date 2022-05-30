package sbnz.mrsandman.neuralinkapp.model.events.alcohol;

import java.io.Serializable;

import org.kie.api.definition.type.Expires;
import org.kie.api.definition.type.Role;

@Role(Role.Type.EVENT)
@Expires("30m")
public class AlcoholBeforeSleepEvent implements Serializable {

	private static final long serialVersionUID = 956194426874483654L;
}
