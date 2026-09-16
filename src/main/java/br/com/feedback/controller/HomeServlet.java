package br.com.feedback.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

/**
 * CONTROLLER da tela inicial (Home).
 * So mostra a pagina de entrada com os atalhos para os cadastros.
 */
@WebServlet("/home")
public class HomeServlet extends BaseServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        this.forward(req, resp, "/WEB-INF/jsp/home.jsp");
    }
}
