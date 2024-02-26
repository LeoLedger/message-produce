package hw.topevery.service;

import cn.hutool.core.util.ObjectUtil;
import hw.topevery.dao.MessageProduceDao;
import hw.topevery.enums.DataTypeEnum;
import hw.topevery.pojo.po.bus.FacToilet;
import hw.topevery.pojo.po.trasnfer.FacFileTransfer;
import hw.topevery.pojo.po.trasnfer.FacToiletTransfer;
import hw.topevery.pojo.po.trasnfer.ImageTransfer;
import hw.topevery.pojo.vo.FileVO;
import hw.topevery.pojo.vo.bus.ItemVO;
import hw.topevery.pojo.vo.bus.ToiletVO;
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
public class BaseToiletServiceImpl extends AbstractMessageProduce<Long, ToiletVO> implements MessageProduceService<ToiletVO> {

    @Autowired
    MessageProduceDao messageProduceDao;

@Scheduled(cron = "${hw.message-produce-config.dgBaseToiletCron:30 * * * * ? }")
@Override
    public void run() {
        this.pushMsg();
    }

    @Override
    public List<ToiletVO> getPushData() {
        List<ToiletVO> toiletVOS = new ArrayList<>();
        List<FacToilet> facToilets = messageProduceDao.findBusBaseFacToiletList();
        for(FacToilet a:facToilets){
            ToiletVO b = new ToiletVO();
            if(ObjectUtils.isEmpty(a) || ObjectUtils.allNull(a))
                break;
            FacToiletTransfer c = messageProduceDao.findBusBaseFacToiletTransfer(a.getToiletId());
            if(ObjectUtils.isEmpty(c) || ObjectUtils.allNull(c))
                break;
            b.setToiletName(a.getToiletName());
            if(ObjectUtil.isNotEmpty(a.getId()))
                b.setId(a.getId().longValue());
            b.setToiletCode(c.getToiletName());
            if(ObjectUtil.isNotEmpty(c.getPropertyType())){
                if(ObjectUtil.equal(1,c.getPropertyType())){
                    b.setToiletType("1");
                }else if(ObjectUtil.equal(2,c.getPropertyType())){
                    b.setToiletType("0");

                }
            }
//            b.setToiletOwnershipPlace("公厕所属场所");
            b.setToiletAddress(a.address);
            b.setGeoX(a.getGeoX());
            b.setGeoY(a.geoY);
            b.setDistrictName(c.getDistrictName());
            b.setDistrictCode(c.getDistrictCode());
            b.setStreetName(c.getStreetName());
            b.setStreetCode(c.getStreetCode());
            b.setCommunityName(c.getCommunityName());
            b.setCommunityCode(c.getCommunityCode());
//            b.setStructureArea(建筑面积);
//            b.setCoverArea(占地面积);
            b.setPlacePrincipal(c.getSupervisePerson());
            b.setPrincipalPhone(c.getSupervisePersonTel());

//            b.setInitialConstructionDate("初建日期");
//            b.setStartDate("启用日期");
//            b.setCost("造价");
            b.setMenPedestalToiletNumber(a.getMaleSit());
            b.setMenSquatToiletNumber(a.getManDwNum());
            b.setLadiesPedestalToiletNumber(a.getFemaleSit());
            b.setLadiesSquatToiletNumber(a.getWomanDwNum());
            b.setMenToiletStationNumber(a.getManZwNum());
            if(ObjectUtil.equal(4,a.getType()))
                b.setIsPublicFacilityTransfer(1);
//            b.setPublicFacilityTransferTime("公建配套移交时间");


            b.setIsAccessibleToilet(a.getThreeToilet());
            b.setIsUnisexToilet(a.getThreeToilet());
            b.setIsUnisexToiletNumber(a.getOnsexToiletNum());
            b.setIsBabyCareRoom(a.getInfantRoom());
            b.setIsMirror(a.getMirror());
//            b.setIsMechanicalExhaust(是否有机械排风);
            if(ObjectUtil.isNotEmpty(a.getNumHandDryer()))
                if (ObjectUtil.compare(0, a.getNumHandDryer())>0) {
                    b.setIsHandDryer(1);
                } else {
                    b.setIsHandDryer(0);
                }
//            b.setIsToiletHandTowel(c.getHasFreePaper());
            b.setIsHandSanitizer(c.getHasLiquidSoap());
            b.setIsFreeToiletPaper(c.getHasFreePaper());
//            b.setIsConfigurateWIFI(是否配置wifi);
//            b.setIsSmartToilet(是否智能公厕);
//            b.setIsGasMonitorEquipment(有无气体监测设备);
            b.setIsChildrenHandWashDish(c.getHasChildWashBasin());
            b.setIsHandrail(c.getHasHandrail());
            b.setIsBabyToilet(c.getHasChildUrinal());
            b.setIsFlushEquipment(c.getHasFlushEqu());
            b.setIsHandBasin(c.getHasWashBasin());
//            b.setIsWheelchairAccess(是否有无障碍通道);
            b.setIsDeodorizer(c.getHasDeodorizationEqu());
//            b.setIsBroadcastSystem(是否有广播系统);
            b.setIsFireFightingApparatus(c.getHasFireControlEqu());
            b.setIsEquipmentRoom(c.getHasDeviceRoom());
            b.setIsManagementRoom(c.getHasManagementRoom());
//            b.setIsToiletSign(是否有标识);
            b.setCleanForm("1");
//            b.setDesignSchemeReviewStatus("设计方案评审情况");
            List<FileVO> image = new ArrayList<>();
            List<FacFileTransfer> d = messageProduceDao.findBusBaseFacFileTransfer(c.getToiletId());
            if(ObjectUtil.isNotEmpty(d)){
                for(FacFileTransfer uploadFile : d) {
                    FileVO file = new FileVO(uploadFile.getFileId(), uploadFile.getFileName());
                    image.add(file);
                }
            }

            b.setImage(image);
            b.setRemark(a.getRemark());
//            b.setToilcode("公厕行业");

            if(ObjectUtil.isNotEmpty(a.getStatus())) {
                if (ObjectUtil.equal(0, a.getStatus())) {
                    b.setLifeCycle("0");
                } else if (ObjectUtil.equal(1, a.getStatus())) {
                    b.setLifeCycle("1");
                } else if (ObjectUtil.equal(2, a.getStatus())) {
                    b.setLifeCycle("2");
                } else if (ObjectUtil.equal(3, a.getStatus())) {
                    b.setLifeCycle("3");
                } else if (ObjectUtil.equal(4, a.getStatus())) {
                    b.setLifeCycle("4");
                } else {

                }
            }
            if(ObjectUtil.isNotEmpty(a.getOpenStartTime()))
                b.setOpenTime(a.getOpenStartTime().toString());
            if(ObjectUtil.isNotEmpty(a.getOpenEndTime()))
                b.setEndTime(a.getOpenEndTime().toString());

            toiletVOS.add(b);
        }
        return  toiletVOS;
    }

    @Override
    public DataTypeEnum getDataType() {
        return DataTypeEnum.FAC_TOILET;
    }
}
