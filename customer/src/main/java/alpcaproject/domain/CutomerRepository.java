package alpcaproject.domain;

import alpcaproject.domain.*;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

//<<< PoEAA / Repository
@RepositoryRestResource(collectionResourceRel = "cutomers", path = "cutomers")
public interface CutomerRepository
    extends PagingAndSortingRepository<Cutomer, String> {}
