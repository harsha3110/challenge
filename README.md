# challenge

Added a public methods transferMoney() and updateAccountBalance() in AccountsRepositoryInMemory.java with following considerations :
1. Checked if the from and to accounts are valid and exist, else will throw exception.
2. From account balance should not be less than the amount to be transfered from it to the toAccount. If not then it will throw exception.
3. Check if amount to be trasnfered is not negative or zero.
4. Overdraft check (negative amount to be transfered) is applied and respective exception is thrown.
5. The fromAccount and toAccount are in synchronized block to make thread safe.
6. The changes are reflected in the original accounts list.
7. Notification is being sent to both accountIds with other accountIds and amount and balance.
8. Respective Exceptions added to handle overdraft condition, Negative amount and missing account.
9. Controller entry added for Post request to transfer money to the account. Can be tested in Postman with following url and RequestBody :
   url : POST http://localhost:18080/v1/accounts/transfer
   raw JSON body :
   {
    "accountIdFrom":"102",
    "accountIdTo": "101",
    "amount" : "50"
}

11. Junit Test Cases written in AccountsControllerTest.java for success and exception scenarios.
12. Changes Made in files :
    a. AccountsController.java
    b. AccountsService.java
    c. AccountsRepository.java
    d. AccountsRespositoryInMemory.java
    e. AccountsControllerTest.java
    f. AccountTransfer.java

14. Added classes :
    a. AccountTransfer.java
    b. MissingAccountException.java
    c. NegativeAmountException.java


