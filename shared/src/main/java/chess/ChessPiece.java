package chess;

import chess.movecalculators.*;

import java.util.Collection;
import java.util.Objects;

/**
 * Represents a chess piece with its type and team color.
 * Provides legal moves for the piece based on its type and board context.
 */
public class ChessPiece {

    private final ChessGame.TeamColor teamColor;
    private final PieceType pieceType;

    public ChessPiece(ChessGame.TeamColor teamColor, PieceType pieceType) {
        this.teamColor = teamColor;
        this.pieceType = pieceType;
    }

    /**
     * Different types of chess pieces.
     */
    public enum PieceType {
        KING,
        QUEEN,
        BISHOP,
        KNIGHT,
        ROOK,
        PAWN
    }

    /**
     * Gets the team color of this piece.
     */
    public ChessGame.TeamColor getTeamColor() {
        return teamColor;
    }

    /**
     * Gets the type of this piece.
     */
    public PieceType getPieceType() {
        return pieceType;
    }

    /**
     * Returns all possible moves for this piece on the given board from the given position.
     * Does not account for checks or turn order.
     */
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition position) {
        return switch (pieceType) {
            case KING -> KingMoveCalculator.getMoves(board, position);
            case QUEEN -> QueenMoveCalculator.getMoves(board, position);
            case BISHOP -> BishopMoveCalculator.getMoves(board, position);
            case KNIGHT -> KnightMoveCalculator.getMoves(board, position);
            case ROOK -> RookMoveCalculator.getMoves(board, position);
            case PAWN -> PawnMoveCalculator.getMoves(board, position);
        };
    }

    /**
     * String representation: uppercase letter for white, lowercase for black.
     */
    @Override
    public String toString() {
        return switch (pieceType) {
            case KING -> teamColor == ChessGame.TeamColor.WHITE ? "K" : "k";
            case QUEEN -> teamColor == ChessGame.TeamColor.WHITE ? "Q" : "q";
            case BISHOP -> teamColor == ChessGame.TeamColor.WHITE ? "B" : "b";
            case KNIGHT -> teamColor == ChessGame.TeamColor.WHITE ? "N" : "n";
            case ROOK -> teamColor == ChessGame.TeamColor.WHITE ? "R" : "r";
            case PAWN -> teamColor == ChessGame.TeamColor.WHITE ? "P" : "p";
        };
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ChessPiece other)) {
            return false;
        }
        return teamColor == other.teamColor && pieceType == other.pieceType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(teamColor, pieceType);
    }
}
