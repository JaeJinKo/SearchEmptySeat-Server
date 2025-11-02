# API 명세서 v1.1

## 인증 (Authentication)

### 회원가입 (Sign Up)
- **URL**: `/api/auth/signup`
- **HTTP Method**: POST

#### Request Parameters
```json
{
  "email": "testuser@example.com",
  "password": "password123",
  "name": "테스트 사용자",
  "phone": "010-1234-5678",
  "location": "Seoul",
  "userType": "USER"
}
```

#### Response
```json
{
  "status": "success",
  "data": {
    "email": "testuser@example.com",
    "name": "테스트 사용자",
    "phone": "010-1234-5678",
    "location": "Seoul",
    "image": ["member/profile/default.png"],
    "userType": "USER"
  },
  "message": "Membership registration successful"
}
```

### 로그인 (Login)
- **URL**: `/api/auth/login`
- **HTTP Method**: POST

#### Request Body
```json
{
  "email": "testuser@example.com",
  "password": "password123"
}
```

#### Response
```json
{
  "status": "success",
  "data": {
    "email": "testuser@example.com",
    "userId": "1",
    "name": "테스트 사용자",
    "phone": "010-1234-5678",
    "location": "Seoul",
    "userType": "USER",
    "image": ["member/profile/default.png"],
    "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
  },
  "message": "Login Success"
}
```

### 비밀번호 찾기 (Forgot Password)
- **URL**: `/api/auth/forgot-password`
- **HTTP Method**: POST

#### Request Body
```json
{
  "email": "testuser@example.com"
}
```

#### Response
```json
{
  "status": "success",
  "data": {
    "email": "testuser@example.com"
  },
  "message": "Reset password successful"
}
```

## 회원 (Member)

### 회원 정보 수정 (Update My Info)
- **URL**: `/api/member/{userId}`
- **HTTP Method**: PATCH

#### Request Parameters
```json
{
  "data": {
    "name": "새 이름",
    "phone": "010-9876-5432",
    "location": "Busan"
  },
  "image": "<MultipartFile>"
}
```

#### Response
```json
{
  "status": "success",
  "data": {
    "email": "testuser@example.com",
    "userId": "1",
    "name": "새 이름",
    "phone": "010-9876-5432",
    "location": "Busan",
    "userType": "USER",
    "image": ["member/profile/updated.png"]
  },
  "message": "Update my info success"
}
```

### 회원 정보 조회 (Get My Info)
- **URL**: `/api/member/{userId}`
- **HTTP Method**: GET

#### Response
```json
{
  "status": "success",
  "data": {
    "email": "testuser@example.com",
    "userId": "1",
    "name": "테스트 사용자",
    "phone": "010-1234-5678",
    "location": "Seoul",
    "userType": "USER",
    "image": ["member/profile/default.png"]
  },
  "message": "Get my info success"
}
```

### 비밀번호 변경 (Update Password)
- **URL**: `/api/member/{userId}/password`
- **HTTP Method**: PATCH

#### Request Body
```json
{
  "newPassword": "newpassword123"
}
```

#### Response
```json
{
  "status": "success",
  "message": "Password updated successfully"
}
```

## 가게 (Store)

### 가게 등록 (Register Store)
- **URL**: `/api/store/register`
- **HTTP Method**: POST

#### Request Parameters
```json
{
  "data": {
    "storeName": "맛있는 한식당",
    "location": "서울시 강남구",
    "description": "한식 전문점입니다.",
    "businessRegistrationNumber": "123-45-67890",
    "bank": "국민은행",
    "accountNumber": "123456-78-901234",
    "depositor": "김사장",
    "category": ["KOREANFOOD", "MEAT"],
    "businessHours": {
      "월요일": "09:00 - 22:00",
      "화요일": "09:00 - 22:00",
      "수요일": "09:00 - 22:00",
      "목요일": "09:00 - 22:00",
      "금요일": "09:00 - 22:00",
      "토요일": "10:00 - 21:00",
      "일요일": "10:00 - 20:00"
    },
    "regularHolidays": {
      "월요일": 0,
      "화요일": 0,
      "수요일": 0,
      "목요일": 0,
      "금요일": 0,
      "토요일": 0,
      "일요일": 1
    },
    "temporaryHolidays": [
      "2024-01-01",
      "2024-02-09",
      "2024-02-10"
    ]
  },
  "images": ["<MultipartFile>"]
}
```

#### Response
```json
{
  "status": "success",
  "data": {
    "storeName": "맛있는 한식당",
    "location": "서울시 강남구",
    "description": "한식 전문점입니다.",
    "businessRegistrationNumber": "123-45-67890",
    "bank": "국민은행",
    "accountNumber": "123456-78-901234",
    "depositor": "김사장",
    "businessHours": {
      "월요일": "09:00 - 22:00",
      "화요일": "09:00 - 22:00",
      "수요일": "09:00 - 22:00",
      "목요일": "09:00 - 22:00",
      "금요일": "09:00 - 22:00",
      "토요일": "10:00 - 21:00",
      "일요일": "10:00 - 20:00"
    },
    "regularHolidays": {
      "월요일": 0,
      "화요일": 0,
      "수요일": 0,
      "목요일": 0,
      "금요일": 0,
      "토요일": 0,
      "일요일": 1
    },
    "temporaryHolidays": [
      "2024-01-01",
      "2024-02-09",
      "2024-02-10"
    ],
    "image": ["store/1/abc123_store_1.png", "store/1/def456_store_2.png"],
    "category": ["KOREANFOOD", "MEAT"],
    "viewCount": 0
  },
  "message": "Store registration successful"
}
```

### 가게 수정 (Update Store)
- **URL**: `/api/store/{storeId}`
- **HTTP Method**: PUT
- **Content-Type**: `multipart/form-data`

#### Request Parameters
- **storeId**: 가게 ID (Long, 필수)
- **storeName**: 가게명 (String, 선택)
- **location**: 위치 (String, 선택)
- **description**: 설명 (String, 선택)
- **businessRegistrationNumber**: 사업자등록번호 (String, 선택)
- **bank**: 은행명 (String, 선택)
- **accountNumber**: 계좌번호 (String, 선택)
- **depositor**: 예금주 (String, 선택)
- **businessHours**: 영업시간 (Map<String, String>, 선택)
- **regularHolidays**: 정기 휴무일 (Map<String, Integer>, 선택) - 0=영업, 1=휴무
- **temporaryHolidays**: 임시 휴무일 (List<String>, 선택) - 날짜 리스트
- **category**: 카테고리 (List<StoreCategory>, 선택)
- **imageFiles**: 이미지 파일들 (MultipartFile[], 선택)

