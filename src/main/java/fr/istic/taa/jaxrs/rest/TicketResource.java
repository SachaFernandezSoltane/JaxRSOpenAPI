package fr.istic.taa.jaxrs.rest;

import fr.istic.taa.jaxrs.dao.StatusDAO;
import fr.istic.taa.jaxrs.dao.TicketDAO;
import fr.istic.taa.jaxrs.dao.UserDAO;
import fr.istic.taa.jaxrs.domain.Status;
import fr.istic.taa.jaxrs.domain.Ticket;
import fr.istic.taa.jaxrs.domain.User;
import io.swagger.v3.core.util.Json;
import jakarta.persistence.EntityNotFoundException;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.util.Collections;
import java.util.List;

@Path("ticket")
@Produces({"application/json", "application/xml"})
public class TicketResource {
    private TicketDAO ticketDAO = new TicketDAO();
    private UserDAO userDAO = new UserDAO();
    private StatusDAO statusDAO = new StatusDAO();

    @POST
    @Path("/new")
    public Response newTicket(JSONObject content) {
        try {
            String titreTicket = (String) content.get("titreTicket");
            String userTicket = (String) content.get("userTicket");
            String statusTicket = (String) content.get("statusTicket");
            String descriptionTicket = (String) content.get("descriptionTicket");

            UserDAO userDAO = new UserDAO();
            StatusDAO statusDAO = new StatusDAO();
            TicketDAO ticketDAO = new TicketDAO();

            User user = userDAO.findOne(Long.parseLong(userTicket));
            Status status = statusDAO.findOne(Long.parseLong(statusTicket));
            Ticket ticket = new Ticket(titreTicket,descriptionTicket,status,user);
            ticketDAO.save(ticket);
            JSONObject responseJson = new JSONObject();
            responseJson.put("message", "SUCCESS");
            return Response.ok().entity(responseJson.toJSONString()).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Failed to create new ticket: " + e.getMessage()).build();
        }
    }

    @GET
    @Path("/all")
    public List<Ticket> getAllTickets() {
        try {
            return ticketDAO.findAll();
        } catch (Exception e) {
            // Log the exception for debugging purposes
            e.printStackTrace();
            // Return an empty list in case of failure
            return Collections.emptyList();
        }
    }

    @DELETE
    @Path("/all")
    public Response deleteAllTickets() {
        try {
            ticketDAO.deleteAll();
            return Response.ok().entity("Ticket deleted successfully").build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Failed to delete all tickets: " + e.getMessage()).build();
        }
    }

    @GET
    @Path("/{idTicket}")
    public Ticket getTicketById(@PathParam("idTicket") Long idTicket) {
        try {
            return ticketDAO.findOne(idTicket);
        } catch (Exception e) {
            // Return null or handle the exception as per your application's logic
            return null;
        }
    }

    @DELETE
    @Path("/{idTicket}")
    public Response deleteTicket(@PathParam("idTicket") Long idTicket) {
        try {
            Ticket ticket = ticketDAO.findOne(idTicket);
            if (ticket == null) {
                return Response.status(Response.Status.NOT_FOUND).entity("Ticket not found").build();
            }
            ticketDAO.delete(ticket);
            JSONObject responseJson = new JSONObject();
            responseJson.put("message", "SUCCESS");
            return Response.ok().entity(responseJson.toJSONString()).build();
        } catch (EntityNotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Bug not found: " + e.getMessage())
                    .build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Failed to delete bug: " + e.getMessage())
                    .build();
        }
    }

    @PUT
    @Path("/update")
    public Ticket updateTicketById(JSONObject content) {
        try {
            System.out.println(content);

            String idTicket = (String) content.get("idTicket");
            String titreTicket = (String) content.get("titreTicket");
            String userTicket = (String) content.get("userTicket");
            String statusTicket = (String) content.get("statusTicket");
            String descriptionTicket = (String) content.get("descriptionTicket");
            Ticket ticket = ticketDAO.findOne(Long.parseLong(idTicket));
            User user = userDAO.findOne(Long.parseLong(userTicket));
            Status status = statusDAO.findOne(Long.parseLong(statusTicket));
            ticket.setName(titreTicket);
            ticket.setUser(user);
            ticket.setStatus(status);
            ticket.setDescription(descriptionTicket);
            ticketDAO.update(ticket);
            return ticketDAO.findOne(1L);
        } catch (Exception e) {
            // Return null or handle the exception as per your application's logic
            return null;
        }
    }
}
