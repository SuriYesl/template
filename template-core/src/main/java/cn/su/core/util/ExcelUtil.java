package cn.su.core.util;

import cn.su.core.model.ExcelHeaderParamVo;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * excel工具类
 *
 * @AUTHOR: sr
 * @DATE: Create In 0:32 2021/1/18 0018
 **/
public class ExcelUtil {
    /**
     * 获取excel文件名
     *
     * @param excelName 文件名
     * @return 文件名
     */
    private static String getExcelFileName(String excelName, String encoding) {
        if (NormHandleUtil.isEmpty(excelName)) {
            excelName = "Excel";
        }

        try {
            if (NormHandleUtil.isEmpty(encoding)) {
                excelName = new String(excelName.getBytes("UTF-8"),"ISO-8859-1");
            } else {
                excelName = new String(excelName.getBytes("UTF-8"),encoding);
            }
        } catch (Exception e) {
            excelName = excelName + "-1";
        }

        return excelName;
    }

    /**
     * 根据文件类型返回一个空的文件
     *
     * @param fileType 文件类型
     * @return 空文件
     */
    private static Workbook createEmptyWorkbookByType(String fileType) {
        if (NormHandleUtil.isEmpty(fileType)) {
            return new SXSSFWorkbook();
        } else if ("xls".equals(fileType)) {
            return new HSSFWorkbook();
        } else {
            return new SXSSFWorkbook();
        }
    }

    /**
     * 写入Excel表头
     *
     * @param workbook 文件
     * @param excelHeaderLists 表头
     */
    private static void writeSimpleExcelHeader(Workbook workbook, List<String> excelHeaderLists) {
        Sheet sheet = workbook.createSheet("sheet1");
        Row row = sheet.createRow(0);
        Cell cell = null;
        Integer celSubScript = 0;

        if (NormHandleUtil.isEmpty(excelHeaderLists)) {
            return;
        }

        for (String excelHeader : excelHeaderLists) {
            cell = row.createCell(celSubScript);
            cell.setCellValue(excelHeader);
            celSubScript++;
        }
    }

    /**
     * 获取字段对应的值
     *
     * @param field 字段
     * @param oneData 数据
     * @param clazz 类对象
     * @param <T> 泛型
     * @return 值
     */
    private static <T> String getFiledValue(Field field, T oneData, Class<?> clazz) {
        Method getMethod = null;
        try {
            getMethod = clazz.getMethod("get" + StringUtil.getInitialsUp(field.getName()));
        } catch (Exception e) {
            try {
                getMethod = clazz.getMethod("get" + field.getName());
            } catch (Exception e2) {
                return "no such get method";
            }
        }

        try {
            if (null == getMethod.invoke(oneData)) {
                return "";
            } else {
                return getMethod.invoke(oneData).toString();
            }
        } catch (Exception e1) {
            return "some problems with getting values";
        }
    }

    /**
     * 获取字段名字与字段的映射
     *
     * @param fields 字段
     * @return 映射关系
     */
    private static Map<String, Field> getFieldMaps(Field[] fields) {
        if (null == fields || fields.length == 0) {
            return new HashMap<>();
        }
        Map<String, Field> nameAndFieldMaps = new HashMap<>();
        for (Field field : fields) {
            nameAndFieldMaps.put(field.getName(), field);
        }

        return nameAndFieldMaps;
    }

    /**
     * 简单写入Excel数据
     *
     * @param workbook 文件
     * @param fieldOrders 字段顺序
     * @param data 数据
     * @param <T> 泛型
     */
    private static <T> void writeSimpleExcelData(Workbook workbook, List<String> fieldOrders, List<T> data) {
        if (NormHandleUtil.isEmpty(data)) {
            return;
        }
        Class<?> clazz = data.get(0).getClass();
        Sheet sheet = workbook.getSheet("sheet1");
        Row row = null;
        Cell cell = null;
        Integer rowSubScript = 1;
        Integer celSubScript = 0;

        Field[] fields = clazz.getDeclaredFields();
        Map<String, Field> nameAndFieldMaps = getFieldMaps(fields);
        if (nameAndFieldMaps.size() == 0) {
            return;
        }

        for (T oneData : data) {
            row = sheet.createRow(rowSubScript);
            if (NormHandleUtil.isEmpty(fieldOrders)) {
                for (Field field : fields) {
                    if (Modifier.isStatic(field.getModifiers())) {
                        continue;
                    }

                    cell = row.createCell(celSubScript);
                    cell.setCellValue(getFiledValue(field, oneData, clazz));
                    celSubScript++;
                }
            } else {
                for (String fieldOrder : fieldOrders) {
                    if (Modifier.isStatic(nameAndFieldMaps.get(fieldOrder).getModifiers())) {
                        continue;
                    }

                    cell = row.createCell(celSubScript);
                    cell.setCellValue(getFiledValue(nameAndFieldMaps.get(fieldOrder), oneData, clazz));
                    celSubScript++;
                }
            }
            rowSubScript++;
            celSubScript = 0;
        }
    }

