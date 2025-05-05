package egorivanov.services;

import egorivanov.models.entity.Match;
import egorivanov.models.score.MatchScore;
import lombok.Getter;

import java.lang.reflect.Array;
import java.util.*;

public class OnGoingMatchService {

    private static final OnGoingMatchService INSTANCE = new OnGoingMatchService();
    private final Map<UUID, MatchScore> matches = new HashMap<>();

    private OnGoingMatchService() {
    }

    public static OnGoingMatchService getInstance() {
        return INSTANCE;
    }

    public MatchScore getMatch(UUID matchId) {
        return matches.get(matchId);
    }


    public UUID startNewMatch(int player1Id, int player2Id,String player1Name,String player2Name) {
        MatchScore matchScore = new MatchScore(player1Id, player2Id,player1Name,player2Name);
        UUID matchId = UUID.randomUUID();
        matches.put(matchId, matchScore);
        return matchId;
    }

    public void addMatch(UUID matchId, MatchScore matchScore) {
        matches.put(matchId, matchScore);
    }


}
