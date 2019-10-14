package com.trilogyed.levelupservice.service;

import com.trilogyed.levelupservice.dao.LevelUpDao;
import com.trilogyed.levelupservice.dao.LevelUpDaoJdcbTemplateImpl;
import com.trilogyed.levelupservice.model.LevelUp;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.*;

public class LevelUpServiceTest {

    LevelUpService levelUpService;
    LevelUpDao levelUpDao;

    @Before
    public void setUp() throws Exception {

        setUpLevelUpMock();

        levelUpService = new LevelUpService(levelUpDao);

    }

    @Test
    public void addLevelUp() {
        LevelUp levelUp = new LevelUp();
        levelUp.setCustomerId(1);
        levelUp.setPoints(10);
        levelUp.setMemberDate(LocalDate.of(2019, 1, 20));

        levelUp = levelUpService.addLevelUp(levelUp);

        LevelUp levelUp1 = levelUpService.getLevelUp(levelUp.getLevelUpId());

        assertEquals(levelUp, levelUp1);
    }

    @Test
    public void getLevelUp() {

        LevelUp levelUp = new LevelUp();
        levelUp.setLevelUpId(1);
        levelUp.setCustomerId(1);
        levelUp.setPoints(10);
        levelUp.setMemberDate(LocalDate.of(2019, 1, 20));

        LevelUp levelUp1 = levelUpService.getLevelUp(levelUp.getLevelUpId());

        assertEquals(levelUp, levelUp1);
    }

    @Test
    public void getAllLevelUps() {

        LevelUp levelUp = new LevelUp();
        levelUp.setCustomerId(1);
        levelUp.setPoints(10);
        levelUp.setMemberDate(LocalDate.of(2019, 1, 20));

        levelUpService.addLevelUp(levelUp);

        levelUp = new LevelUp();
        levelUp.setCustomerId(2);
        levelUp.setPoints(30);
        levelUp.setMemberDate(LocalDate.of(2019, 3, 25));

        levelUpService.addLevelUp(levelUp);

        List<LevelUp> fromService = levelUpService.getAllLevelUps();

        assertEquals(2, fromService.size());

    }


    @Test
    public void deleteLevelUp() {
        LevelUp levelUp = levelUpService.getLevelUp(1);
        levelUpService.deleteLevelUp(1);
        ArgumentCaptor<Integer> postCaptor = ArgumentCaptor.forClass(Integer.class);
        verify(levelUpDao).deleteLevelUp(postCaptor.capture());
        assertEquals(levelUp.getLevelUpId(), postCaptor.getValue().intValue());
    }


    @Test
    public void updateLevelUp() {

        LevelUp levelUp = new LevelUp();
        levelUp.setLevelUpId(1);
        levelUp.setCustomerId(1);
        levelUp.setPoints(100);
        levelUp.setMemberDate(LocalDate.of(2019, 1, 20));

        levelUpService.updateLevelUp(levelUp);
        ArgumentCaptor<LevelUp> postCaptor = ArgumentCaptor.forClass(LevelUp.class);
        verify(levelUpDao).updateLevelUp(postCaptor.capture());
        assertEquals(levelUp.getPoints(), postCaptor.getValue().getPoints());

    }

    /**
    test if invalid levelUp id
     */
    @Test
    public void getLevelUpWithNonExistentId() {
        LevelUp levelUp = levelUpService.getLevelUp(500);
        assertNull(levelUp);
    }

    /**
    get points by customerId
     */
    @Test
    public void getLevelUpPointsByCustomerId() {

        LevelUp levelUp = new LevelUp();
        levelUp.setLevelUpId(1);
        levelUp.setCustomerId(1);
        levelUp.setPoints(10);
        levelUp.setMemberDate(LocalDate.of(2019, 1, 20));

        LevelUp levelUp2 = new LevelUp();
        levelUp2.setLevelUpId(2);
        levelUp2.setCustomerId(2);
        levelUp2.setPoints(30);
        levelUp2.setMemberDate(LocalDate.of(2019, 3, 25));

        LevelUp levelUpForCust1 = levelUpService.getLevelUpByCustomerId(levelUp.getLevelUpId());

        assertEquals(levelUp, levelUpForCust1);

        LevelUp levelUpForCust2 = levelUpService.getLevelUpByCustomerId(levelUp2.getLevelUpId());

        assertEquals(levelUp2, levelUpForCust2);
    }


    public void setUpLevelUpMock() {

        levelUpDao = mock(LevelUpDaoJdcbTemplateImpl.class);

        LevelUp levelUp = new LevelUp();
        levelUp.setCustomerId(1);
        levelUp.setPoints(10);
        levelUp.setMemberDate(LocalDate.of(2019, 1, 20));

        LevelUp levelUp2 = new LevelUp();
        levelUp2.setLevelUpId(1);
        levelUp2.setCustomerId(1);
        levelUp2.setPoints(10);
        levelUp2.setMemberDate(LocalDate.of(2019, 1, 20));

        LevelUp levelUp3 = new LevelUp();
        levelUp3.setCustomerId(2);
        levelUp3.setPoints(30);
        levelUp3.setMemberDate(LocalDate.of(2019, 3, 25));

        LevelUp levelUp4 = new LevelUp();
        levelUp4.setLevelUpId(2);
        levelUp4.setCustomerId(2);
        levelUp4.setPoints(30);
        levelUp4.setMemberDate(LocalDate.of(2019, 3, 25));

        doReturn(levelUp2).when(levelUpDao).addLevelUp(levelUp);
        doReturn(levelUp4).when(levelUpDao).addLevelUp(levelUp3);
        doReturn(levelUp2).when(levelUpDao).getLevelUp(1);
        doReturn(levelUp4).when(levelUpDao).getLevelUp(2);

        doReturn(levelUp2).when(levelUpDao).getLevelUpByCustomerId(1);
        doReturn(levelUp4).when(levelUpDao).getLevelUpByCustomerId(2);

        List<LevelUp> levelUpList = new ArrayList<>();
        levelUpList.add(levelUp2);
        levelUpList.add(levelUp4);

        doReturn(levelUpList).when(levelUpDao).getAllLevelUps();

    }

}
