package hw.topevery.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "hw.message-produce-config")
public class MessageProduceConfig {
    /** 系统编码 **/
    public static String PUSH_URL;
    /** 推送超时（毫秒） **/
    public static Integer PUSH_TIMEOUT;
    /** 系统编码 **/
    public static Integer SYSTEM_CODE;
    /** 数据治理系统编码 **/
    public static String DG_SYSTEM_CODE;
    /** 附件下载地址 **/
    public static String FILE_DOWNLOAD_URL;
    /** 文件ID参数名 **/
    public static String FILE_ID_ATTR;
    /** 文件名称属性名 **/
    public static String FILE_NAME_ATTR;

    @Value("${pushUrl:http://localhost:8089/msg/receive}")
    public void setPushUrl(String pushUrl) {
        MessageProduceConfig.PUSH_URL = pushUrl;
    }

    @Value("${pushTimeout:3000}")
    public void setPushTimeout(Integer timeout) {
        MessageProduceConfig.PUSH_TIMEOUT = timeout;
    }

    @Value("${systemCode:4306}")
    public void setSystemCode(Integer systemCode) {
        MessageProduceConfig.SYSTEM_CODE = systemCode;
    }

    @Value("${dgSystemCode:1}")
    public void setDgSystemCode(String systemCode) {
        MessageProduceConfig.DG_SYSTEM_CODE = systemCode;
    }

    @Value("${fileDownloadUrl:http://localhost:49280/fmp/file}")
    public void setFileDownloadUrl(String url) {
        MessageProduceConfig.FILE_DOWNLOAD_URL = url;
    }

    @Value("${fileIdAttr:fileId}")
    public void setFileIdAttr(String attr) {
        MessageProduceConfig.FILE_ID_ATTR = attr;
    }

    @Value("${fileNameAttr:fileName}")
    public void setFileNameAttr(String attr) {
        MessageProduceConfig.FILE_NAME_ATTR = attr;
    }
}
