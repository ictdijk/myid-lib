package in.yagnyam.myid.utils;

import lombok.NonNull;
import org.bouncycastle.asn1.*;

import java.io.IOException;
import java.util.Date;
import java.util.Locale;

/**
 * Helper class to serialize using ASN
 */
public class AsnSerializer {

    private final ASN1EncodableVector stream = new ASN1EncodableVector();

    /**
     * Write Integer to ASN stream
     * @param number Number to write to Stream
     */
    public void addInteger(int number) {
        stream.add(new ASN1Integer(number));
    }

    /**
     * Write Date to ASN stream
     * @param date Date to write to Stream
     */
    public void addDate(@NonNull Date date) {
        stream.add(new DERGeneralizedTime(date));
    }

    /**
     * Write long to ASN stream (As Numeric String)
     * @param number Big number to write to Stream
     */
    public void addLong(@NonNull Long number) {
        stream.add(new DERNumericString(number.toString()));
    }

    /**
     * Write String to ASN Stream
     * @param string String to write to Stream
     */
    public void addString(String string) {
        stream.add(new DERUTF8String(string == null ? "" : string));
    }

    /**
     * Write Double to ASN Stream as String
     * @param value Double to write to stream
     */
    public void addDouble(double value) {
        addString(String.format(Locale.ENGLISH, "%.6f", value + 0.0000001));
    }


    public byte[] getEncoded() throws IOException {
        DERSequence seq = new DERSequence(stream);
        return seq.getEncoded();
    }
}
