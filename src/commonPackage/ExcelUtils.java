package commonPackage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.formula.functions.Column;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import businessSpecific.GlobalVariables;

public class ExcelUtils {
	private static XSSFSheet ExcelWSheet;
	private static XSSFWorkbook ExcelWBook;
	private static XSSFCell Cell;
	private static XSSFRow Row;
	
	
	// Set excel file from path
	public void setExcelFile(File file, String SheetName)
			throws Exception {

		try {
			FileInputStream ExcelFile = new FileInputStream(file);

			ExcelWBook = new XSSFWorkbook(ExcelFile);
			ExcelWSheet = ExcelWBook.getSheet(SheetName);
		} catch (Exception e) {
			throw (e);
		}
	}
	

	// get excel cell data for the given row and column
	public String getCellData(int RowNum, int ColNum) throws Exception {
		try {
			Cell = ExcelWSheet.getRow(RowNum).getCell(ColNum);
			String CellData = Cell.getStringCellValue().trim();
			
			return CellData;
		} catch (Exception e) {
			return "";
		}
	}
	
	// set excel cell data for the given row and column
		public void setCellData(String Result, int RowNum, int ColNum)
				throws Exception {
			try {
				Cell = ExcelWSheet.getRow(RowNum).getCell(ColNum);
				Cell.setCellValue(Result);
				FileOutputStream fileOut = new FileOutputStream(
						GlobalVariables.Path_TestData
								+ GlobalVariables.File_TestData);
				ExcelWBook.write(fileOut);
				fileOut.flush();
				fileOut.close();
			} catch (Exception e) {
				throw (e);
			}
		}


	/*// set excel cell data for the given row and column
	public static void setCellData(String Result, int RowNum, int ColNum)
			throws Exception {
		try {
			Row = ExcelWSheet.getRow(RowNum);
			Cell = Row.getCell(ColNum, Row.RETURN_BLANK_AS_NULL);
			if (Cell == null) {
				Cell = Row.createCell(ColNum);
				Cell.setCellValue(Result);
			} else {
				Cell.setCellValue(Result);
			}
			FileOutputStream fileOut = new FileOutputStream(
					GlobalVariables.Path_TestData
							+ GlobalVariables.File_TestData);
			ExcelWBook.write(fileOut);
			fileOut.flush();
			fileOut.close();
		} catch (Exception e) {
			throw (e);
		}
	}*/

	// get row number for the cell data - method 1
	public int getRowNumber(String SheetName, String CellData) {

		String excelPath = GlobalVariables.Path_TestData
				+ GlobalVariables.File_TestData;
		File excel = new File(excelPath);
		FileInputStream fis = null;
		Workbook workBook = null;
		String cellValue = null;
		int testRowNo = 0;
		try {
			fis = new FileInputStream(excel);
			workBook = WorkbookFactory.create(fis);
			org.apache.poi.ss.usermodel.Sheet workSheet = workBook
					.getSheet(SheetName);
			int totalRows = ((org.apache.poi.ss.usermodel.Sheet) workSheet)
					.getLastRowNum();
			org.apache.poi.ss.usermodel.Row row = null;

			for (int rowNo = 1; rowNo <= totalRows; rowNo++) {
				row = ((org.apache.poi.ss.usermodel.Sheet) workSheet)
						.getRow(rowNo);
				testRowNo = testRowNo + 1;
				if (row.getCell(0).getStringCellValue()
						.equalsIgnoreCase(CellData)) {
					break;
				}

			}
		} catch (InvalidFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				fis.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return testRowNo;
	}
	

	// get column number for the cell data
	public int getColumnNo(String SheetName, String CellData) {
		int colNumber = 0;
		ExcelWSheet = ExcelWBook.getSheet(SheetName);
		outerloop: for (org.apache.poi.ss.usermodel.Row row : ExcelWSheet) {
			for (org.apache.poi.ss.usermodel.Cell cell : row) {
				
				if (cell.getCellType() == Cell.CELL_TYPE_STRING
						&& cell.getRichStringCellValue().getString().trim()
								.equals(CellData)) {
					break outerloop;
				}
				colNumber = colNumber + 1;
			}
		}

		return colNumber;

	}

	// get list of row numbers matching for the given cell data
	public ArrayList<Integer> getRowNoList(String SheetName,
			String CellData) {

		ArrayList<Integer> rowsList = new ArrayList<Integer>();
		int rowNumber = 0;
		ExcelWSheet = ExcelWBook.getSheet(SheetName);
		outerloop: for (org.apache.poi.ss.usermodel.Row row : ExcelWSheet) {
			
			for (org.apache.poi.ss.usermodel.Cell cell : row) {

				if (cell.getCellType() == Cell.CELL_TYPE_STRING
						&& cell.getRichStringCellValue().getString().trim()
								.equals(CellData)) {
					rowsList.add(rowNumber);
				}
			}
			rowNumber = rowNumber + 1;
		}

		return rowsList;

	}

	// get row number for the cell data - method 2 (Preffered)
	public int getRowNo(String SheetName, String CellData) {

		int rowValue = 0;
		int rowNumber = 0;
		ExcelWSheet = ExcelWBook.getSheet(SheetName);
		outerloop: for (org.apache.poi.ss.usermodel.Row row : ExcelWSheet) {
			rowNumber = rowNumber + 1;
			for (org.apache.poi.ss.usermodel.Cell cell : row) {

				if (cell.getCellType() == Cell.CELL_TYPE_STRING
						&& cell.getRichStringCellValue().getString().trim()
								.equals(CellData)) {
					rowValue = rowNumber;
					break outerloop;
				}
			}
		}

		return rowValue - 1;

	}

}
