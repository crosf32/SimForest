package fr.crosf32.fxtest.entity;

public class Config {
    private int num, delay, maxTime, width, height;

    public Config(int num, int delay, int maxTime, int width, int height) {
        this.num = num;
        this.delay = delay;
        this.maxTime = maxTime;
        this.width = width;
        this.height = height;
    }


    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public int getDelay() {
        return delay;
    }

    public void setDelay(int delay) {
        this.delay = delay;
    }

    public int getMaxTime() {
        return maxTime;
    }

    public void setMaxTime(int maxTime) {
        this.maxTime = maxTime;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }
}
