package hw.topevery.pojo.po;

import hw.topevery.framework.annotation.DbTableField;
import hw.topevery.framework.enums.DbTypeEnum;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 基础的实体
 *
 * @author bingxin.xu
 */
@Data
public class BaseEntity implements Serializable {
    @DbTableField(columnName = "c_id", isKey = true, canInsert = false, dbType = DbTypeEnum.INTEGER)
    private Integer id;

    @DbTableField(columnName = "c_create_id", canUpdate = false, dbType = DbTypeEnum.VARCHAR)
    private String createId;

    @DbTableField(columnName = "c_create_time", canUpdate = false, dbType = DbTypeEnum.VARCHAR)
    private LocalDateTime createTime;

    @DbTableField(columnName = "c_update_id", dbType = DbTypeEnum.VARCHAR)
    private String updateId;

    @DbTableField(columnName = "c_update_time", dbType = DbTypeEnum.TIMESTAMP)
    private LocalDateTime updateTime;

    @DbTableField(columnName = "c_db_status", dbType = DbTypeEnum.INTEGER)
    private Integer dbStatus;
}
