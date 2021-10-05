package org.agrisud.mediathequeapi.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.agrisud.mediathequeapi.constants.DaoConstant;
import org.agrisud.mediathequeapi.constants.SqlConstant;
import org.agrisud.mediathequeapi.model.DocumentType;
import org.agrisud.mediathequeapi.model.Support;
import org.agrisud.mediathequeapi.model.Thematic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
@PropertySource("classpath:sql/document_type.properties")
public class DocumentTypeDao {
	@Autowired
    NamedParameterJdbcTemplate jdbcTemplate;

	@Autowired
	Environment environment;
	
	public int addDocumentType(DocumentType documentType) {
		return jdbcTemplate.update(environment.getProperty(SqlConstant.INSERT_DOCUMENT_TYPE), getSqlParameterSource(documentType));
	}
	
	private MapSqlParameterSource getSqlParameterSource(DocumentType documentType) {
		return new MapSqlParameterSource()
				.addValue(DaoConstant.DOCUMENT_TYPE_ID, documentType.getDocumentTypeId())
				.addValue(DaoConstant.TITLE_ANGLAIS, documentType.getTitleAnglais())
				.addValue(DaoConstant.TITLE_FRANCAIS, documentType.getTitleFrancais())
				.addValue(DaoConstant.PATH_IMAGE, documentType.getPathImage())
				.addValue(DaoConstant.URL_IMAGE, documentType.getUrlImage());
	}

	public int deleteDocumentType(Long id) {
		Map<String, Object> params = new HashMap<>();
		params.put(DaoConstant.DOCUMENT_TYPE_ID, id);
		return jdbcTemplate.update(environment.getProperty(SqlConstant.DELETE_DOCUMENT_TYPE_BY_ID), new MapSqlParameterSource(params));
	}

	public int updateDocumentType(DocumentType documentType) {
		
		return jdbcTemplate.update(environment.getProperty(SqlConstant.UPDATE_DOCUMENT_TYPE),
				getSqlParameterSource(documentType));
	}

	public List<DocumentType> getAllDocumentType() {
		return jdbcTemplate.query(environment.getProperty(SqlConstant.SELECT_ALL_DOCUMENT_TYPE), DocumentType::baseMapper);
	}

	public DocumentType getDocumentTypeById(Long id) {
		Map<String, Object> params = new HashMap<>();
		params.put(DaoConstant.DOCUMENT_TYPE_ID, id);
		try {
			return jdbcTemplate.queryForObject(environment.getProperty(SqlConstant.SELECT_DOCUMENT_TYPE_BY_ID),
					new MapSqlParameterSource(params), getRowMapper());
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}
	private RowMapper<DocumentType> getRowMapper() {
		return (rs, rowNum) -> DocumentType.builder()
				.documentTypeId(rs.getLong(DaoConstant.DOCUMENT_TYPE_ID))
				.titleAnglais(rs.getString(DaoConstant.TITLE_ANGLAIS))
				.titleFrancais(rs.getString(DaoConstant.TITLE_FRANCAIS))
				.pathImage(rs.getString(DaoConstant.PATH_IMAGE))
				.urlImage(rs.getString(DaoConstant.URL_IMAGE))
				.build();
	}

}
