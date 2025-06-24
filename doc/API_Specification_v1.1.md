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
      "화요일": "09:00 - 22:00"
    }
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
      "화요일": "09:00 - 22:00"
    },
    "image": [
      "https://example.com/image1.jpg",
      "https://example.com/image2.jpg"
    ],
    "category": ["KOREANFOOD", "MEAT"],
    "viewCount": 0,
    "averageRating": 0.0,
    "favoriteCount": 0,
    "reservationCount": 0
  },
  "message": "Store registration successful"
}
```

### 가게 정보 수정 (Update Store)
- **URL**: `/api/store/update/{storeId}`
- **HTTP Method**: PUT

#### Request Parameters
```json
{
  "data": {
    "storeName": "업데이트된 가게",
    "location": "Busan",
    "description": "업데이트된 설명",
    "businessHours": {
      "월요일": "10:00 - 20:00",
      "화요일": "10:00 - 20:00"
    },
    "category": ["RESTAURANT"],
    "bank": "신한은행",
    "accountNumber": "987654-32-109876",
    "depositor": "박사장"
  },
  "images": ["<MultipartFile>"]
}
```

#### Response
```json
{
  "status": "success",
  "data": {
    "storeName": "업데이트된 가게",
    "location": "Busan",
    "description": "업데이트된 설명",
    "businessHours": {
      "월요일": "10:00 - 20:00",
      "화요일": "10:00 - 20:00"
    },
    "category": ["RESTAURANT"],
    "bank": "신한은행",
    "accountNumber": "987654-32-109876",
    "depositor": "박사장",
    "image": [
      "https://example.com/updated_image1.jpg",
      "https://example.com/updated_image2.jpg"
    ]
  },
  "message": "Store update successful"
}
```

### 사용자 가게 목록 조회 (Get User Stores)
- **URL**: `/api/store/my`
- **HTTP Method**: GET

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
      "businessHours": {
        "월요일": "09:00 - 22:00",
        "화요일": "09:00 - 22:00"
      },
      "image": [
        "https://example.com/image1.jpg",
        "https://example.com/image2.jpg"
      ],
      "category": ["KOREANFOOD", "MEAT"],
      "viewCount": 0,
      "averageRating": 4.5,
      "favoriteCount": 0,
      "reservationCount": 0
    }
  ],
  "message": "View My Stores"
}
```

### 모든 가게 목록 조회 (Get All Stores)
- **URL**: `/api/store/all`
- **HTTP Method**: GET
- **Query Parameters**:
  - `sortBy` (optional): 정렬 기준
    - `favorite`: 찜 추가한 인원 많은 순
    - `rating`: 리뷰 평점 순
    - `reservation`: 예약 수 많은 순
    - 기본 순서: 쿼리 파라미터를 생략하거나 다른 값을 사용

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
      "businessHours": {
        "월요일": "09:00 - 22:00",
        "화요일": "09:00 - 22:00"
      },
      "image": [
        "https://example.com/image1.jpg",
        "https://example.com/image2.jpg"
      ],
      "category": ["KOREANFOOD", "MEAT"],
      "viewCount": 0,
      "averageRating": 4.5,
      "favoriteCount": 0,
      "reservationCount": 0
    },
    {
      "storeName": "다른 가게",
      "location": "Busan",
      "description": "다른 설명",
      "businessRegistrationNumber": "987-65-43210",
      "bank": "신한은행",
      "accountNumber": "987654-32-109876",
      "depositor": "박사장",
      "businessHours": {
        "월요일": "10:00 - 20:00",
        "화요일": "10:00 - 20:00"
      },
      "image": [
        "https://example.com/other_image1.jpg",
        "https://example.com/other_image2.jpg"
      ],
      "category": ["RESTAURANT"],
      "viewCount": 5,
      "averageRating": 3.8,
      "favoriteCount": 0,
      "reservationCount": 0
    }
  ],
  "message": "View All Stores"
}
```

### 가게 ID로 가게 조회 (Get Store By ID)
- **URL**: `/api/store/{storeId}`
- **HTTP Method**: GET

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
  },
  "message": "View Store By Id"
}
```

### 카테고리별 가게 조회 (Get Stores By Category)
- **URL**: `/api/store/all/category/{category}`
- **HTTP Method**: GET
- **Query Parameters**:
  - `sortBy` (optional): 정렬 기준
    - `favorite`: 찜 추가한 인원 많은 순
    - `rating`: 리뷰 평점 순
    - `reservation`: 예약 수 많은 순
    - 기본 순서: 쿼리 파라미터를 생략하거나 다른 값을 사용

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
      "businessHours": {
        "월요일": "09:00 - 22:00",
        "화요일": "09:00 - 22:00"
      },
      "image": [
        "https://example.com/image1.jpg",
        "https://example.com/image2.jpg"
      ],
      "category": ["KOREANFOOD", "MEAT"],
      "viewCount": 0,
      "averageRating": 4.5,
      "favoriteCount": 10,
      "reservationCount": 0
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
        "화요일": "09:00 - 22:00"
      },
      "image": ["store/image1.png", "store/image2.png"],
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

