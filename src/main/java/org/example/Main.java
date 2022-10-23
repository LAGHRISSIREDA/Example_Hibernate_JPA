package org.example;


import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;

 import java.util.Arrays;
import java.util.List;

public class Main {
    private static JpaService jpaService = JpaService.getInstance();
    public static void main(String[] args) {

        try{

            createProgrammingLanguages();
            printProgrammingLanguages();


        }finally {
            jpaService.shutdown();
        }
    }

    private static void createProgrammingLanguages(){
        jpaService.runInTransaction(entityManager -> {
            Arrays.stream("Java,Javascript,Python,C#,Html,Css".split(","))
                    .map(name->new ProgrammingLanguage(name, (int) (Math.random()*10)))
                    .forEach(entityManager::persist);
            return null;
        });

    }

    private static void printProgrammingLanguages(){
        List<ProgrammingLanguage> list = jpaService.runInTransaction(entityManager ->
                entityManager.createQuery(
                        "SELECT pl FROM ProgrammingLanguage pl WHERE pl.rating>5",
                        ProgrammingLanguage.class
                ).getResultList());
        list.stream()
                .map(pl->pl.getName()+" : "+pl.getRating())
                .forEach(System.out::println);
    }
}