package hw.topevery.service;

import cn.hutool.core.util.ObjectUtil;
import hw.topevery.dao.MessageProduceDao;
import hw.topevery.enums.DataTypeEnum;
import hw.topevery.pojo.po.bus.Person;
import hw.topevery.pojo.po.bus.Section;
import hw.topevery.pojo.po.bus.SysDept;
import hw.topevery.pojo.po.bus.SysMap;
import hw.topevery.pojo.po.trasnfer.PersonTransfer;
import hw.topevery.pojo.vo.GeometryVO;
import hw.topevery.pojo.vo.bus.ItemVO;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @Author LeoLedger
 * @Date 2024/1/23 15:58
 */


@Component
@EnableScheduling
public class BaseItemServiceImpl extends AbstractMessageProduce<Long, ItemVO> implements MessageProduceService<ItemVO> {

    @Autowired
    MessageProduceDao messageProduceDao;

@Scheduled(cron = "${hw.message-produce-config.dgBasePersonCron:20 * * * * ? }")

@Override
    public void run() {
        this.pushMsg();
    }

    @Override
    public List<ItemVO> getPushData() {
        List<Section> sections = messageProduceDao.findBusBaseSectionList();
        List<SysDept> sysDepts = messageProduceDao.findBusBaseSysDeptList();
        List<Person> personList = messageProduceDao.findBusBasePersonList();
        List<SysMap> sysMaps = messageProduceDao.findBusBaseSysMapList();

        List<ItemVO> itemVOS = new ArrayList<>();
        for (Section a  : sections){
            ItemVO b = new ItemVO();
            if(ObjectUtils.isEmpty(a) || ObjectUtils.allNull(a))
                break;
            b.setName(a.getName());
            if(ObjectUtil.isNotEmpty(a.getCode())){
                b.setID(a.getCode());
            }else {
                b.setID("NoData");
            }

            for(SysMap i : sysMaps){
                if(ObjectUtil.equal(a.getDistrictId(),i.getMapId())){
                    b.setDistrictName(i.getName());
                    b.setDistrictCode(i.getCode());
                }
                if(ObjectUtil.equal(a.getStreetId(),i.getMapId())){
                    b.setStreetName(i.getName());
                    b.setStreetCode(i.getCode());
                }

            }
            if(ObjectUtil.isNotEmpty(a.getSectionType())){
                if(ObjectUtil.equal(1,a.getSectionType())){
                    b.setType("1");
                }else if (ObjectUtil.equal(2,a.getSectionType())){
                    b.setType("2");
                }else if (ObjectUtil.equal(3,a.getSectionType())){
                    b.setType("4");
                }else if (ObjectUtil.equal(4,a.getSectionType())){
                    b.setType("5");
                }else{

                }
            }
            if(ObjectUtil.isNotEmpty(a.getProjectTotalAmount()))
                b.setContractAmount(a.getProjectTotalAmount().doubleValue());
            b.setServiceContent(a.getServiceContent());

            List<String> manager = new ArrayList<>();
            List<String> phone = new ArrayList<>();
            if (StringUtils.isNotBlank(a.getProjectManagerIdList())) {
                List<String> projectManagerIdList = Arrays.asList(a.getProjectManagerIdList().split(","));
                for(Person person : personList){
                    for(String it : projectManagerIdList){
                        if(ObjectUtil.equal(person.getPersonId(),it)){
                            manager.add(person.getName());
                            phone.add(person.getDrawNum());
                        }
                    }

                }
            }

            if(ObjectUtil.isNotEmpty(manager)){
                b.setProjectManager(manager.toString());
            }
            if(ObjectUtil.isNotEmpty(phone)){
                b.setProjectManagerPhone(phone.toString());
            }

            GeometryVO geometryVO = new GeometryVO(a.getGeomCol());
            b.setPolygon(geometryVO);
            if(ObjectUtil.isNotEmpty(a.getMeasure()))
                b.setArea(a.getCalculatedArea().doubleValue());
            if(ObjectUtil.isNotEmpty(a.getProjectServiceStart()))
                b.setServiceDateBegin(a.getProjectServiceStart().toString());
            if(ObjectUtil.isNotEmpty(a.getProjectServiceEnd()))
                b.setServiceDateEnd(a.getProjectServiceEnd().toString());
            if(ObjectUtil.isNotEmpty(a.getContractType())){
                if(ObjectUtil.equal(1,a.getContractType()))
                    b.setWay("一年一签");
                if(ObjectUtil.equal(2,a.getContractType()))
                    b.setWay("三年一签");
            }
            String Company =null ;
            String w =null ;
            for (SysDept i : sysDepts){
                if(ObjectUtil.equal(i.getDeptId(),a.getMainManageDeptId()))
                    Company = i.getDeptName();
                if(ObjectUtil.equal(i.getDeptId(),a.getTeamId()))
                    w = i.getDeptName();
            }
            b.setRegulatoryBodie(Company);
//            b.setRegulatoryBodieHead("监管主体负责人");
//            b.setRegulatoryBodieHeadPhone("监管主体联系电话");
            b.setResponsibleParty(w);
//            b.setResponsiblePartyHead("责任主体负责人");
//            b.setResponsiblePartyHeadPhone("责任主体联系电话");
            if(ObjectUtil.isNotEmpty(a.getDbStatus())){
                if(ObjectUtil.equal(0,a.getDbStatus())){
                    b.setResidenceType("1");
                }else if(ObjectUtil.equal(1,a.getDbStatus())){
                    b.setResidenceType("2");
                }
            }

            b.setNote(a.getRemark());
            itemVOS.add(b);
        }

        return  itemVOS;
    }

    @Override
    public DataTypeEnum getDataType() {
        return DataTypeEnum.BUS_BASE_ITEM;
    }
}
