package com.satchain.service;

import com.satchain.bean.bo.TeleControlContentBO;
import com.satchain.bean.model.Contentinfo;
import com.satchain.bean.model.Satelliteinfo;
import com.satchain.bean.model.Taskinfo;
import com.satchain.bean.vo.ContentInfoVO;
import com.satchain.commons.utils.TimeConvertUtil;
import com.satchain.dao.ContentinfoMapper;
import com.satchain.dao.SatelliteinfoMapper;
import com.satchain.dao.TaskinfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class TeleContentService {

    @Autowired
    private TaskinfoMapper taskinfoMapper;

    @Autowired
    private ContentinfoMapper contentinfoMapper;

    @Autowired
    private SatelliteinfoMapper satelliteinfoMapper;


    /**
     * 遥控内容增加
     * */
    public Integer insertTeleContent(TeleControlContentBO bo) throws Exception {
        Taskinfo taskinfo = new Taskinfo();
        taskinfo.setEarthUuid(bo.getGroundid());
        taskinfo.setSatelliteUuid(bo.getSatelliteid());
        taskinfo.setTaskType(bo.getTasktype());

        Timestamp startTime = TimeConvertUtil.str2dateTime(bo.getPlanstarttime());
        Timestamp endTime = TimeConvertUtil.str2dateTime(bo.getPlanendtime());
        taskinfo.setPlanStartTime(startTime);
        taskinfo.setPlanEndTime(endTime);
        int taskid = taskinfoMapper.insertReturn(taskinfo);
        if (taskid == 0){
            throw new Exception("插入到任务分配表失败！");
        }

        Contentinfo contentinfo = new Contentinfo();
        contentinfo.setTaskUuid(taskid);
        contentinfo.setTaskContent(bo.getTaskcontent());
        Contentinfo teleContent = contentinfoMapper.selectByTaskId(taskid);
        if (teleContent != null){
            throw new Exception("遥控内容表中插入失败，已存在该内容！");
        }
        return contentinfoMapper.insert(contentinfo);
    }
    /**
     * 遥控内容查询
     */
    public List<ContentInfoVO> queryContentInfo(String constellationId, String satelliteid,
                                              String begintime, String endtime){
        List<ContentInfoVO> contentinfos = new ArrayList<>();
        Timestamp startTime = TimeConvertUtil.str2dateTime(begintime);
        Timestamp endTime = TimeConvertUtil.str2dateTime(endtime);

        List<Satelliteinfo> satelliteinfos = satelliteinfoMapper.queryBySateOrConstID(satelliteid,constellationId);
        for (Satelliteinfo sat : satelliteinfos){
            List<Taskinfo> taskinfos = taskinfoMapper.queryTaskInfoByidAndTime(sat.getSatelliteUuid(),startTime,endTime);
            for (Taskinfo taskinfo : taskinfos){
                Contentinfo contentinfo = contentinfoMapper.selectByTaskId(taskinfo.getTaskUuid());
                ContentInfoVO contentInfoVO = new ContentInfoVO();
                contentInfoVO.setTaskID(taskinfo.getTaskUuid());
                contentInfoVO.setConstellationid(sat.getConstellationType());
                contentInfoVO.setSatelliteid(taskinfo.getSatelliteUuid());
                contentInfoVO.setGroundid(taskinfo.getEarthUuid());
                contentInfoVO.setTaskContent(contentinfo.getTaskContent());
                Date time = taskinfo.getTaskStartTime();
                contentInfoVO.setTaskTime(TimeConvertUtil.date2String(time));
                contentinfos.add(contentInfoVO);
            }
        }
        return contentinfos;
    }
}
