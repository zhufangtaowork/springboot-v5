//package com.example.springbootv5.until;
//
//import org.apache.poi.hssf.usermodel.*;
//import org.apache.poi.hssf.util.HSSFColor;
//import org.apache.poi.ss.util.CellRangeAddress;
//
//import javax.servlet.http.HttpServletResponse;
//import java.io.OutputStream;
//import java.lang.reflect.Field;
//import java.lang.reflect.Method;
//import java.text.SimpleDateFormat;
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.Iterator;
//import java.util.List;
//
///**
// * Created by staunch on 2016/11/22.
// */
//public class ExportExcelUtil<T> {
//
//    /**
//     * 这是一个通用的方法，利用了JAVA的反射机制，可以将放置在JAVA集合中并且符号一定条件的数据以EXCEL
//     * 的形式输出到指定IO设备上
//     * @param title   表格标题名
//     * @param dataset 需要显示的数据集合,集合中一定要放置符合javabean风格的类的对象。
//     * @param out     与输出设备关联的流对象，可以将EXCEL文档导出到本地文件或者网络中
//     * @param pattern 如果有时间数据，设定输出格式。默认为"yyy-MM-dd"
//     * @param response
//     */
//    @SuppressWarnings("deprecation")
//    public void exportExcel(String title, List<T> dataset, OutputStream out, String pattern, HttpServletResponse response) throws Exception {
//        // 声明一个工作薄
//        HSSFWorkbook workbook = new HSSFWorkbook();
//        // 生成一个表格
//        HSSFSheet sheet = workbook.createSheet(title);
//        // 设置表格默认列宽度为20个字节
//        sheet.setDefaultColumnWidth((short) 20);
//        sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 8));
//        //设置标题样式
//        HSSFCellStyle style = workbook.createCellStyle();
//        // 创建一个居中格式
//        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
//
//        HSSFFont font = workbook.createFont();
//        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
//
//        if (dataset == null || dataset.size() == 0) {
//            return;
//        }
//
//        T tempT = dataset.get(0);
//        Field[] heads = tempT.getClass().getDeclaredFields();
//        List<String> headList = new ArrayList<>();
//        //获取字段注解的表头
//        for(int i=0;i<heads.length;i++) {
//            //过滤掉没加注解的字段
//            if (heads[i].getAnnotations().length == 0) {
//                continue;
//            }
//            ExcelAnno exAnno =(ExcelAnno)heads[i].getAnnotations()[0];
//            String head = exAnno.head();
//            headList.add(head);
//        }
//        HSSFRow row0=sheet.createRow( 0 );
//        //创建单元格（excel的单元格，参数为列索引，可以是0～255之间的任何一个
//        HSSFCell cell0=row0.createCell( 0 );
//        //设置单元格内容
//        cell0.setCellValue( "城感统帐号登陆导出表格" );
//        style.setFont(font);
//        cell0.setCellStyle(style);
//        // 产生表格标题行
//        HSSFRow row = sheet.createRow(1);
//        for (int i=0;i<headList.size();i++) {
//            HSSFCell cell = row.createCell(i);
//            HSSFRichTextString text = new HSSFRichTextString(headList.get(i));
//            cell.setCellValue(text);
//
//        }
//        // 遍历集合数据，产生数据行
//        Iterator<T> it = dataset.iterator();
//        int index = 0;
//        while (it.hasNext()) {
//            index++;
//            row = sheet.createRow(index);
//            T t = (T) it.next();
//            Field[] fields = t.getClass().getDeclaredFields();
//            List<Field> fieldsList = new ArrayList<>();
//            for (Field field : fields) {
//                if(field.getAnnotations().length != 0) {
//                    fieldsList.add(field);
//                }
//            }
//            for (Field field:fieldsList) {
//                HSSFCell cell = row.createCell(fieldsList.indexOf(field));
//                String fieldName = field.getName();
//                String getMethodName = "get"
//                        + fieldName.substring(0, 1).toUpperCase()
//                        + fieldName.substring(1);
//                Class tCls = t.getClass();
//                Method getMethod = tCls.getMethod(getMethodName, new Class[]{});
//                Object value = getMethod.invoke(t, new Object[]{});
//                // 判断值的类型后进行强制类型转换
//                String textValue = null;
//                if (value == null) {
//                    cell.setCellValue("");
//                }
//                if (value instanceof Integer) {
//                    int intValue = (Integer) value;
//                    cell.setCellValue(intValue);
//                } else if (value instanceof Float) {
//                    float fValue = (Float) value;
//                    cell.setCellValue(fValue);
//                } else if (value instanceof Double) {
//                    double dValue = (Double) value;
//                    cell.setCellValue(dValue);
//                } else if (value instanceof Long) {
//                    long longValue = (Long) value;
//                    cell.setCellValue(longValue);
//                } else if (value instanceof Date) {
//                    Date date = (Date) value;
//                    SimpleDateFormat sdf = new SimpleDateFormat(pattern);
//                    textValue = sdf.format(date);
//                    cell.setCellValue(textValue);
//                } else {
//                    // 其它数据类型都当作字符串简单处理
//                    textValue = value==null? "":value.toString();
//                    cell.setCellValue(textValue);
//                }
//            }
//        }
//        String fileName="账户导出表.xls";
//        response.setContentType("application/octet-stream;charset=ISO8859-1");
//        response.setHeader("Content-Disposition", "attachment;filename=" + fileName);
//        response.addHeader("Pargam", "no-cache");
//        response.addHeader("Cache-Control", "no-cache");
//        workbook.write(out);
//        out.flush();
//        out.close();
//    }
//}
