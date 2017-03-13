package com.zeychen.wxservlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.dom4j.Document;
import org.dom4j.DocumentException;

import com.chenzeyi.util.xml.XmlUtil;
import com.zeychen.wxutil.WeiChatUtil;

public class WeichatServlet extends HttpServlet {

	private static final long serialVersionUID = -3638496863788809862L;

	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("text/html;charset=utf-8");
		PrintWriter pt = resp.getWriter();
		String echostr = req.getParameter("echostr");
		if(WeiChatUtil.validateWeichatCode(req)){
			pt.write(echostr);
		}
		pt.flush();
		pt.close();
	}

	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		resp.setCharacterEncoding("UTF-8");   
		XmlUtil xu = new XmlUtil();
		PrintWriter pt = resp.getWriter();
		try {
			Document doc = xu.LoadXmlFromInputstream(req.getInputStream());
			Map<String,Object> params = xu.getElements(doc.getRootElement());
			String toUserName=(String) params.get("ToUserName");
			String fromUserName=(String) params.get("FromUserName");
			String createTime=(String) params.get("CreateTime");
			String msgType=(String) params.get("MsgType");
			String content=(String) params.get("Content");
			String msgId = (String) params.get("MsgId");
			Map<String,Object> returnMsg = new HashMap<String, Object>();
			if("text".equals(msgType)){
				returnMsg.put("ToUserName", fromUserName);
				returnMsg.put("FromUserName", toUserName);
				returnMsg.put("CreateTime", new Date().getTime());
				returnMsg.put("MsgType", "text");
				returnMsg.put("Content", "getMsg = "+content);
				returnMsg.put("MsgId", msgId);
			}
			
			pt.write(xu.getXmlDocStr(returnMsg, "xml"));
			
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally{
			pt.flush();
			pt.close();
		}
		super.doPost(req, resp);
	}
	
}
