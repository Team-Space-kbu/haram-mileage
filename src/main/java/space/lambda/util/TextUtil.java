package space.lambda.util;

import org.w3c.dom.NodeList;

public class TextUtil {

  public static String getTextContent(NodeList nodeList, int index) {
    if (nodeList.item(index) != null) {
      return nodeList.item(index).getTextContent().trim();
    }
    return "";
  }
}
