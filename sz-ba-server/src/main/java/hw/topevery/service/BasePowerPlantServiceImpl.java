package hw.topevery.service;

import cn.hutool.core.util.ObjectUtil;
import hw.topevery.dao.MessageProduceDao;
import hw.topevery.enums.DataTypeEnum;
import hw.topevery.pojo.po.bus.FacRefusePlant;
import hw.topevery.pojo.po.trasnfer.FacFileTransfer;
import hw.topevery.pojo.po.trasnfer.FacRefusePlantTransfer;
import hw.topevery.pojo.vo.FileVO;
import hw.topevery.pojo.vo.bus.CompanyVO;
import hw.topevery.pojo.vo.bus.PowerPlantVO;
import hw.topevery.util.UploadFile;
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
public class BasePowerPlantServiceImpl extends AbstractMessageProduce<Long, PowerPlantVO> implements MessageProduceService<PowerPlantVO> {

    @Autowired
    MessageProduceDao messageProduceDao;

@Scheduled(cron = "${hw.message-produce-config.dgBasePowerPlantCron:30 * * * * ? }")
@Override
    public void run() {
        this.pushMsg();
    }

    @Override
    public List<PowerPlantVO> getPushData() {
        List<PowerPlantVO> powerPlantVOS = new ArrayList<>();
        List<FacRefusePlant> facRefusePlants = messageProduceDao.findBusBaseFacRefusePlantList();
        for(FacRefusePlant a : facRefusePlants){
            PowerPlantVO b = new PowerPlantVO();
            if(ObjectUtils.isEmpty(a) || ObjectUtils.allNull(a))
                break;
            FacRefusePlantTransfer c = messageProduceDao.findBusBaseFacRefusePlantTransfer(a.getPlantId());
            if(ObjectUtils.isEmpty(c) || ObjectUtils.allNull(c))
                break;
            b.setIncinerationPlantName(a.getPlantName());
//            b.setUnifiedSocialCreditIdentifier("统一社会信用代码");
            b.setDistrictName(c.getDistrictName());
            b.setDistrictCode(c.getDistrictCode());
            b.setStreetName(c.getStreetName());
            b.setStreetCode(c.getStreetCode());
            b.setCommunityName(c.getCommunityName());
            b.setCommunityCode(c.getCommunityCode());
            b.setAddress(a.getPosition());
            b.setGeoX(a.getGeoX());
            b.setGeoY(a.getGeoY());
            if(ObjectUtil.isNotEmpty(a.getId()))
                b.setId(a.getId().longValue());
//            b.setConstructionUnit("建设单位");
//            b.setConstructionUnitPrincipal("建设单位负责人");
//            b.setConstructionUnitPrincipalPhone("建设单位负责人电话");
            b.setOwnershipUnit(c.getResponsibilityName());
            b.setOwnershipUnitPrincipal(c.getResponsibilityPerson());
            b.setOwnershipUnitPrincipalPhone(c.getResponsibilityContact());
            b.setOperationUnit(c.getSuperviseName());
            b.setOperationUnitPrincipal(c.getSupervisePerson());
            b.setOperationUnitPrincipalPhone(c.getSuperviseContact());
//            b.setCompetentAdministrativeDepartment("行政主管部门");
//            b.setCost("造价");
//            b.setCoverArea((float)占地面积);
//            b.setStructureArea((float)建筑面积);
//            b.setIsLeachateTreatmentDevice(是否有渗滤液处理装置);
//            b.setIncineratorNumber(焚烧炉的数量);
//            b.setFlyAshTreatment("飞灰处理方式");
//            b.setFlueGasEmissionIndex("烟气排放指标");
//            b.setIsVideoMonitor(是否有视频监测);
//            b.setCameraNumber(摄像头数量);
//            b.setIsEnvironmentalMonitor(是否有环境监测);
            b.setRemark(a.getRemark());
            b.setDesignDailyProcessCapacity(c.getDesignVolume());
//            b.setDesignAnnualProcessCapacity(设计年处理能力);
//            b.setDesignAnnualPowerGeneration(设计年发电量);
//            b.setDesignWasteCalorificValue(设计垃圾热值);
//            b.setOneIncineratorDailyCapacity(单台焚烧炉日处理量);
//            b.setInstallPowerGenerationCapacity(发电装机容量);
//            b.setActualProcessLoad(实际处理负荷);
//            b.setIncinerationLineAnnualCumulativeOperateTime(焚烧线年累计运行时长);
//            b.setLeachateHandlingCapacity(渗沥液处理能力);
//            b.setStartDsate("启用日期");

            if(ObjectUtil.isNotEmpty(c.getState())){
                if(ObjectUtil.equal(1,a.getState()))
                    b.setLifeCycle("0");
                else if(ObjectUtil.equal(0,a.getState()))
                    b.setLifeCycle("1");
                else{
                }
            }
            List<FacFileTransfer> d = messageProduceDao.findBusBaseFacFileTransfer(c.getPlantId());

            List<FileVO> idcardFiles = new ArrayList<>();

            if(ObjectUtil.isNotEmpty(d)){
                for(FacFileTransfer uploadFile : d) {
                    FileVO file = new FileVO(uploadFile.getFileId(), uploadFile.getFileName());
                    idcardFiles.add(file);
                }
            }
            b.setImage(idcardFiles);

            powerPlantVOS.add(b);

        }
        return  powerPlantVOS;
    }

    @Override
    public DataTypeEnum getDataType() {
        return DataTypeEnum.FAC_WASTE_DISPOSAL;
    }
}
