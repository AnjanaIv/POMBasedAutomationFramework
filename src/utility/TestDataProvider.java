package utility;

import java.util.ArrayList;



import config.BaseConfig;
import config.SessionDetails;
//import jxl.Sheet;
//import jxl.Workbook;
/*
 * author: anjana.iv
 * Purpose : Data Provider, fetching details from Excel Sheet
 */
public class TestDataProvider implements BaseConfig{
	static Xls_Reader reader;
	static int numrow=0;
	
	public static ArrayList<Object[]> getExcelData(String sSheetName) {
		ArrayList<Object[]> excelData =  new ArrayList<Object[]>();
		SessionDetails.TestDataIteration = 1;
		Object[] obArray = null;
		Object object;
		ArrayList<Object> singleRowData =  new ArrayList<Object>();
		try {
			reader = new Xls_Reader();
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		for(int rowNum = 2;rowNum <= reader.getRowCount(sSheetName);rowNum++) 
        {
			for(int colNum = 0;colNum <= reader.getColumnCount(sSheetName);colNum++) 
	        {
				//System.out.println(colNum);
				object=reader.getCellData(sSheetName, colNum, rowNum);
				singleRowData.add(colNum, object);
				obArray = singleRowData.toArray();
				
	        }
			singleRowData.removeAll(singleRowData);
			excelData.add(obArray);
        }
		return excelData;
	}
	
	public static ArrayList<Object[]> getMultipleData(String sSheetName,String sTableName) {
		ArrayList<Object[]> excelData =  new ArrayList<Object[]>();
		Object[] obArray = null;
		Object object;
		String[][] tableData = null;
		ArrayList<Object> singleRowData =  new ArrayList<Object>();
		try {
			reader = new Xls_Reader();
		}catch(Exception e) {
			e.printStackTrace();
		}
		try {
			tableData = reader.getTableArray(sSheetName, sTableName);
			System.out.println(tableData);
		} catch (Exception e) {
			e.printStackTrace();
		}
		for(int rowNum = 1;rowNum < tableData.length;rowNum++) 
        {
				object=tableData[rowNum];
				singleRowData.add(object);
				obArray = singleRowData.toArray();
				singleRowData.removeAll(singleRowData);
				excelData.add(obArray);
        }
		return excelData;
	}
	public static ArrayList<Object[]> getDependentData(String sSheetName,String sTableName,String MainData) {
		
		ArrayList<Object[]> excelData =  new ArrayList<Object[]>();
		Object[] obArray = null;
		Object object;
		String[][] tableData = null;
		ArrayList<Object> singleRowData =  new ArrayList<Object>();
		try {
			reader = new Xls_Reader();
		}catch(Exception e) {
			e.printStackTrace();
		}
		try {
			tableData = reader.getDependentTableArray(sSheetName, sTableName,MainData);
			System.out.println(tableData);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		for(int rowNum = 1;rowNum < tableData.length;rowNum++) 
        {
			if(!(tableData[rowNum][0] == null)) {
					object=tableData[rowNum];
					singleRowData.add(object);
					obArray = singleRowData.toArray();
					singleRowData.removeAll(singleRowData);
					excelData.add(obArray);
			}
        }
		return excelData;
	}
	public static String[][] getDependentDatainStr(String sSheetName,String sTableName,String MainData) {
		String[][] tableData = null;
		String[][] tableFinal = null;
		int notNull=0;int p=0;
		try {
			tableData = reader.getDependentTableArray(sSheetName, sTableName,MainData);
			for(int i = 0;i < tableData.length;i++) 
	        {
				if(!(tableData[i][0] == null)) {
					notNull++;
				}
	        }
			tableFinal = new String[notNull][notNull];
			for(int i = 0;i < tableData.length;i++) 
	        {
				if(!(tableData[i][0] == null)) {
					tableFinal[p] = tableData[i];
					p++;
				}
	        }
		} catch (Exception e) {
			e.printStackTrace();
		}
		return tableFinal;
	}

}
