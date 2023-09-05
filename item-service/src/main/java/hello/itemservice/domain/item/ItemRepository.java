package hello.itemservice.domain.item;

// 항상 import 는 java.util 로 해야된다.
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class ItemRepository {

    private static final Map<Long, Item> store = new HashMap<>(); // static
    private static long sequence = 0L; // static

    public Item save(Item item) {
        item.setId(++sequence);
        store.put(item.getId(), item); // item.getId() = getter, setter 에서 getId 메서드 이용
        return item;
    }

    public Item findById(Long id) {
        return store.get(id);
    }

    public List<Item> findAll() {
        return new ArrayList<>(store.values());
    }

    public void update(Long itemId, Item updateParam) { // 정석은 ItemParamDTO dto 이렇게 가는게 맞다, Command Object 을 만들어라
        Item findItem = findById(itemId);// 아이템 itemId 로 찾기
        findItem.setItemName(updateParam.getItemName());// updateParam 정보가 넘어오는 거다.
        findItem.setPrice(updateParam.getPrice());
        findItem.setQuantity(updateParam.getQuantity());
    }

    public void clearStore() {
        store.clear();
    }
}
