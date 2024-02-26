package hw.topevery.pojo.vo;

import lombok.Data;

import java.util.Collections;
import java.util.List;

import static hw.topevery.config.MessageProduceConfig.*;

@Data
public class FileVO {
    /** 附件ID **/
    private String fileId;
    /** 附件名称 **/
    private String fileName;
    /** 获取附件地址 (地址为全路径地址,不要只填服务器接口地址) **/
    private String url;
    /** 如果获取附件信息需要授权,在header中加入相关的授权信息 **/
    private List<HeaderParameter> header;

    public final static String URL_FORMATTER = "%s?%s=%s&%s=%s";

    public FileVO() {
    }

    public FileVO(String fileId) {
        this(fileId, null);
    }

    public FileVO(String fileId, String fileName) {
        this(fileId, fileName, String.format(URL_FORMATTER, FILE_DOWNLOAD_URL, FILE_ID_ATTR, fileId, FILE_NAME_ATTR, fileName), Collections.emptyList());
    }

    public FileVO(String fileId, String fileName, String url, List<HeaderParameter> header) {
        this.fileId = fileId;
        this.fileName = fileName;
        this.url = url;
        this.header = header;
    }

    @Data
    public static class HeaderParameter {
        private String key;
        private String value;
    }
}
