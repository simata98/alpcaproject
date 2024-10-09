package alpcaproject.domain;

import alpcaproject.domain.*;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

//<<< PoEAA / Repository
@RepositoryRestResource(collectionResourceRel = "moves", path = "moves")
public interface MoveRepository
    extends PagingAndSortingRepository<Move, String> {}
