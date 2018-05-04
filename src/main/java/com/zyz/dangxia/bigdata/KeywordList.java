package com.zyz.dangxia.bigdata;

import com.zyz.dangxia.entity.Keyword;
import com.zyz.dangxia.entity.TaskClass;
import com.zyz.dangxia.repository.KeywordRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.security.Key;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 保存关键词的容器,由于关键词不会轻易变动，所以为了避免多次查询可以在虚拟机中缓存
 */
@Component
public class KeywordList {
    Logger logger = LoggerFactory.getLogger(this.getClass());
    private static List<Keyword> keywordList = null;
    public static List<Keyword> getList() {
        return keywordList;
    }
    @Autowired
    KeywordRepository keywordRepository;

    @PostConstruct
    private void init() {
        keywordList = keywordRepository.findUserful();
        logger.info("关键词库填装完毕，一共有{}个",keywordList.size());
    }

    /**
     * 返回一个用于统计关键词出现次数的map
     */
    public static HashMap<Keyword,Integer> getMap() {
        HashMap<Keyword,Integer> result = new HashMap<>(keywordList.size());
        for(Keyword keyword : keywordList) {
            result.put(keyword,0);
        }
        return result;
    }
}
