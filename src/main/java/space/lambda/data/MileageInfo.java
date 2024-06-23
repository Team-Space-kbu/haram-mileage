package space.lambda.data;

import static space.lambda.util.TextUtil.getTextContent;

import lombok.Builder;
import lombok.Getter;
import org.w3c.dom.NodeList;

@Builder
@Getter
public class MileageInfo {
  String membershipNum; //회원번호
  String userName;  //회원명
  String membershipStatus;  //회원등급
  String cardNumber;   //학번
  String earnPoint;   //포인트적립
  String usePoint;   //포인트사용
  String adjustPoint;  //포인트조정
  String availablePoint;  //포인트가용
  String paymentsCount;     //결제횟수
  String paymentAmount;  //결제금액
  String joinDate;      //가입일

  public static MileageInfo toUser(NodeList nList2){
    return MileageInfo.builder()
        .membershipNum(getTextContent(nList2, 0))
        .userName(getTextContent(nList2, 1))
        .membershipStatus(getTextContent(nList2, 2))
        .cardNumber(getTextContent(nList2, 3))
        .earnPoint(getTextContent(nList2, 4))
        .usePoint(getTextContent(nList2, 5))
        .adjustPoint(getTextContent(nList2, 6))
        .availablePoint(getTextContent(nList2, 7))
        .paymentsCount(getTextContent(nList2, 8))
        .paymentAmount(getTextContent(nList2, 9))
        .joinDate(getTextContent(nList2, 10))
        .build();
  }
}
