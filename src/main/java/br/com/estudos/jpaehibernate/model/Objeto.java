package br.com.estudos.jpaehibernate.model;

import jakarta.persistence.*;

import java.io.Serializable;

@Entity
@Table(name="objeto")
public class Objeto implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;
    @Column(nullable = false)
    private String descricao;
    @Column(nullable = false, unique = true)
    private Integer lacre;

    public Objeto(){} //Obrigatório pelo JPA

    public Objeto(String nome, String descricao, Integer lacre) {
        this.nome = nome;
        this.descricao = descricao;
        this.lacre = lacre;
    }

    public Long getId() {return id;}
    public void setId(Long id) {this.id = id;}

    public String getNome() {return nome;}
    public void setNome(String nome) {this.nome = nome;}

    public String getDescricao() {return descricao;}
    public void setDescricao(String descricao) {this.descricao = descricao;}

    public Integer getLacre() {return lacre;}
    public void setLacre(Integer lacre) {this.lacre = lacre;}

    @Override
    public String toString() {
        return "Pessoa{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", descrição ='" + descricao + '\'' +
                ", lacre'" + lacre + '\'' +
                '}';
    }
}
