package space.lambda;

import org.jetbrains.annotations.NotNull;
import space.lambda.api.MileageAllUserApi;
import space.lambda.api.MileageApi;
import space.lambda.api.MileageFindUserApi;
import space.lambda.api.MileageLoginApi;
import space.lambda.model.Type;

public class MileageFactory {

  public MileageApi getMileage(@NotNull Type type) {
    switch (type) {
      case FIND_ALL -> {
        return new MileageAllUserApi();
      }
      case FIND_USER -> {
        return new MileageFindUserApi();
      }
      case LOGIN -> {
        return new MileageLoginApi();
      }
      default -> {
        throw new RuntimeException();
      }
    }
  }

}
