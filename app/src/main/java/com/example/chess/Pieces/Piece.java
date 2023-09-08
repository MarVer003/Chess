package com.example.chess.Pieces;

import java.util.ArrayList;

import com.example.chess.Coordinates;
import com.example.chess.Position;

public abstract class Piece {

    private final boolean WHITE;

    public Piece(boolean white) {
        this.WHITE = white;
    }

    public boolean isWhite() {
        return WHITE;
    }

    public abstract ArrayList<Coordinates> allowedMoves(Position[][] board, Coordinates coordinates);

}
