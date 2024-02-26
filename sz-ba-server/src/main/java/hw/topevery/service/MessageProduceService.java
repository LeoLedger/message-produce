package hw.topevery.service;

import hw.topevery.enums.DataTypeEnum;

import java.util.List;

public interface MessageProduceService<V> {

    void run();

    List<V> getPushData();

    /**
     * 推送
     *
     */
    void pushMsg();

    DataTypeEnum getDataType();
}
