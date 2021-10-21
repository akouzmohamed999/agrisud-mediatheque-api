package org.agrisud.mediathequeapi.constants;

public class SqlConstant {
	private SqlConstant() {
	}
	
	public static final String INSERT_CATEGORY = "insert.category";
	public static final String SELECT_ALL_CATEGORY = "select_all_category";
	public static final String SELECT_CATEGORY_BY_ID = "select_category_by_id";
	public static final String DELETE_CATEGORY_BY_ID = "delete_category_by_id";
	public static final String SELECT_CATEGORY_BY_TILTLE = "select_category_by_title";
	public static final String SELECT_CATEGORY_CHECK_TILTLE = "select_category_check_title";
	public static final String SELECT_CATEGORY_CHILD_OF_LEVEL = "select_category_child_of_level";
	public static final String UPDATE_CATEGORY = "update_category";
	
	
	public static final String INSERT_THEMATIC = "insert_thematic";
	public static final String DELETE_THEMATIC_BY_ID = "delete_thematic_by_id";
	public static final String UPDATE_THEMATIC = "update_thematic";
	public static final String SELECT_ALL_THEMATIC = "select_all_thematic";
	
	
	public static final String INSERT_COUNTRY = "insert_country";
	
	public static final String INSERT_DOCUMENT_TYPE = "insert_document_type";
	public static final String DELETE_DOCUMENT_TYPE_BY_ID = "delete_document_type_by_id";
	public static final String UPDATE_DOCUMENT_TYPE = "update_document_type";
	public static final String SELECT_ALL_DOCUMENT_TYPE = "select_all_document_type";
	
	public static final String INSERT_VIDEO_TYPE = "insert_video_type";
	public static final String DELETE_VIDEO_TYPE_BY_ID = "delete_video_type_by_id";
	public static final String UPDATE_VIDEO_TYPE = "update_video_type";
	public static final String SELECT_ALL_VIDEO_TYPE = "select_all_video_type";
	public static final String SELECT_VIDEO_TYPE_BY_ID = "select_video_type_by_id";
	
	public static final String INSERT_LIST_THEMATIC_SUPPORT = "insert_list_thematic_support";
	
	public static final String INSERT_LIST_COUNTRY_SUPPORT = "insert_list_country_support";
	
	public static final String INSERT_SUPPORT = "insert_support";
	public static final String SELECT_SUPPORT = "select_support";
	public static final String SELECT_LIST_COUNTY_BY_SUPPORT_ID = "select_list_country_by_support_id";
	public static final String SELECT_THEMATIC_BY_SUPPORT_ID = "select_list_thematic_by_support_id";
	public static final String SELECT_THEMATIC_BY_ID = "select_thematic_by_id";
	public static final String SELECT_LIST_THEMATIC_BY_EXPOSITION_ID = "select_thematic_list_by_exposition_id";
	public static final String SELECT_DOCUMENT_TYPE_BY_ID = "select_document_type_by_id";
	public static final String SELECT_SUPPORT_BY_ID = "select_support_by_id";
	public static final String DELETE_LIST_THEMATIC_BY_SUPPORT_ID = "delete_list_thematic_by_support_id";
	public static final String DELETE_LIST_COUTRY_BY_SUPPORT_ID = "delete_list_country_by_support_id";
	public static final String DELET_SUPPORT_BY_ID = "delete_support_by_id";
	public static final String UPDATE_SUPPORT = "update_support";
	public static final String DELET_SUPPORT_BY_CATEGORY_ID = "delete_support_by_category_id";
	public static final String SELECT_SUPPORT_ORDER_BY_TITLE_ASC = "select_support_order_by_title_asc";
	public static final String SELECT_SUPPORT_ORDER_BY_TITLE_DESC = "select_support_order_by_title_desc";
	public static final String SELECT_SUPPORT_ORDER_BY_DATE_ASC = "select_support_order_by_date_asc";
	public static final String SELECT_SUPPORT_ORDER_BY_DOCUMENT_TYPE_FRANCAIS_ASC = "select_support_order_by_document_type_francais_asc";
	public static final String SELECT_SUPPORT_ORDER_BY_DOCUMENT_TYPE_ANGLAIS_ASC = "select_support_order_by_document_type_anglais_asc";
	public static final String SELECT_SUPPORT_ORDER_BY_DATE_DESC = "select_support_order_by_date_desc" ;
	public static final String SELECT_SUPPORT_ORDER_BY_DOCUMENT_TYPE_FRANCAIS_DESC = "select_support_order_by_document_type_francais_desc";
	public static final String SELECT_SUPPORT_ORDER_BY_DOCUMENT_TYPE_ANGLAIS_DESC = "select_support_order_by_document_type_anglais_desc";
	
	public static final String SELECT_SUPPORT_ORDER_BY_DOCUMENT_TYPE_DESC = "select_support_order_by_document_type_desc";

	public static final String INSERT_SUPPORT_VIDEO = "insert_support_video";
	
	public static final String INSERT_LIST_COUNTRY_SUPPORT_VIDEO = "insert_list_country_support_video";
	public static final String SELECT_LIST_COUNTY_BY_SUPPORT_VIDEO_ID = "select_list_country_by_support_video_id";
	public static final String DELETE_LIST_COUTRY_BY_SUPPORT_VIDEO_ID = "delete_list_country_by_support_video_id";
	public static final String INSERT_LIST_THEMATIC_SUPPORT_VIDEO = "insert_list_thematic_support_video";
	public static final String SELECT_THEMATIC_BY_SUPPORT_VIDEO_ID = "select_list_thematic_by_support_video_id";
	public static final String DELETE_LIST_THEMATIC_BY_SUPPORT_VIDEO_ID = "delete_list_thematic_by_support_video_id";
	public static final String SELECT_SUPPORT_VIDEO_BY_ID = "select_support_video_by_id";
	public static final String DELETE_SUPPORT_VIDEO_BY_ID = "delete_support_video_by_id";
	
