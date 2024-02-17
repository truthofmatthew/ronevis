package fragments.ButtonBar;

public class Item {
    private String name;
    private String drawable;

    private Item() {
    }

    private Item(String name, String drawable) {
        this.drawable = drawable;
        this.name = name;
    }

    public static Item createItemBuilder() {
        return new Item();
    }

    public Item createItem() {
        return new Item(name, drawable);
    }

    public String getDrawable() {
        return drawable;
    }

    public Item setDrawable(String drawable) {
        this.drawable = drawable;
        return this;
    }

    public String getName() {
        return name;
    }

    public Item setName(String name) {
        this.name = name;
        return this;
    }
}