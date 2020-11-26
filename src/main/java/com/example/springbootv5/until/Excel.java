package com.example.springbootv5.until;

import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.formula.functions.T;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.util.CellRangeAddress;

import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 * @ClassName： Excel
 * @Description： TODO
 * @Date： 2020/11/10 2:04 下午
 * @author： ZhuFangTao
 */
public class Excel<T> {

    public HSSFWorkbook getHSSFWorkbook(String title, List<T> dataset, String pattern) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        // 声明一个工作薄
        HSSFWorkbook workbook = new HSSFWorkbook();
        // 生成一个表格
        HSSFSheet sheet = workbook.createSheet(title);
        sheet.setDefaultColumnWidth(18);
        sheet.setDefaultRowHeight((short) 500);

        //设置标题合并
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 7));
        //设置标题样式
        HSSFCellStyle headStyle = workbook.createCellStyle();
        // 创建一个居中格式
        headStyle.setAlignment(HorizontalAlignment.CENTER);
        headStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        //设置标题字体加粗样式
        HSSFFont headFont = workbook.createFont();
        headFont.setBold(true);
        headStyle.setFont(headFont);

        //设置样式2
        HSSFCellStyle styleTwo = workbook.createCellStyle();
        // 创建一个居中格式
        styleTwo.setAlignment(HorizontalAlignment.CENTER);
        styleTwo.setVerticalAlignment(VerticalAlignment.CENTER);

        //数据为空直接返回null；
        if (dataset == null || dataset.size() == 0) {
            return null;
        }
        //利用反射获取列名数组
        T temp = dataset.get(0);
        Field[] heads = temp.getClass().getDeclaredFields();
        List<String> headList = new ArrayList<>();
        //获取字段注解的表头
        for(int i=0;i<heads.length;i++) {
            //过滤掉没加注解的字段
            if (heads[i].getAnnotations().length == 0) {
                continue;
            }
            ExcelAnno annotation =(ExcelAnno)heads[i].getAnnotations()[0];
            String head = annotation.head();
            headList.add(head);
        }


        HSSFRow row0=sheet.createRow( 0 );
        //创建单元格（excel的单元格，参数为列索引，可以是0～255之间的任何一个
        HSSFCell cell0=row0.createCell( 0 );
        //设置单元格内容
        cell0.setCellValue( "城感统帐号登陆导出表格" );
        cell0.setCellStyle(headStyle);

        //产生列名
        HSSFRow row = sheet.createRow(1);
        for (int i=0;i<headList.size();i++) {
            HSSFCell cell = row.createCell(i);
            HSSFRichTextString text = new HSSFRichTextString(headList.get(i));
            cell.setCellValue(text);
            cell.setCellStyle(styleTwo);

        }
        // 遍历集合数据，产生数据行
        Iterator<T> it = dataset.iterator();
        int index = 1;
        while (it.hasNext()) {
            index++;
            row = sheet.createRow(index);
            T t = (T) it.next();
            Field[] fields = t.getClass().getDeclaredFields();
            List<Field> fieldsList = new ArrayList<>();
            for (Field field : fields) {
                if(field.getAnnotations().length != 0) {
                    fieldsList.add(field);
                }
            }
            for (Field field:fieldsList) {
                HSSFCell cell = row.createCell(fieldsList.indexOf(field));
                cell.setCellStyle(styleTwo);
                String fieldName = field.getName();
                String getMethodName = "get"
                        + fieldName.substring(0, 1).toUpperCase()
                        + fieldName.substring(1);
                Class tCls = t.getClass();
                Method getMethod = tCls.getMethod(getMethodName, new Class[]{});
                Object value = getMethod.invoke(t, new Object[]{});
                // 判断值的类型后进行强制类型转换
                String textValue = null;
                if (value == null) {
                    cell.setCellValue("");
                }
                if (value instanceof Integer) {
                    int intValue = (Integer) value;
                    cell.setCellValue(intValue);
                } else if (value instanceof Float) {
                    float fValue = (Float) value;
                    cell.setCellValue(fValue);
                } else if (value instanceof Double) {
                    double dValue = (Double) value;
                    cell.setCellValue(dValue);
                } else if (value instanceof Long) {
                    long longValue = (Long) value;
                    cell.setCellValue(longValue);
                } else if (value instanceof Date) {
                    Date date = (Date) value;
                    SimpleDateFormat sdf = new SimpleDateFormat(pattern);
                    textValue = sdf.format(date);
                    cell.setCellValue(textValue);
                } else {
                    // 其它数据类型都当作字符串简单处理
                    textValue = value==null? "":value.toString();
                    cell.setCellValue(textValue);
                }
            }
        }
        return workbook;
    }
}
