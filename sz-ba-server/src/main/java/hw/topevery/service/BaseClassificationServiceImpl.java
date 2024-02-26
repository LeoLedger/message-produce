package hw.topevery.service;

import cn.hutool.core.util.ObjectUtil;
import hw.topevery.dao.MessageProduceDao;
import hw.topevery.enums.DataTypeEnum;
import hw.topevery.pojo.po.bus.FacRefuseClassifyPoint;
import hw.topevery.pojo.po.trasnfer.FacFileTransfer;
import hw.topevery.pojo.po.trasnfer.FacRefuseClassifyPointTransfer;
import hw.topevery.pojo.vo.FileVO;
import hw.topevery.pojo.vo.bus.ClassificationVO;
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
public class BaseClassificationServiceImpl extends AbstractMessageProduce<Long, ClassificationVO> implements MessageProduceService<ClassificationVO> {

    @Autowired
    MessageProduceDao messageProduceDao;

@Scheduled(cron = "${hw.message-produce-config.dgBaseClassificationCron:30 * * * * ? }")
@Override
    public void run() {
        this.pushMsg();
    }

    @Override
    public List<ClassificationVO> getPushData() {
        List<ClassificationVO> classificationVOS = new ArrayList<>();
        List<FacRefuseClassifyPoint> facRefuseClassifyPoints = messageProduceDao.findBusBaseFacRefuseClassifyPointList();
        for(FacRefuseClassifyPoint a :facRefuseClassifyPoints){
            ClassificationVO b = new ClassificationVO();
            if(ObjectUtils.isEmpty(a) || ObjectUtils.allNull(a))
                break;

            FacRefuseClassifyPointTransfer c = messageProduceDao.findBusBaseFacRefuseClassifyPointTransfer(a.getRefuseClassifyPointId());
            if(ObjectUtils.isEmpty(c) || ObjectUtils.allNull(c))
                break;
            List<FacFileTransfer> d = messageProduceDao.findBusBaseFacFileTransfer(c.getRefuseClassifyPointId());

            if(ObjectUtil.isNotEmpty(a.getFacType())){
                if(ObjectUtil.equal(2,a.facType)){
                    b.setPremisesType("2");
                }else if (ObjectUtil.equal(8,a.facType)) {
                    b.setPremisesType("7");
//                }else if (ObjectUtil.equal(13,a.facType)) {
//                    b.setPremisesType("分类源头");
                }else {

                }

            }
            b.setPremisesIdentification(c.getArea());
            b.setPremisesName(c.getName());
            b.setRemark(c.getRemark());

            if(ObjectUtil.isNotEmpty(a.getId()))
                b.setId(a.getId().longValue());
            List<FileVO> image  = new ArrayList<>();
            if(ObjectUtil.isNotEmpty(d)){
                for(FacFileTransfer uploadFile : d) {
                    FileVO file = new FileVO(uploadFile.getFileId(), uploadFile.getFileName());
                    image.add(file);
                }
            }

            b.setCollectionPointImage(image);
            b.setSuperviseSubjectPhone(c.getResponsibilityPersonContact());
            b.setSuperviseSubjectPrincipal(c.getResponsibilityName());
            b.setSuperviseSubject(c.getSuperviseName());
            if(ObjectUtils.isNotEmpty(c.getBuildInfo())){
                if(ObjectUtil.equal(0,c.getBuildInfo()))
                    b.setLifeCycle("0");
                else if (ObjectUtil.equal(1,c.getBuildInfo()))
                    b.setLifeCycle("1");
                else {

                }
            }
            b.setProductionWasteUnit(c.getFacTypeName());
//            b.setIsPropertyCommunityCollectionPoint("是否为物业小区收集点");
//            b.setIsHydroelectricFacilities("有无水电设施");
//            b.setBucketPlacement("放置桶数");
//            b.setTrashCanType("垃圾桶类型");
            b.setManagerPhone(c.getSupervisePersonContact());
            b.setManager(c.getSupervisePerson());
//            b.setCompetentAdministrativeDepartment("行政主管部门");
            b.setOperationUnitPrincipalPhone(c.getSupervisePersonContact());
            b.setOperationUnitPrincipal(c.getSupervisePerson());
            b.setOperationUnit(c.getSuperviseName());
            b.setOwnershipUnitPrincipalPhone(c.getResponsibilityPersonContact());
            b.setOwnershipUnitPrincipal(c.getResponsibilityPerson());
            b.setOwnershipUnit(c.getResponsibilityName());
//            b.setConstructionUnitPrincipal("建设单位负责人");
//            b.setConstructionUnit("建设单位");
//            b.setStartDsate("启用日期");
            if(ObjectUtils.isNotEmpty(c.getGeoY()))
                b.setGeoY(c.getGeoY().toString());
            if(ObjectUtils.isNotEmpty(c.getGeoY()))
                b.setGeoX(c.getGeoX().toString());
            b.setAddress(c.getPosition());
            if(ObjectUtils.isNotEmpty(c.getType())){
                if(ObjectUtil.equal(0,c.getType()))
                    b.setDropPointCategory("1");
                else if (ObjectUtil.equal(1,c.getType()))
                    b.setDropPointCategory("2");
                else if (ObjectUtil.equal(2,c.getType())){
                    b.setLitterableType("4");
                }else {

                }
            }

//            if(ObjectUtils.isNotEmpty(c.getComponentType())){
//                if(ObjectUtil.equal(0,c.getComponentType()))
//                    b.setLitterableType("环卫部件");
//                else if (ObjectUtil.equal(1,c.getComponentType()))
//                    b.setLitterableType("分类部件");
//                else if (ObjectUtil.equal(2,c.getComponentType())){
//                    b.setLitterableType("环卫/分类部件");
//                }else {
//
//                }
//            }
            b.setCommunityCode(c.getCommunityCode());
            b.setCommunityName(c.getCommunityName());
            b.setStreetCode(c.getStreetCode());
            b.setStreetName(c.getStreetName());
            b.setDistrictCode(c.getDistrictCode());
            b.setDistrictName(c.getArea());
            b.setDropPointName(c.getName());
            b.setDropPointCode(c.getName());
//            b.setOpenTime("开放时间");
//            b.setEndTime("结束时间");


            classificationVOS.add(b);

        }
        return  classificationVOS;
    }

    @Override
    public DataTypeEnum getDataType() {
        return DataTypeEnum.FAC_COLLECT_POINT;
    }
}
