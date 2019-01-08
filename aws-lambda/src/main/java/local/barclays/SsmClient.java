package local.barclays;

import com.amazonaws.services.simplesystemsmanagement.AWSSimpleSystemsManagement;
import com.amazonaws.services.simplesystemsmanagement.AWSSimpleSystemsManagementClientBuilder;
import com.amazonaws.services.simplesystemsmanagement.model.DocumentFilter;
import com.amazonaws.services.simplesystemsmanagement.model.DocumentFilterKey;
import com.amazonaws.services.simplesystemsmanagement.model.ListDocumentsRequest;
import com.amazonaws.services.simplesystemsmanagement.model.ListDocumentsResult;

import java.util.ArrayList;
import java.util.List;

public class SsmClient {
  private AWSSimpleSystemsManagement ssm;

  public SsmClient() {

    ssm = AWSSimpleSystemsManagementClientBuilder.standard().build();
  }

  public String getSsmDocument() {
    DocumentFilter documentName = new DocumentFilter();
    documentName.setKey(DocumentFilterKey.Name);
    documentName.setValue("adhoc-p1-barclays-lambda-training");

    List<DocumentFilter> documentFilters = new ArrayList<DocumentFilter>();
    documentFilters.add(documentName);

    ListDocumentsRequest request = new ListDocumentsRequest();
    request.setDocumentFilterList(documentFilters);

    ListDocumentsResult result = ssm.listDocuments(request);

    return result.toString();
  }
}
