import chess.*;
import server.Server;

public class Main {

    public static void main(String[] args) {
        ChessPiece piece = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.PAWN);
        System.out.println("♕ 240 Chess Server: " + piece);

        Server server = new Server();
        int port = server.run(8080);
    }
}
