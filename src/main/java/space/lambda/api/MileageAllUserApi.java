package space.lambda.api;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import okhttp3.FormBody;
import okhttp3.FormBody.Builder;
import okhttp3.Request;
import okhttp3.RequestBody;
import space.lambda.api.base.BaseMileageApi;
import space.lambda.data.Mileage;

public class MileageAllUserApi extends BaseMileageApi implements MileageApi {

  @Override
  public Request getRequest(RequestBody requestBody,String cookie) {
    return new Request.Builder()
        .url(apiUrl + "ddd.sheetAction")
        .post(requestBody)
        .addHeader("User-Agent", agent)
        .addHeader("Accept", agent)
        .addHeader("Accept-Language", "ko-KR,ko;q=0.9,en-US;q=0.8,en;q=0.7,fr;q=0.6")
        .addHeader("Referer", apiUrl)
        .addHeader("Cookie", cookie)
        .build();
  }

  @Override
  public Builder getRequestBody() {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    String formattedDate = LocalDate.now().format(formatter);
    return new FormBody.Builder()
        .add("birth_day", "01")
        .add("birth_day2", "01")
        .add("birth_month", "01")
        .add("birth_month2", "01")
        .add("cst_nos", "")
        .add("date1_1", formattedDate)  // 과거 날짜여도 상관 없음
        .add("date1_2", formattedDate)  // 과거 날짜여도 상관 없음
        .add("date_period1", "366")
        .add("list", "")
        .add("mySheet1", "")
        .add("page_no", "1")  // ✓
        .add("page_size", "4000") // ✓
        .add("page_url", "/master/cust/cust020.jsp")
        .add("r_ogn_cd", "HNON")
        .add("r_ogn_fg", "C")
        .add("row_cnt", "")
        .add("CHG_SHOP_CD", "")
        .add("CST_CARD_NO", "")  // ✓ 학번 검색
        .add("CST_CARD_USE_FG", "")
        .add("CST_CLS_CD", "0") //0재학생, 1교직원
        .add("CST_ID", "")
        .add("CST_NM", "")
        .add("CST_NO", "")  // ✓ 회원번호 검색
        .add("DATE_FG", "A")
        .add("DM_RECV_YN", "")
        .add("EMAIL_ADDR", "")
        .add("EMAIL_RECV_YN", "")
        .add("EX_CST", "")
        .add("EX_VISIT", "")
        .add("HP_CARD1", "")
        .add("HP_CARD2", "")
        .add("HP_NO1", "")  // ✓ 010/011/016
        .add("HP_NO2", "") // ✓ aaaa/aaa
        .add("HP_NO3", "") // ✓ bbbb
        .add("HP_SALE1", "")
        .add("HP_SALE2", "")
        .add("INS_SHOP_CD", "")
        .add("MVIEW_YN", "N")
        .add("ORDERYN", "")
        .add("PNT_FG", "A")
        .add("SALE_FG", "C")
        .add("SEX_FG", "A")
        .add("SHEETSEQ", "1")
        .add("SMS_RECV_YN", "")
        .add("S_CONTROLLER", "master.cust.cust020")
        .add("S_CST_NM", "")
        .add("S_FORWARD", "")
        .add("S_METHOD", "search")
        .add("S_SAVENAME",
            "CST_NO|CST_NM|CST_CLS_NM|CST_CARD_NO|ACC_POINT|USE_POINT|ADJ_POINT|AVL_POINT|ACC_SALE_CNT|ACC_SALE_AMT|INS_DT")
        .add("S_TEL_NO", "")
        .add("S_TREECOL", "")
        .add("SaleYN", "")
        .add("TEL_NO1", "")
        .add("TEL_NO2", "")
        .add("TEL_NO3", "")
        .add("TERM_FG", "A")
        .add("USE_YN", "")
        .add("WEDDING_YN", "A");
  }

  @Override
  public RequestBody setBody(Mileage event) {
    return getRequestBody().build();
  }

}
