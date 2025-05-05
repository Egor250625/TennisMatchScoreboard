package egorivanov.servlets;


import egorivanov.models.entity.Player;
import egorivanov.models.score.MatchScore;
import egorivanov.services.OnGoingMatchService;
import egorivanov.services.PlayerService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.UUID;

@WebServlet(value = "/new-match")
public class NewMatchServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name1 = req.getParameter("playerOne");
        String name2 = req.getParameter("playerTwo");

        if (name1 == null || name2 == null || name1.equals(name2)) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Игроки должны отличаться!");
        } else {
            Player player1 = PlayerService.findOrCreatePlayerByName(name1);
            Player player2 = PlayerService.findOrCreatePlayerByName(name2);

            OnGoingMatchService onGoingMatchService = OnGoingMatchService.getInstance();

            UUID matchId = onGoingMatchService.startNewMatch(
                    player1.getId(),
                    player2.getId(),
                    player1.getName(),
                    player2.getName()
            );
            MatchScore match = new MatchScore(player1.getId(), player2.getId(), player1.getName(), player2.getName());
            onGoingMatchService.addMatch(matchId, match);

            resp.sendRedirect("/match-score?uuid=" + matchId);

        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/new-match.html").forward(req, resp);
    }
}
