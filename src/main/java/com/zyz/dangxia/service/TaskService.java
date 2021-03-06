package com.zyz.dangxia.service;

import com.zyz.dangxia.common.task.PriceSection;
import com.zyz.dangxia.common.task.TaskClassDto;
import com.zyz.dangxia.common.task.TaskDto;
import java.util.Date;
import java.util.List;

public interface TaskService {
    //获取数据库中所有的任务
    List<TaskDto> getAll();

    //根据id获取单个任务
    TaskDto get(int taskId);

    TaskDto getByOrder(int orderId);

    /**
     * 根据经纬度 查询附近的任务
     * @param latitude 纬度
     * @param longitude 经度
     * @param radius 范围
     * @return 以经纬度确定的点为中心，半径为radius的圆 中的任务
     */
    List<TaskDto> getNearby(double latitude,double longitude,double radius);

    /**
     * 获取自己待完成的任务
     */
    List<TaskDto> getAccepted(int userId);

    /**
     * 获取自己发布的任务
     */
    List<TaskDto> getPublished(int userId);

    /**
     * 获取附近
     */
    List<TaskDto> getNearbyQuick(double latitude,double longitude,double radius);

    /**
     * 发布任务
     */
    int add(int publisher, int type, Date publishDate, Date endDate,
            String content, int requireVerify, String location, double latitude,
            double longitude, double price, int classId);

    /**
     * 删除任务
     */
    int delete(int taskId);

//    /**
//     * 修改任务
//     */
//    int update(TaskDO task);

    /**
     * 指定任务给某人执行
     * @param taskId 任务编号
     * @param userId 执行者编号
     * @return
     */
    int appoint(int taskId, int userId);

    int changePrice(double newPrice, int taskId, int receiverId);

    List<TaskClassDto> getClasses();

    PriceSection getPriceSection(int classId, String taskContent, Date date);

    double getPrice(int taskId);

    /**
     * @param userId 查询者id
     * @return 自己服务过的记录
     */
    List<TaskDto> getServed(int userId);

    /**
     * 获取自己被服务过的记录
     *
     * @param userId 查询者id
     * @return 自己被服务过的记录
     */
    List<TaskDto> getBeServed(int userId);
}
