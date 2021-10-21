CREATE TABLE EXPOSITION_IMAGE
(
    exposition_image_id bigint(20) key NOT NULL AUTO_INCREMENT,
    name                varchar(150),
    url_image           varchar(255),
    path_image          varchar(255),
    rank_img            bigint(20)
);
CREATE TABLE EXPOSITION
(
    exposition_id   bigint(20) key NOT NULL AUTO_INCREMENT,
    category_id     bigint,
    title_fr        varchar(150),
    title_en        varchar(150),
    date_exposition varchar(20),
    description_fr  text,
    description_en  text,
    country_id      bigint,
    CONSTRAINT fk_country_exposition FOREIGN KEY (country_id)
        REFERENCES COUNTRY (country_id),
        CONSTRAINT fk_category_exposition FOREIGN KEY (category_id)
        REFERENCES CATEGORY (category_id)
);
CREATE TABLE LIST_THEMATIC_EXPOSITION
(
    list_thematic_exposition_id bigint(20) key NOT NULL AUTO_INCREMENT,
    thematic_id                 bigint(20),
    exposition_id               bigint(20)
);
CREATE TABLE LIST_EXPOSITION_IMAGE
(
    list_exposition_image_id bigint(20) key NOT NULL AUTO_INCREMENT,
    exposition_id            bigint(20),
    exposition_image_id      bigint(20)
);