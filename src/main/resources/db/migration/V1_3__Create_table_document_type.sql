CREATE TABLE DOCUMENT_TYPE (
    DOCUMENT_TYPE_ID bigint(20) key NOT NULL AUTO_INCREMENT,
    TITLE_FRANCAIS varchar(150),
    TITLE_ANGLAIS varchar(150),
    PATH_IMAGE text,
    URL_IMAGE text
);