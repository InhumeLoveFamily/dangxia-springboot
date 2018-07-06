package com.zyz.dangxia.bigdata;

import com.zyz.dangxia.mapper.KeywordMapper;
import com.zyz.dangxia.model.HandledDataDO;
import com.zyz.dangxia.model.KeywordDO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * 将原始数据根据关键词族转换为特征向量的工具类
 */
@Component
public class Raw2HandledDataUtil {
    Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private KeywordMapper keywordMapper;

    public HandledDataDO getHandledData(int classId, String content, Date date, int price) {
        //在数据库中查找大类对应的四个关键词族
        List<KeywordDO> keywordList = keywordMapper.listByClassId(classId);
        if (keywordList.size() == 0) {
            return new HandledDataDO(0, 0, 0, 0, 1, 0, classId);
        } else {
            return getHandledData(classId, keywordList, content, date, price);
        }
    }

    /**
     * 统计关键词族出现的次数,将用户的原始需求文本，转换为特征向量
     *
     * @param content
     * @return
     */
    public HandledDataDO getHandledData(int classId, List<KeywordDO> keywordList, String content, Date date, int price) {
        int[] counts = new int[4];
        for (int i = 0; i < 4; i++) {
            String[] words = keywordList.get(i).getName().split("[、，]");
            for (String word : words) {
                if (content.contains(word)) {
                    logger.info("匹配到关键词{}", word);
                    counts[i]++;
                }
            }
        }
        logger.info(Arrays.toString(counts));
        return new HandledDataDO(counts[0], counts[1], counts[2], counts[3],
                TimeSectionUtil.getT(date.getTime()), price, classId);
    }
}
