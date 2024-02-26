package hw.topevery.enums;

/**
 * <p>数据推送目标类型枚举</p>
 *
 * @author LeoLedger
 * @date 2023-12-23
 **/
public enum PushStatusEnum implements BaseEnum<Integer> {

    /** 待推送 */
    WAITING(0,"待推送"),

    /** 推送完成 */
    FINISHED(1,"推送完成"),

    /** 推送中 */
    PUSHING(2,"推送中"),

    /** 推送失败 */
    FAILED(-1,"推送失败");

    private final Integer value;
    private final String label;

    PushStatusEnum(Integer value, String label) {
        this.value = value;
        this.label = label;
    }

    @Override
    public Integer getValue() {
        return this.value;
    }

    public String getLabel() {
        return this.label;
    }
}
