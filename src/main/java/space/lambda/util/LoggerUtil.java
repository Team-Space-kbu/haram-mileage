package space.lambda.util;

import com.amazonaws.services.lambda.runtime.LambdaLogger;

public class LoggerUtil {

  private static LambdaLogger logger;

  public Boolean statusLogger() {
    return logger == null;
  }

  public void setLogger(LambdaLogger logger) {
    LoggerUtil.logger = logger;
  }

  public void writeLogger(String text) {
    logger.log(text + "\n");
  }
}
