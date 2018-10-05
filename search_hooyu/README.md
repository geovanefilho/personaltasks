# SignIn and Search Tech Test

## ASSUMPTIONS
- There was already a controller created to handle searches, so I used it instead of created another one. (It would not make sense have two controllers to handle searches)
- I used the model SimpleSurnameAndPostcodeQuery as a View Helper as it contains just the information that it was needed for searches. The only bad part in it was the needed to instantiate with null values as there isn't a basic constructor.
- I didn't put any of the search fields as required due there isn't any information that it was needed in the requirements.
- I used the SearchEngineRetrievalService to get the response in the search service created by me.


## DECISIONS
- I changed the views to JSP due that I have more experience in it.
- Some tests can be missing due the time that would need to create everything, but normally I would
create at least two different good cases, one bad case for each bad different case possible,
one for null values and one for empty values

## SOLUTIONS
- The Requirement 1 has his solution started in class: SignInController
- The Requirement 2 has his solution started in class: SearchController
- The Requirement 3 has his solution in class: SearchServiceImpl at filterForNonPaying method
- The Requirement 4 has his solution in class: SearchChargingServiceImpl