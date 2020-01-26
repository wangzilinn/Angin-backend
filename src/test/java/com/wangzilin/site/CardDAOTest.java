package com.***REMOVED***.site;

import com.***REMOVED***.site.dao.CardDAO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;
import java.util.logging.Logger;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class CardDAOTest {
    @Autowired
    CardDAO cardDAO;
    @Test
    public void Teat(){
        System.out.println(cardDAO.findByExpireDateLessThan(new Date()));
//        System.out.println(cardDAO.findByKeyContains("franchi"));
//        System.out.println(cardDAO.findByExpireDateLessThan(new Date(), 4));
//        cardDAO.updateStatus("motivate", -1);
        System.out.println("done");
    }
}
