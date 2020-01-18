package snake;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Snake {
    private List<SnakeSection> sections;
    private boolean isAlive;
    private SnakeDirection direction;

    public Snake(int x, int y) {
        sections = new ArrayList<>();
        SnakeSection head = new SnakeSection(x, y);
        sections.add(head);
        isAlive = true;
    }

    public void move() {
        if (!isAlive) {
            return;
        } else if (direction == SnakeDirection.UP) {
            move(0, -1);
        } else if (direction == SnakeDirection.RIGHT) {
            move(1, 0);
        } else if (direction == SnakeDirection.DOWN) {
            move(0, 1);
        } else if (direction == SnakeDirection.LEFT) {
            move(-1, 0);
        }
    }

    public void move(int dx, int dy)
    {
        SnakeSection new_section = new SnakeSection(sections.get(0).getX()+dx, sections.get(0).getY()+dy);
        checkBorders(new_section);
        checkBody(new_section);
        if (isAlive()) {
            sections.add(0, new_section);
            if ((Room.game.getMouse().getX() == new_section.getX()) && (Room.game.getMouse().getY() == new_section.getY())) {
                Room.game.eatMouse();
            } else {
                sections.remove(sections.size() - 1);
            }
        }
    }

    public int getX() {
        return sections.get(0).getX();
    }

    public int getY() {
        return sections.get(0).getY();
    }

    public List<SnakeSection> getSections() {
        return sections;
    }

    public boolean isAlive() {
        return isAlive;
    }

    public SnakeDirection getDirection() {
        return direction;
    }

    public void setDirection(SnakeDirection direction) {
        this.direction = direction;
    }

    public void checkBorders(SnakeSection head) {
        if (head.getY() < 0 || head.getY() > (Room.game.getHeight() - 1)
                || head.getX() < 0 || head.getX() > (Room.game.getWidth() - 1)) {
            isAlive = false;
        }
    }

    public void checkBody(SnakeSection head) {
        if (sections.contains(head)) {
            isAlive = false;
        }
    }
}
