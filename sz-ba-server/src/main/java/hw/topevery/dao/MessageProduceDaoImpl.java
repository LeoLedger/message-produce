package hw.topevery.dao;

import hw.topevery.config.Database;
import hw.topevery.framework.db.CommandType;
import hw.topevery.framework.db.DbExecute;
import hw.topevery.framework.db.base.BaseEntityDaoImpl;
import hw.topevery.framework.db.entity.SqlQueryMate;
import hw.topevery.framework.db.enums.ScriptConditionEnum;
import hw.topevery.framework.entity.DbCsp;
import hw.topevery.framework.entity.Value;
import hw.topevery.pojo.po.bus.*;
import hw.topevery.pojo.po.trasnfer.*;
import org.apache.http.annotation.Contract;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * 数据推送参数 Dao实现类
 *
 * @author LeoLedger
 * @date 2023-12-23
 */
@Repository
public class MessageProduceDaoImpl extends BaseEntityDaoImpl<Object, Integer> implements MessageProduceDao {

    @Override
    public DbExecute getDbExecute() {
        return Database.dbDatabase;
    }

    @Override
    public <P extends DbCsp> List<Object> getSearch(P p) {
        return null;
    }

    @Override
    public void setDeleteField(String s, Object o) {

    }

    private <R> List<R> resultList(SqlQueryMate queryMate, Class<R> clazz) {
        List<R> result = new ArrayList<>();
        run(queryMate.getSql(), CommandType.Text, dbCmd -> {
            queryMate.getParameters().forEach(dbCmd::addParameter);
            dbCmd.executeToList(result, clazz);
        });
        return result;
    }

    public <E> E selectOne(SqlQueryMate sqlQueryMate, Class<E> target) {
        Value<E> res = new Value<>();
        run(sqlQueryMate.getSql(), CommandType.Text, dbCmd -> {
            sqlQueryMate.getParameters().forEach(dbCmd::addParameter);
            res.data = dbCmd.executeToEntity(target);
        });
        return res.data;
    }

    protected String getTableName(String tableName) {
        String from = "";
        String newTableName = tableName.toLowerCase();
        if (newTableName.contains("left join") || newTableName.contains("inner join")) {
            from = newTableName.replaceAll("left join(\\r\\n*|\\r*|\\n*)", " left join " + getDbPrefix())
                    .replaceAll("inner join(\\r\\n*|\\r*|\\n*)", " inner join " + getDbPrefix());
            //.replaceAll("join\\\\s*\r\n|\r|\n", " join " + getDbPrefix());
            from = getDbPrefix() + from;
        } else {
            from = getDbPrefix() + tableName;
        }
        return from;
    }

    protected SqlQueryMate getSqlQueryMate(String tableName) {
        return new SqlQueryMate(getTableName(tableName));
    }



    private <R> List<R> resultList(String table, Class<R> clazz) {
        return resultList(new SqlQueryMate(table), clazz);
    }

    @Override
    public List<Car> findBusBaseCarList() {
        return resultList("t_car ", Car.class);
    }

    @Override
    public List<Person> findBusBasePersonList() {
        return resultList("t_person ", Person.class);
    }

    @Override
    public List<SectionContract> findBusBaseContractList(){
        return resultList("t_section_contract ", SectionContract.class);

    }

    @Override
    public List<SysDept> findBusBaseSysDeptList(){
        return resultList("t_sys_dept ", SysDept.class);

    }

    @Override
    public List<SysMap> findBusBaseSysMapList(){
        return resultList("t_sys_map ", SysMap.class);

    }

    @Override
    public List<FacRefusePlant> findBusBaseFacRefusePlantList(){
        return resultList("t_fac_refuse_plant ", FacRefusePlant.class);

    }

    @Override
    public List<FacRefuseStation> findBusBaseFacRefuseStationList(){
        return resultList("t_fac_refuse_station ", FacRefuseStation.class);

    }

