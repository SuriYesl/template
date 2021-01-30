package cn.su.core.util;

import cn.su.core.model.KeyValueVo;
import org.apache.poi.ss.usermodel.*;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * @AUTHOR: sr
 * @DATE: Create In 0:32 2021/1/18 0018
 */
public class ImportExcelUtil {
    private static void setMappingOfCellAndModel(int colIndex, String fieldValue, Map<Integer, String> mappingResult) {
        mappingResult.put(colIndex, fieldValue);
    }

    private static String getModelField(String tableName, List<KeyValueVo> tableHeaders) {
        for (KeyValueVo tableHeader : tableHeaders) {
            if (tableHeader.getValue().equals(tableName)) {
                return tableHeader.getKey();
            }
        }

        return "";
    }

    private static <T> void setDataByRow(Sheet oneSheet, int rowIndex, List<KeyValueVo> tableHeaders,
                                         Map<Integer, String> colAndField, Class<?> clazz, List<T> resultVO) {
        T modelVO = null;

        try {
            modelVO = (T) clazz.newInstance();
        } catch (Exception e2) {
            System.out.println("创建泛型实例失败" + e2.toString());
        }

        Method setMethod = null;
        Row oneRow = oneSheet.getRow(rowIndex);
        int totalColNumber = oneRow.getPhysicalNumberOfCells();

        for (int colIndex = 0; colIndex < totalColNumber; colIndex++) {
            Cell oneCell = oneRow.getCell(colIndex);
            if (null == oneCell) {
                continue;
            }

            oneCell.setCellType(CellType.STRING);
            if (rowIndex == 0) {
                String fieldValue = getModelField(oneCell.getStringCellValue(), tableHeaders);

                setMappingOfCellAndModel(colIndex, fieldValue, colAndField);
            } else {
                if (NormHandleUtil.isEmpty(colAndField.get(colIndex))) {
                    continue;
                }

                try {
                    setMethod = clazz.getMethod("set" + StringUtil.getInitialsUp(colAndField.get(colIndex)), String.class);
                } catch (Exception e1) {
                    System.out.println("获取set方法出错" + e1.toString());
                }

                try {
                    setMethod.invoke(modelVO, oneCell.getStringCellValue());
                } catch (Exception e) {
                    System.out.println("设置模型值出错" + e.toString());
                }
            }

        }
        if (rowIndex != 0) {
            resultVO.add(modelVO);
        }
    }

    private static <T> void setDataBySheet(Workbook excel, int sheetIndex, List<KeyValueVo> tableHeaders,
                                           Map<Integer, String> colAndField, Class<?> clazz, Integer startRow,
                                           List<T> resultVO) {
        Sheet oneSheet = excel.getSheetAt(sheetIndex);
        int totalRowNumber = oneSheet.getPhysicalNumberOfRows();

        for (int rowIndex = startRow; rowIndex < totalRowNumber; rowIndex++) {
            setDataByRow(oneSheet, rowIndex, tableHeaders, colAndField, clazz, resultVO);
        }

    }

    public static <T> List<T> getModels(Class<?> clazz, Integer startRow, Workbook excel, List<KeyValueVo> tableHeaders) {
        if (NormHandleUtil.isEmpty(tableHeaders)) {
            return null;
        }

        int sheetsNumber = excel.getNumberOfSheets();

        Map<Integer, String> colAndField = new HashMap<>();
        List<T> resultVO = new LinkedList<>();

        for (int sheetIndex = 0; sheetIndex < sheetsNumber; sheetIndex++) {
            setDataBySheet(excel, sheetIndex, tableHeaders, colAndField, clazz, startRow, resultVO);
        }

        return resultVO;
    }
}
