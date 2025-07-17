package chess;

import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;

/**
 * Manages the logic of a chess game, including moves, turns, check, checkmate, and stalemate.
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessGame {

    private TeamColor teamTurn;
    private ChessBoard board;

    public ChessGame() {
        board = new ChessBoard();
        board.resetBoard();  // Initialize to standard chess starting position
        setTeamTurn(TeamColor.WHITE);
    }

    /**
     * @return Which team's turn it is
     */
    public TeamColor getTeamTurn() {
        return teamTurn;
    }

    /**
     * Set's which teams turn it is
     *
     * @param team the team whose turn it is
     */
    public void setTeamTurn(TeamColor team) {
        teamTurn = team;
    }

    /**
     * Enum identifying the 2 possible teams in a chess game
     */
    public enum TeamColor {
        WHITE,
        BLACK
    }

    /**
     * Gets all valid and legal moves for a piece at the given location
     *
     * @param startPosition the piece to get valid moves for
     * @return Set of valid moves for requested piece, or null if no piece at
     * startPosition
     */
    public Collection<ChessMove> validMoves(ChessPosition startPosition) {
        ChessPiece selectedPiece = board.getPiece(startPosition);
        if (selectedPiece == null) {
            return null;
        }

        HashSet<ChessMove> potentialMoves = (HashSet<ChessMove>) selectedPiece.pieceMoves(board, startPosition);
        HashSet<ChessMove> safeMoves = HashSet.newHashSet(potentialMoves.size());

        for (ChessMove move : potentialMoves) {
            ChessPiece capturedPiece = board.getPiece(move.getEndPosition());

            // Simulate move
            board.addPiece(move.getStartPosition(), null);
            board.addPiece(move.getEndPosition(), selectedPiece);

            if (!isInCheck(selectedPiece.getTeamColor())) {
                safeMoves.add(move);
            }

            // Revert move
            board.addPiece(move.getEndPosition(), capturedPiece);
            board.addPiece(move.getStartPosition(), selectedPiece);
        }

        return safeMoves;
    }

    /**
     * Makes a move in a chess game
     *
     * @param move chess move to perform
     * @throws InvalidMoveException if move is invalid
     */
    public void makeMove(ChessMove move) throws InvalidMoveException {
        ChessPiece movingPiece = board.getPiece(move.getStartPosition());
        if (movingPiece == null) {
            throw new InvalidMoveException("No piece at starting position");
        }

        boolean isCorrectTurn = (getTeamTurn() == movingPiece.getTeamColor());
        Collection<ChessMove> legalMoves = validMoves(move.getStartPosition());

        if (legalMoves == null || legalMoves.isEmpty()) {
            throw new InvalidMoveException("No moves possible from this position");
        }

        boolean moveIsAllowed = legalMoves.contains(move);

        if (moveIsAllowed && isCorrectTurn) {
            // Handle promotion
            if (move.getPromotionPiece() != null) {
                movingPiece = new ChessPiece(movingPiece.getTeamColor(), move.getPromotionPiece());
            }

            // Execute move
            board.addPiece(move.getStartPosition(), null);
            board.addPiece(move.getEndPosition(), movingPiece);

            // Switch turns
            setTeamTurn(getTeamTurn() == TeamColor.WHITE ? TeamColor.BLACK : TeamColor.WHITE);
        } else {
            throw new InvalidMoveException(
                    String.format("Invalid move. Valid move: %b, Correct turn: %b",
                            moveIsAllowed, isCorrectTurn)
            );
        }
    }

    /**
     * Determines if the given team is in check
     *
     * @param teamColor which team to check for check
     * @return True if the specified team is in check
     */
    public boolean isInCheck(TeamColor teamColor) {
        ChessPosition kingLocation = findKingPosition(teamColor);
        if (kingLocation == null) {
            return false;
        }

        // Check if any opponent piece can attack the king
        for (int row = 1; row <= 8; row++) {
            for (int col = 1; col <= 8; col++) {
                ChessPosition position = new ChessPosition(row, col);
                ChessPiece piece = board.getPiece(position);

                if (piece != null && piece.getTeamColor() != teamColor) {
                    if (canReachKing(position, piece, kingLocation)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * Helper method to check if a piece can reach the king
     */
    private boolean canReachKing(ChessPosition position, ChessPiece piece, ChessPosition kingLocation) {
        Collection<ChessMove> moves = piece.pieceMoves(board, position);
        for (ChessMove move : moves) {
            if (move.getEndPosition().equals(kingLocation)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Determines if the given team is in checkmate
     *
     * @param teamColor which team to check for checkmate
     * @return True if the specified team is in checkmate
     */
    public boolean isInCheckmate(TeamColor teamColor) {
        return isInCheck(teamColor) && !hasAnyValidMoves(teamColor);
    }

    /**
     * Determines if the given team is in stalemate, which here is defined as having
     * no valid moves
     *
     * @param teamColor which team to check for stalemate
     * @return True if the specified team is in stalemate, otherwise false
     */
    public boolean isInStalemate(TeamColor teamColor) {
        return !isInCheck(teamColor) && !hasAnyValidMoves(teamColor);
    }

    /**
     * Checks if the specified team has any valid moves remaining
     * @param teamColor team to check
     * @return true if team has at least one valid move, false otherwise
     */
    private boolean hasAnyValidMoves(TeamColor teamColor) {
        for (int row = 1; row <= 8; row++) {
            for (int col = 1; col <= 8; col++) {
                ChessPosition position = new ChessPosition(row, col);
                ChessPiece piece = board.getPiece(position);

                if (piece != null && piece.getTeamColor() == teamColor) {
                    Collection<ChessMove> moves = validMoves(position);
                    if (moves != null && !moves.isEmpty()) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * Helper method to find king's position
     */
    private ChessPosition findKingPosition(TeamColor teamColor) {
        for (int row = 1; row <= 8; row++) {
            for (int col = 1; col <= 8; col++) {
                ChessPosition position = new ChessPosition(row, col);
                ChessPiece piece = board.getPiece(position);
                if (piece != null &&
                        piece.getTeamColor() == teamColor &&
                        piece.getPieceType() == ChessPiece.PieceType.KING) {
                    return position;
                }
            }
        }
        return null;
    }

    /**
     * Sets this game's chessboard with a given board
     *
     * @param board the new board to use
     */
    public void setBoard(ChessBoard board) {
        this.board = board;
    }

    /**
     * Gets the current chessboard
     *
     * @return the chessboard
     */
    public ChessBoard getBoard() {
        return board;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ChessGame chessGame = (ChessGame) o;
        return teamTurn == chessGame.teamTurn &&
                Objects.equals(board, chessGame.board);
    }

    @Override
    public int hashCode() {
        return Objects.hash(teamTurn, board);
    }
}
