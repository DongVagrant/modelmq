package com.wd.mqmodel.common.pojo;

import com.github.pagehelper.PageInfo;

import java.io.Serializable;
import java.util.List;
/**
 * 分页包装类
 *
 * @author zzb
 * @version 1.1.0 2017/06/20
 */
public class ResultPagedData<T> implements Serializable{
	private static final long serialVersionUID = 5850283949283025317L;
	/**
	 * 数据集  详见{@link List},{@link T}
	 */
	private List<T> datas;
	/**
	 * 总个数  详见{@link Integer}
	 */
	private Integer totalCount = 0;
	/**
	 * 总页数  详见{@link Integer}
	 */
	private Integer totalPage = 0;
	/**
	 * 页码  详见{@link Integer}
	 */
	private Integer pageIndex = 1;
	/**
	 * 页大小
	 */
	private Integer pageSize = 200;

	public ResultPagedData() {
		super();
	}
	/**
	 * ResultPagedData对象构造函数
	 *
	 * @param datas  详见{@link List},{@link T}
	 */
	public ResultPagedData(List<T> datas) {
		PageInfo<T> page = new PageInfo<>(datas);
		this.totalCount = Long.valueOf(page.getTotal()).intValue();
		this.pageIndex = page.getPageNum();
		this.datas = datas;
		this.pageSize = page.getPageSize();
		this.totalPage = page.getPages();
	}

	public List<T> getDatas() {
		return this.datas;
	}

	public void setDatas(List<T> datas) {
		this.datas = datas;
	}

	public Integer getTotalCount() {
		return this.totalCount;
	}

	public void setTotalCount(Integer totalCount) {
		this.totalCount = totalCount;
	}

	public Integer getTotalPage() {
		return this.totalPage;
	}

	public void setTotalPage(Integer totalPage) {
		this.totalPage = totalPage;
	}

	public Integer getPageIndex() {
		return this.pageIndex;
	}

	public void setPageIndex(Integer pageIndex) {
		this.pageIndex = pageIndex;
	}

	public Integer getPageSize() {
		return this.pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

}
