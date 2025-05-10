<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List, java.util.Map" %>

<%
    List<Map<String, String>> matches = (List<Map<String, String>>) request.getAttribute("matches");
%>

<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Tennis Scoreboard | Finished Matches</title>

    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;500;700&display=swap" rel="stylesheet">
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
                <a class="nav-link" href="/new-match">Home</a>
                <a class="nav-link" href="/matches">Matches</a>
            </nav>
        </div>
    </section>
</header>

<main>
    <div class="container">
        <h1>Matches</h1>
        <%
            Integer currentPage = (Integer) request.getAttribute("currentPage");
            Integer totalPages = (Integer) request.getAttribute("totalPages");
            String filterParam = request.getParameter("filter_by_player_name");
            if (currentPage != null && totalPages != null && totalPages > 1) {
        %>
        <div class="pagination">
            <% if (currentPage > 1) { %>
            <a class="prev"
               href="?page=<%= currentPage - 1 %><%= filterParam != null ? "&filter_by_player_name=" + filterParam : "" %>">&lt;</a>
            <% } %>

            <% for (int i = 1; i <= totalPages; i++) { %>
            <a class="num-page <%= i == currentPage ? "current" : "" %>"
               href="?page=<%= i %><%= filterParam != null ? "&filter_by_player_name=" + filterParam : "" %>"><%= i %>
            </a>
            <% } %>

            <% if (currentPage < totalPages) { %>
            <a class="next"
               href="?page=<%= currentPage + 1 %><%= filterParam != null ? "&filter_by_player_name=" + filterParam : "" %>">&gt;</a>
            <% } %>
        </div>
        <% } %>

        <form class="input-container" method="get" action="/TennisTable/matches">
            <input class="input-filter" name="filter_by_player_name"
                   placeholder="Filter by name" type="text"
                   value="<%= request.getParameter("filter_by_player_name") != null ?
                    request.getParameter("filter_by_player_name") : "" %>"/>

            <button class="btn-filter" type="submit">Filter</button>
        </form>


        <!-- Table displaying matches -->
        <table class="table-matches">
            <thead>
            <tr>
                <th>Player One</th>
                <th>Player Two</th>
                <th>Winner</th>
            </tr>
            </thead>
            <tbody>
            <%
                if (matches != null && !matches.isEmpty()) {
                    for (Map<String, String> match : matches) {
            %>
            <tr>
                <td><%= match.get("player1") %>
                </td>
                <td><%= match.get("player2") %>
                </td>
                <td><%= match.get("winner") %>
                </td>
            </tr>
            <%
                }
            } else {
            %>
            <tr>
                <td colspan="3">No finished matches available.</td>
            </tr>
            <%
                }
            %>
            </tbody>
        </table>
    </div>
</main>

<footer>
    <div class="footer">
        <p>&copy; Tennis Scoreboard, project from <a href="https://zhukovsd.github.io/java-backend-learning-course/">zhukovsd/java-backend-learning-course</a>
            roadmap.</p>
    </div>
</footer>
</body>
</html>
