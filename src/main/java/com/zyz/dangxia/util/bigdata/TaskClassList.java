package com.zyz.dangxia.util.bigdata;


import com.zyz.dangxia.mapper.TaskClassMapper;
import com.zyz.dangxia.model.TaskClassDO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * 储存大类的列表
 */
@Component
public class TaskClassList {
    private  List<TaskClassDO> taskClassList = null;

    public  List<TaskClassDO> getList() {
        return taskClassList;
    }

    @Autowired
    TaskClassMapper taskClassMapper;

    @PostConstruct
    private void init() {
        taskClassList = taskClassMapper.listUseful();
    }

}
