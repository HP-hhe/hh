package action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.ServletResponse;

import net.sf.json.JSONArray;

import org.apache.struts2.ServletActionContext;
import org.springframework.stereotype.Controller;


import util.HibernateUtil;

@Controller
public class GetDepartment {
	@Resource private HibernateUtil util;
	//获取部门信息
	@SuppressWarnings("unchecked")
	public String get() throws IOException{
		ServletResponse response = ServletActionContext.getResponse();
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		List list = util.getQuery("from Department").list();
		JSONArray json = JSONArray.fromObject(list);
		out.print(json.toString());
		return null;
	}
}
