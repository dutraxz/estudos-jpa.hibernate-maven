package br.com.estudos.jpaehibernate.dao;

import br.com.estudos.jpaehibernate.model.Objeto;
import br.com.estudos.jpaehibernate.util.JPAUtil;

import jakarta.persistence.EntityManager;

import java.util.List;

public class ObjetoDAO {


    public void salvar(Objeto objeto) {

        EntityManager em = JPAUtil.getEntityManager();
        // Chama a Fábrica (emf) e pega um novo EntityManager (em)
        // para esta operação. Este "em" é descartável e vive apenas para esta função.
        try {
            em.getTransaction().begin(); //inicia a transação com o banco
            //Se algo de errado, ele irá desfazer tudo

            em.merge(objeto); //executa a ação de persistencia
            // - Se o Objeto for novo (ID nulo), ele faz um INSERT.
            // - Se o Objeto já existir (ID preenchido), ele faz um UPDATE.
            // O objeto agora está sendo "monitorado" no Contexto de Persistência.

            em.getTransaction().commit(); //Se der certo, confirma as alteraçãoes com o banco para salvar tudo

        } catch (Exception e) {
            // Se a execução chegou aqui, algo falhou (ex: erro de conexão, violação de regra do banco).
            em.getTransaction().rollback();
            // Desfaz a transação: Avisa o banco que houve um problema e ele vai descartar tudo que foi feito nesta sessão
            throw e; //Re-lança o erro e avisa que a quem chamou o metodo que o objeto nao foi salvo
        } finally {
            //Finaliza o Ciclo de Vida
            em.close();
            // Fecha o EntityManager, destrói o contexto de persistência
            // (o cache da sessão), evitando vazamento de recursos
        }
    }

    public void remover(Objeto objeto) {
        EntityManager em = JPAUtil.getEntityManager();

        try {
            em.getTransaction().begin();
            Objeto objetoRemover = em.find(Objeto.class, objeto.getId()); //Busca o objeto para coloca-lo na persistencia
            em.remove(objetoRemover); //Removemos o objeto que esta monitorado
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    public List<Objeto> listar() {
    }
}

