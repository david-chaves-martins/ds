package api_teste.ds.models;


import java.util.Objects;

import jakarta.persistence.*;

@Entity
@Table(name= "tarefa")
public class Tarefa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true)
    private Long Id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false, updatable = false)
    private User user;

    @Column(name = "descricao", length = 255, nullable = false)
    private String descricao;

    public Tarefa() {

    }

    public Tarefa(Long id, User user, String descricao) {
        this.Id = id;
        this.user = user;
        this.descricao = descricao;
    }

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tarefa tarefa = (Tarefa) o;
        return Objects.equals(Id, tarefa.Id) &&
                Objects.equals(user, tarefa.user) &&
                Objects.equals(descricao, tarefa.descricao);

    }

    @Override
    public int hashCode() {
        return Objects.hash(Id, user, descricao);
    }

}