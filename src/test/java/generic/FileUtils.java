package generic;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Properties;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import com.aventstack.extentreports.ExtentTest;
//TODO steps reporting
//TODO json methods
public class FileUtils
{
	public ExtentTest eTest;
	public Logger log4j;
	
	public FileUtils(ExtentTest eTest,Logger log4j)
	{
		this.eTest=eTest;
		this.log4j=log4j;
	}
	
	
	public String getPropertyValue(String path,String key)
	{
		String value="";
		
		Properties p=new Properties();
		try 
		{
		  p.load(new FileInputStream(path));
		} catch (Exception e) 
		{
			e.printStackTrace();
		} 
		value = p.getProperty(key);
		
		return value;
	}
	
	public String getXL_CellData(String path,String sheet,int r,int c)
	{
		String v="";
		try 
		{
			Workbook wb = WorkbookFactory.create(new FileInputStream(path));
			v=wb.getSheet(sheet).getRow(r).getCell(c).toString();
			wb.close();
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		return v;
	}
	
	public int getXL_RowCount(String path,String sheet)
	{
		int rowCount=0;
		try 
		{
			Workbook wb = WorkbookFactory.create(new FileInputStream(path));
			rowCount=wb.getSheet(sheet).getLastRowNum();
			wb.close();
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		return rowCount;
	}

	public int getXL_ColumnCount(String path,String sheet,int row)
	{
		int colCount=0;
		try 
		{
			Workbook wb = WorkbookFactory.create(new FileInputStream(path));
			colCount=wb.getSheet(sheet).getRow(row).getLastCellNum();
			wb.close();
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		return colCount;
	}
	public Iterator<String[]> getDataFromXLForDP(String path,String sheetName) {
		ArrayList<String[]> dataList=new ArrayList<String[]>();
		try {
		Workbook wb = WorkbookFactory.create(new FileInputStream(path));
		Sheet sheet = wb.getSheet(sheetName);
		int rowCount=sheet.getLastRowNum();
		for(int i=1;i<=rowCount;i++) {
			try {
						int cellCount=sheet.getRow(i).getLastCellNum();
						String[] dataRow=new String[cellCount];
						for(int j=0;j<cellCount;j++) {
							try {
								String v = sheet.getRow(i).getCell(j).toString();
								dataRow[j]=v;
							}
							catch (Exception e) {
							}	
						}
						dataList.add(dataRow);
						
			}
			catch (Exception e) {
			}
		}
		wb.close();
		}catch (Exception e) {
			e.printStackTrace();
		}
		Iterator<String[]> iData = dataList.iterator();
		return iData;
	}
	
	public void setXL_CellValue(String path,String sheet,int row,int col,String value) {
		try
		{
			Workbook wb = WorkbookFactory.create(new FileInputStream(path));
			try 
			{
				wb.getSheet(sheet).getRow(row).createCell(col).setCellValue(value);
			}
			catch (Exception e) {
				wb.getSheet(sheet).createRow(row).createCell(col).setCellValue(value);
			}
			wb.write(new FileOutputStream(path));
			wb.close();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public LinkedHashMap<String,String> getDataFromCSV(String path,int row)
	{
		LinkedHashMap<String, String> map=new LinkedHashMap<String, String>();
		try 
		{
			CSVParser csvContent = CSVParser.parse(new FileReader(path),CSVFormat.DEFAULT);
			List<CSVRecord> allRecords = csvContent.getRecords();
			CSVRecord header = allRecords.get(0);
			int colCount = header.size();
	
			CSVRecord data = allRecords.get(row); 
			for(int i=0;i<colCount;i++)
			{
				String key=header.get(i);
				String value=data.get(i);
				map.put(key, value);
			}
		}
		catch (Exception e) 
		{
		}
		return map;
	}
	
	public ArrayList<LinkedHashMap<String, String>> getAllDataFromCSV(String path) throws Exception
	{
		ArrayList<LinkedHashMap<String, String>> listMap=new ArrayList<LinkedHashMap<String, String>>();
		CSVParser csvContent = CSVParser.parse(new FileReader(path), CSVFormat.DEFAULT);
		List<CSVRecord> allRecords = csvContent.getRecords();
		CSVRecord header = allRecords.get(0);
		int colCount = header.size();
		for(int i=1;i<allRecords.size();i++) 
		{
			LinkedHashMap<String, String> map=new LinkedHashMap<String, String>();
				CSVRecord data = allRecords.get(i); 
				for(int j=0;j<colCount;j++)
				{
					String key=header.get(j);
					String value=data.get(j);
					map.put(key, value);
				}
				
				listMap.add(map);
		}
		
		return listMap;
	}
	
	public Iterator<String[]> getDataFromCSVForDP(String path) throws Exception
	{
		ArrayList<String[]> dataList=new ArrayList<String[]>();
		CSVParser csvContent = CSVParser.parse(new FileReader(path), CSVFormat.DEFAULT);
		List<CSVRecord> allRecords = csvContent.getRecords();
		int colCount = allRecords.get(0).size();
		for(int i=1;i<allRecords.size();i++) 
		{
				String[] data=new String[colCount];
				CSVRecord record = allRecords.get(i); 
				for(int j=0;j<colCount;j++)
				{
					String value=record.get(j);
					data[j]=value;
				}
				dataList.add(data);
		}
		return dataList.iterator();
	}
}
