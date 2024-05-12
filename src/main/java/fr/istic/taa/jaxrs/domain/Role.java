package fr.istic.taa.jaxrs.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
@Entity
@XmlRootElement(name = "User")
public class Role implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @XmlAttribute
    private int id;

    @XmlAttribute
    private String roleString;

    @OneToMany(mappedBy = "role")
    @JsonIgnoreProperties("role") // Ignorer la sérialisation de la liste des tickets de l'utilisateur pour éviter la récursion infinie
    private List<User> users;

    public Role() {
        this.roleString = "USER";
    }

    public Role(String roleString) {
        this.roleString = roleString;
    }

    public void setRoleString(String roleString) {
        this.roleString = roleString;
    }

    public String getRoleString() {
        return roleString;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public List<User> getUsers() {
        return users;
    }

    public int getId() {
        return id;
    }
}
