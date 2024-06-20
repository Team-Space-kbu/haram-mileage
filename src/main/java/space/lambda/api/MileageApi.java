package space.lambda.api;

import okhttp3.FormBody.Builder;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

public interface MileageApi {

  Request getRequest(RequestBody requestBody,String cookie);

  Builder getRequestBody();

}
