package com.example.chess;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.chess.Pieces.Bishop;
import com.example.chess.Pieces.King;
import com.example.chess.Pieces.Knight;
import com.example.chess.Pieces.Pawn;
import com.example.chess.Pieces.Piece;
import com.example.chess.Pieces.Queen;
import com.example.chess.Pieces.Rook;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    TextView[][] backgroundBoard = new TextView[8][8];
    TextView[][] displayBoard = new TextView[8][8];
    Position[][] board = new Position[8][8];
    Coordinates clickedPosition = new Coordinates();
    Coordinates selectedPiecePos;

    public static boolean whiteTurn;

    Piece w_king;
    Piece b_king;

    Piece w_queen;
    Piece b_queen;

    Piece w_rook1;
    Piece w_rook2;
    Piece b_rook1;
    Piece b_rook2;

    Piece w_knight1;
    Piece w_knight2;
    Piece b_knight1;
    Piece b_knight2;

    Piece w_bishop1;
    Piece w_bishop2;
    Piece b_bishop1;
    Piece b_bishop2;

    Piece w_pawn1;
    Piece w_pawn2;
    Piece w_pawn3;
    Piece w_pawn4;
    Piece w_pawn5;
    Piece w_pawn6;
    Piece w_pawn7;
    Piece w_pawn8;

    Piece b_pawn1;
    Piece b_pawn2;
    Piece b_pawn3;
    Piece b_pawn4;
    Piece b_pawn5;
    Piece b_pawn6;
    Piece b_pawn7;
    Piece b_pawn8;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewByIds();
        InitializeBoard();
    }

    @SuppressLint("DiscouragedApi")
    private void findViewByIds() {

        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {
                int viewId = getResources().getIdentifier("S" + x + y, "id", getPackageName());
                displayBoard[x][y] = findViewById(viewId);

                int backgroundViewId = getResources().getIdentifier("S0" + x + y, "id", getPackageName());
                backgroundBoard[x][y] = findViewById(backgroundViewId);

                if(viewId == 0 || backgroundViewId == 0)
                    Toast.makeText(this, "Something is not right", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void InitializeBoard() {

        // white
        w_pawn1 = new Pawn(true);
        w_pawn2 = new Pawn(true);
        w_pawn3 = new Pawn(true);
        w_pawn4 = new Pawn(true);
        w_pawn5 = new Pawn(true);
        w_pawn6 = new Pawn(true);
        w_pawn7 = new Pawn(true);
        w_pawn8 = new Pawn(true);

        w_rook1 = new Rook(true);
        w_rook2 = new Rook(true);

        w_knight1 = new Knight(true);
        w_knight2 = new Knight(true);

        w_bishop1 = new Bishop(true);
        w_bishop2 = new Bishop(true);

        w_king = new King(true);
        w_queen = new Queen(true);
        //

        // black
        b_pawn1 = new Pawn(false);
        b_pawn2 = new Pawn(false);
        b_pawn3 = new Pawn(false);
        b_pawn4 = new Pawn(false);
        b_pawn5 = new Pawn(false);
        b_pawn6 = new Pawn(false);
        b_pawn7 = new Pawn(false);
        b_pawn8 = new Pawn(false);

        b_rook1 = new Rook(false);
        b_rook2 = new Rook(false);

        b_knight1 = new Knight(false);
        b_knight2 = new Knight(false);

        b_bishop1 = new Bishop(false);
        b_bishop2 = new Bishop(false);

        b_king = new King(false);
        b_queen = new Queen(false);
        //

        for(int i = 0; i < 8; i++) {
            for(int j = 0; j < 8; j++) {
                board[i][j] = new Position(null);
            }
        }

        // black
        board[0][0].setPiece(b_rook1);
        board[1][0].setPiece(b_knight1);
        board[2][0].setPiece(b_bishop1);
        board[3][0].setPiece(b_king);
        board[4][0].setPiece(b_queen);
        board[5][0].setPiece(b_bishop2);
        board[6][0].setPiece(b_knight2);
        board[7][0].setPiece(b_rook2);
        board[0][0].setPiece(b_rook1);
        board[0][1].setPiece(b_pawn1);
        board[1][1].setPiece(b_pawn2);
        board[2][1].setPiece(b_pawn3);
        board[3][1].setPiece(b_pawn4);
        board[4][1].setPiece(b_pawn5);
        board[5][1].setPiece(b_pawn6);
        board[6][1].setPiece(b_pawn7);
        board[7][1].setPiece(b_pawn8);

        // white
        board[0][7].setPiece(w_rook1);
        board[1][7].setPiece(w_knight1);
        board[2][7].setPiece(w_bishop1);
        board[3][7].setPiece(w_king);
        board[4][7].setPiece(w_queen);
        board[5][7].setPiece(w_bishop2);
        board[6][7].setPiece(w_knight2);
        board[7][7].setPiece(w_rook2);
        board[0][7].setPiece(w_rook1);
        board[0][6].setPiece(w_pawn1);
        board[1][6].setPiece(w_pawn2);
        board[2][6].setPiece(w_pawn3);
        board[3][6].setPiece(w_pawn4);
        board[4][6].setPiece(w_pawn5);
        board[5][6].setPiece(w_pawn6);
        board[6][6].setPiece(w_pawn7);
        board[7][6].setPiece(w_pawn8);

        for(int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Piece tmp = board[i][j].getPiece();
                displayBoard[i][j].setBackgroundResource(pieceDisplay(tmp));
            }
        }
        whiteTurn = true;
    }

    @SuppressLint("NonConstantResourceId")
    public void onClick(View view) {


        clickedPosition = new Coordinates();
        switch (view.getId()) {
            case R.id.S00:
                clickedPosition.setX(0);
                clickedPosition.setY(0);
                break;
            case R.id.S10:
                clickedPosition.setX(1);
                clickedPosition.setY(0);
                break;
            case R.id.S20:
                clickedPosition.setX(2);
                clickedPosition.setY(0);
                break;
            case R.id.S30:
                clickedPosition.setX(3);
                clickedPosition.setY(0);
                break;
            case R.id.S40:
                clickedPosition.setX(4);
                clickedPosition.setY(0);
                break;
            case R.id.S50:
                clickedPosition.setX(5);
                clickedPosition.setY(0);
                break;
            case R.id.S60:
                clickedPosition.setX(6);
                clickedPosition.setY(0);
                break;
            case R.id.S70:
                clickedPosition.setX(7);
                clickedPosition.setY(0);
                break;
            case R.id.S01:
                clickedPosition.setX(0);
                clickedPosition.setY(1);
                break;
            case R.id.S11:
                clickedPosition.setX(1);
                clickedPosition.setY(1);
                break;
            case R.id.S21:
                clickedPosition.setX(2);
                clickedPosition.setY(1);
                break;
            case R.id.S31:
                clickedPosition.setX(3);
                clickedPosition.setY(1);
                break;
            case R.id.S41:
                clickedPosition.setX(4);
                clickedPosition.setY(1);
                break;
            case R.id.S51:
                clickedPosition.setX(5);
                clickedPosition.setY(1);
                break;
            case R.id.S61:
                clickedPosition.setX(6);
                clickedPosition.setY(1);
                break;
            case R.id.S71:
                clickedPosition.setX(7);
                clickedPosition.setY(1);
                break;
            case R.id.S02:
                clickedPosition.setX(0);
                clickedPosition.setY(2);
                break;
            case R.id.S12:
                clickedPosition.setX(1);
                clickedPosition.setY(2);
                break;
            case R.id.S22:
                clickedPosition.setX(2);
                clickedPosition.setY(2);
                break;
            case R.id.S32:
                clickedPosition.setX(3);
                clickedPosition.setY(2);
                break;
            case R.id.S42:
                clickedPosition.setX(4);
                clickedPosition.setY(2);
                break;
            case R.id.S52:
                clickedPosition.setX(5);
                clickedPosition.setY(2);
                break;
            case R.id.S62:
                clickedPosition.setX(6);
                clickedPosition.setY(2);
                break;
            case R.id.S72:
                clickedPosition.setX(7);
                clickedPosition.setY(2);
                break;
            case R.id.S03:
                clickedPosition.setX(0);
                clickedPosition.setY(3);
                break;
            case R.id.S13:
                clickedPosition.setX(1);
                clickedPosition.setY(3);
                break;
            case R.id.S23:
                clickedPosition.setX(2);
                clickedPosition.setY(3);
                break;
            case R.id.S33:
                clickedPosition.setX(3);
                clickedPosition.setY(3);
                break;
            case R.id.S43:
                clickedPosition.setX(4);
                clickedPosition.setY(3);
                break;
            case R.id.S53:
                clickedPosition.setX(5);
                clickedPosition.setY(3);
                break;
            case R.id.S63:
                clickedPosition.setX(6);
                clickedPosition.setY(3);
                break;
            case R.id.S73:
                clickedPosition.setX(7);
                clickedPosition.setY(3);
                break;
            case R.id.S04:
                clickedPosition.setX(0);
                clickedPosition.setY(4);
                break;
            case R.id.S14:
                clickedPosition.setX(1);
                clickedPosition.setY(4);
                break;
            case R.id.S24:
                clickedPosition.setX(2);
                clickedPosition.setY(4);
                break;
            case R.id.S34:
                clickedPosition.setX(3);
                clickedPosition.setY(4);
                break;
            case R.id.S44:
                clickedPosition.setX(4);
                clickedPosition.setY(4);
                break;
            case R.id.S54:
                clickedPosition.setX(5);
                clickedPosition.setY(4);
                break;
            case R.id.S64:
                clickedPosition.setX(6);
                clickedPosition.setY(4);
                break;
            case R.id.S74:
                clickedPosition.setX(7);
                clickedPosition.setY(4);
                break;
            case R.id.S05:
                clickedPosition.setX(0);
                clickedPosition.setY(5);
                break;
            case R.id.S15:
                clickedPosition.setX(1);
                clickedPosition.setY(5);
                break;
            case R.id.S25:
                clickedPosition.setX(2);
                clickedPosition.setY(5);
                break;
            case R.id.S35:
                clickedPosition.setX(3);
                clickedPosition.setY(5);
                break;
            case R.id.S45:
                clickedPosition.setX(4);
                clickedPosition.setY(5);
                break;
            case R.id.S55:
                clickedPosition.setX(5);
                clickedPosition.setY(5);
                break;
            case R.id.S65:
                clickedPosition.setX(6);
                clickedPosition.setY(5);
                break;
            case R.id.S75:
                clickedPosition.setX(7);
                clickedPosition.setY(5);
                break;
            case R.id.S06:
                clickedPosition.setX(0);
                clickedPosition.setY(6);
                break;
            case R.id.S16:
                clickedPosition.setX(1);
                clickedPosition.setY(6);
                break;
            case R.id.S26:
                clickedPosition.setX(2);
                clickedPosition.setY(6);
                break;
            case R.id.S36:
                clickedPosition.setX(3);
                clickedPosition.setY(6);
                break;
            case R.id.S46:
                clickedPosition.setX(4);
                clickedPosition.setY(6);
                break;
            case R.id.S56:
                clickedPosition.setX(5);
                clickedPosition.setY(6);
                break;
            case R.id.S66:
                clickedPosition.setX(6);
                clickedPosition.setY(6);
                break;
            case R.id.S76:
                clickedPosition.setX(7);
                clickedPosition.setY(6);
                break;
            case R.id.S07:
                clickedPosition.setX(0);
                clickedPosition.setY(7);
                break;
            case R.id.S17:
                clickedPosition.setX(1);
                clickedPosition.setY(7);
                break;
            case R.id.S27:
                clickedPosition.setX(2);
                clickedPosition.setY(7);
                break;
            case R.id.S37:
                clickedPosition.setX(3);
                clickedPosition.setY(7);
                break;
            case R.id.S47:
                clickedPosition.setX(4);
                clickedPosition.setY(7);
                break;
            case R.id.S57:
                clickedPosition.setX(5);
                clickedPosition.setY(7);
                break;
            case R.id.S67:
                clickedPosition.setX(6);
                clickedPosition.setY(7);
                break;
            case R.id.S77:
                clickedPosition.setX(7);
                clickedPosition.setY(7);
                break;
        }

        int x = clickedPosition.getX();
        int y = clickedPosition.getY();
        Position pressedSquare = board[x][y];

        if(pressedSquare.getPiece() != null && (whiteTurn == pressedSquare.getPiece().isWhite())) {

            if(selectedPiecePos != null)
                returnColor(selectedPiecePos.getX(), selectedPiecePos.getY());
            selectedPiecePos = clickedPosition;
            if(squareWhite(x, y))
                backgroundBoard[x][y].setBackgroundColor(getColor(R.color.white_selected_square));
            else
                backgroundBoard[x][y].setBackgroundColor(getColor(R.color.black_selected_square));

        } else if(selectedPiecePos != null) {

            if(movePiece(selectedPiecePos, clickedPosition)) {

                whiteTurn = !whiteTurn;

            }

            returnColor(selectedPiecePos.getX(), selectedPiecePos.getY());
            selectedPiecePos = null;
        }
    }

    private void returnColor(int x, int y) {

        if(squareWhite(x, y))
            backgroundBoard[x][y].setBackgroundColor(getColor(R.color.white_square));
        else
            backgroundBoard[x][y].setBackgroundColor(getColor(R.color.black_square));
    }

    private boolean squareWhite(int x, int y) {
        if(x % 2 == 0) {
            return y % 2 != 0;
        }
        else {
            return y % 2 == 0;
        }
    }

    private boolean movePiece(Coordinates beforePos, Coordinates afterPos) {
        Piece tmpPiece = board[beforePos.getX()][beforePos.getY()].getPiece();

        ArrayList<Coordinates> allowedMoves = tmpPiece.allowedMoves(board, beforePos);

        if(allowedMoves != null) {
            for (Coordinates move : allowedMoves) {
                if (move.getX() == afterPos.getX() && move.getY() == afterPos.getY()) {
                    board[beforePos.getX()][beforePos.getY()].setPiece(null);
                    displayBoard[beforePos.getX()][beforePos.getY()].setBackgroundResource(0);

                    board[afterPos.getX()][afterPos.getY()].setPiece(tmpPiece);
                    displayBoard[afterPos.getX()][afterPos.getY()].setBackgroundResource(pieceDisplay(tmpPiece));

                    return true;
                }
            }
        }
        return false;
    }

    private int pieceDisplay(Piece piece) {

        if(piece instanceof Pawn) {
            if(piece.isWhite()) return R.drawable.w_pawn;
            else return R.drawable.b_pawn;
        }
        if(piece instanceof Rook) {
            if(piece.isWhite()) return R.drawable.w_rook;
            else return R.drawable.b_rook;
        }
        if(piece instanceof Knight) {
            if(piece.isWhite()) return R.drawable.w_knight;
            else return R.drawable.b_knight;
        }
        if(piece instanceof Bishop) {
            if(piece.isWhite()) return R.drawable.w_bishop;
            else return R.drawable.b_bishop;
        }
        if(piece instanceof King) {
            if(piece.isWhite()) return R.drawable.w_king;
            else return R.drawable.b_king;
        }
        if(piece instanceof Queen) {
            if(piece.isWhite()) return R.drawable.w_queen;
            else return R.drawable.b_queen;
        }
        return 0;
    }
}