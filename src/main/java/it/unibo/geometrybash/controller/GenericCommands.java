package it.unibo.geometrybash.controller;

import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;

import it.unibo.geometrybash.model.MenuModel;
import it.unibo.geometrybash.view.menus.MainMenuView;
import it.unibo.geometrybash.view.utilities.GameResolution;

/**
 * An helper class to handle generic commands string.
 */
public final class GenericCommands {

    private static Map<String, GameResolution> commandToGameResolution = Map.of(getBigResolutionCommand(),
            GameResolution.BIG,
            getMediumResolutionCommand(), GameResolution.MEDIUM,
            getSmallResolutionCommand(), GameResolution.SMALL);

    private GenericCommands() {
        // impossible to create this class because this is a helper class.
    }

    /**
     * Check if the string is a command to set the color of the player, if so this
     * methods calls the right method.
     *
     * <p>
     * If the string is intended to set the inner color accepts the setterInner
     * consumer, if the string is intended to set the outer color,
     * accepts the setter outer consumer.
     * </p>
     *
     * @param command     the command.
     * @param setterInner the method to call to apply changes if the command is a
     *                    correctly formatted command to set the inner color.
     * @param setterOuter the method to call to apply changes if the command is a
     *                    correctly formatted command to set the inner color.
     * @return true if the command was correctly formatted, false otherwise.
     */
    public static boolean checkSetPlayerColorCommand(final String command, final Consumer<Integer> setterInner,
            final Consumer<Integer> setterOuter) {

        final String[] parts = command.split(" ");
        final Consumer<Integer> setter;

        if (isSetColorCommand(parts)) {
            if (MainMenuView.FLAG_INNER.equals(parts[1])) {
                setter = setterInner;
            } else {
                setter = setterOuter;
            }
            final String color = parts[2].replace("-", "").toLowerCase(Locale.ROOT);
            if (MenuModel.AVAILABLE_COLORS.keySet().stream().anyMatch(x -> x.equalsIgnoreCase(color))) {
                final Optional<Integer> rgbaColor = stringColorToRgba(color);
                if (rgbaColor.isPresent()) {
                    setter.accept(rgbaColor.get());
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Method that converts a color string in the set color command into an
     * hexxadecimal representation of a RGBA color.
     *
     * @param color the string color.
     * @return an Optional empty if the color is not convertible, otherwise it
     *         returns an Optional wrapping a color.
     */
    private static Optional<Integer> stringColorToRgba(final String color) {
        if (MenuModel.AVAILABLE_COLORS.keySet().contains(color)) {
            return Optional.of(MenuModel.AVAILABLE_COLORS.get(color));
        } else {
            return Optional.empty();
        }
    }

    /**
     * Checks if the commands past a string's arrays can be a setColorCommand.
     *
     * <p>
     * This function doesn't check if the color if presents is correctly formatted.
     * </p>
     *
     * @param parts The command received without blank spaces and in an array
     *              representation.
     * @return true if the command can be a setColor command.
     */
    private static boolean isSetColorCommand(final String[] parts) {

        return parts.length == 3 && MainMenuView.CMD_SET_COLOR.equals(parts[0])
                && (MainMenuView.FLAG_INNER.equals(parts[1]) || MainMenuView.FLAG_OUTER.equals(parts[1]));

    }

    /**
     * checks if the command can be a game panel size command, if so returns the
     * correct {@link GameResolution} representation.
     *
     * @param command the command received.
     * @return the correct {@link GameResolution} representation.
     */
    public static Optional<GameResolution> checkResolutionCommand(final String command) {
        if (commandToGameResolution.keySet().contains(command)) {
            return Optional.of(commandToGameResolution.get(command));
        } else {
            return Optional.empty();
        }

    }

    /**
     * checks if the command can be a level selector command, if so returns the
     * correct {@link Integer} representation.
     * 
     * @param command the command received.
     * @return the correct {@link Integer} representation.
     */
    public static Optional<Integer> checkSelectLevelCommand(final String command) {
        final String[] splitCommand = command.split(" ");
        final int invalidInt = Integer.MIN_VALUE;
        if (splitCommand.length == 2 && MainMenuView.CMD_SET_LEVEL.equals(splitCommand[0])) {
            int number;
            try {
                number = Integer.parseInt(splitCommand[1].replace("-", ""));
            } catch (final NumberFormatException e) {
                number = invalidInt;
            }
            return Optional.of(number);
        } else {
            return Optional.empty();
        }

    }

    private static String getBigResolutionCommand() {
        return MainMenuView.ARG_RESOLUTION + " " + MainMenuView.STANDARD_SEPARATOR + MainMenuView.BIG;
    }

    private static String getMediumResolutionCommand() {
        return MainMenuView.ARG_RESOLUTION + " " + MainMenuView.STANDARD_SEPARATOR + MainMenuView.MEDIUM;
    }

    private static String getSmallResolutionCommand() {
        return MainMenuView.ARG_RESOLUTION + " " + MainMenuView.STANDARD_SEPARATOR + MainMenuView.SMALL;
    }

}
