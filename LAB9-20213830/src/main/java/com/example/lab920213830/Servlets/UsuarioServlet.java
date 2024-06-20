package com.example.lab920213830.Servlets;

import com.example.lab920213830.Daos.UsuarioDao;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;

@WebServlet(name = "UsuarioServlet", value = "/UsuarioServlet")
public class UsuarioServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
       String action = request.getParameter("action") == null ? "lista" : request.getParameter("action");

       RequestDispatcher view;

       UsuarioDao usuarioDao = new UsuarioDao();
       switch (action) {
          case "lista":
             request.setAttribute("listaUsuarios", usuarioDao.listarUsuarios());
             view = request.getRequestDispatcher("usuarios/listaUsuarios.jsp");
             view.forward(request, response);
             break;
       }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
