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
    "price": 4000,
    "image": ["https://example.com/menu_image.jpg"],
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
      "name": "아메리카노",
      "section": "음료",
      "price": 4000,
      "image": ["https://example.com/menu_image.jpg"],
      "Description": "신선한 아메리카노",
      "available": true
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
    "price": 4500,
    "image": ["https://example.com/latte_image.jpg"],
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
    "price": 4500,
    "image": ["https://example.com/latte_image.jpg"],
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

## 배치 (Placement)

### 배치 생성 (Create Placement)
- **URL**: `/api/placement`
- **HTTP Method**: POST

#### Request Body
```json
{
  "storePK": 1,
  "layout": "배치 레이아웃"
}
```

#### Response
```json
{
  "status": "success",
  "data": {
    "placementId": 1,
    "storePK": 1,
    "layout": "배치 레이아웃",
    "createdDate": "2023-10-01T12:00:00",
    "updatedDate": "2023-10-01T12:00:00"
  },
  "message": "Placement created"
}
```

### 모든 배치 조회 (Get All Placements)
- **URL**: `/api/placement`
- **HTTP Method**: GET

#### Response
```json
{
  "status": "success",
  "data": [
    {
      "placementId": 1,
      "storePK": 1,
      "layout": "배치 레이아웃",
      "createdDate": "2023-10-01T12:00:00",
      "updatedDate": "2023-10-01T12:00:00"
    }
  ],
  "message": "All placements"
}
```

## 예약 (Reservation)

### 예약 생성 (Create Reservation)
- **URL**: `/api/reservation`
- **HTTP Method**: POST

#### Request Body
```json
{
  "date": "2023-10-01",
  "time": "18:00",
  "storeId": 1,
  "tableNumber": 5
}
```

#### Response
```json
{
  "status": "success",
  "data": {
    "reservationId": 1,
    "date": "2023-10-01",
    "time": "18:00",
    "storeId": 1,
    "tableNumber": 5
  },
  "message": "Reservation created successfully"
}
```

### 모든 예약 조회 (Get All Reservations)
- **URL**: `/api/reservation`
- **HTTP Method**: GET

#### Response
```json
{
  "status": "success",
  "data": [
    {
      "reservationId": 1,
      "date": "2023-10-01",
      "time": "18:00",
      "storeId": 1,
      "tableNumber": 5
    }
  ],
  "message": "All reservations"
}
```

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

## 파일 (File)

### 파일 서빙 (Serve File)
- **URL**: `/api/files/{folder1}/{folder2}/{filename}`
- **HTTP Method**: GET

#### Response
- 파일의 바이너리 데이터

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