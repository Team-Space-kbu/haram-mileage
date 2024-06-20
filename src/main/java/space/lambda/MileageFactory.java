package space.lambda;

import org.jetbrains.annotations.NotNull;
import space.lambda.api.MileageAllUserApi;
import space.lambda.api.MileageApi;
import space.lambda.api.MileageFindUserApi;
import space.lambda.data.MileageType;

public class MileageFactory {

  public MileageApi getMileage(@NotNull MileageType type) {
    switch (type) {
      case FIND_ALL -> {
        return new MileageAllUserApi();
      }
      case FIND_USER -> {
        return new MileageFindUserApi();
      }
      default -> {
        throw new RuntimeException();
      }
    }
  }
}
