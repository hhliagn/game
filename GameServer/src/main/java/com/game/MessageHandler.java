package com.game;

import com.game.world.service.map.entity.MapEnt;
import com.game.role.player.model.Player;

public class MessageHandler {

    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void handle(){

        String revMsg = null;
        String[] split = this.message.split(" ");

        switch (split[0]) {
            case "move":
                try {
                    String mapName = split[1];
                    SpringContext.getWorldService().changeMap(mapName);
                    //String mapName = SpringContext.getMapService().getMap(mapId);
                    revMsg = "切换地图成功，当前地图为：" + mapName;
                }catch (Exception e){
                    revMsg = e.getMessage();
                }
                break;
            case "login":
                try {
                    String accountId = split[1];
                    String password = split[2];
                    SpringContext.getLoginService().login(accountId, password);
                    revMsg = "登录成功";
                } catch (Exception e) {
                    revMsg = e.getMessage();
                }
                break;
            case "logout":
                try {
                    String accountIdc = split[1];
                    SpringContext.getLogoutService().logout(accountIdc);
                    revMsg = "登出成功";
                }catch (Exception e){
                    revMsg = e.getMessage();
                }
                break;
            case "create-role":
                try {
                    String accountId = split[1];
                    String nickName = split[2];
                    int job = Integer.parseInt(split[3]);
                    int sex = Integer.parseInt(split[4]);
                    SpringContext.getAccountService().createRole(accountId, nickName, job, sex);
                    revMsg = "创建玩家成功";
                }catch (Exception e){
                    revMsg = e.getMessage();
                }
                break;
//            case "cur-map":
//                try {
//                    MapEnt mapEnt = SpringContext.getWorldService().getCurMap();
//                    String curMap = mapEnt.getName();
//                    revMsg = "当前地图为：" + curMap;
//                }catch (Exception e){
//                    revMsg = e.getMessage();
//                }
//                break;
            case "print-map":
                try {
                    String mapName = split[1];
                    String result = SpringContext.getWorldService().printMap(mapName);
                    revMsg = result;
                }catch (Exception e){
                    revMsg = e.getMessage();
                }
                break;
//            case "print-account":
//                try {
//                    String result = SpringContext.getWorldService().printAccount();
//                    revMsg = result;
//                }catch (Exception e){
//                    revMsg = e.getMessage();
//                }
//                break;
            case "clear":
                revMsg = "clear";
                break;
//            case "login-player":
//                try {
//                    long playerId = Long.parseLong(split[1]);
//                    SpringContext.getPlayerService().login(playerId);
//                    revMsg = "登录成功";
//                }catch (Exception e){
//                    revMsg = e.getMessage();
//                }
//                break;
        }

        setMessage(revMsg);
    }
}
