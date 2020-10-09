
package utility;

/*
 * author: anjana.iv
 * Purpose : Reading the excel sheet
 */

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Calendar;
import java.util.Iterator;

import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFFormulaEvaluator;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook; 
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.CellValue;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.testng.Reporter;

import config.BaseConfig;
import config.SessionDetails;
//import jxl.Sheet;
//import jxl.Workbook;

public class Xls_Reader implements BaseConfig{
	public static  FileInputStream fis = null;
	public  FileOutputStream fileOut =null;
	//private static jxl.Sheet xSheet = null;
	private org.apache.poi.ss.usermodel.Workbook workbook = null;
	private org.apache.poi.ss.usermodel.Sheet sheet = null;
	private org.apache.poi.ss.usermodel.Row row= null;
	private org.apache.poi.ss.usermodel.Cell cell = null;
	private org.apache.poi.hssf.usermodel.HSSFWorkbook Hworkbook= null;
	private org.apache.poi.hssf.usermodel.HSSFSheet Hsheet= null;
	private org.apache.poi.hssf.usermodel.HSSFCell Hcell= null;
	private static String sExcelFileName = "";
	
	
	
	
public Xls_Reader() {
		String WORKING_DIR = System. getProperty("user.dir");
		sExcelFileName=WORKING_DIR+sTestDataFilePath;
		//this.path=path;
		try {
			fis = new FileInputStream(sExcelFileName);
			workbook = WorkbookFactory.create(fis);
			sheet = workbook.getSheetAt(0);
			fis.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		}	
		// returns the row count in a sheet

		public int getRowCount(String sheetName){
			int index = workbook.getSheetIndex(sheetName);
			if(index==-1)
				return 0;
			else{
			sheet = workbook.getSheetAt(index);
			int number=sheet.getLastRowNum()+1;
			return number;
			}
			
		}
		// returns the data from a cell
		
		
		public String getCellData(String sheetName,String colName,int rowNum){
			DataFormatter objDefaultFormat = new DataFormatter();
			FormulaEvaluator objFormulaEvaluator = new HSSFFormulaEvaluator((HSSFWorkbook) workbook);
			try{
				if(rowNum <=0)
					return "";
			
			int index = workbook.getSheetIndex(sheetName);
			int col_Num=-1;
			if(index==-1)
				return "";
			
			sheet = workbook.getSheetAt(index);
			row=sheet.getRow(0);
			for(int i=0;i<row.getLastCellNum();i++){
				//System.out.println(row.getCell(i).getStringCellValue().trim());
				if(row.getCell(i).getStringCellValue().trim().equals(colName.trim()))
					col_Num=i;
			}
			if(col_Num==-1)
				return "";
			
			row = sheet.getRow(rowNum-1);
			if(row==null)
				return "";
			cell = row.getCell(col_Num);
			objFormulaEvaluator.evaluate(cell); // This will evaluate the cell, And any type of cell will return string value
		    String cellValueStr = objDefaultFormat.formatCellValue(cell,objFormulaEvaluator);
					    return cellValueStr;
			}
			catch(Exception e){
				
				e.printStackTrace();
				return "row "+rowNum+" or column "+colName +" does not exist in xls";
			}
		}
		public void writeToExcel(String sheetName,String colName,int rowNum,String value) {
			try {
				 workbook = WorkbookFactory.create(new FileInputStream(sExcelFileName));
				 int index = workbook.getSheetIndex(sheetName);			
				 sheet= workbook.getSheetAt(index);
				 //to fetch the Column Names of the table
				 row=sheet.getRow(rowNum);
				 int col_Num =-1;
				for(int i=1;i<row.getLastCellNum();i++){
					System.out.println(row.getCell(i).getStringCellValue());
					if(row.getCell(i).getStringCellValue().trim().equals(colName.trim())) {
							col_Num =i;
							break;}
					}
				if(col_Num==-1) {
					System.out.println("Unable to find the column Name in xls");
					return;}
					//to arrive at the row number of the test data iteration for writing cell
					row = sheet.getRow(rowNum+SessionDetails.TestDataIteration);
					if(row==null)
						System.out.println("Unable to find the row details in xls");
					cell = row.getCell(col_Num); 
					if (cell == null)
					{
					    cell = sheet.getRow(rowNum+SessionDetails.TestDataIteration).createCell(col_Num);
					}
					cell.setCellValue(value);
				  FileOutputStream out = new FileOutputStream(sExcelFileName);
				  workbook.write(out);
				  out.close();
				  Reporter.log("Updated the SatTrax ID "+value+ " in the cell reference "+cell.getAddress()+" located in the sheet "+sheetName,true);
			}catch(Exception e) {
				e.printStackTrace();
			}
		}		
		// returns the data from a cell
		public String getCellData(String sheetName,int colNum,int rowNum){
			 FormulaEvaluator evaluator = workbook.getCreationHelper().createFormulaEvaluator();
			FormulaEvaluator objFormulaEvaluator = new HSSFFormulaEvaluator((HSSFWorkbook) workbook);
			try{
				if(rowNum <=0)
					return "";
			
			int index = workbook.getSheetIndex(sheetName);

			if(index==-1)
				return "";
			
		
			sheet = workbook.getSheetAt(index);
			row = sheet.getRow(rowNum-1);
			if(row==null)
				return "";
			cell = row.getCell(colNum);
			evaluator.evaluate(cell);
			//objFormulaEvaluator.evaluate(cell); // This will evaluate the cell, And any type of cell will return string value
			if(cell==null)
				return "";
			else
				try {
				if(cell.getStringCellValue().contains("SatTraxID")) {
					SessionDetails.currentRowRef = (rowNum-1);
				}}catch(Exception e) {
					System.out.println("Specified cell is not a String");
				}
		  if(cell.getCellType()==CellType.STRING)
			  return cell.getStringCellValue();
		  else if(cell.getCellType()==CellType.NUMERIC){
			  String cellText;
			  try {
			   cellText  = String.valueOf(cell.getNumericCellValue());
			  }catch(Exception e) {
				  cellText  = cell.getStringCellValue();
			  }
			  if (HSSFDateUtil.isCellDateFormatted(cell)) {
		           // format in form of M/D/YY
				  double d = cell.getNumericCellValue();

				  Calendar cal =Calendar.getInstance();
				  cal.setTime(HSSFDateUtil.getJavaDate(d));
		            cellText =
		             (String.valueOf(cal.get(Calendar.YEAR))).substring(2);
		           cellText = cal.get(Calendar.MONTH)+1 + "/" +
		                      cal.get(Calendar.DAY_OF_MONTH) + "/" +
		                      cellText;
		         }
			  return cellText;
		  }else if (cell.getCellType()==CellType.FORMULA){
			  //System.out.println(cell.getCellFormula());
		        final FormulaEvaluator eval = workbook.getCreationHelper().createFormulaEvaluator();
		        final CellValue cellValue = eval.evaluate(cell);
			  return cellValue.getStringValue();
		  }else if(cell.getCellType()==CellType.BLANK)
		      return "";
		  else 
			  return String.valueOf(cell.getBooleanCellValue());
			}
			catch(Exception e){
				
				e.printStackTrace();
				return "row "+rowNum+" or column "+colNum +" does not exist  in xls";
			}
		}
		
		
		
	  // find whether sheets exists	
		public boolean isSheetExist(String sheetName){
			int index = workbook.getSheetIndex(sheetName);
			if(index==-1){
				index=workbook.getSheetIndex(sheetName.toUpperCase());
					if(index==-1)
						return false;
					else
						return true;
			}
			else
				return true;
		}
		
		// returns number of columns in a sheet	
		public int getColumnCount(String sheetName){
			// check if sheet exists
			if(!isSheetExist(sheetName))
			 return -1;
			
			sheet = workbook.getSheet(sheetName);
			row = sheet.getRow(0);
			
			if(row==null)
				return -1;
			
			return row.getLastCellNum();
			
			
			
		}

		public int getCellRowNum(String sheetName,String colName,String cellValue){
			
			for(int i=2;i<=getRowCount(sheetName);i++){
		    	if(getCellData(sheetName,colName , i).equalsIgnoreCase(cellValue)){
		    		return (i-1);
		    	}
		    }
			return -1;
			
		}
		
		public String[][] getTableArray(String sheetName,
				String tableName) {
			String[][] tabArray = null;
			try {
				 HSSFSheet ws = (HSSFSheet) workbook.getSheet(sheetName);
				 int startRow = 0,startCol=0, endRow=0, endCol=0, ci = 0, cj;
				 boolean bFlag= false;boolean bRead=false;
				 String cellValue="";
				 @SuppressWarnings("rawtypes")
				Iterator rows = ws.rowIterator();
			        while (rows.hasNext())
			        {
			            row=(Row) rows.next();
			            @SuppressWarnings("rawtypes")
						Iterator cells = row.cellIterator();
			            while (cells.hasNext())
			            {
			                cell=(Cell) cells.next();
			                if(cell.getCellType()==CellType.STRING) {
			                	cellValue= cell.getStringCellValue();}
			      		  	else if(cell.getCellType()==CellType.NUMERIC || cell.getCellType()==CellType.FORMULA ){
			      		  		try {
			      		  		cellValue = String.valueOf(cell.getNumericCellValue());
			      		  		}catch(Exception e) {
			      		  		cellValue = cell.getStringCellValue();
			      		  		}
			      		  	}
			      		  	else if(cell.getCellType()==CellType.BLANK) {
			      		  		cellValue= "";}
			      		  	else if(cell.getCellType()==CellType.BOOLEAN) {
			      			cellValue= String.valueOf(cell.getBooleanCellValue());}
			      			
			                if (cellValue.equalsIgnoreCase(tableName) && (!bFlag)) {
			                	startRow = cell.getRowIndex();
			                	startCol = cell.getColumnIndex();
			                	bFlag = true;
			                }else if(cellValue.trim().equalsIgnoreCase(tableName) && (bFlag)) {
			                	endRow = cell.getRowIndex();
			                	endCol = cell.getColumnIndex();
			                	bRead= true;
			                	break;
			                }
			            }
			            if(bRead) {
			            	break;
			            }
			        }
			    tabArray = new String[endRow - startRow][endCol - startCol - 1];
			    for (int i = startRow + 1; i <= endRow; ++i, ++ci) {
					cj = 0;
					for (int j = startCol + 1; j < endCol; j++, cj++) {
						tabArray[ci][cj] = getCellData(sheetName,j, i+1).trim();
					}
				}				
			    workbook.close();
				} catch (Exception e) {
				System.out.println("Error fetching data details for subtable "+ tableName+ " under sheet "+ sheetName);
				// Assert.fail("error in getTableArray()");
				System.out.println(e);
			}
			return (tabArray);
		}
		
		@SuppressWarnings("rawtypes")
		public String[][] getDependentTableArray(String sheetName,
				String tableName,String matchMainRecord) {
			String[][] tabArray = null;
			try {
				 HSSFSheet ws = (HSSFSheet) workbook.getSheet(sheetName);
				 //tableName="addStopDetails";
				 int startRow = 0,startCol=0, endRow=0, endCol=0, ci = 0, cj;
				 boolean bFlag= false;boolean bRead=false;
				 String cellValue="";
				 Iterator rows = ws.rowIterator();
			        while (rows.hasNext())
			        {
			            row=(Row) rows.next();
			            Iterator cells = row.cellIterator();
			            while (cells.hasNext())
			            {
			                cell=(Cell) cells.next();
			                if(cell.getCellType()==CellType.STRING) {
			                	cellValue= cell.getStringCellValue();}
			      		  	else if(cell.getCellType()==CellType.NUMERIC || cell.getCellType()==CellType.FORMULA ){
			      		  		try {
			      		  		cellValue = String.valueOf(cell.getNumericCellValue());
			      		  		}catch(Exception e) {
			      		  		cellValue = cell.getStringCellValue();
			      		  		}
			      		  	}
			      		  	else if(cell.getCellType()==CellType.BLANK) {
			      		  		cellValue= "";}
			      		  	else if(cell.getCellType()==CellType.BOOLEAN) {
			      			cellValue= String.valueOf(cell.getBooleanCellValue());}
			      			
			                if (cellValue.equalsIgnoreCase(tableName) && (!bFlag)) {
			                	startRow = cell.getRowIndex();
			                	startCol = cell.getColumnIndex();
			                	bFlag = true;
			                }else if(cellValue.trim().equalsIgnoreCase(tableName) && (bFlag)) {
			                	endRow = cell.getRowIndex();
			                	endCol = cell.getColumnIndex();
			                	bRead= true;
			                	break;
			                }
			            }
			            if(bRead) {
			            	break;
			            }
			        }
			    tabArray = new String[endRow - startRow][endCol - startCol - 2];
			    for (int i = startRow + 1; i <= endRow; ++i, ++ci) {
			    	SessionDetails.currentChildRowRef = startRow+1;
					cj = 0;
					if(getCellData(sheetName,1, i+1).trim().equalsIgnoreCase(matchMainRecord)) {
						for (int j = startCol + 2; j < endCol; j++, cj++) {
							System.out.println(getCellData(sheetName,j, i+1).trim());
							tabArray[ci][cj] = getCellData(sheetName,j, i+1).trim();
						}
					}
				}				
			    workbook.close();
				} catch (Exception e) {
				System.out.println("Error fetching data details for subtable "+ tableName+ " under sheet "+ sheetName);
				// Assert.fail("error in getTableArray()");
				System.out.println(e);
			}
			return (tabArray);
		}
		
}

