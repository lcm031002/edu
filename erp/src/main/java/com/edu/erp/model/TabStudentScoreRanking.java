package com.edu.erp.model;

import java.util.Date;
import java.util.List;

public class TabStudentScoreRanking extends BaseObject {
    private static final long serialVersionUID = 1L;
    private Long studentScoreInfoId;
    private String scoreRankingType;
    private String scoreRankingTypeName;
    private Long ranking;

    public Long getStudentScoreInfoId() {
        return studentScoreInfoId;
    }

    public void setStudentScoreInfoId(Long studentScoreInfoId) {
        this.studentScoreInfoId = studentScoreInfoId;
    }

    public String getScoreRankingType() {
        return scoreRankingType;
    }

    public void setScoreRankingType(String scoreRankingType) {
        this.scoreRankingType = scoreRankingType;
    }

    public String getScoreRankingTypeName() {
        return scoreRankingTypeName;
    }

    public void setScoreRankingTypeName(String scoreRankingTypeName) {
        this.scoreRankingTypeName = scoreRankingTypeName;
    }

    public Long getRanking() {
        return ranking;
    }

    public void setRanking(Long ranking) {
        this.ranking = ranking;
    }
}
