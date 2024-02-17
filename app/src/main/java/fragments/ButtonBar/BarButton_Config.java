package fragments.ButtonBar;

import java.io.Serializable;

import fragments.tool.Util;

public class BarButton_Config implements Serializable {
    private boolean btn_toggle = false;
    private boolean btn_checked = false;
    private String icon_text = null;
    private String icon_text_on = null;
    private String title_text = null;
    private String title_text_on = null;
    private int button_id = -1;

    private BarButton_Config() {
    }

    private BarButton_Config(boolean btn_toggle, boolean btn_checked, String icon_text, String icon_text_on, String title_text, String title_text_on, int button_id) {
        this.btn_toggle = btn_toggle;
        this.btn_checked = btn_checked;
        this.icon_text = icon_text;
        this.icon_text_on = icon_text_on;
        this.title_text = title_text;
        this.title_text_on = title_text_on;
        this.button_id = button_id;
    }

    private BarButton_Config(String icon_text, String title_text, int button_id) {
        this.icon_text = icon_text;
        this.title_text = title_text;
        this.button_id = button_id;
    }

    public static BarButton_Config createBarButton_Config_Builder() {
        return new BarButton_Config();
    }

    public BarButton_Config createBarButton_Toggle() {
        return new BarButton_Config(btn_toggle, btn_checked, icon_text, icon_text_on, title_text, title_text_on, button_id);
    }

    public BarButton_Config createBarButton_Simple() {
        return new BarButton_Config(icon_text, title_text, button_id);
    }

    public boolean isBtn_toggle() {
        return btn_toggle;
    }

    public BarButton_Config setBtn_toggle(boolean btn_toggle) {
        this.btn_toggle = btn_toggle;
        return this;
    }

    public boolean isBtn_checked() {
        return btn_checked;
    }

    public BarButton_Config setBtn_checked(boolean btn_checked) {
        this.btn_checked = btn_checked;
        return this;
    }

    public String getIcon_text() {
        return icon_text;
    }

    public BarButton_Config setIcon_text(int icon_text) {
        this.icon_text = Util.getString(icon_text);
        return this;
    }

    public String getIcon_text_on() {
        return icon_text_on;
    }

    public BarButton_Config setIcon_text_on(int icon_text_on) {
        this.icon_text_on = Util.getString(icon_text_on);
        return this;
    }

    public String getTitle_text() {
        return title_text;
    }

    public BarButton_Config setTitle_text(int title_text) {
        this.title_text = Util.getString(title_text);
        return this;
    }

    public String getTitle_text_on() {
        return title_text_on;
    }

    public BarButton_Config setTitle_text_on(int title_text_on) {
        this.title_text_on = Util.getString(title_text_on);
        return this;
    }

    public int getButton_id() {
        return button_id;
    }

    public BarButton_Config setButton_id(int button_id) {
        this.button_id = button_id;
        return this;
    }
}