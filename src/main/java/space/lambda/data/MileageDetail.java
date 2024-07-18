package space.lambda.data;

import static space.lambda.util.TextUtil.getTextContent;
import static space.lambda.util.TextUtil.toDate;

import lombok.Builder;
import lombok.Getter;
import org.w3c.dom.NodeList;
import space.lambda.model.MileageType;

@Builder
@Getter
public class MileageDetail {

  private String changeDate;   //변경일자
  private String saleDate;     //판매일자
  private String status;       //구분
  private String point;        //포인트
  private String etc;         //비고
  private MileageType type;

  public static MileageDetail toDetail(NodeList nList2) {
    String note = getTextContent(nList2, 4);
    String shop = note.replaceAll("성서대\\.|:|\\(승인\\)|포인트|사용|취소", "").trim();
    return MileageDetail.builder()
        .changeDate(toDate(getTextContent(nList2, 0)))
        .saleDate(toDate(getTextContent(nList2, 1)))
        .status(getTextContent(nList2, 2))
        .point(getTextContent(nList2, 3))
        .etc(shop)
        .type(MileageType.findMileageType(shop, note))
        .build();
  }
}
