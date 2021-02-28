package cn.su.dao.model.common;

import java.util.List;

/**
 * @Author: su rui
 * @Date: 2021/2/8 10:24
 * @Description: 分页
 */
public class PageVo<T> extends BaseVo {
    private static final long serialVersionUID = -1104141509250315996L;
    private Integer page;
    private Integer pageIndex;
    private Integer pageSize;
    private Integer totalPage;
    private Integer currentPage;
    private List<T> list;

    public Integer getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(Integer pageIndex) {
        this.pageIndex = pageIndex;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(Integer totalPage) {
        this.totalPage = totalPage;
    }

    public Integer getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }
}
