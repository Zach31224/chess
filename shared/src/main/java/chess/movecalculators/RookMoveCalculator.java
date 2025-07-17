package chess.movecalculators;

import chess.ChessBoard;
import chess.ChessGame;
import chess.ChessMove;
import chess.ChessPosition;

import java.util.HashSet;

public class RookMoveCalculator implements MoveCalculator {

    public static HashSet<ChessMove> getMoves(ChessBoard board, ChessPosition currPosition) {
        int currX = currPosition.getColumn();
        int currY = currPosition.getRow();
        int[][] moveDirections = {{0, 1}, {1, 0}, {0, -1}, {-1, 0}};

        ChessGame.TeamColor team = board.getTeamOfSquare(currPosition);

        return MoveCalculator.generateDirectionalMoves(board, currPosition, moveDirections, currY, currX, team);
    }

}