#### 특징
- **부분 업데이트 지원**: 모든 필드가 선택적이므로 필요한 필드만 전송하여 업데이트 가능
- **기존 데이터 유지**: 전송하지 않은 필드는 기존 값으로 유지

#### 예시 요청 (임시 휴무일만 업데이트)
```json
{
  "temporaryHolidays": [
    "2024-12-25",
    "2024-12-31",
    "2025-01-01"
  ]
}
```

#### 예시 요청 (정기 휴무일만 업데이트)
```json
{
  "regularHolidays": {
    "월요일": 0,
    "화요일": 0,
    "수요일": 0,
    "목요일": 0,
    "금요일": 0,
    "토요일": 0,
    "일요일": 1
  }
}
```

#### Response
```json
{
  "status": "success",
  "data": {
    "storeName": "맛있는 한식당",
    "location": "서울시 강남구",
    "description": "한식 전문점입니다.",
    "businessRegistrationNumber": "123-45-67890",
    "bank": "국민은행",
    "accountNumber": "123456-78-901234",
    "depositor": "김사장",
    "businessHours": {
      "월요일": "09:00 - 22:00",
      "화요일": "09:00 - 22:00",
      "수요일": "09:00 - 22:00",
      "목요일": "09:00 - 22:00",
      "금요일": "09:00 - 22:00",
      "토요일": "10:00 - 21:00",
      "일요일": "10:00 - 20:00"
    },
    "regularHolidays": {
      "월요일": 0,
      "화요일": 0,
      "수요일": 0,
      "목요일": 0,
      "금요일": 0,
      "토요일": 0,
      "일요일": 1
    },
    "temporaryHolidays": [
      "2024-12-25",
      "2024-12-31",
      "2025-01-01"
    ],
    "category": ["KOREANFOOD", "MEAT"],
    "image": ["store/1/abc123_store_1.png", "store/1/def456_store_2.png"]
  },
  "message": "Store update successful"
}
```

### 내 가게 목록 조회 (Get My Stores)
- **URL**: `/api/store/my-stores`
- **HTTP Method**: GET

#### Response
```json
{
  "status": "success",
  "data": [
    {
      "storePK": 1,
      "storeName": "맛있는 한식당",
      "location": "서울시 강남구",
      "description": "한식 전문점입니다.",
      "businessRegistrationNumber": "123-45-67890",
      "bank": "국민은행",
      "accountNumber": "123456-78-901234",
      "depositor": "김사장",
      "businessHours": {
        "월요일": "09:00 - 22:00",
        "화요일": "09:00 - 22:00",
        "수요일": "09:00 - 22:00",
        "목요일": "09:00 - 22:00",
        "금요일": "09:00 - 22:00",
        "토요일": "10:00 - 21:00",
        "일요일": "10:00 - 20:00"
      },
      "regularHolidays": {
        "월요일": 0,
        "화요일": 0,
        "수요일": 0,
        "목요일": 0,
        "금요일": 0,
        "토요일": 0,
        "일요일": 1
      },
      "temporaryHolidays": [
        "2024-01-01",
        "2024-02-09",
        "2024-02-10"
      ],
      "image": ["store/1/abc123_store_1.png", "store/1/def456_store_2.png"],
      "category": ["KOREANFOOD", "MEAT"],
      "createdDate": "2024-01-01T12:00:00",
      "updatedDate": "2024-01-01T12:00:00",
      "viewCount": 150,
      "averageRating": 4.5
    }
  ],
  "message": "View My Stores"
}
```

### 전체 가게 목록 조회 (Get All Stores)
- **URL**: `/api/store/all?sortBy=rating`
- **HTTP Method**: GET

#### Request Parameters
- **sortBy**: 정렬 기준 (optional)
  - `favorite`: 즐겨찾기 수 기준
  - `rating`: 별점 기준
  - `reservation`: 예약 수 기준

#### Response
```json
{
  "status": "success",
  "data": [
    {
      "storePK": 1,
      "storeName": "맛있는 한식당",
      "location": "서울시 강남구",
      "description": "한식 전문점입니다.",
      "businessRegistrationNumber": "123-45-67890",
      "bank": "국민은행",
      "accountNumber": "123456-78-901234",
      "depositor": "김사장",
      "businessHours": {
        "월요일": "09:00 - 22:00",
        "화요일": "09:00 - 22:00",
        "수요일": "09:00 - 22:00",
        "목요일": "09:00 - 22:00",
        "금요일": "09:00 - 22:00",
        "토요일": "10:00 - 21:00",
        "일요일": "10:00 - 20:00"
      },
      "regularHolidays": {
        "월요일": 0,
        "화요일": 0,
        "수요일": 0,
        "목요일": 0,
        "금요일": 0,
        "토요일": 0,
        "일요일": 1
      },
      "temporaryHolidays": [
        "2024-01-01",
        "2024-02-09",
        "2024-02-10"
      ],
      "image": ["store/1/abc123_store_1.png", "store/1/def456_store_2.png"],
      "category": ["KOREANFOOD", "MEAT"],
      "createdDate": "2024-01-01T12:00:00",
      "updatedDate": "2024-01-01T12:00:00",
      "viewCount": 150,
      "averageRating": 4.5,
      "favoriteCount": 25,
      "reservationCount": 12
    }
  ],
  "message": "View All Stores"
}
```

### 가게 상세 조회 (Get Store by ID)
- **URL**: `/api/store/{storeId}`
- **HTTP Method**: GET

