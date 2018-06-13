package app.view.simpleMediaPlayer;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.InputEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;
import javafx.util.Duration;


/**
 * create by Intellij IDEA
 * Author: Al-assad
 * E-mail: yulinying@1994.com
 * Github: https:
 * Date: 2017/1/20 14:22
 * Description:
 */
public class PlayerController {

    private static final boolean repeat = false;
    @FXML
    Button playBT;
    @FXML
    Button stopBT;
    @FXML
    Button maxBT;
    @FXML
    Button volumeBT;
    @FXML
    Label timeLB;
    @FXML
    Slider processSD;
    @FXML
    Slider volumeSD;
    @FXML
    MediaView mediaView;
    @FXML
    VBox controlBar;
    @FXML
    BorderPane mediaPane;
    @FXML
    AnchorPane anchorPane;
    private String playIcon = getClass().getResource("icon/play.png").toString();
    private String pauseIcon = getClass().getResource("icon/pause.png").toString();
    private String stopIcon = getClass().getResource("icon/stop.png").toString();
    private String volOffIcon = getClass().getResource("icon/volume_off.png").toString();
    private String volOnIcon = getClass().getResource("icon/volume_On.png").toString();
    private String maxIcon = getClass().getResource("icon/max.png").toString();
    private MediaPlayer mediaPlayer;
    private Media media;
    private String url;
    private boolean popup;
    private Scene scene;
    private boolean atEndOfMedia = false;
    private double volumeValue;
    private Duration duration;
    private int mediaHeight;
    private int mediaWidth;

    private int currentHeight;
    private int currentWidth;

    public void setScene(Scene scene) {
        this.scene = scene;
    }


    public void initialize() {

        setIcon(playBT, playIcon, 25);
        setIcon(stopBT, stopIcon, 25);
        setIcon(volumeBT, volOnIcon, 15);
        setIcon(maxBT, maxIcon, 25);

    }

    public void start(String url, boolean popup, int width, int height) {
        this.url = url;
        this.popup = popup;


        media = new Media(url);
        mediaPlayer = new MediaPlayer(media);
        mediaView.setMediaPlayer(mediaPlayer);


        setMediaPlayer(width, height);


        setMediaViewOnClick();
        setPlayButton();
        setStopButton();
        setVolumeButton();
        setVolumeSD();
        setProcessSlider();
        setMaximizeButton();

    }