	public static final String SELECT_SUPPORT_VIDEO = "select_support_video";
	public static final String UPDATE_SUPPORT_VIDEO = "update_support_video";
	public static final String SELECT_SUPPORT_VIDEO_ORDER_BY_TITLE_ASC = "select_support_video_order_by_title_asc";
	public static final String SELECT_SUPPORT_VIDEO_ORDER_BY_DATE_ASC = "select_support_video_order_by_date_asc";

	public static final String SELECT_SUPPORT_VIDEO_ORDER_BY_VIDEO_TYPE_FRANCAIS_ASC = "select_support_video_order_by_video_type_francais_asc";
	public static final String SELECT_SUPPORT_VIDEO_ORDER_BY_VIDEO_TYPE_ANGLAIS_ASC = "select_support_video_order_by_video_type_anglais_asc";
	public static final String SELECT_SUPPORT_VIDEO_ORDER_BY_TITLE_DESC = "select_support_video_order_by_title_desc";
	public static final String SELECT_SUPPORT_VIDEO_ORDER_BY_DATE_DESC = "select_support_video_order_by_date_desc";
	public static final String SELECT_SUPPORT_VIDEO_ORDER_BY_VIDEO_TYPE_FRANCAIS_DESC = "select_support_video_order_by_video_type_francais_desc";
	public static final String SELECT_SUPPORT_VIDEO_ORDER_BY_VIDEO_TYPE_ANGLAIS_DESC = "select_support_video_order_by_video_type_anglais_desc";
	
	public static final String SELECT_SUPPORT_ORDER_BY_LANGUE_ASC = "select_support_order_by_langue_asc";
	public static final String SELECT_SUPPORT_ORDER_BY_LANGUE_DESC = "select_support_order_by_langue_desc";
	public static final String SELECT_SUPPORT_VIDEO_ORDER_BY_LANGUE_DESC = "select_support_video_order_by_langue_desc";
	public static final String SELECT_SUPPORT_VIDEO_ORDER_BY_LANGUE_ASC = "select_support_video_order_by_langue_asc";
	public static final String DELET_SUPPORT_VIDEO_BY_CATEGORY_ID = "delete_support_video_by_category_id";

	public static final String INSERT_EXPOSITION_IMAGE = "insert_exposition_image";
	public static final String DELETE_EXPOSITION_IMAGE = "delete_exposition_image";
	public static final String SELECT_EXPOSITION_IMAGE_BY_ID = "select_exposition_image_by_id";
	public static final String SELECT_EXPOSITION_IMAGE = "select_exposition_image";
	public static final String DELETE_EXPOSITION_IMAGE_BY_ID = "delete_exposition_image_by_id";
	public static final String UPDATE_RANK_EXPOSITION_IMAGE_BY_ID = "update_rank_exposition_image_by_id";

	public static final String INSERT_LIST_THEMATIC_EXPOSITION = "insert_list_thematic_exposition";
	public static final String SELECT_LIST_THEMATIC_EXPOSITION_BY_THEMATIC_ID = "select_list_thematic_exposition_by_thematic_id";
	public static final String DELETE_LIST_THEMATIC_EXPOSITION = "delete_list_thematic_exposition_by_exposition_id";


	public static final String INSERT_LIST_EXPOSITION_EXPOSITION_IMAGE = "insert_list_exposition_image";
	public static final String SELECT_LIST_EXPOSITION_EXPOSITION_IMAGE = "select_exposition_image_by_exposition_id";
	public static final String DELETE_LIST_EXPOSITION_EXPOSITION_IMAGE = "delete_list_exposition_image_by_exposition_id";
	public static final String DELETE_LIST_EXPOSITION_EXPOSITION_IMAGE_ID = "delete_list_exposition_image_by_id";

	public static final String INSERT_EXPOSITION = "insert_exposition";
	public static final String UPDATE_EXPOSITION = "update_exposition";
	public static final String DELETE_EXPOSITION = "delete_exposition";
	public static final String SELECT_EXPOSITION_BY_ID = "select_exposition_by_id";
	public static final String SELECT_EXPOSITION_ALL_BY_CATEGORY_ID = "select_exposition_all_by_category_id";

	public static final String DELETE_COUNTRY_BY_ID = "delete_country_by_id";
	public static final String UPDATE_COUNTRY = "update_country";
	public static final String SELECT_ALL_COUNTRY = "select_all_country";
	public static final String SELECT_COUNTRY_BY_ID = "select_country_by_id";
	public static final String SELECT_CATEGORY_CHECK_TILTLE_FRANCAIS = "select_check_title_francais";
	public static final String SELECT_CATEGORY_CHECK_TILTLE_ANGLAIS = "select_check_title_anglais";
	public static final String SELECT_ALL_NEWS = "select_all_news";
	public static final String SELECT_SUPPORT_LAST_NEWS = "select_support_last_news";
	public static final String SELECT_SUPPORT_VIDEO_LAST_NEWS = "select_support_video_last_news";
	public static final String SELECT_EXPOSITION_LAST_NEWS = "select_exposition_last_news";

}