package hello.itemservice.domain.item;

import lombok.Data;

@Data //는  위험하다 예측할 수 없기 때문에 @Setter, @Getter 정도 사용해주는게 좋다.
public class Item {

    private Long id;
    private String itemName;
    private Integer price; // int 는 null 불가, 객체인 Integer 사용
    private Integer quantity;

    public Item() {

    }

    public Item(String itemName, Integer price, Integer quantity) {
        this.itemName = itemName;
        this.price = price;
        this.quantity = quantity;
    }
}
