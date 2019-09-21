package edu.unapec.shoppingorders.servlets;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.unapec.shoppingorders.constants.ShoppingExcel;
import edu.unapec.shoppingorders.models.Client;
import edu.unapec.shoppingorders.repositories.ClientRepository;
import edu.unapec.shoppingorders.repositories.impl.ClientRepositoryImpl;
import edu.unapec.shoppingorders.utils.JsonUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;

@WebServlet("/api/clients")
public class ClientsServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            ClientRepository repository = new ClientRepositoryImpl(getServletContext().getRealPath(ShoppingExcel.PATH));
            String json =  json = JsonUtil.toJson(repository.getAll());

            resp.setContentType("application/json");
            resp.getWriter().write(json);
        } catch (JsonProcessingException  e) {
            resp.getWriter().write("{ message:"+ e.getMessage()+"'}'");
        } catch (IOException e) {
            e.printStackTrace();
            resp.getWriter().write("{ message:"+ e.getMessage()+"'}'");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException {
        try {
            Client client = JsonUtil.toEntity(req.getReader(), Client.class);
            ClientRepository repository = new ClientRepositoryImpl(getServletContext().getRealPath(ShoppingExcel.PATH));

            int idCreated = repository.add(client);
            resp.setContentType("application/json");
            resp.getWriter().write(JsonUtil.toJson(idCreated));
        } catch (IOException ex) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            try {
                resp.getWriter().write(ex.getMessage());
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (Exception ex) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            try {
                resp.getWriter().write(ex.getMessage());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException {
        try{
            Client client = JsonUtil.toEntity(req.getReader(), Client.class);
            ClientRepository repository = new ClientRepositoryImpl(getServletContext().getRealPath(ShoppingExcel.PATH));

            repository.update(client);
            resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
        }catch (IOException ex) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            try {
                resp.getWriter().write(ex.getMessage());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException {
        resp.setContentType("application/json");

        try {
            ClientRepository repository = new ClientRepositoryImpl(getServletContext().getRealPath(ShoppingExcel.PATH));
            repository.delete(Integer.parseInt(req.getParameter("id")));
            resp.sendError(HttpServletResponse.SC_NO_CONTENT);
        } catch (IOException ex) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            try {
                resp.getWriter().write(ex.getMessage());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}
