package p2;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class FileProcesser {

    /**
     * @param dir
     * @param wb
     * @throws IOException
     */
    public static Workbook wb;
    ArrayList<Course> courseList = new ArrayList<>();

    public static void save(String dir, Object[][] p) throws IOException {
        // TODO Auto-generated method stub


        try {
            InputStream inp = new FileInputStream(dir);
            wb = WorkbookFactory.create(inp);
            org.apache.poi.ss.usermodel.Sheet sheet1 = wb.getSheetAt(0);
            int rowStart = sheet1.getFirstRowNum();
            int rowEnd = sheet1.getLastRowNum();
            Row tr = sheet1.getRow(0);
            int ColumnEnd = tr.getLastCellNum();
            String s[] = new String[ColumnEnd];
            for (int i = 0; i < ColumnEnd; i++) {
                Cell c = tr.getCell(i, Row.RETURN_BLANK_AS_NULL);
                String temps = c.getRichStringCellValue().getString();

                s[i] = temps;
            }

            for (int rowNum = rowStart + 1; rowNum <= rowEnd; rowNum++) {
                Row r = sheet1.getRow(rowNum);
                int lastColumn = r.getLastCellNum();
                for (int cn = 0; cn < lastColumn; cn++) {
                    Cell c = r.getCell(cn, Row.RETURN_BLANK_AS_NULL);
                    switch (c.getCellType()) {
                        case Cell.CELL_TYPE_STRING:

                            // System.out.println(c.getRichStringCellValue().getString());
                            p[rowNum - 1][cn] = (Object) c.getRichStringCellValue().getString();
                            break;
                        case Cell.CELL_TYPE_NUMERIC:
                            if (DateUtil.isCellDateFormatted(c)) {
                                // System.out.println(c.getDateCellValue());
                                c.setCellValue(p[rowNum - 1][cn].toString());
                            } else {
                                //System.out.println(c.getNumericCellValue());
                                c.setCellValue(p[rowNum - 1][cn].toString());
                            }
                            break;
                        case Cell.CELL_TYPE_BOOLEAN:
                            // System.out.println(c.getBooleanCellValue());
                            c.setCellValue(p[rowNum - 1][cn].toString());
                            break;
                        case Cell.CELL_TYPE_FORMULA:
                            //  System.out.println(c.getCellFormula());
                            c.setCellValue(p[rowNum - 1][cn].toString());
                            break;
                        default:
                            System.out.println();
                    }
                }
            }

        } catch (Exception e1) {
            e1.printStackTrace();
        }
        FileOutputStream out = null;
        out = new FileOutputStream(dir);
        wb.write(out);
    }

    public void read(final String Dir) {

        try {

            InputStream inp = new FileInputStream(Dir);
            Workbook wb = WorkbookFactory.create(inp);
            org.apache.poi.ss.usermodel.Sheet sheet1 = wb.getSheetAt(0);
            int rowStart = sheet1.getFirstRowNum();
            int rowEnd = sheet1.getLastRowNum();
            Row tr = sheet1.getRow(0);
            int ColumnEnd = tr.getLastCellNum();
            String s[] = new String[ColumnEnd];
            for (int i = 0; i < ColumnEnd; i++) {
                Cell c = tr.getCell(i, Row.RETURN_BLANK_AS_NULL);
                String temps = c.getRichStringCellValue().getString();
                s[i] = temps;
            }

            Object[][] cellData=new Object[rowEnd-rowStart][10];
            for (int rowNum = rowStart + 1; rowNum <= rowEnd; rowNum++) {
                Row r = sheet1.getRow(rowNum);
                int lastColumn = r.getLastCellNum();
                for (int cn = 0; cn < lastColumn; cn++) {
                    Cell c = r.getCell(cn, Row.RETURN_BLANK_AS_NULL);
                    switch (c.getCellType()) {
                        case Cell.CELL_TYPE_STRING:
                            //System.out.println(c.getRichStringCellValue().getString());
                            cellData[rowNum - 1][cn] = c.getRichStringCellValue().getString();
                            break;
                        case Cell.CELL_TYPE_NUMERIC:
                            if (DateUtil.isCellDateFormatted(c)) {
                                // System.out.println(c.getDateCellValue());
                                cellData[rowNum - 1][cn] = c.getDateCellValue();
                            } else {
                                // System.out.println(c.getNumericCellValue());
                                cellData[rowNum - 1][cn] = (int) c.getNumericCellValue();
                            }
                            break;
                        case Cell.CELL_TYPE_BOOLEAN:
                            // System.out.println(c.getBooleanCellValue());
                            cellData[rowNum - 1][cn] = c.getBooleanCellValue();
                            break;
                        case Cell.CELL_TYPE_FORMULA:
                            //  System.out.println(c.getCellFormula());
                            cellData[rowNum - 1][cn] = c.getCellFormula();
                            break;
                        default:
                            System.out.println();
                    }
                }
            }
            getCourseObjectFromCell(cellData, rowEnd - rowStart);

        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }

    public ArrayList<Course> getCourseList() {
        return courseList;
    }

    private void getCourseObjectFromCell(Object[][] cellData, int rowNum) {
        for (int i = 0; i < rowNum; i++) {

            Course course = new Course(cellData[i][0].toString(), cellData[i][1].toString(), cellData[i][2].toString(),
                    cellData[i][3].toString(), (cellData[i][4].toString()), (cellData[i][5].toString()), Integer.valueOf(cellData[i][6].toString()),
                    cellData[i][7].toString());

            courseList.add(course);
        }
    }
}