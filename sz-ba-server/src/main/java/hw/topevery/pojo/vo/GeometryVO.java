package hw.topevery.pojo.vo;

import hw.topevery.framework.GeometryHelper;
import hw.topevery.framework.dto.PointDto;
import lombok.Data;
import org.locationtech.jts.geom.Geometry;

import java.util.List;

@Data
public class GeometryVO {
    /** 坐标类型 1:点 2:线 3:面 **/
    private Integer type;
    /** 传入的坐标系：0、WGS84（天地图、超图、Mapbox等）  1、GCJ02（高德地图、腾讯地图等）   2、BD09（百度地图） **/
    private Integer coordinate;
    /** 坐标数据 **/
    private List<List<PointDto>> points;

    transient private Geometry geometry;

    public final static int WGS84 = 0; // WGS84（天地图、超图、Mapbox等）
    public final static int GCJ02 = 1; // GCJ02（高德地图、腾讯地图等）
    public final static int BD09 = 2; // BD09（百度地图）

    public GeometryVO() {
    }

    public GeometryVO(Geometry geometry) {
        this(geometry, BD09);
    }

    public GeometryVO(Geometry geometry, Integer coordinate) {
        if (null != geometry && !geometry.isEmpty()) {
            this.coordinate = coordinate;
            this.type = GeometryHelper.getGeometryType(geometry);
            this.points = GeometryHelper.convertGeometryToPoint(geometry);
        }
    }

    public GeometryVO(Integer type, Integer coordinate, List<List<PointDto>> points) {
        this.type = type;
        this.coordinate = coordinate;
        this.points = points;
    }
}
