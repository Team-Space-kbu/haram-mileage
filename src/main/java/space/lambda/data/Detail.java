package space.lambda.data;

import static space.lambda.util.TextUtil.getTextContent;

import lombok.Builder;
import lombok.Getter;
import org.w3c.dom.NodeList;

@Builder
@Getter
public class Detail {
  private String changeDate;   //변경일자
  private String saleDate;     //판매일자
  private String status;       //구분
  private String point;        //포인트
  private String note;         //비고


  public static Detail toDetail(NodeList nList2){
    return Detail.builder()
        .changeDate(getTextContent(nList2, 0))
        .saleDate(getTextContent(nList2, 1))
        .status(getTextContent(nList2, 2))
        .point(getTextContent(nList2, 3))
        .note(getTextContent(nList2, 4))
        .build();
  }
}
