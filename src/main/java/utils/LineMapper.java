package utils;

public class LineMapper {
    public static int findLineOfCommandStart(String strs, int num) {
        if (num < 0)
            return -1;

        String[] commandMap = strs.split("\n");

        if (num >= commandMap.length)
            return -1;

        int iter = -1;

        for (int i = 0; i < commandMap.length; i++) {
            if (commandMap[i] != null) {
                // identify commands like "<?html something ?>"
                if (commandMap[i].indexOf("<?") != -1) {
                    iter += 2;

                    if (iter == num || iter - 1 == num)
                        return i + 1;

                    while ((commandMap[i].indexOf("?>") == -1) && (i < commandMap.length))
                        i++;

                    continue;
                }

                // skip comments
                if (commandMap[i].trim().indexOf("//") == 0)
                    continue;

                if (commandMap[i].indexOf("/*") != -1) {
                    while ((commandMap[i].indexOf("*/") == -1) && (i < commandMap.length))
                        i++;

                    continue;
                }

                // identify commands like "html(something)"
                if (commandMap[i].indexOf("(") != -1) {
                    iter++;

                    if (iter == num)
                        return i + 1;

                    while ((commandMap[i].indexOf(")") == -1) && (i < commandMap.length))
                        i++;

                    continue;
                } else {
                    // if missing "()" after command
                    if ((commandMap[i].trim() != ")") && (commandMap[i].trim() != "?>"))
                        return i + 1;
                }
            }
        }

        return -1;
    }
}
