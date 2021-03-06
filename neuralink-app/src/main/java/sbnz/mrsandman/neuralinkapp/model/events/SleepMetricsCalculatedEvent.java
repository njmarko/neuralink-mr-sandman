package sbnz.mrsandman.neuralinkapp.model.events;

import java.io.Serializable;

import org.kie.api.definition.type.Expires;
import org.kie.api.definition.type.Role;
import org.kie.api.definition.type.Role.Type;

@Role(Type.EVENT)
@Expires("12h")
public class SleepMetricsCalculatedEvent extends BaseEvent implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

}
