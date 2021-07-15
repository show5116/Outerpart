-- 사용자
CREATE TABLE OUTERPARK_USER (
	USER_ID VARCHAR2(20) NOT NULL, -- 아이디,
	USER_PASSWORD CHAR(64) NOT NULL, -- 비밀번호,
	USER_NAME VARCHAR2(20) NOT NULL, -- 이름,
	USER_NICKNAME VARCHAR2(20) NOT NULL, -- 닉네임,
	USER_MAIL VARCHAR2(40) NOT NULL, -- 메일,
	USER_TYPE CHAR(8) NOT NULL -- 타입
);

-- 사용자
ALTER TABLE OUTERPARK_USER
	ADD CONSTRAINT PK_OUTERPARK_USER -- 사용자 기본키
	PRIMARY KEY (
		USER_ID -- 아이디
	);

-- 방
CREATE TABLE ROOM (
	ROOM_NUMBER NUMBER(5,0) NOT NULL, -- 방번호,
	USER_ID VARCHAR2(20) NOT NULL, -- 아이디,
	ROOM_NAME VARCHAR2(20) NOT NULL, -- 방이름,
	MAX_CAPACITY NUMBER(3,0) NOT NULL, -- 수용인원,
	ROOM_COUNT NUMBER(2,0) NOT NULL, -- 방개수,
	BED_COUNT NUMBER(2,0) NOT NULL, -- 침대,
	ROOM_PRICE NUMBER(10,0) NOT NULL, -- 가격,
	CITY VARCHAR2(10) NOT NULL, -- 지역,
	EXPLANATION VARCHAR(200) NULL -- 설명
);

-- 방
ALTER TABLE ROOM
	ADD CONSTRAINT PK_ROOM -- 방 기본키
	PRIMARY KEY (
		ROOM_NUMBER -- 방번호
	);

-- 렌트카
CREATE TABLE CAR (
	CAR_NUMBER VARCHAR2(10) NOT NULL, -- 차번호,
	USER_ID VARCHAR2(20) NOT NULL, -- 아이디,
	CAR_KIND VARCHAR2(20) NOT NULL, -- 차종류,
	CAR_PRICE NUMBER(10,0) NOT NULL, -- 가격,
	CAR_SEATS NUMBER(2,0) NOT NULL, -- 인승,
	CITY VARCHAR2(10) NOT NULL, -- 지역,
	CAR_COLOR VARCHAR2(20) NULL -- 차색상
);

-- 렌트카
ALTER TABLE CAR
	ADD CONSTRAINT PK_CAR -- 렌트카 기본키
	PRIMARY KEY (
		CAR_NUMBER -- 차번호
	);

-- 관광명소
CREATE TABLE TOUR (
	TOUR_NUMBER NUMBER(5,0) NOT NULL, -- 투어번호,
	USER_ID VARCHAR2(20) NOT NULL, -- 아이디,
	TOUR_NAME VARCHAR2(20) NOT NULL, -- 이름,
	TOUR_TIME VARCHAR2(10) NOT NULL, -- 투어시간,
	TOUR_PRICE NUMBER(10,0) NOT NULL, -- 가격,
	CITY VARCHAR2(10) NOT NULL, -- 지역,
	EXPLANATION VARCHAR2(100) NULL -- 설명
);

-- 관광명소
ALTER TABLE TOUR
	ADD CONSTRAINT PK_TOUR -- 관광명소 기본키
	PRIMARY KEY (
		TOUR_NUMBER -- 투어번호
	);

-- 패키지
CREATE TABLE PACK (
	PACK_NUM NUMBER(5,0) NOT NULL, -- 패키지번호,
	TOUR_NUMBER NUMBER(5,0) NOT NULL, -- 투어번호,
	ROOM_NUMBER NUMBER(5,0) NOT NULL, -- 방번호,
	CAR_NUMBER VARCHAR2(10) NOT NULL, -- 차번호,
	SALE_RATE NUMBER(10,0) NOT NULL -- 할인가격
);

