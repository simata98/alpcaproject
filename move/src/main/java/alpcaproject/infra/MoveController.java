package alpcaproject.infra;

import alpcaproject.domain.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;

import alpcaproject.util.NaverMapService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.HashMap;

//<<< Clean Arch / Inbound Adaptor

@RequiredArgsConstructor
@RestController
//@RequestMapping(value="/moves")
@Transactional
public class MoveController {

    private final NaverMapService naverMapService;
    private final MoveRepository moveRepository;

    @RequestMapping(
            value = "/moves/startmove",
            method = RequestMethod.POST,
            produces = "application/json;charset=UTF-8"
    )
    public void startMove(
            HttpServletRequest request,
            HttpServletResponse response,
            @RequestBody StartMoveCommand startMoveCommand
    ) throws Exception {
        System.out.println("##### /move/startMove  called #####");
        Move move = new Move();
        move.startMove(startMoveCommand);
    }

    @RequestMapping(
            value = "/moves/updatemove",
            method = RequestMethod.POST,
            produces = "application/json;charset=UTF-8"
    )
    public void updateMove(
            HttpServletRequest request,
            HttpServletResponse response,
            @RequestBody UpdateMoveCommand updateMoveCommand
    ) throws Exception {
        System.out.println("##### /move/updateMove  called #####");
        Move move = new Move();
        move.updateMove(updateMoveCommand);
    }

    @RequestMapping(
            value = "/moves/endmove",
            method = RequestMethod.POST,
            produces = "application/json;charset=UTF-8"
    )
    public void endMove(
            HttpServletRequest request,
            HttpServletResponse response,
            @RequestBody EndMoveCommand endMoveCommand
    ) throws Exception {
        System.out.println("##### /move/endMove  called #####");
        Move move = new Move();
        move.endMove(endMoveCommand);
    }

    @RequestMapping(
            value = "/moves/cancelmove",
            method = RequestMethod.POST,
            produces = "application/json;charset=UTF-8"
    )
    public void cancelMove(
            HttpServletRequest request,
            HttpServletResponse response,
            @RequestBody CancelMoveCommand cancelMoveCommand
    ) throws Exception {
        System.out.println("##### /move/cancelMove  called #####");
        Move move = new Move();
        move.cancelMove(cancelMoveCommand);
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
