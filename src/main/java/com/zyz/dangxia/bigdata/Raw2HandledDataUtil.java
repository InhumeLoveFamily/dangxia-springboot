package com.zyz.dangxia.bigdata;

import com.zyz.dangxia.entity.HandledData;
import com.zyz.dangxia.entity.Keyword;
import com.zyz.dangxia.repository.KeywordRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * 将原始数据转换为特征向量的工具类
 */
@Component
public class Raw2HandledDataUtil {
    Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private KeywordRepository keywordRepository;

    public HandledData getHandledData(int classId, String content, Date date, int price) {
        //在数据库中查找大类对应的四个关键词族
        List<Keyword> keywordList = keywordRepository.findByClassId(classId);
        if (keywordList.size() == 0) {
            return new HandledData(0, 0, 0, 0, 1, 0, classId);
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
    public HandledData getHandledData(int classId, List<Keyword> keywordList, String content, Date date, int price) {
        int[] counts = new int[4];
        for (int i = 0; i < 4; i++) {
            String[] words = keywordList.get(i).getContent().split("[、，]");
            for (String word : words) {
                if (content.contains(word)) {
                    counts[i]++;
                }
            }
        }
        logger.info(Arrays.toString(counts));
        return new HandledData(counts[0], counts[1], counts[2], counts[3],
                TimeSectionUtil.getT(date.getTime()), price, classId);
    }
}
