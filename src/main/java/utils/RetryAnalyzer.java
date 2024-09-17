package utils;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

/**
 * IRetryAnalyzer is an interface in TestNG that provides a mechanism to retry failed tests a specified number of times. This can be useful in scenarios where intermittent failures occur due to network issues, resource constraints, or other external factors.
 *
 * Here's how IRetryAnalyzer works:
 *
 * Implementation: You create a class that implements the IRetryAnalyzer interface.
 * Retry Logic: In the retry(ITestResult result) method, you define the retry logic. This typically involves checking if the test has failed a certain number of times and returning true if it should be retried, otherwise returning false.
 * Configuration: You annotate your test method with @Test(retryAnalyzer = YourRetryAnalyzerClass.class).
 * Why use IRetryAnalyzer in automation?
 *
 * Handle intermittent failures: It helps to deal with flaky tests that fail due to temporary conditions.
 * Improve test stability: By retrying failed tests, you can increase the overall reliability of your test suite.
 * Reduce manual intervention: It can reduce the need for manual re-runs of failed tests.
 * Flexibility: You can customize the retry logic to suit your specific needs, such as retrying only certain types of failures or limiting the maximum number of retries.
 */
public class RetryAnalyzer implements IRetryAnalyzer {
    int count = 0;
    int retryCount = 1;
    @Override
    public boolean retry(ITestResult iTestResult) {
        while (count<retryCount){
            count++;
            return true;
        }
        return false;
    }
}
