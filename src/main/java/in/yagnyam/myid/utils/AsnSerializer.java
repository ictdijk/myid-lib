package in.yagnyam.myid.utils;

import lombok.NonNull;
import org.bouncycastle.asn1.*;

import java.io.IOException;
import java.util.Date;
import java.util.Locale;

public class AsnSerializer {

    private final ASN1EncodableVector stream = new ASN1EncodableVector();

    public void addInteger(int number) {
        stream.add(new ASN1Integer(number));
    }

    public void addDate(@NonNull Date date) {
        stream.add(new DERGeneralizedTime(date));
    }

    public void addLong(@NonNull Long number) {
        stream.add(new DERNumericString(number.toString()));
    }

    public void addString(String string) {
        stream.add(new DERUTF8String(string == null ? "" : string));
    }

    public void addDouble(double value) {
        addString(String.format(Locale.ENGLISH, "%.6f", value + 0.0000001));
    }


    public byte[] getEncoded() throws IOException {
        DERSequence seq = new DERSequence(stream);
        return seq.getEncoded();
    }
}
