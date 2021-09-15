CREATE TABLE SUPPORT (
    SUPPORT_ID bigint(20) key NOT NULL AUTO_INCREMENT,
    TITLE varchar(150),
    PATH_SUPPORT text,
    PATH_IMAGE text,
    URL_IMAGE text,
    URL_SUPPORT text,
    LANGUAGE varchar(100),
    DATE_SUPPORT varchar(100),
    document_type_id bigint,
    category_id bigint,
    CONSTRAINT fk_document_type_support FOREIGN KEY (document_type_id)
        REFERENCES DOCUMENT_TYPE (document_type_id),
    CONSTRAINT fk_category_support FOREIGN KEY (category_id)
        REFERENCES CATEGORY (category_id)
    
);