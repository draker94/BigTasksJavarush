package mvc.view;


import mvc.controller.Controller;
import mvc.model.ModelData;

public interface View {
    public void refresh(ModelData modelData);

    public void setController(Controller controller);

    }

