package hw.topevery.service;

import cn.hutool.core.util.ObjectUtil;
import hw.topevery.dao.MessageProduceDao;
import hw.topevery.enums.DataTypeEnum;
import hw.topevery.pojo.po.bus.Person;
import hw.topevery.pojo.po.trasnfer.FacFileTransfer;
import hw.topevery.pojo.po.trasnfer.PersonFileTransfer;
import hw.topevery.pojo.po.trasnfer.PersonTransfer;
import hw.topevery.pojo.vo.FileVO;
import hw.topevery.pojo.vo.bus.PersonVO;
import hw.topevery.util.UploadFile;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;

/**
 * @author LeoLedger
 * @description: 生产者基础服务接口
 * @date 2023-12-23 16:19
 */
@Component
@EnableScheduling
public class BusBasePersonServiceImpl extends AbstractMessageProduce<Long, PersonVO> implements MessageProduceService<PersonVO> {

    @Autowired
    MessageProduceDao messageProduceDao;

    @Scheduled(cron = "${hw.message-produce-config.dgBasePersonCron:-}")
    @Override
    public void run() {
        this.pushMsg();
    }

    @Override
    public List<PersonVO> getPushData() {
        List<PersonVO> personVOList = new ArrayList<>();
        List<Person> personList =  messageProduceDao.findBusBasePersonList();
        for (Person a :personList ){
            PersonVO b = new PersonVO();
            if(ObjectUtils.isEmpty(a) || ObjectUtils.allNull(a))
                break;
            PersonTransfer c = messageProduceDao.findBusBasePersonTransfer(a.getPersonId());
            if(ObjectUtils.isEmpty(c) || ObjectUtils.allNull(c))
                break;


            if(ObjectUtil.isNotEmpty(a.getId()))
                b.setId(a.getId().longValue());
            BeanUtils.copyProperties(a,b);
            b.setPersonCode(a.getCode());
            b.setPersonName(a.getName());
            b.setGender(ObjectUtil.equal(a.getSex(),0)?"男" :"女");
            b.setIdNumber(a.getIdcard());
            if(ObjectUtil.isNotNull(a.getBirthday()))
             b.setBirthdate(a.getBirthday().toString());
            b.setEducation(c.getEducationName());

            if(ObjectUtil.isNotEmpty(a.getBirthday())){
                LocalDate now = LocalDate.now(); // 获取当前日期
                Period period = Period.between(a.getBirthday(), now); // 计算出生日期和当前日期之间的时间差
                int age = period.getYears(); // 获取时间差中的年份，即为年龄
                b.setAge(age);
            }

            b.setRegister(a.getNativePlace());
            b.setHomeTelephone(a.getDrawNum());
            b.setPhoneNumber(a.getDrawNum());
          //无数据：紧急联系人  emergencyContact，  紧急联系人电话 emergencyContactNumber，特殊情况说明 specialCaseDescription
            b.setResidentialAddress(a.getAddress());
            b.setJobType(ObjectUtil.equal(a.getPersonWorkType(),1)?"清扫":"清运");
            b.setPersonnelType(a.getPersonType());
            b.setPersonnelStatus(ObjectUtil.equal(a.getWorkState(),0)?"1":"2");
            if(ObjectUtil.isNotNull(a.getJoinTime()))
                b.setHireDate(a.getJoinTime().toString());
            b.setSubordinateCompany(c.getTeamName());
            b.setOwningLot(c.getSectionName());
            b.setDeviceNumber(c.getDeviceCode());
            b.setSocialSecurityNumber(a.getSecurityNo());
            b.setRemark(a.getRemark());

            List<PersonFileTransfer> d = messageProduceDao.findBusBasePersonFileTransfer(c.getPersonId());
            List<FileVO> idCard = new ArrayList<>();
            if(ObjectUtil.isNotEmpty(d)){
                for(PersonFileTransfer uploadFile : d) {
                    if (ObjectUtil.equal(2,uploadFile.getDataType())){
                        FileVO file = new FileVO(uploadFile.getFileId(), uploadFile.getFileName());
                        idCard.add(file);
                    }

                }
            }
            b.setIdAttachment(idCard);

            if(ObjectUtil.isNotEmpty(d)){
                for(PersonFileTransfer uploadFile : d) {
                    if (ObjectUtil.equal(1,uploadFile.getDataType())){
                        FileVO file1=new  FileVO(uploadFile.getFileId(), uploadFile.getFileName());
                        b.setProfilePhoto(file1);
                        break;
                    }
                }
            }

            List<FileVO> quality = new ArrayList<>();
            if(ObjectUtil.isNotEmpty(d)){
                for(PersonFileTransfer uploadFile : d) {
                    if (ObjectUtil.equal(6,uploadFile.getDataType())){
                        FileVO file = new FileVO(uploadFile.getFileId(), uploadFile.getFileName());
                        quality.add(file);
                    }
                    else if (ObjectUtil.equal(4,uploadFile.getDataType())){
                        FileVO file = new FileVO(uploadFile.getFileId(), uploadFile.getFileName());
                        quality.add(file);
                    }
                    else if (ObjectUtil.equal(3,uploadFile.getDataType())){
                        FileVO file = new FileVO(uploadFile.getFileId(), uploadFile.getFileName());
                        quality.add(file);
                    }else {

                    }

                }
            }
            b.setQualificationCertificateAttachment(quality);


            List<FileVO> contract  = new ArrayList<>();
            if(ObjectUtil.isNotEmpty(d)){
                for(PersonFileTransfer uploadFile : d) {
                    if (ObjectUtil.equal(8,uploadFile.getDataType())){
                        FileVO file = new FileVO(uploadFile.getFileId(), uploadFile.getFileName());
                        contract.add(file);
                    }

                }
            }
            b.setAppendix(contract);

            personVOList.add(b);
        }
        return  personVOList;
    }

    @Override
    public DataTypeEnum getDataType() {
        return DataTypeEnum.BUS_BASE_PERSON;
    }
}
