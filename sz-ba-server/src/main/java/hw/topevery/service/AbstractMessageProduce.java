package hw.topevery.service;

import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSON;
import hw.topevery.config.MessageProduceConfig;
import hw.topevery.enums.TargetTypeEnum;
import hw.topevery.framework.util.LogUtil;
import hw.topevery.framework.util.ObjectUtil;
import hw.topevery.pojo.vo.CommonVO;
import hw.topevery.pojo.vo.PushBaseVO;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class AbstractMessageProduce<K, V extends CommonVO<K>> implements MessageProduceService<V> {

    /**
     * 推送
     *
     */
    public void pushMsg() {
        List<V> list = getPushData();
        if (ObjectUtil.isNullOrBlank(list))
            return;

        List<List<V>> chunkedList = new ArrayList<>(); // 切分后的列表

        int chunkSize = 10; // 每个切分块的大小

        for (int i = 0; i < list.size(); i += chunkSize) {
            // 计算当前切分块的起始索引和结束索引
            int startIndex = i;
            int endIndex = Math.min(i + chunkSize, list.size());

            // 获取当前切分块的子列表
            List<V> chunk = list.subList(startIndex, endIndex);

            // 将切分块添加到切分后的列表
            chunkedList.add(chunk);
        }

        if (null == list || list.isEmpty()) {
            return;
        }
        Map<K, String> map = new HashMap<>();

//        for (List<V> vList : chunkedList) {
//            vList.forEach(item -> {
//                map.put(item.getId(), JSON.toJSONString(item));
//            });
//            PushBaseVO<K> data = new PushBaseVO<>();
//            data.setData(map);
//            data.setSourceType(MessageProduceConfig.SYSTEM_CODE);
//            data.setTargetType(TargetTypeEnum.DG.getValue());
//            data.setDataType(getDataType().getValue());
//            try {
//                HttpUtil.post(MessageProduceConfig.PUSH_URL, JSON.toJSONString(data), MessageProduceConfig.PUSH_TIMEOUT);
//            } catch (Exception e) {
//                LogUtil.error("推送失败" + data, e);
//            }
//        }


//        for (List<V> vList : chunkedList) {
            list.forEach(item -> {
                map.put(item.getId(), JSON.toJSONString(item));
            });
            PushBaseVO<K> data = new PushBaseVO<>();
            data.setData(map);
            data.setSourceType(MessageProduceConfig.SYSTEM_CODE);
            data.setTargetType(TargetTypeEnum.DG.getValue());
            data.setDataType(getDataType().getValue());
            try {
                HttpUtil.post(MessageProduceConfig.PUSH_URL, JSON.toJSONString(data), MessageProduceConfig.PUSH_TIMEOUT);
            } catch (Exception e) {
                LogUtil.error("推送失败" + data, e);
            }
        }

}
