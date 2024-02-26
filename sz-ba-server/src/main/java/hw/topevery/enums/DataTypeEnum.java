package hw.topevery.enums;

/**
 * <p>推送数据类型枚举</p>
 *
 * @author LeoLedger
 * @date 2023-12-23
 **/
public enum DataTypeEnum implements BaseEnum<Integer> {
    /** 业务主体对象——环卫车辆 */
    BUS_BASE_CAR(1401, "业务主体对象——环卫车辆"),

    /** 业务主体对象——环卫车辆 */
    BUS_BASE_PERSON(1402, "业务主体对象——环卫人员"),

    /** 业务主体对象——服务企业 */
    BUS_BASE_COMPANY(1403, "业务主体对象——服务企业"),

    /** 业务主体对象——项目/标段 */
    BUS_BASE_ITEM(1404, "业务主体对象——项目/标段"),

    /** 业务主体对象——履约合同 */
    BUS_BASE_CONTRACT(1405, "业务主体对象——履约合同"),

    /** 环卫设施——分类投放点 */
    FAC_CLASSIFY_POINT(1501, "环卫设施——分类投放点"),

    /** 环卫设施——垃圾收集点 */
    FAC_COLLECT_POINT(1502, "环卫设施——垃圾收集点"),

    /** 环卫设施——垃圾收集点 */
    FAC_STATION(1503, "环卫设施——垃圾收集点"),

    /** 环卫设施——垃圾收集点 */
    FAC_STAGING_POINT(1504, "环卫设施——垃圾收集点"),

    /** 环卫设施——垃圾处理设施 */
    FAC_WASTE_DISPOSAL(1505, "环卫设施——垃圾处理设施"),

    /** 环卫设施——公共厕所 */
    FAC_TOILET(1506, "环卫设施——公共厕所");

    private final Integer value;
    private final String label;

    DataTypeEnum(Integer value, String label) {
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
