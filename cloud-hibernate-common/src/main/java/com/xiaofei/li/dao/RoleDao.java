package com.xiaofei.li.dao;

import com.xiaofei.li.entity.Permission;
import com.xiaofei.li.entity.Role;
import com.xiaofei.li.entity.User;
import com.xiaofei.li.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

/**
 * Author: xiaofei
 * Date: 2022-04-17, 18:50
 * Description:
 */
@Repository
public class RoleDao {
    public Set<Role> getRolesByUserId(Integer userId){
        String hql_FetchRoleByUserId="from User as u left join fetch u.roles as r where u.id=:id";
        try (Session session= HibernateUtil.getSession()){
            Query<User> query = session.createQuery(hql_FetchRoleByUserId);
            query.setParameter("id",userId);
            return query.uniqueResult().getRoles();
        }
    }
    public Set<Permission> getPermissionsByRoleName(String roleName){
        Session session = HibernateUtil.getSession();
        String hql_getRoleByName = "from Role as r left join fetch r.permissions where r.roleName=:role_name";
                                    //from Role r right join fetch User as u where r.roleName=:role_name 
        Transaction transaction = session.beginTransaction();
        try{
            Query<Role> query = session.createQuery(hql_getRoleByName);
            query.setParameter("role_name",roleName);
            transaction.commit();
            return query.uniqueResult().getPermissions();
        }catch (Exception e){
            e.printStackTrace();
            transaction.rollback();
        }finally {
            HibernateUtil.close();
        }
        return null;
    }

    public Role getRoleByRoleName(String roleName) {
        String hql_getRoleByRoleName="from Role r left join fetch r.users where r.roleName=:role_name";
        try (Session session = HibernateUtil.getSession()){
            Query<Role> query = session.createQuery(hql_getRoleByRoleName);
            query.setParameter("role_name",roleName);
            return query.uniqueResult();
        }
    }
}
