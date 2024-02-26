package hw.topevery.pojo.vo;

import lombok.Data;

import java.util.Map;

@Data
public class PushBaseVO<K> {

    /** 数据目标类型 */
    private Integer targetType;

    /** 数据源类型 */
    private Integer sourceType;

    /** 数据类型 */
    private Integer dataType;

    /** 数据（key:唯一便是，value:JSON数据） */
    private Map<K, String> data;
}
