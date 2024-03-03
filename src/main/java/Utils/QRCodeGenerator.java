package Utils;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.Writer;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import java.util.HashMap;
import java.util.Map;
public class QRCodeGenerator {
    public static BitMatrix generateQRCodeMatrix(String content) throws WriterException {
        // Création d'une carte (Map) pour stocker les indices de codage (encoding hints)
        Map<EncodeHintType, Object> hints = new HashMap<>();

        // Ajout de l'indice de correction d'erreur avec un niveau H (le niveau de correction le plus élevé)
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);

        // Ajout de l'indice de marge pour spécifier la marge autour du QR Code
        hints.put(EncodeHintType.MARGIN, 1);

        // Création d'un objet Writer pour écrire le QR Code
        Writer writer = new QRCodeWriter();

        // Encodage du contenu pour créer la matrice BitMatrix du QR Code
        // La fonction encode prend le contenu, le format du code-barres (QR_CODE),
        // la largeur et la hauteur du QR Code, et les indices de codage
        return writer.encode(content, BarcodeFormat.QR_CODE, 300, 300, hints);
    }
}