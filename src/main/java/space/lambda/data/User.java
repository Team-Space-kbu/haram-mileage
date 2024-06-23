package space.lambda.data;

import static space.lambda.util.TextUtil.getTextContent;

import lombok.Builder;
import lombok.Getter;
import org.w3c.dom.NodeList;

@Builder
@Getter
public class User {
  String uniqueNumber; //회원번호
  String userType;  //회원명
  String studentStatus;  //회원등급
  String cardNumber;   //학번
  String earnPoints;   //포인트적립
  String usePoints;   //포인트사용
  String adjustmentPoint;  //포인트조정
  String availablePoints;  //포인트가용
  String paymentsNum;     //결제횟수
  String amountPayment;  //결제금액
  String joinDate;      //가입일

  public static User toUser(NodeList nList2){
    return User.builder()
        .uniqueNumber(getTextContent(nList2, 0))
        .userType(getTextContent(nList2, 1))
        .studentStatus(getTextContent(nList2, 2))
        .cardNumber(getTextContent(nList2, 3))
        .earnPoints(getTextContent(nList2, 4))
        .usePoints(getTextContent(nList2, 5))
        .adjustmentPoint(getTextContent(nList2, 6))
        .availablePoints(getTextContent(nList2, 7))
        .paymentsNum(getTextContent(nList2, 8))
        .amountPayment(getTextContent(nList2, 9))
        .joinDate(getTextContent(nList2, 10))
        .build();
  }
}
