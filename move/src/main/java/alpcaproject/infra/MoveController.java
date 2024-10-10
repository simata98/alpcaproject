package alpcaproject.infra;

import alpcaproject.domain.*;

import java.util.Map;
import java.util.HashMap;
import java.util.Optional;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;

import alpcaproject.util.NaverMapService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//<<< Clean Arch / Inbound Adaptor

@RequiredArgsConstructor
@RestController
@RequestMapping(value="/moves")
@Transactional
public class MoveController {

    private final NaverMapService naverMapService;

    @PostMapping("/start")
    public ResponseEntity<Map<String, String>> start(@RequestBody MoveStarted moveStarted) {
        String moveId = Move.startMove(moveStarted);
        Map<String, String> response = new HashMap<>();
        response.put("moveId", moveId);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/update")
    public void update(@RequestBody MoveUpdated moveUpdated) {
        Move.updateMove(moveUpdated);
    }

    @GetMapping("/cancel")
    public void cancel(MoveCancelled moveCancelled) {
        Move.cancelMove(moveCancelled);
    }

    @GetMapping("/end")
    public void end(MoveEnded moveEnded) {
         Move.endMove(moveEnded);
    }
//    @GetMapping("/directions")
//    public String getDrivingDirections(
//            @RequestParam("start") String start,
//            @RequestParam("goal") String goal
//    ) {
//        return naverMapService.getDirections(start, goal);
//    }
}
//>>> Clean Arch / Inbound Adaptor
