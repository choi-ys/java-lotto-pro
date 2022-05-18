package step3.lotto.domain.lotto;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.assertAll;
import static step3.lotto.domain.lotto.Lotto.INVALID_LOTTO_NUMBER_COUNT_ERROR;
import static step3.lotto.domain.lotto.Lotto.LOTTO_NUMBER_DUPLICATED_ERROR;
import static step3.lotto.domain.lotto.LottoNumber.INVALID_LOTTO_NUMBER_RANGE_ERROR;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

/**
 * @author : choi-ys
 * @date : 2022/05/15 7:39 오후
 */
@DisplayName("Domain:Lotto")
class LottoTest {

    @Test
    @DisplayName("1~45 범위의 중복 없는 6개 숫자를 가지는 로또 객체 생성")
    public void createLottoTest() {
        // Given
        final List<Integer> given = Arrays.asList(1, 2, 3, 4, 5, 6);

        // When
        Lotto lotto = Lotto.of(given);

        // Then
        assertAll(
            () -> assertThat(lotto.size()).isEqualTo(given.size())
        );
    }

    @Test
    @DisplayName("6개의 로또 번호를 입력하지 않는 경우 예외")
    public void throwException_WhenGivenNumberIsNotEnoughCount() {
        // Given
        final List<Integer> given = Arrays.asList(1, 2, 3);

        // When & Then
        assertThatExceptionOfType(IllegalArgumentException.class)
            .isThrownBy(() -> Lotto.of(given))
            .withMessageMatching(INVALID_LOTTO_NUMBER_COUNT_ERROR);
    }

    @Test
    @DisplayName("1~45 범위를 초과하는 숫자가 포함된 로또 객체 생성 시 예외")
    public void throwException_WhenGivenNumberIsInvalidRange() {
        // Given
        final List<Integer> given = Arrays.asList(1, 2, 3, 4, 5, 99);

        // When & Then
        assertThatExceptionOfType(IllegalArgumentException.class)
            .isThrownBy(() -> Lotto.of(given))
            .withMessageMatching(INVALID_LOTTO_NUMBER_RANGE_ERROR);
    }

    @Test
    @DisplayName("중복된 수를 포함한 로또 객체 생성 시 예외")
    public void throwException_WhenGivenNumberIsDuplicated() {
        // Given
        final List<Integer> given = Arrays.asList(1, 2, 3, 4, 6, 6);

        // When & Then
        assertThatExceptionOfType(IllegalArgumentException.class)
            .isThrownBy(() -> Lotto.of(given))
            .withMessageMatching(LOTTO_NUMBER_DUPLICATED_ERROR);
    }

    @ParameterizedTest
    @MethodSource
    @DisplayName("지난 주 로또 번호와 비교하여 반환된 당첨 여부 판별 객체 검증")
    public void matchTest(Winnings given, Lotto lotto, String givenDescription, MatchResult expected) {
        // When & Then
        assertThat(lotto.match(given)).as(givenDescription).isEqualTo(expected);
    }

    private static Stream matchTest() {
        final Lotto winningsLotto = Lotto.of(Arrays.asList(1, 2, 3, 4, 5, 6));
        final LottoNumber bonusNumber = LottoNumber.of(7);
        Winnings winnings = Winnings.of(winningsLotto, bonusNumber);
        return Stream.of(
            Arguments.of(winnings, Lotto.of(Arrays.asList(1, 2, 3, 4, 5, 6)), "6개 번호가 일치하는 1등 검증", MatchResult.FIRST_PLACE),
            Arguments.of(winnings, Lotto.of(Arrays.asList(1, 2, 3, 4, 5, 7)), "5개 번호와 보너스 번호가 일치하는 2등 검증", MatchResult.SECOND_PLACE),
            Arguments.of(winnings, Lotto.of(Arrays.asList(1, 2, 3, 4, 5, 8)), "5개 번호가 일치하는 3등 검증", MatchResult.THIRD_PLACE),
            Arguments.of(winnings, Lotto.of(Arrays.asList(1, 2, 3, 4, 7, 8)), "4개 번호가 일치하는 4등 검증", MatchResult.FORTH_PLACE),
            Arguments.of(winnings, Lotto.of(Arrays.asList(1, 2, 3, 7, 8, 9)), "3개 번호가 일치하는 5등 검증", MatchResult.FIFTH_PLACE),
            Arguments.of(winnings, Lotto.of(Arrays.asList(1, 2, 7, 8, 9, 10)), "2개 번호가 일치하는 당첨 없음 검증", MatchResult.NOTHING),
            Arguments.of(winnings, Lotto.of(Arrays.asList(1, 7, 8, 9, 10, 11)), "1개 번호가 일치하는 당첨 없음 검증", MatchResult.NOTHING),
            Arguments.of(winnings, Lotto.of(Arrays.asList(7, 8, 9, 10, 11, 12)), "0개 번호가 일치하는 당첨 없음 검증", MatchResult.NOTHING)
        );
    }
}
