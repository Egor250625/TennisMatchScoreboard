<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="egorivanov.models.score.MatchScore" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%
    MatchScore score = (MatchScore) request.getAttribute("score");
    String player1 = (String) request.getAttribute("player1");
    String player2 = (String) request.getAttribute("player2");
    String matchId = (String) request.getAttribute("matchId");
    int player1Id = score.getPlayer1Id();
    int player2Id = score.getPlayer2Id();
%>

<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Tennis Scoreboard | Match Score</title>

    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;500;700&display=swap" rel="stylesheet">
    <link href="https://fonts.googleapis.com/css2?family=Roboto+Mono:wght@300&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="css/style.css">

    <script src="js/app.js"></script>
</head>
<body>
<header class="header">
    <section class="nav-header">
        <div class="brand">
            <div class="nav-toggle">
                <img src="images/menu.png" alt="Logo" class="logo">
            </div>
            <span class="logo-text">TennisScoreboard</span>
        </div>
        <div>
            <nav class="nav-links">
                <a class="nav-link" href="#">Home</a>
                <a class="nav-link" href="#">Matches</a>
            </nav>
        </div>
    </section>
</header>

<main>
    <div class="container">
        <h1>Current match</h1>

        <section class="score">
            <table class="table">
                <thead class="result">
                <tr>
                    <th>Player</th>
                    <th>Sets</th>
                    <th>Games</th>
                    <th>Points</th>
                    <th>Action</th>
                </tr>
                </thead>
                <tbody>
                <tr class="player1">
                    <td><%= player1 %></td>
                    <td><%= score.getPlayer1Sets() %></td>
                    <td><%= score.getPlayer1Games() %></td>
                    <td><%= score.getPlayer1Points() %></td>
                    <td>
                        <form method="post" action="match-score?uuid=<%= matchId %>">
                            <input type="hidden" name="playerId" value="<%= player1Id %>" />
                            <button class="score-btn" type="submit">Score</button>
                        </form>
                    </td>
                </tr>
                <tr class="player2">
                    <td><%= player2 %></td>
                    <td><%= score.getPlayer2Sets() %></td>
                    <td><%= score.getPlayer2Games() %></td>
                    <td><%= score.getPlayer2Points() %></td>
                    <td>
                        <form method="post" action="match-score?uuid=<%= matchId %>">
                            <input type="hidden" name="playerId" value="<%= player2Id %>" />
                            <button class="score-btn" type="submit">Score</button>
                        </form>
                    </td>
                </tr>
                </tbody>
            </table>
        </section>
    </div>
</main>

<footer>
    <div class="footer">
        <p>&copy; Tennis Scoreboard, project from <a href="https://zhukovsd.github.io/java-backend-learning-course/">zhukovsd/java-backend-learning-course</a> roadmap.</p>
    </div>
</footer>
</body>
</html>
