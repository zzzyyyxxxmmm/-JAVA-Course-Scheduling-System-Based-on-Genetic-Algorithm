package p2;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.sl.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class file {

	/**
	 * 
	 * @param dir
	 * @param wb
	 * @throws IOException 
	 */
	public static Workbook wb;
	public static void save(String dir,Object[][] p) throws IOException {
		// TODO Auto-generated method stub
		
		
		try
	    {
			InputStream inp = new FileInputStream(dir);
		    wb = WorkbookFactory.create(inp);
			org.apache.poi.ss.usermodel.Sheet sheet1 = wb.getSheetAt(0);
			int rowStart  =sheet1.getFirstRowNum();
		     int rowEnd =sheet1.getLastRowNum();
		    Row tr = sheet1.getRow(0);
		   int ColumnEnd =tr.getLastCellNum();
		   String s[]=new String[ColumnEnd];
		   for (int i = 0; i <ColumnEnd;i++)
		   {
			   Cell c = tr.getCell(i, Row.RETURN_BLANK_AS_NULL);
			   String temps=c.getRichStringCellValue().getString();

			   s[i]=temps;
		   }
		 
		   for (int rowNum = rowStart+1; rowNum <= rowEnd; rowNum++) {
		       Row r = sheet1.getRow(rowNum);
		       int lastColumn =r.getLastCellNum();				     
		       for (int cn = 0; cn < lastColumn; cn++) {
		          Cell c = r.getCell(cn, Row.RETURN_BLANK_AS_NULL);
		          switch (c.getCellType()) {
	                case Cell.CELL_TYPE_STRING:
	                	
	                   // System.out.println(c.getRichStringCellValue().getString());
	                	p[rowNum-1][cn]=(Object)c.getRichStringCellValue().getString();
	                    break;
	                case Cell.CELL_TYPE_NUMERIC:
	                    if (DateUtil.isCellDateFormatted(c)) {
	                       // System.out.println(c.getDateCellValue());
	                    	c.setCellValue(p[rowNum-1][cn].toString());
	                    } else {
	                       //System.out.println(c.getNumericCellValue());
	                    	c.setCellValue(p[rowNum-1][cn].toString());
	                    }
	                    break;
	                case Cell.CELL_TYPE_BOOLEAN:
	                   // System.out.println(c.getBooleanCellValue());
	                	c.setCellValue(p[rowNum-1][cn].toString());
	                    break;
	                case Cell.CELL_TYPE_FORMULA:
	                  //  System.out.println(c.getCellFormula());
	                	c.setCellValue(p[rowNum-1][cn].toString());
	                    break;
	                default:
	                    System.out.println();
	            }				   
		       }
		   }
		   
		   }
		   catch (Exception e1) 
			{   
		           e1.printStackTrace();     
		     }	
		
		
		
		
		
		
		
		
		
		
		
		
		FileOutputStream out = null;
		   out=new FileOutputStream(dir);
		   wb.write(out);
	}
	public static void read(final String Dir,Object[][] cellData){
		
		
	    try
	    {

	    	InputStream inp = new FileInputStream(Dir);
		    Workbook wb = WorkbookFactory.create(inp);
			org.apache.poi.ss.usermodel.Sheet sheet1 = wb.getSheetAt(0);
			int rowStart  =sheet1.getFirstRowNum();
		    int rowEnd =sheet1.getLastRowNum();
		    Row tr = sheet1.getRow(0);
		   int ColumnEnd =tr.getLastCellNum();
		   String s[]=new String[ColumnEnd];
		   for (int i = 0; i <ColumnEnd;i++)
		   {
			   Cell c = tr.getCell(i, Row.RETURN_BLANK_AS_NULL);
			   String temps=c.getRichStringCellValue().getString();
			   s[i]=temps;
		   }
		 
		   for (int rowNum = rowStart+1; rowNum <= rowEnd; rowNum++) {
		       Row r = sheet1.getRow(rowNum);
		       int lastColumn =r.getLastCellNum();				     
		       for (int cn = 0; cn < lastColumn; cn++) {
		          Cell c = r.getCell(cn, Row.RETURN_BLANK_AS_NULL);
		          switch (c.getCellType()) {
	                case Cell.CELL_TYPE_STRING:
	                    //System.out.println(c.getRichStringCellValue().getString());
	                	cellData[rowNum-1][cn]=c.getRichStringCellValue().getString();
	                    break;
	                case Cell.CELL_TYPE_NUMERIC:
	                    if (DateUtil.isCellDateFormatted(c)) {
	                       // System.out.println(c.getDateCellValue());
	                    	cellData[rowNum-1][cn]=c.getDateCellValue();
	                    } else {
	                       // System.out.println(c.getNumericCellValue());
	                    	cellData[rowNum-1][cn]=(int)c.getNumericCellValue();			                       
	                    }
	                    break;
	                case Cell.CELL_TYPE_BOOLEAN:
	                   // System.out.println(c.getBooleanCellValue());
	                	cellData[rowNum-1][cn]=c.getBooleanCellValue();
	                    break;
	                case Cell.CELL_TYPE_FORMULA:
	                  //  System.out.println(c.getCellFormula());
	                	cellData[rowNum-1][cn]=c.getCellFormula();
	                    break;
	                default:
	                    System.out.println();
	            }				   
		       }
		   }
		   
		   }
		   catch (Exception e1) 
			{   
		           e1.printStackTrace();     
		     }	
	   /* for(int i=0;i<=68;i++)
	    {
	    	for(int j=0;j<=7;j++)
	    		System.out.print(cellData[i][j].toString()+" ");
	    	System.out.println();
	    }*/
	    	
	 	    			
}
}
