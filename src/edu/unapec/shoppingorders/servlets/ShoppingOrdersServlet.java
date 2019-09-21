package edu.unapec.shoppingorders.servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.unapec.shoppingorders.constants.ShoppingExcel;
import edu.unapec.shoppingorders.models.ShoppingOrder;
import edu.unapec.shoppingorders.repositories.ProductRepository;
import edu.unapec.shoppingorders.repositories.ShoppingOrderRepository;
import edu.unapec.shoppingorders.repositories.impl.ProductRepositoryImpl;
import edu.unapec.shoppingorders.repositories.impl.ShoppingOrderRepositoryImpl;
import edu.unapec.shoppingorders.utils.JsonUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;

@WebServlet("/api/shopping-orders")
public class ShoppingOrdersServlet extends HttpServlet {
    ShoppingOrderRepository repository;
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException {
        try {
            String path = getServletContext().getRealPath(ShoppingExcel.PATH);
            repository = new ShoppingOrderRepositoryImpl(path);
            String json = JsonUtil.toJson(repository.getAll());

            resp.setContentType("application/json");
            resp.getWriter().write(json);
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
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException {
        resp.setContentType("application/json");

        try {
            ShoppingOrder order = JsonUtil.toEntity(req.getReader(), ShoppingOrder.class);
            ShoppingOrderRepository repository = new ShoppingOrderRepositoryImpl(getServletContext().getRealPath(ShoppingExcel.PATH));
            int idCreated = repository.add(order);

            resp.getWriter().write(JsonUtil.toJson(idCreated));
        } catch (IOException ex) {
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
        try {
            ShoppingOrder shoppingOrder = JsonUtil.toEntity(req.getReader() ,ShoppingOrder.class);
            ShoppingOrderRepository repository = new ShoppingOrderRepositoryImpl(getServletContext().getRealPath(ShoppingExcel.PATH));

            repository.update(shoppingOrder);
            resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
        } catch (IOException ex) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            try {
                resp.getWriter().write(ex.getMessage());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        repository = new ShoppingOrderRepositoryImpl(getServletContext().getRealPath(ShoppingExcel.PATH));
        repository.delete(Integer.parseInt(req.getParameter("id")));

        resp.setContentType("application/json");
        resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
        resp.getWriter().write("{message: 'success'}");
    }
}

