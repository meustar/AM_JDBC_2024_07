DROP DATABASE IF EXISTS `AM_JDBC_2024_07`;
CREATE DATABASE `AM_JDBC_2024_07`;
USE `AM_JDBC_2024_07`;

SHOW TABLES;

CREATE TABLE article(
                        id INT(10) UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
                        regDate DATETIME NOT NULL,
                        updateDate DATETIME NOT NULL,
                        title CHAR(100) NOT NULL,
                        content TEXT NOT NULL
);

CREATE TABLE `member`(
                         id INT(10) UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
                         regDate DATETIME NOT NULL,
                         updateDate DATETIME NOT NULL,
                         loginId CHAR(30) NOT NULL,
                         loginPw CHAR(200) NOT NULL,
                         `name` CHAR(100) NOT NULL
);

SELECT *
FROM article;

SELECT *
FROM `member`;


#################################################################################
INSERT INTO article
SET regDate = NOW(),
updateDate = NOW(),
title = CONCAT('제목', SUBSTRING(RAND() * 1000 FROM 1 FOR 2)),
content = CONCAT('내용', SUBSTRING(RAND() * 1000 FROM 1 FOR 2));

INSERT INTO `member`
SET regDate = NOW(),
updateDate = NOW(),
loginId = 'test1',
loginPw = 'test1',
`name` = '김철수';

INSERT INTO `member`
SET regDate = NOW(),
updateDate = NOW(),
loginId = 'test2',
loginPw = 'test2',
`name` = '홍길동';

# select에 조건식이 true 일 경우 반환값은 1. flase 일 경우 0
SELECT COUNT(*) > 0
FROM `member`
WHERE loginId = 'test2';

SELECT COUNT(*) > 0
FROM `member`
WHERE loginId = 'test3';

SELECT NOW();
SELECT '제목1';
SELECT CONCAT('제목',' 1'); #문자열 더하기.
SELECT RAND();              #0부터~ 1까지의 랜덤
SELECT RAND() * 10;         #0부터 10까지의 랜덤
SELECT SUBSTRING(RAND() * 10 FROM 1 FOR 1);

SELECT *
FROM article
ORDER BY id DESC;

UPDATE article
SET updateDate = NOW(),
    title = 'title1',
    content = 'content1'
WHERE id = 4;