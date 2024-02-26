package hw.topevery.pojo.query;
import hw.topevery.enums.DataTypeEnum;
import hw.topevery.enums.PushStatusEnum;
import hw.topevery.enums.SourceTypeEnum;
import hw.topevery.enums.TargetTypeEnum;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 数据推送查询对象
 *
 * @author LeoLedger
 * @date 2023-12-23
 */
@Data
public class DataPushRecordQuery {
    /** 开始时间 **/
    private LocalDateTime startTime;
    /** 结束时间 **/
    private LocalDateTime endTime;
    /** 取消推送 **/
    private Boolean canceled;
    /** 参数ID **/
    private Integer paramsId;
    /** 推送状态 **/
    private PushStatusEnum pushStatus;
    /** 推送状态 **/
    private PushStatusEnum[] pushStatusArr;
    /** 失败次数 **/
    private Integer maxFailCount;
    /** 限制数量 **/
    private Integer limit;
}
