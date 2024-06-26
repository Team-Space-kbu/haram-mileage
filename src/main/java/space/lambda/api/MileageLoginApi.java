package space.lambda.api;

import okhttp3.FormBody;
import okhttp3.FormBody.Builder;
import okhttp3.Request;
import okhttp3.RequestBody;
import space.lambda.api.base.BaseMileageApi;
import space.lambda.model.MileageModel;

public class MileageLoginApi extends BaseMileageApi implements MileageApi {

  @Override
  public Request getRequest(RequestBody requestBody, String cookie) {
    return new Request.Builder()
        .url(apiUrl + "login/login_check.jsp")
        .post(requestBody)
        .addHeader("User-Agent", agent)
        .addHeader("DNT", "1")
        .addHeader("Accept", accept)
        .addHeader("Accept-Language", "ko-KR,ko;q=0.9,en-US;q=0.8,en;q=0.7,fr;q=0.6")
        .addHeader("Referer", apiUrl + "mobile/login/login_form.jsp?logoutFg=Y")
        .build();
  }

  @Override
  public Builder getRequestBody() {
    return new FormBody.Builder()
        .add("mac_addr", "")
        .add("AutoFg", "M")
        .add("appfg", "web")
        .add("logoutFg", "Y")
        .add("login_auto_serial", "Y");
  }

  @Override
  public RequestBody setBody(MileageModel event) {
    return getRequestBody()
        /////////////////////
        //로그인 관련 데이터 추가//
        /////////////////////
        .build();
  }
}
