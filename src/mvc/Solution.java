package mvc;


import mvc.controller.Controller;
import mvc.model.MainModel;
import mvc.model.Model;
import mvc.view.EditUserView;
import mvc.view.UsersView;

public class Solution {
    public static void main(String[] args) {
        Model model = new MainModel();

        UsersView usersView = new UsersView();
        Controller controller = new Controller();

        usersView.setController(controller);
        controller.setModel(model);
        controller.setUsersView(usersView);



        EditUserView editUserView = new EditUserView();
        editUserView.setController(controller);
        controller.setEditUserView(editUserView);

        usersView.fireEventShowAllUsers();
        usersView.fireEventOpenUserEditForm(126L);
        editUserView.fireEventUserDeleted(124L);
        editUserView.fireEventUserChanged("Вася", 126L, 88);
        usersView.fireEventShowDeletedUsers();




    }
}