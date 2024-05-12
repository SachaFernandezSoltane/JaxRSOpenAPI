package fr.istic.taa.jaxrs.domain;

import jakarta.persistence.*;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


@Entity
@XmlRootElement(name = "Status")
public class Status implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @XmlAttribute
    private int id;

    @XmlAttribute
    private String statusString;

    @OneToMany(mappedBy = "status", cascade = CascadeType.REMOVE)
    private List<Ticket> tickets = new ArrayList<>();

    public Status() {
        this.statusString = "OPEN";
    }

    public Status(String statusString) {
        this.statusString = statusString;
    }

    public void setUserStatus(String userStatus) {
        this.statusString = userStatus;
    }

    public String getUserStatus() {
        return statusString;
    }

    public void setTickets(Ticket ticket) {
        this.tickets.add(ticket);
    }

    public int getId() {
        return id;
    }
}