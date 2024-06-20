package space.lambda.data;

public record Mileage(String type, String data) {

  public MileageType toType() {
    switch (type) {
      case "search" -> {
        return MileageType.FIND_ALL;
      }
      case "student" -> {
        return MileageType.FIND_USER;
      }
      default -> {
        return MileageType.EMPTY;
      }
    }
  }

}