    @Override
    public List<FacRefuseClassifyPoint> findBusBaseFacRefuseClassifyPointList(){
        return resultList("t_fac_refuse_classify_point ", FacRefuseClassifyPoint.class);
    }

    @Override
    public List<FacToilet> findBusBaseFacToiletList(){
        return resultList("t_fac_toilet ", FacToilet.class);

    }


    @Override
    public List<Section> findBusBaseSectionList(){
        return resultList("t_section ", Section.class);

    }


    @Override
    public List<Company> findBusBaseCompanyList(){
        return resultList("t_company ", Company.class);

    }

    @Override
    public List<SysDictTransfer> findBusBaseSysDictTransfer(){
        return resultList("t_sys_dict ", SysDictTransfer.class);

    }

    @Override
    public PersonTransfer findBusBasePersonTransfer(String id) {
        String table = "t_person p\n" +
                "        left join t_section s on s.c_section_id = p.c_section_id\n" +
                "        left join t_sys_dept d on d.c_dept_id = p.c_company_id\n" +
                "        left join t_sys_dept t on t.c_dept_id = p.c_team_id\n" +
                "        left join t_sys_class sc on p.c_person_type = sc.c_class_id\n" +
                "        left join t_device_object_relate dor on dor.c_object_id = p.c_person_id and dor.c_object_type = 0 and dor.c_db_status = 0\n" +
                "        left join t_device_info di on di.c_device_id = dor.c_device_id\n" +
                "        left join t_sys_dict sd on sd.c_dict_id = p.c_education_id";
        String columns = " p.*,\n" +
                "        d.c_dept_name as c_company_name,\n" +
                "        t.c_dept_name as c_team_name,\n" +
                "        s.c_name as c_section_name,\n" +
                "        sc.c_name as c_person_type_name,\n" +
                "        di.c_device_code,\n" +
                "        di.c_sim_code,\n" +
                "        sd.c_dict_name as c_education_name";
        SqlQueryMate sqlQueryMate = getSqlQueryMate(table).setColumns(columns);
        sqlQueryMate
                .where(true , "p.c_person_id", ScriptConditionEnum.Eq, id);
        return selectOne(sqlQueryMate,PersonTransfer.class);

    }

    @Override
    public CarTransfer findBusBaseCarTransfer(String id) {
        String table ="   t_car c\n" +
                "        left join t_section s on s.c_section_id = c.c_section_id\n" +
                "        left join (\n" +
                "        SELECT dor.c_object_id as c_object_id, group_concat( d.c_device_code SEPARATOR '、' ) AS c_device_code\n" +
                "        FROM t_device_object_relate dor\n" +
                "        LEFT JOIN t_device_info d ON dor.c_device_id = d.c_device_id\n" +
                "        AND dor.c_object_type = 1 GROUP BY dor.c_object_id\n" +
                "        ) AS temp_device_info ON temp_device_info.c_object_id = c.c_car_id\n" +
                "        left join t_sys_dept d on d.c_dept_id = c.c_company_id\n" +
                "        left join t_sys_dept t on t.c_dept_id = c.c_team_id\n" +
                "        left join t_sys_class sc on sc.c_class_id = c.c_car_type\n" +
                "        left join t_sys_dict sd on sd.c_dict_id = c.c_power_type\n" +
                "        left join t_sys_dict sd2 on sd2.c_dict_id = c.c_camera_source";

        String columns = "   c.*,"+
                "  sc.c_name as c_car_type_desc,\n" +
                "        sd.c_dict_name as c_power_type_desc,\n" +
                "        temp_device_info.c_device_code as c_device_code,\n" +
                "        s.c_section_id as c_section_id,\n" +
                "        s.c_name as c_section_name,\n" +
                "        d.c_dept_name as c_company_name,\n" +
                "        t.c_dept_name as c_team_name,\n" +
                "        sd2.c_dict_name as c_camera_source_desc,\n" +
                "        s.c_geomCol,\n" +
                "        s.c_geo_mercator";

        SqlQueryMate sqlQueryMate = getSqlQueryMate(table).setColumns(columns);
        sqlQueryMate
                .where(true , "c.c_car_id", ScriptConditionEnum.Eq, id);
        return selectOne(sqlQueryMate,CarTransfer.class);

    }

