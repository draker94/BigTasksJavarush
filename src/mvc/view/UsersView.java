package mvc.view;


import mvc.bean.User;
import mvc.controller.Controller;
import mvc.model.ModelData;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class UsersView implements View {
    private Controller controller;


    @Override
    public void refresh(ModelData modelData) {
        if(!modelData.isDisplayDeletedUserList()) {
            System.out.println("All users:");
        }
        else if (modelData.isDisplayDeletedUserList()) {
            System.out.println("All deleted users:");
        }

        List<User> users = modelData.getUsers();


        for (User e : users) {
            System.out.println("\t" + e);
        }
        System.out.println("===================================================");
    }

    @Override
    public void setController(Controller controller) {
        this.controller = controller;
    }

    public void fireEventShowAllUsers() {
        controller.onShowAllUsers();
    }

    public void fireEventShowDeletedUsers() {
        controller.onShowAllDeletedUsers();
    }

    public void fireEventOpenUserEditForm(long id) {
        controller.onOpenUserEditForm(id);
    }



}
