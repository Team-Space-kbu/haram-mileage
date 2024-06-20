package space.lambda.api;

import okhttp3.FormBody.Builder;
import okhttp3.Request;
import okhttp3.RequestBody;
import space.lambda.api.base.BaseMileageApi;
import space.lambda.data.Mileage;

public class MileageFindUserApi extends BaseMileageApi implements MileageApi {

  @Override
  public Request getRequest(RequestBody requestBody,String cookie) {
    return new Request.Builder()
        .url(apiUrl + "ddd.sheetAction")
        .post(requestBody)
        .addHeader("User-Agent", agent)
        .addHeader("Accept", accept)
        .addHeader("Accept-Language", "ko-KR,ko;q=0.9,en-US;q=0.8,en;q=0.7,fr;q=0.6")
        .addHeader("Referer", apiUrl)
        .addHeader("Cookie", cookie)
        .build();
  }

  @Override
  public Builder getRequestBody() {
    return new Builder()
        .add("S_CONTROLLER", "master.cust.cust020_cst_info")
        .add("S_METHOD", "search")
        .add("S_SAVENAME", "CHG_DATE|SALE_DATE|CHG_FG|POINT|REMARK")
        .add("SHEETSEQ", "2")
        .add("page_no", "1")
        .add("page_no2", "1")
        .add("page_size", "2000")
        .add("page_size2", "2000");

  }

  @Override
  public RequestBody setBody(Mileage event) {
    return getRequestBody()
        .add("CST_NO", event.data())       //조회할 사용자 정보
        .build();
  }
}