-- 패키지
ALTER TABLE PACK
	ADD CONSTRAINT PK_PACK -- 패키지 기본키
	PRIMARY KEY (
		PACK_NUM -- 패키지번호
	);

-- 결제
CREATE TABLE PAYMENT (
	CART_NO NUMBER(5,0) NOT NULL, -- 결제번호,
	USER_ID VARCHAR2(20) NOT NULL, -- 아이디,
	PACK_NUM NUMBER(5,0) NOT NULL, -- 패키지번호,
	START_DATE DATE NOT NULL, -- 시작날짜,
	END_DATE DATE NOT NULL, -- 종료날짜,
	PEOPLE NUMBER(3,0) NOT NULL, -- 인원수,
	PAY_DATE DATE NOT NULL, -- 결제날짜,
	REFUND CHAR(1) NOT NULL -- 환불여부
);

-- 결제
ALTER TABLE PAYMENT
	ADD CONSTRAINT PK_PAYMENT -- 결제 기본키
	PRIMARY KEY (
		CART_NO -- 결제번호
	);

-- 방
ALTER TABLE ROOM
	ADD CONSTRAINT FK_OUTERPARK_USER_TO_ROOM -- 사용자 -> 방
	FOREIGN KEY (
		USER_ID -- 아이디
	)
	REFERENCES OUTERPARK_USER ( -- 사용자
		USER_ID -- 아이디
	);

-- 렌트카
ALTER TABLE CAR
	ADD CONSTRAINT FK_OUTERPARK_USER_TO_CAR -- 사용자 -> 렌트카
	FOREIGN KEY (
		USER_ID -- 아이디
	)
	REFERENCES OUTERPARK_USER ( -- 사용자
		USER_ID -- 아이디
	);

-- 관광명소
ALTER TABLE TOUR
	ADD CONSTRAINT FK_OUTERPARK_USER_TO_TOUR -- 사용자 -> 관광명소
	FOREIGN KEY (
		USER_ID -- 아이디
	)
	REFERENCES OUTERPARK_USER ( -- 사용자
		USER_ID -- 아이디
	);

-- 패키지
ALTER TABLE PACK
	ADD CONSTRAINT FK_ROOM_TO_PACK -- 방 -> 패키지
	FOREIGN KEY (
		ROOM_NUMBER -- 방번호
	)
	REFERENCES ROOM ( -- 방
		ROOM_NUMBER -- 방번호
	);

-- 패키지
ALTER TABLE PACK
	ADD CONSTRAINT FK_TOUR_TO_PACK -- 관광명소 -> 패키지
	FOREIGN KEY (
		TOUR_NUMBER -- 투어번호
	)
	REFERENCES TOUR ( -- 관광명소
		TOUR_NUMBER -- 투어번호
	);

-- 패키지
ALTER TABLE PACK
	ADD CONSTRAINT FK_CAR_TO_PACK -- 렌트카 -> 패키지
	FOREIGN KEY (
		CAR_NUMBER -- 차번호
	)
	REFERENCES CAR ( -- 렌트카
		CAR_NUMBER -- 차번호
	);

-- 결제
ALTER TABLE PAYMENT
	ADD CONSTRAINT FK_PACK_TO_PAYMENT -- 패키지 -> 결제
	FOREIGN KEY (
		PACK_NUM -- 패키지번호
	)
	REFERENCES PACK ( -- 패키지
		PACK_NUM -- 패키지번호
	);

-- 결제
ALTER TABLE PAYMENT
	ADD CONSTRAINT FK_OUTERPARK_USER_TO_PAYMENT -- 사용자 -> 결제
	FOREIGN KEY (
		USER_ID -- 아이디
	)
	REFERENCES OUTERPARK_USER ( -- 사용자
		USER_ID -- 아이디
	);