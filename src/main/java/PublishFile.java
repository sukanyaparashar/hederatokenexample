import com.google.protobuf.InvalidProtocolBufferException;
import com.hedera.hashgraph.sdk.*;
import io.github.cdimascio.dotenv.Dotenv;
import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.TimeoutException;

public class PublishFile {
    public static void main(String[] args) throws HederaReceiptStatusException, TimeoutException, HederaPreCheckStatusException, InvalidProtocolBufferException {

        //Grab your Hedera testnet account ID and private key
        AccountId myAccountId = AccountId.fromString(Dotenv.load().get("MY_ACCOUNT_ID"));
        PrivateKey myPrivateKey = PrivateKey.fromString(Dotenv.load().get("MY_PRIVATE_KEY"));

        //Create your Hedera testnet client
        Client client = Client.forTestnet();
        client.setOperator(myAccountId, myPrivateKey);
        var byteCodeHex = "YOUR SMART CONTRACT BYTE CODE";
        //Create file for smart contract
        var tx = new FileCreateTransaction();
        tx.setContents(byteCodeHex.getBytes());
        tx.setExpirationTime(Instant.now().plus(Duration.ofSeconds(7890000)));
        tx.setMaxTransactionFee(Hbar.from(100000000));
        TransactionResponse fileTxId = tx.execute(client);
        TransactionReceipt fileReceipt = fileTxId.getReceipt(client);
        System.out.println(fileReceipt.status);
        System.out.println(fileReceipt.fileId);
    }
}
