package alpcaproject.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * 네이버 맵스 API를 호출하는 Http통신을 위한 utils
 */


@RequiredArgsConstructor
@Slf4j
@Component
public class HttpUtils {

    // application.yaml의 api 호출 정보 주입
    @Value("${naver-maps.url}")
    private String naverMapsAPIURL;

    @Value("${naver-maps.client-id}")
    private String naverMapsAPIClientId;

    @Value("${naver-maps.client-token}")
    private String naverMapsAPIClientToken;

    public NaverMapsAPIDTO.ResponseDTO getMapsAPIAsync(String uri)
            throws InterruptedException, ExecutionException, IOException {

        ObjectMapper objectMapper = new ObjectMapper();

        HttpClient client = HttpClient.newHttpClient();

        log.info("naverMapsAPIURL: {}", naverMapsAPIURL);
        log.info("naverMapsAPIClientId: {}", naverMapsAPIClientId);
        log.info("naverMapsAPIClientToken: {}", naverMapsAPIClientToken);

        HttpRequest request = HttpRequest.newBuilder()
                .setHeader("X-NCP-APIGW-API-KEY-ID", naverMapsAPIClientId)
                .setHeader("X-NCP-APIGW-API-KEY", naverMapsAPIClientToken)
                .uri(URI.create(naverMapsAPIURL + uri))
                .build();

        CompletableFuture<HttpResponse<String>> resFuture = client.sendAsync(request, HttpResponse.BodyHandlers.ofString());

        HttpResponse<String> httpResponse = resFuture.get();
        log.info("res: {}", httpResponse.toString());

        NaverMapsAPIDTO.ResponseDTO responseBody = objectMapper.readValue(httpResponse.body(),
                NaverMapsAPIDTO.ResponseDTO.class);
        return responseBody;

    }


}
