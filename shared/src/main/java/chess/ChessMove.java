package chess;

import chess.ChessPiece;
import chess.ChessPosition;

import java.util.Objects;

public class ChessMove {

    private final ChessPosition from;
    private final ChessPosition to;
    private final ChessPiece.PieceType promoteTo;

    public ChessMove(ChessPosition from, ChessPosition to, ChessPiece.PieceType promoteTo) {
        this.from = from;
        this.to = to;
        this.promoteTo = promoteTo;
    }

    public ChessPosition getStartPosition() {
        return from;
    }

    public ChessPosition getEndPosition() {
        return to;
    }

    public ChessPiece.PieceType getPromotionPiece() {
        return promoteTo;
    }

    @Override
    public String toString() {
        return "Move{" + "from=" + from + ", to=" + to + ", promotion=" + promoteTo + '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof ChessMove other)) return false;
        return Objects.equals(from, other.from) &&
                Objects.equals(to, other.to) &&
                promoteTo == other.promoteTo;
    }

    @Override
    public int hashCode() {
        return Objects.hash(from, to, promoteTo);
    }
}
