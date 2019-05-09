package com.game;

import com.game.scene.Scene;
import com.game.user.account.model.Account;
import com.game.user.account.model.BaseAccountInfo;
import com.game.user.mapInfo.entity.MapInfoEnt;

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
            case "login":
                try {
                    String accountId = split[1];
                    String password = split[2];
                    SpringContext.getAccountService().login(accountId, password);
                    revMsg = "登录成功";
                } catch (Exception e) {
                    revMsg = e.getMessage();
                }
                break;
            case "logout":
                try {
                    String accountIdc = split[1];
                    SpringContext.getAccountService().logout(accountIdc);
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
            case "enter":
                try {
                    Account loginAccount = SpringContext.getAccountService().getLoginAccount();
                    if (loginAccount == null){
                        revMsg = "用户没登陆";
                        break;
                    }
                    String accountId = loginAccount.getAccountId();
                    MapInfoEnt mapInfoEnt = SpringContext.getMapInfoService().getMapInfoEnt(accountId);
                    int curMapId = mapInfoEnt.getCurMapId();
                    SpringContext.getSceneService().enter(accountId, curMapId);
                    revMsg = "进入场景";
                }catch (Exception e){
                    revMsg = e.getMessage();
                }
                break;
            case "move":
                try {
                    int x = Integer.parseInt(split[1]);
                    int y = Integer.parseInt(split[2]);
                    Account loginAccount = SpringContext.getAccountService().getLoginAccount();
                    if (loginAccount == null){
                        revMsg = "用户没登陆";
                        break;
                    }
                    String accountId = loginAccount.getAccountId();
                    BaseAccountInfo baseAccountInfo
                            = SpringContext.getAccountService().getBaseAccountInfo(accountId);
                    long sceneId = baseAccountInfo.getSceneId();
                    Scene scene = SpringContext.getSceneService().getScene(sceneId);
                    if (scene == null){
                        revMsg = "用户还没进入场景, 请先enter";
                        break;
                    }
                    scene.move(x, y);
                    String msg = scene.getMessage();
                    revMsg = "已移动到指定位置" + "\n" + msg;
                }catch (Exception e){
                    revMsg = e.getMessage();
                }
                break;
            case "change-map":
                try {
                    Account loginAccount = SpringContext.getAccountService().getLoginAccount();
                    if (loginAccount == null){
                        revMsg = "用户没登陆";
                        break;
                    }
                    String accountId = loginAccount.getAccountId();
                    String mapName = split[1];
                    SpringContext.getMapService().changeMap(accountId, mapName);
                    int mapId = SpringContext.getMapService().name2Id(mapName);
                    SpringContext.getSceneService().enter(accountId, mapId);
                    revMsg = "切换地图成功";
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
