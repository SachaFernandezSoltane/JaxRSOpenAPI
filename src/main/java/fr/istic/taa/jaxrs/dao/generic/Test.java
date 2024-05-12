package fr.istic.taa.jaxrs.dao.generic;

import fr.istic.taa.jaxrs.dao.*;
import fr.istic.taa.jaxrs.domain.Status;
import fr.istic.taa.jaxrs.domain.Ticket;
import fr.istic.taa.jaxrs.domain.User;
import fr.istic.taa.jaxrs.domain.Role;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;


public class Test {

    private EntityManager manager;

    public Test(EntityManager manager) {
        this.manager = manager;
    }

    public static void main(String[] args) {
        EntityManager manager = EntityManagerHelper.getEntityManager();
        UserDAO userDAO = new UserDAO();
        RoleDAO roleDAO = new RoleDAO();
        TagDAO tagDAO = new TagDAO();
        TicketDAO ticketDAO = new TicketDAO();
        StatusDAO statusDAO = new StatusDAO();

        EntityTransaction tx = manager.getTransaction();

        try {
            // Création des rôles
            Role role = new Role("ADMIN");
            Role role2 = new Role("USER");

            // Création des utilisateurs
            User user = new User("test","test","test");
            User user2 = new User("test2","test2","test2");
            user.setRole(role);
            user2.setRole(role2);

            // Création des statuts
            Status status = new Status("OPEN");
            Status status2 = new Status("CLOSED");
            Status status3 = new Status("PENDING");

            // Sauvegarde des rôles
            roleDAO.save(role);
            roleDAO.save(role2);

            // Sauvegarde des utilisateurs
            userDAO.save(user);
            userDAO.save(user2);

            // Sauvegarde des statuts
            statusDAO.save(status);
            statusDAO.save(status2);
            statusDAO.save(status3);

            Ticket ticket1 = new Ticket("test1", "Ce ticket concerne un problème de connexion utilisateur.", status, user);
            Ticket ticket2 = new Ticket("test2", "Ce ticket traite un dysfonctionnement dans le système de paiement.", status3, user);
            Ticket ticket3 = new Ticket("test3", "Ce ticket concerne une erreur lors du chargement de la page d'accueil.", status, user2);
            Ticket ticket4 = new Ticket("test4", "Ce ticket concerne un problème de performance dans le module de recherche.", status, user);
            Ticket ticket5 = new Ticket("test5", "Ce ticket concerne un bug dans le formulaire de contact.", status2, user);
            Ticket ticket6 = new Ticket("test6", "Ce ticket traite un problème d'affichage sur les pages produits.", status, user2);
            Ticket ticket7 = new Ticket("test7", "Ce ticket concerne une erreur dans le processus de commande.", status2, user2);
            Ticket ticket8 = new Ticket("test8", "Ce ticket traite un problème de compatibilité avec les navigateurs mobiles.", status, user2);
            Ticket ticket9 = new Ticket("test9", "Ce ticket concerne un bug dans le panier d'achat.", status2, user);
            Ticket ticket10 = new Ticket("test10", "Ce ticket traite un problème de sécurité sur le site web.", status3, user2);

            ticketDAO.save(ticket1);
            ticketDAO.save(ticket2);
            ticketDAO.save(ticket3);
            ticketDAO.save(ticket4);
            ticketDAO.save(ticket5);
            ticketDAO.save(ticket6);
            ticketDAO.save(ticket7);
            ticketDAO.save(ticket8);
            ticketDAO.save(ticket9);
            ticketDAO.save(ticket10);
        } catch (Exception e) {
            e.printStackTrace();
            tx.rollback(); // Annuler la transaction en cas d'erreur
        } finally {
            manager.close();
            EntityManagerHelper.closeEntityManagerFactory();
        }
    }
}