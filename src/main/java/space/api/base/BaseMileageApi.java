package space.api.base;


import space.api.MileageApi;

public abstract class BaseMileageApi implements MileageApi {
  protected String apiUrl = "https://asp.netusys.com/";
  protected String agent = "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/125.0.0.0 Safari/537.36";
  protected String accept = "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9";
}
