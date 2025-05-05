package egorivanov.servlets;


import egorivanov.models.score.MatchScore;
import egorivanov.repository.PlayerRepository;
import egorivanov.services.MatchesService;
import egorivanov.services.OnGoingMatchService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@WebServlet("/matches")
public class MatchesServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        MatchesService matchesService = MatchesService.getINSTANCE();


        List<Map<String, String>> finishedMatches = matchesService.getFinishedMatchesWithNames();

        Collections.reverse(finishedMatches);

        req.setAttribute("matches", finishedMatches);


        req.getRequestDispatcher("matches.jsp")
                .forward(req, resp);
    }
}
