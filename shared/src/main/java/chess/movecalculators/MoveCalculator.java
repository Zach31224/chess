package chess.movecalculators;

import chess.ChessBoard;
import chess.ChessGame;
import chess.ChessMove;
import chess.ChessPosition;

import java.util.HashSet;

public interface MoveCalculator {

    static HashSet<ChessMove> getMoves(ChessBoard board, ChessPosition currPosition) {
        return null;
    }

    static boolean isValidSquare(ChessPosition position) {
        return (position.getRow() >= 1 && position.getRow() <= 8) &&
                (position.getColumn() >= 1 && position.getColumn() <= 8);
    }

    /**
     * Generates moves based on static relative positions (like knight moves)
     */
    static HashSet<ChessMove> generateStaticMoves(
            ChessPosition currPosition,
            int[][] relativeMoves,
            ChessBoard board) {

        HashSet<ChessMove> moves = HashSet.newHashSet(8); // Knight has max 8 moves
        int currentColumn = currPosition.getColumn();
        int currentRow = currPosition.getRow();
        ChessGame.TeamColor team = board.getTeamOfSquare(currPosition);

        for (int[] relativeMove : relativeMoves) {
            ChessPosition possiblePosition = new ChessPosition(
                    currentRow + relativeMove[1],
                    currentColumn + relativeMove[0]
            );

            if (MoveCalculator.isValidSquare(possiblePosition) &&
                    board.getTeamOfSquare(possiblePosition) != team) {
                moves.add(new ChessMove(currPosition, possiblePosition, null));
            }
        }
        return moves;
    }

    /**
     * Generates moves in continuous directions (like rook, bishop, queen)
     */
    static HashSet<ChessMove> generateDirectionalMoves(
            ChessBoard board,
            ChessPosition currPosition,
            int[][] moveDirections,
            int currentRow,
            int currentColumn,
            ChessGame.TeamColor team) {

        HashSet<ChessMove> moves = HashSet.newHashSet(27); // Queen has max 27 moves
        for (int[] direction : moveDirections) {
            boolean obstructed = false;
            int distance = 1;

            while (!obstructed) {
                ChessPosition possiblePosition = new ChessPosition(
                        currentRow + direction[1] * distance,
                        currentColumn + direction[0] * distance
                );

                if (!MoveCalculator.isValidSquare(possiblePosition)) {
                    obstructed = true;
                }
                else if (board.getPiece(possiblePosition) == null) {
                    moves.add(new ChessMove(currPosition, possiblePosition, null));
                }
                else if (board.getTeamOfSquare(possiblePosition) != team) {
                    moves.add(new ChessMove(currPosition, possiblePosition, null));
                    obstructed = true;
                }
                else {
                    obstructed = true;
                }
                distance++;
            }
        }
        return moves;
    }
}