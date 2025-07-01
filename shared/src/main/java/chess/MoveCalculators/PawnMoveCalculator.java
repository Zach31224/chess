package chess.MoveCalculators;

import chess.*;

import java.util.HashSet;

public class PawnMoveCalculator implements MoveCalculator {

    public static HashSet<ChessMove> getMoves(ChessBoard board, ChessPosition position) {
        HashSet<ChessMove> possibleMoves = new HashSet<>(16); // max potential pawn moves

        int col = position.getColumn();
        int row = position.getRow();

        ChessGame.TeamColor color = board.getTeamOfSquare(position);
        int direction = (color == ChessGame.TeamColor.WHITE) ? 1 : -1;

        // Can I get a promotion?
        boolean isOnSecondToLastRank = (color == ChessGame.TeamColor.WHITE && row == 7)
                || (color == ChessGame.TeamColor.BLACK && row == 2);

        ChessPiece.PieceType[] promotionOptions = isOnSecondToLastRank
                ? new ChessPiece.PieceType[]{ChessPiece.PieceType.QUEEN, ChessPiece.PieceType.ROOK,
                ChessPiece.PieceType.BISHOP, ChessPiece.PieceType.KNIGHT}
                : new ChessPiece.PieceType[]{null};

        for (ChessPiece.PieceType promo : promotionOptions) {
            // Forward move by 1
            ChessPosition oneForward = new ChessPosition(row + direction, col);
            if (MoveCalculator.isValidSquare(oneForward) && board.getPiece(oneForward) == null) {
                possibleMoves.add(new ChessMove(position, oneForward, promo));

                // Double move at start
                boolean onStartRow = (color == ChessGame.TeamColor.WHITE && row == 2)
                        || (color == ChessGame.TeamColor.BLACK && row == 7);
                ChessPosition twoForward = new ChessPosition(row + 2 * direction, col);
                if (onStartRow && board.getPiece(twoForward) == null) {
                    possibleMoves.add(new ChessMove(position, twoForward, promo));
                }
            }

            // Diagonal attack from left
            ChessPosition diagLeft = new ChessPosition(row + direction, col - 1);
            if (MoveCalculator.isValidSquare(diagLeft)) {
                ChessPiece target = board.getPiece(diagLeft);
                if (target != null && target.getTeamColor() != color) {
                    possibleMoves.add(new ChessMove(position, diagLeft, promo));
                }
            }

            // Diagonal attack from right
            ChessPosition diagRight = new ChessPosition(row + direction, col + 1);
            if (MoveCalculator.isValidSquare(diagRight)) {
                ChessPiece target = board.getPiece(diagRight);
                if (target != null && target.getTeamColor() != color) {
                    possibleMoves.add(new ChessMove(position, diagRight, promo));
                }
            }
        }

        return possibleMoves;
    }
}
