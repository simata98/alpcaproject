package alpcaproject.infra;

import alpcaproject.domain.*;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.RepresentationModelProcessor;
import org.springframework.stereotype.Component;

@Component
public class LocationHateoasProcessor
    implements RepresentationModelProcessor<EntityModel<Location>> {

    @Override
    public EntityModel<Location> process(EntityModel<Location> model) {
        return model;
    }
}
