package todolist.View;

import todolist.Control.TodolistControl;
import todolist.Control.TodolistDAO;
import todolist.Model.Task;
import java.util.ArrayList;
import java.util.List;

import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

public class TodolistView extends JFrame {
    // atributos
    private JPanel mainPanel;
    private JTextField taskInputNameField;
    private JButton addButton;
    private JList<String> taskList;
    private DefaultListModel<String> listModel;
    private JButton deleteButton;
    private JButton markDoneButton;
    private JComboBox<String> filterComboBox;
    private JButton clearCompletedButton;
    private List<Task> tasks;

    public TodolistView() {
        super("TO-DO List App");
        // this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(700, 700);
        // Inicializa o painel principal
        mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        // Inicializa a lista de tasks e a lista de tasks concluídas
        tasks = new ArrayList<>();
        listModel = new DefaultListModel<>();
        taskList = new JList<>(listModel);


        new TodolistDAO().criaTabela();

        taskInputNameField = new JTextField("Digite sua tarefa...");
        addButton = new JButton("Adicionar Tarefa");
        deleteButton = new JButton("Excluir Tarefa");
        markDoneButton = new JButton("Concluir a Tarefa");
        filterComboBox = new JComboBox<>(new String[] { "Todas", "Ativas",
                "Concluídas" });
        clearCompletedButton = new JButton("Limpar Concluídas");
        // Configuração do painel de entrada
        JPanel inputPanel = new JPanel(new BorderLayout());
        inputPanel.add(taskInputNameField, BorderLayout.CENTER);
    
        inputPanel.add(filterComboBox, BorderLayout.WEST);
        // Configuração do painel de botões
        JPanel buttonPanel = new JPanel();
        GridLayout gr = new GridLayout(4, 1, 0, 8);
        buttonPanel.setLayout(gr);
        buttonPanel.add(addButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(markDoneButton);
        buttonPanel.add(clearCompletedButton);
        mainPanel.add(inputPanel, BorderLayout.NORTH);
        mainPanel.add(new JScrollPane(taskList), BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
       
        this.add(mainPanel);
        

     
        taskInputNameField.setBackground(Color.gray);
        taskInputNameField.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        // taskInputNameField.setForeground(Color.WHITE);

        taskList.setBackground(Color.GRAY);
        taskInputNameField.setBorder(BorderFactory.createLineBorder(Color.black));
        taskList.setForeground(Color.BLACK);

        buttonPanel.setBackground(new Color(27, 27, 28));
        buttonPanel.setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0, 10), 5));

        addButton.setBackground(Color.gray);
        addButton.setForeground(Color.WHITE);
        addButton.setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0, 10), 5));

        deleteButton.setBackground(Color.gray);
        deleteButton.setForeground(Color.WHITE);
        deleteButton.setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0, 10), 5));

        markDoneButton.setBackground(Color.gray);
        markDoneButton.setForeground(Color.WHITE);
        markDoneButton.setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0, 10), 5));

        clearCompletedButton.setBackground(Color.gray);
        clearCompletedButton.setForeground(Color.WHITE);
        clearCompletedButton.setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0, 10), 5));

       
        TodolistControl controller = new TodolistControl(tasks, taskList, listModel);
     
        addButton.addActionListener(e -> { 
            controller.addTask(taskInputNameField.getText().trim()); 
        });

        deleteButton.addActionListener(e -> { 
            controller.deleteTask(taskList.getSelectedIndex());
        });

        markDoneButton.addActionListener(e -> {
            controller.markTaskDone(taskList.getSelectedIndex());
        });

        filterComboBox.addActionListener(e -> { 
            controller.filterTasks(filterComboBox);
        });

        clearCompletedButton.addActionListener(e -> { 
            controller.clearCompletedTasks();
        });
        

        // KeyListeners :
        taskInputNameField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    controller.addTask(taskInputNameField.getText().trim());
                }

            }
        }); 

        taskList.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_DELETE) { // Verifica se a tecla pressionada é a 'DELETE'
                    controller.deleteTask(taskList.getSelectedIndex()); // Se for , executa o método deleteTask(), apaga a tarefa.
                }

            }
        }); 
   

        // MouseListeners :
        taskList.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2 && e.getButton() == MouseEvent.BUTTON1) { // Conta quantos clicks foram dados
                                                                                     // e
                    // se foram com o botão esquerdo do
                    // mouse.
                    controller.markTaskDone(taskList.getSelectedIndex()); // Caso tenha dado 2 click seguidos com o botão esquerdo do mouse , executa o
                    // método markTaskDone(), marca a tarefa como concluída
                }
            }

        }); 
   

        taskInputNameField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) { // Quando o campo é clicado , "ganha o foco"
                if (taskInputNameField.getText().equals("Digite sua tarefa...")) {
                    taskInputNameField.setText(""); // O texto é limpado
                    taskInputNameField.setForeground(Color.WHITE); // Cor do texto Branco
                }
            }

            @Override
            public void focusLost(FocusEvent e) { // Quando outro componente é clicado , "perde o foco"
                if (taskInputNameField.getText().isEmpty()) {
                    taskInputNameField.setForeground(Color.GRAY); // Cor do texto cinza
                    taskInputNameField.setText("Digite sua tarefa..."); // Um texto padrão é colocado
                }
            }
        });

        this.addWindowListener(new WindowAdapter() { // Adiciona o ouvinte ao JFrame

            @Override
            public void windowClosing(WindowEvent e) {
                close(); 
            }

        });

        controller.updateTaskList();
    }


    public void run() {
        // Exibe a janela
        this.setVisible(true);
    }

    public void close() {
        int resposta = JOptionPane.showConfirmDialog(null, "Você deseja sair do app?", "TODO-LIST",
                JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE); // Pergunta ao usuário se deseja fechar o app
        if (resposta == JOptionPane.YES_OPTION) { // Caso escolha 'YES' , o app é fechado.
            this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        } else {
            this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE); // Caso escolha 'NO' , o app continuar aberto
        }
    }
}
