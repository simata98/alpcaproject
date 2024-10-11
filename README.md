# 가족 도착 알리미
---

# Table of contents
---
- [서비스 시나리오](#서비스-시나리오)
    - [기능적요구사항](#기능적-요구사항)
    - [비기능적요구사항](#비기능적-요구사항)
    - [분석설계](#분석-설계)
    - [이벤트스토밍 결과](#event-storming-결과)
- [구현](#구현)
    - [CQRS](#cqrs)
- [운영](#운영)
    - [Docker](#docker-build-이미지-생성-및-배포docker-hub)
    - [K8S](#k8s-배포-설정)
    - [API GateWay](#api-gateway)
    - [Ingress](#ingress-설정)
    - [Secret](#Secret)
- [Front](#Front)
<br>
<br>

## 서비스 시나리오

---

### 기능적 요구사항

1. 고객 가족구성원을 등록/삭제/조회한다.
2. 원격지에서 가족구성원이 이동을 시작하면 웹으로 전달된다.
3. 가족구성원이 원격지에서 출발한다.
4. 가족구성원이 이동을 취소할 수 있다.
5. 가족이 이동상태를 중간중간 조회한다.
6. 목적지가 바뀌거나/도착 10분전 마다 웹으로 알림을 보낸다.

### 비기능적 요구사항

- 이동을 취소한 가족구성원을 지속적으로 위치 요청을 하면 안된다. (Sync 호출)
- 모든 가족 구성원에 대한 위치 정보 및 이동 상태 등을 한번에 확인할 수 있어야한다. (CQRS)
- 도착 상태가 바뀔 때마다 웹 알림을 줄 수 있어야 한다. (Event driven)

<br>
<br>

## 분석 설계

---

![스크린샷 2024-10-07 오후 2.05.42.png](AICT%20Cloud%20Project%20b2a5de0cd0f54fa1bbb1cbd870b78442/%25E1%2584%2589%25E1%2585%25B3%25E1%2584%258F%25E1%2585%25B3%25E1%2584%2585%25E1%2585%25B5%25E1%2586%25AB%25E1%2584%2589%25E1%2585%25A3%25E1%2586%25BA_2024-10-07_%25E1%2584%258B%25E1%2585%25A9%25E1%2584%2592%25E1%2585%25AE_2.05.42.png)

![스크린샷 2024-10-07 오후 2.05.23.png](AICT%20Cloud%20Project%20b2a5de0cd0f54fa1bbb1cbd870b78442/%25E1%2584%2589%25E1%2585%25B3%25E1%2584%258F%25E1%2585%25B3%25E1%2584%2585%25E1%2585%25B5%25E1%2586%25AB%25E1%2584%2589%25E1%2585%25A3%25E1%2586%25BA_2024-10-07_%25E1%2584%258B%25E1%2585%25A9%25E1%2584%2592%25E1%2585%25AE_2.05.23.png)

## Event Storming 결과

---

### 이벤트 도출

![image.png](AICT%20Cloud%20Project%20b2a5de0cd0f54fa1bbb1cbd870b78442/image.png)

### 추가 이벤트 도출

![image.png](AICT%20Cloud%20Project%20b2a5de0cd0f54fa1bbb1cbd870b78442/image%201.png)

이동이 종료되었을 때 Event를 생성할 필요가 있음

### 액터, 커맨드 연결

![image.png](AICT%20Cloud%20Project%20b2a5de0cd0f54fa1bbb1cbd870b78442/image%202.png)

### 어그리게잇 묶기 및 바운디드 컨텍스트 매핑

![image.png](AICT%20Cloud%20Project%20b2a5de0cd0f54fa1bbb1cbd870b78442/image%203.png)

### 모델 수정 및 비기능 요구사항 검증

![image.png](AICT%20Cloud%20Project%20b2a5de0cd0f54fa1bbb1cbd870b78442/image%204.png)

### 가족구성원 승인여부 및 위치 등록 요구사항 수정

![image.png](AICT%20Cloud%20Project%20b2a5de0cd0f54fa1bbb1cbd870b78442/image%205.png)

### 목적지 위치 등록에 따른 move Policy 변경

![image.png](AICT%20Cloud%20Project%20b2a5de0cd0f54fa1bbb1cbd870b78442/image%206.png)

### 최종 (불필요한 Policy 제거, customer/family 로직 분기)

![image.png](AICT%20Cloud%20Project%20b2a5de0cd0f54fa1bbb1cbd870b78442/image%207.png)

사용자와 가족단위를 함께 승인 및 거절 처리하는 것은 어렵다고 판단하여, 컨텍스트를 분리.

회원가입 시 가족을 등록하면 가족 구성원이 승인 및 거절 판별 후에 승인 시 가족이 되는 식으로 변경.

![image.png](AICT%20Cloud%20Project%20b2a5de0cd0f54fa1bbb1cbd870b78442/image8.png)

Family가 되기 위해서 Customer가 Register가 된 후 바로 Command로 실행하는 것이 아닌 Policy를 통해 한 번 Event를 받고 동작하도록 수정
Alarm은 알람을 준다는 행위 자체를 한 개의 Policy로 보고 3개의 Command로 나뉘어져 있던 것을 한 개로 통합.

<br>
<br>

## 구현
---
각 BC별로 대변되는 마이크로 서비스들을 스프링부트로 구현. 구현한 각 서비스를 로컬에서 실행하는 방법은 다음과 같다. (사용 포트 넘버는 8081 ~ 808n)
```shell
mvn spring-boot:run
```

<br>
<br>

### CQRS
---
이동(Move), 위치(Location) 등 Status에 대하여 고객(Customer)와 가족(Family)가 조회 및 알람을 받을 수 있도록 CQRS로 구현.

- Customer 개별 distace, duration, status Aggregate Status를 moveview를 통해 통합 조회하여 성능 Issue를 사전에 예방할 수 있다.
- 비동기식으로 처리되며, Kafka를 통해 수신/처리 되어 별도 Table에 관리
- CustomerRegister 이벤트 발생 시, Pub/Sub 기반으로 FamilyId에 해당하는 구성원이 수락해야 계정이 Active가 되도록 구성
![image.png](AICT%20Cloud%20Project%20b2a5de0cd0f54fa1bbb1cbd870b78442/image12.png)

CQRS 데이터프로젝션을 moveview로 구현하여 API 요청시 customerId에 따른 상태값을 조회할 수 있다.
```json
{
    "_embedded": {
        "moveviews": [
            {
                "customerId": "guest2",
                "distance": 25220,
                "duration": 1630132,
                "status": "moving",
                "locId": "loc-20241011013733",
                "_links": {
                    "self": {
                        "href": "http://localhost:8087/moveviews/move-20241011013732"
                    },
                    "moveview": {
                        "href": "http://localhost:8087/moveviews/move-20241011013732"
                    }
                }
            },
            {
                "customerId": "guest1",
                "distance": 10328,
                "duration": 1334892,
                "status": "moving",
                "locId": "loc-20241011013746",
                "_links": {
                    "self": {
                        "href": "http://localhost:8087/moveviews/move-20241011013746"
                    },
                    "moveview": {
                        "href": "http://localhost:8087/moveviews/move-20241011013746"
                    }
                }
            },
            {
                "customerId": "guest1",
                "distance": 19217,
                "duration": 1543509,
                "status": "moving",
                "locId": "loc-20241011014036",
                "_links": {
                    "self": {
                        "href": "http://localhost:8087/moveviews/move-20241011014035"
                    },
                    "moveview": {
                        "href": "http://localhost:8087/moveviews/move-20241011014035"
                    }
                }
            },
            {
                "customerId": "guest1",
                "distance": 19324,
                "duration": 1589128,
                "status": "moving",
                "locId": "loc-20241011014351",
                "_links": {
                    "self": {
                        "href": "http://localhost:8087/moveviews/move-20241011014350"
                    },
                    "moveview": {
                        "href": "http://localhost:8087/moveviews/move-20241011014350"
                    }
                }
            },
            {
                "customerId": "guest1",
                "distance": 10091,
                "duration": 1287967,
                "status": "moving",
                "locId": "loc-20241011014856",
                "_links": {
                    "self": {
                        "href": "http://localhost:8087/moveviews/move-20241011014856"
                    },
                    "moveview": {
                        "href": "http://localhost:8087/moveviews/move-20241011014856"
                    }
                }
            },
            {
                "customerId": "guest1",
                "distance": 25217,
                "duration": 1596921,
                "status": "moving",
                "locId": "loc-20241011015303",
                "_links": {
                    "self": {
                        "href": "http://localhost:8087/moveviews/move-20241011015303"
                    },
                    "moveview": {
                        "href": "http://localhost:8087/moveviews/move-20241011015303"
                    }
                }
            }
        ]
    },
    "_links": {
        "self": {
            "href": "http://localhost:8087/moveviews"
        },
        "profile": {
            "href": "http://localhost:8087/profile/moveviews"
        }
    },
    "page": {
        "size": 20,
        "totalElements": 6,
        "totalPages": 1,
        "number": 0
    }
}
```

<br>
<br>

## 운영

### Docker Build 이미지 생성 및 배포(Docker hub)
![image.png](AICT%20Cloud%20Project%20b2a5de0cd0f54fa1bbb1cbd870b78442/image9.png)

<br>

### K8S 배포 설정
![image.png](AICT%20Cloud%20Project%20b2a5de0cd0f54fa1bbb1cbd870b78442/image10.png)
Azure Aks에 배포

![image.png](AICT%20Cloud%20Project%20b2a5de0cd0f54fa1bbb1cbd870b78442/image11.png)
AKS Pod, service, deployment, replicaset 들이 정상적으로 배포되고 LoadBalancer를 통해 잘 외부 IP에 배포된 모습
<br>

### API GateWay 
```yaml
server:
  port: 8088

---

spring:
  profiles: default
  cloud:
    gateway:
#<<< API Gateway / Routes
      routes:
        - id: customer
          uri: http://localhost:8082
          predicates:
            - Path=/customers/**, 
        - id: move
          uri: http://localhost:8083
          predicates:
            - Path=/moves/**, 
        - id: family
          uri: http://localhost:8084
          predicates:
            - Path=/families/**, 
        - id: alarm
          uri: http://localhost:8085
          predicates:
            - Path=/alarms/**, 
        - id: location
          uri: http://localhost:8086
          predicates:
            - Path=/locations/**, 
        - id: frontend
          uri: http://localhost:8080
          predicates:
            - Path=/**
#>>> API Gateway / Routes
      globalcors:
        corsConfigurations:
          '[/**]':
            allowedOrigins:
              - "*"
            allowedMethods:
              - "*"
            allowedHeaders:
              - "*"
            allowCredentials: true


---

spring:
  profiles: docker
  cloud:
    gateway:
      routes:
        - id: customer
          uri: http://customer:8080
          predicates:
            - Path=/customers/**, 
        - id: move
          uri: http://move:8080
          predicates:
            - Path=/moves/**, 
        - id: family
          uri: http://family:8080
          predicates:
            - Path=/families/**, 
        - id: alarm
          uri: http://alarm:8080
          predicates:
            - Path=/alarms/**, 
        - id: location
          uri: http://location:8080
          predicates:
            - Path=/locations/**, 
        - id: frontend
          uri: http://frontend:8080
          predicates:
            - Path=/**
      globalcors:
        corsConfigurations:
          '[/**]':
            allowedOrigins:
              - "*"
            allowedMethods:
              - "*"
            allowedHeaders:
              - "*"
            allowCredentials: true

server:
  port: 8080

```

### Ingress 설정
Ingress를 사용하여 GateWay 포워딩 진행

![image.png](AICT%20Cloud%20Project%20b2a5de0cd0f54fa1bbb1cbd870b78442/image13.png)


```yaml
apiVersion: networking.k8s.io/v1
kind: "Ingress"
metadata: 
  name: "family-allimee-ingress"
  annotations: 
    nginx.ingress.kubernetes.io/ssl-redirect: "false"
    ingressclass.kubernetes.io/is-default-class: "true"
spec: 
  ingressClassName: nginx
  rules: 
    - host: ""
      http: 
        paths: 
          - path: /*
            pathType: Prefix
            backend: 
              service:
                name: frontend
                port:
                  number: 8080
          - path: /customers
            pathType: Prefix
            backend: 
              service:
                name: customer
                port:
                  number: 8080
          - path: /locations
            pathType: Prefix
            backend: 
              service:
                name: location
                port:
                  number: 8080
          - path: /alarms
            pathType: Prefix
            backend: 
              service:
                name: alarm
                port:
                  number: 8080
          - path: /moves
            pathType: Prefix
            backend: 
              service:
                name: move
                port:
                  number: 8080
          - path: /families
            pathType: Prefix
            backend: 
              service:
                name: family
                port:
                  number: 8080     
          - path: /moveviews
            pathType: Prefix
            backend: 
              service:
                name: moveview
                port:
                  number: 8080  
```

### Secret
API Key는 따로 관리하기 위해 yaml 파일로 관리 후 변수로 로드
![image.png](AICT%20Cloud%20Project%20b2a5de0cd0f54fa1bbb1cbd870b78442/secret.png)

```yaml
          env:
          - name: naver-maps-client-id
            valueFrom:
              secretKeyRef:
                name: secret-config
                key: naver-maps-client-id
          - name: naver-maps-client-token
            valueFrom:
              secretKeyRef:
                name: secret-config
                key: naver-maps-client-token
```

<br>
<br>

## Front
---
![image.png](AICT%20Cloud%20Project%20b2a5de0cd0f54fa1bbb1cbd870b78442/main.png)

- Join : 사용자 이름 및 가입할 가족 이름을 입력하고 승인을 받습니다.
- Member Device : 가입한 사용자 이름과 가족 이름을 입력 후 출발지 주소와 도착지 주소를 입력하고 시작 버튼을 누르면 가족들에게 출발했다는 알림이 갑니다.
- 이동 상태 : 가족들이 이동중인 가족구성원의 상태를 확인할 수 있고, 도착 전 알림을 받습니다.

<br>
<br>

![image.png](AICT%20Cloud%20Project%20b2a5de0cd0f54fa1bbb1cbd870b78442/move.png)

- 이동 중인 상태일 떄 지속적으로 움직임을 업데이트 하여 위치를 최신화합니다.

<br>
<br>

![image.gif](AICT%20Cloud%20Project%20b2a5de0cd0f54fa1bbb1cbd870b78442/참고.gif)

- 사용자가 이동중 상태로 변경시 정상적으로 가족들이 확인할 수 있습니다.
