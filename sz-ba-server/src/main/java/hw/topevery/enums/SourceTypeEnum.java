package hw.topevery.enums;

/**
 * <p>数据推送源类型枚举</p>
 *
 * @author LeoLedger
 * @date 2023-12-23
 **/
public enum SourceTypeEnum implements BaseEnum<Integer> {
    /** 福田 */
    FU_TIAN(440304, "福田"),

    /** 罗湖 */
    LUO_HU(440303, "罗湖"),

    /** 南山 */
    NAN_SHAN(440305, "南山"),

    /** 盐田 */
    YAN_TIAN(440308, "盐田"),

    /** 宝安 */
    BAO_AN(440306, "宝安"),

    /** 龙岗 */
    LONG_GANG(440307, "龙岗"),

    /** 龙华 */
    LONG_HUA(440311, "龙华"),

    /** 坪山 */
    PING_SHAN(440310, "坪山"),

    /** 光明 */
    GUANG_MING(440309, "光明"),

    /** 大鹏新区 */
    DA_PENG(440312, "大鹏新区");

    private final Integer value;
    private final String label;

    SourceTypeEnum(Integer value, String label) {
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
