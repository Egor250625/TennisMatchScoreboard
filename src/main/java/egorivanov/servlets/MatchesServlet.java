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

        //req.setAttribute("matches", finishedMatches);
        String pageParam = req.getParameter("page");
        int currentPage = (pageParam == null || pageParam.isEmpty()) ? 1 : Integer.parseInt(pageParam);

        String filteredName = req.getParameter("filter_by_player_name");

        if (filteredName != null && !filteredName.trim().isEmpty()) {
            finishedMatches = matchesService.filterByName(filteredName, finishedMatches);
        }
        List<Map<String, String>> paginatedMatches = matchesService.paginatedMatches(finishedMatches,currentPage);

        int totalPages = (int) Math.ceil((double) finishedMatches.size() / 7);

        req.setAttribute("matches", paginatedMatches);
        req.setAttribute("currentPage", currentPage);
        req.setAttribute("totalPages", totalPages);

        req.getRequestDispatcher("matches.jsp")
                .forward(req, resp);
    }
}
