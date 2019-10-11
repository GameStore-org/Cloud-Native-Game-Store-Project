package com.trilogyed.levelupservice.model;

import java.util.Date;
import java.util.Objects;

public class LevelUp {
    private int levelUpId;
    private int customerId;
    private int points;
    private Date memberDate;

    public LevelUp(int levelUpId, int customerId, int points, Date memberDate) {
        this.levelUpId = levelUpId;
        this.customerId = customerId;
        this.points = points;
        this.memberDate = memberDate;
    }

    public int getLevelUpId() {
        return levelUpId;
    }

    public void setLevelUpId(int levelUpId) {
        this.levelUpId = levelUpId;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public Date getMemberDate() {
        return memberDate;
    }

    public void setMemberDate(Date memberDate) {
        this.memberDate = memberDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LevelUp that = (LevelUp) o;
        return levelUpId == that.levelUpId &&
                customerId == that.customerId &&
                points == that.points &&
                Objects.equals(memberDate, that.memberDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(levelUpId, customerId, points, memberDate);
    }

    @Override
    public String toString() {
        return "LevelUpDao{" +
                "levelUpId=" + levelUpId +
                ", customerId=" + customerId +
                ", points=" + points +
                ", memberDate=" + memberDate +
                '}';
    }
}