#### Response
```json
{
  "status": "success",
  "data": {
    "currentReservations": 0,
    "estimatedWaitTime": 1,
    "weeklyStats": [
      {
        "day": "월",
        "averageTeams": 0
      },
      {
        "day": "화",
        "averageTeams": 0
      }
      // ... 다른 요일 데이터
    ]
  },
  "message": "Reservation statistics retrieved successfully"
}
```

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
        "createdDate": "2024-03-25T21:30:00",
        "updatedDate": "2024-03-25T21:30:00"
    },
    "message": "자리 배치가 생성되었습니다."
}
```

### 가게별 자리 배치 조회 (Get Placement by Store)
- **URL**: `/api/placement/store/{storePK}`
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
        "createdDate": "2024-03-25T21:30:00",
        "updatedDate": "2024-03-25T21:30:00"
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
            "status": 2    // 0=빈자리, 1=예약, 2=사용중
        },
        "2": {
            "status": 1
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
                "status": 2
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
        "createdDate": "2024-03-25T21:30:00",
        "updatedDate": "2024-03-25T21:35:00"
    },
    "message": "자리 배치가 업데이트되었습니다."
}
```

#### 자동 자리 상태 업데이트
- **설명**: 시스템이 30분마다 자동으로 실행하여 예약된 자리의 상태를 업데이트합니다.
- **동작 방식**:
  1. 현재 시간으로부터 30분 이내의 예약을 조회
  2. 각 예약에 대해:
     - 해당 가게의 자리 배치 정보 조회
     - 예약된 테이블 번호와 일치하는 자리의 상태가 빈자리(0)인 경우
     - 자동으로 예약 상태(1)로 변경
- **상태 코드**:
  - `0`: 빈자리
  - `1`: 예약됨
  - `2`: 사용중

#### 주의사항
1. 자리 배치 수정 시 테이블의 위치(x, y), 최대/최소 인원은 변경할 수 없습니다.
2. 자리 상태는 0(빈자리), 1(예약), 2(사용중) 중 하나여야 합니다.
3. 예약 시간 30분 전부터 해당 테이블의 상태가 자동으로 예약 상태(1)로 변경됩니다.
4. layoutSize는 1(1~20), 2(21~40), 3(41~60) 중 하나여야 합니다.

## 예약 (Reservation)

### 예약 생성 (Create Reservation)
- **URL**: `/api/reservations/create`
- **HTTP Method**: POST

#### Request Body
```json
{
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
    "paymentMethod": "offline",  // "point","offline"
    "status": "pending"      // "pending", "confirmed", "cancelled"
}
```

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
        "status": "confirmed",
        "createdDate": "2024-03-25T17:00:00",
        "endDate": "2024-03-25T19:00:00"
    },
    "message": "Reservation created"
}
```

### 예약 취소 (Cancel Reservation)
- **URL**: `/api/reservations/cancel/{reservationId}`
- **HTTP Method**: DELETE

#### Response
```json
{
    "status": "success",
    "message": "Reservation cancelled successfully"
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
- **URL**: `/api/reservations/user/{userId}`
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

## 리뷰 (Review)

### 리뷰 생성 (Create Review)
- **URL**: `/api/review`
- **HTTP Method**: POST

#### Request Body
```json
{
  "userPK": 1,
  "storePK": 1,
  "image": [
    "https://example.com/review_image1.jpg",
    "https://example.com/review_image2.jpg"
  ],
  "rating": 4.5,
  "content": "맛있어요!"
}
```

#### Response
```json
{
  "status": "success",
  "data": {
    "reviewId": 1,
    "userPK": 1,
    "storePK": 1,
    "image": [
      "https://example.com/review_image1.jpg",
      "https://example.com/review_image2.jpg"
    ],
    "rating": 4.5,
    "content": "맛있어요!",
    "createdDate": "2023-10-01T12:00:00"
  },
  "message": "Review created"
}
```

### 모든 리뷰 조회 (Get All Reviews)
- **URL**: `/api/review`
- **HTTP Method**: GET

#### Response
```json
{
  "status": "success",
  "data": [
    {
      "reviewId": 1,
      "userPK": 1,
      "storePK": 1,
      "image": [
        "https://example.com/review_image1.jpg",
        "https://example.com/review_image2.jpg"
      ],
      "rating": 4.5,
      "content": "맛있어요!",
      "createdDate": "2023-10-01T12:00:00"
    }
  ],
  "message": "All reviews"
}
```

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
- **리뷰 이미지**: `/api/files/review/{reviewId}/{uuid}_review.{ext}`

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