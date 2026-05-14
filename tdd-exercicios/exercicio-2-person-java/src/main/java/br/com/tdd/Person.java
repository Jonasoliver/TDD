package br.com.tdd;

import java.util.ArrayList;
import java.util.List;

/**
 * Representa uma pessoa.
 *
 * Conforme o diagrama UML:
 *  - id: int
 *  - name: String
 *  - age: int
 *  - relacionamento "possui" 1..* com Email
 */
public class Person {

    private int id;
    private String name;
    private int age;
    private List<Email> emails = new ArrayList<>();

    public Person() {
    }

    public Person(int id, String name, int age) {
        this.id = id;
        this.name = name;
        this.age = age;
    }

    public Person(int id, String name, int age, List<Email> emails) {
        this(id, name, age);
        if (emails != null) {
            this.emails = emails;
        }
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

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public List<Email> getEmails() {
        return emails;
    }

    public void setEmails(List<Email> emails) {
        this.emails = (emails == null) ? new ArrayList<>() : emails;
    }

    public void addEmail(Email email) {
        if (this.emails == null) {
            this.emails = new ArrayList<>();
        }
        this.emails.add(email);
    }
}
