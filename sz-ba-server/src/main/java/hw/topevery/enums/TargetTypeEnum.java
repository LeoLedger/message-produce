package hw.topevery.enums;

/**
 * <p>数据推送目标类型枚举</p>
 *
 * @author LeoLedger
 * @date 2023-12-23
 **/
public enum TargetTypeEnum implements BaseEnum<Integer> {

    /** 数据治理平台 */
    DG(1,"数据治理平台"),

    /** 全周期运管服平台 */
    SSSP(2,"全周期运管服平台");

    private final Integer value;
    private final String label;

    TargetTypeEnum(Integer value, String label) {
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

    public static TargetTypeEnum valueOf(Integer value) {
        for (TargetTypeEnum val: values()) {
            if (val.getValue().equals(value)) {
                return val;
            }
        }
        return null;
    }
}
