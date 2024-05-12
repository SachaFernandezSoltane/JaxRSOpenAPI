package fr.istic.taa.jaxrs.rest;

import fr.istic.taa.jaxrs.dao.RoleDAO;
import fr.istic.taa.jaxrs.dao.StatusDAO;
import fr.istic.taa.jaxrs.dao.UserDAO;
import fr.istic.taa.jaxrs.domain.Role;
import fr.istic.taa.jaxrs.domain.Status;
import fr.istic.taa.jaxrs.domain.User;
import jakarta.persistence.Entity;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;
import jakarta.xml.bind.annotation.XmlRootElement;
import org.json.simple.JSONObject;

import java.util.Collections;
import java.util.List;

@Path("role")
@Produces({"application/json", "application/xml"})
public class RoleResource {
    private RoleDAO roleDAO = new RoleDAO();
    @POST
    @Path("/new")
    public Response newRole() {
        try {
            Role role = new Role();
            roleDAO.save(role);
            return Response.ok().entity("SUCCESS").build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Failed to create new status: " + e.getMessage()).build();
        }
    }

    @GET
    @Path("/all")
    public List<Role> getAllRoles() {
        try {
            return roleDAO.findAll();
        } catch (Exception e) {
            // Log the exception for debugging purposes
            e.printStackTrace();
            // Return an empty list in case of failure
            return Collections.emptyList();
        }
    }

    @GET
    @Path("/is_admin/{id}")
    public Response isAdmin(@PathParam("id") Long id) {
        try {
            UserDAO userDAO = new UserDAO();
            User user = userDAO.findOne(id);
            System.out.println(user.getRole().getRoleString());
            if(user.getRole().getRoleString().equals("ADMIN")){
                JSONObject responseJson = new JSONObject();
                responseJson.put("message", true);
                return Response.ok().entity(responseJson.toJSONString()).build();
            }else{
                JSONObject responseJson = new JSONObject();
                responseJson.put("message", false);
                return Response.ok().entity(responseJson.toJSONString()).build();
            }
        } catch (Exception e) {
            // Log the exception for debugging purposes
            e.printStackTrace();
            // Return an empty list in case of failure
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Failed to create new ticket: " + e.getMessage()).build();
        }
    }
}
