import com.hedera.hashgraph.sdk.*;
import io.github.cdimascio.dotenv.Dotenv;
import org.jetbrains.annotations.Contract;

import java.math.BigInteger;
import java.util.concurrent.TimeoutException;

public class QuerySmartContract {
    public static void main(String[] args) throws TimeoutException, HederaPreCheckStatusException {
        //Grab your Hedera testnet account ID and private key
        AccountId myAccountId = AccountId.fromString(Dotenv.load().get("MY_ACCOUNT_ID"));
        PrivateKey myPrivateKey = PrivateKey.fromString(Dotenv.load().get("MY_PRIVATE_KEY"));
        ContractId contractId = ContractId.fromString(Dotenv.load().get("CONTRACT_ID"));
        AccountId secondAccountId = AccountId.fromString(Dotenv.load().get("SECOND_ACCOUNT_ADDRESS"));

        //Create your Hedera testnet client
        Client client = Client.forTestnet();
        client.setOperator(myAccountId, myPrivateKey);

        //Check smart contract owner account balance
        var balanceOwnerParams = new ContractFunctionParameters()
                .addAddress(myAccountId.toSolidityAddress());

        var contractCallBalanceResult = new ContractCallQuery()
                .setContractId(contractId)
                .setGas(10000000)
                .setFunction("balanceOf", balanceOwnerParams)
                .execute(client);
        if (contractCallBalanceResult.errorMessage != null) {
            System.out.println("error calling contract: " + contractCallBalanceResult.errorMessage);
            return;
        }
        BigInteger balance = contractCallBalanceResult.getInt256(0);
        System.out.println("The account balance is: " + balance);

        //Transfer token from smart contract owner account to another address
        var transferParams = new ContractFunctionParameters()
                .addAddress(secondAccountId.toSolidityAddress())
                .addUint256(BigInteger.valueOf(200000));

        var contractCallTransferResult = new ContractExecuteTransaction()
                .setContractId(contractId)
                .setGas(10000000)
                .setFunction("transfer", transferParams)
                .execute(client);
        var transferResult = contractCallTransferResult.transactionId;
        System.out.println("Transfer is successful: " + transferResult);

        //Check the balance of the account where the tokens are transferred from the smart contract owner's account
        var balanceAccountParams = new ContractFunctionParameters()
                .addAddress(secondAccountId.toSolidityAddress());

        var contractCallBalanceAccountResult = new ContractCallQuery()
                .setContractId(contractId)
                .setGas(10000000)
                .setFunction("balanceOf", balanceAccountParams)
                .execute(client);
        if (contractCallBalanceAccountResult.errorMessage != null) {
            System.out.println("error calling contract: " + contractCallBalanceAccountResult.errorMessage);
            return;
        }
        BigInteger balanceAccount = contractCallBalanceAccountResult.getInt256(0);
        System.out.println("The account balance is: " + balanceAccount);
    }
}
