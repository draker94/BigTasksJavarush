package mvc.model;



import mvc.bean.User;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class FakeModel implements Model {
    private ModelData modelData = new ModelData();

    @Override
    public ModelData getModelData() {
        return modelData;
    }

    @Override
    public void loadDeletedUsers() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void deleteUserById(long id) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void changeUserData(String name, long id, int level) {
        throw new UnsupportedOperationException();

    }

    @Override
    public void loadUserById(long userId) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void loadUsers() {
        ArrayList<User> list = new ArrayList<>();
        User user1 = new User("A", 1, 1);
        User user2 = new User("B", 2, 1);
        list.add(user1);
        list.add(user2);
        modelData.setUsers(list);
    }
}
