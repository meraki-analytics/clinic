package com.merakianalytics.clinic;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.merakianalytics.clinic.exceptions.ClinicAnnotationException;
import com.merakianalytics.clinic.exceptions.ClinicGetHelpException;
import com.merakianalytics.clinic.exceptions.ClinicParseException;

/**
 * The main entry point to the clinic command line parsing library. Use {@code Clinic.cli(YourClass.class)} from your main to get started.
 *
 * @see <a href="https://github.com/meraki-analytics/clinic">GitHub</a>
 * @see <a href="http://clinic.readthedocs.io/en/latest/">Documentation</a>
 * @since 1.0.0
 */
public abstract class Clinic {
    /**
     * A clinic application builder. Used to configure the clinic application's behavior.
     *
     * @since 1.0.0
     */
    public static class Builder {
        private static final String[] DEFAULT_ARGS = new String[0];
        private static final boolean DEFAULT_AUTOMATIC = false;
        private static final String DEFAULT_EXECUTABLE_NAME = "program";
        private static final String DEFAULT_HELP = null;

        private String[] args = DEFAULT_ARGS;
        private boolean automatic = DEFAULT_AUTOMATIC;
        private final Class<?> clazz;
        private String executableName = DEFAULT_EXECUTABLE_NAME;
        private String help = DEFAULT_HELP;

        private Builder(final Class<?> clazz) {
            this.clazz = clazz;
        }

        /**
         * Sets the command line arguments to use for the clinic application. Usually taken directly from your main(String[] args) method.
         *
         * @param args
         *        the command line arguments for the clinic application to use
         * @return the application builder
         * @since 1.0.0
         */
        public Builder args(final String[] args) {
            this.args = args;
            return this;
        }

        /**
         * Sets clinic to try to automatically attempt to use any static method in your cli class as a command, regardless of whether it's been annotated
         * with @Command.
         *
         * @return the application builder
         * @since 1.0.0
         */
        public Builder automatic() {
            automatic = true;
            return this;
        }

        /**
         * Sets whether clinic should try to automatically attempt to use any static method in your cli class as a command, regardless of whether it's been
         * annotated with @Command.
         *
         * @param automatic
         *        whether to automatically use static methods
         * @return the application builder
         * @since 1.0.0
         */
        public Builder automatic(final boolean automatic) {
            this.automatic = automatic;
            return this;
        }

        /**
         * Runs the clinic application in a new {@link java.lang.Thread} and provides a {@link java.util.concurrent.Future} to monitor the result
         *
         * @return a {@link java.util.concurrent.Future} that will provide the exit code of the clinic application
         * @since 1.0.0
         */
        public Future<Integer> await() {
            final CompletableFuture<Integer> future = new CompletableFuture<>();
            new Thread(() -> {
                future.complete(Clinic.run(clazz, args, executableName, help, automatic));
            }).start();
            return future;
        }

        /**
         * Sets the intended executable name that will be used to launch your Java application once it is deployed. This will be used as part of the --help
         * prompt.
         *
         * @param executableName
         *        the executable name to use in --help prompts
         * @return the application builder
         * @since 1.0.0
         */
        public Builder executableName(final String executableName) {
            this.executableName = executableName;
            return this;
        }

        /**
         * Executes the clinic application using the provided {@link java.util.concurrent.Executor}
         *
         * @param executor
         *        the {@link java.util.concurrent.Executor} to use
         * @since 1.0.0
         */
        public void execute(final Executor executor) {
            executor.execute(Clinic.getRunnable(clazz, args, executableName, help, automatic));
        }

        /**
         * Gets a {@link java.lang.Runnable} that executes the clinic application when run
         *
         * @return a {@link java.lang.Runnable} that executes the clinic application when run
         * @since 1.0.0
         */
        public Runnable getRunnable() {
            return Clinic.getRunnable(clazz, args, executableName, help, automatic);
        }

        /**
         * Gets an un-started {@link java.lang.Thread} that executes the clinic application when run
         *
         * @return an un-started {@link java.lang.Thread} that executes the clinic application when run
         * @since 1.0.0
         */
        public Thread getThread() {
            return new Thread(Clinic.getRunnable(clazz, args, executableName, help, automatic));
        }

        /**
         * Sets the help description for the application to show along with usage information if the user uses the --help option
         *
         * @param help
         *        the help description
         * @return the application builder
         * @since 1.0.0
         */
        public Builder help(final String help) {
            this.help = help;
            return this;
        }

        /**
         * Sets clinic to <b>not</b> try to automatically attempt to use any static method in your cli class as a command, regardless of whether it's been
         * annotated. This is the default.
         * with @Command.
         *
         * @return the application builder
         * @since 1.0.0
         */
        public Builder manual() {
            automatic = false;
            return this;
        }

        /**
         * Runs the clinic application
         *
         * @return the exit code of the application
         * @since 1.0.0
         */
        public int run() {
            return Clinic.run(clazz, args, executableName, help, automatic);
        };

        /**
         * Runs the clinic application in a new {@link java.lang.Thread}
         *
         * @return the {@link java.lang.Thread} that is running the clinic application
         * @since 1.0.0
         */
        public Thread start() {
            final Thread thread = getThread();
            thread.start();
            return thread;
        }

