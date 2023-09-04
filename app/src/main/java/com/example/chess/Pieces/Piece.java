package com.example.chess.Pieces;

import com.example.chess.Coordinates;

public class Piece {

    private boolean white;

    public Piece(boolean white) {
        this.white = white;
    }

    public boolean isWhite() {
        return white;
    }

}
