Feature: Categories details

  Scenario Outline: Verify the category details for category <categoryId>
    Given I fetch details for category with id "6327" and catalogue parameter "false"
    Then I should see the status code as <expectedStatusCode>
    And the response should contain the following properties:
      | Property              | Value                  |
      | CategoryId            | <categoryId>           |
      | Name                  | <expectedCategoryName> |
      | CanRelist             | <expectedCanRelist>    |

    And the response has promotion with "<expectedPromoName>" name and "<expectedPromoDesc>" description

    Examples:
      | categoryId | expectedStatusCode | expectedCategoryName | expectedCanRelist | expectedPromoName | expectedPromoDesc |
      | 6327       | 200                | Carbon credits       | true              | Gallery           | Good position in category   |