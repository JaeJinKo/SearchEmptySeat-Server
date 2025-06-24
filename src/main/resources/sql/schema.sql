CREATE TABLE tbl_members (
    userId BIGSERIAL PRIMARY KEY,
    email VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    name VARCHAR(255) NOT NULL,
    phone VARCHAR(20),
    location VARCHAR(255),
    userType VARCHAR(20) NOT NULL,
    points INT DEFAULT 0,
    image JSON,
    createdDate TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updatedDate TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
CREATE TABLE tbl_favorite (
    favoritePK BIGSERIAL PRIMARY KEY,
    userId BIGINT NOT NULL,
    storeID BIGINT NOT NULL,
    favoriteDate TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (userId) REFERENCES tbl_members(userId),
    FOREIGN KEY (storeID) REFERENCES tbl_store(storePK)
);
CREATE TABLE tbl_charge (
    chargePK BIGSERIAL PRIMARY KEY,
    userId BIGINT NOT NULL,
    amount INT NOT NULL,
    description VARCHAR(255),
    chargeDate TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (userId) REFERENCES tbl_members(userId)
);
CREATE TABLE tbl_store (
    storePK BIGSERIAL PRIMARY KEY,
    userId BIGINT NOT NULL,
    storeName VARCHAR(255) NOT NULL,
    location VARCHAR(255) NOT NULL,
    description VARCHAR(255),
    businessRegistrationNumber VARCHAR(50) NOT NULL,
    bank VARCHAR(50),
    accountNumber VARCHAR(50),
    depositor VARCHAR(50),
    businessHours JSON,
    image JSON,
    createdDate TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updatedDate TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    latitude DOUBLE PRECISION,
    longitude DOUBLE PRECISION,
    FOREIGN KEY (userId) REFERENCES tbl_members(userId)
);
CREATE TABLE tbl_storeViews (
    viewsPK BIGSERIAL PRIMARY KEY,
    storeId BIGINT NOT NULL,
    view_count INT DEFAULT 0,
    lastViewedDate TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updatedDate TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (storeId) REFERENCES tbl_store(storePK)
);
CREATE TABLE tbl_placement (
    placementPK BIGSERIAL PRIMARY KEY,
    storePK BIGINT NOT NULL,
    layout JSON,
    layoutSize INT,
    createdDate TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updatedDate TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (storePK) REFERENCES tbl_store(storePK)
);
CREATE TABLE tbl_menu_section (
    sectionPK BIGSERIAL PRIMARY KEY,
    storePK BIGINT NOT NULL,
    name VARCHAR(255) NOT NULL,
    priority INT NOT NULL,
    createdDate TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updatedDate TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (storePK) REFERENCES tbl_store(storePK)
);
CREATE TABLE tbl_menu (
    menuPK BIGSERIAL PRIMARY KEY,
    storePK BIGINT NOT NULL,
    name VARCHAR(255) NOT NULL,
    sectionPK BIGINT,
    image JSON,
    price INT NOT NULL,
    description VARCHAR(255),
    isAvailable BOOLEAN DEFAULT TRUE,
    createdDate TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (storePK) REFERENCES tbl_store(storePK),
    FOREIGN KEY (sectionPK) REFERENCES tbl_menu_section(sectionPK)
);
CREATE TABLE tbl_reservation (
    reservationPK BIGSERIAL PRIMARY KEY,
    userId BIGINT NOT NULL,
    storePK BIGINT NOT NULL,
    reservationNum INT NOT NULL,
    reservationTime TIMESTAMP NOT NULL,
    table_num INT,
    menu JSON,
    seats VARCHAR(255),
    partySize INT NOT NULL,
    paymentMethod VARCHAR(20) NOT NULL,
    status VARCHAR(20) DEFAULT 'pending',
    createdDate TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    endDate TIMESTAMP,
    FOREIGN KEY (userId) REFERENCES tbl_members(userId),
    FOREIGN KEY (storePK) REFERENCES tbl_store(storePK)
);
CREATE TABLE tbl_paymentHistory (
    paymentHistory BIGSERIAL PRIMARY KEY,
    reservationPK BIGINT NOT NULL,
    amount INT NOT NULL,
    paymentDate TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (reservationPK) REFERENCES tbl_reservation(reservationPK)
);
CREATE TABLE tbl_review (
    reviewPK BIGSERIAL PRIMARY KEY,
    userPK BIGINT NOT NULL,
    storePK BIGINT NOT NULL,
    image JSON,
    rating INT NOT NULL CHECK (rating BETWEEN 1 AND 5),
    content VARCHAR(255),
    createdDate TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (userPK) REFERENCES tbl_members(userId),
    FOREIGN KEY (storePK) REFERENCES tbl_store(storePK)
);
CREATE TABLE tbl_board (
    boardPK BIGSERIAL PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    content VARCHAR(255) NOT NULL,
    userId BIGINT NOT NULL,
    createdDate TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updatedDate TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    isNotice BOOLEAN DEFAULT FALSE,
    FOREIGN KEY (userId) REFERENCES tbl_members(userId)
);
