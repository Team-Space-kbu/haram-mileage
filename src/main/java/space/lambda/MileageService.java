package space.lambda;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import space.lambda.api.MileageApi;
import space.lambda.data.MileageUseDetail;
import space.lambda.model.MileageModel;
import space.lambda.model.Type;
import space.lambda.data.MileageInfo;
import space.lambda.util.LoggerUtil;

public class MileageService {

  public static final OkHttpClient client = new OkHttpClient().newBuilder().build();
  private static final LoggerUtil logger = new LoggerUtil();

  public Response mileageRequest(
      MileageApi mileageApi,
      MileageModel event,
      String cookie
  ) throws IOException {
    logger.writeLogger("An API request was made to the mileage server." + event.toType());
    RequestBody body = mileageApi.setBody(event);
    Request request = mileageApi.getRequest(body, cookie);
    return client.newCall(request).execute();
  }

  public String mileageLogin(MileageFactory factory, MileageModel event) {
    logger.writeLogger("No login information, try logging in");
    MileageApi mileageApi = factory.getMileage(Type.LOGIN);
    try (Response response = mileageRequest(mileageApi, event, "")) {
      if (!response.isSuccessful()) {
        throw new IOException("Unexpected code " + response);
      }
      String cookie = response.header("Set-Cookie");
      logger.writeLogger("set cookie : " + cookie);
      return cookie;
    } catch (IOException e) {
      logger.writeLogger("An error occurred during the login process" + e.getMessage());
      throw new RuntimeException(e);
    }
  }

  public List<MileageUseDetail> mileageFindUser(NodeList nList) {
    return IntStream.range(0, nList.getLength())
        .mapToObj(nList::item)
        .filter(node -> node.getNodeType() == Node.ELEMENT_NODE)
        .map(node -> (Element) node)
        .map(eElement -> MileageUseDetail.toDetail(eElement.getElementsByTagName("TD")))
        .collect(Collectors.toList());
  }

  public List<MileageInfo> mileageAllUser(NodeList nList) {
    return IntStream.range(0, nList.getLength())
        .mapToObj(nList::item)
        .filter(nNode -> nNode.getNodeType() == Node.ELEMENT_NODE)
        .map(nNode -> (Element) nNode)
        .map(eElement -> MileageInfo.toUser(eElement.getElementsByTagName("TD")))
        .collect(Collectors.toList());
  }



}
