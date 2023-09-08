package com.example.chess.Pieces;

import com.example.chess.Coordinates;
import com.example.chess.Position;

import java.util.ArrayList;

public class King extends Piece {

    public King(boolean white) {
        super(white);
    }

    @Override
    public ArrayList<Coordinates> allowedMoves(Position[][] board, Coordinates coordinates) {
        return null;
    }
}
