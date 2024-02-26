package hw.topevery.service;

import cn.hutool.core.util.ObjectUtil;
import hw.topevery.dao.MessageProduceDao;
import hw.topevery.enums.DataTypeEnum;
import hw.topevery.pojo.po.bus.Car;
import hw.topevery.pojo.po.bus.Section;
import hw.topevery.pojo.po.bus.SectionContract;
import hw.topevery.pojo.po.bus.SysDept;
import hw.topevery.pojo.vo.bus.ContractVO;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.http.annotation.Contract;
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
public class BaseContractServiceImpl extends AbstractMessageProduce<Long, ContractVO> implements MessageProduceService<ContractVO> {

    @Autowired
    MessageProduceDao messageProduceDao;

@Scheduled(cron = "${hw.message-produce-config.dgBaseContractCron:20 * * * * ? }")
@Override
    public void run() {
        this.pushMsg();
    }

    @Override
    public List<ContractVO> getPushData() {
        List<SectionContract> contracts =  messageProduceDao.findBusBaseContractList();
        List<SysDept> sysDepts = messageProduceDao.findBusBaseSysDeptList();
        List<Section> sections = messageProduceDao.findBusBaseSectionList();
        List<ContractVO> contractVOS = new ArrayList<>();
        for(SectionContract a : contracts){
            ContractVO b =new ContractVO();
            if(ObjectUtils.isEmpty(a) || ObjectUtils.allNull(a))
                break;
            if(ObjectUtil.isNotEmpty(a.getContractCode())){
                b.setID(a.getContractCode());
            }else {
                b.setID("NoData");
            }
            b.setName(a.getContractName());
            if(ObjectUtil.isNotEmpty(a.getContractType())){
                if(ObjectUtil.equal(1,a.getContractType()))
                    b.setType("一年一签");
                if(ObjectUtil.equal(2,a.getContractType()))
                    b.setType("三年一签");
            }
            String Company = null;
            String w = null;
            String e = null;
            for (SysDept i : sysDepts){
                if(ObjectUtil.equal(i.getDeptId(),a.getCompanyId()))
                    Company = i.getDeptName();
                if(ObjectUtil.equal(i.getDeptId(),a.getManageDeptId()))
                    w = i.getDeptName();
            }
            for(Section i : sections){
                if(ObjectUtil.equal(i.getSectionId(),a.getSectionId())){
                    e = i.getName();
                    break;
                }

            }

            if(ObjectUtil.isNotEmpty(Company))
                b.setCompany(Company);
            b.setLot(e);
            if(ObjectUtil.isNotEmpty(w))
                b.setRegulatoryBodie(w);
            if(ObjectUtil.isNotEmpty(a.getBiddingDate()))
                b.setBidderDate(a.getBiddingDate().toString());
            if(ObjectUtil.isNotEmpty(a.getBiddingMoney()))
                b.setBidderMoney(a.getBiddingMoney().doubleValue());
            if(ObjectUtil.isNotEmpty(a.getServiceStartDate()))
                b.setServiceDateBegin(a.getServiceStartDate().toString());
            if(ObjectUtil.isNotEmpty(a.getServiceEndDate()))
            b.setServiceDateEnd(a.getServiceEndDate().toString());
            if(ObjectUtil.isNotEmpty(a.getContractYear())){
                if(ObjectUtil.equal(1,a.getContractType()))
                    b.setContractTerm("一年");
                if(ObjectUtil.equal(2,a.getContractType()))
                    b.setContractTerm("两年");
                if(ObjectUtil.equal(3,a.getContractType()))
                    b.setContractTerm("三年");
            }


//            b.setContractWay("合同方式");
            b.setPartyA(a.getContactsA());
            b.setPartyAPhone(a.getContactsAPhone());
            if(ObjectUtil.isNotEmpty(a.getId()))
                b.setId(a.getId().longValue());
            b.setPartyB(a.getContactsB());
            b.setPartyBPhone(a.getContactsBPhone());
            b.setSupervision(a.getContactsC());
            b.setSupervisionPhone(a.getContactsCPhone());
            b.setStatus(a.getState());
            b.setWork(a.getWorkContent());
            b.setWorkScope(a.getWorkRange());
            b.setNotes(a.getRemark());
            contractVOS.add(b);
        }
        return  contractVOS;
    }

    @Override
    public DataTypeEnum getDataType() {
        return DataTypeEnum.BUS_BASE_CONTRACT;
    }
}
