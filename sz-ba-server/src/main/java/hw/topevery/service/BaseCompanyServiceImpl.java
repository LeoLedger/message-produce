package hw.topevery.service;

import cn.hutool.core.util.ObjectUtil;
import hw.topevery.dao.MessageProduceDao;
import hw.topevery.enums.DataTypeEnum;
import hw.topevery.pojo.po.bus.Company;
import hw.topevery.pojo.vo.bus.CompanyVO;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author LeoLedger
 * @Date 2024/1/23 15:58
 */


@Component
@EnableScheduling
public class BaseCompanyServiceImpl extends AbstractMessageProduce<Long, CompanyVO> implements MessageProduceService<CompanyVO> {

    @Autowired
    MessageProduceDao messageProduceDao;

    @Scheduled(cron = "${hw.message-produce-config.dgBasePersonCron:20 * * * * ? }")
    @Override
    public void run() {
        this.pushMsg();
    }

    @Override
    public List<CompanyVO> getPushData() {
        List<CompanyVO> companyVOS = new ArrayList<>();
        List<Company> companies = messageProduceDao.findBusBaseCompanyList();
        for(Company  a : companies){
            CompanyVO b = new CompanyVO();
            if(ObjectUtils.isEmpty(a) || ObjectUtils.allNull(a))
                break;
            b.setBusinessLicense(a.getOrganizationCode());
            b.setAlias(a.getName());
            b.setRepresentativePhone(a.getContactsPhone());
            b.setName(a.getName());
            b.setAddress(a.getAddress());
//            b.setType(a.getUnitType());//公司资质
            b.setRepresentative(a.getLowPeople());
            b.setCode(a.getSocialCreditCode());
            b.setLocation(a.getAddress());
            if(ObjectUtil.isNotEmpty(a.getBuildDate()))
                b.setRegistrationDate(a.getBuildDate().toString());
            b.setQualifications(a.getQualification());
//            b.setCertificateNo("资格证书编号");
//            b.setRegisteredCapital(10000.00);//注册资金
            b.setContact(a.getContacts());
            b.setContactPhone(a.getContactsPhone());
            b.setOfficeLocation(a.getAddress());
//            b.setOfficePhone("办公电话");
//            b.setLotNowNum(1);//履约标段数
//            b.setLotNum(3);//累计履约标段数
//            b.setServiceArea("履约区域");
//            b.setServiceStatus(1);//履约状态
            if(ObjectUtil.isNotEmpty(a.getId()))
                b.setId(a.getId().longValue());
            if(ObjectUtil.isNotEmpty(a.getState())){
                if(ObjectUtil.equal(0,a.getState()))
                    b.setStatus("1");
                else  if(ObjectUtil.equal(1,a.getState()))
                    b.setStatus("3");
                else {

                }

            }
            companyVOS.add(b);
        }

        return  companyVOS;
    }

    @Override
    public DataTypeEnum getDataType() {
        return DataTypeEnum.BUS_BASE_COMPANY;
    }
}
