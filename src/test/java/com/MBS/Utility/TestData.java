package com.MBS.Utility;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.Properties;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.simple.*;
import org.json.simple.parser.*;
import org.json.simple.parser.ParseException;
import org.testng.annotations.Test;
import io.restassured.mapper.ObjectMapper;

public class TestData {
	
	public static String[] renameFile(String origin, String  newFileName)
	{
		String fileType="";
		String originf="";
	
		File f1 = new File("UploadData/"+origin);
		
		if(origin.contains(".xlsx")) {
			fileType=".xlsx";
		originf=origin.split(".x")[0];
		}
		else if(origin.contains(".csv")) {
			fileType=".csv";
		originf=origin.split(".c")[0];
		}
		else if(origin.contains(".txt")) {
			fileType=".txt";
		originf=origin.split(".t")[0];
		}
		
		File f2 = new File("UploadData/"+originf+"_"+newFileName+fileType);
		f1.renameTo(f2);
		String [] str={f2.getAbsolutePath(),originf+"_"+newFileName+fileType};
		return str;
	}
	
	public static String renameFile()
	{
		File folder = new File("DownloadData/");
		File[] listOfFiles = folder.listFiles();

		for (int i = 0; i < listOfFiles.length; i++) {
		  if (listOfFiles[i].isFile()) {
		   // System.out.println("File " + listOfFiles[i].getName());
		    //System.out.println("First File " + listOfFiles[0].getName());
		  } else if (listOfFiles[i].isDirectory()) {
		  //  System.out.println("Directory " + listOfFiles[i].getName());
		  }
		  
		 // System.out.println(" First File ===> " + listOfFiles[0].getName()); 
		}
		return listOfFiles[0].getName();
	}
	
	public static String uploadFilePath(String filename) {
		String dataFilePath = "UploadData/" + filename ;
		File datafile = new File(dataFilePath);
		String fullpath = datafile.getAbsolutePath();
		return fullpath;
	}
	public static String dowanloadFilePath(String filename) {
		String dataFilePath = "DownloadData/" + filename;
		File datafile = new File(dataFilePath);
		String fullpath = datafile.getAbsolutePath();
		return fullpath;
	}
	public static String filePath(String folder,String filename) {
		String dataFilePath = folder+"/" + filename;
		File datafile = new File(dataFilePath);
		String fullpath = datafile.getAbsolutePath();
		return fullpath;
	}
	public static String findFile(String directory,String fileNm)
	{
		File dir = new File(directory);
	      String[] children = dir.list();
	      String name = null;
	      
	      if (children == null) {
	      } else {
	         for (int i = 0; i < children.length; i++) {
	            if(children[i].contains(fileNm))
	            {
	            	name=children[i];
	            	break;
	            }
	           
	         }
	        
	      } return name;
	}
	   


