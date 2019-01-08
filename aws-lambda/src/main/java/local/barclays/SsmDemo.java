package local.barclays;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;

public class SsmDemo {

  SsmClient ssm = new SsmClient();

  public String ssmDemoHandler(String name, Context context) {
    LambdaLogger logger = context.getLogger();

    String document = ssm.getSsmDocument();

    logger.log("Results : " + document);
    return document;
  }
}
