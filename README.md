**PREREQUISITES**
1. Download and install IntelliJ IDEA CE editor.
2. JDK 12+
3. Create a Hedera account on the portal.

**CREATE A TOKEN SMART CONTRACT IN SOLIDITY**
1. Create a token smart contract in solidity. There is one example_token.sol file with the project which can be modified according to your needs.
2. Compile the file using REMIX browser which is used significantly used for compiling .sol files.
3. Get the bytecode of the .sol file and save it.

**CHANGE THE CREDENTIALS**
1. Change the .env file variables with your Hedera account credentials (MY_ACCOUNT_ID & MY_PRIVATE_KEY).

**CREATE A FILE ON HEDERA NETWORK**
1. Open PublishFile.java inside the src/main/java folder and replace the byteCodeHex variable with your bytecode of the smart contract compiled with REMIX.
2. Run PublishFile.java.
3. When it finishes running, it outputs a file id.
4. Replace the FILE_ID variable in the .env file with the output.

**DEPLOY THE SMART CONTRACT**
1. Open DeploySmartContract.java inside src/main/java folder.
2. Run DeploySmartContract.java.
3. When it finishes running, it outputs a smart contract id.
4. Replace the CONTRACT_ID variable in the .env file with the output.

**CREATE ACCOUNT ON THE HEDERA NETWORK**
1. Open CreateAccount.java inside src/main/java folder.
2. Run CreateAccount.java.
3. When it finishes running, it outputs a new account id, private key and public key. Save those results.
4. Replace the SECOND_ACCOUNT_ADDRESS variable in the .env file with the account id from the result.

**QUERY THE SMART CONTRACT AND TRANSFER TOKENS**
1. Open QuerySmartContract.java inside src/main/java folder.
2. Run QuerySmartContract.java.
3. When it finishes running, it outputs the balance of the main account, transfer some tokens to other account and outputs the balance of the other account.

**CREATE A TOKEN ON HEDERA NETWORK WITHOUT SOLIDITY SMART CONTRACT**
1. Open CreateToken.java inside src/main/java folder.
2. Run CreateToken.java.
3. When it finishes running, it outputs the token id for the token created on the Hedera network.# hederatokenexample
