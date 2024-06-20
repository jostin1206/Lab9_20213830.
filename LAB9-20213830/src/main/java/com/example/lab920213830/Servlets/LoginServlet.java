package com.example.lab920213830.Servlets;

import com.example.lab920213830.Beans.Usuario;
import com.example.lab920213830.Daos.UsuarioDao;
import com.example.webapphr2.Beans.Employee;
import com.example.webapphr2.Daos.EmployeeDao;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet(name = "LoginServlet", value = "/LoginServlet")
public class LoginServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HttpSession httpSession = request.getSession(); //dame una sesion
        Usuario usuarioLogged = (Usuario) httpSession.getAttribute("usuarioLogueado");//dame ese usuario

        if(usuarioLogged != null && usuarioLogged.getIdUsuario() > 0){

            if(request.getParameter("a") != null){//logout
                httpSession.invalidate();
            }
            response.sendRedirect(request.getContextPath());
        }else{
            request.getRequestDispatcher("loginForm.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        System.out.println("username: " + username + " | password: " + password);
        UsuarioDao usuarioDao = new UsuarioDao();

        if(usuarioDao.validarUsuarioPasswordHashed(username,password)){
            System.out.println("usuario y password válidos");
            Usuario usuario = usuarioDao.obtenerUsuario(username);
            HttpSession httpSession = request.getSession();
            httpSession.setAttribute("usuarioLogueado",usuario);
            response.sendRedirect(request.getContextPath());
        }else{
            System.out.println("usuario o password incorrectos");
            request.setAttribute("err","Usuario o password incorrectos");
            request.getRequestDispatcher("login.jsp").forward(request,response);
        }
    }
}