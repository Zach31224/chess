package chess;

import java.util.Collection;
import java.util.HashSet;

/**
 * For a class that can manage a chess game, making moves on a board
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessGame {

    private TeamColor teamTurn;
    private ChessBoard board;

    public ChessGame() {
        board = new ChessBoard();
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
     * Gets a valid moves for a piece at the given location
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
            board.addPiece(startPosition, null);
            board.addPiece(move.getEndPosition(), selectedPiece);

            if (!isInCheck(selectedPiece.getTeamColor())) {
                safeMoves.add(move);
            }

            // Revert move
            board.addPiece(move.getEndPosition(), capturedPiece);
            board.addPiece(startPosition, selectedPiece);
        }

        return safeMoves;
    }

    /**
     * Makes a move in a chess game
     *
     * @param move chess move to preform
     * @throws InvalidMoveException if move is invalid
     */
    public void makeMove(ChessMove move) throws InvalidMoveException {
        boolean isCorrectTurn = getTeamTurn() == board.getTeamOfSquare(move.getStartPosition());
        Collection<ChessMove> legalMoves = validMoves(move.getStartPosition());

        if (legalMoves == null) {
            throw new InvalidMoveException("No moves possible from this position.");
        }

        boolean moveIsAllowed = legalMoves.contains(move);

        if (moveIsAllowed && isCorrectTurn) {
            ChessPiece movingPiece = board.getPiece(move.getStartPosition());

            if (move.getPromotionPiece() != null) {
                movingPiece = new ChessPiece(movingPiece.getTeamColor(), move.getPromotionPiece());
            }

            board.addPiece(move.getStartPosition(), null);
            board.addPiece(move.getEndPosition(), movingPiece);

            setTeamTurn(getTeamTurn() == TeamColor.WHITE ? TeamColor.BLACK : TeamColor.WHITE);
        } else {
            throw new InvalidMoveException(String.format("Valid move: %b  Your Turn: %b", moveIsAllowed, isCorrectTurn));
        }
    }

    /**
     * Determines if the given team is in check
     *
     * @param teamColor which team to check for check
     * @return True if the specified team is in check
     */
    public boolean isInCheck(TeamColor teamColor) {
        ChessPosition kingLocation = null;

        // Find king
        for (int row = 1; row <= 8 && kingLocation == null; row++) {
            for (int col = 1; col <= 8 && kingLocation == null; col++) {
                ChessPiece piece = board.getPiece(new ChessPosition(row, col));
                if (piece != null &&
                        piece.getTeamColor() == teamColor &&
                        piece.getPieceType() == ChessPiece.PieceType.KING) {
                    kingLocation = new ChessPosition(row, col);
                }
            }
        }

        // Look for threats to the king
        for (int row = 1; row <= 8; row++) {
            for (int col = 1; col <= 8; col++) {
                ChessPiece attacker = board.getPiece(new ChessPosition(row, col));
                if (attacker == null || attacker.getTeamColor() == teamColor) {
                    continue;
                }
                for (ChessMove move : attacker.pieceMoves(board, new ChessPosition(row, col))) {
                    if (move.getEndPosition().equals(kingLocation)) {
                        return true;
                    }
                }
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
        return isInCheck(teamColor) && isInStalemate(teamColor);
    }

    /**
     * Determines if the given team is in stalemate, which here is defined as having
     * no valid moves
     *
     * @param teamColor which team to check for stalemate
     * @return True if the specified team is in stalemate, otherwise false
     */
    public boolean isInStalemate(TeamColor teamColor) {
        for (int row = 1; row <= 8; row++) {
            for (int col = 1; col <= 8; col++) {
                ChessPosition position = new ChessPosition(row, col);
                ChessPiece piece = board.getPiece(position);

                if (piece != null && piece.getTeamColor() == teamColor) {
                    Collection<ChessMove> moves = validMoves(position);
                    if (moves != null && !moves.isEmpty()) {
                        return false;
                    }
                }
            }
        }
        return true;
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
}
