package space.lambda.model;

import java.util.regex.Pattern;

public enum MileageType {
  CAFE,
  GYM,
  MART,
  BOOKSTORE,
  COPYROOM,
  STUDENT,
  ETC;

  private static final String regex =  "(\\d{2,4}(-\\d)?)[\\s]?([^\\-]+)[\\s]?-[\\s]?([^\\-]+)";
  private static final Pattern pattern = Pattern.compile(regex);

  public static MileageType findMileageType(String etc, String status) {
    if (status.contains("조정")) {
      return MileageType.STUDENT;
    }
    switch (etc) {
      case "북카페" -> {
        return MileageType.BOOKSTORE;
      }
      case "헬스장" -> {
        return MileageType.GYM;
      }
      case "매점", "마트" -> {
        return MileageType.MART;
      }
      case "복사실" -> {
        return MileageType.COPYROOM;
      }
      case "카페", "카페코스테스", "카페테리아" -> {
        return MileageType.CAFE;
      }
      case "교학처" -> {
        return MileageType.STUDENT;
      }
      default -> {
        if (pattern.matcher(etc).find()) {
          return MileageType.STUDENT;
        }
        return MileageType.ETC;
      }
    }

  }
}
