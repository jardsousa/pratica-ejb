package com.gugawag.pdist.servlets;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.ejb.EJB;
import javax.servlet.annotation.WebServlet; 

import com.gugawag.pdist.ejbs.MensagemService;
import com.gugawag.pdist.model.Mensagem; 
@WebServlet(urlPatterns = {"/mensagem.do"})
public class MensagemServlet extends javax.servlet.http.HttpServlet {

    @EJB(lookup="java:module/mensagemService")
    private MensagemService mensagemService;

    protected void doPost(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response)
            throws javax.servlet.ServletException, IOException {
        try {
            long id = Long.parseLong(request.getParameter("id"));
            String mensagem = request.getParameter("mensagem");
            PrintWriter resposta = response.getWriter();
            mensagemService.inserir(id, mensagem);
            for(Mensagem msg: mensagemService.listar()){
                resposta.println(msg.getMensagem());
            }

        } catch (RuntimeException e) {
            response.getWriter().write("Error: " + e.getMessage());
        }
    }

    protected void doGet(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response)
            throws javax.servlet.ServletException, IOException {
        List<Mensagem> mensagens = mensagemService.listar();
        
        response.setContentType("text/html");
        response.getWriter().println("<html><body>");
        response.getWriter().println("<h1>List of Messages</h1>");
        response.getWriter().println("<ul>");
        
        for (Mensagem msg : mensagens) {
            response.getWriter().println("<li>" + msg.getMensagem() + "</li>");
        }
        
        response.getWriter().println("</ul>");
        response.getWriter().println("</body></html>");
    }
}
