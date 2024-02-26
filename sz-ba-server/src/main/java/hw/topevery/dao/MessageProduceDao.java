package hw.topevery.dao;

import hw.topevery.framework.db.base.BaseEntityDao;
import hw.topevery.pojo.po.bus.*;
import hw.topevery.pojo.po.trasnfer.*;


import java.util.List;

/**
 * 数据推送参数 Dao
 *
 * @author LeoLedger
 * @date 2023-12-23
 */
public interface MessageProduceDao extends BaseEntityDao<Object, Integer> {

    List<Car> findBusBaseCarList();

    List<Person> findBusBasePersonList();

    List<SectionContract> findBusBaseContractList();

    PersonTransfer findBusBasePersonTransfer(String id);

    CarTransfer findBusBaseCarTransfer(String id);

    FacRefuseClassifyPointTransfer findBusBaseFacRefuseClassifyPointTransfer(String id);

    List<Company> findBusBaseCompanyList();

    List<FacRefuseClassifyPoint> findBusBaseFacRefuseClassifyPointList();

    List<FacToilet> findBusBaseFacToiletList();

    List<FacRefuseStation> findBusBaseFacRefuseStationList();

    List<FacRefusePlant> findBusBaseFacRefusePlantList();

    FacRefuseStationTransfer findBusBaseFacRefuseStationTransfer(String id);

    FacToiletTransfer findBusBaseFacToiletTransfer(String id);

    FacRefusePlantTransfer findBusBaseFacRefusePlantTransfer(String id);

    List<FacFileTransfer> findBusBaseFacFileTransfer(String facid);

    List<PersonFileTransfer> findBusBasePersonFileTransfer(String facid);

    List<CarFileTransfer> findBusBaseCarFileTransfer(String facid);

    List<SysDictTransfer> findBusBaseSysDictTransfer();





//
//    List<PersonFile> findBusBasePersonFileList();
//
//    List<CarFile> findBusBaseCarFileList();

    List<Section> findBusBaseSectionList();

    List<SysDept> findBusBaseSysDeptList();

    List<SysMap> findBusBaseSysMapList();


//    List<DeviceInfo> findBusBaseDeviceInfoList();
//
//    List<DeviceObjectRelate> findBusBaseDeviceObjectRelateList();


}
