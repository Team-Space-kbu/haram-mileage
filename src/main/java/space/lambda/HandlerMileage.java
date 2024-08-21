package space.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.StringReader;
import java.util.List;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import okhttp3.Response;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import space.lambda.data.MileageDetail;
import space.lambda.data.MileageInfo;
import space.lambda.data.MileageUseDetail;
import space.lambda.model.MileageModel;
import space.lambda.model.Type;
import space.lambda.util.LoggerUtil;

// Handler value: space.lambda.HandlerMileage
public class HandlerMileage implements
    RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {

  private static final ObjectMapper objectMapper = new ObjectMapper();
  private static final MileageFactory factory = new MileageFactory();
  private static final DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
  private static final MileageService service = new MileageService();
  private static final LoggerUtil logger = new LoggerUtil();
  private static String cookie;

  @Override
  public APIGatewayProxyResponseEvent handleRequest(
      APIGatewayProxyRequestEvent input,
      Context context
  ) {
    if (logger.statusLogger()) {
      //로그기록 변수 설정
      logger.setLogger(context.getLogger());
    }
    try {
      MileageModel event = objectMapper.readValue(input.getBody(), MileageModel.class);

      if (event.toType() == Type.EMPTY) {
        logger.writeLogger("The requested value type is invalid. : " + event.type());
        throw new RuntimeException("The requested value type is invalid.");
      }

      //요청 값에 따른 body값 생성
      switch (event.toType()) {
        case FIND_ALL, USER_DETAIL, DETAIL_USE -> {
          return new APIGatewayProxyResponseEvent()
              .withStatusCode(200)
              .withBody(objectMapper.writeValueAsString(getMileageData(event)));
        }
        default -> throw new RuntimeException();
      }
    } catch (IOException e) {
      logger.writeLogger("IOException Error : " + e.getMessage());
      throw new RuntimeException(e);
    } catch (ParserConfigurationException e) {
      logger.writeLogger("ParserConfigurationException Error : " + e.getMessage());
      throw new RuntimeException(e);
    } catch (SAXException e) {
      logger.writeLogger("SAXException Error : " + e.getMessage());
      throw new RuntimeException(e);
    } catch (Throwable e) {
      logger.writeLogger("Throwable Error : " + e.getMessage());
      throw new RuntimeException(e);
    }
  }

  private Object getMileageData(
      MileageModel event
  ) throws IOException, ParserConfigurationException, SAXException {
    //쿠기 상태가 없을 경우 로그인을 요청하여 쿠키 발급
    if (cookie == null || cookie.isEmpty()) {
      cookie = service.mileageLogin(factory, event);
    } else {
      logger.writeLogger("Login information exists.");
    }

    //api 요청 구간
    Response response = service.mileageRequest(
        factory.getMileage(event.toType()),
        event,
        cookie
    );

    if (!response.isSuccessful()) {
      throw new IOException("Unexpected code " + response);
    }
    NodeList nList = toNodeList(response);

    //로그인 정보가 말소 되었을 경우 재발급할 수 있도록 재귀 요청
    if (nList.getLength() <= 0) {
      cookie = "";
      logger.writeLogger("Your login information cannot be verified.");
      return getMileageData(event);
    }

    switch (event.toType()) {
      case FIND_ALL, DETAIL_USER -> {
        return service.mileageAllUser(nList);
      }
      case USER_DETAIL -> {
        return service.mileageFindUser(nList);
      }
      case DETAIL_USE -> {
        List<MileageUseDetail> userUseDetail = service.mileageFindUser(nList);
        return MileageDetail.builder()
            .userUseDetail(userUseDetail)
            .mileageInfos((List<MileageInfo>) getMileageData(new MileageModel("DETAIL_USER", event.data())))
            .build();
      }
      default -> throw new RuntimeException();
    }
  }

  private NodeList toNodeList(
      Response response
  ) throws ParserConfigurationException, IOException, SAXException {
    String string = response.body() != null ?
        response.body()
            .string()
            .trim()
            .replaceAll(
                "[^\\u0009\\u000A\\u000D\\u0020-\\uD7FF\\uE000-\\uFFFD\\u10000-\\u10FFF]+",
                ""
            )
        : "";
    InputSource inputSource = new InputSource(new StringReader(string));
    logger.writeLogger("response xml to InputSource");

    Document document = builderFactory.newDocumentBuilder().parse(inputSource);
    return document.getElementsByTagName("TR");
  }

}


