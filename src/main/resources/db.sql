use hanafun;

CREATE TABLE `user` (
	`user_id`	BIGINT	NOT NULL AUTO_INCREMENT,
	`username`	VARCHAR(255)	NOT NULL,
	`password`	VARCHAR(20)	NOT NULL,
	`point`	INT	NOT NULL DEFAULT 0,
	`email`	VARCHAR(255)	NOT NULL,
	`is_host`	BOOLEAN	NOT NULL	DEFAULT FALSE,
	`is_deleted`	BOOLEAN	NOT NULL DEFAULT FALSE,
	`created_date`	DATETIME	NOT NULL DEFAULT NOW(),
	`updated_date`	DATETIME	NULL,
	PRIMARY KEY (`user_id`)
);

CREATE TABLE `account` (
	`account_id`	BIGINT	NOT NULL AUTO_INCREMENT,
	`user_id`	BIGINT	NOT NULL,
	`account_number`	VARCHAR(20)	NOT NULL,
	`account_name`  VARCHAR(50) NOT NULL,
	`password`	INT	NOT NULL,
	`balance`	INT	NOT NULL DEFAULT 0,
	`qr`	VARCHAR(255)	NULL,
	`is_deleted`	BOOLEAN	NOT NULL DEFAULT FALSE,
	`created_date`	DATETIME	NOT NULL DEFAULT NOW(),
	`updated_date`	DATETIME	NULL,
	PRIMARY KEY (`account_id`),
	FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`)
);

CREATE TABLE `host` (
	`host_id`	BIGINT	NOT NULL AUTO_INCREMENT,
	`user_id`	BIGINT	NOT NULL,
	`introduction`	VARCHAR(255)	NULL,
	`account_id`	BIGINT	NOT NULL,
	`created_date`	DATETIME	NOT NULL DEFAULT NOW(),
	`updated_date`	DATETIME	NULL,
	PRIMARY KEY (`host_id`),
	FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`),
	FOREIGN KEY (`account_id`) REFERENCES `account` (`account_id`)
);

CREATE TABLE `category` (
	`category_id`	BIGINT	NOT NULL AUTO_INCREMENT,
	`category_name`	VARCHAR(255)	NOT NULL,
	`created_date`	DATETIME	NOT NULL DEFAULT NOW(),
	`updated_date`	DATETIME	NULL,
	PRIMARY KEY (`category_id`)
);

CREATE TABLE `lesson` (
	`lesson_id`	BIGINT	NOT NULL AUTO_INCREMENT,
	`host_id`	BIGINT	NOT NULL,
	`category_id`	BIGINT	NOT NULL,
	`title`	VARCHAR(30)	NOT NULL,
	`location`	VARCHAR(255)    NOT NULL,
	`price`	INT	NOT NULL,
	`capacity`	INT	NOT NULL,
	`image`	VARCHAR(255)	NOT NULL,
	`description`	VARCHAR(255)	NOT NULL,
	`materials`	VARCHAR(255)	NULL,
	`applicant_sum`	INT	NOT NULL	DEFAULT 0,
	`is_deleted`	BOOLEAN	NOT NULL DEFAULT FALSE,
	`created_date`	DATETIME	NOT NULL DEFAULT NOW(),
	`updated_date`	DATETIME	NULL,
	PRIMARY KEY (`lesson_id`),
	FOREIGN KEY (`host_id`) REFERENCES `host` (`host_id`),
	FOREIGN KEY (`category_id`) REFERENCES `category` (`category_id`)
);

CREATE TABLE `revenue` (
	`revenue_id`	BIGINT	NOT NULL AUTO_INCREMENT,
	`lesson_id`	BIGINT	NOT NULL,
	`revenue`	BIGINT	NOT NULL,
	`material_price`	INT	NOT NULL	DEFAULT 0,
	`rental_price`	INT	NOT NULL	DEFAULT 0,
	`etc_price`	INT	NOT NULL	DEFAULT 0,
	`created_date`	DATETIME	NOT NULL DEFAULT NOW(),
	`updated_date`	DATETIME	NULL,
	PRIMARY KEY (`revenue_id`),
	FOREIGN KEY (`lesson_id`) REFERENCES `lesson` (`lesson_id`)
);

CREATE TABLE `lessondate` (
	`lessondate_id`	BIGINT	NOT NULL AUTO_INCREMENT,
	`lesson_id`	BIGINT	NOT NULL,
	`date`	DATE	NOT NULL,
	`start_time`	DATETIME	NOT NULL,
	`end_time`	DATETIME	NOT NULL,
	`applicant`	INT	NOT NULL	DEFAULT 0,
	`created_date`	DATETIME	NOT NULL DEFAULT NOW(),
	`updated_date`	DATETIME	NULL,
	PRIMARY KEY (`lessondate_id`),
	FOREIGN KEY (`lesson_id`) REFERENCES `lesson` (`lesson_id`)
);

CREATE TABLE `reservation` (
	`reservation_id`	BIGINT	NOT NULL AUTO_INCREMENT,
	`user_id`	BIGINT	NOT NULL	COMMENT '게스트',
	`lessondate_id`	BIGINT	NOT NULL,
	`applicant`	INT	NOT NULL	DEFAULT 0,
	`is_deleted`	BOOLEAN	NOT NULL DEFAULT FALSE,
	`created_date`	DATETIME	NOT NULL DEFAULT NOW(),
	`updated_date`	DATETIME	NULL,
	PRIMARY KEY (`reservation_id`),
	FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`),
	FOREIGN KEY (`lessondate_id`) REFERENCES `lessondate` (`lessondate_id`)
);

CREATE TABLE `transaction` (
	`transaction_id`	BIGINT	NOT NULL AUTO_INCREMENT,
	`deposit_id`	BIGINT	NOT NULL	COMMENT '호스트',
	`withdraw_id`	BIGINT	NOT NULL	COMMENT '게스트',
	`reservation_id`	BIGINT	NOT NULL,
	`payment`	INT	NOT NULL	COMMENT '포인트 합산 금액',
	`point`	INT	NOT NULL,
	`type`	ENUM('QR', 'PENDING', 'COMPLETE')	NOT NULL,
	`created_date`	DATETIME	NOT NULL DEFAULT NOW(),
	`updated_date`	DATETIME	NULL,
	PRIMARY KEY (`transaction_id`),
	FOREIGN KEY (`deposit_id`) REFERENCES `account` (`account_id`),
	FOREIGN KEY (`withdraw_id`) REFERENCES `account` (`account_id`),
	FOREIGN KEY (`reservation_id`) REFERENCES `reservation` (`reservation_id`)
);

CREATE TABLE `hanastorage` (
	`hanastorage_id`	BIGINT	NOT NULL AUTO_INCREMENT,
	`transaction_id`	BIGINT	NOT NULL,
	`lessondate`	DATE	NOT NULL,
	`is_deleted`	BOOLEAN	NOT NULL DEFAULT FALSE,
	`created_date`	DATETIME	NOT NULL DEFAULT NOW(),
	`updated_date`	DATETIME	NULL,
	PRIMARY KEY (`hanastorage_id`),
	FOREIGN KEY (`transaction_id`) REFERENCES `transaction` (`transaction_id`)
);