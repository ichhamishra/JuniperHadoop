package com.iig.gcp.constants;

public class OracleConstants {
	
	// Oracle Driver Details 
	public final static String ORACLE_DRIVER="oracle.jdbc.driver.OracleDriver";
	//public final static String ORACLE_IP_PORT_SID="144.21.92.88:1521:ORCL";
	public final static String ORACLE_IP_PORT_SID="144.21.70.142:1521:ORCL";
	//public final static String ORACLE_DB_NAME="juniper_admin";
	public final static String ORACLE_DB_NAME="juniper_admin";
	public final static String ORACLE_JDBC_URL="jdbc:oracle:thin:@"+ORACLE_IP_PORT_SID+"";
	public static final String ORACLE_PASSWORD = "Infy123##";

	public final static String EXTRACTION_COMPUTE_URL="http://35.231.63.185:8095/";
	public static String GET_SOURCE_CONNECTIONS="SELECT connection_id,connection_name,host_name,port_no,username,password,database_name,service_name from JUNIPER_EXT_CONNECTION_MASTER where connection_type=?";

}
