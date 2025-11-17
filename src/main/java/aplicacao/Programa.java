package aplicacao;

import dominio.Pessoa;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class Programa {

     public static void main(String[]args) {
         EntityManagerFactory emf = Persistence.createEntityManagerFactory("aulajpa");
         //Aqui instaciamos a config do EntytyManagerFactory com as config do banco no (persistence.xml)

         EntityManager em = emf.createEntityManager();
         //Aqui instanciamos o EntytyManager para termos a conexão com o banco e a persistencia implementada

         Pessoa p = em.find(Pessoa.class, 3);

         System.out.println("Os dados foram inseridos com sucesso ao banco!");
         System.out.println(p);

         em.close();
         emf.close();
     }
}
