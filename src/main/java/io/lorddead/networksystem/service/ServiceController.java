package io.lorddead.networksystem.service;

import io.lorddead.networksystem.dao.Dao;
import io.lorddead.networksystem.dao.NetworkDao;
import io.lorddead.networksystem.model.Network;
import io.lorddead.networksystem.ui.ConsoleView;

import java.sql.SQLException;
import java.util.Map;
import java.util.Optional;

public class ServiceController {

    private final ConsoleView view;
    private Map<Class<? extends Dao<?>>, Dao<?>> daos;

    public ServiceController(ConsoleView view) {
        this.view = view;
    }

    public void process() {
        try {
            while (true) {
                var userCode = view.getUserChoice();
                if (!userCode.isValid()) {
                    continue;
                }

                Optional<UserChoice> action = UserChoice.valueOf(userCode.getCode());
                if (action.isEmpty()) {
                    view.showDisplayInfo("\nInvalid code. Please try again.\n");
                } else {
                    var userChoice = action.get();
                    if (userChoice == UserChoice.EXIT) {
                        break;
                    }

                    processChoice(userChoice);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            view.close();
        }
    }

    private void processChoice(UserChoice userChoice) throws SQLException {
        switch (userChoice) {
            case ADD_NETWORK -> {
                Network newNetwork = view.getNetworkFromUser();
                NetworkDao networkDao = new NetworkDao();
                networkDao.save(newNetwork);

                view.showDisplayObjectFromDatabase(newNetwork);
            }

            default -> throw new RuntimeException("Unsupported operation: " + userChoice);
        }
    }
}
