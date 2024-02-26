package hw.topevery.pojo.po;

import hw.topevery.framework.annotation.DbTable;
import hw.topevery.framework.annotation.DbTableField;
import hw.topevery.framework.enums.DbTypeEnum;
import lombok.Data;

import java.time.LocalDateTime;

/**
* 数据推送记录 实体类
*
* @author LeoLedger
* @date 2023-12-23
*/
@Data
@DbTable(tableName = "t_dg_data_push_record")
public class DgDataPushRecord {
    @DbTableField(columnName = "c_id", isKey = true, canUpdate = false, canInsert = false, dbType = DbTypeEnum.BIGINT)
    private Long id;
    @DbTableField(columnName = "c_data_id", canUpdate = false, dbType = DbTypeEnum.BIGINT)
    private Long dataId;
    @DbTableField(columnName = "c_create_time", canUpdate = false, dbType = DbTypeEnum.TIMESTAMP)
    private LocalDateTime createTime;
    /** 参数ID */
    @DbTableField(columnName = "c_params_id", canUpdate = false, dbType = DbTypeEnum.INTEGER)
    private Integer paramsId;
    /** 推送时间 */
    @DbTableField(columnName = "c_push_time", dbType = DbTypeEnum.TIMESTAMP)
    private LocalDateTime pushTime;
    /** 推送状态（0-待推送，1-已推送，-1-推送失败） */
    @DbTableField(columnName = "c_push_status", dbType = DbTypeEnum.INTEGER)
    private Integer pushStatus;
    /** 失败次数 */
    @DbTableField(columnName = "c_fail_count", dbType = DbTypeEnum.INTEGER)
    private Integer failCount;
    /** JSON数据 */
    @DbTableField(columnName = "c_data", canUpdate = false, dbType = DbTypeEnum.VARCHAR)
    private String data;
    /** 取消推送 */
    @DbTableField(columnName = "c_canceled", dbType = DbTypeEnum.BIT)
    private Boolean canceled;
    /** 逻辑删除 */
    @DbTableField(columnName = "c_db_status", dbType = DbTypeEnum.BIT)
    private Boolean dbStatus;

}
