/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package Smart.Contracts.Romain;

import Smart.Contracts.Romain.managers.*;
import org.hyperledger.besu.ethereum.vm.OperationTracer;
import org.web3j.crypto.Credentials;
import org.web3j.evm.PassthroughTracer;
import org.web3j.quicksort.QuickSort;
import org.web3j.tx.gas.DefaultGasProvider;

import java.math.BigInteger;
import java.util.ArrayList;

public class Main {

    private static final String ADDRESS = "0x2cf178c0fcf153dd0f40db1af064824a8c6751a5";

    public static void main(String[] args) throws Exception {
        Web3JManager manager = new Web3JManager();
        try{
            manager.setup();
        }catch(Exception e){
            System.out.println("une erreur est survenue lors de la configuration de Web3J");
        }

        String quickSortContractAddress =
                QuickSort.deploy(manager.getWeb3J(),manager.getCredentials(),new DefaultGasProvider()).send().getContractAddress();

        QuickSort quickSort = QuickSort.load(ADDRESS,manager.getWeb3J(),manager.getCredentials(),new DefaultGasProvider());
        InputGenerator inputGenerator = new IntegerInputGenerator();

        BigInteger[] gas = new BigInteger[101];

        for(int i = 1; i < 101 ; i++) {
            ArrayList<BigInteger> tab = inputGenerator.generateArray(i);
            gas[i] = quickSort.sort(tab).send().getGasUsed();
        }

        for(int i = 0; i < gas.length; i++){
            System.out.println(gas[i]);
        }
    }
}