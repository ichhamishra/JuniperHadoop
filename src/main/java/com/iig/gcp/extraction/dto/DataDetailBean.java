package com.iig.gcp.extraction.dto;

public class DataDetailBean {
	
	private String table_name ;
	private String table_name_short ;
	private String schema ;
	private String column_name;
	private String where_clause;
	private String fetch_type;
	private String incremental_column;
	private String cols;
	public String getTable_name() {
		return table_name;
	}
	public void setTable_name(String table_name) {
		this.table_name = table_name;
	}
	public String getTable_name_short() {
		return table_name_short;
	}
	public void setTable_name_short(String table_name_short) {
		this.table_name_short = table_name_short;
	}
	public String getSchema() {
		return schema;
	}
	public void setSchema(String schema) {
		this.schema = schema;
	}
	public String getColumn_name() {
		return column_name;
	}
	public void setColumn_name(String column_name) {
		this.column_name = column_name;
	}
	public String getWhere_clause() {
		return where_clause;
	}
	public void setWhere_clause(String where_clause) {
		this.where_clause = where_clause;
	}
	public String getFetch_type() {
		return fetch_type;
	}
	public void setFetch_type(String fetch_type) {
		this.fetch_type = fetch_type;
	}
	public String getIncremental_column() {
		return incremental_column;
	}
	public void setIncremental_column(String incremental_column) {
		this.incremental_column = incremental_column;
	}
	public String getCols() {
		return cols;
	}
	public void setCols(String cols) {
		this.cols = cols;
	}
}
