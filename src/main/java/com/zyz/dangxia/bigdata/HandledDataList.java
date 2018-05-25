package com.zyz.dangxia.bigdata;

import com.zyz.dangxia.entity.HandledData;
import com.zyz.dangxia.repository.EvaluationCacheRepository;
import com.zyz.dangxia.repository.HandledDataRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 对样本数据定期存入内存并处理，分离
 */
@Component
public class HandledDataList {
    private static final int COUNT_OF_CLASSES = 12;//一共有12个大类（不包括-1）

    Logger logger = LoggerFactory.getLogger(this.getClass());
    //读写锁 适用于对数据结构读取的次数远远大于写的次数，不同线程读与读之间不会互斥，
    // 但是一旦一个线程加了写锁，其它线程无法读取知道写锁被释放
    private ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
    @Autowired
    private HandledDataRepository handledDataRepository;

    @Autowired
    private EvaluationCacheRepository evaluationCacheRepository;

    private List<HandledData> allDatas;

    private HandledDatasForOneClass[] datas;

    public List<HandledData> getAllDatas() {
        return allDatas;
    }

    public double[][] getDataWithDistanceList(int classId) {
        Lock rLock = lock.readLock();
        rLock.lock();
        try {
            double[][] source = datas[classId - 1].dataWithDistance;
            double[][] copy = new double[source.length][7];

            for (int i = 0; i < source.length; i++) {
                System.arraycopy(source[i], 0, copy[i], 0, 7);
            }
            return copy;

        } finally {
            rLock.unlock();
        }
    }

    @PostConstruct
    @Scheduled(cron = "0 0 3 * * ?")
    public void init() {
        Lock wLock = lock.writeLock();
        wLock.lock();
        try {
            //逐个查出各大类的数据，进行赋值
            allDatas = handledDataRepository.findAll();
            datas = new HandledDatasForOneClass[COUNT_OF_CLASSES];
            for (int j = 0; j < COUNT_OF_CLASSES; j++) {
                List<HandledData> temp = handledDataRepository.findByClassId(j + 1);
                logger.info(temp.toString());
                datas[j] = new HandledDatasForOneClass(temp.size());

                for (int i = 0; i < temp.size(); i++) {
//                    HandledData data = allDatas.get(i);
                    datas[j].dataWithDistance[i][0] = temp.get(i).getC0();
                    datas[j].dataWithDistance[i][1] = temp.get(i).getC1();
                    datas[j].dataWithDistance[i][2] = temp.get(i).getC2();
                    datas[j].dataWithDistance[i][3] = temp.get(i).getC3();
                    datas[j].dataWithDistance[i][4] = temp.get(i).getT();
                    datas[j].dataWithDistance[i][5] = temp.get(i).getP();
                    datas[j].dataWithDistance[i][6] = 0;//最后一个变量是距离
                }
                logger.info("{}分类的蝇量数据装填完毕，一共有{}条数据", j + 1, datas[j].dataWithDistance.length);
            }
//            evaluationCacheRepository.flashDB();
            System.gc();
        } finally {

            wLock.unlock();
        }
    }

    //用于存储一个大分类下的样本数据
    class HandledDatasForOneClass {
        //由于样本的数量是不固定的，需要创建时决定数组大小
        double[][] dataWithDistance;

        HandledDatasForOneClass(int count) {
            dataWithDistance = new double[count][7];
        }
    }
}
