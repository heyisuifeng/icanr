package com.kmak.utils.zhifubao;

import lombok.Data;

@Data
public class RespMsgJson implements java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6324746883787093927L;
	private int status;		//状态
	private String desc;	//描述
	private Object body;	//对象
}
