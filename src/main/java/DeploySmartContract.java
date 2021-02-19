import com.hedera.hashgraph.sdk.*;
import io.github.cdimascio.dotenv.Dotenv;

import java.math.BigInteger;
import java.util.concurrent.TimeoutException;

public class DeploySmartContract {
    public static void main(String[] args) throws TimeoutException, HederaPreCheckStatusException, HederaReceiptStatusException {

        //Grab your Hedera testnet account ID and private key
        AccountId myAccountId = AccountId.fromString(Dotenv.load().get("MY_ACCOUNT_ID"));
        PrivateKey myPrivateKey = PrivateKey.fromString(Dotenv.load().get("MY_PRIVATE_KEY"));

        //Create your Hedera testnet client
        Client client = Client.forTestnet();
        client.setOperator(myAccountId, myPrivateKey);
        var fileId = Dotenv.load().get("FILE_ID");

        //Deploy the smart contract
        var tx_contract = new ContractCreateTransaction();
        tx_contract.setBytecodeFileId(FileId.fromString(fileId));
        tx_contract.setMaxTransactionFee(Hbar.from(100000000));   // note: much higher than file storage!
        tx_contract.setGas(1000000);
        TransactionResponse txId_contract = tx_contract.execute(client);
        TransactionReceipt txReceipt_contract = txId_contract.getReceipt(client);
        System.out.println("Result of smart contract deployment:");
        System.out.println(txReceipt_contract.status);
        System.out.println("Smart contract lives at: " + txReceipt_contract.contractId);
    }
}
