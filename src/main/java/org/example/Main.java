package org.example;


import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;

public class Main {
    private static JpaService jpaService = JpaService.getInstance();
    public static void main(String[] args) {

        try{
            EntityManagerFactory entityManagerFactory = jpaService.getEntityManagerFactory();
            EntityManager entityManager = entityManagerFactory.createEntityManager();
            EntityTransaction transaction = entityManager.getTransaction();
            boolean success=false;
            transaction.begin();

            try{
                entityManager.persist(new ProgrammingLanguage("JavaScript",12));
                entityManager.persist(new ProgrammingLanguage("C++",32));
                entityManager.persist(new ProgrammingLanguage("Java",50));
                success = true;
            }finally {
                if(success){
                    transaction.commit();
                }else{
                    transaction.rollback();
                }
            }


        }finally {
            jpaService.shutdown();
        }
    }
}