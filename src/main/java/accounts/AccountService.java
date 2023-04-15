package accounts;

import dbService.DBException;
import dbService.DBService;
import dbService.dataSets.UsersDataSet;

public class AccountService {

    public AccountService() {
    }

    public void addNewUser(UserProfile userProfile, DBService dbService) {
        try {

            long userId = dbService.addUser(userProfile.getLogin(), userProfile.getPass());
            System.out.println("Added user id: " + userId);

            UsersDataSet dataSet = dbService.getUser(userId);
            System.out.println("User data set: " + dataSet);

        } catch (DBException e) {
            e.printStackTrace();
        }
    }
    //Добавил проверку на NULL
    public UserProfile getUserByLogin(String login, DBService dbService) throws DBException {
        long id = dbService.getUserID(login);
        UsersDataSet usersDataSet = dbService.getUser(id);
        if (usersDataSet == null) {
            return null;
        }
        UserProfile userProfile = new UserProfile(usersDataSet.getName(), usersDataSet.getPassword());
        return userProfile;
    }
}
