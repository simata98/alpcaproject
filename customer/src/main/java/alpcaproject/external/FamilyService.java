package alpcaproject.external;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "family", url = "${api.url.family}")
public interface FamilyService {
    @RequestMapping(method = RequestMethod.POST, path = "/families")
    public void approveMember(@RequestBody Family family);
}
