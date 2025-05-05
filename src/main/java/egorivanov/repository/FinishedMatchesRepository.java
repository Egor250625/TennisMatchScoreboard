package egorivanov.repository;

import egorivanov.models.entity.Match;
import egorivanov.models.entity.Player;
import egorivanov.models.score.MatchScore;
import egorivanov.utils.HibernateUtil;
import lombok.Getter;
import org.hibernate.Session;

import javax.persistence.EntityManager;
import java.util.List;

public class FinishedMatchesRepository {
    private EntityManager entityManager;
    private static final FinishedMatchesRepository INSTANCE = new FinishedMatchesRepository();

    private FinishedMatchesRepository() {

    }

    public static FinishedMatchesRepository getInstance() {
        return INSTANCE;
    }

    public void saveMatch(MatchScore matchScore, int playerId) {
        try (var sessionFactory = HibernateUtil.buildSessionFactory();
             var session = sessionFactory.openSession()) {
            session.beginTransaction();

            var player1 = Player.builder().name(matchScore.getPlayer1Name()).build();
            var player2 = Player.builder().name(matchScore.getPlayer2Name()).build();

            session.save(player1);
            session.save(player2);

            Player winner = null;
            if (playerId == matchScore.getPlayer1Id())
                winner = player1;
            else if (playerId == matchScore.getPlayer2Id())
                winner = player2;


            Match match = Match.builder().player1(player1).player2(player2).winner(winner).build();
            session.save(match);

            System.out.println(session.get(Match.class, 1));
            session.getTransaction().commit();

        }
    }

    public List<Match> findAllFinished() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("FROM Match", Match.class).list();
        }
    }

}
