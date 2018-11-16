package com.potato.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * 封装ajax请求的返回类型
 * @param <T>
 */
@Getter
@Setter
public class SeckillResult<T> {
	private boolean success;

	private T data;

	private String error;

	public SeckillResult(boolean success, String error) {
		this.success = success;
		this.error = error;
	}

	public SeckillResult(boolean success, T data) {
		this.success = success;
		this.data = data;
	}
}
