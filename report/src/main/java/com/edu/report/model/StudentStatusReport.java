package com.edu.report.model;

/**
 * 学员状态报表实体类
 *
 * @author: linj
 * @create: 2018/3/1  17:05
 */
public class StudentStatusReport {
    private Long   branchId;
    private String branchName;
    private String changeTime;
    private int predictionNum;//预报名人数
    private int newNum;//新生人数
    private int readingNum;//在读人数
    private int notpayNum;//有来未缴人数
    private int lossNum;//流失人数
    private int sleepNum;//沉睡人数
    private int moveNum;//搬家人数

    public Long getBranchId() {
        return branchId;
    }

    public void setBranchId(Long branchId) {
        this.branchId = branchId;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public String getChangeTime() {
        return changeTime;
    }

    public void setChangeTime(String changeTime) {
        this.changeTime = changeTime;
    }

    public int getPredictionNum() {
        return predictionNum;
    }

    public void setPredictionNum(int predictionNum) {
        this.predictionNum = predictionNum;
    }

    public int getNewNum() {
        return newNum;
    }

    public void setNewNum(int newNum) {
        this.newNum = newNum;
    }

    public int getReadingNum() {
        return readingNum;
    }

    public void setReadingNum(int readingNum) {
        this.readingNum = readingNum;
    }

    public int getNotpayNum() {
        return notpayNum;
    }

    public void setNotpayNum(int notpayNum) {
        this.notpayNum = notpayNum;
    }

    public int getLossNum() {
        return lossNum;
    }

    public void setLossNum(int lossNum) {
        this.lossNum = lossNum;
    }

    public int getSleepNum() {
        return sleepNum;
    }

    public void setSleepNum(int sleepNum) {
        this.sleepNum = sleepNum;
    }

    public int getMoveNum() {
        return moveNum;
    }

    public void setMoveNum(int moveNum) {
        this.moveNum = moveNum;
    }
}
