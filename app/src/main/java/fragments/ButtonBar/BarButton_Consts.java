package fragments.ButtonBar;

import java.util.ArrayList;

import mt.karimi.ronevis.R;

/**
 * Created by mt.karimi on 22/10/2016.
 * if you decompiled my application for any reason
 * let me know and we can be friend :)
 * email me at mtk.irib@gmail.com
 */
public class BarButton_Consts {
    private static ArrayList<BarButton_Config> items;

    public static ArrayList<BarButton_Config> createBGTools() {
        items = new ArrayList<>();
        items.clear();
        items.add(BarButton_Config.createBarButton_Config_Builder()
                .setIcon_text(R.string.Icon_folder_image)
                .setTitle_text(R.string.imgbgDialog)
                .setButton_id(R.id.btn_BG_Vitrin)
                .createBarButton_Simple());
        items.add(BarButton_Config.createBarButton_Config_Builder()
                .setIcon_text(R.string.Icon_import)
                .setTitle_text(R.string.imgviewselect)
                .setButton_id(R.id.btn_BG_Import)
                .createBarButton_Simple());
        items.add(BarButton_Config.createBarButton_Config_Builder()
                .setIcon_text(R.string.Icon_palette)
                .setTitle_text(R.string.userbgcolor)
                .setButton_id(R.id.btn_BG_Color)
                .createBarButton_Simple());
        items.add(BarButton_Config.createBarButton_Config_Builder()
                .setIcon_text(R.string.Icon_gradient)
                .setTitle_text(R.string.btnGradient)
                .setButton_id(R.id.btn_BG_Gradient)
                .createBarButton_Simple());
//        items.add(BarButton_Config.createBarButton_Config_Builder()
//                .setIcon_text(R.string.Icon_texture)
//                .setTitle_text(R.string.btnTexture)
//                .setButton_id(R.id.btn_BG_Texture)
//                .createBarButton_Simple());
        items.add(BarButton_Config.createBarButton_Config_Builder()
                .setIcon_text(R.string.Icon_opacity)
                .setTitle_text(R.string.btnBgOpacity)
                .setButton_id(R.id.btn_BG_Opacity)
                .createBarButton_Simple());
        items.add(BarButton_Config.createBarButton_Config_Builder()
                .setIcon_text(R.string.Icon_blur)
                .setTitle_text(R.string.btnBgBlur)
                .setButton_id(R.id.btn_BG_Blur)
                .createBarButton_Simple());
        items.add(BarButton_Config.createBarButton_Config_Builder()
                .setTitle_text(R.string.btnBGCrop)
                .setIcon_text(R.string.Icon_crop)
                .setButton_id(R.id.btn_BG_Size)
                .createBarButton_Simple());
        items.add(BarButton_Config.createBarButton_Config_Builder()
                .setTitle_text(R.string.btnBGImageCrop)
                .setIcon_text(R.string.Icon_crop_free)
                .setButton_id(R.id.btn_BG_Image_Crop)
                .createBarButton_Simple());
        items.add(BarButton_Config.createBarButton_Config_Builder()
                .setTitle_text(R.string.btndelbg)
                .setIcon_text(R.string.Icon_delete)
                .setButton_id(R.id.btn_BG_Delete)
                .createBarButton_Simple());
        return items;
    }

    public static ArrayList<BarButton_Config> createStickerTools() {
        items = new ArrayList<>();
        items.clear();
        items.add(BarButton_Config.createBarButton_Config_Builder()
                .setIcon_text(R.string.Icon_folder_image)
                .setTitle_text(R.string.imgStickerDialog)
                .setButton_id(R.id.btn_Sticker_Vitrin)
                .createBarButton_Simple());
        items.add(BarButton_Config.createBarButton_Config_Builder()
                .setIcon_text(R.string.Icon_import)
                .setTitle_text(R.string.imglogoselect)
                .setButton_id(R.id.btn_Sticker_Import)
                .createBarButton_Simple());
        items.add(BarButton_Config.createBarButton_Config_Builder()
                .setIcon_text(R.string.Icon_arrow_expand)
                .setTitle_text(R.string.btnLogoExpand)
                .setButton_id(R.id.btn_Sticker_Expand)
                .createBarButton_Simple());
        items.add(BarButton_Config.createBarButton_Config_Builder()
                .setTitle_text(R.string.btnRotationLogo)
                .setIcon_text(R.string.Icon_sync)
                .setButton_id(R.id.btn_Sticker_Rotation)
                .createBarButton_Simple());
        items.add(BarButton_Config.createBarButton_Config_Builder()
                .setTitle_text(R.string.btnarrows)
                .setIcon_text(R.string.Icon_arrow_all)
                .setButton_id(R.id.btn_Sticker_Position)
                .createBarButton_Simple());
        items.add(BarButton_Config.createBarButton_Config_Builder()
                .setIcon_text(R.string.Icon_opacity)
                .setTitle_text(R.string.btnOpacityLogo)
                .setButton_id(R.id.btn_Sticker_Opacity)
                .createBarButton_Simple());
        items.add(BarButton_Config.createBarButton_Config_Builder()
                .setIcon_text(R.string.Icon_content_duplicate)
                .setTitle_text(R.string.btnimageduplicate)
                .setButton_id(R.id.btn_Sticker_Duplicate)
                .createBarButton_Simple());
        items.add(BarButton_Config.createBarButton_Config_Builder()
                .setTitle_text(R.string.imgdelST)
                .setIcon_text(R.string.Icon_delete)
                .setButton_id(R.id.btn_Sticker_Delete)
                .createBarButton_Simple());
        return items;
    }

