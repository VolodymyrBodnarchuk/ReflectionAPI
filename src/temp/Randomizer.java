package temp;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public interface Randomizer {

    <T> T randomize(List<T> list);
}
