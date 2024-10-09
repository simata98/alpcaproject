package alpcaproject.util;

import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class NaverMapService {

    private final NaverMapClient naverMapClient;

    // application.yaml의 api 호출 정보 주입
    @Value("${naver-maps.client-id}")
    private String naverMapsAPIClientId;

    @Value("${naver-maps.client-token}")
    private String naverMapsAPIClientToken;

    public NaverMapsAPIDTO.ResponseDTO getDirections(String start, String goal) {
        return naverMapClient.getDirections(start, goal, "traavoidtoll", naverMapsAPIClientId, naverMapsAPIClientToken);
    }
}

