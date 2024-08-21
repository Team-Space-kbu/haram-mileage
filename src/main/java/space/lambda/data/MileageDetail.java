package space.lambda.data;

import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class MileageDetail {
  List<MileageInfo> mileageInfos;
  List<MileageUseDetail> userUseDetail;
}
