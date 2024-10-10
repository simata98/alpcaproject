package alpcaproject.domain;

import alpcaproject.domain.*;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

//<<< PoEAA / Repository
@RepositoryRestResource(collectionResourceRel = "families", path = "families")
public interface FamilyRepository
    extends PagingAndSortingRepository<Family, Long> {}
