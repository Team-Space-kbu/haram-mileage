package space.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import java.io.IOException;
import java.io.StringReader;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import okhttp3.Response;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import space.lambda.api.MileageApi;
import space.lambda.data.Mileage;
import space.lambda.util.LoggerUtil;

// Handler value: space.lambda.HandlerMileage
public class HandlerMileage implements RequestHandler<Mileage, Object> {

  private static String cookie;

  private static final MileageFactory factory = new MileageFactory();
  private static final DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
  private static final MileageService service = new MileageService();
  private final LoggerUtil logger = new LoggerUtil();

  @Override
  public Object handleRequest(Mileage event, Context context) {
    if (logger.statusLogger()) {
      //로그기록 변수 설정
      logger.setLogger(context.getLogger());
    }

    //쿠기 상태가 없을 경우 로그인을 요청하여 쿠키 발급
    if (cookie == null || cookie.isEmpty()) {
      cookie = service.mileageLogin(factory, event);
    } else {
      logger.writeLogger("Login information exists.");
    }
    try  {
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
        return handleRequest(event, context);
      }

      //요청 값에 따른 body값 생성
      switch (event.toType()) {
        case FIND_ALL -> {
          return service.mileageAllUser(nList);
        }
        case FIND_USER -> {
          return service.mileageFindUser(nList);
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

  private NodeList toNodeList(
      Response response
  ) throws ParserConfigurationException, IOException, SAXException {
    String string = response.body()
        .string()
        .trim()
        .replaceAll(
            "[^\\u0009\\u000A\\u000D\\u0020-\\uD7FF\\uE000-\\uFFFD\\u10000-\\u10FFF]+",
            ""
        );
    InputSource inputSource = new InputSource(new StringReader(string));
    logger.writeLogger("response xml to InputSource");

    Document document = builderFactory.newDocumentBuilder().parse(inputSource);
    return document.getElementsByTagName("TR");
  }


}


