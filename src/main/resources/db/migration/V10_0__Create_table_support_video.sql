CREATE TABLE SUPPORT_VIDEO (
    SUPPORT_ID bigint(20) key NOT NULL AUTO_INCREMENT,
    TITLE varchar(150),
    PATH_SUPPORT text,
    URL_SUPPORT text,
    LINK text,
    LANGUAGE varchar(100),
    DATE_SUPPORT varchar(100),
    video_type_id bigint,
    category_id bigint,
    CONSTRAINT fk_video_type_support FOREIGN KEY (video_type_id)
        REFERENCES VIDEO_TYPE (video_type_id),
    CONSTRAINT fk_category_support_video FOREIGN KEY (category_id)
        REFERENCES CATEGORY (category_id)
    
);