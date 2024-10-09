package alpcaproject.infra;

import alpcaproject.domain.*;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.RepresentationModelProcessor;
import org.springframework.stereotype.Component;

@Component
public class CutomerHateoasProcessor
    implements RepresentationModelProcessor<EntityModel<Cutomer>> {

    @Override
    public EntityModel<Cutomer> process(EntityModel<Cutomer> model) {
        return model;
    }
}
