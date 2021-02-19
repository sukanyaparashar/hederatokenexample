import com.hedera.hashgraph.sdk.*;
import io.github.cdimascio.dotenv.Dotenv;
import java.util.concurrent.TimeoutException;

public class CreateToken {
    public static void main(String[] args) throws HederaReceiptStatusException, TimeoutException, HederaPreCheckStatusException {
        //Grab your Hedera testnet account ID and private key
        AccountId myAccountId = AccountId.fromString(Dotenv.load().get("MY_ACCOUNT_ID"));
        PrivateKey myPrivateKey = PrivateKey.fromString(Dotenv.load().get("MY_PRIVATE_KEY"));

        //Create your Hedera testnet client
        Client client = Client.forTestnet();
        client.setOperator(myAccountId, myPrivateKey);

        //Create the transaction
        TokenCreateTransaction transaction = new TokenCreateTransaction()
                .setTokenName("YOUR_TOKEN_NAME")
                .setTokenSymbol("YOUR_TOKEN_SYMBOL")
                .setTreasuryAccountId(myAccountId)
                .setInitialSupply(5000)
                .setAdminKey(myPrivateKey.getPublicKey())
                .setMaxTransactionFee(new Hbar(30)); //Change the default max transaction fee

        //Build the unsigned transaction, sign with admin private key of the token, sign with the token treasury private key, submit the transaction to a Hedera network
        TransactionResponse txResponse = transaction.freezeWith(client).sign(myPrivateKey).execute(client);
        //Request the receipt of the transaction
        TransactionReceipt receipt = txResponse.getReceipt(client);
        //Get the token ID from the receipt
        TokenId tokenId = receipt.tokenId;
        System.out.println("The new token ID is " + tokenId);
    }
}
