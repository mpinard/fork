package mpinard.sample;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class SampleTest {

    @Test
    public void When_CalculateSum_Expect_Total() {
        assertThat(new Sample().calculateSum(1, 2)).isEqualTo(3);
    }

}