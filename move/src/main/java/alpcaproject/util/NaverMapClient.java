package alpcaproject.util;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "naverMapClient", url = "https://naveropenapi.apigw.ntruss.com/map-direction-15/v1/driving")
public interface NaverMapClient {

    @GetMapping
    NaverMapsAPIDTO.ResponseDTO getDirections(
            @RequestParam("start") String start,
            @RequestParam("goal") String goal,
            @RequestParam("option") String option,
            @RequestHeader("x-ncp-apigw-api-key-id") String naverMapsAPIClientId,
            @RequestHeader("x-ncp-apigw-api-key") String naverMapsAPIClientToken
    );
}