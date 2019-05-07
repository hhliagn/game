package com.lhh.service;

import com.game.world.service.map.dao.MapEntDao;
import com.game.world.service.map.entity.MapEnt;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;

public class HelloServiceTest {

    @Autowired
    private MapEntDao mapEntDao;

    @Test
    public void test(){

        ApplicationContext applicationContext =
                new ClassPathXmlApplicationContext("applicationContext.xml");
        /*HelloService helloService =
                (HelloService) applicationContext.getBean("helloService");
        helloService.print();*/
        /*Npc lhh = new Npc("lhh");
        System.out.println(lhh.name);

        BirthPlace birthPlace = BirthPlace.instance();

        int mapId = birthPlace.getMapId();
        System.out.println(mapId);

        List<AbstractEntity> entityList = birthPlace.getEntityList();
        for (AbstractEntity abstractEntity : entityList) {
            if (abstractEntity instanceof Npc){
                Npc npc = (Npc) abstractEntity;
                String name = npc.getName();
                System.out.println(name);
            }
        }*/

        //applicationContext.getBean()

        List<MapEnt> all = mapEntDao.loadAll();
        for (MapEnt mapEnt : all) {
            System.out.println(mapEnt.getName());
        }
    }
}
