package hw.topevery.service;

import cn.hutool.core.util.ObjectUtil;
import hw.topevery.dao.MessageProduceDao;
import hw.topevery.enums.DataTypeEnum;
import hw.topevery.pojo.po.bus.FacRefuseStation;
import hw.topevery.pojo.po.bus.Section;
import hw.topevery.pojo.po.trasnfer.FacFileTransfer;
import hw.topevery.pojo.po.trasnfer.FacRefuseStationTransfer;
import hw.topevery.pojo.vo.FileVO;
import hw.topevery.pojo.vo.bus.StationVO;
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
public class BaseStationServiceImpl extends AbstractMessageProduce<Long, StationVO> implements MessageProduceService<StationVO> {

    @Autowired
    MessageProduceDao messageProduceDao;

    @Scheduled(cron = "${hw.message-produce-config.dgBaseStationCron:30 * * * * ? }")
    @Override
    public void run() {
        this.pushMsg();
    }

    @Override
    public List<StationVO> getPushData() {
        List<StationVO> stationVOS = new ArrayList<>();
        List<FacRefuseStation> sectionList = messageProduceDao.findBusBaseFacRefuseStationList();

        for(FacRefuseStation a : sectionList){
            StationVO b = new StationVO();
            if(ObjectUtils.isEmpty(a) || ObjectUtils.allNull(a))
                break;
            FacRefuseStationTransfer c = messageProduceDao.findBusBaseFacRefuseStationTransfer(a.getRefuseStationId());
            if(ObjectUtils.isEmpty(c) || ObjectUtils.allNull(c))
                break;
            b.setTransferStationName(a.getRefuseStationName());
//            b.setTransferStationCode(a.getDeviceCode());
            if(ObjectUtil.isNotEmpty(a.getStationType())){
                if(ObjectUtil.equal(1,a.getStationType()))
                    b.setTransferStationCategory("1");
                else if(ObjectUtil.equal(2,a.getStationType()))
                    b.setTransferStationCategory("2");
                else if(ObjectUtil.equal(3,a.getStationType()))
                    b.setTransferStationCategory("3");
                else{
                }
            }
            if(ObjectUtil.isNotEmpty(a.getId()))
                b.setId(a.getId().longValue());
//            b.setTransferStationScale("中转站规模");
//            b.setTransferRefuseType("转运垃圾类型");
//            b.setEquityOwnership(c.getResponsibilityName());
            b.setTransferStationAddress(a.getPosition());
            b.setDistrictName(c.getDistrictName());
            b.setDistrictCode(c.getDistrictCode());
            b.setStreetName(c.getStreetName());
            b.setStreetCode(c.getStreetCode());
            b.setCommunityName(c.getCommunityName());
            b.setCommunityCode(c.getCommunityCode());
            b.setOwningLot(c.getSectionName());
//            b.setServiceArea("服务范围");
            if(ObjectUtil.isNotEmpty(c.getTransformationTime()))
                b.setConstructionDate(c.getTransformationTime().toString());
//            b.setOperationalDate("投入使用日期");
//            b.setStructureArea(建筑面积);
//            b.setCoverArea(占地面积);
//            b.setConstructionUnit("建设单位");
//            b.setConstructionUnitPrincipal("建设单位负责人");
//            b.setConstructionUnitPrincipalPhone("建设单位负责人电话");
//            b.setEndTime("开放结束时间");
//            b.setRFIDDeviceID("RFID设备ID");
            b.setRFIDDeviceCode(c.getDeviceCode());
//            b.setIsPublicToilet(是否有公厕);
//            b.setDesignDailyTransitCapacity(日均设计转运能力);
//            b.setDailyTansports(日转运量);
//            b.setDailyTransferTimes(日转运次数);
//            b.setStaffNumber(工作人员数);
//            b.setCompressionTankCapacity(压缩箱压缩能力);
            b.setCompressionBoxesNumber(c.getLjysxNum());
//            b.setMonthlyWaterCharge(每月水费);
//            b.setMonthlyElectricityCharge(每月电费);
//            b.setVideoSurveillanceEquipment(视频监控设备);
//            b.setCameraNumber(摄像投数量);
//            b.setIsMeasureWeighEquipment(有无计量称重设备);
//            b.setIsGasMonitorEquipment(有无气体监测设备);
//            b.setIsDeodorizer(有无除臭设备);
//            b.setIsSpraySystem(有无喷淋系统);
//            b.setIsHighPressureWashEquipment(有无高压冲洗设备);
//            b.setIsFireEquipment(有无消防器材);
//            b.setIsRainSewageDiversion(有无雨污分流);
//            b.setDeodorantEquipmentSupplier(除臭设备供货商);
//            b.setDeodorizationEquipmentContractPrice(除臭设备合同价);
//            b.setDeodorizationEquipmentProcess(除臭设备除臭工艺);
//            b.setDesignSchemeReviewStatus(设计方案评审情况);
//            b.setIsLandUseProcedureComplete(用地手续是否完备);
            b.setManager(c.getSupervisePerson());
            b.setManagerPhone(c.getSuperviseContact());
            b.setGeoX(a.getGeoX());
            b.setGeoY(a.getGeoY());

            if(ObjectUtil.isNotEmpty(a.getStatus())){
                if(ObjectUtil.equal(1,a.getStatus()))
                    b.setLifeCycle("1");
                else if(ObjectUtil.equal(0,a.getStatus()))
                    b.setLifeCycle("0");
                else if(ObjectUtil.equal(2,a.getStatus()))
                    b.setLifeCycle("2");
                else if(ObjectUtil.equal(3,a.getStatus()))
                    b.setLifeCycle("3");
                else if(ObjectUtil.equal(4,a.getStatus()))
                    b.setLifeCycle("4");
                else{
                }
            }
            b.setRemark(a.getRemark());
            b.setPremisesName(c.getRefuseStationName());

//            if(ObjectUtils.isNotEmpty(c.getComponentType())){
//                if(ObjectUtil.equal(0,c.getComponentType()))
//                    b.setPremisesType("环卫部件");
//                else if (ObjectUtil.equal(1,c.getComponentType()))
//                    b.setPremisesType("分类部件");
//                else if (ObjectUtil.equal(2,c.getComponentType())){
//                    b.setPremisesType("环卫/分类部件");
//                }else {
//
//                }
//            }
            List<FileVO> idcardFiles = new ArrayList<>();
            List<FacFileTransfer> d = messageProduceDao.findBusBaseFacFileTransfer(c.getRefuseStationId());
            if(ObjectUtil.isNotEmpty(d)){
                for(FacFileTransfer uploadFile : d) {
                    FileVO file = new FileVO(uploadFile.getFileId(), uploadFile.getFileName());
                    idcardFiles.add(file);
                }
            }

//            b.setPremisesIdentification(所属场所标识);
            b.setTransferStationImage(idcardFiles);
//            b.setDeodorantDeviceInstallationTime(除臭设备安装时间);
//            b.setOpenTime(开放开始时间);
            stationVOS.add(b);

        };
        return  stationVOS;
    }

    @Override
    public DataTypeEnum getDataType() {
        return DataTypeEnum.FAC_STATION;
    }
}
