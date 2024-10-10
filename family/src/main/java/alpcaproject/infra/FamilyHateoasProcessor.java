package alpcaproject.infra;

import alpcaproject.domain.*;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.RepresentationModelProcessor;
import org.springframework.stereotype.Component;

@Component
public class FamilyHateoasProcessor
    implements RepresentationModelProcessor<EntityModel<Family>> {

    @Override
    public EntityModel<Family> process(EntityModel<Family> model) {
        return model;
    }
}
