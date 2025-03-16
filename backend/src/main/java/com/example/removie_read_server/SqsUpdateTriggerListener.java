package com.example.removie_read_server;

import io.awspring.cloud.sqs.annotation.SqsListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SqsUpdateTriggerListener {
    private final Logger logger = LoggerFactory.getLogger(SqsUpdateTriggerListener.class);
    private final UpdateTrigger updateTrigger;

    @Autowired
    public SqsUpdateTriggerListener(UpdateTrigger updateTrigger) {
        this.updateTrigger = updateTrigger;
    }

    @SqsListener("update")
    public void updateTriggerMessage(String message){

        if("update".equals(message)){
            updateTrigger.updateProcess();
            logger.debug("메시징 수신 완료");
        }
        else {
            logger.debug("메시징 수신 실패");
        }
    }
}
