package com.zyz.dangxia.mapper;

import com.zyz.dangxia.dto.TaskDto;
import com.zyz.dangxia.model.TaskDO;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(TaskDO record);

    int insertSelective(TaskDO record);

    TaskDO selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TaskDO record);

    int updateByPrimaryKey(TaskDO record);

    List<TaskDO> list();

    TaskDO getByOrderId(int orderId);

    /**
     * 获取任务列表，结果以时间升序
     *
     * @param taskType 0->火速需求 1->长期需求
     * @return 任务列表
     */
    List<TaskDO> listAcceptableDesc(int taskType);

    List<TaskDO> listToDo(int userId);

    List<TaskDO> listPublished(int userId);

    double getPrice(int taskId);

    List<TaskDO> listServed(int userId);

    List<TaskDO> listBeServed(int userId);

    int getPublisher(int taskId);

    TaskDto getDtoByPrimaryKey(int taskId);
}