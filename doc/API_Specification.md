# API 명세서

## 인증 (Authentication)

### 회원가입 (Sign Up)
- **URL**: `/api/auth/signup`
- **HTTP Method**: POST
- **Request Parameters**:
  - `data`: 사용자 데이터 (JSON 형식의 문자열)
  - `image`: 프로필 이미지 (선택 사항, MultipartFile)
- **Response**: `ApiResponse<Map<String, Object>>`

### 로그인 (Login)
- **URL**: `/api/auth/login`
- **HTTP Method**: POST
- **Request Body**: `LoginRequest` (JSON)
- **Response**: `ApiResponse<Map<String, Object>>`

### 비밀번호 찾기 (Forgot Password)
- **URL**: `/api/auth/forgot-password`
- **HTTP Method**: POST
- **Request Body**: `FindPasswordRequest` (JSON)
- **Response**: `ApiResponse<Map<String, String>>`

## 회원 (Member)

### 회원 정보 수정 (Update My Info)
- **URL**: `/api/member/{userId}`
- **HTTP Method**: PATCH
- **Path Variable**: `userId` (Long)
- **Request Parameters**:
  - `data`: 사용자 데이터 (JSON 형식의 문자열)
  - `image`: 프로필 이미지 (선택 사항, MultipartFile)
- **Response**: `ApiResponse<Map<String, Object>>`

### 회원 정보 조회 (Get My Info)
- **URL**: `/api/member/{userId}`
- **HTTP Method**: GET
- **Path Variable**: `userId` (Long)
- **Response**: `ApiResponse<MyInfoResponse>`

### 비밀번호 변경 (Update Password)
- **URL**: `/api/member/{userId}/password`
- **HTTP Method**: PATCH
- **Path Variable**: `userId` (Long)
- **Request Body**: `{"newPassword": "새 비밀번호"}`
- **Response**: `ApiResponse<String>`

## 가게 (Store)

### 가게 등록 (Register Store)
- **URL**: `/api/store/register`
- **HTTP Method**: POST
- **Request Parameters**:
  - `data`: 가게 데이터 (JSON 형식의 문자열)
  - `images`: 가게 이미지들 (선택 사항, List<MultipartFile>)
- **Response**: `ApiResponse<Map<String, Object>>`

### 가게 정보 수정 (Update Store)
- **URL**: `/api/store/update/{storeId}`
- **HTTP Method**: PUT
- **Path Variable**: `storeId` (Long)
- **Request Parameters**:
  - `data`: 가게 데이터 (JSON 형식의 문자열)
  - `images`: 가게 이미지들 (선택 사항, List<MultipartFile>)
- **Response**: `ApiResponse<Map<String, Object>>`

### 사용자 가게 목록 조회 (Get User Stores)
- **URL**: `/api/store/my`
- **HTTP Method**: GET
- **Response**: `ApiResponse<List<StoreResponse>>`

### 모든 가게 목록 조회 (Get All Stores)
- **URL**: `/api/store/all`
- **HTTP Method**: GET
- **Response**: `ApiResponse<List<StoreResponse>>`

### 가게 ID로 가게 조회 (Get Store By ID)
- **URL**: `/api/store/{storeId}`
- **HTTP Method**: GET
- **Path Variable**: `storeId` (Long)
- **Response**: `ApiResponse<StoreResponse>`

### 카테고리별 가게 조회 (Get Stores By Category)
- **URL**: `/api/store/category/{category}`
- **HTTP Method**: GET
- **Path Variable**: `category` (String)
- **Response**: `ApiResponse<List<StoreResponse>>`

## 메뉴 (Menu)

### 메뉴 추가 (Add Menu)
- **URL**: `/api/menu/add`
- **HTTP Method**: POST
- **Request Parameters**:
  - `data`: 메뉴 데이터 (JSON 형식의 문자열)
  - `image`: 메뉴 이미지 (선택 사항, MultipartFile)
- **Response**: `ApiResponse<Map<String, Object>>`

### 가게별 메뉴 조회 (Get Menu by Store)
- **URL**: `/api/menu/store/{storePK}`
- **HTTP Method**: GET
- **Path Variable**: `storePK` (Long)
- **Response**: `ApiResponse<List<MenuResponse>>`

