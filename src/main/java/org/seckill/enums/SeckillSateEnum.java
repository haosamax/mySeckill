package org.seckill.enums;

/**
 * 使用美剧表示我们的常量数据字段
 * @Author: haojunhao
 * @Date: Create in 2017/9/24
 **/
public enum  SeckillSateEnum {
    SUCCESS(1,"秒杀成功"),
    END(0,"秒杀结束"),
    REPEATE_KILL(-1,"重复秒杀"),
    INNER_ERROR(-2,"系统异常"),
    DATA_REWRITE(-3,"数据篡改");

    private int state;

    private String stateInfo;

    SeckillSateEnum(int state, String stateInfo) {
        this.state = state;
        this.stateInfo = stateInfo;
    }

    public int getState() {
        return state;
    }

    public String getStateInfo() {
        return stateInfo;
    }

    public static SeckillSateEnum stateOf(int index){
        for (SeckillSateEnum seckillSateEnum : values()){
            if (index == seckillSateEnum.getState()){
                return seckillSateEnum;
            }
        }
        return null;
    }
}
