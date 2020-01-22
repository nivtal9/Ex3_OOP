package gameClient;

import java.sql.*;
import java.util.LinkedList;

import static gameClient.SimpleDB.*;

class DB_Reader {
    private static LinkedList<Integer> levellst;
    DB_Reader(){
        levellst=new LinkedList<>();
        levellst.add(0);
        levellst.add(1);
        levellst.add(3);
        levellst.add(5);
        levellst.add(9);
        levellst.add(11);
        levellst.add(13);
        levellst.add(16);
        levellst.add(19);
        levellst.add(20);
        levellst.add(23);
    }
    static String printLog(int id) {
        StringBuilder str= new StringBuilder();
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = DriverManager.getConnection(jdbcUrl, jdbcUser, jdbcUserPassword);
            Statement statement = connection.createStatement();
            ResultSet resultSet=null;
            int ind =0;
            int MaxLevel=0;
            str.append("Max Score per level Played by: ").append(id).append("\n").append("\n");
            for (int i = 0; i <24 ; i++) {
                boolean have_a_score=false;
                int MaxScore=0;
                int MinMoves=Integer.MAX_VALUE;
                Timestamp time = null;
                String allCustomersQuery = "SELECT * FROM Logs where userID="+id+" AND levelID="+i;
                resultSet = statement.executeQuery(allCustomersQuery);
                str.append("level ").append(i).append(") ");
                //try {
                    while (resultSet.next()) {
                        have_a_score = true;
                        ind++;
                        int level = resultSet.getInt("levelID");
                        if (level > MaxLevel) {
                            MaxLevel = level;
                        }
                        boolean Toughlevel = ToughLevels(i);
                        int score = resultSet.getInt("score");
                        int moves = resultSet.getInt("moves");
                        if (Toughlevel) {
                            if (underMaxMoves(moves, level)) {
                                if (score > MaxScore) {
                                    MaxScore = score;
                                    MinMoves = moves;
                                    time = resultSet.getTimestamp("time");
                                }
                            }
                        } else {
                            if (score > MaxScore) {
                                MaxScore = score;
                                MinMoves = moves;
                                time = resultSet.getTimestamp("time");
                            }
                        }
                    }
/*                }
                catch (Exception e){
                    have_a_score=false;
                }*/
                if(have_a_score&&time!=null){
                    str.append("score: ").append(MaxScore).append(", moves: ").append(MinMoves).append(", at Time: ").append(time.toString()).append("\n");
                }
                else{
                    str.append("not played yet/not passed minimum requirements").append("\n");
                }
            }
            str.append("\n").append("ID: ").append(id).append(" has Played: ").append(ind).append(" games.").append("\n").append("MaxLevel Reached is:").append(MaxLevel).append("\n");
            assert resultSet != null;
            resultSet.close();
            statement.close();
            connection.close();
        }

        catch (SQLException sqle) {
            System.out.println("SQLException: " + sqle.getMessage());
            System.out.println("Vendor Error: " + sqle.getErrorCode());
        }
        catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return str.toString();
    }
    static String ToughStages(int id){
        StringBuilder str=new StringBuilder();
        str.append("Placements for 'Tough Levels' are:").append("\n");
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = DriverManager.getConnection(jdbcUrl, jdbcUser, jdbcUserPassword);
            Statement statement = connection.createStatement();
            ResultSet resultSet = null;
            for(int level:levellst) {
                String allCustomersQuery = "SELECT * FROM Logs where levelID=" + level;
                resultSet = statement.executeQuery(allCustomersQuery);
                int ind = 0;
                boolean not_my_id=true;
                while (resultSet.next()&&not_my_id) {
                    ind++;
                    if(resultSet.getInt("userID")==id){
                        not_my_id=false;
                    }
                }
                str.append(level).append(") ").append(ind).append("\n");
            }
            assert resultSet != null;
            resultSet.close();
            statement.close();
            connection.close();
        }

        catch (SQLException sqle) {
            System.out.println("SQLException: " + sqle.getMessage());
            System.out.println("Vendor Error: " + sqle.getErrorCode());
        }
        catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return str.toString();
    }
    private static boolean underMaxMoves(int moves,int level){
        switch(level) {
            case 0 :
            case 16:
            case 20:
                return moves <= 290;
            case 1:
            case 3:
            case 9:
            case 11:
            case 13:
            case 19:
                return moves<= 580;
            case 5:
                return moves<= 500;
            case 23:
                return moves<= 1140;
            default: return false;
        }
    }
    static boolean ToughLevels(int level){
        return levellst.contains(level);
    }
}
