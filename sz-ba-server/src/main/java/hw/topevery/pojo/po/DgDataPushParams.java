package hw.topevery.pojo.po;

import hw.topevery.framework.annotation.DbTable;
import hw.topevery.framework.annotation.DbTableField;
import hw.topevery.framework.enums.DbTypeEnum;
import lombok.Data;

import java.time.LocalDateTime;

/**
* 数据推送参数 实体类
*
* @author LeoLedger
* @date 2023-12-23
*/
@Data
@DbTable(tableName = "t_dg_data_push_params")
public class DgDataPushParams {
    @DbTableField(columnName = "c_id", isKey = true, canUpdate = false, canInsert = false, dbType = DbTypeEnum.INTEGER)
    private Integer id;
    /** topic值 */
    @DbTableField(columnName = "c_topic", dbType = DbTypeEnum.VARCHAR)
    private String topic;
    /** 系统ID */
    @DbTableField(columnName = "c_system_id", dbType = DbTypeEnum.VARCHAR)
    private String systemId;
    /** 系统code */
    @DbTableField(columnName = "c_system_code", dbType = DbTypeEnum.VARCHAR)
    private String systemCode;
    /** 消息code */
    @DbTableField(columnName = "c_message_code", dbType = DbTypeEnum.VARCHAR)
    private String messageCode;
    /** 推送地址 */
    @DbTableField(columnName = "c_url", dbType = DbTypeEnum.VARCHAR)
    private String url;
    /** 调试 */
    @DbTableField(columnName = "c_debug", dbType = DbTypeEnum.BIT)
    private Boolean debug;
    /** 数据目标类型（1-数据治理平台） */
    @DbTableField(columnName = "c_target_type", dbType = DbTypeEnum.INTEGER)
    private Integer targetType;
    /** 数据源类型 */
    @DbTableField(columnName = "c_source_type", dbType = DbTypeEnum.INTEGER)
    private Integer sourceType;
    /** 数据类型 */
    @DbTableField(columnName = "c_data_type", dbType = DbTypeEnum.INTEGER)
    private Integer dataType;
    /** 禁用 */
    @DbTableField(columnName = "c_disable", dbType = DbTypeEnum.BIT)
    private Boolean disable;

}
