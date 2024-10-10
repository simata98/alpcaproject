package alpcaproject.infra;

import javax.transaction.Transactional;

import alpcaproject.util.NaverMapService;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import alpcaproject.domain.MoveRepository;
import lombok.RequiredArgsConstructor;

//<<< Clean Arch / Inbound Adaptor

@RequiredArgsConstructor
@RequiredArgsConstructor
@RestController
@Transactional
public class MoveController {

    @Autowired
    MoveRepository moveRepository;

}
//>>> Clean Arch / Inbound Adaptor