#### Response
```json
{
  "status": "success",
  "data": {
    "storePK": 1,
    "storeName": "맛있는 한식당",
    "location": "서울시 강남구",
    "description": "한식 전문점입니다.",
    "businessRegistrationNumber": "123-45-67890",
    "bank": "국민은행",
    "accountNumber": "123456-78-901234",
    "depositor": "김사장",
    "businessHours": {
      "월요일": "09:00 - 22:00",
      "화요일": "09:00 - 22:00",
      "수요일": "09:00 - 22:00",
      "목요일": "09:00 - 22:00",
      "금요일": "09:00 - 22:00",
      "토요일": "10:00 - 21:00",
      "일요일": "10:00 - 20:00"
    },
    "regularHolidays": {
      "월요일": 0,
      "화요일": 0,
      "수요일": 0,
      "목요일": 0,
      "금요일": 0,
      "토요일": 0,
      "일요일": 1
    },
    "temporaryHolidays": [
      "2024-01-01",
      "2024-02-09",
      "2024-02-10"
    ],
    "image": ["store/1/abc123_store_1.png", "store/1/def456_store_2.png"],
    "category": ["KOREANFOOD", "MEAT"],
    "createdDate": "2024-01-01T12:00:00",
    "updatedDate": "2024-01-01T12:00:00",
    "viewCount": 150,
    "averageRating": 4.5
  },
  "message": "View Store By Id"
}
```

### 카테고리별 가게 조회 (Get Stores by Category)
- **URL**: `/api/store/category/{category}?sortBy=rating`
- **HTTP Method**: GET

#### Request Parameters
- **category**: 카테고리 (String, 필수)
- **sortBy**: 정렬 기준 (optional)

#### Response
```json
{
  "status": "success",
  "data": [
    {
      "storePK": 1,
      "storeName": "맛있는 한식당",
      "location": "서울시 강남구",
      "description": "한식 전문점입니다.",
      "businessRegistrationNumber": "123-45-67890",
      "bank": "국민은행",
      "accountNumber": "123456-78-901234",
      "depositor": "김사장",
      "businessHours": {
        "월요일": "09:00 - 22:00",
        "화요일": "09:00 - 22:00",
        "수요일": "09:00 - 22:00",
        "목요일": "09:00 - 22:00",
        "금요일": "09:00 - 22:00",
        "토요일": "10:00 - 21:00",
        "일요일": "10:00 - 20:00"
      },
      "regularHolidays": {
        "월요일": 0,
        "화요일": 0,
        "수요일": 0,
        "목요일": 0,
        "금요일": 0,
        "토요일": 0,
        "일요일": 1
      },
      "temporaryHolidays": [
        "2024-01-01",
        "2024-02-09",
        "2024-02-10"
      ],
      "image": ["store/1/abc123_store_1.png", "store/1/def456_store_2.png"],
      "category": ["KOREANFOOD", "MEAT"],
      "createdDate": "2024-01-01T12:00:00",
      "updatedDate": "2024-01-01T12:00:00",
      "viewCount": 150,
      "averageRating": 4.5,
      "favoriteCount": 25,
      "reservationCount": 12
    }
  ],
  "message": "View Stores By Category"
}
```

### 가게명 검색 (Search Stores by Name)
- **URL**: `/api/store/search?storeName=%E%3`
- **HTTP Method**: GET

#### Request Parameters
- **storeName**: 검색할 가게명 (String, 필수)
- 한글 검색 시 URL 인코딩 필요

#### Response
```json
{
  "status": "success",
  "data": [
    {
      "storeName": "맛있는 한식당",
      "location": "서울시 강남구",
      "description": "한식 전문점입니다.",
      "businessRegistrationNumber": "123-45-67890",
      "bank": "국민은행",
      "accountNumber": "123456-78-901234",
      "depositor": "김사장",
      "category": ["KOREANFOOD", "MEAT"],
      "businessHours": {
        "월요일": "09:00 - 22:00",
        "화요일": "09:00 - 22:00",
        "수요일": "09:00 - 22:00",
        "목요일": "09:00 - 22:00",
        "금요일": "09:00 - 22:00",
        "토요일": "10:00 - 21:00",
        "일요일": "10:00 - 20:00"
      },
      "regularHolidays": {
        "월요일": 0,
        "화요일": 0,
        "수요일": 0,
        "목요일": 0,
        "금요일": 0,
        "토요일": 0,
        "일요일": 1
      },
      "temporaryHolidays": [
        "2024-01-01",
        "2024-02-09",
        "2024-02-10"
      ],
      "image": ["store/1/abc123_store_1.png", "store/1/def456_store_2.png"],
      "viewCount": 100,
      "averageRating": 4.5,
      "favoriteCount": 20,
      "reservationCount": 15
    }
  ],
  "message": "Search Stores By Name"
}
```

### 가게 예약 통계 조회 (Get Store Reservation Statistics)
- **URL**: `/api/store/{storeId}/reservations/stats`
- **HTTP Method**: GET
- **설명**: 가게의 예약 현황 통계를 조회합니다. 현재 시간에서 30일 전까지의 데이터를 기반으로 요일별 평균 예약 수를 계산합니다.

#### Response
```json
{
  "status": "success",
  "data": {
    "currentReservations": 5,
    "estimatedWaitTime": 1,
    "monthlyStats": [
      {
        "day": "월",
        "averageTeams": 3.2
      },
      {
        "day": "화",
        "averageTeams": 4.5
      },
      {
        "day": "수",
        "averageTeams": 2.8
      },
      {
        "day": "목",
        "averageTeams": 5.1
      },
      {
        "day": "금",
        "averageTeams": 6.7
      },
      {
        "day": "토",
        "averageTeams": 8.3
      },
      {
        "day": "일",
        "averageTeams": 7.9
      }
    ]
  },
  "message": "Reservation statistics retrieved successfully"
}
```

#### 데이터 설명
- **currentReservations**: 오늘의 예약 수
- **estimatedWaitTime**: 예상 대기 시간 (분 단위, 현재 1분으로 고정)
- **monthlyStats**: 요일별 평균 예약 수 (30일 데이터 기반)
  - **day**: 요일 (월~일)
  - **averageTeams**: 해당 요일의 평균 예약 수 (소수점 첫째자리까지)

#### 계산 방식
1. 현재 시간에서 30일 전까지의 예약 데이터를 조회
2. 각 요일별로 예약 수를 합산
3. 해당 요일이 나타난 주차 수로 나누어 평균 계산
4. 소수점 첫째자리까지 반올림하여 반환