    @Override
    public FacRefuseClassifyPointTransfer findBusBaseFacRefuseClassifyPointTransfer(String id){
        String columns ="p.*,\n" +
                "               sec.c_name      AS sectionName,\n" +
                "               sup.c_dept_name AS superviseName,\n" +
                "               rep.c_dept_name AS responsibilityName,\n" +
                "               com.c_dept_name as workDeptName,\n" +
                "               com.c_dept_id   as workDeptId,\n" +
                "               map1.c_name     as c_district_name,\n" +
                "               map2.c_name     as c_street_name,\n" +
                "               b.c_code as c_district_code,\n" +
                "               c.c_code as c_street_code,\n" +
                "               d.c_code as c_community_code, "+
                "               map3.c_name       as c_community_name";
        String table = "t_fac_refuse_classify_point p\n" +
                "                 left join t_sys_map as map1 on map1.c_map_id = p.c_district_id\n" +
                "                 left join t_sys_map as map2 on map2.c_map_id = p.c_street_id\n" +
                "                 left join t_sys_map as map3 on map3.c_map_id = p.c_community_id\n" +
                "                 LEFT JOIN t_section sec ON sec.c_section_id = p.c_section_id and sec.c_db_status = 0\n" +
                "                 LEFT JOIN t_sys_dept sup ON sup.c_dept_id = p.c_supervise_id\n" +
                "                 LEFT JOIN t_sys_dept rep ON rep.c_dept_id = p.c_responsibility_id\n" +
                "LEFT JOIN t_sys_map b on p.c_district_id = b.c_map_id\n" +
                "LEFT JOIN t_sys_map c on p.c_street_id = c.c_map_id\n" +
                "LEFT JOIN t_sys_map d on p.c_community_id = d.c_map_id or p.c_community_id = d.c_name" +
                "                 LEFT JOIN t_sys_dept com on com.c_dept_id = sec.c_company_id";
        SqlQueryMate sqlQueryMate = getSqlQueryMate(table).setColumns(columns);
        sqlQueryMate
                .where(true , "p.c_refuse_classify_point_id", ScriptConditionEnum.Eq, id);
        return selectOne(sqlQueryMate,FacRefuseClassifyPointTransfer.class);

    }


    @Override
    public FacRefuseStationTransfer findBusBaseFacRefuseStationTransfer(String id){

        String columns ="map1.c_name       as c_district_name,\n" +
                "               map2.c_name       as c_street_name,\n" +
                "               map3.c_name       as c_community_name,\n" +
                "               section.c_name    as c_section_name,\n" +
                "               dept1.c_dept_name as c_supervise_name,\n" +
                "               dept2.c_dept_name as c_responsibility_name,\n" +
                "               b.c_code as c_district_code,\n" +
                "               c.c_code as c_street_code,\n" +
                "               d.c_code as c_community_code,\n"+
                "               station.* ";
        String table =" t_fac_refuse_station station\n" +
                "                     left join t_section as section on section.c_section_id = station.c_section_id\n" +
                "                     left join t_sys_map as map1 on map1.c_map_id = station.c_district_id and map1.c_map_type = 13002\n" +
                "                     left join t_sys_map as map2 on map2.c_map_id = station.c_street_id and map2.c_map_type = 13003\n" +
                "                     left join t_sys_map as map3 on map3.c_map_id = station.c_community_id\n" +
                "                     left join t_sys_dept as dept1 on dept1.c_dept_id = station.c_supervise_id\n" +
                "LEFT JOIN t_sys_map b on station.c_district_id = b.c_map_id\n" +
                "LEFT JOIN t_sys_map c on station.c_street_id = c.c_map_id\n" +
                "LEFT JOIN t_sys_map d on station.c_community_id = d.c_map_id or station.c_community_id = d.c_name"+
                "                     left join t_sys_dept as dept2 on dept2.c_dept_id = station.c_responsibility_id";
        SqlQueryMate sqlQueryMate = getSqlQueryMate(table).setColumns(columns);
        sqlQueryMate
                .where(true , "station.c_refuse_station_id", ScriptConditionEnum.Eq, id);
        return selectOne(sqlQueryMate,FacRefuseStationTransfer.class);

    }

