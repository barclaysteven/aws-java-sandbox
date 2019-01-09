package local.barclays;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.simplesystemsmanagement.AWSSimpleSystemsManagement;
import com.amazonaws.services.simplesystemsmanagement.AWSSimpleSystemsManagementClientBuilder;
import com.amazonaws.services.simplesystemsmanagement.model.DocumentFilter;
import com.amazonaws.services.simplesystemsmanagement.model.DocumentFilterKey;
import com.amazonaws.services.simplesystemsmanagement.model.ListDocumentsRequest;
import com.amazonaws.services.simplesystemsmanagement.model.ListDocumentsResult;

import java.util.ArrayList;
import java.util.List;

public class SsmDemo1 {

  public String ssmDemoHandler(String name, Context context) {
    LambdaLogger logger = context.getLogger();

    AWSSimpleSystemsManagement ssm = AWSSimpleSystemsManagementClientBuilder.standard().build();

    DocumentFilter documentName = new DocumentFilter();
    documentName.setKey(DocumentFilterKey.Name);
    documentName.setValue("adhoc-p1-barclays-lambda-training");

    List<DocumentFilter> documentFilters = new ArrayList<DocumentFilter>();
    documentFilters.add(documentName);

    ListDocumentsRequest request = new ListDocumentsRequest();
    request.setDocumentFilterList(documentFilters);

    ListDocumentResult result = ssm.listDocuments(request);

    logger.log("Results : " + result.toString());
    return result.toString();
  }
}
