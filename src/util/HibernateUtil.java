package util;

import java.io.Serializable;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
//持久层注入，所有方法都需要事务管理
@Service @Transactional
public class HibernateUtil {
	//注入，通过反射机制自动注入
	@Resource private SessionFactory sessionFactory;
	//拿到一个session
	public Session getSession(){
		return sessionFactory.openSession();
	}
	//通过当前session来保存一个对象
	public void add(Object obj){
		sessionFactory.getCurrentSession().save(obj);
	}
	//通过当前session来更新一个对象
	public void update(Object obj){
		sessionFactory.getCurrentSession().update(obj);
	}
	//通过当前session来删除多个对象，这里采用的是不限长的参数
	public void delete(Object... objs){
		for(Object obj : objs)
			sessionFactory.getCurrentSession().delete(obj);
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(propagation=Propagation.NOT_SUPPORTED)
	public Object get(Class clazz, Serializable id){
		return sessionFactory.openSession().get(clazz, id);
	}
	// 打开一个新的session来创建一个查询的对象
	public Query getQuery(String hql){
		return sessionFactory.openSession().createQuery(hql);
	}
}
