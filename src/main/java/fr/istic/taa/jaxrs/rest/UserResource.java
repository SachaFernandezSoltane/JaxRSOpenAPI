package fr.istic.taa.jaxrs.rest;

import fr.istic.taa.jaxrs.dao.RoleDAO;
import fr.istic.taa.jaxrs.dao.UserDAO;
import fr.istic.taa.jaxrs.domain.Role;
import fr.istic.taa.jaxrs.domain.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.swagger.v3.core.util.Json;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import io.jsonwebtoken.security.Keys;
import java.security.Key;


@Path("user")
@Produces({"application/json", "application/xml"})
public class UserResource {
    private UserDAO userDAO = new UserDAO();
    private static final Key SECRET_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS512);


    @POST
    @Path("/login")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response login(String content) {
        try {
            JSONParser parser = new JSONParser();
            JSONObject jsonObject = (JSONObject) parser.parse(content);
            String email = (String) jsonObject.get("email");
            String password = (String) jsonObject.get("password");

            // Vérifier si l'utilisateur existe dans la base de données en utilisant email et mot de passe
            User user = userDAO.findByEmailAndPassword(email, password);
            if (user != null) {
                // Générer un jeton JWT
                String token = Jwts.builder()
                        .setSubject(user.getId().toString()) // Utiliser l'ID de l'utilisateur comme sujet du jeton
                        .setExpiration(new Date(System.currentTimeMillis() + 86400000)) // Expire dans 24 heures
                        .signWith(SECRET_KEY, SignatureAlgorithm.HS512)
                        .compact();

                // Retourner le jeton JWT dans la réponse
                JSONObject responseJson = new JSONObject();
                responseJson.put("message", "SUCCESS");
                responseJson.put("token", token);
                responseJson.put("user_id", user.getId());
                System.out.print(user.getId());
                return Response.ok().entity(responseJson.toJSONString()).build();
            } else {
                return Response.status(Response.Status.UNAUTHORIZED)
                        .entity("Identifiants incorrects").build();
            }
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Erreur lors de la connexion : " + e.getMessage()).build();
        }
    }

    @POST
    @Path("/new")
    public Response newUser(JSONObject content) {
        try {
            String email = (String) content.get("email");
            String password = (String) content.get("password");
            String username = (String) content.get("username");
            User user = new User(username, email, password);
            RoleDAO roleDAO = new RoleDAO();
            Role role = roleDAO.findByRoleString("USER");
            user.setRole(role);
            userDAO.save(user);
            JSONObject responseJson = new JSONObject();
            responseJson.put("message", "SUCCESS");
            return Response.ok().entity(responseJson.toJSONString()).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Failed to create new user: " + e.getMessage()).build();
        }
    }

    @GET
    @Path("/all")
    public List<User> getAllUsers() {
        try {
            return userDAO.findAll();
        } catch (Exception e) {
            // Log the exception for debugging purposes
            e.printStackTrace();
            // Return an empty list in case of failure
            return Collections.emptyList();
        }
    }

    @DELETE
    @Path("/all")
    public Response deleteAllUsers() {
        try {
            userDAO.deleteAll();
            return Response.ok().entity("User deleted successfully").build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Failed to delete all users: " + e.getMessage()).build();
        }
    }

    @GET
    @Path("/{idUser}")
    public User getUserById(@PathParam("idUser") Long idUser) {
        try {
            return userDAO.findOne(idUser);
        } catch (Exception e) {
            // Return null or handle the exception as per your application's logic
            return null;
        }
    }

    @DELETE
    @Path("/{idUser}")
    public Response deleteUser(@PathParam("idUser") Long idUser) {
        try {
            User user = userDAO.findOne(idUser);
            if (user == null) {
                return Response.status(Response.Status.NOT_FOUND).entity("User not found").build();
            }
            userDAO.delete(user);
            return Response.ok().entity("User deleted successfully").build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Failed to delete user: " + e.getMessage()).build();
        }
    }
}
