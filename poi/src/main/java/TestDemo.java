import com.sun.tools.doclets.formats.html.SourceToHTMLConverter;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.WorkbookUtil;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;

/**
 * Created by lszhen on 2018/1/22.
 */
public class TestDemo {
    private static final String suffix = ".txt";
    private static final String filePath = "/Users/lszhen/Documents/MIT/t1.xlsx";
    private static final String outPutFile = "/Users/lszhen/Documents/MIT/t1.txt";
    public static void main(String[] args) throws IOException, InvalidFormatException {
        File file = new File(filePath);

        String fileName = file.getName();
        System.out.println("fileName="+file.getName());
        String tableName = fileName.substring(0,fileName.lastIndexOf("."));
        System.out.println("tableName = "+tableName);
        XSSFWorkbook xssfWorkbook = new XSSFWorkbook(file);
        XSSFSheet xssfSheet = xssfWorkbook.getSheetAt(0);

        String sql ="";

//        StringBu
        //获取头行，即table列
        XSSFRow firstRow = xssfSheet.getRow(0);
        int firstCell = firstRow.getFirstCellNum();
        int lastCell = firstRow.getLastCellNum();
        for (int k = firstCell;k<=lastCell;k++){
             XSSFCell cell= firstRow.getCell(k);
            //略过空行
            if(null==cell) {continue;}
             String cellString = cell.getStringCellValue();
            System.out.println(cellString);
        }


        int rowstart = xssfSheet.getFirstRowNum();
        int rowEnd = xssfSheet.getLastRowNum();
        for(int i=rowstart;i<=rowEnd;i++)
        {
            XSSFRow row = xssfSheet.getRow(i);
            if(null == row) continue;
            int cellStart = row.getFirstCellNum();
            int cellEnd = row.getLastCellNum();

            for(int k=cellStart;k<=cellEnd;k++)
            {
                XSSFCell cell = row.getCell(k);
                //略过空行
                if(null==cell) {continue;}

                switch (cell.getCellType())
                {
                    case HSSFCell.CELL_TYPE_NUMERIC: // 数字
                        System.out.print(cell.getNumericCellValue()
                                + "   ");
                        break;
                    case HSSFCell.CELL_TYPE_STRING: // 字符串
                        System.out.print(cell.getStringCellValue()
                                + "   ");
                        break;
                    case HSSFCell.CELL_TYPE_BOOLEAN: // Boolean
                        System.out.println(cell.getBooleanCellValue()
                                + "   ");
                        break;
                    case HSSFCell.CELL_TYPE_FORMULA: // 公式
                        System.out.print(cell.getCellFormula() + "   ");
                        break;
                    case HSSFCell.CELL_TYPE_BLANK: // 空值
                        System.out.println(" ");
                        break;
                    case HSSFCell.CELL_TYPE_ERROR: // 故障
                        System.out.println(" ");
                        break;
                    default:
                        System.out.print("未知类型   ");
                        break;
                }

            }
            System.out.print("\n");
        }

        String content = "file out put test";
        writeToFile(content);
    }

    private static void writeToFile(String content) throws IOException {
        //文件输出
        FileOutputStream fot = null;
        try {
            File outputFile = new File(outPutFile);
            fot = new FileOutputStream(outputFile);
            byte[] contentBytes = content.getBytes();
            fot.write(contentBytes);
            //强制将缓存中的输出流（字节流，字符流等）强制输出
            fot.flush();
            fot.close();
        }catch (IOException e){
            e.printStackTrace();
        }finally {
            try {
                if (fot != null){
                    fot.close();
                }
            }catch (IOException ex){
                ex.printStackTrace();
            }
        }

    }
}
