package tests;

import com.google.cloud.vision.v1.*;
import com.google.protobuf.ByteString;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class DiplomaOCR {
    public static void main(String[] args) throws Exception {
        // Path to your diploma image file
        String imagePath = "C:\\Users\\user\\IdeaProjects\\gestionDemandes\\src\\main\\java\\imageslogo.png";

        // Instantiates a client
        try (ImageAnnotatorClient vision = ImageAnnotatorClient.create()) {
            // Reads the image file into memory
            Path path = Paths.get(imagePath);
            byte[] data = Files.readAllBytes(path);
            ByteString imgBytes = ByteString.copyFrom(data);

            // Builds the image annotation request
            Image img = Image.newBuilder().setContent(imgBytes).build();
            Feature feat = Feature.newBuilder().setType(Feature.Type.TEXT_DETECTION).build();
            AnnotateImageRequest request = AnnotateImageRequest.newBuilder()
                    .addFeatures(feat)
                    .setImage(img)
                    .build();

            // Performs text detection on the image
            BatchAnnotateImagesResponse response = vision.batchAnnotateImages(java.util.Collections.singletonList(request));

            // Extracts OCR text from the response
            AnnotateImageResponse res = response.getResponses(0);
            if (res.hasError()) {
                System.err.println("Error: " + res.getError().getMessage());
            } else {
                // Check if the recognized text contains "ingénieur"
                boolean containsIngenieur = false;
                for (EntityAnnotation annotation : res.getTextAnnotationsList()) {
                    String text = annotation.getDescription();
                    if (text.toLowerCase().contains("ingénieur")) {
                        containsIngenieur = true;
                        break;
                    }
                }

                if (containsIngenieur) {
                    System.out.println("The diploma mentions 'ingénieur'.");
                } else {
                    System.out.println("The diploma does not mention 'ingénieur'.");
                }
            }
        }
    }
}
