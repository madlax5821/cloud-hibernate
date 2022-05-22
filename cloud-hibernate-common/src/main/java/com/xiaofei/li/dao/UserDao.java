package com.xiaofei.li.dao;

import com.xiaofei.li.entity.*;
import com.xiaofei.li.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
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
    @Autowired
    private RoleDao roleDao;

    public void saveUser(User user){
        Session session = HibernateUtil.getSession();
        Transaction transaction = session.beginTransaction();
        try{
            Role role = roleDao.getRoleByRoleName("ROLE_USER");
            user.addRole(role);
            session.save(user);
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

    public User getUserByUsername(String username) {
        String hql_getUserByUsername = "from User as u where u.fullName.firstName=:first_name";
        try(Session session = HibernateUtil.getSession()){
            Query<User> query = session.createQuery(hql_getUserByUsername);
            query.setParameter("first_name",username);
            return query.getSingleResult();
        }
    }
    
    public void addRoleByRoleName(Integer userId, String roleName){
        Session session = HibernateUtil.getSession();
        Transaction transaction = session.beginTransaction();
        try{
            User user = session.get(User.class, userId);
            Role role = roleDao.getRoleByRoleName(roleName);
            user.addRole(role);
            saveUser(user);
            transaction.commit();
        }catch (Exception e){
            e.printStackTrace();
            transaction.rollback();
        }finally {
            HibernateUtil.close();
        }
    }

    public void deleteRoleByRoleId(Integer userId, Integer roleId){
        Session session = HibernateUtil.getSession();
        Transaction transaction = session.beginTransaction();
        try{
            User user = session.get(User.class, userId);
            Role role = session.get(Role.class, roleId);
            user.removeRole(role);
            session.saveOrUpdate(user);
            transaction.commit();
        }catch (Exception e){
            e.printStackTrace();
            transaction.rollback();
        }finally {
            HibernateUtil.close();
        }
    }
    
    public User getUserByEmail(String email){
        String hql_getUserByEmail="from User u where u.email=:email";
        try (Session session = HibernateUtil.getSession()){
            Query<User> query = session.createQuery(hql_getUserByEmail);
            query.setParameter("email",email);
            return query.uniqueResult();
        }
    }

    public void updateUser(User user) {
        String hql_updateByDynamicAttributes="update User u set u.fullName.firstName=:first_name, u.fullName.lastName=:last_name" +
                ", u.age=:age where u.id=:id";
        try (Session session = HibernateUtil.getSession()){
            session.beginTransaction();
            Query<User> query = session.createQuery(hql_updateByDynamicAttributes);
            String firstName = null;
            String lastName = null;
            if (user.getFullName()!=null){
                firstName=user.getFullName().getFirstName();
                lastName=user.getFullName().getLastName();
            }
            query.setParameter("first_name",firstName);
            query.setParameter("last_name",lastName);
            query.setParameter("age",user.getAge());
            query.setParameter("id",user.getId());
            
            query.executeUpdate();
            session.getTransaction().commit();
        }
    }
}
