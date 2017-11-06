package local.barclays;

import com.amazonaws.services.ecr.AmazonECR;
import com.amazonaws.services.ecr.AmazonECRClientBuilder;
import com.amazonaws.services.ecr.model.DescribeRepositoriesRequest;
import com.amazonaws.services.ecr.model.DescribeRepositoriesResult;
import com.amazonaws.services.ecr.model.PutLifecyclePolicyRequest;
import com.amazonaws.services.ecr.model.PutLifecyclePolicyResult;

public class EcrPlayground {

  public static void main(String[] args) {
    AmazonECR ecrClient = AmazonECRClientBuilder.defaultClient();

    DescribeRepositoriesRequest request = new DescribeRepositoriesRequest().withRepositoryNames("adhoc-p1-barclays-ecr-poc");
    DescribeRepositoriesResult result = ecrClient.describeRepositories(request);

    String lifecyclePolicyText = "{\"rules\":[{\"rulePriority\":1, \"description\":\"Rule for removing old images\",\"selection\":{\"tagStatus\": \"tagged\",\"tagPrefixList\":[\"latest\"],\"countType\":\"imageCountMoreThan\", \"countNumber\": 5}, \"action\":{\"type\":\"expire\"}}]}";

    PutLifecyclePolicyRequest policyRequest = new PutLifecyclePolicyRequest().withRepositoryName("adhoc-p1-barclays-ecr-poc").withLifecyclePolicyText(lifecyclePolicyText);

    PutLifecyclePolicyResult lifecyclePolicyResult = ecrClient.putLifecyclePolicy(policyRequest);

    System.out.println("Results: " + result.toString());
    System.out.println("Results: " + lifecyclePolicyResult.toString());
  }

}
