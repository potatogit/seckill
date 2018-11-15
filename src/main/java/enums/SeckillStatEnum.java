package enums;

public enum SeckillStatEnum {
    SUCCESS(1, "Seckill succeed"),
    END(0, "Seckill ends"),
    REPEAT_KILL(-1, "Seckill repeated"),
    INNER_ERROR(-2, "system error"),
    DATA_REWRITE(-3, "data changed");

    private int state;
    private String stateInfo;

    SeckillStatEnum(int state, String stateInfo) {
        this.state = state;
        this.stateInfo = stateInfo;
    }

    public int getState() {
        return state;
    }

    public String getStateInfo() {
        return stateInfo;
    }



}
