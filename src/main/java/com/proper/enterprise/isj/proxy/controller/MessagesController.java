package com.proper.enterprise.isj.proxy.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.proper.enterprise.isj.proxy.model.Messages;
import com.proper.enterprise.platform.core.controller.BaseController;
import com.proper.enterprise.platform.core.utils.JSONUtil;

/**
 * Created by think on 2016/8/15 0015. 取得登录用户的消息列表
 */
@RestController
@RequestMapping(path = "/messages")
public class MessagesController extends BaseController {

	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<Messages>> getMessages() {
		List<Messages> msgList = new ArrayList<Messages>();
		String msg = "[" + "  {" + "    'id': '2'," + "    'date': '2016年08月05日 10:00',"
				+ "    'content': '30分钟之内未支付，由系统自动取消。30分钟之内未支付，由系统自动取消。'" + "  }," + "  {"
				+ "    'id': '1'," + "    'date': '2016年08月03日 09:10',"
				+ "    'content': '30分钟之内未支付，由系统自动取消。'" + "  }" + "]";
		try {
			msgList = JSONUtil.parse(msg, List.class);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return responseOfGet(msgList);
	}
}
