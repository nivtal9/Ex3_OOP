package Tests;

import GameElements.*;
import Server.Game_Server;
import Server.game_service;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class RobotTest {

    @Test
    void getValue() throws JSONException {
        game_service game = Game_Server.getServer(13);
        int value;
        JSONObject line = new JSONObject(game.toString());
        JSONObject ttt = line.getJSONObject("GameServer");
        int rs = ttt.getInt("robots");
        for (int i = 0; i < rs; i++) {
            game.addRobot(i);
            String robot_json = game.getRobots().get(i);
            JSONObject line2 = new JSONObject(robot_json);
            JSONObject ttt2 = line2.getJSONObject("Robot");
            value = ttt2.getInt("value");
            robot_data f = new Robot(game.getRobots(), i);
            System.out.println(value +" "+f.getValue());
            assertEquals(value, f.getValue());
        }
    }

    @Test
    void getDest() throws JSONException {
        game_service game = Game_Server.getServer(23);
        int Dest;
        JSONObject line = new JSONObject(game.toString());
        JSONObject ttt = line.getJSONObject("GameServer");
        int rs = ttt.getInt("robots");
        for (int i = 0; i < rs; i++) {
            game.addRobot(i);
            String robot_json = game.getRobots().get(i);
            JSONObject line2 = new JSONObject(robot_json);
            JSONObject ttt2 = line2.getJSONObject("Robot");
            Dest = ttt2.getInt("dest");
            robot_data f = new Robot(game.getRobots(), i);
            System.out.println(Dest +" "+f.getDest());
            assertEquals(Dest, f.getDest());
        }
    }

    @Test
    void getSrc() throws JSONException {
        game_service game = Game_Server.getServer(20);
        int src;
        JSONObject line = new JSONObject(game.toString());
        JSONObject ttt = line.getJSONObject("GameServer");
        int rs = ttt.getInt("robots");
        for (int i = 0; i < rs; i++) {
            game.addRobot(i);
            String robot_json = game.getRobots().get(i);
            JSONObject line2 = new JSONObject(robot_json);
            JSONObject ttt2 = line2.getJSONObject("Robot");
            src = ttt2.getInt("src");
            robot_data f = new Robot(game.getRobots(), i);
            System.out.println(src +" "+f.getSrc());
            assertEquals(src, f.getSrc());
        }
    }

    @Test
    void getId() throws JSONException {
        game_service game = Game_Server.getServer(18);
        int id;
        JSONObject line = new JSONObject(game.toString());
        JSONObject ttt = line.getJSONObject("GameServer");
        int rs = ttt.getInt("robots");
        for (int i = 0; i < rs; i++) {
            game.addRobot(i);
            String robot_json = game.getRobots().get(i);
            JSONObject line2 = new JSONObject(robot_json);
            JSONObject ttt2 = line2.getJSONObject("Robot");
            id = ttt2.getInt("id");
            robot_data f = new Robot(game.getRobots(), i);
            System.out.println(id +" "+f.getId());
            assertEquals(id, f.getId());
        }
    }

    @Test
    void getLocation() throws JSONException {
        game_service game = Game_Server.getServer(18);
        String location="";
        JSONObject line = new JSONObject(game.toString());
        JSONObject ttt = line.getJSONObject("GameServer");
        int rs = ttt.getInt("robots");
        for (int i = 0; i < rs; i++) {
            game.addRobot(i);
            String robot_json = game.getRobots().get(i);
            JSONObject line2 = new JSONObject(robot_json);
            JSONObject ttt2 = line2.getJSONObject("Robot");
            location = ttt2.getString("pos");
            robot_data f = new Robot(game.getRobots(), i);
            System.out.println(location.substring(0,location.indexOf(','))+" "+f.getLocation().x());
            System.out.println(location.substring(location.indexOf(',')+1,location.lastIndexOf(','))+" "+f.getLocation().y());
            assertEquals(Double.parseDouble(location.substring(0,location.indexOf(','))),f.getLocation().x());
            assertEquals(Double.parseDouble(location.substring(location.indexOf(',')+1,location.lastIndexOf(','))),f.getLocation().y());
        }
    }

    @Test
    void totalScore() throws JSONException {
        game_service game = Game_Server.getServer(6);
        JSONObject line = new JSONObject(game.toString());
        JSONObject ttt = line.getJSONObject("GameServer");
        int grade = ttt.getInt("grade");
        robot_data f=new Robot(game.toString());
        System.out.println(grade+" "+f.TotalScore());
        assertEquals(grade,f.TotalScore());
    }
}