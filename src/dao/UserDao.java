package dao;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Service;

import entity.Department;
import entity.User;

import util.HibernateUtil;

@Service
public class UserDao {
	@Resource private HibernateUtil util;
	//添加用户
	public void add(User user, Integer did){
		user.setDepartment((Department)util.get(Department.class, did));
		util.add(user);
	}
	//删除用户
	public void delete(Integer id){
		util.delete(util.get(User.class, id));
	}
	//更新用户
	public void update(User user, Integer did){
		user.setDepartment((Department)util.get(Department.class, did));
		util.update(user);
	}
	//通过名字查找
	@SuppressWarnings("unchecked")
	public List searchByName(String str1, String str2){
		String hql = "from User u where u.name like ? and u.department=?";
		if(str2 == null || str2.equals("") || str2.equals("0"))
			hql = "from User u where u.name like ?";
		return this.search(hql, str1, str2);
	}
	//通过邮箱查找
	@SuppressWarnings("unchecked")
	public List searchByEmail(String str1, String str2){
		String hql = "from User u where u.email like ? and u.department=?";
		if(str2 == null || str2.equals("") || str2.equals("0"))
			hql = "from User u where u.email like ?";
		return this.search(hql, str1, str2);
	}
	//全部查找，没有时返回的是一个空的list
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
