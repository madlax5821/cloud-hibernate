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
    
    public void saveAccount(Account account, Integer userId){
        Session session = HibernateUtil.getSession();
        Transaction transaction = session.beginTransaction();
        try{
            User user = session.get(User.class, userId);
            user.addAccount(account);
            session.save(user);
            transaction.commit();
        }catch (Exception e){
            e.printStackTrace();
            transaction.rollback();
        }finally {
            HibernateUtil.close();
        }
    }
    
    public void deleteAccountWithInUser(Account account, Integer userId){
        Session session = HibernateUtil.getSession();
        Transaction transaction = session.beginTransaction();
        try{
            User user = session.get(User.class, userId);
            user.removeAccount(account);
            
            session.saveOrUpdate(user);
            deleteAccountByAccountNumber(account.getAccountNum());
            transaction.commit();
        }catch (Exception e){
            e.printStackTrace();
            transaction.rollback();
        }finally {
            HibernateUtil.close();
        }
    }
    
    public void deleteAccountByAccountNumber(String accountNum){
        String hql_deleteAccountByAccountNum="delete from Account a where a.accountNum=:account_number";
        try (Session session = HibernateUtil.getSession()){
            Transaction transaction = session.beginTransaction();
            Query<Account> query = session.createQuery(hql_deleteAccountByAccountNum);
            query.setParameter("account_number",accountNum);
            query.executeUpdate();
            session.getTransaction().commit();
        }
    }
}
