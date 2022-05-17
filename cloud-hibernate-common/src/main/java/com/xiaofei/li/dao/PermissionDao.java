package com.xiaofei.li.dao;

import com.xiaofei.li.entity.Permission;
import com.xiaofei.li.entity.Role;
import com.xiaofei.li.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

/**
 * Author: xiaofei
 * Date: 2022-05-14, 21:26
 * Description:
 */
@Repository
public class PermissionDao {
    public List<Permission> getAllPermissions(){
        String hql_getAllPermissions = "from Permission p";
        try(Session session = HibernateUtil.getSession()){
            Query<Permission> query = session.createQuery(hql_getAllPermissions);
            return query.getResultList();
        }
    }

    public Set<Role> getRolesByPermissionUrl(String permissionUrl){
        String hql_getRolesByPermissionUrl="from Permission p right join fetch p.roles where p.permissionUrl=:permission_url";
        try (Session session = HibernateUtil.getSession()){
            Query<Permission> query = session.createQuery(hql_getRolesByPermissionUrl);
            query.setParameter("permission_url",permissionUrl);
            return query.uniqueResult().getRoles();
        }
    }

    public Set<Role> getRolesByPermissionId(Integer id) {
        String hql_getRolesByPermissionId="from Permission p left join fetch p.roles where p.id=:id";
        try (Session session = HibernateUtil.getSession()){
            Query<Permission> query = session.createQuery(hql_getRolesByPermissionId);
            query.setParameter("id",id);
            return query.uniqueResult().getRoles();
        }
    }
}
