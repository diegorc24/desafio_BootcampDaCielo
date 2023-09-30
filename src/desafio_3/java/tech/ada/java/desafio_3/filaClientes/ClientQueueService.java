package tech.ada.java.desafio_3.filaClientes;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.*;

@Service
public class ClientQueueService {
    private static final String QUEUE_NAME = "ClientQueue";
    private String queueUrl;

    @Autowired
    private SqsClient sqsClient;

    @PostConstruct
    public void init() {
        try {
            GetQueueUrlResponse getQueueUrlResponse = sqsClient.getQueueUrl(GetQueueUrlRequest.builder()
                    .queueName(QUEUE_NAME)
                    .build());
            this.queueUrl = getQueueUrlResponse.queueUrl();
        } catch (QueueDoesNotExistException e) {
            CreateQueueResponse createQueueResponse = sqsClient.createQueue(CreateQueueRequest.builder()
                    .queueName(QUEUE_NAME)
                    .build());
            this.queueUrl = createQueueResponse.queueUrl();
        }
    }

    public void addToQueue(long clientId) {
        sqsClient.sendMessage(SendMessageRequest.builder()
                .queueUrl(queueUrl)
                .messageBody(String.valueOf(clientId))
                .build());
    }

    public long removeFromQueue() {
        ReceiveMessageResponse receiveMessageResponse = sqsClient.receiveMessage(ReceiveMessageRequest.builder()
                .queueUrl(queueUrl)
                .maxNumberOfMessages(1)
                .waitTimeSeconds(20)
                .build());

        if (receiveMessageResponse.messages().isEmpty()) {
            return -1;
        }

        Message message = receiveMessageResponse.messages().get(0);
        sqsClient.deleteMessage(DeleteMessageRequest.builder()
                .queueUrl(queueUrl)
                .receiptHandle(message.receiptHandle())
                .build());

        return Long.parseLong(message.body());
    }

    public boolean isQueueEmpty() {
        GetQueueAttributesResponse response = sqsClient.getQueueAttributes(GetQueueAttributesRequest.builder()
                .queueUrl(queueUrl)
                .attributeNames(QueueAttributeName.APPROXIMATE_NUMBER_OF_MESSAGES)
                .build());

        return Integer.parseInt(response.attributes().get(QueueAttributeName.APPROXIMATE_NUMBER_OF_MESSAGES)) == 0;
    }
}


