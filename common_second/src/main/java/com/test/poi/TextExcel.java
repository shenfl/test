package com.test.poi;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * https://blog.csdn.net/fukaiit/article/details/82724545
 * @author shenfl
 */
public class TextExcel {
    public static void main(String[] args) throws Exception {
        loadXlsx();
    }

    private static void loadXlsx() throws IOException {
        //创建输入流
        FileInputStream fis = new FileInputStream(new File("ES_ADD.xlsx"));
        //由输入流得到工作簿
        XSSFWorkbook workbook = new XSSFWorkbook(fis);
        //得到工作表
        XSSFSheet sheet = workbook.getSheetAt(0);
        //得到行,0表示第一行
        XSSFRow row = sheet.getRow(0);
        //创建单元格行号由row确定,列号作为参数传递给createCell;第一列从0开始计算
        XSSFCell cell = row.getCell(2);
        //给单元格赋值
        String cellValue = cell.getStringCellValue();
        System.out.println("第一行第三列的值是"+cellValue);
        workbook.close();
        fis.close();
    }
}
