package egorivanov.services;

import egorivanov.models.entity.Match;
import egorivanov.repository.FinishedMatchesRepository;
import egorivanov.repository.PlayerRepository;

import java.util.*;
import java.util.stream.Collectors;

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

    public List<Map<String, String>> filterByName(String name, List<Map<String, String>> matches) {
        List<Map<String, String>> filteredMatches = matches.stream()
                .filter(match -> match.get("player1").contains(name) || match.get("player2")
                        .contains(name))
                .collect(Collectors.toList());

        return filteredMatches.isEmpty() ? null : filteredMatches;
    }

    public List<Map<String, String>> paginatedMatches(List<Map<String, String>> matches, int page) {
        final int pageSize = 7;

        int totalMatches = matches.size();
        int totalPages = (int) Math.ceil((double) totalMatches / pageSize);

        if (page < 1) {
            page = 1;
        } else if (page > totalPages) {
            page = totalPages;
        }

        int fromIndex = (page - 1) * pageSize;
        int toIndex = Math.min(fromIndex + pageSize, totalMatches);

        return matches.subList(fromIndex, toIndex);
    }

}
