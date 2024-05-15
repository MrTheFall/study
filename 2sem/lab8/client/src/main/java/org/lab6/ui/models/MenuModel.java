package org.lab6.ui.models;

import common.models.Dragon;
import common.models.User;
import common.network.Request;
import common.network.Response;
import common.utils.ArgumentType;
import common.utils.Command;
import javafx.concurrent.ScheduledService;
import javafx.concurrent.Task;
import javafx.util.Duration;
import jdk.jshell.spi.ExecutionControl;
import org.lab6.auth.SessionHandler;
import org.lab6.commands.Register;
import org.lab6.commands.Show;
import org.lab6.network.TCPClient;
import org.lab6.ui.controllers.MenuFormController;

import javax.management.DescriptorAccess;
import java.io.Console;
import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

public class MenuModel {

    private static final int THREADS = Runtime.getRuntime().availableProcessors();
    private final static int UPDATE_TIME = 5000;
    private final TCPClient connectionHandler;
    private final List<Dragon> collection = new CopyOnWriteArrayList<>();
    private final MenuFormController controller;
    private final ExecutorService executorService = Executors.newFixedThreadPool(THREADS);

    public MenuModel(TCPClient connectionHandler, MenuFormController controller) {
        this.connectionHandler = connectionHandler;
        this.controller = controller;
        scheduledService.setPeriod(Duration.millis(UPDATE_TIME));
        scheduledService.start();
    }

    public void executeCommand(Command command, Map<ArgumentType, Object> args) {
        Task<String> task = new Task<String>() {
            @Override
            protected String call() throws Exception {
                String message = connectionHandler.sendAndReceiveCommand(new Request(command, args)).getMessage();
                return message;
            }
        };
        task.setOnFailed(event -> {
            ExecutionControl.UserException exception = (ExecutionControl.UserException) task.getException();
        });
        task.setOnFailed(event -> {
            if (task.getException().getClass().equals(SocketTimeoutException.class)) {
                controller.getTerminal().appendText("От сервера не был получен ответ..." + "\n");
            }
        });
        task.setOnSucceeded(event -> {
            if (task.getValue() != null) controller.getTerminal().appendText(task.getValue() + "\n");
        });
        executorService.execute(task);
    }



    private final ScheduledService<Void> scheduledService = new ScheduledService<Void>() {

        @Override
        protected Task<Void> createTask() {
            return new Task<Void>() {
                @Override
                protected Void call() {
                    updateCollection();
                    return null;
                }
            };
        }
    };
    public List<Dragon> getCollection() {
        return collection;
    }

    public void updateCollection() {
        executorService.execute(createTaskForUpdate());
    }

    private Task<Response> createTaskForUpdate() {
        Task<Response> taskForUpdate = new Task<Response>() {
            @Override
            protected Response call() throws ExecutionControl.UserException {
                Request request = new Request(new Show(), Map.of(ArgumentType.AUTH_SESSION, SessionHandler.getSession()));
                try {
                    return connectionHandler.sendAndReceiveCommand(request);
                } catch (IOException | ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
            }
        };
        taskForUpdate.setOnSucceeded(event -> {
            List<Dragon> recievedPeople = ((Response) taskForUpdate.getValue()).getDragons();
            List<Integer> currentIds = collection.stream().map(Dragon::getId).collect(Collectors.toList());
            if (recievedPeople != null) {
                controller.notifyDataChanged(getElementsToRemove(recievedPeople, currentIds),
                        getElementsToAdd(recievedPeople, currentIds),
                        getElementsToUpdate(recievedPeople));
                collection.clear();
                collection.addAll(recievedPeople);
            }
        });
        taskForUpdate.setOnFailed(event -> {
            try {
                System.out.println(taskForUpdate.getException());
                ExecutionControl.UserException e = ((ExecutionControl.UserException) taskForUpdate.getException());
                controller.closeApplication();
                controller.getTerminal().appendText(e.getMessage() + "\n");
            } catch (RuntimeException e) {
                controller.getTerminal().appendText(taskForUpdate.getException() + "\n");
            }
            });
        return taskForUpdate;

    }

    private List<Dragon> getElementsToAdd(List<Dragon> recievedPeople, List<Integer> currentIds) {
        List<Integer> recievedIds = recievedPeople.stream().map(Dragon::getId).toList();
        List<Dragon> newElements = new ArrayList<>();
        for (Integer id : recievedIds) {
            if (!currentIds.contains(id)) {
                recievedPeople.stream().filter(Dragon -> Dragon.getId() == id).findFirst().ifPresent(newElements::add);
            }
        }
        return newElements;
    }

    public MenuFormController getController() {
        return controller;
    }

    private List<Dragon> getElementsToRemove(List<Dragon> recievedPeople, List<Integer> currentIds) {
        List<Integer> recievedIds = recievedPeople.stream().map(Dragon::getId).toList();
        List<Dragon> elementsToRemove = new ArrayList<>();
        for (Integer id : currentIds) {
            if (!recievedIds.contains(id)) {
                collection.stream().filter(Dragon -> Dragon.getId() == id).findFirst().ifPresent(elementsToRemove::add);
            }
        }
        return elementsToRemove;
    }

    private List<Dragon> getElementsToUpdate(List<Dragon> recievedPeople) {
        List<Dragon> elementsToUpdate = new ArrayList<>();
        for (Dragon recievedDragon : recievedPeople) {
            for (Dragon human : collection) {
                if (human.getId() == recievedDragon.getId() && human.hashCode() != recievedDragon.hashCode()) {
                    elementsToUpdate.add(recievedDragon);
                }
            }
        }
        return elementsToUpdate;
    }

    public TCPClient getConnectionHandler() {
        return connectionHandler;
    }
}