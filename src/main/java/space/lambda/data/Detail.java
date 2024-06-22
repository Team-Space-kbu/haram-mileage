package space.lambda.data;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class Detail {
  private String changeDate;   //변경일자
  private String saleDate;     //판매일자
  private String status;       //구분
  private String point;        //포인트
  private String note;         //비고
}
