package com.example.chess.Pieces;

import java.util.ArrayList;

import com.example.chess.Coordinates;
import com.example.chess.Position;

public abstract class Piece {

    private final boolean WHITE;
    private Coordinates piecePostion;

    public Piece(boolean white) {
        this.WHITE = white;
    }

    public Piece(boolean white, int x, int y) {
        this.WHITE = white;
        this.piecePostion = new Coordinates(x, y);
    }

    public boolean isWhite() {
        return WHITE;
    }

    public abstract ArrayList<Coordinates> allowedMoves(Position[][] board, Coordinates coordinates);

}
