package InputParsing;

import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;

public class OptionsBuilder
{
    public static Options getOptions()
    {
        Options options = new Options();

        Option mutation = new Option("mut", "mutation", true, "mutation algorithm");
        mutation.setArgs(Option.UNLIMITED_VALUES);
        options.addOption(mutation);

        Option crossover = new Option("cross", "crossover", true, "crossover algorithm");
        crossover.setArgs(Option.UNLIMITED_VALUES);
        options.addOption(crossover);

        Option algorithm = new Option("algo", "algorithm", true, "optimization algorithm");
        algorithm.setArgs(Option.UNLIMITED_VALUES);
        options.addOption(algorithm);

        Option evals = new Option("evals", "evaluators", true, "objective calculation evaluators");
        evals.setArgs(Option.UNLIMITED_VALUES);
        options.addOption(evals);

        Option maxc = new Option("maxc", "maximumNumberOfCycles", true, "maximum number of cycles");
        maxc.setRequired(true);
        options.addOption(maxc);

        Option file = new Option("file", "problemFile", true, "name of problem file");
        file.setRequired(true);
        options.addOption(file);



        return options;

    }
}
