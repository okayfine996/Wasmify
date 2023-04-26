package com.github.okayfine996.wasmify.cmwasm.utils.crypto;

import com.github.okayfine996.wasmify.cmwasm.exception.AddressFormatException;
import com.github.okayfine996.wasmify.cmwasm.utils.crypto.encode.Bech32;
import com.github.okayfine996.wasmify.cmwasm.utils.crypto.encode.ConvertBits;
import com.github.okayfine996.wasmify.cmwasm.utils.crypto.hash.Ripemd;
import org.web3j.crypto.Keys;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import static org.bitcoinj.core.ECKey.CURVE;

public class AddressUtil {

    public static String createNewAddressSecp256k1(String mainPrefix, byte[] publickKey){
        // convert 33 bytes public key to 65 bytes public key

        String addressResult = null;
        try {
            byte[] uncompressedPubKey = CURVE.getCurve().decodePoint(publickKey).getEncoded(false);
            byte[] pub = new byte[64];
            // truncate last 64 bytes to generate address
            System.arraycopy(uncompressedPubKey, 1, pub, 0, 64);

            //get address
            byte[] address = Keys.getAddress(pub);
            //get okexchain
            byte[] bytes = encode(0, address);
            addressResult = Bech32.encode(mainPrefix, bytes);
        } catch (Exception e) {

        }
        return addressResult;
    }

    public static byte[] getPubkeyValue(byte[] publickKey) throws Exception {
        try {
            byte[] pubKeyHash = sha256Hash(publickKey, 0, publickKey.length);
            byte[] value = Ripemd.ripemd160(pubKeyHash);
            return value;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    public static String getPubkeyBech32FromValue(String mainPrefix, byte[] publickKeyValue) throws Exception {
        try {
            byte[] bytes = encode(0, publickKeyValue);
            String pubBech32 = Bech32.encode(mainPrefix, bytes);
            return pubBech32;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    public static byte[] decodeAddress(String address){
        byte[] dec = Bech32.decode(address).getData();
        return ConvertBits.convertBits(dec, 0, dec.length, 5, 8, false);
    }

    private static byte[] sha256Hash(byte[] input, int offset, int length) throws NoSuchAlgorithmException {
        byte[] result = new byte[32];
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        digest.update(input, offset, length);
        return digest.digest();
    }

    private static byte[] encode(int witnessVersion, byte[] witnessProgram) throws AddressFormatException {
        byte[] convertedProgram = ConvertBits.convertBits(witnessProgram, 0, witnessProgram.length, 8, 5, true);
        return convertedProgram;
    }




}
