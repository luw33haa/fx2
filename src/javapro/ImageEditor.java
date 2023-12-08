package javapro;

import javafx.application.Application;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImageEditor extends Application {

    private ImageView imageView;
    private TextArea textArea;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        primaryStage.setTitle("Image Editor");

        // Создание контейнера BorderPane
        BorderPane borderPane = new BorderPane();

        // Панель с кнопками
        HBox buttonPane = new HBox();
        buttonPane.setPadding(new Insets(10));
        buttonPane.setSpacing(10);

        // Кнопка загрузки изображения
        Button loadButton = new Button("Загрузить изображение");
        loadButton.setOnAction(event -> loadImage(primaryStage));
        buttonPane.getChildren().add(loadButton);

        // Кнопка сохранения изображения
        Button saveButton = new Button("Сохранить изображение");
        saveButton.setOnAction(event -> saveImage(primaryStage));
        buttonPane.getChildren().add(saveButton);

        // Кнопка обработки изображения
        Button processButton = new Button("Обработать изображение");
        processButton.setOnAction(event -> processImage());
        buttonPane.getChildren().add(processButton);
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("FXMLDocument.fxml"));
        Parent root = fxmlLoader.load();
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setOpacity(1);
        stage.setTitle("My New Stage Title");
        stage.setScene(new Scene(root, 450, 450));
        stage.showAndWait();


        // Добавление панели с кнопками в верхнюю часть BorderPane
        borderPane.setTop(buttonPane);

        // Панель с изображением
        VBox imagePane = new VBox();
        imagePane.setPadding(new Insets(10));
        imagePane.setSpacing(10);

        // Изображение
        imageView = new ImageView();
        imagePane.getChildren().add(imageView);

        // Текстовое поле для вывода информации
        textArea = new TextArea();
        textArea.setEditable(false);
        imagePane.getChildren().add(textArea);

        // Добавление панели с изображением в центр BorderPane
        borderPane.setCenter(imagePane);

        // Создание главной сцены
        Scene scene = new Scene(borderPane, 800, 600);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    // Метод загрузки изображения
    private void loadImage(Stage primaryStage) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Изображения", "*.png", "*.jpg", "*.jpeg"));

        File selectedFile = fileChooser.showOpenDialog(primaryStage);
        if (selectedFile != null) {
            try {
                BufferedImage bufferedImage = ImageIO.read(selectedFile);
                Image image = SwingFXUtils.toFXImage(bufferedImage, null);
                imageView.setImage(image);
                textArea.clear();
                textArea.appendText("Изображение загружено: " + selectedFile.getName());
            } catch (IOException e) {
                textArea.clear();
                textArea.appendText("Ошибка при загрузке изображения: " + e.getMessage());
            }
        }
    }

    // Метод сохранения изображения
    private void saveImage(Stage primaryStage) {
        if (imageView.getImage() != null) {
            FileChooser fileChooser = new FileChooser();
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Изображения", "*.png", "*.jpg", "*.jpeg"));

            File selectedFile = fileChooser.showSaveDialog(primaryStage);
            if (selectedFile != null) {
                try {
                    BufferedImage bufferedImage = SwingFXUtils.fromFXImage(imageView.getImage(), null);
                    ImageIO.write(bufferedImage, "png", selectedFile);
                    textArea.clear();
                    textArea.appendText("Изображение сохранено: " + selectedFile.getName());
                } catch (IOException e) {
                    textArea.clear();
                    textArea.appendText("Ошибка при сохранении изображения: " + e.getMessage());
                }
            }
        } else {
            textArea.appendText("Нет изображения для сохранения");
        }
    }

    // Метод обработки изображения
    private void processImage() {
        if (imageView.getImage() != null) {
            // Здесь можно добавить код для обработки изображения
            textArea.appendText("\nИзображение обработано");
        } else {
            textArea.appendText("\nНет изображения для обработки");
        }
    }
}