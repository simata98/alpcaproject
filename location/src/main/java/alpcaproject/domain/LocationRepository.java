package alpcaproject.domain;

import alpcaproject.domain.*;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

//<<< PoEAA / Repository
@RepositoryRestResource(collectionResourceRel = "locations", path = "locations")
public interface LocationRepository
    extends PagingAndSortingRepository<Location, String> {}
