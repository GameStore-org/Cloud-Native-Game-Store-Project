package com.trilogyed.adminapi.viewModels;

import java.time.LocalDate;
import java.util.Objects;

public class LevelUpViewModel {

    private int levelUpId;
    private int customerId;
    private int pointsId;
    private LocalDate memberDate;

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

    public int getPointsId() {
        return pointsId;
    }

    public void setPointsId(int pointsId) {
        this.pointsId = pointsId;
    }

    public LocalDate getMemberDate() {
        return memberDate;
    }

    public void setMemberDate(LocalDate memberDate) {
        this.memberDate = memberDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LevelUpViewModel that = (LevelUpViewModel) o;
        return levelUpId == that.levelUpId &&
                customerId == that.customerId &&
                pointsId == that.pointsId &&
                memberDate.equals(that.memberDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(levelUpId, customerId, pointsId, memberDate);
    }
}