    /**
     * 获取一个简单表头的Excel文件
     *
     * @param excelHeaderLists 表头
     * @param data 数据
     * @param <T> 泛型
     * @return 文件
     */
    public static <T> Workbook getSimpleExcelWorkBook(List<String> excelHeaderLists,
                                                      List<T> data) {
        return getSimpleExcelWorkBook(null, null, excelHeaderLists, data);
    }

    /**
     * 获取一个简单表头的Excel文件
     *
     * @param fileType 文件类型
     * @param excelHeaderLists 表头
     * @param data 数据
     * @param <T> 泛型
     * @return 文件
     */
    public static <T> Workbook getSimpleExcelWorkBook(String fileType,
                                                      List<String> excelHeaderLists,
                                                      List<T> data) {
        return getSimpleExcelWorkBook(fileType, null, excelHeaderLists, data);
    }

    /**
     * 获取一个简单表头的Excel文件
     *
     * @param fileType 文件类型
     * @param fieldOrders 模型字段顺序
     * @param excelHeaderLists 表头
     * @param data 数据
     * @param <T> 泛型
     * @return 文件
     */
    public static <T> Workbook getSimpleExcelWorkBook(String fileType,
                                                      List<String> fieldOrders,
                                                      List<String> excelHeaderLists,
                                                      List<T> data) {
        Workbook workbook = createEmptyWorkbookByType(fileType);
        writeSimpleExcelHeader(workbook, excelHeaderLists);
        writeSimpleExcelData(workbook, fieldOrders, data);
        return workbook;
    }

    /**
     * 获取导出列的顺序模型字段
     *
     * @param fieldOrder 结果
     * @param excelHeaderParamVos 参数
     * @return 字段
     */
    private static List<String> getFieldOrder(List<String> fieldOrder, List<ExcelHeaderParamVo> excelHeaderParamVos) {
        if (NormHandleUtil.isEmpty(fieldOrder)) {
            return fieldOrder;
        }

        Integer mostDeep = 0;
        Integer thisRowDeep = 0;
        for (ExcelHeaderParamVo excelHeaderParamVo : excelHeaderParamVos) {
            thisRowDeep = excelHeaderParamVo.getStartRow() + excelHeaderParamVo.getRowSpan() - 1;
            if (mostDeep < thisRowDeep) {
                mostDeep = thisRowDeep;
            }
        }

        List<String> fieldOrders = new LinkedList<>();
        List<ExcelHeaderParamVo> excelHeaderOrders =  new LinkedList<>();
        for (ExcelHeaderParamVo excelHeaderParamVo : excelHeaderParamVos) {
            thisRowDeep = excelHeaderParamVo.getStartRow() + excelHeaderParamVo.getRowSpan() - 1;
            if (mostDeep.equals(thisRowDeep)) {
                fieldOrders.add(excelHeaderParamVo.getFieldValue());
                excelHeaderOrders.add(excelHeaderParamVo);
            }
        }

        excelHeaderOrders = excelHeaderOrders.parallelStream()
                .sorted((order1,order2) -> order1.getOrder().compareTo(order2.getOrder()))
                .collect(Collectors.toList());

        for (ExcelHeaderParamVo excelHeaderOrder : excelHeaderOrders) {
            fieldOrders.add(excelHeaderOrder.getFieldValue());
        }
        return fieldOrders;
    }

    /**
     * 写入自定义表头
     *
     * @param workbook 文件
     * @param excelHeaderParamVos 表头数据
     */
    private static void writeSelfExcelHeader(Workbook workbook,
                                      List<ExcelHeaderParamVo> excelHeaderParamVos) {
        if (NormHandleUtil.isEmpty(excelHeaderParamVos)) {
            return;
        }

        Sheet sheet = workbook.createSheet("sheet1");
        Row row = null;
        Cell cell = null;
        Integer rowSubScript = 0;
        Integer celSubScript = 0;

        for (ExcelHeaderParamVo excelHeaderParamVo : excelHeaderParamVos) {
            row = sheet.createRow(excelHeaderParamVo.getStartRow());
            cell = row.createCell(excelHeaderParamVo.getStartCol());
            cell.setCellValue(excelHeaderParamVo.getCellValue());
        }

        for (ExcelHeaderParamVo excelHeaderParamVo : excelHeaderParamVos) {
            //起始行,结束行,起始列,结束列
            CellRangeAddress callRangeAddress = new CellRangeAddress(excelHeaderParamVo.getStartRow(),
                    excelHeaderParamVo.getStartRow() + excelHeaderParamVo.getRowSpan(),
                    excelHeaderParamVo.getStartCol(),
                    excelHeaderParamVo.getStartCol() + excelHeaderParamVo.getColSpan());
            sheet.addMergedRegion(callRangeAddress);
        }
    }

