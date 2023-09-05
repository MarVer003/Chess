package com.example.chess;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.chess.Pieces.Bishop;
import com.example.chess.Pieces.King;
import com.example.chess.Pieces.Knight;
import com.example.chess.Pieces.Pawn;
import com.example.chess.Pieces.Piece;
import com.example.chess.Pieces.Queen;
import com.example.chess.Pieces.Rook;

public class MainActivity extends AppCompatActivity {

    TextView[][] backgroundBoard = new TextView[8][8];
    TextView[][] displayBoard = new TextView[8][8];
    Position[][] board = new Position[8][8];
    Coordinates clickedPosition = new Coordinates();
    Coordinates selectedPiecePos = new Coordinates();

    boolean whiteTurn;
    boolean somethingSelected;

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

    private void findViewByIds() {
        displayBoard[0][0] = findViewById(R.id.S00);
        displayBoard[0][1] = findViewById(R.id.S01);
        displayBoard[0][2] = findViewById(R.id.S02);
        displayBoard[0][3] = findViewById(R.id.S03);
        displayBoard[0][4] = findViewById(R.id.S04);
        displayBoard[0][5] = findViewById(R.id.S05);
        displayBoard[0][6] = findViewById(R.id.S06);
        displayBoard[0][7] = findViewById(R.id.S07);

        displayBoard[1][0] = findViewById(R.id.S10);
        displayBoard[1][1] = findViewById(R.id.S11);
        displayBoard[1][2] = findViewById(R.id.S12);
        displayBoard[1][3] = findViewById(R.id.S13);
        displayBoard[1][4] = findViewById(R.id.S14);
        displayBoard[1][5] = findViewById(R.id.S15);
        displayBoard[1][6] = findViewById(R.id.S16);
        displayBoard[1][7] = findViewById(R.id.S17);

        displayBoard[2][0] = findViewById(R.id.S20);
        displayBoard[2][1] = findViewById(R.id.S21);
        displayBoard[2][2] = findViewById(R.id.S22);
        displayBoard[2][3] = findViewById(R.id.S23);
        displayBoard[2][4] = findViewById(R.id.S24);
        displayBoard[2][5] = findViewById(R.id.S25);
        displayBoard[2][6] = findViewById(R.id.S26);
        displayBoard[2][7] = findViewById(R.id.S27);

        displayBoard[3][0] = findViewById(R.id.S30);
        displayBoard[3][1] = findViewById(R.id.S31);
        displayBoard[3][2] = findViewById(R.id.S32);
        displayBoard[3][3] = findViewById(R.id.S33);
        displayBoard[3][4] = findViewById(R.id.S34);
        displayBoard[3][5] = findViewById(R.id.S35);
        displayBoard[3][6] = findViewById(R.id.S36);
        displayBoard[3][7] = findViewById(R.id.S37);

        displayBoard[4][0] = findViewById(R.id.S40);
        displayBoard[4][1] = findViewById(R.id.S41);
        displayBoard[4][2] = findViewById(R.id.S42);
        displayBoard[4][3] = findViewById(R.id.S43);
        displayBoard[4][4] = findViewById(R.id.S44);
        displayBoard[4][5] = findViewById(R.id.S45);
        displayBoard[4][6] = findViewById(R.id.S46);
        displayBoard[4][7] = findViewById(R.id.S47);

        displayBoard[5][0] = findViewById(R.id.S50);
        displayBoard[5][1] = findViewById(R.id.S51);
        displayBoard[5][2] = findViewById(R.id.S52);
        displayBoard[5][3] = findViewById(R.id.S53);
        displayBoard[5][4] = findViewById(R.id.S54);
        displayBoard[5][5] = findViewById(R.id.S55);
        displayBoard[5][6] = findViewById(R.id.S56);
        displayBoard[5][7] = findViewById(R.id.S57);

        displayBoard[6][0] = findViewById(R.id.S60);
        displayBoard[6][1] = findViewById(R.id.S61);
        displayBoard[6][2] = findViewById(R.id.S62);
        displayBoard[6][3] = findViewById(R.id.S63);
        displayBoard[6][4] = findViewById(R.id.S64);
        displayBoard[6][5] = findViewById(R.id.S65);
        displayBoard[6][6] = findViewById(R.id.S66);
        displayBoard[6][7] = findViewById(R.id.S67);

        displayBoard[7][0] = findViewById(R.id.S70);
        displayBoard[7][1] = findViewById(R.id.S71);
        displayBoard[7][2] = findViewById(R.id.S72);
        displayBoard[7][3] = findViewById(R.id.S73);
        displayBoard[7][4] = findViewById(R.id.S74);
        displayBoard[7][5] = findViewById(R.id.S75);
        displayBoard[7][6] = findViewById(R.id.S76);
        displayBoard[7][7] = findViewById(R.id.S77);

        backgroundBoard[0][0] = findViewById(R.id.S000);
        backgroundBoard[0][1] = findViewById(R.id.S001);
        backgroundBoard[0][2] = findViewById(R.id.S002);
        backgroundBoard[0][3] = findViewById(R.id.S003);
        backgroundBoard[0][4] = findViewById(R.id.S004);
        backgroundBoard[0][5] = findViewById(R.id.S005);
        backgroundBoard[0][6] = findViewById(R.id.S006);
        backgroundBoard[0][7] = findViewById(R.id.S007);

        backgroundBoard[1][0] = findViewById(R.id.S010);
        backgroundBoard[1][1] = findViewById(R.id.S011);
        backgroundBoard[1][2] = findViewById(R.id.S012);
        backgroundBoard[1][3] = findViewById(R.id.S013);
        backgroundBoard[1][4] = findViewById(R.id.S014);
        backgroundBoard[1][5] = findViewById(R.id.S015);
        backgroundBoard[1][6] = findViewById(R.id.S016);
        backgroundBoard[1][7] = findViewById(R.id.S017);

        backgroundBoard[2][0] = findViewById(R.id.S020);
        backgroundBoard[2][1] = findViewById(R.id.S021);
        backgroundBoard[2][2] = findViewById(R.id.S022);
        backgroundBoard[2][3] = findViewById(R.id.S023);
        backgroundBoard[2][4] = findViewById(R.id.S024);
        backgroundBoard[2][5] = findViewById(R.id.S025);
        backgroundBoard[2][6] = findViewById(R.id.S026);
        backgroundBoard[2][7] = findViewById(R.id.S027);

        backgroundBoard[3][0] = findViewById(R.id.S030);
        backgroundBoard[3][1] = findViewById(R.id.S031);
        backgroundBoard[3][2] = findViewById(R.id.S032);
        backgroundBoard[3][3] = findViewById(R.id.S033);
        backgroundBoard[3][4] = findViewById(R.id.S034);
        backgroundBoard[3][5] = findViewById(R.id.S035);
        backgroundBoard[3][6] = findViewById(R.id.S036);
        backgroundBoard[3][7] = findViewById(R.id.S037);

        backgroundBoard[4][0] = findViewById(R.id.S040);
        backgroundBoard[4][1] = findViewById(R.id.S041);
        backgroundBoard[4][2] = findViewById(R.id.S042);
        backgroundBoard[4][3] = findViewById(R.id.S043);
        backgroundBoard[4][4] = findViewById(R.id.S044);
        backgroundBoard[4][5] = findViewById(R.id.S045);
        backgroundBoard[4][6] = findViewById(R.id.S046);
        backgroundBoard[4][7] = findViewById(R.id.S047);

        backgroundBoard[5][0] = findViewById(R.id.S050);
        backgroundBoard[5][1] = findViewById(R.id.S051);
        backgroundBoard[5][2] = findViewById(R.id.S052);
        backgroundBoard[5][3] = findViewById(R.id.S053);
        backgroundBoard[5][4] = findViewById(R.id.S054);
        backgroundBoard[5][5] = findViewById(R.id.S055);
        backgroundBoard[5][6] = findViewById(R.id.S056);
        backgroundBoard[5][7] = findViewById(R.id.S057);

        backgroundBoard[6][0] = findViewById(R.id.S060);
        backgroundBoard[6][1] = findViewById(R.id.S061);
        backgroundBoard[6][2] = findViewById(R.id.S062);
        backgroundBoard[6][3] = findViewById(R.id.S063);
        backgroundBoard[6][4] = findViewById(R.id.S064);
        backgroundBoard[6][5] = findViewById(R.id.S065);
        backgroundBoard[6][6] = findViewById(R.id.S066);
        backgroundBoard[6][7] = findViewById(R.id.S067);

        backgroundBoard[7][0] = findViewById(R.id.S070);
        backgroundBoard[7][1] = findViewById(R.id.S071);
        backgroundBoard[7][2] = findViewById(R.id.S072);
        backgroundBoard[7][3] = findViewById(R.id.S073);
        backgroundBoard[7][4] = findViewById(R.id.S074);
        backgroundBoard[7][5] = findViewById(R.id.S075);
        backgroundBoard[7][6] = findViewById(R.id.S076);
        backgroundBoard[7][7] = findViewById(R.id.S077);

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

        Piece tmpPiece = board[x][y].getPiece();
        if(tmpPiece != null) {
            if(selectedPiecePos != null)
                returnColor(selectedPiecePos.getX(), selectedPiecePos.getY());
            selectedPiecePos = clickedPosition;
            if(colorWhite(x, y))
                backgroundBoard[x][y].setBackgroundColor(getColor(R.color.white_selected_square));
            else
                backgroundBoard[x][y].setBackgroundColor(getColor(R.color.black_selected_square));

        } else if(selectedPiecePos != null) {
            board[x][y].setPiece(board[selectedPiecePos.getX()][selectedPiecePos.getY()].getPiece());
            refreshBoard(selectedPiecePos, clickedPosition);

            returnColor(selectedPiecePos.getX(), selectedPiecePos.getY());
            selectedPiecePos = null;
        }
    }

    private void returnColor(int x, int y) {

        if(colorWhite(x, y))
            backgroundBoard[x][y].setBackgroundColor(getColor(R.color.white_square));
        else
            backgroundBoard[x][y].setBackgroundColor(getColor(R.color.black_square));
    }

    private boolean colorWhite(int x, int y) {
        if(x % 2 == 0) {
            return y % 2 != 0;
        }
        else {
            return y % 2 == 0;
        }
    }

    private void refreshBoard(Coordinates beforePos, Coordinates afterPos) {
        Piece tmpPiece = board[beforePos.getX()][beforePos.getY()].getPiece();

        board[beforePos.getX()][beforePos.getY()].setPiece(null);
        displayBoard[beforePos.getX()][beforePos.getY()].setBackgroundResource(0);

        board[afterPos.getX()][afterPos.getY()].setPiece(tmpPiece);
        displayBoard[afterPos.getX()][afterPos.getY()].setBackgroundResource(pieceDisplay(tmpPiece));

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
















