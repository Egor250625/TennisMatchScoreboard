package egorivanov.repository;

import egorivanov.utils.HibernateUtil;
import org.hibernate.Session;

import javax.persistence.EntityManager;
import java.util.UUID;

public class PlayerRepository {
    private static final PlayerRepository INSTANCE = new PlayerRepository();

    private PlayerRepository() {
    }

    public static PlayerRepository getInstance() {
        return INSTANCE;
    }

    public String findNameById(int playerId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery(
                            "SELECT p.name FROM Player p WHERE p.id = :id", String.class)
                    .setParameter("id", playerId)
                    .uniqueResult();
        }
    }
}