### 오늘 현황 조회 (Get Today's Store Stats)
- **URL**: `/api/store/{storeId}/today-stats`
- **HTTP Method**: GET
- **설명**: 오늘 날짜 기준으로 현재 시간, 오늘 총 매출, 예약 건수, 예약 취소 건수, 평균 예약 금액, 최고 예약 금액을 반환합니다.

#### Response
```json
{
  "status": "success",
  "data": {
    "currentTime": "2024-06-25T15:30:00",
    "totalRevenue": 120000,
    "totalReservations": 8,
    "cancelledReservations": 0,
    "averageReservationAmount": 15000.0,
    "highestReservationAmount": 30000
  },
  "message": "오늘 현황을 조회했습니다."
}
```

#### 데이터 설명
- **currentTime**: 현재 시간 (ISO-8601 포맷)
- **totalRevenue**: 오늘 총 매출 (원)
- **totalReservations**: 오늘 예약 건수
- **cancelledReservations**: 오늘 예약 취소 건수(현재 0, 추후 예약 상태 관리 시 변경 가능)
- **averageReservationAmount**: 오늘 평균 예약 금액 (소수점 첫째자리까지)
- **highestReservationAmount**: 오늘 최고 예약 금액 (원)

### 지도 핀 목록 조회 (Get Map Pins)
- **URL**: `/api/map/pins`
- **HTTP Method**: GET
- **설명**: 모바일 지도에서 가게 핀을 표시하기 위한 기본 정보를 조회합니다.

#### Response
```json
{
  "status": "success",
  "data": [
    {
      "storePK": 1,
      "location": "서울시 강남구 테헤란로 123"
    },
    {
      "storePK": 2,
      "location": "서울시 마포구 홍대입구역 456"
    },
    {
      "storePK": 3,
      "location": "서울시 종로구 인사동 789"
    }
  ],
  "message": "Map pins fetched"
}
```

#### 데이터 설명
- **storePK**: 가게 고유 식별자
- **location**: 가게 주소

### 지도 핀 디테일 조회 (Get Map Pin Detail)
- **URL**: `/api/map/pin/{storePK}`
- **HTTP Method**: GET
- **설명**: 지도에서 핀을 클릭했을 때 표시할 상세 정보를 조회합니다.

#### Response
```json
{
  "status": "success",
  "data": {
    "storePK": 1,
    "storeName": "맛있는 한식당",
    "availableSeats": 12
  },
  "message": "Map pin detail fetched"
}
```

#### 데이터 설명
- **storePK**: 가게 고유 식별자
- **storeName**: 가게 이름
- **availableSeats**: 현재 빈자리 수 (Placement 레이아웃에서 status=0인 테이블들의 총 인원 수)

#### 빈자리 수 계산 방식
1. 해당 가게의 Placement 레이아웃 정보를 조회
2. 각 테이블의 status가 0(빈자리)인 경우
3. 해당 테이블의 table(최대 인원) 값을 합산
4. 모든 빈 테이블의 최대 인원을 합한 값이 availableSeats

## 메뉴 (Menu)

### 메뉴 추가 (Add Menu)
- **URL**: `/api/menu/add`
- **HTTP Method**: POST

#### Request Parameters
```json
{
  "data": {
    "storePK": 1,
    "name": "아메리카노",
    "section": "음료",
    "priority": 1,
    "price": 4000,
    "description": "신선한 아메리카노",
    "available": true
  },
  "image": "<MultipartFile>"
}
```

#### Response
```json
{
  "status": "success",
  "data": {
    "storeName": "맛있는 한식당",
    "name": "아메리카노",
    "section": "음료",
    "sectionPK": 1,
    "priority": 1,
    "price": 4000,
    "image": ["store/1/menu/a1b2c3d4-e5f6-7890-abcd-ef1234567890_menu.png"],
    "Description": "신선한 아메리카노",
    "available": true
  },
  "message": "Menu add successful"
}
```

### 가게별 메뉴 조회 (Get Menu by Store)
- **URL**: `/api/menu/store/{storePK}`
- **HTTP Method**: GET

#### Response
```json
{
  "status": "success",
  "data": [
    {
      "menuPK": 1,
      "name": "아메리카노",
      "section": {
        "sectionPK": 1,
        "name": "음료",
        "priority": 1,
        "createdDate": "2023-10-01T12:00:00",
        "updatedDate": "2023-10-01T12:00:00"
      },
      "price": 4000,
      "image": ["store/1/menu/a1b2c3d4-e5f6-7890-abcd-ef1234567890_menu.png"],
      "description": "신선한 아메리카노",
      "isAvailable": true
    }
  ],
  "message": "View menus"
}
```

### 메뉴 수정 (Update Menu)
- **URL**: `/api/menu/update/{menuId}`
- **HTTP Method**: PUT

#### Request Parameters
```json
{
  "data": {
    "name": "카페라떼",
    "section": "음료",
    "priority": 1,
    "price": 4500,
    "description": "부드러운 카페라떼",
    "available": true
  },
  "image": "<MultipartFile>"
}
```

#### Response
```json
{
  "status": "success",
  "data": {
    "menuId": 1,
    "name": "카페라떼",
    "section": "음료",
    "sectionPK": 1,
    "priority": 1,
    "price": 4500,
    "image": ["store/1/menu/xyz789abc123def456_menu.jpg"],
    "description": "부드러운 카페라떼",
    "available": true
  },
  "message": "Menu update successful"
}
```

### 메뉴 삭제 (Delete Menu)
- **URL**: `/api/menu/del/{menuId}`
- **HTTP Method**: DELETE

#### Response
```json
{
  "status": "success",
  "data": {
    "menuId": 1,
    "name": "카페라떼",
    "section": "음료",
    "sectionPK": 1,
    "priority": 1,
    "price": 4500,
    "image": ["store/1/menu/xyz789abc123def456_menu.jpg"],
    "description": "부드러운 카페라떼",
    "available": true
  },
  "message": "Menu delete successful"
}
```

### 메뉴 재고 상태 업데이트 (Update Menus Stock)
- **URL**: `/api/menu/outofstock`
- **HTTP Method**: PATCH

#### Request Body
```json
{
  "menus": [
    {
      "menuPK": 1,
      "available": false
    }
  ]
}
```

