# SearchEmptySeat-Server 프로젝트 컨텍스트

## 프로젝트 개요
- **프로젝트명**: SearchEmptySeat-Server
- **기술 스택**: Spring Boot, Java, Gradle
- **목적**: 레스토랑 예약 및 관리 시스템
- **현재 상태**: 개발 중 (API 구현 단계)

## 프로젝트 구조
```
SearchEmptySeat-Server/
├── build.gradle.kts
├── doc/
│   ├── API_Specification_v1.1.md
│   └── API_Specification.md
├── src/main/java/com/BubbleWrap/SearchEmptySeat/
│   ├── config/          # 설정 클래스들
│   ├── controller/      # REST API 컨트롤러들
│   ├── dto/            # 데이터 전송 객체들
│   ├── exception/      # 예외 처리
│   ├── model/          # 엔티티 모델들
│   ├── repository/     # 데이터 접근 계층
│   ├── service/        # 비즈니스 로직
│   ├── security/       # JWT 인증
│   └── utils/          # 유틸리티 클래스들
└── src/main/resources/
    ├── application.properties
    └── sql/schema.sql
```

## 주요 모델 (Entity)
- **Member**: 사용자 정보
- **Store**: 매장 정보 (위도/경도 포함)
- **Menu/MenuSection**: 메뉴 및 메뉴 섹션
- **Reservation**: 예약 정보
- **Placement**: 좌석 배치 정보
- **Review**: 리뷰
- **Board**: 게시판
- **Favorite**: 즐겨찾기
- **PaymentHistory**: 결제 내역

## 최근 구현된 주요 기능들

### 1. 즐겨찾기 API 개선
- **FavoriteResponse DTO 수정**: 매장 상세 정보 포함
- **favoriteDate 필드 제거**: 응답에서 제외
- **Favorite 모델에 getter 추가**: 필요한 필드 접근 메서드 추가

### 2. 매장 정보 확장
- **Store 모델에 위도/경도 필드 추가**: `latitude`, `longitude`
- **API 스펙 업데이트**: 새로운 필드 반영

### 3. 예약 통계 API
- **새로운 엔드포인트**: `/api/stores/{storeId}/reservation-stats`
- **기능**: 
  - 최근 7일 예약 통계
  - 현재 예약 수
  - 일평균 예약 수
  - 대기 시간 (1분으로 고정)
- **구현 위치**: `StoreService`, `StoreController`

### 4. 좌석 배치 관리 시스템
- **PlacementService**: 좌석 배치 CRUD 작업
- **PlacementController**: 좌석 배치 API 엔드포인트
- **DTO 클래스들**:
  - `PlacementRequest`: 좌석 배치 생성 요청
  - `PlacementResponse`: 좌석 배치 응답
  - `PlacementUpdateRequest`: 좌석 배치 수정 요청
- **스케줄링 기능**: 예약 기반 좌석 상태 자동 업데이트

## API 엔드포인트 구조
- **인증**: JWT 기반 (`/api/auth/*`)
- **매장 관리**: `/api/stores/*`
- **예약 관리**: `/api/reservations/*`
- **메뉴 관리**: `/api/menus/*`, `/api/menu-sections/*`
- **좌석 관리**: `/api/placements/*`
- **리뷰**: `/api/reviews/*`
- **게시판**: `/api/boards/*`
- **즐겨찾기**: `/api/favorites/*`

## 데이터베이스
- **스키마 파일**: `src/main/resources/sql/schema.sql`
- **연결 설정**: `application.properties`

## 보안 설정
- **JWT 인증**: `JwtFilter`, `JwtUtil`
- **Spring Security**: `SecurityConfig`
- **사용자 타입**: `UserType` enum (ADMIN, OWNER, CUSTOMER)

## 에러 처리
- **BusinessException**: 비즈니스 로직 예외
- **GlobalExceptionHandler**: 전역 예외 처리
- **ErrorCode**: 표준화된 에러 코드

## 다음 단계 제안
1. Reservation 모델의 tableNumber 필드 추가
2. API 스펙 문서 업데이트 완료
3. 테스트 코드 작성
4. 배포 준비

## 주의사항
- 모든 API는 표준화된 `ApiResponse<T>` 형태로 응답
- 에러는 `ErrorCode` enum을 통해 일관성 있게 처리
- JWT 토큰 기반 인증 필수
- 파일 업로드 기능 포함 (`FileController`, `FileStorageService`) 