        /**
         * Executes the clinic application using the proviced {@link java.util.concurrent.ExecutorService}
         *
         * @param service
         *        the {@link java.util.concurrent.ExecutorService} to use
         * @return a {@link java.util.concurrent.Future} that will provide the exit code of the clinic application
         * @since 1.0.0
         */
        public Future<Integer> submit(final ExecutorService service) {
            return service.submit(Clinic.getCallable(clazz, args, executableName, help, automatic));
        }
    }

    private static final int FAILURE = 1;
    private static final int SUCCESS = 0;

    /**
     * Creates a command line application based on the clinic-annotated methods defined in the provided class
     *
     * @param clazz
     *        the class whose methods should be used to run the command line application
     * @return an clinic application builder
     * @since 1.0.0
     */
    public static Builder cli(final Class<?> clazz) {
        return new Builder(clazz);
    }

    private static String getApplicationHelp(final String executableName, final String help, final Collection<Command> commands) {
        final StringBuilder builder = new StringBuilder(System.lineSeparator());
        builder.append("Usage:  " + executableName + " COMMAND" + System.lineSeparator());
        builder.append(System.lineSeparator());
        if(help != null) {
            builder.append(help + System.lineSeparator());
            builder.append(System.lineSeparator());
        }

        if(commands.size() > 0) {
            int max = 0;
            for(final Command command : commands) {
                if(command.getName().length() > max) {
                    max = command.getName().length();
                }
            }
            final int maxLength = max;

            builder.append("Commands:" + System.lineSeparator());
            commands.stream().sorted((final Command one, final Command two) -> one.getName().compareTo(two.getName()))
                .forEachOrdered((final Command command) -> {
                    builder.append("  " + command.getName());
                    final int buffer = maxLength - command.getName().length();
                    for(int i = 0; i < buffer; i++) {
                        builder.append(' ');
                    }
                    builder.append("  ");
                    if(command.getHelp() != null) {
                        builder.append(" " + command.getHelp());
                    }
                    builder.append(System.lineSeparator());
                });
            builder.append(System.lineSeparator());
            builder.append("Run '" + executableName + " COMMAND --help' for more information on a command." + System.lineSeparator());
            builder.append(System.lineSeparator());
        }
        return builder.toString();
    }

    private static Callable<Integer> getCallable(final Class<?> clazz, final String[] args, final String executableName, final String help,
        final boolean automatic) {
        return () -> {
            return Clinic.run(clazz, args, executableName, help, automatic);
        };
    }

    private static Runnable getRunnable(final Class<?> clazz, final String[] args, final String executableName, final String help, final boolean automatic) {
        return () -> {
            Clinic.run(clazz, args, help, executableName, automatic);
        };
    }

    private static int run(final Class<?> clazz, String[] args, final String executableName, final String help, final boolean automatic) {
        final Map<String, Command> commands = Arrays.stream(clazz.getDeclaredMethods()).filter((final Method method) -> {
            return Modifier.isStatic(method.getModifiers()) && !"main".equals(method.getName())
                && (automatic || method.isAnnotationPresent(com.merakianalytics.clinic.annotations.Command.class)
                    || method.isAnnotationPresent(com.merakianalytics.clinic.annotations.AutoCommand.class));
        }).map((final Method method) -> Command.get(executableName, method)).collect(Collectors.toMap(Command::getName, Function.identity()));

        if(commands.isEmpty()) {
            throw new ClinicAnnotationException("No @Commands were found in " + clazz.getSimpleName() + "!");
        }

        // If there is only one command specified, or a command is annotated as default, default to that.
        Command command = commands.size() == 1 ? commands.values().iterator().next() : null;
        if(command == null) {
            final Command[] defaultCommands = commands.values().stream().filter(Command::isDefaultCommand).toArray(Command[]::new);
            if(defaultCommands.length > 1) {
                throw new ClinicAnnotationException("Multiple @Commands were designated as default commands! Only one command can be default.");
            }
            command = defaultCommands.length > 0 ? defaultCommands[0] : null;
        }

        if(args.length > 0) {
            if(Common.HELP_OPTION.equals(args[0])) {
                System.out.print(getApplicationHelp(executableName, help, commands.values()));
                return SUCCESS;
            }

            // If the first argument isn't an option or help, treat it as a command.
            if(!args[0].startsWith("-")) {
                if(!commands.keySet().contains(args[0])) {
                    System.out.print("Unrecognized command: " + args[0] + "!" + System.lineSeparator());
                    System.out.print(getApplicationHelp(executableName, help, commands.values()));
                    return FAILURE;
                }

                command = commands.get(args[0]);
                args = Arrays.copyOfRange(args, 1, args.length);
            }
        }

        if(command == null) {
            System.out.print("No command was provided!" + System.lineSeparator());
            System.out.print(getApplicationHelp(executableName, help, commands.values()));
            return FAILURE;
        }

        try {
            command.execute(args);
            return SUCCESS;
        } catch(final ClinicGetHelpException e) {
            System.out.print(e.getMessage());
            return SUCCESS;
        } catch(final ClinicParseException e) {
            System.out.print(e.getMessage());
            return FAILURE;
        }
    }

    private Clinic() {}
}
