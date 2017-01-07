package model;

import java.util.regex.*;

/**
 * Creates a sea and add life into it.
 */
public class GameOfLife {
    private static Sea sea;
    protected static boolean debug = false;

    public static void main(String[] args) {
        boolean useDefault = false;
        Integer nbPilchard = null;
        Integer nbShark = null;
        Integer height = null;
        Integer width = null;
        Integer nbCycle = null;

        for (int a = 0; a < args.length; a++) {
            Matcher matcher;

            if (args[a].equals("--debug")) {
                debug = true;
            }

            if (args[a].equals("--default")) {
                useDefault = true;
            }

            if (args[a].equals("--help") || args[a].equals("-h")) {
                printUsage();
                System.exit(0);
            }

            matcher = Pattern.compile("^--pilchards=(\\d+)$").matcher(args[a]);
            if (matcher.find()) {
                nbPilchard = Integer.parseInt(matcher.group(1));
            }

            matcher = Pattern.compile("^--sharks=(\\d+)$").matcher(args[a]);
            if (matcher.find()) {
                nbShark = Integer.parseInt(matcher.group(1));
            }

            matcher = Pattern.compile("^--height=(\\d+)$").matcher(args[a]);
            if (matcher.find()) {
                height = Integer.parseInt(matcher.group(1));
            }

            matcher = Pattern.compile("^--width=(\\d+)$").matcher(args[a]);
            if (matcher.find()) {
                height = Integer.parseInt(matcher.group(1));
            }

            matcher = Pattern.compile("^--cycles=(\\d+)$").matcher(args[a]);
            if (matcher.find()) {
                nbCycle = Integer.parseInt(matcher.group(1));
            }
        }

        // All or none
        if ((height == null || width == null) && (height != null || width != null)) {
            exitUsage();
        }

        // All or none
        if ((nbPilchard == null || nbShark == null) && (nbPilchard != null || nbShark != null)) {
            exitUsage();
        }

        if (useDefault) {
            sea = getDefault();
        } else if ((height == null && nbPilchard != null) || (height != null && nbPilchard == null)) {
            // The four data need to be set
            exitUsage();
        } else if (height == null && nbPilchard == null) {
            sea = new Sea();
        } else {
            sea = new Sea(height, width, nbShark, nbPilchard);
        }

        startLife(nbCycle != null ? nbCycle : 40);
    }

    /**
     * Print usage and exit the app with an error code
     */
    private static void printUsage() {
        String usage = "\n";
        usage += "Usage: [options]\n";
        usage += "\n";
        usage += "List of options:\n";
        usage += "  --debug         Debug all actions\n";
        usage += "  --default       Use the default values\n";
        usage += "\n";
        usage += " The sea values (all of them are optionnal but if one is set, all are needed)\n";
        usage += "  --height=N      The height of the sea\n";
        usage += "  --width=N       The width of the sea\n";
        usage += "  --sharks=N      The number of shark to add\n";
        usage += "  --pilchards=N   The number of Pilchard to add";

        System.out.println(usage);
    }

    /**
     * Print usage and exit the app with an error code
     */
    private static void exitUsage() {
        printUsage();
        System.exit(-1);
    }

    /**
     * Create a 28x11 sea with fixed fish positions
     * @return The default sea
     */
    private static Sea getDefault() {
        int height = 11;
        int width = 28;

        Cell[][] grid = new Cell[height][width];

        grid[0][0] = new Pilchard(0, 0);
        grid[0][1] = new Shark(0, 1);
        grid[1][0] = new Shark(1, 0);

        int x, y;
        for (int i = (height * width) - 1; i >= 0; i--) {
            y = i % height;
            x = i / height;

            if (grid[y][x] == null) {
                grid[y][x] = new Water(y, x);
            }
        }

        return new Sea(grid);
    }

    /**
     * Start life for a defined time
     * @param nbCycle The number of cycle to apply
     */
    public static void startLife(int nbCycle) {
        int cycle;
        for (cycle = 0; cycle < nbCycle; cycle++) {
            System.out.printf("\n######### Cycle %02d #########\n", cycle);
            System.out.println(sea);
            sea.applyCycle();
        }

        System.out.printf("\n######### Cycle %02d #########\n", cycle);
        System.out.println(sea);
    }

    /**
     * Start life for an unlimited time
     */
    public static void startLife() {
        startLife((int) Double.POSITIVE_INFINITY);
    }
}
