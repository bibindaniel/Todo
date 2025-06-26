package com.studyspring.todocrud.config;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.appender.ConsoleAppender;
import org.apache.logging.log4j.core.appender.RollingFileAppender;
import org.apache.logging.log4j.core.appender.rolling.DefaultRolloverStrategy;
import org.apache.logging.log4j.core.appender.rolling.SizeBasedTriggeringPolicy;
import org.apache.logging.log4j.core.config.LoggerConfig;
import org.apache.logging.log4j.core.layout.PatternLayout;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;

@Configuration
public class AppConfig {

    @Bean
    public LoggerContext loggerContext() {
        // Get the logger context from LogManager
        org.apache.logging.log4j.spi.LoggerContext context = LogManager.getContext(false);
        if (!(context instanceof org.apache.logging.log4j.core.LoggerContext)) {
            throw new IllegalStateException("Log4j2 LoggerContext not found. Please ensure no SLF4J bridges or other logging frameworks are on the classpath.");
        }
        LoggerContext log4jContext = (org.apache.logging.log4j.core.LoggerContext) context;
        try {
            // Ensure logs directory exists
            Files.createDirectories(Paths.get("logs"));
            final org.apache.logging.log4j.core.config.Configuration config = log4jContext.getConfiguration();
            // Console appender
            ConsoleAppender consoleAppender = ConsoleAppender.newBuilder()
                    .setName("Console")
                    .setTarget(ConsoleAppender.Target.SYSTEM_OUT)
                    .setLayout(PatternLayout.newBuilder()
                            .withPattern("[%d{yyyy-MM-dd HH:mm:ss.SSS}] [%-5level] [%c] - %msg%n")
                            .build())
                    .build();
            consoleAppender.start();
            // File appender
            String logFile = new File("logs/app.log").getAbsolutePath();
            SizeBasedTriggeringPolicy triggeringPolicy = SizeBasedTriggeringPolicy.createPolicy("10 MB");
            triggeringPolicy.start();
            DefaultRolloverStrategy rolloverStrategy = DefaultRolloverStrategy.newBuilder()
                    .withMax("7")
                    .withMin("1")
                    .withConfig(config)
                    .build();
            RollingFileAppender fileAppender = RollingFileAppender.newBuilder()
                    .setName("File")
                    .withFileName(logFile)
                    .withFilePattern(logFile + ".%d{yyyy-MM-dd}-%i")
                    .setLayout(PatternLayout.newBuilder()
                            .withPattern("[%d{yyyy-MM-dd HH:mm:ss.SSS}] [%-5level] [%c] - %msg%n")
                            .build())
                    .withPolicy(triggeringPolicy)
                    .withStrategy(rolloverStrategy)
                    .build();
            fileAppender.start();
            config.addAppender(consoleAppender);
            config.addAppender(fileAppender);
            LoggerConfig rootLogger = config.getRootLogger();
            rootLogger.setLevel(Level.INFO);
            rootLogger.addAppender(consoleAppender, Level.INFO, null);
            rootLogger.addAppender(fileAppender, Level.INFO, null);
            LoggerConfig appLogger = new LoggerConfig("com.studyspring", Level.DEBUG, false);
            appLogger.addAppender(consoleAppender, Level.DEBUG, null);
            appLogger.addAppender(fileAppender, Level.DEBUG, null);
            config.addLogger("com.studyspring", appLogger);
            log4jContext.updateLoggers();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return log4jContext;
    }
}
