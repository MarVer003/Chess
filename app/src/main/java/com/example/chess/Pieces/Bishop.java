package com.example.chess.Pieces;

import com.example.chess.Coordinates;
import com.example.chess.Position;

import java.util.ArrayList;

public class Bishop extends Piece {

    public Bishop(boolean white) {
        super(white);
    }

    @Override
    public ArrayList<Coordinates> allowedMoves(Position[][] board, Coordinates coordinates) {
        return null;
    }
}
