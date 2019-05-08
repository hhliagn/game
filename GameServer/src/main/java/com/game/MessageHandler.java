package com.game;

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
                    String accountID = split[1];
                    String mapName = split[2];
                    SpringContext.getMapService().changeMap(accountID, mapName);
                    int mapId = SpringContext.getMapService().name2Id(mapName);
                    SpringContext.getSceneService().enter(accountID, mapId);
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
            case "clear":
                revMsg = "clear";
                break;
        }

        setMessage(revMsg);
    }
}