#### Response
```json
{
  "status": "success",
  "data": [
    {
      "menuId": 1,
      "available": false
    }
  ],
  "message": "Bulk stock update success"
}
```

## 메뉴 섹션 (Menu Section)

### 가게별 메뉴 섹션 조회 (Get Menu Sections by Store)
- **URL**: `/api/menu-section/store/{storePK}`
- **HTTP Method**: GET

#### Response
```json
{
  "status": "success",
  "data": [
    {
      "sectionPK": 1,
      "name": "음료",
      "priority": 1,
      "createdDate": "2023-10-01T12:00:00",
      "updatedDate": "2023-10-01T12:00:00"
    },
    {
      "sectionPK": 2,
      "name": "메인",
      "priority": 2,
      "createdDate": "2023-10-01T12:00:00",
      "updatedDate": "2023-10-01T12:00:00"
    }
  ],
  "message": "Menu sections retrieved successfully"
}
```

### 메뉴 섹션 수정 (Update Menu Section)
- **URL**: `/api/menu-section/update/{sectionPK}`
- **HTTP Method**: PUT

#### Request Body
```json
{
  "name": "음료류",
  "priority": 1
}
```

#### Response
```json
{
  "status": "success",
  "data": {
    "sectionPK": 1,
    "name": "음료류",
    "priority": 1,
    "updatedDate": "2023-10-01T12:00:00"
  },
  "message": "Menu section updated successfully"
}
```

### 메뉴 섹션 삭제 (Delete Menu Section)
- **URL**: `/api/menu-section/delete/{sectionPK}`
- **HTTP Method**: DELETE

#### Response
```json
{
  "status": "success",
  "data": {
    "sectionPK": 1,
    "name": "음료류",
    "priority": 1
  },
  "message": "Menu section deleted successfully"
}
```

### 메뉴 섹션 일괄 수정 (Bulk Update Menu Sections)
- **URL**: `/api/menu-section/bulk-update/{storePK}`
- **HTTP Method**: PUT

#### Request Body
```json
[
  { "sectionPK": 1, "name": "음료", "priority": 1 },
  { "sectionPK": 2, "name": "메인", "priority": 2 },
  { "sectionPK": 3, "name": "사이드", "priority": 3 }
]
```

#### Response
```json
{
  "status": "success",
  "data": [
    {
      "sectionPK": 1,
      "name": "음료",
      "priority": 1,
      "createdDate": "2023-10-01T12:00:00",
      "updatedDate": "2023-10-01T12:00:00"
    },
    {
      "sectionPK": 2,
      "name": "메인",
      "priority": 2,
      "createdDate": "2023-10-01T12:00:00",
      "updatedDate": "2023-10-01T12:00:00"
    }
  ],
  "message": "Menu sections updated successfully"
}
```

## 결제 내역 (Payment History)

### 결제 내역 생성 (Create Payment History)
- **URL**: `/api/payment-history`
- **HTTP Method**: POST

#### Request Body
```json
{
  "reservationPK": 1,
  "amount": 10000
}
```

#### Response
```json
{
  "status": "success",
  "data": {
    "paymentId": 1,
    "reservationPK": 1,
    "amount": 10000,
    "paymentDate": "2023-10-01T12:00:00"
  },
  "message": "PaymentHistory created"
}
```

### 모든 결제 내역 조회 (Get All Payment Histories)
- **URL**: `/api/payment-history`
- **HTTP Method**: GET

#### Response
```json
{
  "status": "success",
  "data": [
    {
      "paymentId": 1,
      "reservationPK": 1,
      "amount": 10000,
      "paymentDate": "2023-10-01T12:00:00"
    }
  ],
  "message": "All payment histories"
}
```

## 자리 배치 (Placement)

### 자리 배치 등록 (Create Placement)
- **URL**: `/api/placement`
- **HTTP Method**: POST

#### Request Body
```json
{
    "storePK": 1,
    "layout": {
        "1": {
            "x": 10,
            "y": 10,
            "table": 4,    // 최대 인원
            "min": 2,      // 최소 인원
            "status": 0    // 0=빈자리, 1=예약, 2=사용중
        },
        "2": {
            "x": 20,
            "y": 20,
            "table": 2,
            "min": 1,
            "status": 0
        }
    },
    "layoutSize": 1       // 1="1~20", 2="21~40", 3="41~60"
}
```

#### Response
```json
{
    "status": "success",
    "data": {
        "placementPK": 1,
        "storePK": 1,
        "layout": {
            "1": {
                "x": 10,
                "y": 10,
                "table": 4,
                "min": 2,
                "status": 0
            },
            "2": {
                "x": 20,
                "y": 20,
                "table": 2,
                "min": 1,
                "status": 0
            }
        },
        "layoutSize": 1,
        "createdDate": "2024-01-01T12:00:00",
        "updatedDate": "2024-01-01T12:00:00"
    },
    "message": "자리 배치가 생성되었습니다."
}
```

### 자리 배치 조회 (Get Placement by Store)
- **URL**: `/api/placement/{storePK}`
- **HTTP Method**: GET

#### Response
```json
{
    "status": "success",
    "data": {
        "placementPK": 1,
        "storePK": 1,
        "layout": {
            "1": {
                "x": 10,
                "y": 10,
                "table": 4,
                "min": 2,
                "status": 0
            },
            "2": {
                "x": 20,
                "y": 20,
                "table": 2,
                "min": 1,
                "status": 1
            }
        },
        "layoutSize": 1,
        "createdDate": "2024-01-01T12:00:00",
        "updatedDate": "2024-01-01T12:00:00"
    },
    "message": "가게의 자리 배치 정보를 조회했습니다."
}
```

### 자리 배치 수정 (Update Placement)
- **URL**: `/api/placement/{placementPK}`
- **HTTP Method**: PUT

#### Request Body
```json
{
    "layout": {
        "1": {
            "x": 10,
            "y": 10,
            "table": 4,
            "min": 2,
            "status": 1    // 상태만 업데이트 가능
        },
        "2": {
            "x": 20,
            "y": 20,
            "table": 2,
            "min": 1,
            "status": 2
        }
    }
}
```

