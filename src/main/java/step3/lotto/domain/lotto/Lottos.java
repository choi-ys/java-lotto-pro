package step3.lotto.domain.lotto;

import java.util.ArrayList;
import java.util.List;

/**
 * @author : choi-ys
 * @date : 2022/05/17 1:52 오후
 */
public class Lottos {

    private List<Lotto> lottos;

    public Lottos(List<Lotto> lottos) {
        this.lottos = lottos;
    }

    public List<Lotto> getLottos() {
        return new ArrayList<>(lottos);
    }

    public int getLottosSize() {
        return lottos.size();
    }

    public void addAll(Lottos lottos) {
        this.lottos.addAll(lottos.getLottos());
    }
}
