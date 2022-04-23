package com.xiaofei.li.dao;

import com.xiaofei.li.entity.Account;
import com.xiaofei.li.entity.User;
import com.xiaofei.li.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import java.util.Set;

/**
 * Author: xiaofei
 * Date: 2022-04-23, 14:31
 * Description:
 */
@Repository
public class AccountDao {
    public Set<Account> getAccountsByUserId(Integer id){
        String hql_FetchAccountsByUserId="from User u left join fetch u.accounts as a where u.id=:id";
        try(Session session = HibernateUtil.getSession()){
            Query<User> query = session.createQuery(hql_FetchAccountsByUserId);
            query.setParameter("id",id);
            return query.uniqueResult().getAccounts();
        }
    }
}
