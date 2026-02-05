package it.unibo.geometrybash.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;
import java.util.function.Consumer;

import org.junit.jupiter.api.Test;

import it.unibo.geometrybash.model.MenuModel;
import it.unibo.geometrybash.view.menus.MainMenuView;
import it.unibo.geometrybash.view.utilities.GameResolution;

class GenericCommandsTest {
    private Integer setterInnerString = 0;
    private Integer setterOuterString = 0;

    @Test
    void checkSetPlayerColorCommand() {

        final Consumer<Integer> setterInner = i -> setterInnerString = i;
        final Consumer<Integer> setterOuter = i -> setterOuterString = i;
        final String errorTestCommand = "aaa";
        final String errorWrongColor = MainMenuView.CMD_SET_COLOR + " "
                + MainMenuView.FLAG_INNER + " -aaa";
        final String correctInnerCommand = MainMenuView.CMD_SET_COLOR
                + " "
                + MainMenuView.FLAG_INNER
                + " "
                + MenuModel.AVAILABLE_COLORS.keySet().iterator().next();

        final String correctOuterCommand = MainMenuView.CMD_SET_COLOR
                + " "
                + MainMenuView.FLAG_OUTER
                + " "
                + MenuModel.AVAILABLE_COLORS.keySet().iterator().next();

        assertFalse(GenericCommands.checkSetPlayerColorCommand(errorTestCommand, setterInner, setterOuter));
        assertEquals(0, setterInnerString);
        assertEquals(0, setterOuterString);

        assertFalse(GenericCommands.checkSetPlayerColorCommand(errorWrongColor, setterInner, setterOuter));
        assertEquals(0, setterInnerString);
        assertEquals(0, setterOuterString);

        assertTrue(GenericCommands.checkSetPlayerColorCommand(correctInnerCommand, setterInner, setterOuter));
        assertNotEquals(0, setterInnerString);
        assertEquals(0, setterOuterString);
        setterInnerString = 0;

        assertTrue(GenericCommands.checkSetPlayerColorCommand(correctOuterCommand, setterInner, setterOuter));
        assertEquals(0, setterInnerString);
        assertNotEquals(0, setterOuterString);
        setterOuterString = 0;

    }

    @Test
    void checkResolutionCommand() {
        final String existingCommand = MainMenuView.ARG_RESOLUTION + " " + MainMenuView.STANDARD_SEPARATOR
                + MainMenuView.BIG;
        final GameResolution existingCommandResolution = GameResolution.BIG;
        final String notExistingCommand = "aaa";
        final Optional<GameResolution> emptyOptional = Optional.empty();
        assertEquals(existingCommandResolution, GenericCommands.checkResolutionCommand(existingCommand).get());
        assertEquals(emptyOptional, GenericCommands.checkResolutionCommand(notExistingCommand));
    }

    @Test
    void checkSelectLevelCommand() {
        final Integer numberLevel = 0;
        final String existingCommand = MainMenuView.CMD_SET_LEVEL + " " + numberLevel.toString();
        final String tooMuchWordsCommand = MainMenuView.CMD_SET_LEVEL + " 0 1";
        final String wrongFirstCommand = "NOT" + MainMenuView.CMD_SET_LEVEL + " 0";
        final String notNumericalCommand = MainMenuView.CMD_SET_LEVEL + " " + MenuModel.LEVELS_NAME_LIST.get(0);
        final Optional<Integer> empty = Optional.empty();

        assertEquals(empty, GenericCommands.checkSelectLevelCommand(tooMuchWordsCommand));
        assertEquals(empty, GenericCommands.checkSelectLevelCommand(wrongFirstCommand));
        assertEquals(Optional.of(Integer.MIN_VALUE), GenericCommands.checkSelectLevelCommand(notNumericalCommand));
        assertEquals(Optional.of(numberLevel), GenericCommands.checkSelectLevelCommand(existingCommand));

    }

}
