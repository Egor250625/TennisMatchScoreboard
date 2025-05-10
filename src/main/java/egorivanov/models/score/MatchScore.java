package egorivanov.models.score;


import egorivanov.models.MatchStatus;
import egorivanov.models.entity.Player;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class MatchScore {
    private int player1Id;
    private int player2Id;
    private String player1Name;
    private String player2Name;


    private int player1Points;
    private int player2Points;
    private int player1Games;
    private int player2Games;
    private int player1Sets;
    private int player2Sets;
    private MatchStatus status;

    public MatchScore(int player1Id, int player2Id, String player1Name, String player2Name) {
        this.player1Id = player1Id;
        this.player2Id = player2Id;
        this.player1Name = player1Name;
        this.player2Name = player2Name;
    }


    public void addPointToPlayer(int playerId) {
        if (playerId == player1Id) {
            player1Points++;
        } else if (playerId == player2Id) {
            player2Points++;
        } else throw new IllegalArgumentException("Unknown player ID: " + playerId);
    }


    public boolean isGameWon() {
        if (player1Points >= 4 && player2Points <= 3) {
            return true;
        } else if (player2Points >= 4 && player1Points <= 3) {
            return true;
        }
        return false;
    }

    public void resetPoints() {
        player1Points = 0;
        player2Points = 0;
    }

    public boolean isSetWon() {

        if (player1Games >= 6 && player1Games - player2Games >= 2) {
            return true;
        }

        if (player2Games >= 6 && player2Games - player1Games >= 2) {
            return true;
        }
        return false;
    }

    public void resetGames() {
        player1Games = 0;
        player2Games = 0;
    }

    public boolean isMatchFinished() {
        if (player1Sets >= 3) {
            status = MatchStatus.FINISHED;
            return true;
        } else if (player2Sets >= 3) {
            status = MatchStatus.FINISHED;
            return true;
        }
        return false;
    }

    public void addGameToPlayer(int playerId) {
        if (playerId == player1Id) {
            player1Games++;
        } else if (playerId == player2Id) {
            player2Games++;
        } else throw new IllegalArgumentException("Unknown player ID: " + playerId);
    }

    public void addSetToPlayer(int playerId) {
        if (playerId == player1Id) {
            player1Sets++;
        } else if (playerId == player2Id) {
            player2Sets++;
        } else throw new IllegalArgumentException("Unknown player ID: " + playerId);
    }

}
