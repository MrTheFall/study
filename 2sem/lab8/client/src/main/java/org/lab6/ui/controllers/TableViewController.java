package org.lab6.ui.controllers;

import com.sun.javafx.charts.Legend;
import common.models.Color;
import common.models.Dragon;
import javafx.beans.property.*;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Pagination;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import org.lab6.ui.ChildUIType;
import org.lab6.ui.GUIConfig;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class TableViewController extends DataVisualizerController{
    private static final int ROWS_PER_PAGE = 16;
    private final List<Dragon> fullCollection = new ArrayList<>();
    private final List<Dragon> collectionToShow = new ArrayList<>();
    private final ObservableList<Dragon> observableDragonList = FXCollections.observableArrayList();
    private Dragon chosenDragon;

    private int currentPage = 0;

    @FXML
    private Pagination pagination = new Pagination();
    @FXML
    private TableView<Dragon> dragonsTable;
    private Predicate<Dragon> currentFiltrationPredicate = null;

    @FXML
    private TableColumn<Dragon, Integer> id;
    @FXML
    private TableColumn<Dragon, String> name;
    @FXML
    private TableColumn<Dragon, Double> x;
    @FXML
    private TableColumn<Dragon, Long> y;
    @FXML
    private TableColumn<Dragon, String> creationDate;
    @FXML
    private TableColumn<Dragon, Integer> age;
    @FXML
    private TableColumn<Dragon, Boolean> speaking;
    @FXML
    private TableColumn<Dragon, String> color;
    @FXML
    private TableColumn<Dragon, String> character;
    @FXML
    private TableColumn<Dragon, String> killerName;
    @FXML
    private TableColumn<Dragon, String> killerBirthday;
    @FXML
    private TableColumn<Dragon, Float> killerHeight;
    @FXML
    private TableColumn<Dragon, Long> killerWeight;
    @FXML
    private TableColumn<Dragon, Float> killerX;
    @FXML
    private TableColumn<Dragon, Float> killerY;
    @FXML
    private TableColumn<Dragon, Long> killerZ;
    @FXML
    private TextField idFilter, nameFilter, xFilter, yFilter, creationDateFilter, ageFilter, speakingFilter, colorFilter, characterFilter, killerNameFilter, killerBirthdateFilter, killerHeightFilter, killerWeightFilter, killerXFilter, killerYFilter, killerZFilter, authorFilter;
    @FXML
    private TableColumn<Dragon, String> author;

    @FXML
    private void initialize() {
        dragonsTable.setOnMousePressed(mouseEvent -> {
            if (mouseEvent.isPrimaryButtonDown() && mouseEvent.getClickCount() == 2) {
                chosenDragon = dragonsTable.getSelectionModel().getSelectedItem();

                if (chosenDragon != null)
                    getParentController().loadUI(GUIConfig.PROFILE_WINDOW_PATH, null, ChildUIType.DRAGON_PROFILE_CHILD_WINDOW);
            }
        });
        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        x.setCellValueFactory(dragon -> new SimpleDoubleProperty(dragon.getValue().getCoordinates().getX()).asObject());
        y.setCellValueFactory(dragon -> new SimpleLongProperty(dragon.getValue().getCoordinates().getY()).asObject());
        creationDate.setCellValueFactory(new PropertyValueFactory<>("creationDate"));
        age.setCellValueFactory(new PropertyValueFactory<>("age"));
        speaking.setCellValueFactory(new PropertyValueFactory<>("speaking"));
        color.setCellValueFactory(dragon -> new SimpleStringProperty(dragon.getValue().getColor().toString()));
        character.setCellValueFactory(dragon -> new SimpleStringProperty(dragon.getValue().getCharacter() != null
                ? dragon.getValue().getCharacter().toString()
                : "-"));
        killerName.setCellValueFactory(dragon -> new SimpleStringProperty(dragon.getValue().getKiller() != null
                ? dragon.getValue().getKiller().getName().toString()
                : "-"));
        killerBirthday.setCellValueFactory(dragon -> new SimpleStringProperty(dragon.getValue().getKiller() != null
                ? dragon.getValue().getKiller().getBirthday() != null
                ? dragon.getValue().getKiller().getBirthday().toString()
                : "-"
                : "-"));
        killerHeight.setCellValueFactory(dragon -> new SimpleObjectProperty(dragon.getValue().getKiller() != null
                ? dragon.getValue().getKiller().getHeight() != null
                ? dragon.getValue().getKiller().getHeight()
                : "-"
                : "-"));
        killerWeight.setCellValueFactory(dragon -> new SimpleObjectProperty(dragon.getValue().getKiller() != null
                ? dragon.getValue().getKiller().getWeight() != null
                ? dragon.getValue().getKiller().getWeight()
                : "-"
                : "-"));
        killerX.setCellValueFactory(dragon -> new SimpleObjectProperty(dragon.getValue().getKiller() != null
                ? dragon.getValue().getKiller().getLocation() != null
                ? dragon.getValue().getKiller().getLocation().getX()
                : "-"
                : "-"));
        killerY.setCellValueFactory(dragon -> new SimpleObjectProperty(dragon.getValue().getKiller() != null
                ? dragon.getValue().getKiller().getLocation() != null
                ? dragon.getValue().getKiller().getLocation().getY()
                : "-"
                : "-"));
        killerZ.setCellValueFactory(dragon -> new SimpleObjectProperty(dragon.getValue().getKiller() != null
                ? dragon.getValue().getKiller().getLocation() != null
                ? dragon.getValue().getKiller().getLocation().getZ()
                : "-"
                : "-"));
        author.setCellValueFactory(new PropertyValueFactory<>("ownerUsername"));
        dragonsTable.setItems(observableDragonList);
        pagination.currentPageIndexProperty().addListener((observable, oldValue, newValue) -> {
            currentPage = newValue.intValue();
            updateTable();
        });
        setUpFiltrationListeners();
    }

    private void setUpFiltrationListeners() {
        idFilter.textProperty().addListener(idFieldChangeListener);
        nameFilter.textProperty().addListener(nameFieldChangeListener);
        xFilter.textProperty().addListener(xFieldChangeListener);
        yFilter.textProperty().addListener(yFieldChangeListener);
        creationDateFilter.textProperty().addListener(creationDateFieldChangeListener);
        ageFilter.textProperty().addListener(ageFieldChangeListener);
        speakingFilter.textProperty().addListener(speakingFieldChangeListener);
        colorFilter.textProperty().addListener(colorFieldChangeListener);
        characterFilter.textProperty().addListener(characterFieldChangeListener);
        killerNameFilter.textProperty().addListener(killerNameFieldChangeListener);
        killerBirthdateFilter.textProperty().addListener(killerBirthdayFieldChangeListener);
        killerHeightFilter.textProperty().addListener(killerHeightFieldChangeListener);
        killerWeightFilter.textProperty().addListener(killerWeightFieldChangeListener);
        killerXFilter.textProperty().addListener(killerXFieldChangeListener);
        killerYFilter.textProperty().addListener(killerYFieldChangeListener);
        killerZFilter.textProperty().addListener(killerZFieldChangeListener);
        authorFilter.textProperty().addListener(authorFieldChangeListener);
    }

    private final ChangeListener<String> idFieldChangeListener = (observableValue, oldValue, newValue) -> {
        if (newValue.isEmpty()) {
            currentFiltrationPredicate = null;
            collectionToShow.clear();
            collectionToShow.addAll(fullCollection);
        } else {
            collectionToShow.clear();
            Predicate<Dragon> filtrationPredicate = dragon -> String.valueOf(dragon.getId()).startsWith(newValue.toLowerCase());
            collectionToShow.addAll(fullCollection.stream().filter(filtrationPredicate).toList());
            currentFiltrationPredicate = filtrationPredicate;
        }
        currentPage = 0;
        pagination.setPageCount((int) Math.ceil(collectionToShow.size() / (double) ROWS_PER_PAGE));
        updateTable();
    };
    private final ChangeListener<String> nameFieldChangeListener = (observableValue, oldValue, newValue) -> {
        if (newValue.isEmpty()) {
            currentFiltrationPredicate = null;
            collectionToShow.clear();
            collectionToShow.addAll(fullCollection);
        } else {
            collectionToShow.clear();
            Predicate<Dragon> filtrationPredicate = dragon -> dragon.getName().toLowerCase().contains(newValue.toLowerCase());
            collectionToShow.addAll(fullCollection.stream().filter(filtrationPredicate).toList());
            currentFiltrationPredicate = filtrationPredicate;
        }
        currentPage = 0;
        pagination.setPageCount((int) Math.ceil(collectionToShow.size() / (double) ROWS_PER_PAGE));
        updateTable();
    };
    private final ChangeListener<String> xFieldChangeListener = (observableValue, oldValue, newValue) -> {
        if (newValue.isEmpty()) {
            currentFiltrationPredicate = null;
            collectionToShow.clear();
            collectionToShow.addAll(fullCollection);
        } else {
            collectionToShow.clear();
            Predicate<Dragon> filtrationPredicate = dragon -> String.valueOf(dragon.getCoordinates().getX()).startsWith(newValue.toLowerCase());
            collectionToShow.addAll(fullCollection.stream().filter(filtrationPredicate).toList());
            currentFiltrationPredicate = filtrationPredicate;
        }
        currentPage = 0;
        pagination.setPageCount((int) Math.ceil(collectionToShow.size() / (double) ROWS_PER_PAGE));
        updateTable();
    };
    private final ChangeListener<String> yFieldChangeListener = (observableValue, oldValue, newValue) -> {
        if (newValue.isEmpty()) {
            currentFiltrationPredicate = null;
            collectionToShow.clear();
            collectionToShow.addAll(fullCollection);
        } else {
            collectionToShow.clear();
            Predicate<Dragon> filtrationPredicate = dragon -> String.valueOf(dragon.getCoordinates().getY()).startsWith(newValue.toLowerCase());
            collectionToShow.addAll(fullCollection.stream().filter(filtrationPredicate).toList());
            currentFiltrationPredicate = filtrationPredicate;
        }
        currentPage = 0;
        pagination.setPageCount((int) Math.ceil(collectionToShow.size() / (double) ROWS_PER_PAGE));
        updateTable();
    };
    private final ChangeListener<String> creationDateFieldChangeListener = (observableValue, oldValue, newValue) -> {
        if (newValue.isEmpty()) {
            currentFiltrationPredicate = null;
            collectionToShow.clear();
            collectionToShow.addAll(fullCollection);
        } else {
            collectionToShow.clear();
            Predicate<Dragon> filtrationPredicate = dragon -> String.valueOf(dragon.getCreationDate()).contains(newValue.toLowerCase());
            collectionToShow.addAll(fullCollection.stream().filter(filtrationPredicate).toList());
            currentFiltrationPredicate = filtrationPredicate;
        }
        currentPage = 0;
        pagination.setPageCount((int) Math.ceil(collectionToShow.size() / (double) ROWS_PER_PAGE));
        updateTable();
    };
    private final ChangeListener<String> ageFieldChangeListener = (observableValue, oldValue, newValue) -> {
        if (newValue.isEmpty()) {
            currentFiltrationPredicate = null;
            collectionToShow.clear();
            collectionToShow.addAll(fullCollection);
        } else {
            collectionToShow.clear();
            Predicate<Dragon> filtrationPredicate = dragon -> String.valueOf(dragon.getAge()).startsWith(newValue.toLowerCase());
            collectionToShow.addAll(fullCollection.stream().filter(filtrationPredicate).toList());
            currentFiltrationPredicate = filtrationPredicate;
        }
        currentPage = 0;
        pagination.setPageCount((int) Math.ceil(collectionToShow.size() / (double) ROWS_PER_PAGE));
        updateTable();
    };
    private final ChangeListener<String> speakingFieldChangeListener = (observableValue, oldValue, newValue) -> {
        if (newValue.isEmpty()) {
            currentFiltrationPredicate = null;
            collectionToShow.clear();
            collectionToShow.addAll(fullCollection);
        } else {
            collectionToShow.clear();
            Predicate<Dragon> filtrationPredicate = dragon -> String.valueOf(dragon.getSpeaking()).toLowerCase().startsWith(newValue.toLowerCase());
            collectionToShow.addAll(fullCollection.stream().filter(filtrationPredicate).toList());
            currentFiltrationPredicate = filtrationPredicate;
        }
        currentPage = 0;
        pagination.setPageCount((int) Math.ceil(collectionToShow.size() / (double) ROWS_PER_PAGE));
        updateTable();
    };
    private final ChangeListener<String> colorFieldChangeListener = (observableValue, oldValue, newValue) -> {
        if (newValue.isEmpty()) {
            currentFiltrationPredicate = null;
            collectionToShow.clear();
            collectionToShow.addAll(fullCollection);
        } else {
            collectionToShow.clear();
            Predicate<Dragon> filtrationPredicate = dragon -> String.valueOf(dragon.getColor()).toLowerCase().contains(newValue.toLowerCase());
            collectionToShow.addAll(fullCollection.stream().filter(filtrationPredicate).toList());
            currentFiltrationPredicate = filtrationPredicate;
        }
        currentPage = 0;
        pagination.setPageCount((int) Math.ceil(collectionToShow.size() / (double) ROWS_PER_PAGE));
        updateTable();
    };
    private final ChangeListener<String> characterFieldChangeListener = (observableValue, oldValue, newValue) -> {
        if (newValue.isEmpty()) {
            currentFiltrationPredicate = null;
            collectionToShow.clear();
            collectionToShow.addAll(fullCollection);
        } else {
            collectionToShow.clear();
            Predicate<Dragon> filtrationPredicate = dragon -> (dragon.getCharacter() == null ? "-" : String.valueOf(dragon.getCharacter())).toLowerCase().contains(newValue.toLowerCase());
            collectionToShow.addAll(fullCollection.stream().filter(filtrationPredicate).toList());
            currentFiltrationPredicate = filtrationPredicate;
        }
        currentPage = 0;
        pagination.setPageCount((int) Math.ceil(collectionToShow.size() / (double) ROWS_PER_PAGE));
        updateTable();
    };
    private final ChangeListener<String> killerNameFieldChangeListener = (observableValue, oldValue, newValue) -> {
        if (newValue.isEmpty()) {
            currentFiltrationPredicate = null;
            collectionToShow.clear();
            collectionToShow.addAll(fullCollection);
        } else {
            collectionToShow.clear();
            Predicate<Dragon> filtrationPredicate = dragon -> (dragon.getKiller() == null ? "-" : dragon.getKiller().getName()).toLowerCase().contains(newValue.toLowerCase());
            collectionToShow.addAll(fullCollection.stream().filter(filtrationPredicate).toList());
            currentFiltrationPredicate = filtrationPredicate;
        }
        currentPage = 0;
        pagination.setPageCount((int) Math.ceil(collectionToShow.size() / (double) ROWS_PER_PAGE));
        updateTable();
    };
    private final ChangeListener<String> killerBirthdayFieldChangeListener = (observableValue, oldValue, newValue) -> {
        if (newValue.isEmpty()) {
            currentFiltrationPredicate = null;
            collectionToShow.clear();
            collectionToShow.addAll(fullCollection);
        } else {
            collectionToShow.clear();
            Predicate<Dragon> filtrationPredicate = dragon -> (dragon.getKiller() == null ? "-" : (dragon.getKiller().getBirthday() == null ? "-" : dragon.getKiller().getBirthday().toString())).toLowerCase().contains(newValue.toLowerCase());
            collectionToShow.addAll(fullCollection.stream().filter(filtrationPredicate).toList());
            currentFiltrationPredicate = filtrationPredicate;
        }
        currentPage = 0;
        pagination.setPageCount((int) Math.ceil(collectionToShow.size() / (double) ROWS_PER_PAGE));
        updateTable();
    };
    private final ChangeListener<String> killerHeightFieldChangeListener = (observableValue, oldValue, newValue) -> {
        if (newValue.isEmpty()) {
            currentFiltrationPredicate = null;
            collectionToShow.clear();
            collectionToShow.addAll(fullCollection);
        } else {
            collectionToShow.clear();
            Predicate<Dragon> filtrationPredicate = dragon -> (dragon.getKiller() == null ? "-" : (dragon.getKiller().getHeight() == null ? "-" : dragon.getKiller().getHeight().toString())).toLowerCase().startsWith(newValue.toLowerCase());
            collectionToShow.addAll(fullCollection.stream().filter(filtrationPredicate).toList());
            currentFiltrationPredicate = filtrationPredicate;
        }
        currentPage = 0;
        pagination.setPageCount((int) Math.ceil(collectionToShow.size() / (double) ROWS_PER_PAGE));
        updateTable();
    };
    private final ChangeListener<String> killerWeightFieldChangeListener = (observableValue, oldValue, newValue) -> {
        if (newValue.isEmpty()) {
            currentFiltrationPredicate = null;
            collectionToShow.clear();
            collectionToShow.addAll(fullCollection);
        } else {
            collectionToShow.clear();
            Predicate<Dragon> filtrationPredicate = dragon -> (dragon.getKiller() == null ? "-" : (dragon.getKiller().getWeight() == null ? "-" : dragon.getKiller().getWeight().toString())).toLowerCase().startsWith(newValue.toLowerCase());
            collectionToShow.addAll(fullCollection.stream().filter(filtrationPredicate).toList());
            currentFiltrationPredicate = filtrationPredicate;
        }
        currentPage = 0;
        pagination.setPageCount((int) Math.ceil(collectionToShow.size() / (double) ROWS_PER_PAGE));
        updateTable();
    };
    private final ChangeListener<String> killerXFieldChangeListener = (observableValue, oldValue, newValue) -> {
        if (newValue.isEmpty()) {
            currentFiltrationPredicate = null;
            collectionToShow.clear();
            collectionToShow.addAll(fullCollection);
        } else {
            collectionToShow.clear();
            Predicate<Dragon> filtrationPredicate = dragon -> (dragon.getKiller() == null ? "-" : (dragon.getKiller().getLocation().getX() == null ? "-" : dragon.getKiller().getLocation().getX().toString())).toLowerCase().startsWith(newValue.toLowerCase());
            collectionToShow.addAll(fullCollection.stream().filter(filtrationPredicate).toList());
            currentFiltrationPredicate = filtrationPredicate;
        }
        currentPage = 0;
        pagination.setPageCount((int) Math.ceil(collectionToShow.size() / (double) ROWS_PER_PAGE));
        updateTable();
    };
    private final ChangeListener<String> killerYFieldChangeListener = (observableValue, oldValue, newValue) -> {
        if (newValue.isEmpty()) {
            currentFiltrationPredicate = null;
            collectionToShow.clear();
            collectionToShow.addAll(fullCollection);
        } else {
            collectionToShow.clear();
            Predicate<Dragon> filtrationPredicate = dragon -> (dragon.getKiller() == null ? "-" : String.valueOf(dragon.getKiller().getLocation().getY())).toLowerCase().startsWith(newValue.toLowerCase());
            collectionToShow.addAll(fullCollection.stream().filter(filtrationPredicate).toList());
            currentFiltrationPredicate = filtrationPredicate;
        }
        currentPage = 0;
        pagination.setPageCount((int) Math.ceil(collectionToShow.size() / (double) ROWS_PER_PAGE));
        updateTable();
    };
    private final ChangeListener<String> killerZFieldChangeListener = (observableValue, oldValue, newValue) -> {
        if (newValue.isEmpty()) {
            currentFiltrationPredicate = null;
            collectionToShow.clear();
            collectionToShow.addAll(fullCollection);
        } else {
            collectionToShow.clear();
            Predicate<Dragon> filtrationPredicate = dragon -> (dragon.getKiller() == null ? "-" : (dragon.getKiller().getLocation().getZ() == null ? "-" : dragon.getKiller().getLocation().getZ().toString())).toLowerCase().startsWith(newValue.toLowerCase());
            collectionToShow.addAll(fullCollection.stream().filter(filtrationPredicate).toList());
            currentFiltrationPredicate = filtrationPredicate;
        }
        currentPage = 0;
        pagination.setPageCount((int) Math.ceil(collectionToShow.size() / (double) ROWS_PER_PAGE));
        updateTable();
    };

    @Override
    public void updateInfo(List<Dragon> elementsToRemove, List<Dragon> elementsToAdd, List<Dragon> elementsToUpdate) {
        fullCollection.removeAll(elementsToRemove);
        fullCollection.addAll(elementsToAdd);
        List<Integer> updatedIds = elementsToUpdate.stream().map(Dragon::getId).toList();
        fullCollection.removeIf(dragon -> updatedIds.contains(dragon.getId()));
        fullCollection.addAll(elementsToUpdate);
        fullCollection.sort(Comparator.comparing(Dragon::getId));
        collectionToShow.clear();
        if (currentFiltrationPredicate != null) {
            collectionToShow.addAll(fullCollection.stream().filter(currentFiltrationPredicate).toList());
        } else {
            collectionToShow.addAll(fullCollection);
        }
        pagination.setPageCount((int) Math.ceil(collectionToShow.size() / (double) ROWS_PER_PAGE));
        updateTable();
    }

    private final ChangeListener<String> authorFieldChangeListener = (observableValue, oldValue, newValue) -> {
        if (newValue.isEmpty()) {
            currentFiltrationPredicate = null;
            collectionToShow.clear();
            collectionToShow.addAll(fullCollection);
        } else {
            collectionToShow.clear();
            Predicate<Dragon> filtrationPredicate = dragon -> dragon.getOwnerUsername().toLowerCase().contains(newValue.toLowerCase());
            collectionToShow.addAll(fullCollection.stream().filter(filtrationPredicate).toList());
            currentFiltrationPredicate = filtrationPredicate;
        }
        currentPage = 0;
        pagination.setPageCount((int) Math.ceil(collectionToShow.size() / (double) ROWS_PER_PAGE));
        updateTable();
    };

    @Override
    public void setInfo(List<Dragon> elementsToSet) {
        fullCollection.clear();
        fullCollection.addAll(elementsToSet);
        fullCollection.sort(Comparator.comparing(Dragon::getId));
        pagination.setPageCount((int) Math.ceil(fullCollection.size() / (double) ROWS_PER_PAGE));
        collectionToShow.addAll(fullCollection);
        updateTable();
    }

    @Override
    public Dragon getChosenDragon() {
        return chosenDragon;
    }

    @Override
    public void setChosenDragon(Dragon dragon) {
        this.chosenDragon = dragon;

    }

    private void updateTable() {
        TableColumn<Dragon, ?> sort = null;
        if (dragonsTable.getSortOrder().size() > 0) {
            sort = dragonsTable.getSortOrder().get(0);
        }
        int from = currentPage * ROWS_PER_PAGE;
        int to = from + ROWS_PER_PAGE;
        List<Dragon> subList = new ArrayList<>();
        for (int i = 0; i < collectionToShow.size(); i++) {
            if (i >= from && i < to) {
                subList.add(collectionToShow.get(i));
            }
        }
        observableDragonList.clear();
        observableDragonList.addAll(subList);
        if (sort != null) {
            dragonsTable.getSortOrder().add(sort);
        }
    }

}
