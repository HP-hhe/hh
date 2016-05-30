package dao;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Service;

import entity.Admin;
import entity.Department;

import util.HibernateUtil;

@Service //dao层组件，持久层注入
public class AdminDao {
	@Resource private HibernateUtil util;
	//添加管理员
	public void add(Admin admin, Integer did){
		admin.setDepartment((Department)util.get(Department.class, did));
		util.add(admin);
	}
	//删除
	public void delete(Integer id){
		util.delete(util.get(Admin.class, id));
	}
	
	//通过名字来查找
	@SuppressWarnings("unchecked")
	public List searchByName(String str1, String str2){
		String hql = "from Admin a where a.name like ? and a.department=?";
		if(str2 == null || str2.equals("") || str2.equals("0"))
			hql = "from Admin a where a.name like ?";
		return this.search(hql, str1, str2);
	}
	//通过邮箱来查找
	@SuppressWarnings("unchecked")
	public List searchByEmail(String str1, String str2){
		String hql = "from Admin a where a.email like ? and a.department=?";
		if(str2 == null || str2.equals("") || str2.equals("0"))
			hql = "from Admin a where a.email like ?";
		return this.search(hql, str1, str2);
	}
	//查找出来进行展示
	@SuppressWarnings("unchecked")
	public List search(String hql, String str1, String str2){
		Session session = util.getSession();
		Query query = session.createQuery(hql);
		query.setString(0, "%"+str1+"%");
		if(hql.split("[?]").length > 1)
			query.setInteger(1, Integer.valueOf(str2));
		return query.list();
	}
}
