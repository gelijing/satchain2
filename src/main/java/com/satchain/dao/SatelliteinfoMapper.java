package com.satchain.dao;

import com.satchain.bean.model.Satelliteinfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SatelliteinfoMapper {

    int insert(Satelliteinfo record);

    int updateByPrimaryKey(Satelliteinfo record);

    int updateByIdSelective(Satelliteinfo record);

    List<Satelliteinfo> selectBySatelliteId(@Param("satelliteId") String satelliteId, @Param("satelliteName") String satelliteName);

    int deleteByIdName(@Param("satelliteId") String satelliteId, @Param("satelliteName") String satelliteName);

    List<Satelliteinfo> queryBySateOrConstID(@Param("satelliteId") String satelliteId,@Param("constellationId")String constellationId);

}