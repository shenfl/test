package com.test.flink;

/**
 * Created by shenfl on 2018/9/30
 */
public class Pattern {
    private String firstAction;
    private String secondAction;
    public Pattern() {}
    public Pattern(String firstAction, String secondAction) {
        this.firstAction = firstAction;
        this.secondAction = secondAction;
    }

    public String getFirstAction() {
        return firstAction;
    }

    public void setFirstAction(String firstAction) {
        this.firstAction = firstAction;
    }

    public String getSecondAction() {
        return secondAction;
    }

    public void setSecondAction(String secondAction) {
        this.secondAction = secondAction;
    }

    @Override
    public String toString() {
        return "Pattern{" +
                "firstAction='" + firstAction + '\'' +
                ", secondAction='" + secondAction + '\'' +
                '}';
    }
}
