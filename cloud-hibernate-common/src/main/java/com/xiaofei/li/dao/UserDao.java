package com.xiaofei.li.dao;

import com.xiaofei.li.entity.*;
import com.xiaofei.li.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

/**
 * Author: xiaofei
 * Date: 2022-04-17, 15:08
 * Description:
 */
@Repository
public class UserDao {

    public void saveUser(User user){
        Session session = HibernateUtil.getSession();
        Transaction transaction = session.beginTransaction();
        try{
            session.saveOrUpdate(user);
            transaction.commit();
        }catch (Exception e){
            e.printStackTrace();
            transaction.rollback();
        }finally {
            HibernateUtil.close();
        }
    }

    public User getUserById(Integer id){
        try(Session session = HibernateUtil.getSession()){
            return session.get(User.class, id);
        }
    }

    public List<User> getAllUsers() {
        String hql_getAllUsers = "from User";
        try (Session session = HibernateUtil.getSession()) {
            Query<User> query = session.createQuery(hql_getAllUsers);
            return query.getResultList();
        }
    }

    public void deleteUserById(Integer id) {
        Session session = HibernateUtil.getSession();
        Transaction transaction = session.beginTransaction();
        try{
            User user = getUserById(id);
            session.delete(user);
            transaction.commit();
        }catch (Exception e){
            e.printStackTrace();
            transaction.rollback();
        }finally {
            HibernateUtil.close();
        }
    }
}
