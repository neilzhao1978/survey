package com.neil.survey.util;


import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import com.google.gson.JsonObject;

public class PageEntity {
	/**
	 * 页号从1开始，不能为null
	 */
	@NotNull
	@Min(value = 1)
	private Integer pageNumber;
	/**
	 * 分页，页大小，，不能为null，最小值取1
	 */
	@NotNull
	@Min(value = 1)
	private Integer pageSize;
	/**
	 * @备注： 排序字段
	 */
	private String orderByFieldName;
	/**
	 * @备注： 是否递减排序，true表示递减排序，false表示递增排序
	 */
	private boolean isDesc;
	/**
	 * @备注： 查询条件参数
	 */
	private JsonObject parameters;

	public PageEntity() {

	}

	public JsonObject getParameters() {
		return parameters;
	}

	public void setParameters(JsonObject parameters) {
		this.parameters = parameters;
	}

	public String getOrderByFieldName() {
		return orderByFieldName;
	}

	public void setOrderByFieldName(String orderByFieldName) {
		this.orderByFieldName = orderByFieldName;
	}

	public boolean isDesc() {
		return isDesc;
	}

	public void setDesc(boolean isDesc) {
		this.isDesc = isDesc;
	}

	public Integer getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(Integer pageNumber) {
		this.pageNumber = pageNumber;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		StringBuilder sb = new StringBuilder();
		sb.append(pageSize).append(pageNumber).append(orderByFieldName).append(isDesc).append(parameters);
		return sb.toString().hashCode();
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof PageEntity)) {
			return false;
		}
		PageEntity page = (PageEntity) obj;
		// 分页参数对比
		if (this.pageNumber.equals(page.pageNumber) && this.pageSize.equals(page.pageSize)) {
			// 排序参数对比
			if (this.isDesc == page.isDesc) {
				// 排序参数对比
				if ((this.orderByFieldName == null && page.orderByFieldName == null)
						|| (this.orderByFieldName != null && this.orderByFieldName.equals(page.orderByFieldName))) {
					// 查询条件对比
					if ((this.parameters == null && page.parameters == null) || (this.parameters != null
							&& page.parameters != null && this.parameters.equals(page.parameters))) {
							return true;
					}
				}
			}
		}
		return false;
	}
}
