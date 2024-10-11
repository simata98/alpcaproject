package alpcaproject.infra;

import alpcaproject.domain.*;
import java.util.List;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;

@CrossOrigin(origins = "http://localhost:5173")
@RepositoryRestResource(collectionResourceRel = "moveviews", path = "moveviews")
public interface MoveviewRepository
    extends PagingAndSortingRepository<Moveview, String> {}
