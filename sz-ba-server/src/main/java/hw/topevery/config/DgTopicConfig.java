package hw.topevery.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "hw.dg-config.topic")
public class DgTopicConfig {
    /** 业务主体对象——环卫车辆 */
    public static String DG_BASE_CAR;

    /** 业务主体对象——环卫车辆 */
    public static String DG_BASE_PERSON;

    /** 业务主体对象——服务企业 */
    public static String DG_BASE_COMPANY;

    /** 业务主体对象——项目/标段 */
    public static String DG_BASE_ITEM;

    /** 业务主体对象——履约合同 */
    public static String DG_BASE_CONTRACT;

    @Value("${dgBaseCar:topic}")
    public void setDgBaseCar(String dgBaseCar) {
        DG_BASE_CAR = dgBaseCar;
    }

    @Value("${dgBasePerson:topic}")
    public void setDgBasePerson(String dgBasePerson) {
        DG_BASE_PERSON = dgBasePerson;
    }

    @Value("${dgBaseCompany:topic}")
    public void setDgBaseCompany(String dgBaseCompany) {
        DG_BASE_COMPANY = dgBaseCompany;
    }

    @Value("${dgBaseItem:topic}")
    public void setDgBaseItem(String dgBaseItem) {
        DG_BASE_ITEM = dgBaseItem;
    }

    @Value("${dgBaseContract:topic}")
    public void setDgBaseContract(String dgBaseContract) {
        DG_BASE_CONTRACT = dgBaseContract;
    }
}
