package br.com.tdd;

/**
 * Representa um e-mail associado a uma {@link Person}.
 *
 * Conforme o diagrama de classes:
 *  - id: int
 *  - name: String  (o endereco de e-mail propriamente dito)
 */
public class Email {

    private int id;
    private String name;

    public Email() {
    }

    public Email(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
