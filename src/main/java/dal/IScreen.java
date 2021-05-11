package dal;

import be.DefaultScreen;
import be.Screen;
import be.ScreenElement;
import be.Users;
import dal.exception.DALexception;

import java.util.List;

/**
 *
 */
public interface IScreen {
    void save(Screen screen, List<ScreenElement> screenElements, List<Users> usersList) throws DALexception;

    List<Screen> getMainScreens() throws DALexception;
}
