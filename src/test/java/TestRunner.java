import egorivanov.models.entity.Match;
import egorivanov.models.entity.Player;
import egorivanov.models.score.MatchScore;
import egorivanov.repository.FinishedMatchesRepository;
import egorivanov.services.OnGoingMatchService;
import egorivanov.services.PlayerService;
import egorivanov.services.ScoreCalculatorService;
import egorivanov.utils.HibernateUtil;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestRunner {
    @Test
    public void addMatchTest(){
        FinishedMatchesRepository finishedMatchesRepository = FinishedMatchesRepository.getInstance();
        MatchScore matchScore = new MatchScore(15,16,"Egor","Andrei");
        finishedMatchesRepository.saveMatch(matchScore,15);


    }


    @Test
    public void addPoint() {
        try (var sessionFactory = HibernateUtil.buildSessionFactory();
             var session = sessionFactory.openSession()) {
            session.beginTransaction();

            var player11 = Player.builder().name("Egor").build();
            var player22 = Player.builder().name("Andrey").build();

            session.save(player11);
            session.save(player22);

            Player player1 = PlayerService.findOrCreatePlayerByName(player11.getName());
            Player player2 = PlayerService.findOrCreatePlayerByName(player22.getName());

            // Создание матча
            UUID matchId = OnGoingMatchService.getInstance().startNewMatch(
                    player1.getId(),
                    player2.getId(),
                    player1.getName(),
                    player2.getName()
            );

            // ScoreCalculatorService использует singleton OngoingMatchesService
            ScoreCalculatorService scoreCalculatorService = new ScoreCalculatorService(OnGoingMatchService.getInstance());

            // Действие — добавляем очко игроку 1
            boolean result = scoreCalculatorService.addPoint(matchId, player1.getId());

            // Получаем матч и проверяем результат
            MatchScore matchScore = OnGoingMatchService.getInstance().getMatch(matchId);
            System.out.println(matchScore);
            System.out.println("До: " + matchScore.getPlayer1Points());
            System.out.println("После: " + matchScore.getPlayer1Points());
            // Проверка
            // assertTrue(result);
            // assertEquals(1, matchScore.getPlayer1Points());
            // assertEquals(0, matchScore.getPlayer2Points());
//
            session.getTransaction().commit();
        }
    }

    @Test
    public void createPlayer() {

        try (var sessionFactory = HibernateUtil.buildSessionFactory();
             var session = sessionFactory.openSession()) {
            session.beginTransaction();

            var player1 = Player.builder().name("Egor").build();
            var player2 = Player.builder().name("Andrey").build();

            session.save(player1);
            session.save(player2);

            var match = new Match();
            match.setPlayer1(session.get(Player.class, 1));
            match.setPlayer2(session.get(Player.class, 2));
            match.setWinner(session.get(Player.class, 2));

            session.save(match);


            System.out.println(session.get(Match.class, 1));
            String playerName = "Egor";
            Player player = session.createQuery(
                            "FROM Player where name  = :name", Player.class)
                    .setParameter("name", playerName)
                    .uniqueResult();
            System.out.println(player);

            session.getTransaction().commit();
        }
    }
}
