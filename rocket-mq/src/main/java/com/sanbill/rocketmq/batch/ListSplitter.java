package com.sanbill.rocketmq.batch;

import org.apache.rocketmq.common.message.Message;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * 批量消息总长度可能大于4MB室，最好进行消息分割
 */
public class ListSplitter implements Iterator<List<Message>> {
    private final int SIZE_LIMIT = 1024 * 1024 * 4;
    private final List<Message> messages;
    private int currIndex;

    public ListSplitter(List<Message> messages) {
        this.messages = messages;
    }

    @Override
    public boolean hasNext() {
        return currIndex < messages.size();
    }

    @Override
    public List<Message> next() {
        //nextIndex指向的是超过大小的索引
        int nextIndex = currIndex;
        int totalSize = 0;
        for (; nextIndex < messages.size(); nextIndex++) {
            //消息的大小计算
            int tmpSize = getMessageSize(messages.get(nextIndex));
            if (tmpSize + totalSize > SIZE_LIMIT) {
                break;
            } else {
                totalSize += tmpSize;
            }
        }
        List subList = messages.subList(currIndex, nextIndex);
        currIndex = nextIndex;
        return subList;
    }

    /**
     * 大小计算分成几部分：topic,body,properties所有属性，日志(20)
     *
     * @param message
     * @return
     */
    private int getMessageSize(Message message) {
        int size = message.getTopic().length() + message.getBody().length;
        Map<String, String> properties = message.getProperties();
        for (Map.Entry<String, String> entry : properties.entrySet()) {
            size += entry.getKey().length() + entry.getValue().length();
        }
        size += 20; //增加日志的开销20字节;
        return size;
    }
}
