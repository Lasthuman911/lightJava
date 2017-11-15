import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.DataValidation;
import org.apache.poi.ss.usermodel.DataValidationConstraint;
import org.apache.poi.ss.usermodel.DataValidationHelper;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddressList;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * execl java poi
 *
 * @author wzm
 * @create 2017-11-15-15:52
 */
public class ExeclUtil {
    /**
     * @param @param filePath  Excel文件路径
     * @param @param handers   Excel列标题(数组)
     * @param @param downData  下拉框数据(数组)
     * @param @param downRows  下拉列的序号(数组,序号从0开始)
     * @return void
     * @throws
     * @author wzm
     * @Date 2017年11月2日
     * @Description: 生成Excel导入模板
     */

    public static HSSFWorkbook createExcelTemplate(String fileName, String[] handers,
                                                   List<String[]> downData, String[] downRows, HttpServletResponse response) {
        //创建工作薄
        HSSFWorkbook wb = new HSSFWorkbook();

        //表头样式
        HSSFCellStyle style = wb.createCellStyle();
        // 创建一个居中格式
//               style.setAlignment(HSSFCellStyle.ALIGN_CENTER_SELECTION);
        //字体样式
        HSSFFont fontStyle = wb.createFont();
        fontStyle.setFontName("微软雅黑");
        fontStyle.setFontHeightInPoints((short) 12);
//        fontStyle.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);
        style.setFont(fontStyle);
        style.setWrapText(true);

        //新建sheet
        HSSFSheet sheet1 = wb.createSheet("Sheet1");
        HSSFSheet sheet2 = wb.createSheet("Sheet2");
        HSSFSheet sheet3 = wb.createSheet("Sheet3");

        //生成sheet1内容,第一个sheet的第一行为标题
        HSSFRow rowFirst = sheet1.createRow(0);
        rowFirst.setHeightInPoints(20);
        //写标题
        for (int i = 0; i < handers.length; i++) {
            //获取第一行的每个单元格
            HSSFCell cell = rowFirst.createCell(i);
            if (i == 0 || i == 3 || i == 4 || i == 5 || i == 8) {
                //设置每列的列宽
                sheet1.setColumnWidth(i, 6000);
            } else {
                //设置每列的列宽
                sheet1.setColumnWidth(i, 3000);
            }
            //加样式
            cell.setCellStyle(style);
            //往单元格里写数据
            cell.setCellValue(handers[i]);
        }

        //设置下拉框数据
        String[] arr = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};
        int index = 0;
        HSSFRow row = null;
        for (int r = 0; r < downRows.length; r++) {
            //获取下拉对象
            String[] dlData = downData.get(r);
            int rownum = Integer.parseInt(downRows[r]);
            if (dlData == null) {
                continue;
            }
            if (dlData.length < 5) {
                //255以内的下拉,参数分别是：作用的sheet、下拉内容数组、起始行、终止行、起始列、终止列
                //超过255个报错
                sheet1.addValidationData(setDataValidation(sheet1, dlData, 1, 50000, rownum, rownum));
            } else {
                //255以上的下拉，即下拉列表元素很多的情况
                //1、设置有效性
                //String strFormula = "Sheet2!$A$1:$A$5000" ; //Sheet2第A1到A5000作为下拉列表来源数据
                //Sheet2第A1到A5000作为下拉列表来源数据
                String strFormula = "Sheet2!$" + arr[index] + "$1:$" + arr[index] + "$5000";
                //设置每列的列宽
                sheet2.setColumnWidth(r, 4000);
                //设置数据有效性加载在哪个单元格上,参数分别是：从sheet2获取A1到A5000作为一个下拉的数据、起始行、终止行、起始列、终止列
                //下拉列表元素很多的情况
                sheet1.addValidationData(SetDataValidation(strFormula, 1, 50000, rownum, rownum));

                //2、生成sheet2内容
                for (int j = 0; j < dlData.length; j++) {
                    //第1个下拉选项，直接创建行、列,创建数据行
                    if (index == 0) {
                        row = sheet2.createRow(j);
                        //设置每列的列宽
                        sheet2.setColumnWidth(j, 4000);
                        //设置对应单元格的值
                        row.createCell(0).setCellValue(dlData[j]);

                    } else { //非第1个下拉选项

                        int rowCount = sheet2.getLastRowNum();
                        //前面创建过的行，直接获取行，创建列
                        if (j <= rowCount) {
                            //获取行，创建列,设置对应单元格的值
                            sheet2.getRow(j).createCell(index).setCellValue(dlData[j]);

                        } else {
                            //未创建过的行，直接创建行、创建列,设置每列的列宽
                            sheet2.setColumnWidth(j, 4000);
                            //创建行、创建列,设置对应单元格的值
                            sheet2.createRow(j).createCell(index).setCellValue(dlData[j]);
                        }
                    }
                }
                index++;
            }
        }
        return wb;
    }

    /**
     * @param @param strFormula
     * @param @param firstRow   起始行
     * @param @param endRow     终止行
     * @param @param firstCol   起始列
     * @param @param endCol     终止列
     * @author wzm
     * @Date 2017年11月2日
     * @Description: 下拉列表元素很多的情况 (255以上的下拉)
     */
    private static HSSFDataValidation SetDataValidation(String strFormula,
                                                        int firstRow, int endRow, int firstCol, int endCol) {

        // 设置数据有效性加载在哪个单元格上。四个参数分别是：起始行、终止行、起始列、终止列
        CellRangeAddressList regions = new CellRangeAddressList(firstRow, endRow, firstCol, endCol);
        DVConstraint constraint = DVConstraint.createFormulaListConstraint(strFormula);
        HSSFDataValidation dataValidation = new HSSFDataValidation(regions, constraint);

        dataValidation.createErrorBox("Error", "只允许选择有效的下拉框值");
        dataValidation.createPromptBox("", null);

        return dataValidation;
    }


    /**
     * @param @param sheet
     * @param @param textList
     * @param @param firstRow
     * @param @param endRow
     * @param @param firstCol
     * @param @param endCol
     * @author wzm
     * @Date 2017年11月2日
     * @Description: 下拉列表元素不多的情况(255以内的下拉)
     */
    private static DataValidation setDataValidation(Sheet sheet, String[] textList, int firstRow, int endRow, int firstCol, int endCol) {

        DataValidationHelper helper = sheet.getDataValidationHelper();
        //加载下拉列表内容
        DataValidationConstraint constraint = helper.createExplicitListConstraint(textList);
        //DVConstraint constraint = new DVConstraint();
        constraint.setExplicitListValues(textList);

        //设置数据有效性加载在哪个单元格上。四个参数分别是：起始行、终止行、起始列、终止列
        CellRangeAddressList regions = new CellRangeAddressList((short) firstRow, (short) endRow, (short) firstCol, (short) endCol);

        //数据有效性对象
        DataValidation data_validation = helper.createValidation(constraint, regions);
        //DataValidation data_validation = new DataValidation(regions, constraint);

        return data_validation;
    }

}