#### Response
```json
{
    "status": "success",
    "data": {
        "placementPK": 1,
        "storePK": 1,
        "layout": {
            "1": {
                "x": 10,
                "y": 10,
                "table": 4,
                "min": 2,
                "status": 1
            },
            "2": {
                "x": 20,
                "y": 20,
                "table": 2,
                "min": 1,
                "status": 2
            }
        },
        "layoutSize": 1,
        "createdDate": "2024-01-01T12:00:00",
        "updatedDate": "2024-01-01T12:30:00"
    },
    "message": "자리 배치가 업데이트되었습니다."
}
```

#### 테이블 상태 설명
- **0 (빈자리)**: 예약 가능한 상태
- **1 (예약)**: 예약된 상태
- **2 (사용중)**: 현재 사용 중인 상태

## 예약 (Reservation)

### 예약 상태 (Reservation Status)
- **pending**: 대기 중 (예약 생성 시 기본값)
- **cancelled**: 취소됨 (사용자가 취소한 예약)
- **completed**: 완료됨 (예약 시간이 1시간 지난 예약)

### 예약 생성 (Create Reservation)
- **URL**: `/api/reservations/create`
- **HTTP Method**: POST

#### 예약 번호 생성 규칙
- **가게별 독립**: 각 가게마다 별도의 예약 번호 체계
- **일일 리셋**: 매일 00시(자정)에 예약 번호가 1번으로 리셋
- **순차 증가**: 같은 가게에서 같은 날 생성된 예약은 1, 2, 3... 순서로 증가
- **예시**: 
  - A가게 2024-03-25: 1번, 2번, 3번 예약
  - A가게 2024-03-26: 1번, 2번 예약 (다시 1부터 시작)
  - B가게 2024-03-25: 1번, 2번 예약 (A가게와 독립적)

#### Request Body
```json
{
    "storePK": 1,
    "reservationTime": "2024-03-25T18:00:00",
    "menu": {
        "1": {
            "name": "아메리카노",
            "price": 4000,
            "quantity": 2
        },
        "2": {
            "name": "카페라떼",
            "price": 4500,
            "quantity": 1
        }
    },
    "tableNumber": 5,
    "partySize": 3,
    "paymentMethod": "offline"  // "point","offline"
}
```

#### 설명
- `userId`는 JWT 토큰에서 자동으로 추출됨
- `reservationNum`은 서버에서 자동으로 생성됨 (가게별로 매일 1부터 시작)
- `status`는 자동으로 "pending"으로 설정됨

#### Response
```json
{
    "status": "success",
    "data": {
        "reservationPK": 1,
        "userId": 1,
        "storePK": 1,
        "reservationNum": 1,
        "reservationTime": "2024-03-25T18:00:00",
        "menu": {
            "1": {
                "name": "아메리카노",
                "price": 4000,
                "quantity": 2
            },
            "2": {
                "name": "카페라떼",
                "price": 4500,
                "quantity": 1
            }
        },
        "tableNumber": 5,
        "partySize": 3,
        "paymentMethod": "point",
        "status": "pending",
        "createdDate": "2024-03-25T17:00:00",
        "endDate": "2024-03-25T19:00:00"
    },
    "message": "Reservation created"
}
```

### 예약 취소 (Cancel Reservation)
- **URL**: `/api/reservations/cancel/{reservationId}`
- **HTTP Method**: DELETE

#### 설명
- 예약을 삭제하지 않고 status를 "cancelled"로 변경
- 예약 시간 30분 전까지만 취소 가능
- 이미 취소된 예약은 다시 취소할 수 없음

#### Response
```json
{
    "status": "success",
    "message": "Reservation cancelled successfully"
}
```

#### Error Response
```json
{
    "status": "error",
    "code": "RESERVATION_ALREADY_CANCELLED",
    "message": "이미 취소된 예약입니다."
}
```


### 만료된 예약 완료 처리 (Complete Expired Reservations)
- **URL**: `/api/reservations/complete-expired`
- **HTTP Method**: POST

#### 설명
- 예약 시간이 1시간 지난 pending 예약들을 completed로 변경
- **서버에서 매시간 자동으로 실행됨** (스케줄러)
- 수동으로도 호출 가능 (테스트용)

#### Response
```json
{
    "status": "success",
    "data": "Expired reservations completed: 3",
    "message": "success"
}
```

### 가게별 예약 목록 조회 (Get Owner Reservations)
- **URL**: `/api/reservations/owner/{storeId}`
- **HTTP Method**: GET

#### Response
```json
{
    "status": "success",
    "data": [
        {
            "reservationPK": 1,
            "userId": 1,
            "storePK": 1,
            "reservationNum": 1,
            "reservationTime": "2024-03-25T18:00:00",
            "menu": {
                "1": {
                    "name": "아메리카노",
                    "price": 4000,
                    "quantity": 2
                }
            },
            "tableNumber": 5,
            "partySize": 3,
            "paymentMethod": "point",
            "status": "confirmed",
            "createdDate": "2024-03-25T17:00:00",
            "endDate": "2024-03-25T19:00:00"
        }
    ],
    "message": "Owner reservations retrieved successfully"
}
```

### 사용자별 예약 목록 조회 (Get User Reservations)
- **URL**: `/api/reservations/user`
- **HTTP Method**: GET

#### 설명
- 현재 로그인한 사용자의 예약 목록을 조회
- JWT 토큰에서 사용자 정보를 자동으로 추출

#### Response
```json
{
    "status": "success",
    "data": [
        {
            "reservationPK": 1,
            "userId": 1,
            "storePK": 1,
            "reservationNum": 1,
            "reservationTime": "2024-03-25T18:00:00",
            "menu": {
                "1": {
                    "name": "아메리카노",
                    "price": 4000,
                    "quantity": 2
                }
            },
            "tableNumber": 5,
            "partySize": 3,
            "paymentMethod": "point",
            "status": "confirmed",
            "createdDate": "2024-03-25T17:00:00",
            "endDate": "2024-03-25T19:00:00"
        }
    ],
    "message": "User reservations retrieved successfully"
}
```

### 예약 상세 정보 조회 (Get Reservation Details)
- **URL**: `/api/reservations/details/{reservationId}`
- **HTTP Method**: GET

