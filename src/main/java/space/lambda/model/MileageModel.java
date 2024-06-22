package space.lambda.model;

public record MileageModel(String type, String data) {

  public Type toType() {
    switch (type) {
      case "search" -> {
        return Type.FIND_ALL;
      }
      case "student" -> {
        return Type.FIND_USER;
      }
      default -> {
        return Type.EMPTY;
      }
    }
  }

}