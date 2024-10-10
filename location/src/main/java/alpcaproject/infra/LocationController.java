package alpcaproject.infra;

import alpcaproject.domain.*;
import java.util.Optional;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//<<< Clean Arch / Inbound Adaptor

@RestController
@RequestMapping(value="/locations")
@Transactional
public class LocationController {

    @PostMapping
    public void registerLoc(@RequestBody Location location){
        location.register();
    }

    @DeleteMapping
    public void deleteLoc(@RequestBody Location location){
        location.delete();
    }
}
//>>> Clean Arch / Inbound Adaptor