    @Override
    public FacToiletTransfer findBusBaseFacToiletTransfer(String id){
        String columns =" t.*,\n" +
                "               tsc.c_name as c_type_name,\n" +
                "               dist.c_name     as c_district_name,\n" +
                "               street.c_name   as c_street_name,\n" +
                "               communtity.c_name   as c_community_name,\n" +
                "               sec.c_name      as c_section_name,\n" +
                "               sup.c_dept_name as c_supervise_name,\n" +
                "               sup.c_dept_name as c_supervise_name,\n" +
                "               b.c_code as c_district_code,\n" +
                "               c.c_code as c_street_code,\n" +
                "               d.c_code as c_community_code";
        String table ="t_fac_toilet t\n" +
                "                 LEFT JOIN t_section sec ON sec.c_section_id = t.c_section_id\n" +
                "                 LEFT JOIN t_sys_map dist ON dist.c_map_id = t.c_district_id and dist.c_map_type = 13002\n" +
                "                 LEFT JOIN t_sys_map street ON street.c_map_id = t.c_street_id and street.c_map_type = 13003\n" +
                "                 LEFT JOIN t_sys_map communtity ON communtity.c_map_id = t.c_community_id\n" +
                "                 LEFT JOIN t_sys_class tsc ON tsc.c_class_id = t.c_type and tsc.c_db_status = 0\n" +
                "                 LEFT JOIN t_sys_dept sup ON sup.c_dept_id = t.c_supervise_id\n" +
                "                 LEFT JOIN t_sys_map map ON sup.c_dept_id = t.c_supervise_id\n" +
                "LEFT JOIN t_sys_map b on t.c_district_id = b.c_map_id\n" +
                "LEFT JOIN t_sys_map c on t.c_street_id = c.c_map_id\n" +
                "LEFT JOIN t_sys_map d on t.c_community_id = d.c_map_id or t.c_community_id = d.c_name"+
                "                 LEFT JOIN t_sys_dept rep ON rep.c_dept_id = t.c_responsibility_id";

        SqlQueryMate sqlQueryMate = getSqlQueryMate(table).setColumns(columns);
        sqlQueryMate
                .where(true , "t.c_toilet_id", ScriptConditionEnum.Eq, id);
        return selectOne(sqlQueryMate,FacToiletTransfer.class);
    }

