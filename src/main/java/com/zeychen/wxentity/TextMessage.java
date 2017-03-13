package com.zeychen.wxentity;

import com.zeychen.wxutil.ClassMapping;
import com.zeychen.wxutil.FieldMapping;

@ClassMapping(dbName = "ZY_DB", tableName = "MESSAGE")
public class TextMessage {

	@FieldMapping(columnName = "toUserName")
	private String toUserName;
	
	@FieldMapping(columnName = "fromUserName")
	private String fromUserName;
	
	@FieldMapping(columnName = "createTime")
	private String createTime;
	
	@FieldMapping(columnName = "msgType")
	private String msgType;
	
	@FieldMapping(columnName = "content")
	private String content;
	
	@FieldMapping(columnName = "msgId")
	private String msgId;

	public String getToUserName() {
		return toUserName;
	}

	public void setToUserName(String toUserName) {
		this.toUserName = toUserName;
	}

	public String getFromUserName() {
		return fromUserName;
	}

	public void setFromUserName(String fromUserName) {
		this.fromUserName = fromUserName;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getMsgType() {
		return msgType;
	}

	public void setMsgType(String msgType) {
		this.msgType = msgType;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getMsgId() {
		return msgId;
	}

	public void setMsgId(String msgId) {
		this.msgId = msgId;
	}

}
