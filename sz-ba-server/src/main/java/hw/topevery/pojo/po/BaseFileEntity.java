package hw.topevery.pojo.po;

import hw.topevery.framework.annotation.DbTableField;
import hw.topevery.framework.enums.DbTypeEnum;
import io.swagger.annotations.ApiModelProperty;

/**
 * 文件基础实体类
 *
 * @author zxa
 * @date 2020-11-06
 */
public class BaseFileEntity extends BaseEntity {

    @ApiModelProperty(value = "文件ID")
    @DbTableField(columnName = "c_file_id", dbType = DbTypeEnum.VARCHAR, isKey = false, canUpdate = true, canInsert = true)
    private String fileId;

    @ApiModelProperty(value = "文件名称")
    @DbTableField(columnName = "c_file_name", dbType = DbTypeEnum.VARCHAR, isKey = false, canUpdate = true, canInsert = true)
    private String fileName;

    @ApiModelProperty(value = "文件类型")
    @DbTableField(columnName = "c_file_type", dbType = DbTypeEnum.VARCHAR, isKey = false, canUpdate = true, canInsert = true)
    private String fileType;

    @ApiModelProperty(value = "文件类型(int)：0、图片，1、视频，2、音频，3、其他")
    @DbTableField(columnName = "c_file_ext", dbType = DbTypeEnum.TINYINT, isKey = false, canUpdate = true, canInsert = true)
    private Integer fileExt;

    @ApiModelProperty(value = "数据类型")
    @DbTableField(columnName = "c_data_type", dbType = DbTypeEnum.TINYINT, isKey = false, canUpdate = true, canInsert = true)
    private Integer dataType;

}
