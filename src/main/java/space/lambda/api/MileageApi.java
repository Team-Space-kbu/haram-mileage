package space.lambda.api;

import okhttp3.FormBody.Builder;
import okhttp3.Request;
import okhttp3.RequestBody;
import space.lambda.model.MileageModel;

public interface MileageApi {

  Request getRequest(RequestBody requestBody, String cookie);

  Builder getRequestBody();

  RequestBody setBody(MileageModel event);
}
