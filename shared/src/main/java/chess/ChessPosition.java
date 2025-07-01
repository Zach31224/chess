package chess;

import java.util.Objects;

public class ChessPosition {

    private final int rank;
    private final int file;

    public ChessPosition(int rank, int file) {
        this.rank = rank;
        this.file = file;
    }

    public int getRow() {
        return rank;
    }

    public int getColumn() {
        return file;
    }

    @Override
    public String toString() {
        return "(" + rank + ", " + file + ")";
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof ChessPosition other)) return false;
        return rank == other.rank && file == other.file;
    }

    @Override
    public int hashCode() {
        return Objects.hash(rank, file);
    }
}
