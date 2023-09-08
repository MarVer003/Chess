package com.example.chess.Pieces;

import com.example.chess.Coordinates;
import com.example.chess.Position;

import java.util.ArrayList;

public class Rook extends Piece {

    public Rook(boolean white) {
        super(white);
    }

    @Override
    public ArrayList<Coordinates> allowedMoves(Position[][] board, Coordinates coordinates) {
        return null;
    }
}
