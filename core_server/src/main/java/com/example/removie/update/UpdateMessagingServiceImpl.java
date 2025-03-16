package com.example.removie.update;

import io.awspring.cloud.sqs.operations.SqsTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 모든 프로세스 종료 후 백엔드 메시지를 보내는 서비스입니다.
 * 해당 신호를 받은 백엔드 서버는 캐시를 초기화합니다, 해당 메시지가 실패하여도
 * 백엔드 서버에 캐시는 자동으로 소멸됩니다.
 *
 * @author An_Jicheol
 * @version 1.0
 */

@Service
public class UpdateMessagingServiceImpl implements UpdateMessagingService{
    private static final Logger logger = LoggerFactory.getLogger(UpdateMessagingServiceImpl.class);
    private final SqsTemplate sqsTemplate;

    @Autowired
    public UpdateMessagingServiceImpl(SqsTemplate sqsTemplate) {
        this.sqsTemplate = sqsTemplate;
    }

    public void sendMessage() {
        sqsTemplate.send(to -> to.queue("update").payload("update"));
        logger.debug("메시징 완료");
    }
}
