package todolist.Control;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import todolist.Control.Connection.ConnectionFactory;
import todolist.Model.Task;

public class TodolistDAO {
    private Connection connection;
    private List<Task> tasks;

    public TodolistDAO(){
        this.connection = ConnectionFactory.getConnection();
    }

     // criar Tabela
    public void criaTabela() {
        String sql = "CREATE TABLE IF NOT EXISTS listadetarefasapp (id SERIAL NOT NULL PRIMARY KEY, NOME VARCHAR(255), DATA VARCHAR(255))";
        try (Statement stmt = this.connection.createStatement()) {
            stmt.execute(sql);
            System.out.println("Tabela criada com sucesso.");
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao criar a tabela: " + e.getMessage(), e);
        } finally {
            ConnectionFactory.closeConnection(this.connection);
        }
    }

    // Listar todos os valores cadastrados
    public List<Task> listarTodos() {
        PreparedStatement stmt = null;
        // Declaração do objeto PreparedStatement para executar a consulta
        ResultSet rs = null;
        // Declaração do objeto ResultSet para armazenar os resultados da consulta
        tasks = new ArrayList<>();
        // Cria uma lista para armazenar os carros recuperados do banco de dados
        try {
            stmt = connection.prepareStatement("SELECT * FROM listadetarefasapp");
            // Prepara a consulta SQL para selecionar todos os registros da tabela
            rs = stmt.executeQuery();
            // Executa a consulta e armazena os resultados no ResultSet
            while (rs.next()) {
                // Para cada registro no ResultSet, cria um objeto Carros com os valores do
                // registro

                Task task = new Task(rs.getString("data"), rs.getString("nome"));
                tasks.add(task); // Adiciona o objeto Clientes à lista de carros
            }
        } catch (SQLException ex) {
            System.out.println(ex); // Em caso de erro durante a consulta, imprime o erro
        } finally {
            ConnectionFactory.closeConnection(connection, stmt, rs);
            // Fecha a conexão, o PreparedStatement e o ResultSet
        }
        return tasks; // Retorna a lista de clientes recuperados do banco de dados
    }

    public void apagarTabela() {
        String sql = "DROP TABLE listadetarefasapp";
        try (Statement stmt = connection.createStatement()) {
            stmt.executeUpdate(sql);
            System.out.println("Tabela apagada com sucesso.");
        } catch (Exception e) {
            throw new RuntimeException("Erro ao apagar tabela.", e);
        } finally {
            ConnectionFactory.closeConnection(this.connection);
        }
    }

    public void cadastrar(String nome, String data) {
        PreparedStatement stmt = null;
        // Define a instrução SQL parametrizada para cadastrar na tabela
        String sql = "INSERT INTO listadetarefasapp(nome, data) VALUES (?, ?)";
        try {
            stmt = connection.prepareStatement(sql);
            stmt.setString(1, nome.toUpperCase().trim());
            stmt.setString(2, data.trim());
            stmt.executeUpdate();
            System.out.println("Dados inseridos com sucesso");
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao inserir dados no banco de dados.", e);
        } finally {
            ConnectionFactory.closeConnection(connection, stmt);
        }
    }

    // Atualizar dados no banco
    public void atualizar(String nomeCompleto, int id) {
        PreparedStatement stmt = null;
        // Define a instrução SQL parametrizada para atualizar dados pela placa
        String sql = "UPDATE listadetarefasapp SET nome = ? WHERE id = ?";
        try {
            stmt = connection.prepareStatement(sql);
            stmt.setString(1, nomeCompleto.toUpperCase().trim());
            // placa é chave primaria não pode ser alterada.
            stmt.setInt(2, id);
            stmt.executeUpdate();
            System.out.println("Dados atualizados com sucesso");
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar dados no banco de dados.", e);
        } finally {
            ConnectionFactory.closeConnection(connection, stmt);
        }
    }

    // Apagar dados do banco
    public void apagar(int id) {
        PreparedStatement stmt = null;
        // Define a instrução SQL parametrizada para apagar dados pela placa
        String sql = "DELETE FROM listadetarefasapp WHERE id = ?";
        try {
            stmt = connection.prepareStatement(sql);
            stmt.setInt(1, id);
            stmt.executeUpdate(); // Executa a instrução SQL
            System.out.println("Dado apagado com sucesso");
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao apagar dados no banco de dados.", e);
        } finally {
            ConnectionFactory.closeConnection(connection, stmt);
        }
    }
}