    public static ArrayList<BarButton_Config> createTextTools() {
        items = new ArrayList<>();
        items.clear();
        items.add(BarButton_Config.createBarButton_Config_Builder()
                .setBtn_toggle(true)
                .setBtn_checked(false)
                .setIcon_text(R.string.Icon_pencil_box_outline)
                .setIcon_text_on(R.string.Icon_pencil_box)
                .setTitle_text(R.string.btnaddtext)
                .setTitle_text_on(R.string.btnedittext)
                .setButton_id(R.id.btn_Text_Add)
                .createBarButton_Toggle());
        items.add(BarButton_Config.createBarButton_Config_Builder()
                .setIcon_text(R.string.Icon_format_title)
                .setTitle_text(R.string.btntextfont)
                .setButton_id(R.id.btn_Text_Font)
                .createBarButton_Simple());
        items.add(BarButton_Config.createBarButton_Config_Builder()
                .setIcon_text(R.string.Icon_format_size)
                .setTitle_text(R.string.btnSize)
                .setButton_id(R.id.btn_Text_Size)
                .createBarButton_Simple());
        items.add(BarButton_Config.createBarButton_Config_Builder()
                .setIcon_text(R.string.Icon_Textbox)
                .setTitle_text(R.string.btnCrop)
                .setButton_id(R.id.btn_Text_Crop)
                .createBarButton_Simple());
        items.add(BarButton_Config.createBarButton_Config_Builder()
                .setIcon_text(R.string.Icon_text_shadow)
                .setTitle_text(R.string.btntextshadow)
                .setButton_id(R.id.btn_Text_Shadow)
                .createBarButton_Simple());
        items.add(BarButton_Config.createBarButton_Config_Builder()
                .setIcon_text(R.string.Icon_palette)
                .setTitle_text(R.string.userColorPallete)
                .setButton_id(R.id.btn_Text_Color)
                .createBarButton_Simple());
//        items.add(BarButton_Config.createBarButton_Config_Builder()
//                .setIcon_text(R.string.Icon_format_marker)
//                .setTitle_text(R.string.textColorBackGround)
//                .setButton_id(R.id.btn_Text_BG_Color)
//                .createBarButton_Simple());
        items.add(BarButton_Config.createBarButton_Config_Builder()
                .setIcon_text(R.string.Icon_gradient)
                .setTitle_text(R.string.btnGradient)
                .setButton_id(R.id.btn_Text_Gradient)
                .createBarButton_Simple());
        items.add(BarButton_Config.createBarButton_Config_Builder()
                .setIcon_text(R.string.Icon_format_line_weight)
                .setTitle_text(R.string.btnStroke)
                .setButton_id(R.id.btn_Text_Stroke)
                .createBarButton_Simple());
//        items.add(BarButton_Config.createBarButton_Config_Builder()
//                .setIcon_text(R.string.Icon_texture)
//                .setTitle_text(R.string.btnTexture)
//                .setButton_id(R.id.btn_Text_Texture)
//                .createBarButton_Simple());
        items.add(BarButton_Config.createBarButton_Config_Builder()
                .setIcon_text(R.string.Icon_opacity)
                .setTitle_text(R.string.btnOpacity)
                .setButton_id(R.id.btn_Text_Opacity)
                .createBarButton_Simple());
        items.add(BarButton_Config.createBarButton_Config_Builder()
                .setTitle_text(R.string.btntextalign)
                .setIcon_text(R.string.Icon_format_align_center)
                .setButton_id(R.id.btn_Text_Align)
                .createBarButton_Simple());
        items.add(BarButton_Config.createBarButton_Config_Builder()
                .setBtn_toggle(true)
                .setBtn_checked(false)
                .setIcon_text(R.string.Icon_format_bold)
                .setIcon_text_on(R.string.Icon_format_bold)
                .setTitle_text(R.string.btntextbold)
                .setTitle_text_on(R.string.btntextbold)
                .setButton_id(R.id.btn_Text_Bold)
                .createBarButton_Toggle());
        items.add(BarButton_Config.createBarButton_Config_Builder()
                .setBtn_toggle(true)
                .setBtn_checked(false)
                .setIcon_text(R.string.Icon_format_italic)
                .setIcon_text_on(R.string.Icon_format_italic)
                .setTitle_text(R.string.btntextitalic)
                .setTitle_text_on(R.string.btntextitalic)
                .setButton_id(R.id.btn_Text_Italic)
                .createBarButton_Toggle());
        items.add(BarButton_Config.createBarButton_Config_Builder()
                .setBtn_toggle(true)
                .setBtn_checked(false)
                .setIcon_text(R.string.Icon_format_strikethrough)
                .setIcon_text_on(R.string.Icon_format_strikethrough)
                .setTitle_text(R.string.btntextstrikethrough)
                .setTitle_text_on(R.string.btntextstrikethrough)
                .setButton_id(R.id.btn_Text_Strikethrough)
                .createBarButton_Toggle());
        items.add(BarButton_Config.createBarButton_Config_Builder()
                .setBtn_toggle(true)
                .setBtn_checked(false)
                .setIcon_text(R.string.Icon_format_underline)
                .setIcon_text_on(R.string.Icon_format_underline)
                .setTitle_text(R.string.btntextunderline)
                .setTitle_text_on(R.string.btntextunderline)
                .setButton_id(R.id.btn_Text_Underline)
                .createBarButton_Toggle());
        items.add(BarButton_Config.createBarButton_Config_Builder()
                .setTitle_text(R.string.btnLinespacing)
                .setIcon_text(R.string.Icon_format_line_spacing)
                .setButton_id(R.id.btn_Text_LineSpace)
                .createBarButton_Simple());
        items.add(BarButton_Config.createBarButton_Config_Builder()
                .setTitle_text(R.string.btnrotation)
                .setIcon_text(R.string.Icon_sync)
                .setButton_id(R.id.btn_Text_Rotation)
                .createBarButton_Simple());
        items.add(BarButton_Config.createBarButton_Config_Builder()
                .setTitle_text(R.string.btnarrows)
                .setIcon_text(R.string.Icon_arrow_all)
                .setButton_id(R.id.btn_Text_Position)
                .createBarButton_Simple());
        items.add(BarButton_Config.createBarButton_Config_Builder()
                .setTitle_text(R.string.btntextduplicate)
                .setIcon_text(R.string.Icon_content_duplicate)
                .setButton_id(R.id.btn_Text_Duplicate)
                .createBarButton_Simple());
        items.add(BarButton_Config.createBarButton_Config_Builder()
                .setBtn_toggle(true)
                .setBtn_checked(false)
                .setIcon_text(R.string.Icon_lock_open_outline)
                .setIcon_text_on(R.string.Icon_lock_outline)
                .setTitle_text(R.string.btntextLock)
                .setTitle_text_on(R.string.btntextLock)
                .setButton_id(R.id.btn_Text_Lock)
                .createBarButton_Toggle());
        items.add(BarButton_Config.createBarButton_Config_Builder()
                .setTitle_text(R.string.btndeltxt)
                .setIcon_text(R.string.Icon_delete)
                .setButton_id(R.id.btn_Text_Delete)
                .createBarButton_Simple());
        return items;
    }

