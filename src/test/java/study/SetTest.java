package study;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashSet;
import java.util.Set;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

/**
 * @author : choi-ys
 * @date : 2022-05-11 오전 12:49
 */
@DisplayName("Set Collection에 대한 학습 테스트")
public class SetTest {
    private Set<Integer> numbers;

    @BeforeEach
    void setUp() {
        numbers = new HashSet<>();
        numbers.add(1);
        numbers.add(1);
        numbers.add(2);
        numbers.add(3);
    }

    @Test
    @DisplayName("Set : 포함된 원소의 개수에 대한 검증")
    public void setTest() {
        // When & Then
        assertThat(numbers).hasSize(3);
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 2, 3})
    @DisplayName("Set : 요소 포함 여부 검증")
    public void setContainTest(int given) {
        // when
        boolean actual = numbers.contains(given);

        // then
        Assertions.assertThat(actual).isTrue();
    }

    @ParameterizedTest
    @DisplayName("Set : 요소 포함/미포함 여부 검증")
    @CsvSource(value = {"1:true", "2:true", "3:true", "4:false", "5:false"}, delimiterString = ":")
    public void setParameterTest(int given, boolean expected) {
        // Given
        boolean actual = numbers.contains(given);

        // When & Then
        assertThat(actual).isEqualTo(expected);
    }
}