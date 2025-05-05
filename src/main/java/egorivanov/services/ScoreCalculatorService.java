package egorivanov.services;

import egorivanov.models.MatchStatus;
import egorivanov.models.score.MatchScore;
import egorivanov.repository.FinishedMatchesRepository;
import egorivanov.utils.HibernateUtil;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.UUID;
public class ScoreCalculatorService {
    private  OnGoingMatchService onGoingMatchService;
    private static final ScoreCalculatorService INSTANCE = new ScoreCalculatorService();


    public ScoreCalculatorService(OnGoingMatchService onGoingMatchService) {
        this.onGoingMatchService = onGoingMatchService;
    }

   private ScoreCalculatorService(){
       this.onGoingMatchService = OnGoingMatchService.getInstance();

   }

    public static ScoreCalculatorService  getInstance() {
        return INSTANCE;
    }


    public boolean addPoint(UUID matchUUID, int playerId) {
        FinishedMatchesRepository finishedMatchesRepository = FinishedMatchesRepository.getInstance();
        MatchScore matchScore = onGoingMatchService.getMatch(matchUUID);
        if (matchScore == null || matchScore.getStatus() == MatchStatus.FINISHED) {
            return false;
        }
        boolean isPlayerId1 = matchScore.getPlayer1Id() == playerId;
        boolean isPlayerId2 = matchScore.getPlayer2Id() == playerId;

        if (isPlayerId1) {
            matchScore.addPointToPlayer(playerId);
        }else if(isPlayerId2){
            matchScore.addPointToPlayer(playerId);
        }



        if (matchScore.isGameWon()) {
            addGame(matchScore);
            matchScore.resetPoints();
        }

        if (matchScore.isSetWon()) {
            addSet(matchScore);
            matchScore.resetGames();
        }

        if(matchScore.isMatchFinished()){
            finishedMatchesRepository.saveMatch(matchScore,playerId);
        }
        return true;
    }


    public void addGame(MatchScore matchScore) {
        if (matchScore.getPlayer1Points() > matchScore.getPlayer2Points()) {
            matchScore.addGameToPlayer(matchScore.getPlayer1Id());
        } else if (matchScore.getPlayer2Points() > matchScore.getPlayer1Points()) {
            matchScore.addGameToPlayer(matchScore.getPlayer2Id());
        }
    }

    public void addSet(MatchScore matchScore) {
        if (matchScore.getPlayer1Games() > matchScore.getPlayer2Games()) {
            matchScore.addSetToPlayer(matchScore.getPlayer1Id());
        } else if (matchScore.getPlayer2Games() > matchScore.getPlayer1Games()) {
            matchScore.addSetToPlayer(matchScore.getPlayer2Id());
        }
    }
}