    public static ArrayList<BarButton_Config> createTools() {
        items = new ArrayList<>();
        items.clear();
        items.add(BarButton_Config.createBarButton_Config_Builder()
                .setIcon_text(R.string.Icon_new_box)
                .setTitle_text(R.string.menunew)
                .setButton_id(R.id.menunew)
                .createBarButton_Simple());
        items.add(BarButton_Config.createBarButton_Config_Builder()
                .setTitle_text(R.string.menushare)
                .setIcon_text(R.string.Icon_share_variant)
                .setButton_id(R.id.menushare)
                .createBarButton_Simple());
        items.add(BarButton_Config.createBarButton_Config_Builder()
                .setTitle_text(R.string.menushop)
                .setIcon_text(R.string.Icon_shopping)
                .setButton_id(R.id.menushop)
                .createBarButton_Simple());
//        items.add(BarButton_Config.createBarButton_Config_Builder()
//                .setIcon_text(R.string.Icon_file_export)
//                .setTitle_text(R.string.menusaveFile)
//                .setButton_id(R.id.menusaveFile)
//                .createBarButton_Simple());
        items.add(BarButton_Config.createBarButton_Config_Builder()
                .setIcon_text(R.string.Icon_file_import)
                .setTitle_text(R.string.menuOpenFile)
                .setButton_id(R.id.menuOpenFile)
                .createBarButton_Simple());
        items.add(BarButton_Config.createBarButton_Config_Builder()
                .setIcon_text(R.string.Icon_information)
                .setTitle_text(R.string.menuabout)
                .setButton_id(R.id.menuabout)
                .createBarButton_Simple());
        items.add(BarButton_Config.createBarButton_Config_Builder()
                .setIcon_text(R.string.Icon_help_circle)
                .setTitle_text(R.string.menutrain)
                .setButton_id(R.id.menutrain)
                .createBarButton_Simple());
        return items;
    }
}
