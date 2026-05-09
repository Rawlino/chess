package chess;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

import static chess.ChessPiece.PieceType.*;


/**
 * A class that can manage a chess game, making moves on a board
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessGame {

    private ChessBoard masterBoard;
    private TeamColor teamTurn;

    public ChessGame() {
        masterBoard = new ChessBoard();
        masterBoard.resetBoard();
        teamTurn = TeamColor.WHITE;
    }

    /**
     * @return Which team's turn it is
     */
    public TeamColor getTeamTurn() {
        return teamTurn;
    }

    /**
     * Sets which teams turn it is
     *
     * @param team the team whose turn it is
     */
    public void setTeamTurn(TeamColor team) {
        teamTurn = team;
    }

    /**
     * Enum identifying the 2 possible teams in a chess game
     */
    public enum TeamColor {
        WHITE,
        BLACK
    }

    /**
     * Gets all valid moves for a piece at the given location
     *
     * @param startPosition the piece to get valid moves for
     * @return Set of valid moves for requested piece, or null if no piece at
     * startPosition
     */
    public Collection<ChessMove> validMoves(ChessPosition startPosition) {
        //Testing function now
        ChessPiece piece =  masterBoard.getPiece(startPosition);
        ArrayList<ChessMove> legalMoves = new ArrayList<>();

        if (piece == null) {
            return null;
        } else {
            Collection<ChessMove> moves = piece.pieceMoves(masterBoard, startPosition);

            for (ChessMove move : moves) {
                ChessBoard copy = copyBoard(masterBoard);

                copy.addPiece(move.getStartPosition(), null);
                copy.addPiece(move.getEndPosition(), piece);

                if (isInCheck(piece.getTeamColor())) {
                    //pass
                } else {
                    legalMoves.add(move);
                }
            }
        }
        return legalMoves;
    }

    /**
     * Makes a move in the chess game
     *
     * @param move chess move to perform
     * @throws InvalidMoveException if move is invalid
     */
    public void makeMove(ChessMove move) throws InvalidMoveException {
        ChessPiece piece =  masterBoard.getPiece(move.getStartPosition());

        if (piece == null) {
            throw new InvalidMoveException("Piece doesn't exist");
        } else if (piece.getTeamColor() != teamTurn) {
            throw new InvalidMoveException("Not this team's turn");
        } else {
            Collection<ChessMove> legalMoves = validMoves(move.getStartPosition());
            if (legalMoves.contains(move)) {
                masterBoard.addPiece(move.getStartPosition(), null);
                if (move.getPromotionPiece() != null) {
                    if (move.getPromotionPiece() == QUEEN) {
                        masterBoard.addPiece(move.getEndPosition(), new ChessPiece(piece.getTeamColor(), QUEEN));
                        if (teamTurn == TeamColor.WHITE) {
                            teamTurn = TeamColor.BLACK;
                        } else {
                            teamTurn = TeamColor.WHITE;
                        }
                    } else if (move.getPromotionPiece() ==  BISHOP) {
                        masterBoard.addPiece(move.getEndPosition(), new ChessPiece(piece.getTeamColor(), BISHOP));
                        if (teamTurn == TeamColor.WHITE) {
                            teamTurn = TeamColor.BLACK;
                        } else {
                            teamTurn = TeamColor.WHITE;
                        }
                    } else if (move.getPromotionPiece() ==  KNIGHT) {
                        masterBoard.addPiece(move.getEndPosition(), new ChessPiece(piece.getTeamColor(), KNIGHT));
                        if (teamTurn == TeamColor.WHITE) {
                            teamTurn = TeamColor.BLACK;
                        } else {
                            teamTurn = TeamColor.WHITE;
                        }
                    } else {
                        masterBoard.addPiece(move.getEndPosition(), new ChessPiece(piece.getTeamColor(), ROOK));
                        if (teamTurn == TeamColor.WHITE) {
                            teamTurn = TeamColor.BLACK;
                        } else {
                            teamTurn = TeamColor.WHITE;
                        }
                    }
                } else {
                    masterBoard.addPiece(move.getEndPosition(), piece);
                    if (teamTurn == TeamColor.WHITE) {
                        teamTurn = TeamColor.BLACK;
                    } else {
                        teamTurn = TeamColor.WHITE;
                    }
                }
            } else {
                throw new InvalidMoveException("Move is not a legal move");
            }

        }

    }

    /**
     * Determines if the given team is in check
     *
     * @param teamColor which team to check for check
     * @return True if the specified team is in check
     */
    public boolean isInCheck(TeamColor teamColor) {
        ChessPosition kingLocation = findKing(teamColor);

        for (int row = 1; row <= 8; row++) {
            for (int col = 1; col <= 8; col++) {
                if (masterBoard.getPiece(new ChessPosition(row, col)).getTeamColor() != teamColor) {
                    Collection<ChessMove> moves = masterBoard.getPiece(new ChessPosition(row, col)).pieceMoves(masterBoard, new ChessPosition(row, col));
                    for (ChessMove move : moves) {
                        if (move.getEndPosition().equals(kingLocation)) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    /**
     * Determines if the given team is in checkmate
     *
     * @param teamColor which team to check for checkmate
     * @return True if the specified team is in checkmate
     */
    public boolean isInCheckmate(TeamColor teamColor) {
//        throw new RuntimeException("Not implemented");
        return true;
    }

    /**
     * Determines if the given team is in stalemate, which here is defined as having
     * no valid moves while not in check.
     *
     * @param teamColor which team to check for stalemate
     * @return True if the specified team is in stalemate, otherwise false
     */
    public boolean isInStalemate(TeamColor teamColor) {
//        throw new RuntimeException("Not implemented");
        return true;
    }

    /**
     * Sets this game's chessboard to a given board
     *
     * @param board the new board to use
     */
    public void setBoard(ChessBoard board) {
        masterBoard = board;
    }

    /**
     * Gets the current chessboard
     *
     * @return the chessboard
     */
    public ChessBoard getBoard() {
        return masterBoard;
    }

    private ChessPosition findKing(TeamColor team) {
        for (int row = 1; row <= 8; row++) {
            for (int col = 1; col <= 8; col++) {
                if (masterBoard.getPiece(new ChessPosition(row, col)).getPieceType() == KING) {
                    if (masterBoard.getPiece(new ChessPosition(row, col)).getTeamColor() == team) {
                        return new ChessPosition(row, col);
                    }
                }
            }
        }
        return null;
    }

    private ChessBoard copyBoard(ChessBoard ogBoard) {
        ChessBoard newBoard = new ChessBoard();

        for (int row = 1; row <= 8; row++) {
            for (int col = 1; col <= 8; col++) {
                ChessPiece piece = ogBoard.getPiece(new ChessPosition(row, col));
                if (piece.getPieceType() == null) {
                    //pass
                } else {
                    newBoard.addPiece(new ChessPosition(row, col), piece);
                }
            }
        }

        return newBoard;
    }

}
