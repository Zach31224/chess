package chess;

import chess.ChessGame;
import chess.ChessPiece;
import chess.ChessPosition;

import java.util.Arrays;

public class ChessBoard {

    private ChessPiece[][] layout;

    public ChessBoard() {
        layout = new ChessPiece[8][8];
    }

    public void addPiece(ChessPosition pos, ChessPiece piece) {
        layout[pos.getColumn() - 1][pos.getRow() - 1] = piece;
    }

    public ChessPiece getPiece(ChessPosition pos) {
        return layout[pos.getColumn() - 1][pos.getRow() - 1];
    }

    public ChessGame.TeamColor getTeamOfSquare(ChessPosition pos) {
        ChessPiece p = getPiece(pos);
        return (p != null) ? p.getTeamColor() : null;
    }

    public void resetBoard() {
        layout = new ChessPiece[8][8];

        for (int i = 1; i <= 8; i++) {
            addPiece(new ChessPosition(2, i), new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.PAWN));
            addPiece(new ChessPosition(7, i), new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.PAWN));
        }

        setupBackRank(1, ChessGame.TeamColor.WHITE);
        setupBackRank(8, ChessGame.TeamColor.BLACK);
    }

    private void setupBackRank(int row, ChessGame.TeamColor team) {
        ChessPiece.PieceType[] pieces = {
                ChessPiece.PieceType.ROOK, ChessPiece.PieceType.KNIGHT, ChessPiece.PieceType.BISHOP,
                ChessPiece.PieceType.QUEEN, ChessPiece.PieceType.KING, ChessPiece.PieceType.BISHOP,
                ChessPiece.PieceType.KNIGHT, ChessPiece.PieceType.ROOK
        };
        for (int col = 1; col <= 8; col++) {
            addPiece(new ChessPosition(row, col), new ChessPiece(team, pieces[col - 1]));
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int r = 7; r >= 0; r--) {
            sb.append("|");
            for (int c = 0; c < 8; c++) {
                sb.append(layout[c][r] != null ? layout[c][r].toString() : " ");
                sb.append("|");
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof ChessBoard other)) {
            return false;
        }
        return Arrays.deepEquals(layout, other.layout);
    }

    @Override
    public int hashCode() {
        return Arrays.deepHashCode(layout);
    }
}
