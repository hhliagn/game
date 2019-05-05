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

    ////
    public List<PlayerEnt> loadAll() {
        String hql = "FROM PlayerEnt";
        Query query = getSession().createQuery(hql);
        List<PlayerEnt> playerEntList = query.list();
        for (PlayerEnt playerEnt : playerEntList) {
            playerEnt.doDeserialize();
        }
        return playerEntList;
    }

    public PlayerEnt get(long id) {
        String hql = "select p from PlayerEnt p where id = ?";
        Query query = getSession().createQuery(hql).setLong(0, id);
        PlayerEnt playerEnt = (PlayerEnt) query.uniqueResult();
        playerEnt.doDeserialize();
        return playerEnt;
    }

    public void save(PlayerEnt playerEnt) {
        playerEnt.doSerialize();
        getSession().saveOrUpdate(playerEnt);
    }
}