#### Response
```json
{
    "status": "success",
    "data": {
        "reservationPK": 1,
        "userId": 1,
        "storePK": 1,
        "reservationNum": 1,
        "reservationTime": "2024-03-25T18:00:00",
        "menu": {
            "1": {
                "name": "아메리카노",
                "price": 4000,
                "quantity": 2
            }
        },
        "tableNumber": 5,
        "partySize": 3,
        "paymentMethod": "point",
        "status": "confirmed",
        "createdDate": "2024-03-25T17:00:00",
        "endDate": "2024-03-25T19:00:00"
    },
    "message": "Reservation details retrieved successfully"
}
```

#### 주의사항
1. 예약 취소는 예약 시간 30분 전까지만 가능합니다.
2. 예약 상태는 "pending"(대기), "confirmed"(확정), "cancelled"(취소) 중 하나여야 합니다.
3. 결제 방법은 "point"(포인트) 또는 "offline"(현장결제) 중 하나여야 합니다.
4. 예약 시간은 현재 시간 이후여야 합니다.
5. 예약 인원은 해당 테이블의 최대 인원을 초과할 수 없습니다.

### 시간대별 예약 가능 좌석 조회 (Get Available Time Slots)
- **URL**: `/api/reservations/store/{storePK}/available-slots?date={date}`
- **HTTP Method**: GET

#### 설명
- 특정 날짜의 시간대별 예약 가능한 좌석 수를 1시간 단위로 조회
- 가게의 영업시간에 따라 시간대가 자동으로 생성됨
- 휴무일(정기/임시)은 오류 반환
- 취소되지 않은 예약만 좌석 수에 반영됨

#### Request Parameters
- **storePK** (Path): 가게 ID
- **date** (Query): 조회할 날짜 (형식: `YYYY-MM-DD`, 예: `2024-10-24`)

#### Response
```json
{
    "status": "success",
    "data": {
        "date": "2024-10-24",
        "openTime": "11:00",
        "closeTime": "22:00",
        "timeSlots": [
            {
                "time": "11:00",
                "availableSeats": 20
            },
            {
                "time": "12:00",
                "availableSeats": 15
            },
            {
                "time": "13:00",
                "availableSeats": 8
            },
            {
                "time": "14:00",
                "availableSeats": 10
            },
            {
                "time": "15:00",
                "availableSeats": 18
            },
            {
                "time": "16:00",
                "availableSeats": 20
            },
            {
                "time": "17:00",
                "availableSeats": 12
            },
            {
                "time": "18:00",
                "availableSeats": 5
            },
            {
                "time": "19:00",
                "availableSeats": 3
            },
            {
                "time": "20:00",
                "availableSeats": 7
            },
            {
                "time": "21:00",
                "availableSeats": 16
            }
        ]
    },
    "message": "Available time slots fetched"
}
```

#### Error Response (휴무일)
```json
{
    "status": "error",
    "code": "ERROR-0213",
    "message": "해당 날짜는 휴무일입니다."
}
```

#### Error Response (영업시간 미설정)
```json
{
    "status": "error",
    "code": "ERROR-0214",
    "message": "영업시간이 설정되지 않았습니다."
}
```

#### 로직 설명
1. **총 좌석 수 계산**: 자리 배치(Placement) 정보에서 모든 테이블의 최대 인원을 합산
2. **예약된 좌석 수 계산**: 각 시간대(1시간 단위)에 해당하는 예약의 `partySize` 합산
3. **가용 좌석 수**: `총 좌석 수 - 예약된 좌석 수` (최소 0)
4. **휴무일 체크**: 
   - 정기 휴무일: `regularHolidays`에서 요일별 확인
   - 임시 휴무일: `temporaryHolidays` 리스트에 날짜 포함 여부 확인

## 리뷰 (Review)

### 리뷰 생성 (Create Review)
- **URL**: `/api/review/add`
- **HTTP Method**: POST
- **Content-Type**: `multipart/form-data`

#### 설명
- JWT 토큰에서 사용자 정보를 자동으로 추출
- 이미지는 선택사항이며, 최대 5개까지 업로드 가능
- rating은 0~10 범위로 저장되며, 응답에서는 0~5로 변환되어 반환

#### Request Body
```form-data
data: {
  "storePK": 1,
  "rating": 8,  // 0~10 범위
  "content": "맛있어요!"
}
images: [파일1, 파일2, ...]  // 선택사항, 최대 5개
```

#### Response
```json
{
  "status": "success",
  "data": {
    "reviewId": 1,
    "storeId": 1,
    "user": "사용자명",
    "rating": 4.0,  // 0~5 범위로 변환
    "content": "맛있어요!",
    "image": [
      "store/1/review/uuid1_review.jpg",
      "store/1/review/uuid2_review.png"
    ]
  },
  "message": "Review submitted"
}
```

### 가게별 리뷰 조회 (Get Reviews by Store)
- **URL**: `/api/review/store/{storePK}`
- **HTTP Method**: GET

#### 설명
- 특정 가게의 모든 리뷰를 생성일 기준 내림차순으로 조회
- rating은 0~5 범위로 반환 (DB에는 0~10으로 저장)

#### Response
```json
{
  "status": "success",
  "data": [
    {
      "reviewPK": 1,
      "userName": "사용자명",
      "rating": 4.0,  // 0~5 범위
      "content": "맛있어요!",
      "image": [
        "store/1/review/uuid1_review.jpg",
        "store/1/review/uuid2_review.png"
      ],
      "createdDate": "2024-03-25T17:00:00"
    }
  ],
  "message": "Reviews fetched"
}
```

### 가게별 리뷰 통계 조회 (Get Review Statistics by Store)
- **URL**: `/api/review/store/{storePK}/stats`
- **HTTP Method**: GET

#### 설명
- 특정 가게의 리뷰 통계를 조회
- 총 리뷰 수, 평균 평점, 각 평점별(1~5점) 리뷰 개수를 반환
- rating은 0~5 범위로 반환 (DB에는 0~10으로 저장)

#### Response
```json
{
  "status": "success",
  "data": {
    "totalReviews": 42,
    "averageRating": 4.2,
    "rating1Count": 2,
    "rating2Count": 3,
    "rating3Count": 8,
    "rating4Count": 15,
    "rating5Count": 14
  },
  "message": "Review statistics fetched"
}
```

