package egorivanov.services;

import egorivanov.models.entity.Match;
import egorivanov.models.entity.Player;
import egorivanov.utils.HibernateUtil;

public class PlayerService {

    public static Player findOrCreatePlayerByName(String playerName) {
        try (var sessionFactory = HibernateUtil.buildSessionFactory();
             var session = sessionFactory.openSession()) {
            session.beginTransaction();
            Player player = session.createQuery(
                            "FROM Player where name  = :name", Player.class)
                    .setParameter("name", playerName)
                    .uniqueResult();

            if (player == null) {
                Player newPlayer = Player.builder().name(playerName).build();
                session.save(newPlayer);
                session.getTransaction().commit();
                return newPlayer;
            } else {
                return player;
            }
        }
    }


}
