package fr.istic.taa.jaxrs.rest;

import fr.istic.taa.jaxrs.dao.StatusDAO;
import fr.istic.taa.jaxrs.domain.Status;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.util.Collections;
import java.util.List;

@Path("status")
@Produces({"application/json", "application/xml"})
public class StatusResource {
    private StatusDAO statusDAO = new StatusDAO();

    @POST
    @Path("/new")
    public Response newStatus() {
        try {
            Status status = new Status();
            statusDAO.save(status);
            return Response.ok().entity("SUCCESS").build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Failed to create new status: " + e.getMessage()).build();
        }
    }

    @GET
    @Path("/all")
    public List<Status> getAllStatus() {
        try {
            return statusDAO.findAll();
        } catch (Exception e) {
            // Log the exception for debugging purposes
            e.printStackTrace();
            // Return an empty list in case of failure
            return Collections.emptyList();
        }
    }

    @DELETE
    @Path("/all")
    public Response deleteAllStatus() {
        try {
            statusDAO.deleteAll();
            return Response.ok().entity("Status deleted successfully").build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Failed to delete all status: " + e.getMessage()).build();
        }
    }

    @GET
    @Path("/{idStatus}")
    public Status getStatusById(@PathParam("idStatus") Long idStatus) {
        try {
            return statusDAO.findOne(idStatus);
        } catch (Exception e) {
            // Return null or handle the exception as per your application's logic
            return null;
        }
    }

    @DELETE
    @Path("/{idStatus}")
    public Response deleteStatus(@PathParam("idStatus") Long idStatus) {
        try {
            Status status = statusDAO.findOne(idStatus);
            if (status == null) {
                return Response.status(Response.Status.NOT_FOUND).entity("Status not found").build();
            }
            statusDAO.delete(status);
            return Response.ok().entity("Status deleted successfully").build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Failed to delete status: " + e.getMessage()).build();
        }
    }
}
