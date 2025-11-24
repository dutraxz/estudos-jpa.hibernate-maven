package br.com.estudos.jpaehibernate.dao;

import br.com.estudos.jpaehibernate.model.Objeto;
import org.junit.jupiter.api.*; // Importa todas as classes do JUnit
import static org.junit.jupiter.api.Assertions.*; // Para os comandos 'assert'

import java.util.List;

// 1. FORÇA A ORDEM DE EXECUÇÃO: Essencial para testes de CRUD sequenciais
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ObjetoDAOTest {

    private final ObjetoDAO dao = new ObjetoDAO();

    // ID que será usado para o UPDATE e DELETE (salvo entre os testes)
    private static Long idParaAtualizar;

    // ID que será o primeiro a ser deletado
    private static Long idParaDeletar;

    // ----------------------------------------------------
    // C R E A T E (Criação/Salvar)
    // ----------------------------------------------------

    // O @Order(1) garante que este método será o primeiro a rodar
    @Test
    @Order(1)
    void testA_InserirCincoItens() {
        System.out.println("--- 1. INSERINDO 5 ITENS (CREATE) ---");

        // 1. Instancia o Objeto (usando Integer para o lacre, como na sua Model)
        Objeto item0 = new Objeto("Chaveiro", "Chaveiro com pingente de pato", 900); // Lacre como Integer

        // 2. Salva (Chama o método VOID da DAO)
        dao.salvar(item0);

        // 3. BUSCA O OBJETO DE VOLTA (para pegar o ID que foi gerado pelo banco)
        // Usamos um truque: listamos todos e pegamos o primeiro (ou fazemos uma busca por nome)
        List<Objeto> lista = dao.listarTodos();
        Objeto objetoMonitorado = lista.get(0); // Pega o primeiro objeto inserido

        // Salva os IDs para os testes subsequentes
        idParaAtualizar = objetoMonitorado.getId();
        idParaDeletar = objetoMonitorado.getId();

        // ASSERT: Confirma que o ID foi gerado.
        assertNotNull(objetoMonitorado.getId(), "O ID do primeiro objeto não pode ser nulo.");

        // Insere mais 4 itens (usando Integer para o lacre)
        dao.salvar(new Objeto("Carteira", "Couro marrom, com R$10 dentro", 10));
        dao.salvar(new Objeto("Óculos", "Óculos de sol, marca Ray-Ban", 777));
        dao.salvar(new Objeto("Livro", "Livro de culinária, com capa rasgada", 333));
        dao.salvar(new Objeto("Celular", "Samsung S20, tela trincada", 202));

        System.out.println("5 itens inseridos. ID de teste: " + idParaAtualizar);
    }
    // ----------------------------------------------------
    // R E A D (Listar Todos)
    // ----------------------------------------------------

    @Test
    @Order(2)
    void testB_ListarTodos() {
        System.out.println("--- 2. LISTANDO TODOS (READ) ---");
        List<Objeto> todos = dao.listarTodos();

        // ASSERT: Verifica se realmente temos 5 itens ou mais (caso o banco tenha sujeira)
        assertTrue(todos.size() >= 5, "Deveria ter pelo menos 5 objetos no banco.");

        System.out.println("Total de objetos encontrados: " + todos.size());
        for (Objeto o : todos) {
            System.out.println(" -> ID: " + o.getId() + " | Nome: " + o.getNome());
        }
    }

    // ----------------------------------------------------
    // R E A D (Buscar por ID)
    // ----------------------------------------------------

    @Test
    @Order(3)
    void testC_BuscarPorId() {
        System.out.println("--- 3. BUSCANDO POR ID (READ) ---");

        // Busca o item que salvamos no primeiro teste
        Objeto objetoBuscado = dao.buscarPorId(idParaAtualizar);

        // ASSERT: Verifica se o objeto foi encontrado e se é o objeto certo.
        assertNotNull(objetoBuscado, "O objeto buscado pelo ID " + idParaAtualizar + " não foi encontrado.");
        assertEquals("Chaveiro", objetoBuscado.getNome(), "O nome do objeto encontrado deve ser 'Chaveiro'.");

        System.out.println("Objeto " + idParaAtualizar + " encontrado: " + objetoBuscado.getDescricao());
    }

    // ----------------------------------------------------
    // U P D A T E (Atualizar)
    // ----------------------------------------------------

    @Test
    @Order(4)
    void testD_AtualizarUmItem() {
        System.out.println("--- 4. ATUALIZANDO UM ITEM (UPDATE) ---");

        Objeto objetoParaAtualizar = dao.buscarPorId(idParaAtualizar);
        assertNotNull(objetoParaAtualizar, "Objeto para atualizar não pode ser nulo.");

        // CORREÇÃO CRÍTICA: Novo lacre deve ser uma String (Ex: "900-ATUALIZADO")
        Integer novoLacre = 9-900;

        // CORREÇÃO SINTAXE: Passando a variável String correta
        objetoParaAtualizar.setLacre(novoLacre);

        dao.salvar(objetoParaAtualizar);

        // ASSERT: Busca novamente e verifica a String
        Objeto objetoConfirmado = dao.buscarPorId(idParaAtualizar);
        assertEquals(novoLacre, objetoConfirmado.getLacre(), "O lacre não foi atualizado no banco de dados.");

        System.out.println("Item " + idParaAtualizar + " atualizado para Lacre: " + novoLacre);
    }
    // ----------------------------------------------------
    // D E L E T E (Remover)
    // ----------------------------------------------------

    @Test
    @Order(5)
    void testE_ExcluirUmItem() {
        System.out.println("--- 5. EXCLUINDO UM ITEM (DELETE) ---");

        // 1. Busca o item a ser deletado
        Objeto objetoParaDeletar = dao.buscarPorId(idParaDeletar);

        // 2. Chama o método de remoção
        dao.remover(objetoParaDeletar);

        // 3. ASSERT: Tenta buscar novamente para confirmar que foi deletado.
        Objeto objetoDeletado = dao.buscarPorId(idParaDeletar);
        assertNull(objetoDeletado, "O objeto deve ser nulo após a remoção.");

        System.out.println("Item " + idParaDeletar + " excluído com sucesso.");
    }
}