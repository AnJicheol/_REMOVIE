
## **📌 개요**

- **프로젝트 명칭:** REMOVIE
- **개발 인원:** 1명
- **개발 기간:** 2024.09 - 2025.01
- **1차 리팩터링:** 2025.05 - 2025.05  (Backend Spring -> GO 마이그레이션)
- **2차 리팩터링:** 클라이언트 리팩터링 예정

![동작](https://github.com/user-attachments/assets/7ecb203c-98bc-42f5-b572-4b21aabb6db3)


## **📌 설명**

Removie는 **재개봉 영화 알림 서비스** 입니다.

- 기존 영화관에서 다시 보고 싶은 영화가 **재개봉하면 알림**을 받을 수 있음
- **재개봉 영화 리스트**와 **최신 개봉 영화 순위** 확인 가능


## **📌 참고 사항**
- **REMOVIE는 KOBIS(영화관입장통합전산망) 스크래핑 프로젝트 입니다**
- **따라서 데이터 이용에 사전 허락을 구하였습니다**
- **공개 리포지토리에서 KOBIS 관련 민감한 코드 블록 처리 하였습니다**


## **📌 인프라**

![removie_aws](https://github.com/user-attachments/assets/8fae0b20-c427-417b-9635-66b4ab5871e8)



# 백엔드 서버


## **⚙️ 개발 환경**

#### Spring -> GO 마이그레이션 됨
#### **개발 언어**

- GO

#### **데이터베이스**

- MySQL
- Redis

#### **배포 환경**

- AWS Lambda
- GitHubAction

#### **개발 도구**

- IntelliJ IDEA
  
## **✅ 캐시 전략**

Backend서버에서는 별도 캐시 없이 CloudFront 캐시에 의존합니다.
데이터가 하루동안 유효하기 때문에 단기간 유지되는 Lambda 캐시보다는 CloudFront 캐시가 합리적이라 판단하였기 때문입니다.

## **🔄 복구 정책**

Retry & Backoff: Lambda가 서버 역할을 수행하는 동안, 요청 재시도에 대한 책임은 클라이언트에 있습니다.

서버리스 환경(Lambda)에서 서킷 브레이커 패턴을 구현하려면 외부 저장소(예: Redis, DynamoDB 등)가 필요 하지만,
대부분의 정상 요청 흐름에도 불필요한 외부 의존성을 추가하게 되어,
성능과 안정성 측면에서 클라이언트 처리가 합리적이라 판단하였습니다.

추후 EC2 운영시 서버에서 처리 예정.

## API 엔드 포인트

| 메서드 | 경로                 | 설명                 | 요청 파라미터           | 응답 형식 |
| --- | ------------------ | ------------------ | ----------------- | ----- |
| GET | `/release/info`    | 개봉작 정보 목록 조회 (페이징) | `limit`, `offset` | JSON  |
| GET | `/release/info/re` | 재개봉 영화 목록 조회       | 없음                | JSON  |
| GET | `/movie`           | 영화 제목으로 영화 정보 조회   | `movieTitle`      | JSON  |


## **📌 인프라**



![backend](https://github.com/user-attachments/assets/047dcd5e-6809-41ee-b77b-38840af823f1)



# 코어 서버


## **✅ 주요 기능**

1. 영화 데이터 파싱
2. DB 쓰기 작업
3. Redis 쓰기 작업


## **⚙️ 개발 환경**

#### **개발 언어**

- Java 21

#### **프레임워크 & 라이브러리**

- Spring Boot 3.3
- Spring JPA 
- Redis

#### **데이터베이스**

- MySQL 

#### **인프라 & 배포 환경**
- EventBridge
- AWS ECS
- AWS ECR
- GitHubAction
- Docker

#### **개발 도구**

- IntelliJ IDEA
- Gradle


## **📌 인프라**
![core](https://github.com/user-attachments/assets/a13d3532-3bb4-4104-924d-3225e81f461f)



## **📌 Movie Process**
![_process](https://github.com/user-attachments/assets/873632b0-4d14-4d7f-8331-8050cf2bdcac)


## **📌 Cinema Process**
![cinema](https://github.com/user-attachments/assets/7882fe0e-af0c-40a4-aacb-95e9175d3915)

