package space.data;

import lombok.Builder;

@Builder
public class SpaceResponse<T> {
  private String code;
  private String message;
  private T data;
}