#### 데이터 설명
- **totalReviews**: 해당 가게의 총 리뷰 수
- **averageRating**: 평균 평점 (0.0 ~ 5.0, 소수점 첫째자리까지)
- **rating1Count**: 1점(0.0~1.0) 리뷰 개수
- **rating2Count**: 2점(1.5~2.0) 리뷰 개수
- **rating3Count**: 3점(2.5~3.0) 리뷰 개수
- **rating4Count**: 4점(3.5~4.0) 리뷰 개수
- **rating5Count**: 5점(4.5~5.0) 리뷰 개수

## 파일 서빙 (File Serving)

### 이미지 파일 서빙
- **URL**: `/api/files/**`
- **HTTP Method**: GET

#### 설명
유연한 경로 매칭을 통해 다양한 깊이의 파일 경로를 지원합니다.

#### 지원하는 경로 패턴
- **멤버 프로필**: `/api/files/member/profile/{uuid}_profile.{ext}`
- **매장 이미지**: `/api/files/store/{storeId}/{uuid}_store.{ext}`
- **메뉴 이미지**: `/api/files/store/{storeId}/menu/{uuid}_menu.{ext}`
- **리뷰 이미지**: `/api/files/store/{storeId}/review/{uuid}_review.{ext}`

#### 지원하는 파일 형식
- **확장자**: `.png`, `.jpg`, `.jpeg`, `.gif`, `.webp`
- **MIME 타입**: `image/png`, `image/jpeg`, `image/gif`, `image/webp`
- **최대 파일 크기**: 100MB

#### 보안 조치
- 경로 순회 공격 방지 (`..`, `//`, `\\` 차단)
- 허용된 경로 패턴만 접근 가능
- 파일 확장자 및 MIME 타입 검증
- 파일 크기 제한

#### Response
```json
// 성공 시: 이미지 파일 바이너리 데이터
// 실패 시: 404 Not Found 또는 400 Bad Request
```

#### 예시
```
GET /api/files/member/profile/a1b2c3d4-e5f6-7890-abcd-ef1234567890_profile.png
GET /api/files/store/1/menu/xyz789abc123def456_menu.jpg
GET /api/files/store/1/abc123def456_store.png
```

## 게시판 (Board)

### 게시글 생성 (Create Board)
- **URL**: `/api/board`
- **HTTP Method**: POST

#### Request Body
```json
{
  "title": "새로운 게시글",
  "content": "게시글 내용입니다."
}
```

#### Response
```json
{
  "status": "success",
  "data": {
    "boardId": 1,
    "title": "새로운 게시글",
    "content": "게시글 내용입니다."
  },
  "message": "Board created successfully"
}
```

### 모든 게시글 조회 (Get All Boards)
- **URL**: `/api/board`
- **HTTP Method**: GET

#### Response
```json
{
  "status": "success",
  "data": [
    {
      "boardId": 1,
      "title": "새로운 게시글",
      "content": "게시글 내용입니다."
    }
  ],
  "message": "All boards"
}
```

## 즐겨찾기 (Favorite)

### 즐겨찾기 추가 (Add Favorite)
- **URL**: `/api/favorites/{storeId}`
- **HTTP Method**: POST

#### Response
```json
{
  "status": "success",
  "data": {
    "favoriteId": 1,
    "store": {
      "storeName": "맛있는 한식당",
      "location": "서울시 강남구",
      "description": "한식 전문점입니다.",
      "businessRegistrationNumber": "123-45-67890",
      "bank": "국민은행",
      "accountNumber": "123456-78-901234",
      "depositor": "김사장",
      "businessHours": {
        "월요일": "09:00 - 22:00",
        "화요일": "09:00 - 22:00"
      },
      "image": [
        "https://example.com/image1.jpg",
        "https://example.com/image2.jpg"
      ],
      "category": ["KOREANFOOD", "MEAT"],
      "viewCount": 10,
      "averageRating": 4.5,
      "favoriteCount": 0,
      "reservationCount": 0,
      "latitude": 37.5665,
      "longitude": 126.9780
    }
  },
  "message": "Favorite added successfully"
}
```

### 즐겨찾기 삭제 (Remove Favorite)
- **URL**: `/api/favorites/{storeId}`
- **HTTP Method**: DELETE

#### Response
```json
{
  "status": "success",
  "message": "Favorite removed successfully"
}
```

### 사용자 즐겨찾기 목록 조회 (Get Favorite List)
- **URL**: `/api/favorites/{userId}`
- **HTTP Method**: GET

#### Response
```json
{
  "status": "success",
  "data": [
    {
      "favoriteId": 1,
      "store": {
        "storeName": "맛있는 한식당",
        "location": "서울시 강남구",
        "description": "한식 전문점입니다.",
        "businessRegistrationNumber": "123-45-67890",
        "bank": "국민은행",
        "accountNumber": "123456-78-901234",
        "depositor": "김사장",
        "businessHours": {
          "월요일": "09:00 - 22:00",
          "화요일": "09:00 - 22:00"
        },
        "image": [
          "https://example.com/image1.jpg",
          "https://example.com/image2.jpg"
        ],
        "category": ["KOREANFOOD", "MEAT"],
        "viewCount": 10,
        "averageRating": 4.5,
        "favoriteCount": 0,
        "reservationCount": 0,
        "latitude": 37.5665,
        "longitude": 126.9780
      }
    }
  ],
  "message": "Favorite list retrieved successfully"
}
```

## 통계 (Statistics)

### 메뉴 판매율 통계 (Menu Sales Statistics)
- **URL**: `/api/web/statistics/menu-sales/{storeId}`
- **HTTP Method**: GET
- **설명**: 가게의 확정된 예약에서 판매된 메뉴들의 판매율을 퍼센트로 계산하여 반환합니다.

#### Response
```json
{
    "status": "success",
    "data": {
        "아이스 아메리카노": "20",
        "카페라떼": "50",
        "카페모카": "30"
    },
    "message": "메뉴 판매율 통계를 조회했습니다."
}
```

#### 주의사항
1. 통계는 'confirmed' 상태의 예약만을 대상으로 계산됩니다.
2. 각 메뉴의 판매율은 소수점을 버리고 정수로 반환됩니다.
3. 판매된 메뉴가 없는 경우 빈 객체를 반환합니다.