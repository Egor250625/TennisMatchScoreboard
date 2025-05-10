package egorivanov.servlets;

import egorivanov.models.MatchStatus;
import egorivanov.models.entity.Match;
import egorivanov.models.entity.Player;
import egorivanov.models.score.MatchScore;
import egorivanov.services.OnGoingMatchService;
import egorivanov.services.PlayerService;
import egorivanov.services.ScoreCalculatorService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

@WebServlet(value = "/match-score")
public class MatchScoreServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UUID matchId = UUID.fromString(req.getParameter("uuid"));
        OnGoingMatchService onGoingMatchService = OnGoingMatchService.getInstance();
        MatchScore match = onGoingMatchService.getMatch(matchId);

        if (match.getStatus() == MatchStatus.FINISHED){
            resp.sendRedirect("matches");
            return;
        }

        req.setAttribute("player1Id", match.getPlayer1Id());
        req.setAttribute("player2Id", match.getPlayer2Id());
        req.setAttribute("player1", match.getPlayer1Name());
        req.setAttribute("player2", match.getPlayer2Name());
        req.setAttribute("score", match);
        req.setAttribute("matchId", matchId.toString());
        req.getRequestDispatcher("match-score.jsp")
                .forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UUID matchId = UUID.fromString(req.getParameter("uuid"));
        int playerId = Integer.parseInt(req.getParameter("playerId"));
        ScoreCalculatorService scoreCalculatorService = ScoreCalculatorService.getInstance();
        OnGoingMatchService onGoingMatchService = OnGoingMatchService.getInstance();
        MatchScore match = onGoingMatchService.getMatch(matchId);



        if (match == null) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Match not found");
            return;
        }

        if (playerId == (match.getPlayer1Id())) {
            scoreCalculatorService.addPoint(matchId, match.getPlayer1Id());
        } else if (playerId == (match.getPlayer2Id())) {
            scoreCalculatorService.addPoint(matchId, match.getPlayer2Id());
        } else {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid player name");
            return;
        }

        resp.sendRedirect("/TennisTable/match-score?uuid=" + matchId);

    }
}