    /**
     * 获取数据起始行
     *
     * @param excelHeaderParamVos 表头
     * @return 起始行
     */
    private static Integer getSelfExcelDataRowStart(List<ExcelHeaderParamVo> excelHeaderParamVos) {
        Integer maxRow = 0;
        Integer thisRowDeep = 0;
        for (ExcelHeaderParamVo excelHeaderParamVo : excelHeaderParamVos) {
            thisRowDeep = excelHeaderParamVo.getStartRow() + excelHeaderParamVo.getRowSpan() - 1;
            if (maxRow < thisRowDeep) {
                maxRow = thisRowDeep;
            }
        }

        return maxRow + 1;
    }

    /**
     * 写入自定义表头excel的数据
     *
     * @param rowStart 开始行
     * @param workbook excel
     * @param fieldOrders 字段顺序
     * @param data 数据
     * @param <T> 泛型
     */
    private static <T> void writeSelfExcelData(Integer rowStart, Workbook workbook,
                                               List<String> fieldOrders, List<T> data) {
        if (NormHandleUtil.isEmpty(data)) {
            return;
        }
        Class<?> clazz = data.get(0).getClass();
        Sheet sheet = workbook.getSheet("sheet1");
        Row row = null;
        Cell cell = null;
        Integer rowSubScript = rowStart;
        Integer celSubScript = 0;

        Field[] fields = clazz.getDeclaredFields();
        Map<String, Field> nameAndFieldMaps = getFieldMaps(fields);
        if (nameAndFieldMaps.size() == 0) {
            return;
        }

        for (T oneData : data) {
            row = sheet.createRow(rowSubScript);
            if (NormHandleUtil.isEmpty(fieldOrders)) {
                for (Field field : fields) {
                    if (Modifier.isStatic(field.getModifiers())) {
                        continue;
                    }

                    cell = row.createCell(celSubScript);
                    cell.setCellValue(getFiledValue(field, oneData, clazz));
                    celSubScript++;
                }
            } else {
                for (String fieldOrder : fieldOrders) {
                    if (Modifier.isStatic(nameAndFieldMaps.get(fieldOrder).getModifiers())) {
                        continue;
                    }

                    cell = row.createCell(celSubScript);
                    cell.setCellValue(getFiledValue(nameAndFieldMaps.get(fieldOrder), oneData, clazz));
                    celSubScript++;
                }
            }
            rowSubScript++;
            celSubScript = 0;
        }
    }

    /**
     * 获取一个简单表头的Excel文件
     *
     * @param excelHeaderParamVos 表头
     * @param data 数据
     * @param <T> 泛型
     * @return 文件
     */
    public static <T> Workbook getSelfExcelWorkBook(List<ExcelHeaderParamVo> excelHeaderParamVos,
                                                    List<T> data) {
        return getSelfExcelWorkBook(null, null, excelHeaderParamVos, data);
    }

    /**
     * 获取一个简单表头的Excel文件
     *
     * @param fieldOrders 字段顺序
     * @param excelHeaderParamVos 表头
     * @param data 数据
     * @param <T> 泛型
     * @return 文件
     */
    public static <T> Workbook getSelfExcelWorkBook(List<String> fieldOrders,
                                                    List<ExcelHeaderParamVo> excelHeaderParamVos,
                                                    List<T> data) {
        return getSelfExcelWorkBook(null, fieldOrders, excelHeaderParamVos, data);
    }

    /**
     * 获取一个简单表头的Excel文件
     *
     * @param fileType 类型
     * @param excelHeaderParamVos 表头
     * @param data 数据
     * @param <T> 泛型
     * @return 文件
     */
    public static <T> Workbook getSelfExcelWorkBook(String fileType,
                                                    List<ExcelHeaderParamVo> excelHeaderParamVos,
                                                    List<T> data) {
        return getSelfExcelWorkBook(fileType, null, excelHeaderParamVos, data);
    }

    /**
     * 获取一个简单表头的Excel文件
     *
     * @param fileType 文件类型
     * @param fieldOrders 字段顺序
     * @param excelHeaderParamVos 表头
     * @param data 数据
     * @param <T> 泛型
     * @return 文件
     */
    public static <T> Workbook getSelfExcelWorkBook(String fileType,
                                                    List<String> fieldOrders,
                                                    List<ExcelHeaderParamVo> excelHeaderParamVos,
                                                    List<T> data) {
        Workbook workbook = createEmptyWorkbookByType(fileType);
        writeSelfExcelHeader(workbook, excelHeaderParamVos);

        fieldOrders = getFieldOrder(fieldOrders, excelHeaderParamVos);
        Integer dataStartRow = getSelfExcelDataRowStart(excelHeaderParamVos);

        writeSelfExcelData(dataStartRow, workbook, fieldOrders, data);
        return workbook;
    }
}
