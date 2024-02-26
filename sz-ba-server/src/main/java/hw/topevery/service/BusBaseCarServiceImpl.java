package hw.topevery.service;

import hw.topevery.dao.MessageProduceDao;
import hw.topevery.enums.DataTypeEnum;
import hw.topevery.pojo.po.bus.Car;
import hw.topevery.pojo.po.bus.Section;
import hw.topevery.pojo.po.bus.SysDept;
import hw.topevery.pojo.po.trasnfer.CarFileTransfer;
import hw.topevery.pojo.po.trasnfer.SysDictTransfer;
import hw.topevery.pojo.vo.FileVO;
import cn.hutool.core.util.ObjectUtil;
import hw.topevery.pojo.vo.bus.CarVO;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author LeoLedger
 * @description: 生产者基础服务接口
 * @date 2023-12-23 16:19
 */
@Component
@EnableScheduling
public class BusBaseCarServiceImpl extends AbstractMessageProduce<Long, CarVO> implements MessageProduceService<CarVO> {

    @Autowired
    MessageProduceDao messageProduceDao;

    @Scheduled(cron = "${hw.message-produce-config.dgBasePersonCron:2 * * * * ? }")
    @Override
    public void run() {
        this.pushMsg();
    }

    @Override
    public List<CarVO> getPushData() {
        List<CarVO> carVOList = new ArrayList<>();
        List<Car> carsList =  messageProduceDao.findBusBaseCarList();
        List<SysDictTransfer> e = messageProduceDao.findBusBaseSysDictTransfer();
        List<SysDept> sysDepts = messageProduceDao.findBusBaseSysDeptList();
        List<Section> sections = messageProduceDao.findBusBaseSectionList();

        for(Car a :carsList ){
            CarVO b = new CarVO();
            if(ObjectUtils.isEmpty(a) || ObjectUtils.allNull(a))
                break;


            b.setCarNumber(a.no);
            b.setEngineNumber(a.engineNo);
            b.setDynamicType(a.powerType);
            for(Section item:sections){
                if(ObjectUtil.equal(item.getSectionId(),a.sectionId)){
                    b.setOwningLot(item.getName());
                    break;
                }
            }

            if(ObjectUtils.isNotEmpty(a.ownerType)){
                if(a.ownerType.equals(0))
                    b.setOwnershipType("1");
                if(a.ownerType.equals(1))
                    b.setOwnershipType("2");
            }
            if(ObjectUtils.isNotEmpty(a.workState))
                b.setStatus(a.workState.toString());
            if(ObjectUtils.isNotEmpty(a.buyDate))
                b.setAcquisitionDate(a.buyDate.toString());
            if(ObjectUtils.isNotEmpty(a.startDate))
                b.setOperationalDate(a.startDate.toString());
            if(ObjectUtils.isNotEmpty(a.yearExamineDate))
                b.setAnnualInspectionDate(a.yearExamineDate.toString());
            if(ObjectUtils.isNotEmpty(a.drivingLicenseExpireDate))
                b.setDrivingLicenseRegistrationDate(a.drivingLicenseRegisterDate.toString());
            if(ObjectUtils.isNotEmpty(a.drivingLicenseExpireDate))
                b.setDrivingLicenseInspectionValidity(a.drivingLicenseExpireDate.toString());
            for(SysDept item:sysDepts){
                if (ObjectUtil.equal(item.getDeptId(),a.companyId)){
                    b.setAffiliatedEnterprise(item.getDeptName());
                }else if (ObjectUtil.equal(item.getDeptId(),a.teamId)) {
                    b.setAffiliatedEnterprise(item.getDeptName());
                }else if (ObjectUtil.equal(item.getDeptId(),a.useDeptId)) {
                    b.setResponsibleDepartment(item.getDeptName());
                }else{

                }
            }


            b.setCarBrand(a.brand);
            b.setCarModels(a.vehicleType);
//           无数据： /**
//             * 车牌颜色
//             */
//            private String carLicenseColor;
//            /**
//             * 车辆长度
//             */
//            private Double carLength;
//            /**
//             * 车辆宽度
//             */
//            private Double carWidth;
//            /**
//             * 车辆高度
//             */
//            private Double carHeight;
            if(ObjectUtil.isNotEmpty(a.getId()))
                b.setId(a.getId().longValue());
            if(ObjectUtil.isNotEmpty(a.vehicleTare))
                b.setTotalMass(a.vehicleTare.doubleValue());
            if(ObjectUtil.isNotEmpty(a.vehicleLoad))
                b.setReadyLoadMass(a.vehicleLoad.doubleValue());
            if(ObjectUtil.isNotEmpty(a.maximumPayload))
                b.setApprovedLoadMass(a.maximumPayload.doubleValue());
//            无数据 ：parkingSpace, videoAccessOrNot，mandatoryRetirementDate，leasePurchaseContract，commercialInsuranceAttachment
            List<CarFileTransfer> d = messageProduceDao.findBusBaseCarFileTransfer(a.getCarId());

            if(ObjectUtil.isNotEmpty(d)){
                for(CarFileTransfer uploadFile : d) {
                    if (ObjectUtil.equal(1,uploadFile.getDataType())){
                        FileVO file1=new  FileVO(uploadFile.getFileId(), uploadFile.getFileName());
                        b.setCarPhoto(file1);
                        break;
                    }
                }
            }

            List<FileVO> contract = new ArrayList<>();

            if(ObjectUtil.isNotEmpty(d)){
                for(CarFileTransfer uploadFile : d) {
                    if (ObjectUtil.equal(3,uploadFile.getDataType())){
                        FileVO file=new  FileVO(uploadFile.getFileId(), uploadFile.getFileName());
                        contract.add(file);
                    }
                }
            }
            b.setLeasePurchaseContract(contract);
            // 无数据： CommercialInsuranceAttachment
            b.setGpsAccessOrNot(a.isOpenPotential);
            b.setDriver(a.driverName);
            if(ObjectUtil.isNotEmpty(e)){
                for(SysDictTransfer item : e) {
                   if(ObjectUtil.equal(a.getPowerType(),item.getDictId())){
                       if(ObjectUtil.equal("已做DPF改造的柴油车",item.getDictName()) || ObjectUtil.equal("柴油车",item.getDictName())|| ObjectUtil.equal("汽油车",item.getDictName())){
                           b.setDynamicType("1");
                       }else if(ObjectUtil.equal("电动车",item.getDictName())){
                           b.setDynamicType("3");
                       }else{

                       }
                   }
                }
            }
            b.setDriverPhoneNumber(a.driverTel);
            if(ObjectUtil.isNotEmpty(a.businessExamineDate))
                b.setCommercialInsuranceDeadline(a.businessExamineDate.toString());
            b.setRemark(a.remark);
            carVOList.add(b);
        }
        return  carVOList;
    }

    @Override
    public DataTypeEnum getDataType() {
        return DataTypeEnum.BUS_BASE_CAR;
    }
}
