package generic;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import org.apache.log4j.Logger;
import com.aventstack.extentreports.ExtentTest;
import com.codoid.products.fillo.Fillo;
import com.codoid.products.fillo.Recordset;
//TODO: Reporting the steps
public class DBUtils {

	public ExtentTest eTest;
	public Logger log4j;
	public DBUtils(ExtentTest eTest,Logger log4j)
	{
		this.eTest=eTest;
		this.log4j=log4j;
	}
	
	public ArrayList<LinkedHashMap<String, String>> getDataFromMySQL(String dbURL,String un,String pw,String query) {
		ArrayList<LinkedHashMap<String, String>> listMap=new ArrayList<LinkedHashMap<String, String>>();
		try
		{
			Connection connection = DriverManager.getConnection(dbURL,un,pw);
			Statement sqlStatement = connection.createStatement();
			ResultSet resultSet = sqlStatement.executeQuery(query);
			ResultSetMetaData allColumns = resultSet.getMetaData();
			int columnCount = allColumns.getColumnCount();
				while(resultSet.next())
				{
					LinkedHashMap<String, String> map=new LinkedHashMap<String, String>();
					for(int i=1;i<=columnCount;i++)
					{
						String columnName = allColumns.getColumnName(i);
						String value=resultSet.getString(i);
						map.put(columnName,value);
					}
					listMap.add(map);
				}

				connection.close();
		} 
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		
		return listMap;
	}

	public Iterator<String[]> getDataFromMySQLToDP(String dbURL,String un,String pw,String query) {
		ArrayList<String[]> list=new ArrayList<String[]>();
		try
		{
			Connection connection = DriverManager.getConnection(dbURL,un,pw);
			Statement sqlStatement = connection.createStatement();
			ResultSet resultSet = sqlStatement.executeQuery(query);
			ResultSetMetaData allColumns = resultSet.getMetaData();
			int columnCount = allColumns.getColumnCount();
				while(resultSet.next())
				{
					String[] data=new String[columnCount];
					for(int i=0;i<columnCount;i++)
					{
		
						String value=resultSet.getString(i+1);
						data[i]=value;
					}
					list.add(data);
				}

				connection.close();
		} 
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		
		return list.iterator();
	}
	public void executeDMLInMySql(String dbURL,String un,String pw,String query)
	{
		try 
		{
			Connection connection = DriverManager.getConnection(dbURL,un,pw);
			Statement sqlStatement = connection.createStatement();
			sqlStatement.executeUpdate(query);
			connection.close();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public Iterator<String[]> getDataFromXLtoDP(String path,String sheet)
	{
		ArrayList<String[]> list=new ArrayList<String[]>();
		try
		{
			Fillo fillo=new Fillo();
			com.codoid.products.fillo.Connection connection=fillo.getConnection(path);
			String strQuery="Select * from "+sheet;
			Recordset recordset=connection.executeQuery(strQuery);
			int colCount = recordset.getCount();
			while(recordset.next())
			{
				colCount = recordset.getCount();
				String[] data=new String[colCount];
				for(int i=0;i<colCount;i++) 
				{
					String value=recordset.getField(i).value();
					data[i]=value;
				}
				
				list.add(data);
			}
			recordset.close();
			connection.close();
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		return list.iterator();
	}
	
	public void executeDMLInXL(String path,String query)
	{
		try 
		{
			Fillo fillo=new Fillo();
			com.codoid.products.fillo.Connection connection=fillo.getConnection(path);
			connection.executeUpdate(query);
			connection.close();

		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
}
