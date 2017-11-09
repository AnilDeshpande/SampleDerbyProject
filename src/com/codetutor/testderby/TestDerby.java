package com.codetutor.testderby;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class TestDerby {
	
	private static String dbURL = "jdbc:derby:todolistdatabase.db;create=true";
	
	private static final String TABLE_TODOLIST = "table_todolist";
	private static final String COLUMN_TODO_ID = "column_todolist_id";
	private static final String COLUMN_USERID_FK = "column_userid";
	private static final String COLUMN_TODO = "column_todo";
	private static final String COLUMN_PLACE = "column_place";
	
	private static final String TABLE_USERS = "table_users";
	private static final String COLUMN_USER_ID = "column_user_id";
	private static final String COLUMN_USER_NAME = "column_user_name";
	private static final String COLUMN_USER_PASSWORD = "column_user_password";
	
	private static final String CREATE_USER_TABLE = "CREATE TABLE "+TABLE_USERS+
														" ("+COLUMN_USER_ID+" INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), "+
															COLUMN_USER_NAME+" VARCHAR(24) NOT NULL, "+
															COLUMN_USER_PASSWORD + " VARCHAR(24) NOT NULL, "+
															"CONSTRAINT primary_key PRIMARY KEY ("+COLUMN_USER_ID+"))";
	
	private static final String CREATE_TODO_TABLE = "CREATE TABLE "+TABLE_TODOLIST+
																	"("+COLUMN_TODO_ID+" INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), "+
																	COLUMN_TODO	+" VARCHAR(24) NOT NULL, "+
																	COLUMN_PLACE+" VARCHAR(24) NOT NULL, "+
																	COLUMN_USERID_FK+" INTEGER NOT NULL, "+
																	"CONSTRAINT "+COLUMN_USERID_FK+" FOREIGN KEY ("+COLUMN_USER_ID+") REFERENCES "+TABLE_USERS+"("+COLUMN_USER_ID+"))";
	
	
	
	private static Connection conn = null;
    private static Statement stmt = null;
    
    private static void createConnection()
    {
        try
        {
            Class.forName("org.apache.derby.jdbc.EmbeddedDriver").newInstance();
            //Get a connection
            conn = DriverManager.getConnection(dbURL); 
        }
        catch (Exception except)
        {
            except.printStackTrace();
        }
    }

    public static boolean tableExist(Connection conn, String tableName) throws SQLException {
        boolean tExists = false;
        try (ResultSet rs = conn.getMetaData().getTables(null, null, tableName, null)) {
            while (rs.next()) { 
                String tName = rs.getString("TABLE_NAME");
                if (tName != null && tName.equals(tableName)) {
                    tExists = true;
                    break;
                }
            }
        }
        return tExists;
    }
	public static void main(String[] args) {
		createConnection();
		
		try{
			stmt = conn.createStatement();
			stmt.execute(CREATE_USER_TABLE);
			System.out.println(TABLE_USERS +" exists: "+tableExist(conn, TABLE_USERS));
			System.out.println(TABLE_TODOLIST +" exists: "+tableExist(conn, TABLE_TODOLIST));
		}catch(Exception ex){
			ex.printStackTrace();
		}
		
		
		
		
	}
}
