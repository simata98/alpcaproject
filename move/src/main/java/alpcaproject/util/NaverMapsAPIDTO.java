package alpcaproject.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 네이버 맵스 API를 호출하기 위한 request, response 객체
 * Request 객체를 통해 query string을 만들기 위한 메서드 포함
 */

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class NaverMapsAPIDTO {

    @Data
    @Builder
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class RequestDTO {
        private double startX;
        private double startY;
        private double goalX;
        private double goalY;

        public String getQueryStringFromRequestDTO() {
            return "?start=" + this.startX + "," + this.startY
                    + "&goal=" + this.goalX + "," + this.goalY
                    + "&option=traavoidtoll";
        }
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class ResponseDTO {
        private Integer code;
        private String message;
        private String currentDateTime;
        private Route route;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class Route {
        private List<TraOptimal> traavoidtoll;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class TraOptimal {
        private Summary summary;
        private List<List<Double>> path;
        private List<SectionUnit> section;
        private List<GuideUnit> guide;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class Summary {
        private Location start;
        private Goal goal;
        private Integer distance;
        private Integer duration;
        private Integer etaServiceType;
        private String departureTime;
        private List<List<Double>> bbox;
        private Integer tollFare;
        private Integer taxiFare;
        private Integer fuelPrice;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class Location {
        private List<Double> location;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class Goal extends Location {
        private Integer dir;
    }


    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class SectionUnit {
        private Integer pointIndex;
        private Integer pointCount;
        private Integer distance;
        private String name;
        private Integer congestion;
        private Integer speed;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class GuideUnit {
        private String type;
        private Integer pointIndex;
        private Integer guideCode;
        private String instructions;
        private Integer distance;
        private Integer duration;
    }

}
