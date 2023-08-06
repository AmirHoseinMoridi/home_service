package com.example.home_service.util;

import ir.maktab.exception.EntityManagerException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;


public class JpaUtil {

    private static final EntityManager em;

    static {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("home_service");
        if (emf != null) {
            em = emf.createEntityManager();
        } else {
            throw new EntityManagerException("Failed to create EntityManagerFactory");
        }
    }
    private JpaUtil() {
    }
    public static synchronized EntityManager getEntityManager() {
        return em;
    }

}
