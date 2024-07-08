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

SELECT *
FROM article;

SELECT NOW();

SELECT '제목1';

SELECT CONCAT('제목',' 1');

SELECT SUBSTRING(RAND() * 1000 FROM 1 FOR 2);

INSERT INTO article
SET regDate = NOW(),
updateDate = NOW(),
title = CONCAT('제목', SUBSTRING(RAND() * 1000 FROM 1 FOR 2)),
content = CONCAT('내용', SUBSTRING(RAND() * 1000 FROM 1 FOR 2));

SELECT *
FROM article;