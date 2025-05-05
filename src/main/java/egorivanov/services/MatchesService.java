package egorivanov.services;

import egorivanov.models.entity.Match;
import egorivanov.repository.FinishedMatchesRepository;
import egorivanov.repository.PlayerRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MatchesService {

    private static MatchesService INSTANCE = new MatchesService();

    public MatchesService() {
    }

    public static MatchesService getINSTANCE() {
        return INSTANCE;
    }

    public List<Map<String, String>> getFinishedMatchesWithNames() {
        FinishedMatchesRepository finishedMatchesRepository = FinishedMatchesRepository.getInstance();
        PlayerRepository playerRepository = PlayerRepository.getInstance();
        List<Match> matches = finishedMatchesRepository.findAllFinished();
        List<Map<String, String>> result = new ArrayList<>();


        for (Match match : matches) {
            String player1 = playerRepository.findNameById(match.getPlayer1().getId());
            String player2 = playerRepository.findNameById(match.getPlayer2().getId());
            String winner = playerRepository.findNameById(match.getWinner().getId());

            Map<String, String> finishedMatches = new HashMap<>();
            finishedMatches.put("player1", player1);
            finishedMatches.put("player2", player2);
            finishedMatches.put("winner", winner);

            result.add(finishedMatches);
        }
        return result;
    }

}