	public static XSSFSheet ExcelWSheet;
	public static XSSFWorkbook ExcelWBook;
	public static XSSFCell Cell;
	public static XSSFRow Row;
	static DataFormatter formatter = new DataFormatter();
	// Data provider
	@SuppressWarnings("unchecked")
	public static <UnicodeString> UnicodeString getCellData(int RowNum, int ColNum) throws Exception {
		try {
			Cell = ExcelWSheet.getRow(RowNum).getCell(ColNum);
			int dataType = Cell.getCellType();
			if (dataType == 3) {
				return (UnicodeString) "";
			} else {
				DataFormatter formatter = new DataFormatter();
				UnicodeString Data = (UnicodeString) formatter.formatCellValue(Cell);
				return Data;
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
			throw (e);
		}
	}
	public static void  clearProperties(String Datafile) throws IOException {
		FileInputStream fileInput = null;
		try {
			fileInput = new FileInputStream(new File(Datafile));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		FileWriter fw= new FileWriter(new File(Datafile));
		Properties prop = new Properties();
		try {
				prop.load(fileInput);
				prop.store(fw, null);
		} catch (Exception e) {
			System.out.println("Exception: " + e);
		} finally {
		}
	}
	public static Object[][] getDataForDataprovider(String FilePath, String SheetName,int startRow,int startCol)
			throws Exception {
		String[][] tabArray = null;
		try {
			FileInputStream ExcelFile = new FileInputStream(FilePath);
			ExcelWBook = new XSSFWorkbook(ExcelFile);
			ExcelWSheet = ExcelWBook.getSheet(SheetName);
			int ci, cj;
			int totalRows = ExcelWSheet.getLastRowNum();
			Row = ExcelWSheet.getRow(2);
			int totalCols = Row.getPhysicalNumberOfCells();
			tabArray = new String[totalRows - 1][totalCols];
			ci = 0;
			for (int i = startRow; i <= totalRows; i++, ci++) {
				cj = 0;
				for (int j = startCol; j < totalCols; j++, cj++) {
					tabArray[ci][cj] = getCellData(i, j);
				}
			}
		} catch (FileNotFoundException e) {
			System.out.println("Could not read the Excel sheet");
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("Could not read the Excel sheet");
			e.printStackTrace();
		}
		return (tabArray);
	}

//  write data into excel file
	
	public static void writeExcel(String filename, String sheetname,  String cell[]) throws IOException {
		File datafile = new File(filename);
		String fullpath = datafile.getAbsolutePath();
		ExcelWBook = new XSSFWorkbook(fullpath);
		ExcelWSheet = ExcelWBook.getSheet(sheetname);
		int totalRows = ExcelWSheet.getLastRowNum();
		try {
			int rowno = totalRows + 1;
			FileInputStream inputStream = new FileInputStream(new File(fullpath));
			@SuppressWarnings("resource")
			XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
			XSSFSheet firstSheet = workbook.getSheetAt(0);
			XSSFRow row = firstSheet.createRow(rowno);
			for(int i=0;i<cell.length;i++)
			{
				row.createCell(i).setCellValue(cell[i].toString());
			}
			inputStream.close();
			FileOutputStream fos = new FileOutputStream(new File(fullpath));
			workbook.write(fos);
			fos.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	
	// Create new excel file
	public static void createXLSFile(String filepath,String Sheetname, String cell[]) {
		try
		{
		File datafile = new File(filepath);
		String fullpath = datafile.getAbsolutePath();
		@SuppressWarnings("resource")
		XSSFWorkbook workbook = new XSSFWorkbook();
		XSSFSheet sheet = workbook.createSheet(Sheetname);
		XSSFRow row = sheet.createRow(0);
		for(int i=0;i<cell.length;i++)
		{
			row.createCell(i).setCellValue(cell[i].toString());
		}
		FileOutputStream fileOut = new FileOutputStream(fullpath);
		workbook.write(fileOut);
		fileOut.close();
		}catch (Exception ex) {
			System.out.println(ex);
		}
	}
// Get total number of row from excel sheet
	public static int getTotalRow(String Datafile,String sheet) {
		int totalRows = 0;
		try {
			File datafile = new File(Datafile);
			String fullpath = datafile.getAbsolutePath();
			ExcelWBook = new XSSFWorkbook(fullpath);
			ExcelWSheet = ExcelWBook.getSheet(sheet);
			totalRows = ExcelWSheet.getLastRowNum();
		} catch (Exception e) {
		}
		return totalRows;
	}

	public static boolean verifyCellValue(String datafile,String sheet, String value,int i , int j)
	{
		boolean ServiceFlag = false;
		try {
			FileInputStream ExcelFile = new FileInputStream(datafile);
			ExcelWBook = new XSSFWorkbook(ExcelFile);
			ExcelWSheet = ExcelWBook.getSheet(sheet);
			String ser = getCellData(i, j);
			
					if (ser.equalsIgnoreCase(value.trim())||value.trim().contains(ser)||ser.contains(value.trim()))
						ServiceFlag = true;
				
		} catch (FileNotFoundException e) {
			System.out.println("Could not read the Excel sheet");
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("Could not read the Excel sheet");
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ServiceFlag;
	}
	public static String getCellValue(String datafile,String sheet,int i , int j)
	{
		String ser=null;
		try {
			FileInputStream ExcelFile = new FileInputStream(datafile);
			ExcelWBook = new XSSFWorkbook(ExcelFile);
			ExcelWSheet = ExcelWBook.getSheet(sheet);
			ser = getCellData(i, j);
		} catch (FileNotFoundException e) {
			System.out.println("Could not read the Excel sheet");
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("Could not read the Excel sheet");
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ser;
	}

	public static boolean verifyRowWiseColumnValue(String Datafile,String sheet,String value,int ColumnNumber) throws Exception {
		boolean ServiceFlag = false;
		String[][] tabArray = null;
		try {
			FileInputStream ExcelFile = new FileInputStream(Datafile);
			ExcelWBook = new XSSFWorkbook(ExcelFile);
			ExcelWSheet = ExcelWBook.getSheet(sheet);
			int startRow = 1;
			int ci, cj;
			int totalRows = ExcelWSheet.getLastRowNum();
			int totalCols = 5;
			tabArray = new String[totalRows][totalCols];
			ci = 0;
			for (int i = startRow; i <= totalRows; i++, ci++) {
				cj = ColumnNumber;
				int j = ColumnNumber;
				tabArray[ci][cj] = getCellData(i, j);

				if (((String) getCellData(i, j)).equalsIgnoreCase(value.trim())
						|| ((String) getCellData(i, j)).contains(value.trim())) {
					ServiceFlag = true;
					break;
				}
			}
		} catch (FileNotFoundException e) {
			System.out.println("Could not read the Excel sheet");
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("Could not read the Excel sheet");
			e.printStackTrace();
		}
		return ServiceFlag;
	}
	public static void removeRowFromExcel(String Datafile,String sheetName, String value) throws IOException {
		try {
			Workbook wb = WorkbookFactory.create(new FileInputStream(Datafile));
			Sheet sheet = wb.getSheet(sheetName);
			@SuppressWarnings("resource")
			Workbook wb2 = new HSSFWorkbook();
			wb2 = wb;
			Row row;
			row = sheet.getRow(0);
			if (row == null)
				row = sheet.getRow(1);
			int lastIndex = sheet.getLastRowNum();
			boolean flag = true;
			int rownum = 0;
			for (int n = 1; n <= sheet.getLastRowNum(); n++) {
				// sheet.getRow(0).getCell(0);
				row = sheet.getRow(n);
				for (int cn = 0; cn < row.getLastCellNum(); cn++) {
					Cell c = row.getCell(cn);
					String text = c.getStringCellValue();
					if (value.equals(text)) {
						flag = false;
						break;
					}
				}
				rownum = n;
				if (flag == false) {
					break;
				}
			}
			if (rownum != 0) {
				row = sheet.getRow(rownum);
				row.setZeroHeight(true);
				sheet.removeRow(row);
				if(rownum + 1<=lastIndex){
				sheet.shiftRows(rownum + 1, lastIndex, -1);
				}
				FileOutputStream fileOut = new FileOutputStream(Datafile);
				wb2.write(fileOut);
				fileOut.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public static void removeRowFromExcel(String Datafile,String sheetName, int rowNumber) throws IOException {
		try {
			Workbook wb = WorkbookFactory.create(new FileInputStream(Datafile));
			Sheet sheet = wb.getSheet(sheetName);
			@SuppressWarnings("resource")
			Workbook wb2 = new HSSFWorkbook();
			wb2 = wb;
			int lastIndex = sheet.getLastRowNum();
			Row row;
			row = sheet.getRow(rowNumber);
			//row.setZeroHeight(true);
			sheet.removeRow(row);
			if(rowNumber + 1<=lastIndex){
			sheet.shiftRows(rowNumber + 1, lastIndex, -1);
			}
			FileOutputStream fileOut = new FileOutputStream(Datafile);
			wb2.write(fileOut);
			fileOut.close();
			
			//sheet.removeRow(sheet.getRow(rowNumber));
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static String getValueFromConfig(String Datafile, String value) throws IOException {
		String result="";
		File file = new File(Datafile);
		FileInputStream fileInput = null;
		try {
			fileInput = new FileInputStream(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		Properties prop = new Properties();
		try {
				prop.load(fileInput);
				result = prop.getProperty(value);
		} catch (Exception e) {
			System.out.println("Exception: " + e);
		} finally {
		}
		return result;
	}
	public static void setValueConfig(String Datafile, String Key, String value) throws IOException {
		File file = new File(Datafile);
		FileInputStream fileInput = null;
		try {
			fileInput = new FileInputStream(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		Properties prop = new Properties();
		try {
				prop.load(fileInput);
				prop.setProperty(Key, value);
		} catch (Exception e) {
			System.out.println("Exception: " + e);
		} finally {
		}
		
		prop.store(new FileOutputStream(file), null);
	}
	public static void setCellData(String DataFile,String sheet, String Result,  int RowNum, int ColNum) throws Exception  
	 {
	  try
	  {
		  FileInputStream ExcelFile = new FileInputStream(DataFile);
		  ExcelWBook = new XSSFWorkbook(ExcelFile);
		  ExcelWSheet = ExcelWBook.getSheet(sheet);
	   Row  = ExcelWSheet.getRow(RowNum);
	   Cell = Row.getCell(ColNum);
	   if (Cell == null) 
	   {
	    Cell = Row.createCell(ColNum);
	    Cell.setCellValue(Result);
	   } 
	   else 
	   {
	    Cell.setCellValue(Result);
	   }
	   
	   /*Constant variables Test Data path and Test Data file name*/
	   FileOutputStream fileOut = new FileOutputStream(DataFile);
	   ExcelWBook.write(fileOut);
	   fileOut.flush();
	   fileOut.close();
	  }
	  catch(Exception e)
	  {
	   throw (e);
	  }
	 }
	public static void createZipfileForOutPut( String FolderName)
	{String home = System.getProperty("user.home");
	File directory = new File(home+"/Documents/" +"AutomationExecutionReports");
    if (! directory.exists()){
        directory.mkdir();
    }
	try {
		FileOutputStream fos = new FileOutputStream(home+"/Documents/AutomationExecutionReports/"+FolderName+".zip");
		ZipOutputStream zos = new ZipOutputStream(fos);
			File folder = new File("test-output/HHAeXchangeAutomation.html");
			ArrayList<File> filelist = new ArrayList<File>();
			listf(folder.getPath(), filelist);
	
		    for (int i = 0; i < filelist.size(); i++) {
		      if (filelist.get(i).isFile()) {
		    	  addCopyFile(filelist.get(i).getPath(), zos);
		      } else if (filelist.get(i).isDirectory()) {
		      }
		    }	
			zos.close();
			fos.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void listf(String directoryName, ArrayList<File> files) {
	    File directory = new File(directoryName);
	    File[] fList = directory.listFiles();
	    for (File file : fList) {
	        if (file.isFile()) {
	            files.add(file);
	        } else if (file.isDirectory()) {
	            listf(file.getPath(), files);
	        }
	    }
	}
	
	public static void addCopyFile(String fileName, ZipOutputStream zos) throws FileNotFoundException, IOException {
		File file = new File(fileName);
		FileInputStream fis = new FileInputStream(file);
		ZipEntry zipEntry = new ZipEntry(fileName);
		zos.putNextEntry(zipEntry);
		byte[] bytes = new byte[1024];
		int length;
		while ((length = fis.read(bytes)) >= 0) {
			zos.write(bytes, 0, length);
		}
		zos.closeEntry();
		fis.close();
	}
	
	public static void deletePastScreenshots(String path)
	{
		File index = new File(path);
		
		if(index.exists() && index.isDirectory())
		{
			String[]entries = index.list();
			
			for(String s: entries)
			{
			    File currentFile = new File(index.getPath(),s);
			    currentFile.delete();
			}
			
			index.delete();
		}
	}
	
	public static void updatedBuildAnalysis(String DataFile,String sheet, String header,String TestName, String Result,int col) throws Exception  
	 {
		String[][] tabArray = null;
	  try
	  {
		  FileInputStream ExcelFile = new FileInputStream(DataFile);
		  ExcelWBook = new XSSFWorkbook(ExcelFile);
		  ExcelWSheet = ExcelWBook.getSheet(sheet);
	  // Row  = ExcelWSheet.getRow(RowNum);
	  // Cell = Row.getCell(ColNum);
	   int ColumnNumber=1;
	   int startRow = 1;
		int ci, cj;
		int totalRows = ExcelWSheet.getLastRowNum();
		int totalCols = 2;
		tabArray = new String[totalRows][totalCols];
		ci = 0;
		for (int i = startRow; i <= totalRows; i++, ci++) {
			cj = ColumnNumber;
			int j = ColumnNumber;
			tabArray[ci][cj] = getCellData(i, j);

			if (((String) getCellData(i, j)).equalsIgnoreCase(TestName.trim())) {
				
				break;
			}
		}
		Row= ExcelWSheet.getRow(Cell.getRowIndex());
		Cell=Row.createCell(col);
	    Cell.setCellValue(Result);
	  
	   FileOutputStream fileOut = new FileOutputStream(DataFile);
	   ExcelWBook.write(fileOut);
	   fileOut.flush();
	   fileOut.close();
	  }
	  catch(Exception e)
	  {
	   throw (e);
	  }
	
	 }
	public static int setHeaderDateTime(String DataFile,String sheet, String Header) throws Exception  
	 { int col=0;
	  try
	  {
		  FileInputStream ExcelFile = new FileInputStream(DataFile);
		  ExcelWBook = new XSSFWorkbook(ExcelFile);
		  ExcelWSheet = ExcelWBook.getSheet(sheet);
	   Row  = ExcelWSheet.getRow(0);
	   col=Row.getLastCellNum();
	   Cell = Row.getCell(col);
	   if (Cell == null) 
	   {
	    Cell = Row.createCell(col);
	    Cell.setCellValue(Header);
	   } 
	   else 
	   {
	    Cell.setCellValue(Header);
	   }
	   
	   FileOutputStream fileOut = new FileOutputStream(DataFile);
	   ExcelWBook.write(fileOut);
	   fileOut.flush();
	   fileOut.close();
	  }catch(Exception e)
	  {
	  }
	  return col;
	 }
	private static final int BUFFER_SIZE = 4096;
	public static void unzip(String zipFilePath, String destDirectory) throws IOException {
        File destDir = new File(destDirectory);
        if (!destDir.exists()) {
            destDir.mkdir();
        }
        ZipInputStream zipIn = new ZipInputStream(new FileInputStream(zipFilePath));
        ZipEntry entry = zipIn.getNextEntry();
        // iterates over entries in the zip file
        while (entry != null) {
            String filePath = destDirectory + File.separator + entry.getName();
            if (!entry.isDirectory()) {
                // if the entry is a file, extracts it
                extractFile(zipIn, filePath);
            } else {
                // if the entry is a directory, make the directory
                File dir = new File(filePath);
                dir.mkdir();
            }
            zipIn.closeEntry();
            entry = zipIn.getNextEntry();
        }
        zipIn.close();
    }
    /**
     * Extracts a zip entry (file entry)
     * @param zipIn
     * @param filePath
     * @throws IOException
     */
    private static void extractFile(ZipInputStream zipIn, String filePath) throws IOException {
        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(filePath));
        byte[] bytesIn = new byte[BUFFER_SIZE];
        int read = 0;
        while ((read = zipIn.read(bytesIn)) != -1) {
            bos.write(bytesIn, 0, read);
        }
        bos.close();
    }
    
    public static ArrayList<String>  getColumnData(String Filepath,String sheetname, String header) throws IOException
   	{
   		int rownum=0;
   		ArrayList<String> code = new  ArrayList<String>();
   		formatter = new DataFormatter();
   		FileInputStream	fin = new FileInputStream(Filepath);
   			ExcelWBook = new XSSFWorkbook(fin);
   			ExcelWSheet = ExcelWBook.getSheet(sheetname);
   		try {

   			int rowCount = ExcelWSheet.getLastRowNum();
   			for (int i = 0; i < rowCount; i++) {

   				rownum = i + 1;
   				code.add(getCellData(rownum, header));
   			}
   			ExcelWBook.close();
   			fin.close();
   		}
   		catch(Exception e)
   		{
   			
   		}
   		return code;
   	}
    
    public static ArrayList<ArrayList<String>> getColumnData1(String Filepath,String sheetname, String[] header) throws IOException
	{
		int rownum=0;
		ArrayList<ArrayList<String>> code = new  ArrayList<ArrayList<String>>(header.length);
		ArrayList<String> c =null;
		formatter = new DataFormatter();
		FileInputStream	fin = new FileInputStream(Filepath);
			ExcelWBook = new XSSFWorkbook(fin);
			ExcelWSheet = ExcelWBook.getSheet(sheetname);
		try {

			int rowCount = ExcelWSheet.getLastRowNum();
				
				for (int i = 0; i < rowCount; i++) {
					c = new ArrayList<String>();
				rownum = i + 1;
				for(int j=0;j<header.length;j++)
				{
					c.add(getCellData(rownum, header[j]));
				}
				code.add(c);
			}
			
			ExcelWBook.close();
			fin.close();
		}
		catch(Exception e)
		{
			
		}
		
		return code;
	}
    public static ArrayList<ArrayList<String>> getColumnDataOtherFile(String filename,String splitsign,String[] header)
	{
        String line = "";
        int loop=0;
		ArrayList<ArrayList<String>> code = new  ArrayList<ArrayList<String>>(header.length);
        ArrayList<String> c = null;
        ArrayList<Integer> index= new  ArrayList<Integer>();
		String[] data = null;
	
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {

            while ((line = br.readLine()) != null) {
  	
            	if(line.contains(splitsign)) 
            	{
            		data = line.split(Pattern.quote(splitsign));
                
                if(loop==0)
                {
                	System.out.println("-=====-i"+loop);
                  for(int i=0;i<header.length;i++)
                  {
                	  
                	  System.out.println(i+"-===data[i]=-i"+data[i]);
                	  for(int j=0;j<data.length;j++)
                	  {
                		  if(data[j].contains(header[i]))
                		  {
                			  index.add(i);
                			  break;
                		  }
                	  }
                  }
					loop++;
                }
                else
                {
                	c = new ArrayList<String>();
                	for(int i=0;i<index.size();i++)
                	{
                		c.add(data[i]);
                	}
                	code.add(c);
                }
            }
            }
            
        } catch (IOException e) {
            e.printStackTrace();
        }
		return code;	 
	}
    
    public static ArrayList<ArrayList<String>>  getColumnData(String Filepath,String sheetname, String[] header) throws IOException
	{
		int rownum=0;
		ArrayList<ArrayList<String>> code = new  ArrayList<ArrayList<String>>(header.length);
		ArrayList<String> c =null;
		formatter = new DataFormatter();
		FileInputStream	fin = new FileInputStream(Filepath);
			ExcelWBook = new XSSFWorkbook(fin);
			ExcelWSheet = ExcelWBook.getSheet(sheetname);
		try {

			int rowCount = ExcelWSheet.getLastRowNum();
				
				for (int i = 0; i < rowCount; i++) {
					c = new ArrayList<String>();
				rownum = i + 1;
				for(int j=0;j<header.length;j++)
				{
					c.add(getCellData(rownum, header[j]));
				}
				code.add(c);
			}
			
			ExcelWBook.close();
			fin.close();
		}
		catch(Exception e)
		{
			
		}
		
		return code;
	}
    
    public static ArrayList<String>  getColumnDataByColumnNumber(String Filepath,String sheetname, int header) throws IOException
   	{
   		ArrayList<String> code = new  ArrayList<String>();
   		formatter = new DataFormatter();
   		FileInputStream	fin = new FileInputStream(Filepath);
   			ExcelWBook = new XSSFWorkbook(fin);
   			ExcelWSheet = ExcelWBook.getSheet(sheetname);

   		
   		try {

   			for (int i = 0; i < 1; i++) {
   				code.add(getCellDataInteger(i,  header));
   			}
   			ExcelWBook.close();
   			fin.close();
   		}
   		catch(Exception e)
   		{
   			
   		}
   		return code;
   	}
   	
	
	

	public static String getCellData(int RowNum, String Header) throws Exception {
		try {
			String value = formatter.formatCellValue(ExcelWSheet.getRow(RowNum).getCell(readHeader(Header)));
			return value;
		} catch (Exception e) {
			return "";
		}
	}
	

	public static String getCellDataInteger(int RowNum, int n) throws Exception {
		try {
			String value = formatter.formatCellValue(ExcelWSheet.getRow(RowNum).getCell(n));
			return value;
		} catch (Exception e) {
			return "";
		}
	}

	public static int readHeader(String Header) {
		try {
			int colNum = ExcelWSheet.getRow(0).getLastCellNum();
			Row = ExcelWSheet.getRow(0);
			for (int j = 0; j < colNum; j++) {
				Cell = Row.getCell(j);
				String cellValue = formatter.formatCellValue(Cell);
				if (cellValue.equalsIgnoreCase(Header)) {
					return j;
				}

			}
		} catch (Exception e) {
		}
		return -1;
	}
	
	public static int getTotalHeader(String datafile,String sheet) throws IOException
	{
		FileInputStream ExcelFile = new FileInputStream(datafile);
		ExcelWBook = new XSSFWorkbook(ExcelFile);
		ExcelWSheet = ExcelWBook.getSheet(sheet);
		
		return  ExcelWSheet.getRow(0).getLastCellNum();
	}
	
	public static int getFileCount(String directory, String type)
	{
		int n=0;
			  File dir = new File(directory);
			  for (File file : dir.listFiles()) {
			    if (file.getName().endsWith((type))) {
			      n++;
			    }
			  }
		return n;
	}
	
	public static ArrayList<String>  getAllFileData(String filename,String splitsign,String field)
	{
		String dataFile =filename;
        String line = "";
        int loop=0;
        int no=1;
		int filedIndex=0;
        ArrayList<String> code = new  ArrayList<String>();
		boolean flag=false;
		String[] data = null;
	
        try (BufferedReader br = new BufferedReader(new FileReader(dataFile))) {

            while ((line = br.readLine()) != null) {
  	
            	if(line.contains(splitsign)) 
            	{
            		data = line.split(Pattern.quote(splitsign));
                
                if(loop==0)
                {
                  for(int i=0;i<data.length;i++)
                  {
				   if(data[i].contains(field))
				   {
					filedIndex=i;
					break;
				   }
                  }
					loop++;
                }
                else
                {
                	code.add(data[filedIndex]);
                	
                }
            }
            }
            
        } catch (IOException e) {
            e.printStackTrace();
        }
		return code;	 
	}
	public static int  getTotalColumn(String filename,String splitsign)
	{
        String line = "";
		String[] data = null;
	
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {

            while ((line = br.readLine()) != null) {
  	
            	if(line.contains(splitsign)) 
            	{
            		data = line.split(Pattern.quote(splitsign));
            	}
            	break;
            }
            
        } catch (IOException e) {
            e.printStackTrace();
        }
		return data.length;	 
	}
	public static int  getTotalRowOtherFiles(String filename,String splitsign)
	{
        String line = "";
        int lineNo=0;
	
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {

            while ((line = br.readLine()) != null) {
  	
            	lineNo++;
            }
            
        } catch (IOException e) {
            e.printStackTrace();
        }
		return lineNo;	 
	}
	public static String getNoMatch(String minimum)
	{
	String code="";	
		
	
	for(int i=1;i<Integer.parseInt(minimum);i++)
	{
		if(i==1)
		code=String.valueOf(i);
		else
		code=code+";"+String.valueOf(i);		
	}
		
		return code;
	}
	
	
	public static String getLowConf(String minimum, String confCode)
	{
	String code="";	
	for(int i=Integer.parseInt(minimum);i<=10;i++)
	{
			if(!confCode.equalsIgnoreCase(""))
			{
				
				if(!confCode.contains(String.valueOf(i)))
				{
					if(i==Integer.parseInt(minimum))
					{
						code=String.valueOf(i);
					}
					else
					{
						if(code.equalsIgnoreCase(""))
							code=String.valueOf(i);
						else
						code=code+";"+String.valueOf(i);
					}
				}		
			}
			else
			{
				if(i==Integer.parseInt(minimum))
				{
					code=String.valueOf(i);
				}
				else
					code=code+";"+String.valueOf(i);
			
			}
		}
		return code;
	}
	

	 public static ArrayList<String>  getColumnDatafromeachrow(String Filepath,String sheetname, String header) throws IOException
	   	{
	   		int rownum=0;
	   		ArrayList<String> code = new  ArrayList<String>();
	   		formatter = new DataFormatter();
	   		FileInputStream	fin = new FileInputStream(Filepath);
	   			ExcelWBook = new XSSFWorkbook(fin);
	   			ExcelWSheet = ExcelWBook.getSheet(sheetname);
	   		try {

	   			int rowCount = ExcelWSheet.getLastRowNum();
	   			for (int i = 0; i < rowCount; i++) {

	   				rownum = i + 1;
	   				code.add(getCellData(rownum, header));
	   				
	   				
	   			}
	   			ExcelWBook.close();
	   			fin.close();
	   		}
	   		catch(Exception e)
	   		{
	   			
	   		}
	   		return code;
	   	}
	 
	 public static Date getTokenExpiryTime() {
		 
		 Calendar calendar = Calendar.getInstance();
		 calendar.add(Calendar.SECOND, 28800);
		 
		return calendar.getTime();
		 	 
	 }
	 
	 
	 public static String readFromJsonFile(String fileName) throws Exception {
		 
		 	String file = fileName;
	        String json = readFileAsString(file);
	        System.out.println(json);
			return json;
		   }
		
	 public static String readFileAsString(String file)throws Exception
	    {
	        return new String(Files.readAllBytes(Paths.get(file)));
	    }
	   
	    
	 
}
