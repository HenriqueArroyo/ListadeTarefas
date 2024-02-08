package todolist.Model;

public class Task {
    // atributos
    private String data;
    private String nome;
    private boolean done;

    // Métodos
    public Task(String data, String nome) {
        this.data = data;
        this.nome = nome;
        this.done = false;
    }

 

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
