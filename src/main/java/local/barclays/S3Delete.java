package local.barclays;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class S3Delete {

  public static void main(String[] args) throws IOException {

    AWSCredentials credentials = null;

    System.out.println(System.getenv());

    try {
      credentials = new ProfileCredentialsProvider("fh1-eu-west-1").getCredentials();
    } catch(Exception e) {
      throw new AmazonClientException(
          "Cannot load the credentials from the credential profiles file. " +
              "Please make sure that your credentials file is at the correct " +
              "location (~/.aws/credentials), and is in valid format.",
          e);
    }

    AmazonS3 s3 = AmazonS3ClientBuilder.standard()
        .withCredentials(new AWSStaticCredentialsProvider(credentials))
        .withRegion("eu-west-1")
        .build();

    String bucketName = "service-s3bucket-rdc-storage-rafs-rdc-rafs-pro-s3-d1qk0wcp63f6";
    String key = "dzi/128818/11-0149/1012";

    List<DeleteObjectsRequest.KeyVersion> keys = new ArrayList<DeleteObjectsRequest.KeyVersion>();

    System.out.println("===========================================");
    System.out.println("Listing Amazon S3 Objects in keyspace: " + bucketName + key);
    System.out.println("===========================================\n");

    try {
      ListObjectsV2Request request = new ListObjectsV2Request().withBucketName(bucketName).withPrefix(key);
      ListObjectsV2Result result;

//      do {
        result = s3.listObjectsV2(request);
        keys.clear();
        for (S3ObjectSummary objectSummary : result.getObjectSummaries()) {
//          keys.add(new DeleteObjectsRequest.KeyVersion(objectSummary.getKey()));
          System.out.println(" - " + objectSummary.getKey() + "  " +
              "(size = " + objectSummary.getSize() +
              ")");
        }
//        System.out.println("===========================================");
//        System.out.println("Deleting Amazon S3 Objects in keyspace: " + bucketName + key);
//        System.out.println("===========================================\n");
//
//        DeleteObjectsRequest deleteObjectsRequest = new DeleteObjectsRequest(bucketName).withQuiet(false);
//
//        deleteObjectsRequest.setKeys(keys);
//        DeleteObjectsResult delObjRes = null;
//        try {
//          delObjRes = s3.deleteObjects(deleteObjectsRequest);
//          System.out.format("Successfully deleted all the %s items.\n", delObjRes.getDeletedObjects().size());
//        } catch (MultiObjectDeleteException mode) {
//          printDeleteResults(mode);
//        }
        System.out.println("Next Continuation Token: " + result.getNextContinuationToken());
        request.setContinuationToken(result.getNextContinuationToken());
//      } while(result.isTruncated() == true);
    } catch (AmazonServiceException ase) {
      System.out.println("Caught an AmazonServiceException, " +
          "which means your request made it " +
          "to Amazon S3, but was rejected with an error response " +
          "for some reason.");
      System.out.println("Error Message:    " + ase.getMessage());
      System.out.println("HTTP Status Code: " + ase.getStatusCode());
      System.out.println("AWS Error Code:   " + ase.getErrorCode());
      System.out.println("Error Type:       " + ase.getErrorType());
      System.out.println("Request ID:       " + ase.getRequestId());
    } catch (AmazonClientException ace) {
      System.out.println("Caught an AmazonClientException, " +
          "which means the client encountered " +
          "an internal error while trying to communicate" +
          " with S3, " +
          "such as not being able to access the network.");
      System.out.println("Error Message: " + ace.getMessage());
    }

//    System.out.println("===========================================");
//    System.out.println("Deleting Amazon S3 Objects in keyspace: " + bucketName + key);
//    System.out.println("===========================================\n");
//
//    DeleteObjectsRequest deleteObjectsRequest = new DeleteObjectsRequest(bucketName).withQuiet(false);
//
//    DeleteObjectsResult delObjRes = null;
//    try {
//      delObjRes = s3.deleteObjects(deleteObjectsRequest);
//      System.out.format("Successfully deleted all the %s items.\n", delObjRes.getDeletedObjects().size());
//    } catch (MultiObjectDeleteException mode) {
//      printDeleteResults(mode);
//    }
  }

  static void printDeleteResults(MultiObjectDeleteException mode) {
    System.out.format("%s \n", mode.getMessage());
    System.out.format("No. of objects successfully deleted = %s\n", mode.getDeletedObjects().size());
    System.out.format("No. of objects failed to delete = %s\n", mode.getErrors().size());
    System.out.format("Printing error data...\n");
    for (MultiObjectDeleteException.DeleteError deleteError : mode.getErrors()){
      System.out.format("Object Key: %s\t%s\t%s\n",
          deleteError.getKey(), deleteError.getCode(), deleteError.getMessage());
    }
  }
}
