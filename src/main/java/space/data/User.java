package space.data;

import lombok.Builder;
import lombok.Getter;

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



}
