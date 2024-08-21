package space.lambda.model;

public record MileageModel(String type, String data) {

  public Type toType() {
    switch (type) {
      case "search" -> {
        return Type.FIND_ALL;
      }
      case "student" -> {
        return Type.USER_DETAIL;
      }
      case "detail" -> {
        return Type.DETAIL_USE;
      }
      case "DETAIL_USER" -> {
        return Type.DETAIL_USER;
      }
      default -> {
        return Type.EMPTY;
      }
    }
  }

}