    @Override
    public FacRefusePlantTransfer findBusBaseFacRefusePlantTransfer(String id){

        String columns =" map1.c_name       as c_district_name,\n" +
                "               map2.c_name       as c_street_name,\n" +
                "               map3.c_name       as c_community_name,\n" +
                "               section.c_name    as c_section_name,\n" +
                "               dept1.c_dept_name as c_supervise_name,\n" +
                "               dept2.c_dept_name as c_responsibility_name,\n" +
                "               dept3.c_dept_name as c_deal_dept_name,\n" +
                "               plan.*,\n" +
                "               gpw.c_count as c_count,\n" +
                "               b.c_code as c_district_code,\n" +
                "               c.c_code as c_street_code,\n" +
                "               d.c_code as c_community_code, \n"+
                "                gpw.c_weight as c_weight";
        String table ="t_fac_refuse_plant as plan\n" +
                "                     left join t_sys_map as map1 on map1.c_map_id = plan.c_district_id and map1.c_map_type = 13002\n" +
                "                     left join t_sys_map as map2 on map2.c_map_id = plan.c_street_id and map2.c_map_type = 13003\n" +
                "                     left join t_sys_map as map3 on map3.c_map_id = plan.c_community_id\n" +
                "                     left join t_section as section on section.c_section_id = plan.c_section_id\n" +
                "                     left join t_sys_dept as dept1 on dept1.c_dept_id = plan.c_supervise_id\n" +
                "                     left join t_sys_dept as dept2 on dept2.c_dept_id = plan.c_responsibility_id\n" +
                "                     left join t_sys_dept as dept3 on dept3.c_dept_id = plan.c_deal_dept_id\n" +
                "LEFT JOIN t_sys_map b on plan.c_district_id = b.c_map_id\n" +
                "LEFT JOIN t_sys_map c on plan.c_street_id = c.c_map_id\n" +
                "LEFT JOIN t_sys_map d on plan.c_community_id = d.c_map_id  or plan.c_community_id = d.c_name "+
                "                     LEFT JOIN (select c_plant_id, count(c_id) as c_count, sum(c_all_weight) as c_weight from t_garbage_plant_weigh where  to_days(c_date) = to_days(now()) group by c_plant_id) gpw ON gpw.c_plant_id = plan.c_plant_id\n";

        SqlQueryMate sqlQueryMate = getSqlQueryMate(table).setColumns(columns);
        sqlQueryMate
                .where(true , "plan.c_plant_id", ScriptConditionEnum.Eq, id);
        return selectOne(sqlQueryMate,FacRefusePlantTransfer.class);
    }



    @Override
    public List<FacFileTransfer> findBusBaseFacFileTransfer(String facid){
        String table = "t_fac_file tff";

        String columns = "tff.*";

        SqlQueryMate sqlQueryMate = new SqlQueryMate(table).setColumns(columns).setOrderBy("tff.c_id desc");
        sqlQueryMate
                .where(true, "tff.c_fac_id", ScriptConditionEnum.Eq, facid);
        return resultList(sqlQueryMate,  FacFileTransfer.class);
    }

    @Override
    public List<PersonFileTransfer> findBusBasePersonFileTransfer(String facid){
        String table = "t_person_file tff";

        String columns = "tff.*";

        SqlQueryMate sqlQueryMate = new SqlQueryMate(table).setColumns(columns).setOrderBy("tff.c_id desc");
        sqlQueryMate
                .where(true, "tff.c_person_id", ScriptConditionEnum.Eq, facid);
        return resultList(sqlQueryMate,  PersonFileTransfer.class);
    }

    @Override
    public List<CarFileTransfer> findBusBaseCarFileTransfer(String facid){
        String table = "t_car_file tff";

        String columns = "tff.*";

        SqlQueryMate sqlQueryMate = new SqlQueryMate(table).setColumns(columns).setOrderBy("tff.c_id desc");
        sqlQueryMate
                .where(true, "tff.c_car_id", ScriptConditionEnum.Eq, facid);
        return resultList(sqlQueryMate,  CarFileTransfer.class);
    }




//    @Override
//    public List<Company> findBusBaseCompanyList() {
//        return resultList("t_company", Company.class);
//    }
//
//    @Override
//    public List<Section> findBusBaseSectionList() {
//        return resultList("t_section", Section.class);
//    }

//    @Override
//    public List<SysDept> findBusBaseSysDeptList() {
//        return resultList("t_sys_dept", SysDept.class);
//    }

//    @Override
//    public List<PersonFile> findBusBasePersonFileList() {
//        return resultList("t_person_file", PersonFile.class);
//    }

//    @Override
//    public List<CarFile> findBusBaseCarFileList() {
//        return resultList("t_car_file", CarFile.class);
//    }

//
//    @Override
//    public List<DeviceInfo> findBusBaseDeviceInfoList() {
//        return resultList("t_device_info", DeviceInfo.class);
//    }
//
//    @Override
//    public List<DeviceObjectRelate> findBusBaseDeviceObjectRelateList() {
//        return resultList("t_device_object_relate", DeviceObjectRelate.class);
//    }
}
