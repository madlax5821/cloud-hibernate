package com.xiaofei.li.util;

import com.xiaofei.li.entity.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;

import java.util.Properties;

/**
 * Author: xiaofei
 * Date: 2022-04-17, 14:40
 * Description:
 */
public class HibernateUtil {
    private static SessionFactory factory;
    private static Session session;

    static {
        try {
            initializeFactory();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private static SessionFactory initializeFactory() {
        String url = System.getProperty("hibernate.url");
        String driver = System.getProperty("hibernate.driver");
        String username = System.getProperty("hibernate.username");
        String password = System.getProperty("hibernate.password");
        String dialect = System.getProperty("hibernate.dialect");

        Properties settings = new Properties();

        settings.put(Environment.URL,url);
        settings.put(Environment.DRIVER,driver);
        settings.put(Environment.USER,username);
        settings.put(Environment.PASS,password);
        settings.put(Environment.DIALECT,dialect);
        settings.put(Environment.SHOW_SQL,"true");
        settings.put(Environment.FORMAT_SQL,"true");
        settings.put(Environment.HBM2DDL_AUTO,"update");
        settings.put(Environment.HBM2DDL_AUTO,"validate");

        Configuration configuration = new Configuration();
        configuration.addProperties(settings);
        configuration.addAnnotatedClass(User.class);
        configuration.addAnnotatedClass(FullName.class);
        configuration.addAnnotatedClass(Account.class);
        configuration.addAnnotatedClass(Role.class);
        configuration.addAnnotatedClass(Permission.class);
        configuration.addAnnotatedClass(IDCard.class);

        StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder();
        ServiceRegistry serviceRegistry = builder.applySettings(configuration.getProperties()).build();
        factory = configuration.buildSessionFactory(serviceRegistry);
        return factory;
    }

    public static Session getSession(){
        try {
            session = factory.openSession();
        }catch (Exception e){
            e.printStackTrace();
        }
        return session;
    }

    public static void close(){
        session.close();
        //factory.close();
    }
}
