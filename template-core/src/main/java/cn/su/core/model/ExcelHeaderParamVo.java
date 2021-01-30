package cn.su.core.model;

/**
 * Excel表头模型
 *
 * @AUTHOR: sr
 * @DATE: Create In 0:32 2021/1/18 0018
 **/
public class ExcelHeaderParamVo {
    /**
     * 起始行，从0开始计数，第一行是0
     */
    private int startRow;

    /**
     * 起始列，从0开始计数，第一列是0
     */
    private int startCol;

    /**
     * 跨行
     */
    private int rowSpan;

    /**
     * 跨列
     */
    private int colSpan;

    /**
     * 单元格长度
     */
    private int cellWidth;

    /**
     * 对应模型的字段值，如：页面显示“名字”，后端模型用name
     */
    private String fieldValue;

    /**
     * 单元格值，如：名字
     */
    private String cellValue;

    /**
     * 排序
     */
    private Integer order;

    public Integer getOrder() {
        return this.order;

    }

    public void setOrder(Integer order) {
        this.order = order;
    }

    public String getFieldValue() {
        return this.fieldValue;

    }

    public void setFieldValue(String fieldValue) {
        this.fieldValue = fieldValue;
    }

    public String getCellValue() {
        return this.cellValue;

    }

    public void setCellValue(String cellValue) {
        this.cellValue = cellValue;
    }

    public int getStartRow() {
        return this.startRow;

    }

    public void setStartRow(int startRow) {
        this.startRow = startRow;
    }

    public int getStartCol() {
        return this.startCol;

    }

    public void setStartCol(int startCol) {
        this.startCol = startCol;
    }

    public int getRowSpan() {
        return this.rowSpan;

    }

    public void setRowSpan(int rowSpan) {
        this.rowSpan = rowSpan;
    }

    public int getColSpan() {
        return this.colSpan;

    }

    public void setColSpan(int colSpan) {
        this.colSpan = colSpan;
    }

    public int getCellWidth() {
        return this.cellWidth;

    }

    public void setCellWidth(int cellWidth) {
        this.cellWidth = cellWidth;
    }
}
