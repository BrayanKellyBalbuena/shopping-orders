package edu.unapec.shoppingorders.servlets;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.unapec.shoppingorders.constants.ShoppingExcel;
import edu.unapec.shoppingorders.models.Product;
import edu.unapec.shoppingorders.repositories.ProductRepository;
import edu.unapec.shoppingorders.repositories.impl.ProductRepositoryImpl;
import edu.unapec.shoppingorders.utils.JsonUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;

@WebServlet("/api/products")
public class ProductsServlet extends HttpServlet {
    private ProductRepository productRepository;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = getServletContext().getRealPath(ShoppingExcel.PATH);
        ProductRepository repository = null;
        try {
            repository = new ProductRepositoryImpl(path);
            String json =  json = JsonUtil.toJson(repository.getAll());

            resp.setContentType("application/json");
            resp.getWriter().write(json);
        } catch (JsonProcessingException e) {
            resp.getWriter().write("{ message:"+ e.getMessage()+"'}'");
        } catch (IOException e) {
            e.printStackTrace();
            resp.getWriter().write("{ message:"+ e.getMessage()+"'}'");
        }
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException {
        try {
            Product product = JsonUtil.toEntity(req.getReader(), Product.class);
            ProductRepository repository = new ProductRepositoryImpl(getServletContext().getRealPath(ShoppingExcel.PATH));
            int idCreated = repository.add(product);

            resp.setContentType("application/json");
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
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try{
            Product product = JsonUtil.toEntity(req.getReader(), Product.class);
            ProductRepository repository = new ProductRepositoryImpl(getServletContext().getRealPath(ShoppingExcel.PATH));

            repository.update(product);
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
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");

        try {
            productRepository = new ProductRepositoryImpl(getServletContext().getRealPath(ShoppingExcel.PATH));
            productRepository.delete(Integer.parseInt(req.getParameter("id")));
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
