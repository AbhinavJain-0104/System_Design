package com.example.stockpricerandom.observer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service

public class ConsoleNotificationService implements Observer{
        private static final Logger logger = LoggerFactory.getLogger(ConsoleNotificationService.class);

        @Override
        public void notify(String message) {
            logger.info("Notification: {}", message);
        }
}
