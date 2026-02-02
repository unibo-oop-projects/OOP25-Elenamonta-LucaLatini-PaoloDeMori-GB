package it.unibo.geometrybash.controller;

import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;

import it.unibo.geometrybash.view.menus.MainMenuView;
import it.unibo.geometrybash.view.utilities.GameResolution;

/**
 * An helper class to handle generic commands string.
 */
public final class GenericCommands {

    private static Map<String, GameResolution> commandToGameResolution = Map.of(getBigResolutionCommand(),
            GameResolution.BIG,
            getMediumResolutionCommand(), GameResolution.MEDIUM,
            getSmallResolutionCommand(), GameResolution.BIG);

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
     * @param command        the command.
     * @param setterInner    the method to call to apply changes if the command is a
     *                       correctly formatted command to set the inner color.
     * @param setterOuterthe method to call to apply changes if the command is a
     *                       correctly formatted command to set the inner color.
     * @return true if the command was correctly formatted, false otherwise.
     */
    public static boolean checkSetPlayerColorCommand(String command, Consumer<Integer> setterInner,
            Consumer<Integer> setterOuter) {

        String[] parts = command.split(" ");
        Consumer<Integer> setter;

        if (isSetColorCommand(parts)) {
            if (parts[1].equals(MainMenuView.FLAG_INNER)) {
                setter = setterInner;
            } else {
                setter = setterOuter;
            }
            String color = parts[2].replace("-", "").toLowerCase();
            if (Arrays.stream(MainMenuView.AVAILABLE_COLORS).anyMatch(x -> x.equalsIgnoreCase(color))) {
                Optional<Integer> rgbaColor = stringColorToRgba(color);
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
    private static Optional<Integer> stringColorToRgba(String color) {
        switch (color) {
            case "red":
                return Optional.of(0XFFFF0000);
            case "blue":
                return Optional.of(0XFF0000FF);
            case "green":
                return Optional.of(0XFF00FF00);
            case "yellow":
                return Optional.of(0XFFFFD700);
            case "white":
                return Optional.of(0XFFFFFFFF);
            default:
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
    private static boolean isSetColorCommand(String[] parts) {

        if (parts.length != 3 || !parts[0].equals(MainMenuView.CMD_SET_COLOR)
                || ((!parts[1].equals(MainMenuView.FLAG_INNER)) && (!parts[1].equals(MainMenuView.FLAG_OUTER)))) {
            return false;
        }

        return true;
    }

    /**
     * checks if the command can be a game panel size command, if so returns the
     * correct {@link GameResolution} representation.
     * 
     * @param command the command received.
     * @return the correct {@link GameResolution} representation.
     */
    public static Optional<GameResolution> checkResolutionCommand(String command) {
        if (command.contains(command)) {
            return Optional.of(commandToGameResolution.get(command));
        } else {
            return Optional.empty();
        }

    }

    private static String getBigResolutionCommand() {
        return MainMenuView.ARG_RESOLUTION + " -" + MainMenuView.BIG;
    }

    private static String getMediumResolutionCommand() {
        return MainMenuView.ARG_RESOLUTION + " -" + MainMenuView.MEDIUM;
    }

    private static String getSmallResolutionCommand() {
        return MainMenuView.ARG_RESOLUTION + " -" + MainMenuView.SMALL;
    }

}
