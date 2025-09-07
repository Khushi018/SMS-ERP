package com.aristiec.schoolmanagementsystem.modal.onlineexam;

import ai.djl.Application;
import ai.djl.Model;
import ai.djl.ModelException;
import ai.djl.inference.Predictor;
import ai.djl.modality.cv.Image;
import ai.djl.modality.cv.ImageFactory;
import ai.djl.modality.cv.output.DetectedObjects;
import ai.djl.repository.zoo.Criteria;
import ai.djl.repository.zoo.ModelZoo;
import ai.djl.translate.TranslateException;

import java.io.IOException;
import java.nio.file.Path;

public class FaceDetectionService {

    public DetectedObjects detectFaces(Path imagePath) throws IOException, ModelException, TranslateException {
        var translator = ai.djl.modality.cv.translator.SingleShotDetectionTranslator.builder().build();
        
        Criteria<Image, DetectedObjects> criteria = Criteria.builder()
                .optApplication(Application.CV.OBJECT_DETECTION)
                .setTypes(Image.class, DetectedObjects.class)
                .optFilter("backbone", "mobilenet0.25")
                .optEngine("PyTorch") // or "TensorFlow"
                .optTranslator(translator)
                .build();

        try (Model model = ModelZoo.loadModel(criteria);
             Predictor<Image, DetectedObjects> predictor = model.newPredictor(translator)) {
            Image img = ImageFactory.getInstance().fromFile(imagePath);
            DetectedObjects results = predictor.predict(img);
            return results;
        }
    }

    
}

