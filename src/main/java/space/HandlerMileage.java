package space;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import java.io.IOException;
import java.io.StringReader;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import space.api.MileageApi;
import space.data.Mileage;
import space.data.MileageType;
import space.data.SpaceResponse;

// Handler value: space.HandlerMileage
public class HandlerMileage implements RequestHandler<Mileage, SpaceResponse<Object>> {

  private static LambdaLogger logger;
  private static MileageApi mileageApi;
  private static String cookie;

  private static final MileageFactory factory = new MileageFactory();
  private static final OkHttpClient client = new OkHttpClient().newBuilder().build();
  private static final DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();

  private void init(Context context) {
    logger = context.getLogger();
    logger.log("String Login, Not found login cookie");

    mileageApi = factory.getMileage(MileageType.LOGIN);
    RequestBody body = mileageApi.getRequestBody().build();
    Request request = mileageApi.getRequest(body, "");
    try {
      cookie = client.newCall(request).execute().header("Set-Cookie");
    } catch (IOException e) {
      logger.log(e.getMessage());
      throw new RuntimeException(e);
    }
  }

  private RequestBody setBody(Mileage event) {
    switch (event.toType()) {
      case FIND_USER -> {
        // 고객번호
        return mileageApi.getRequestBody().add("CST_NO", event.data()).build();
      }
      case FIND_ALL -> {
        return mileageApi.getRequestBody().build();
      }
      case LOGIN -> {
        return mileageApi.getRequestBody().build();
      }
      default -> {
        throw new IllegalStateException("잘못된 요청값이 처리되었습니다.");
      }
    }
  }

  @Override
  public SpaceResponse<Object> handleRequest(Mileage event, Context context) {
    if (logger == null || cookie == null) {
      init(context);
    }
    mileageApi = factory.getMileage(event.toType());
    Request request = mileageApi.getRequest(setBody(event), "");
    try (Response response = client.newCall(request).execute()) {
      if (!response.isSuccessful()) {
        throw new IOException("Unexpected code " + response);
      }
      String string = response.body()
          .string()
          .trim()
          .replaceAll("[^\\u0009\\u000A\\u000D\\u0020-\\uD7FF\\uE000-\\uFFFD\\u10000-\\u10FFF]+", "");
      InputSource inputSource = new InputSource(new StringReader(string));
      Document document = builderFactory.newDocumentBuilder().parse(inputSource);


    } catch (IOException e) {
      logger.log("IOException Error : " + e.getMessage());
      throw new RuntimeException(e);
    } catch (ParserConfigurationException e) {
      logger.log("ParserConfigurationException Error : " + e.getMessage());
      throw new RuntimeException(e);
    } catch (SAXException e) {
      logger.log("SAXException Error : " + e.getMessage());
      throw new RuntimeException(e);
    } catch (Throwable e){
      logger.log("Throwable Error : " + e.getMessage());
      throw new RuntimeException(e);
    }

    logger.log("String found: " + event.data());
    return SpaceResponse.builder().build();
  }
}


