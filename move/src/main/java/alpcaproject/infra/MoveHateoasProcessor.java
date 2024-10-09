package alpcaproject.infra;

import alpcaproject.domain.*;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.RepresentationModelProcessor;
import org.springframework.stereotype.Component;

@Component
public class MoveHateoasProcessor
    implements RepresentationModelProcessor<EntityModel<Move>> {

    @Override
    public EntityModel<Move> process(EntityModel<Move> model) {
        return model;
    }
}
