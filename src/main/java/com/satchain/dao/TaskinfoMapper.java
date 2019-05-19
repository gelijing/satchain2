package com.satchain.dao;

import com.satchain.bean.model.Taskinfo;
import org.apache.ibatis.annotations.Param;

import java.sql.Timestamp;
import java.util.List;

public interface TaskinfoMapper {

    int deleteByTaskid(@Param("taskid") Integer taskid,@Param("distrisign") Integer distrisign);

    int insert(Taskinfo record);

    int insertReturn(Taskinfo record);

    Taskinfo selectByTaskId(@Param("taskid") Integer taskid);

    int updateByTaskId(Taskinfo record);

    int updateFlagByTaskId(@Param("taskid") Integer taskid,@Param("datadistrisign") Integer datadistrisign);

    List<Taskinfo> queryTaskInfoByTaskBO(@Param("satelliteid") List satelliteid,
                                         @Param("tasktype") Integer tasktype, @Param("distrisign") Integer distrisign,
                                         @Param("starttime") Timestamp starttime, @Param("stoptime") Timestamp stoptime);
    List<Taskinfo> queryTaskInfoByidAndTime(@Param("satelliteid") String satelliteid,
                                         @Param("starttime") Timestamp starttime, @Param("stoptime") Timestamp stoptime);

    List<Taskinfo> queryBySatelliteId(@Param("satelliteid") String satelliteid);
}