package space;

import org.jetbrains.annotations.NotNull;
import space.api.MileageAllUserApi;
import space.api.MileageApi;
import space.api.MileageFindUserApi;
import space.data.MileageType;

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