    void setMediaPlayer(int width, int height) {
        mediaPlayer.setCycleCount(repeat ? MediaPlayer.INDEFINITE : 1);

        mediaPlayer.setOnReady(new Runnable() {
            @Override
            public void run() {
                duration = mediaPlayer.getMedia().getDuration();
                volumeValue = mediaPlayer.getVolume();

                mediaHeight = media.getHeight();
                mediaWidth = media.getWidth();


                setSize(width, height);


                if (scene != null) {
                    scene.widthProperty().addListener(new ChangeListener<Number>() {
                        @Override
                        public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                            setSize(newValue.intValue(), currentHeight);
                        }
                    });
                    scene.heightProperty().addListener(new ChangeListener<Number>() {
                        @Override
                        public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                            setSize(currentWidth, newValue.intValue());
                        }
                    });
                }

                EventHandler onScreen = new EventHandler<InputEvent>() {
                    @Override
                    public void handle(InputEvent event) {
                        controlBar.setVisible(true);
                    }
                };
                EventHandler offScreen = new EventHandler<InputEvent>() {
                    @Override
                    public void handle(InputEvent event) {
                        controlBar.setVisible(false);
                    }
                };
                if (scene != null && popup) {
                    ((Stage) scene.getWindow()).fullScreenProperty().addListener(new ChangeListener<Boolean>() {
                        @Override
                        public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                            if (newValue.booleanValue()) {
                                controlBar.setVisible(false);
                                mediaPane.addEventHandler(MouseEvent.MOUSE_CLICKED, onScreen);
                                controlBar.addEventHandler(MouseEvent.MOUSE_EXITED, offScreen);
                            } else {
                                controlBar.setVisible(true);
                                mediaPane.removeEventHandler(MouseEvent.MOUSE_CLICKED, onScreen);
                                controlBar.removeEventHandler(MouseEvent.MOUSE_EXITED, offScreen);
                            }
                        }
                    });
                }

                updateValues();


            }
        });

        mediaPlayer.currentTimeProperty().addListener(new ChangeListener<Duration>() {
            @Override
            public void changed(ObservableValue<? extends Duration> observable, Duration oldValue, Duration newValue) {
                updateValues();
            }
        });
    }

    private void setMediaViewOnClick() {
        mediaView.setOnMouseClicked(event -> {
            if (media == null)
                return;
            MediaPlayer.Status status = mediaPlayer.getStatus();
            if (status == MediaPlayer.Status.UNKNOWN || status == MediaPlayer.Status.HALTED) {
                return;
            }

            if (status == MediaPlayer.Status.PAUSED || status == MediaPlayer.Status.READY || status == MediaPlayer.Status.STOPPED) {

                if (atEndOfMedia) {
                    mediaPlayer.seek(mediaPlayer.getStartTime());
                    atEndOfMedia = false;
                }
                mediaPlayer.play();
                setIcon(playBT, pauseIcon, 25);
            } else {
                mediaPlayer.pause();
                setIcon(playBT, playIcon, 25);
            }
        });
    }


    private void setPlayButton() {
        playBT.setOnAction((ActionEvent e) -> {
            if (media == null)
                return;
            MediaPlayer.Status status = mediaPlayer.getStatus();
            if (status == MediaPlayer.Status.UNKNOWN || status == MediaPlayer.Status.HALTED) {
                return;
            }

            if (status == MediaPlayer.Status.PAUSED || status == MediaPlayer.Status.READY || status == MediaPlayer.Status.STOPPED) {

                if (atEndOfMedia) {
                    mediaPlayer.seek(mediaPlayer.getStartTime());
                    atEndOfMedia = false;
                }
                mediaPlayer.play();
                setIcon(playBT, pauseIcon, 25);
            } else {
                mediaPlayer.pause();
                setIcon(playBT, playIcon, 25);
            }
        });
    }


    private void setStopButton() {
        stopBT.setOnAction((ActionEvent e) -> {
            if (media == null)
                return;
            mediaPlayer.stop();
            setIcon(playBT, playIcon, 25);
        });
    }


    private void setProcessSlider() {
        processSD.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                if (processSD.isValueChanging()) {
                    mediaPlayer.seek(duration.multiply(processSD.getValue() / 100.0));
                }
            }
        });
    }


    public void setMaximizeButton() {
        maxBT.setOnAction((ActionEvent e) -> {
            if (popup) {
                ((Stage) scene.getWindow()).setFullScreen(true);
            } else {
                mediaPlayer.pause();
                setIcon(playBT, pauseIcon, 25);
                SimpleMediaPlayer player = SimpleMediaPlayer.popup(url);
                player.getController().getMediaPlayer().seek(this.mediaPlayer.getCurrentTime());

            }
        });
    }


    private void setVolumeButton() {
        volumeBT.setOnAction((ActionEvent e) -> {
            if (media == null)
                return;

            if (mediaPlayer.getVolume() > 0) {
                volumeValue = mediaPlayer.getVolume();
                volumeSD.setValue(0);
                setIcon(volumeBT, volOffIcon, 25);
            } else {
                mediaPlayer.setVolume(volumeValue);
                volumeSD.setValue(volumeValue * 100);
                setIcon(volumeBT, volOnIcon, 15);
            }
        });
    }


    private void setVolumeSD() {
        volumeSD.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                mediaPlayer.setVolume(newValue.doubleValue() / 100);
            }
        });
    }


    protected void updateValues() {
        if (processSD != null && timeLB != null && volumeSD != null && volumeBT != null) {
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    Duration currentTime = mediaPlayer.getCurrentTime();
                    timeLB.setText(formatTime(currentTime, duration));
                    processSD.setDisable(duration.isUnknown());
                    if (!processSD.isDisabled() && duration.greaterThan(Duration.ZERO) && !processSD.isValueChanging()) {
                        processSD.setValue(currentTime.toMillis() / duration.toMillis() * 100);
                    }
                    if (!volumeSD.isValueChanging()) {
                        volumeSD.setValue((int) Math.round(mediaPlayer.getVolume() * 100));
                        if (mediaPlayer.getVolume() == 0) {
                            setIcon(volumeBT, volOffIcon, 20);
                        } else {
                            setIcon(volumeBT, volOnIcon, 20);
                        }
                    }
                }
            });
        }
    }


    protected String formatTime(Duration elapsed, Duration duration) {

        int intElapsed = (int) Math.floor(elapsed.toSeconds());
        int elapsedHours = intElapsed / (60 * 60);
        int elapsedMinutes = (intElapsed - elapsedHours * 60 * 60) / 60;
        int elapsedSeconds = intElapsed - elapsedHours * 60 * 60 - elapsedMinutes * 60;
        if (duration.greaterThan(Duration.ZERO)) {
            int intDuration = (int) Math.floor(duration.toSeconds());
            int durationHours = intDuration / (60 * 60);
            int durationMinutes = (intDuration - durationHours * 60 * 60) / 60;
            int durationSeconds = intDuration - durationHours * 60 * 60 - durationMinutes * 60;

            if (durationHours > 0) {
                return String.format("%02d:%02d:%02d / %02d:%02d:%02d", elapsedHours, elapsedMinutes, elapsedSeconds, durationHours, durationMinutes, durationSeconds);
            } else {
                return String.format("%02d:%02d / %02d:%02d", elapsedMinutes, elapsedSeconds, durationMinutes, durationSeconds);
            }
        } else {
                return String.format("");
        }
    }


    private void setIcon(Button button, String path, int size) {
        Image icon = new Image(path);
        ImageView imageView = new ImageView(icon);
        imageView.setFitWidth(size);
        imageView.setFitHeight((int) (size * icon.getHeight() / icon.getWidth()));
        button.setGraphic(imageView);

        ColorAdjust colorAdjust = new ColorAdjust();
        button.setOnMousePressed(event -> {
            colorAdjust.setBrightness(0.5);
            button.setEffect(colorAdjust);
        });
        button.setOnMouseReleased(event -> {
            colorAdjust.setBrightness(0);
            button.setEffect(colorAdjust);
        });
    }

    public MediaPlayer getMediaPlayer() {
        return this.mediaPlayer;
    }


    public void destroy() {
        if (mediaPlayer.getStatus() == MediaPlayer.Status.PLAYING) {
            mediaPlayer.stop();
        }
        mediaPlayer.dispose();
        media = null;
        mediaPlayer = null;
    }


    public void setSize(int width, int height) {
        currentWidth = width;
        currentHeight = height;
        setUISuitable();

    }

    private void setUISuitable() {
        anchorPane.setPrefSize(currentWidth, currentHeight);
        anchorPane.setBottomAnchor(controlBar, 0.0);
        anchorPane.setTopAnchor(mediaPane, ((double) currentHeight - (double) currentWidth * (double) mediaHeight / (double) mediaWidth - 50) / 2);
        mediaView.setFitWidth(currentWidth);
        mediaView.setFitHeight((double) currentWidth * (double) mediaHeight / (double) mediaHeight);
        controlBar.setPrefWidth(currentWidth);


    }

    public boolean getPopup() {
        return this.popup;
    }


}
