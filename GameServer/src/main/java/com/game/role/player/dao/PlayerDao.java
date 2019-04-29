package com.game.role.player.dao;

import com.game.pojo.entity.EntityEnt;
import com.game.pojo.model.Entity;
import com.game.role.player.entity.PlayerEnt;
import com.game.role.player.model.Player;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component("PlayerDao")
@Transactional
public class PlayerDao {
    @Autowired
    private SessionFactory sessionFactory;

    public Session getSession(){
        return sessionFactory.getCurrentSession();
    }

    public List<PlayerEnt> findAll(){
        String hql = "FROM PlayerEnt";
        Query query = getSession().createQuery(hql);
        List<PlayerEnt> playerEntList = query.list();
        return playerEntList;
    }

    public Player findOne(long id) {
        String hql = "select p from PlayerEnt p where id = ?";
        Query query = getSession().createQuery(hql).setLong(0, id);
        PlayerEnt playerEnt = (PlayerEnt) query.uniqueResult();
        return Player.valueOf(playerEnt);
    }

    public List<PlayerEnt> find(String accountId) {
        String hql = "select p from PlayerEnt p where accountId = ?";
        Query query = getSession().createQuery(hql).setString(0, accountId);
        List<PlayerEnt> playerEntList = query.list();
        return playerEntList;
    }

    public void save(Player player) {
        getSession().update(player.getPlayerEnt());
    }

    public Player createPlayer(long id, String accountId) {
        try{
            PlayerEnt playerEnt = new PlayerEnt();
            playerEnt.setId(id);
            playerEnt.setAccountId(accountId);
            playerEnt.setAlive(true);
            getSession().save(playerEnt);
            return Player.valueOf(playerEnt);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
}
