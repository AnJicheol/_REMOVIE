
## **📌 개요**

- **프로젝트 명칭:** REMOVIE
- **개발 인원:** 1명
- **개발 기간:** 2024.09 - 2025.01
.
![동작](https://github.com/user-attachments/assets/7ecb203c-98bc-42f5-b572-4b21aabb6db3)


## **📌 설명**

Removie는 **재개봉 영화 알림 서비스** 입니다.

- 기존 영화관에서 다시 보고 싶은 영화가 **재개봉하면 알림**을 받을 수 있음
- **재개봉 영화 리스트**와 **최신 개봉 영화 순위** 확인 가능


## **📌 참고 사항**
- **REMOVIE는 KOBIS(영화관입장통합전산망) 스크래핑 프로젝트 입니다**
- **따라서 데이터 이용에 사전 허락을 구하였습니다**
- **공개 리포지토리에서 KOBIS 관련 민감한 코드 블록 처리 하였습니다**
- **테이블 매핑 정보는 모두 제거하였습니다**

## **📌 인프라**

![removie_aws](https://github.com/user-attachments/assets/8fae0b20-c427-417b-9635-66b4ab5871e8)



# 백엔드 서버

## **✅ 주요 기능**

1. API 통신

![backend](https://github.com/user-attachments/assets/047dcd5e-6809-41ee-b77b-38840af823f1)




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


## API 엔드 포인트

| 메서드 | 경로                 | 설명                 | 요청 파라미터           | 응답 형식 |
| --- | ------------------ | ------------------ | ----------------- | ----- |
| GET | `/release/info`    | 개봉작 정보 목록 조회 (페이징) | `limit`, `offset` | JSON  |
| GET | `/release/info/re` | 재개봉 영화 목록 조회       | 없음                | JSON  |
| GET | `/movie`           | 영화 제목으로 영화 정보 조회   | `movieTitle`      | JSON  |


# 코어 서버


## **✅ 주요 기능**

1. 영화 데이터 파싱
2. DB 쓰기 작업
3. Redis 쓰기 작업


![core](https://github.com/user-attachments/assets/a13d3532-3bb4-4104-924d-3225e81f461f)


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


