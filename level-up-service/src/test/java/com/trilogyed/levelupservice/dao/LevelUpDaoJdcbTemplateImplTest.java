package com.trilogyed.levelupservice.dao;

import com.trilogyed.levelupservice.exception.NotFoundException;
import com.trilogyed.levelupservice.model.LevelUp;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class LevelUpDaoJdcbTemplateImplTest {

    @Autowired
    private LevelUpDao levelUpDao;

    @Before
    public void setUp() throws Exception {
        levelUpDao.getAllLevelUps()
                .stream()
                .forEach(l->levelUpDao.deleteLevelUp(l.getLevelUpId()));
    }

    @Test
    public void addGetDeleteLevelUp() {
        LevelUp levelUp = new LevelUp();
        levelUp.setCustomerId(1);
        levelUp.setPoints(20);
        levelUp.setMemberDate(LocalDate.of(2019,1,20));

        levelUp = levelUpDao.addLevelUp(levelUp);

        LevelUp levelUp1 = levelUpDao.getLevelUp(levelUp.getLevelUpId());
        assertEquals(levelUp, levelUp1);

        levelUpDao.deleteLevelUp(levelUp.getLevelUpId());
        assertEquals(0, levelUpDao.getAllLevelUps().size());
    }

    /**
    test returns null if levelUpId isn't exist
     */
    @Test
    public void getLevelUpWithNonExistentId() {
        LevelUp levelUp = levelUpDao.getLevelUp(500);
        assertNull(levelUp);
    }

    /**
    delete without a valid id
     */
    @Test(expected  = NotFoundException.class)
    public void deleteLevelUpWithNonExistentId() {
        levelUpDao.deleteLevelUp(500);
    }

    /**
    update levelUp
     */
    @Test
    public void updateLevelUp() {
        LevelUp levelUp = new LevelUp();
        levelUp.setCustomerId(1);
        levelUp.setPoints(20);
        levelUp.setMemberDate(LocalDate.of(2019,1,20));
        levelUp = levelUpDao.addLevelUp(levelUp);

        levelUp.setPoints(500);
        levelUpDao.updateLevelUp(levelUp);

        assertEquals(500, levelUpDao.getLevelUp(levelUp.getLevelUpId()).getPoints());
    }

    /**
    update without a valid id
     */
    @Test(expected  = IllegalArgumentException.class)
    public void updateLevelUpWithIllegalArgumentException() {

        LevelUp levelUp = new LevelUp();
        levelUp.setLevelUpId(500);
        levelUp.setCustomerId(1);
        levelUp.setPoints(20);
        levelUp.setMemberDate(LocalDate.of(2019,1,20));

        levelUpDao.updateLevelUp(levelUp);

    }

    /**
    get all levelUps
     */
    @Test
    public void getAllLevelUps() {
        LevelUp levelUp = new LevelUp();
        levelUp.setCustomerId(1);
        levelUp.setPoints(20);
        levelUp.setMemberDate(LocalDate.of(2019,1,20));
        levelUpDao.addLevelUp(levelUp);
        levelUpDao.addLevelUp(levelUp);
        levelUpDao.addLevelUp(levelUp);

        assertEquals(3, levelUpDao.getAllLevelUps().size());
    }

    /**
    if no levelUps
     */
    @Test
    public void getAllLevelUpsWhenNoLevelUps() {
        assertEquals(0, levelUpDao.getAllLevelUps().size());
    }

    /**
    get points by customer id
     */
    @Test
    public void getLevelUpPointsByCustomerId() {
        LevelUp levelUp = new LevelUp();
        levelUp.setCustomerId(1);
        levelUp.setPoints(80);
        levelUp.setMemberDate(LocalDate.of(2019,1,20));
        levelUpDao.addLevelUp(levelUp);

        LevelUp levelUp2 = new LevelUp();
        levelUp2.setCustomerId(10);
        levelUp2.setPoints(120);
        levelUp2.setMemberDate(LocalDate.of(2019,1,20));
        levelUp2 = levelUpDao.addLevelUp(levelUp2);

        LevelUp levelUpFromDao = levelUpDao.getLevelUpByCustomerId(levelUp2.getCustomerId());
        assertEquals(levelUp2, levelUpFromDao);
    }

    /**
    if customer id invalid
     */
    @Test
    public void getLevelUpByCustomerWithNonExistentId() {
        LevelUp levelUp = levelUpDao.getLevelUpByCustomerId(500);
        assertNull(levelUp);
    }


}