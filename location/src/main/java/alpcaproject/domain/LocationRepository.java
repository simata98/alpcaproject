package alpcaproject.domain;

import alpcaproject.domain.*;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

//<<< PoEAA / Repository
//@RepositoryRestResource(collectionResourceRel = "locations", path = "locations")
@Repository
public interface LocationRepository
    extends PagingAndSortingRepository<Location, String> {}
