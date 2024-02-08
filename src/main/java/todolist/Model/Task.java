package todolist.Model;

public class Task {
    // atributos
    // private int usuario;
    private String data;
    private String nome;
    private boolean done;

    // Métodos
    public Task(String data, String nome) {
        // this.usuario = usuario;
        this.data = data;
        this.nome = nome;
        this.done = false;
    }

    // Getters and setters
    // public int getUsuario() {
    //     return usuario;
    // }

    // public void setUsuario(int usuario) {
    //     this.usuario = usuario;
    // }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

}
