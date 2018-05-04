package com.zyz.dangxia.bigdata;

import com.zyz.dangxia.entity.TaskClass;
import com.zyz.dangxia.repository.TaskClassRepository;
import com.zyz.dangxia.repository.TaskRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
@Component
public class TaskClassList {
    Logger logger = LoggerFactory.getLogger(this.getClass());
    private  List<TaskClass> taskClassList = null;
    public  List<TaskClass> getList() {
        return taskClassList;
    }
    @Autowired
    TaskClassRepository taskClassRepository;
    @PostConstruct
    private void init() {
        taskClassList = taskClassRepository.findUserful();
    }

}
