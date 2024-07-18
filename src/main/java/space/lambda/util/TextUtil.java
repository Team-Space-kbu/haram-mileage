package space.lambda.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.ZoneOffset;
import org.w3c.dom.NodeList;

public class TextUtil {

  private static final DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyyMMdd");
  private static final DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssXXX");

  public static String toDate(String date){
    return date.isEmpty() ? "" :
        LocalDate.parse(date, inputFormatter)
            .atStartOfDay(ZoneOffset.of("+09:00"))
            .format(outputFormatter);
  }

  public static String getTextContent(NodeList nodeList, int index) {
    if (nodeList.item(index) != null) {
      return nodeList.item(index).getTextContent().trim();
    }
    return "";
  }
}
