package sbnz.mrsandman.neuralinkapp.model.events.heartrate;

import java.io.Serializable;

import org.kie.api.definition.type.Expires;
import org.kie.api.definition.type.Role;

import sbnz.mrsandman.neuralinkapp.model.events.BaseEvent;

@Role(Role.Type.EVENT)
@Expires("10m")
public class HeartBeatEvent extends BaseEvent implements Serializable{
	private static final long serialVersionUID = 1L;
}
