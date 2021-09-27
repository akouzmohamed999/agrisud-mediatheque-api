CREATE TABLE CATEGORY (
    CATEGORY_ID bigint(20) key NOT NULL AUTO_INCREMENT,
    TITLE varchar(150),
    PATH_FOLDER text,
    PATH_IMAGE text,
    URL_IMAGE text,
    LAST_LEVEL boolean
);