### 메뉴 수정 (Update Menu)
- **URL**: `/api/menu/update/{menuId}`
- **HTTP Method**: PUT
- **Path Variable**: `menuId` (Long)
- **Request Parameters**:
  - `data`: 메뉴 데이터 (JSON 형식의 문자열)
  - `image`: 메뉴 이미지 (선택 사항, MultipartFile)
- **Response**: `ApiResponse<Map<String, Object>>`

### 메뉴 삭제 (Delete Menu)
- **URL**: `/api/menu/del/{menuId}`
- **HTTP Method**: DELETE
- **Path Variable**: `menuId` (Long)
- **Response**: `ApiResponse<Map<String, Object>>`

### 메뉴 재고 상태 업데이트 (Update Menus Stock)
- **URL**: `/api/menu/outofstock`
- **HTTP Method**: PATCH
- **Request Body**: `OutOfStockRequest` (JSON)
- **Response**: `ApiResponse<List<Map<String,Object>>>`

## 결제 내역 (Payment History)

### 결제 내역 생성 (Create Payment History)
- **URL**: `/api/payment-history`
- **HTTP Method**: POST
- **Request Body**: `PaymentHistoryRequest` (JSON)
- **Response**: `ApiResponse<PaymentHistoryResponse>`

### 모든 결제 내역 조회 (Get All Payment Histories)
- **URL**: `/api/payment-history`
- **HTTP Method**: GET
- **Response**: `ApiResponse<List<PaymentHistoryResponse>>`

## 배치 (Placement)

### 배치 생성 (Create Placement)
- **URL**: `/api/placement`
- **HTTP Method**: POST
- **Request Body**: `PlacementRequest` (JSON)
- **Response**: `ApiResponse<PlacementResponse>`

### 모든 배치 조회 (Get All Placements)
- **URL**: `/api/placement`
- **HTTP Method**: GET
- **Response**: `ApiResponse<List<PlacementResponse>>`

## 예약 (Reservation)

### 예약 생성 (Create Reservation)
- **URL**: `/api/reservation`
- **HTTP Method**: POST
- **Request Body**: `ReservationRequest` (JSON)
- **Response**: `ApiResponse<ReservationResponse>`

### 모든 예약 조회 (Get All Reservations)
- **URL**: `/api/reservation`
- **HTTP Method**: GET
- **Response**: `ApiResponse<List<ReservationResponse>>`

## 리뷰 (Review)

### 리뷰 생성 (Create Review)
- **URL**: `/api/review`
- **HTTP Method**: POST
- **Request Body**: `ReviewRequest` (JSON)
- **Response**: `ApiResponse<ReviewResponse>`

### 모든 리뷰 조회 (Get All Reviews)
- **URL**: `/api/review`
- **HTTP Method**: GET
- **Response**: `ApiResponse<List<ReviewResponse>>`

## 파일 (File)

### 파일 서빙 (Serve File)
- **URL**: `/api/files/{folder1}/{folder2}/{filename}`
- **HTTP Method**: GET
- **Path Variables**:
  - `folder1` (String)
  - `folder2` (String)
  - `filename` (String)
- **Response**: 파일의 바이너리 데이터

## 게시판 (Board)

### 게시글 생성 (Create Board)
- **URL**: `/api/board`
- **HTTP Method**: POST
- **Request Body**: `BoardRequest` (JSON)
- **Response**: `ApiResponse<BoardResponse>`

### 모든 게시글 조회 (Get All Boards)
- **URL**: `/api/board`
- **HTTP Method**: GET
- **Response**: `ApiResponse<List<BoardResponse>>`

## 즐겨찾기 (Favorite)

### 즐겨찾기 추가 (Add Favorite)
- **URL**: `/api/favorites/{storeId}`
- **HTTP Method**: POST
- **Path Variable**: `storeId` (Long)
- **Response**: `ApiResponse<FavoriteResponse>`

### 즐겨찾기 삭제 (Remove Favorite)
- **URL**: `/api/favorites/{storeId}`
- **HTTP Method**: DELETE
- **Path Variable**: `storeId` (Long)
- **Response**: `ApiResponse<String>`

### 사용자 즐겨찾기 목록 조회 (Get Favorite List)
- **URL**: `/api/favorites/{userId}`
- **HTTP Method**: GET
- **Path Variable**: `userId` (Long)
- **Response**: `ApiResponse<List<FavoriteResponse>>` 