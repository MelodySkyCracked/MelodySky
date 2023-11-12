/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.swt.internal.win32;

import java.util.Objects;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.internal.C;
import org.eclipse.swt.internal.Library;
import org.eclipse.swt.internal.win32.ACCEL;
import org.eclipse.swt.internal.win32.ACTCTX;
import org.eclipse.swt.internal.win32.BITMAP;
import org.eclipse.swt.internal.win32.BITMAPINFOHEADER;
import org.eclipse.swt.internal.win32.BLENDFUNCTION;
import org.eclipse.swt.internal.win32.BP_PAINTPARAMS;
import org.eclipse.swt.internal.win32.BUTTON_IMAGELIST;
import org.eclipse.swt.internal.win32.CANDIDATEFORM;
import org.eclipse.swt.internal.win32.CHOOSECOLOR;
import org.eclipse.swt.internal.win32.CHOOSEFONT;
import org.eclipse.swt.internal.win32.CIDA;
import org.eclipse.swt.internal.win32.COMBOBOXINFO;
import org.eclipse.swt.internal.win32.COMPOSITIONFORM;
import org.eclipse.swt.internal.win32.CREATESTRUCT;
import org.eclipse.swt.internal.win32.DEVMODE;
import org.eclipse.swt.internal.win32.DIBSECTION;
import org.eclipse.swt.internal.win32.DOCHOSTUIINFO;
import org.eclipse.swt.internal.win32.DOCINFO;
import org.eclipse.swt.internal.win32.DRAWITEMSTRUCT;
import org.eclipse.swt.internal.win32.DROPFILES;
import org.eclipse.swt.internal.win32.EMR;
import org.eclipse.swt.internal.win32.EMREXTCREATEFONTINDIRECTW;
import org.eclipse.swt.internal.win32.FLICK_DATA;
import org.eclipse.swt.internal.win32.FLICK_POINT;
import org.eclipse.swt.internal.win32.GCP_RESULTS;
import org.eclipse.swt.internal.win32.GESTURECONFIG;
import org.eclipse.swt.internal.win32.GESTUREINFO;
import org.eclipse.swt.internal.win32.GRADIENT_RECT;
import org.eclipse.swt.internal.win32.GUITHREADINFO;
import org.eclipse.swt.internal.win32.HDHITTESTINFO;
import org.eclipse.swt.internal.win32.HDITEM;
import org.eclipse.swt.internal.win32.HDLAYOUT;
import org.eclipse.swt.internal.win32.HELPINFO;
import org.eclipse.swt.internal.win32.HIGHCONTRAST;
import org.eclipse.swt.internal.win32.ICONINFO;
import org.eclipse.swt.internal.win32.INITCOMMONCONTROLSEX;
import org.eclipse.swt.internal.win32.INPUT;
import org.eclipse.swt.internal.win32.LITEM;
import org.eclipse.swt.internal.win32.LOGBRUSH;
import org.eclipse.swt.internal.win32.LOGFONT;
import org.eclipse.swt.internal.win32.LVCOLUMN;
import org.eclipse.swt.internal.win32.LVHITTESTINFO;
import org.eclipse.swt.internal.win32.LVINSERTMARK;
import org.eclipse.swt.internal.win32.LVITEM;
import org.eclipse.swt.internal.win32.MARGINS;
import org.eclipse.swt.internal.win32.MCHITTESTINFO;
import org.eclipse.swt.internal.win32.MEASUREITEMSTRUCT;
import org.eclipse.swt.internal.win32.MENUBARINFO;
import org.eclipse.swt.internal.win32.MENUINFO;
import org.eclipse.swt.internal.win32.MENUITEMINFO;
import org.eclipse.swt.internal.win32.MINMAXINFO;
import org.eclipse.swt.internal.win32.MONITORINFO;
import org.eclipse.swt.internal.win32.MSG;
import org.eclipse.swt.internal.win32.NMCUSTOMDRAW;
import org.eclipse.swt.internal.win32.NMHDR;
import org.eclipse.swt.internal.win32.NMHEADER;
import org.eclipse.swt.internal.win32.NMLINK;
import org.eclipse.swt.internal.win32.NMLISTVIEW;
import org.eclipse.swt.internal.win32.NMLVCUSTOMDRAW;
import org.eclipse.swt.internal.win32.NMLVDISPINFO;
import org.eclipse.swt.internal.win32.NMLVODSTATECHANGE;
import org.eclipse.swt.internal.win32.NMREBARCHEVRON;
import org.eclipse.swt.internal.win32.NMREBARCHILDSIZE;
import org.eclipse.swt.internal.win32.NMTBCUSTOMDRAW;
import org.eclipse.swt.internal.win32.NMTBHOTITEM;
import org.eclipse.swt.internal.win32.NMTOOLBAR;
import org.eclipse.swt.internal.win32.NMTREEVIEW;
import org.eclipse.swt.internal.win32.NMTTCUSTOMDRAW;
import org.eclipse.swt.internal.win32.NMTTDISPINFO;
import org.eclipse.swt.internal.win32.NMTVCUSTOMDRAW;
import org.eclipse.swt.internal.win32.NMTVDISPINFO;
import org.eclipse.swt.internal.win32.NMTVITEMCHANGE;
import org.eclipse.swt.internal.win32.NMUPDOWN;
import org.eclipse.swt.internal.win32.NONCLIENTMETRICS;
import org.eclipse.swt.internal.win32.NOTIFYICONDATA;
import org.eclipse.swt.internal.win32.OSVERSIONINFOEX;
import org.eclipse.swt.internal.win32.OUTLINETEXTMETRIC;
import org.eclipse.swt.internal.win32.PAINTSTRUCT;
import org.eclipse.swt.internal.win32.POINT;
import org.eclipse.swt.internal.win32.PRINTDLG;
import org.eclipse.swt.internal.win32.PROCESS_INFORMATION;
import org.eclipse.swt.internal.win32.PROPERTYKEY;
import org.eclipse.swt.internal.win32.REBARBANDINFO;
import org.eclipse.swt.internal.win32.RECT;
import org.eclipse.swt.internal.win32.SAFEARRAY;
import org.eclipse.swt.internal.win32.SCRIPT_ANALYSIS;
import org.eclipse.swt.internal.win32.SCRIPT_CONTROL;
import org.eclipse.swt.internal.win32.SCRIPT_FONTPROPERTIES;
import org.eclipse.swt.internal.win32.SCRIPT_ITEM;
import org.eclipse.swt.internal.win32.SCRIPT_LOGATTR;
import org.eclipse.swt.internal.win32.SCRIPT_PROPERTIES;
import org.eclipse.swt.internal.win32.SCRIPT_STATE;
import org.eclipse.swt.internal.win32.SCROLLBARINFO;
import org.eclipse.swt.internal.win32.SCROLLINFO;
import org.eclipse.swt.internal.win32.SHDRAGIMAGE;
import org.eclipse.swt.internal.win32.SHELLEXECUTEINFO;
import org.eclipse.swt.internal.win32.SHFILEINFO;
import org.eclipse.swt.internal.win32.SIZE;
import org.eclipse.swt.internal.win32.STARTUPINFO;
import org.eclipse.swt.internal.win32.SYSTEMTIME;
import org.eclipse.swt.internal.win32.TBBUTTON;
import org.eclipse.swt.internal.win32.TBBUTTONINFO;
import org.eclipse.swt.internal.win32.TCHAR;
import org.eclipse.swt.internal.win32.TCHITTESTINFO;
import org.eclipse.swt.internal.win32.TCITEM;
import org.eclipse.swt.internal.win32.TEXTMETRIC;
import org.eclipse.swt.internal.win32.TOOLINFO;
import org.eclipse.swt.internal.win32.TOUCHINPUT;
import org.eclipse.swt.internal.win32.TRACKMOUSEEVENT;
import org.eclipse.swt.internal.win32.TRIVERTEX;
import org.eclipse.swt.internal.win32.TVHITTESTINFO;
import org.eclipse.swt.internal.win32.TVINSERTSTRUCT;
import org.eclipse.swt.internal.win32.TVITEM;
import org.eclipse.swt.internal.win32.TVSORTCB;
import org.eclipse.swt.internal.win32.UDACCEL;
import org.eclipse.swt.internal.win32.WINDOWPLACEMENT;
import org.eclipse.swt.internal.win32.WINDOWPOS;
import org.eclipse.swt.internal.win32.WNDCLASS;
import org.eclipse.swt.widgets.Display;

public class OS
extends C {
    public static final boolean IsDBLocale;
    public static final int WIN32_VERSION;
    public static final int WIN32_BUILD;
    public static final int WIN32_BUILD_WIN10_1809 = 17763;
    public static final int WIN32_BUILD_WIN10_2004 = 19041;
    public static final int WIN32_BUILD_WIN11_21H2 = 22000;
    public static final String NO_MANIFEST = "org.eclipse.swt.internal.win32.OS.NO_MANIFEST";
    public static final int ACTCTX_FLAG_RESOURCE_NAME_VALID = 8;
    public static final int ACTCTX_FLAG_SET_PROCESS_DEFAULT = 16;
    public static final int ACTCTX_FLAG_HMODULE_VALID = 128;
    public static final int MANIFEST_RESOURCE_ID = 2;
    public static final int SM_IMMENABLED = 82;
    public static final int ABS_DOWNDISABLED = 8;
    public static final int ABS_DOWNHOT = 6;
    public static final int ABS_DOWNNORMAL = 5;
    public static final int ABS_DOWNPRESSED = 7;
    public static final int ABS_LEFTDISABLED = 12;
    public static final int ABS_LEFTHOT = 10;
    public static final int ABS_LEFTNORMAL = 9;
    public static final int ABS_LEFTPRESSED = 11;
    public static final int ABS_RIGHTDISABLED = 16;
    public static final int ABS_RIGHTHOT = 14;
    public static final int ABS_RIGHTNORMAL = 13;
    public static final int ABS_RIGHTPRESSED = 15;
    public static final int ABS_UPDISABLED = 4;
    public static final int ABS_UPHOT = 2;
    public static final int ABS_UPNORMAL = 1;
    public static final int ABS_UPPRESSED = 3;
    public static final int AC_SRC_OVER = 0;
    public static final int AC_SRC_ALPHA = 1;
    public static final int ALTERNATE = 1;
    public static final int ASSOCF_NOTRUNCATE = 32;
    public static final int ASSOCF_INIT_IGNOREUNKNOWN = 1024;
    public static final int ASSOCSTR_COMMAND = 1;
    public static final int ASSOCSTR_DEFAULTICON = 15;
    public static final int ASSOCSTR_FRIENDLYAPPNAME = 4;
    public static final int ASSOCSTR_FRIENDLYDOCNAME = 3;
    public static final int ATTR_INPUT = 0;
    public static final int ATTR_TARGET_CONVERTED = 1;
    public static final int ATTR_CONVERTED = 2;
    public static final int ATTR_TARGET_NOTCONVERTED = 3;
    public static final int ATTR_INPUT_ERROR = 4;
    public static final int ATTR_FIXEDCONVERTED = 5;
    public static final int BCM_FIRST = 5632;
    public static final int BCM_GETIDEALSIZE = 5633;
    public static final int BCM_GETIMAGELIST = 5635;
    public static final int BCM_GETNOTE = 5642;
    public static final int BCM_GETNOTELENGTH = 5643;
    public static final int BCM_SETIMAGELIST = 5634;
    public static final int BCM_SETNOTE = 5641;
    public static final int BDR_SUNKENINNER = 8;
    public static final int BF_LEFT = 1;
    public static final int BF_TOP = 2;
    public static final int BF_RIGHT = 4;
    public static final int BF_BOTTOM = 8;
    public static final int BITSPIXEL = 12;
    public static final int BI_BITFIELDS = 3;
    public static final int BI_RGB = 0;
    public static final int BLACKNESS = 66;
    public static final int BLACK_BRUSH = 4;
    public static final int BUTTON_IMAGELIST_ALIGN_LEFT = 0;
    public static final int BUTTON_IMAGELIST_ALIGN_RIGHT = 1;
    public static final int BUTTON_IMAGELIST_ALIGN_CENTER = 4;
    public static final int BM_CLICK = 245;
    public static final int BM_GETCHECK = 240;
    public static final int BM_SETCHECK = 241;
    public static final int BM_SETIMAGE = 247;
    public static final int BM_SETSTYLE = 244;
    public static final int BN_CLICKED = 0;
    public static final int BN_DOUBLECLICKED = 5;
    public static final int BPBF_COMPATIBLEBITMAP = 0;
    public static final int BP_PUSHBUTTON = 1;
    public static final int BP_RADIOBUTTON = 2;
    public static final int BP_CHECKBOX = 3;
    public static final int BP_GROUPBOX = 4;
    public static final int BST_CHECKED = 1;
    public static final int BST_INDETERMINATE = 2;
    public static final int BST_UNCHECKED = 0;
    public static final int BS_3STATE = 5;
    public static final int BS_BITMAP = 128;
    public static final int BS_CENTER = 768;
    public static final int BS_CHECKBOX = 2;
    public static final int BS_COMMANDLINK = 14;
    public static final int BS_DEFPUSHBUTTON = 1;
    public static final int BS_FLAT = 32768;
    public static final int BS_GROUPBOX = 7;
    public static final int BS_ICON = 64;
    public static final int BS_LEFT = 256;
    public static final int BS_MULTILINE = 8192;
    public static final int BS_NOTIFY = 16384;
    public static final int BS_OWNERDRAW = 11;
    public static final int BS_PATTERN = 3;
    public static final int BS_PUSHBUTTON = 0;
    public static final int BS_PUSHLIKE = 4096;
    public static final int BS_RADIOBUTTON = 4;
    public static final int BS_RIGHT = 512;
    public static final int BS_SOLID = 0;
    public static final int BTNS_AUTOSIZE = 16;
    public static final int BTNS_BUTTON = 0;
    public static final int BTNS_CHECK = 2;
    public static final int BTNS_CHECKGROUP = 6;
    public static final int BTNS_DROPDOWN = 8;
    public static final int BTNS_GROUP = 4;
    public static final int BTNS_SEP = 1;
    public static final int BTNS_SHOWTEXT = 64;
    public static final int CBN_DROPDOWN = 7;
    public static final int CBN_EDITCHANGE = 5;
    public static final int CBN_KILLFOCUS = 4;
    public static final int CBN_SELCHANGE = 1;
    public static final int CBN_SETFOCUS = 3;
    public static final int CBS_AUTOHSCROLL = 64;
    public static final int CBS_DROPDOWN = 2;
    public static final int CBS_DROPDOWNLIST = 3;
    public static final int CBS_CHECKEDNORMAL = 5;
    public static final int CBS_MIXEDNORMAL = 9;
    public static final int CBS_NOINTEGRALHEIGHT = 1024;
    public static final int CBS_SIMPLE = 1;
    public static final int CBS_UNCHECKEDNORMAL = 1;
    public static final int CBS_CHECKEDDISABLED = 8;
    public static final int CBS_CHECKEDHOT = 6;
    public static final int CBS_CHECKEDPRESSED = 7;
    public static final int CBS_MIXEDDISABLED = 12;
    public static final int CBS_MIXEDHOT = 10;
    public static final int CBS_MIXEDPRESSED = 11;
    public static final int CBS_UNCHECKEDDISABLED = 4;
    public static final int CBS_UNCHECKEDHOT = 2;
    public static final int CBS_UNCHECKEDPRESSED = 3;
    public static final int CB_ADDSTRING = 323;
    public static final int CB_DELETESTRING = 324;
    public static final int CB_ERR = -1;
    public static final int CB_ERRSPACE = -2;
    public static final int CB_FINDSTRINGEXACT = 344;
    public static final int CB_GETCOUNT = 326;
    public static final int CB_GETCURSEL = 327;
    public static final int CB_GETDROPPEDCONTROLRECT = 338;
    public static final int CB_GETDROPPEDSTATE = 343;
    public static final int CB_GETDROPPEDWIDTH = 351;
    public static final int CB_GETEDITSEL = 320;
    public static final int CB_GETHORIZONTALEXTENT = 349;
    public static final int CB_GETITEMHEIGHT = 340;
    public static final int CB_GETLBTEXT = 328;
    public static final int CB_GETLBTEXTLEN = 329;
    public static final int CB_INSERTSTRING = 330;
    public static final int CB_LIMITTEXT = 321;
    public static final int CB_RESETCONTENT = 331;
    public static final int CB_SELECTSTRING = 333;
    public static final int CB_SETCURSEL = 334;
    public static final int CB_SETDROPPEDWIDTH = 352;
    public static final int CB_SETEDITSEL = 322;
    public static final int CB_SETHORIZONTALEXTENT = 350;
    public static final int CB_SETITEMHEIGHT = 339;
    public static final int CB_SHOWDROPDOWN = 335;
    public static final int CCHDEVICENAME = 32;
    public static final int CCHFORMNAME = 32;
    public static final int CCHILDREN_SCROLLBAR = 5;
    public static final int CCS_NODIVIDER = 64;
    public static final int CCS_NORESIZE = 4;
    public static final int CCS_VERT = 128;
    public static final int CC_ANYCOLOR = 256;
    public static final int CC_ENABLEHOOK = 16;
    public static final int CC_FULLOPEN = 2;
    public static final int CC_RGBINIT = 1;
    public static final int CDDS_POSTERASE = 4;
    public static final int CDDS_POSTPAINT = 2;
    public static final int CDDS_PREERASE = 3;
    public static final int CDDS_PREPAINT = 1;
    public static final int CDDS_ITEM = 65536;
    public static final int CDDS_ITEMPOSTPAINT = 65538;
    public static final int CDDS_ITEMPREPAINT = 65537;
    public static final int CDDS_SUBITEM = 131072;
    public static final int CDDS_SUBITEMPOSTPAINT = 196610;
    public static final int CDDS_SUBITEMPREPAINT = 196609;
    public static final int CDIS_SELECTED = 1;
    public static final int CDIS_GRAYED = 2;
    public static final int CDIS_DISABLED = 4;
    public static final int CDIS_CHECKED = 8;
    public static final int CDIS_FOCUS = 16;
    public static final int CDIS_DEFAULT = 32;
    public static final int CDIS_HOT = 64;
    public static final int CDIS_MARKED = 128;
    public static final int CDIS_INDETERMINATE = 256;
    public static final int CDIS_SHOWKEYBOARDCUES = 512;
    public static final int CDIS_DROPHILITED = 4096;
    public static final int CDM_FIRST = 1124;
    public static final int CDM_GETSPEC = 1124;
    public static final int CDN_FIRST = -601;
    public static final int CDN_SELCHANGE = -602;
    public static final int CDRF_DODEFAULT = 0;
    public static final int CDRF_DOERASE = 8;
    public static final int CDRF_NEWFONT = 2;
    public static final int CDRF_NOTIFYITEMDRAW = 32;
    public static final int CDRF_NOTIFYPOSTERASE = 64;
    public static final int CDRF_NOTIFYPOSTPAINT = 16;
    public static final int CDRF_NOTIFYSUBITEMDRAW = 32;
    public static final int CDRF_SKIPDEFAULT = 4;
    public static final int CDRF_SKIPPOSTPAINT = 256;
    public static final int CFS_RECT = 1;
    public static final int CFS_EXCLUDE = 128;
    public static final int CF_EFFECTS = 256;
    public static final int CF_INITTOLOGFONTSTRUCT = 64;
    public static final int CF_SCREENFONTS = 1;
    public static final int CF_TEXT = 1;
    public static final int CF_UNICODETEXT = 13;
    public static final int CF_USESTYLE = 128;
    public static final int CLR_DEFAULT = -16777216;
    public static final int CLR_INVALID = -1;
    public static final int CLR_NONE = -1;
    public static final int COLORONCOLOR = 3;
    public static final int COLOR_3DDKSHADOW = 21;
    public static final int COLOR_3DFACE = 15;
    public static final int COLOR_3DHIGHLIGHT = 20;
    public static final int COLOR_3DHILIGHT = 20;
    public static final int COLOR_3DLIGHT = 22;
    public static final int COLOR_3DSHADOW = 16;
    public static final int COLOR_ACTIVECAPTION = 2;
    public static final int COLOR_BTNFACE = 15;
    public static final int COLOR_BTNHIGHLIGHT = 20;
    public static final int COLOR_BTNSHADOW = 16;
    public static final int COLOR_BTNTEXT = 18;
    public static final int COLOR_CAPTIONTEXT = 9;
    public static final int COLOR_GRADIENTACTIVECAPTION = 27;
    public static final int COLOR_GRADIENTINACTIVECAPTION = 28;
    public static final int COLOR_GRAYTEXT = 17;
    public static final int COLOR_HIGHLIGHT = 13;
    public static final int COLOR_HIGHLIGHTTEXT = 14;
    public static final int COLOR_HOTLIGHT = 26;
    public static final int COLOR_INACTIVECAPTION = 3;
    public static final int COLOR_INACTIVECAPTIONTEXT = 19;
    public static final int COLOR_INFOBK = 24;
    public static final int COLOR_INFOTEXT = 23;
    public static final int COLOR_MENU = 4;
    public static final int COLOR_MENUTEXT = 7;
    public static final int COLOR_SCROLLBAR = 0;
    public static final int COLOR_WINDOW = 5;
    public static final int COLOR_WINDOWFRAME = 6;
    public static final int COLOR_WINDOWTEXT = 8;
    public static final int COMPLEXREGION = 3;
    public static final int CP_ACP = 0;
    public static final int CP_UTF8 = 65001;
    public static final int CP_DROPDOWNBUTTON = 1;
    public static final int CPS_COMPLETE = 1;
    public static final int CS_DBLCLKS = 8;
    public static final int CS_DROPSHADOW = 131072;
    public static final int CS_GLOBALCLASS = 16384;
    public static final int CS_HREDRAW = 2;
    public static final int CS_VREDRAW = 1;
    public static final int CS_OWNDC = 32;
    public static final int CW_USEDEFAULT = Integer.MIN_VALUE;
    public static final int CWP_SKIPINVISIBLE = 1;
    public static final String DATETIMEPICK_CLASS = "SysDateTimePick32";
    public static final int DC_BRUSH = 18;
    public static final int DCX_CACHE = 2;
    public static final int DEFAULT_CHARSET = 1;
    public static final int DEFAULT_GUI_FONT = 17;
    public static final int DFCS_BUTTONCHECK = 0;
    public static final int DFCS_CHECKED = 1024;
    public static final int DFCS_FLAT = 16384;
    public static final int DFCS_INACTIVE = 256;
    public static final int DFCS_PUSHED = 512;
    public static final int DFCS_SCROLLDOWN = 1;
    public static final int DFCS_SCROLLLEFT = 2;
    public static final int DFCS_SCROLLRIGHT = 3;
    public static final int DFCS_SCROLLUP = 0;
    public static final int DFC_BUTTON = 4;
    public static final int DFC_SCROLL = 3;
    public static final int DIB_RGB_COLORS = 0;
    public static final int DI_NORMAL = 3;
    public static final int DI_NOMIRROR = 16;
    public static final int DLGC_BUTTON = 8192;
    public static final int DLGC_HASSETSEL = 8;
    public static final int DLGC_STATIC = 256;
    public static final int DLGC_WANTALLKEYS = 4;
    public static final int DLGC_WANTARROWS = 1;
    public static final int DLGC_WANTCHARS = 128;
    public static final int DLGC_WANTTAB = 2;
    public static final short DMCOLLATE_FALSE = 0;
    public static final short DMCOLLATE_TRUE = 1;
    public static final int DM_SETDEFID = 1025;
    public static final int DM_COLLATE = 32768;
    public static final int DM_COPIES = 256;
    public static final int DM_DUPLEX = 4096;
    public static final int DM_ORIENTATION = 1;
    public static final int DM_OUT_BUFFER = 2;
    public static final short DMORIENT_PORTRAIT = 1;
    public static final short DMORIENT_LANDSCAPE = 2;
    public static final short DMDUP_SIMPLEX = 1;
    public static final short DMDUP_VERTICAL = 2;
    public static final short DMDUP_HORIZONTAL = 3;
    public static final int DSTINVERT = 0x550009;
    public static final int DT_BOTTOM = 8;
    public static final int DT_CALCRECT = 1024;
    public static final int DT_CENTER = 1;
    public static final int DT_EDITCONTROL = 8192;
    public static final int DT_EXPANDTABS = 64;
    public static final int DT_ENDELLIPSIS = 32768;
    public static final int DT_HIDEPREFIX = 0x100000;
    public static final int DT_LEFT = 0;
    public static final int DT_NOPREFIX = 2048;
    public static final int DT_RASPRINTER = 2;
    public static final int DT_RIGHT = 2;
    public static final int DT_RTLREADING = 131072;
    public static final int DT_SINGLELINE = 32;
    public static final int DT_TOP = 0;
    public static final int DT_VCENTER = 4;
    public static final int DT_WORDBREAK = 16;
    public static final int DTM_FIRST = 4096;
    public static final int DTM_GETSYSTEMTIME = 4097;
    public static final int DTM_SETMCSTYLE = 4107;
    public static final int DTM_GETIDEALSIZE = 4111;
    public static final int DTM_SETFORMAT = 4146;
    public static final int DTM_SETSYSTEMTIME = 4098;
    public static final int DTN_FIRST = -760;
    public static final int DTN_DATETIMECHANGE = -759;
    public static final int DTN_CLOSEUP = -753;
    public static final int DTN_DROPDOWN = -754;
    public static final int DTS_LONGDATEFORMAT = 4;
    public static final int DTS_SHORTDATECENTURYFORMAT = 12;
    public static final int DTS_SHORTDATEFORMAT = 0;
    public static final int DTS_TIMEFORMAT = 9;
    public static final int DTS_UPDOWN = 1;
    public static final int E_POINTER = -2147467261;
    public static final int EBP_NORMALGROUPBACKGROUND = 5;
    public static final int EBP_NORMALGROUPCOLLAPSE = 6;
    public static final int EBP_NORMALGROUPEXPAND = 7;
    public static final int EBP_NORMALGROUPHEAD = 8;
    public static final int EBNGC_NORMAL = 1;
    public static final int EBNGC_HOT = 2;
    public static final int EBNGC_PRESSED = 3;
    public static final int EBP_HEADERBACKGROUND = 1;
    public static final int EC_LEFTMARGIN = 1;
    public static final int EC_RIGHTMARGIN = 2;
    public static final int EDGE_SUNKEN = 10;
    public static final int EDGE_ETCHED = 6;
    public static final int EM_CANUNDO = 198;
    public static final int EM_CHARFROMPOS = 215;
    public static final int EM_DISPLAYBAND = 1075;
    public static final int EM_GETFIRSTVISIBLELINE = 206;
    public static final int EM_GETLIMITTEXT = 213;
    public static final int EM_GETLINE = 196;
    public static final int EM_GETLINECOUNT = 186;
    public static final int EM_GETMARGINS = 212;
    public static final int EM_GETPASSWORDCHAR = 210;
    public static final int EM_GETSCROLLPOS = 1245;
    public static final int EM_GETSEL = 176;
    public static final int EM_LIMITTEXT = 197;
    public static final int EM_LINEFROMCHAR = 201;
    public static final int EM_LINEINDEX = 187;
    public static final int EM_LINELENGTH = 193;
    public static final int EM_LINESCROLL = 182;
    public static final int EM_POSFROMCHAR = 214;
    public static final int EM_REPLACESEL = 194;
    public static final int EM_SCROLLCARET = 183;
    public static final int EM_SETBKGNDCOLOR = 1091;
    public static final int EM_SETLIMITTEXT = 197;
    public static final int EM_SETMARGINS = 211;
    public static final int EM_SETOPTIONS = 1101;
    public static final int EM_SETPARAFORMAT = 1095;
    public static final int EM_SETPASSWORDCHAR = 204;
    public static final int EM_SETCUEBANNER = 5377;
    public static final int EM_SETREADONLY = 207;
    public static final int EM_SETRECT = 179;
    public static final int EM_SETSEL = 177;
    public static final int EM_SETTABSTOPS = 203;
    public static final int EM_UNDO = 199;
    public static final int EMR_EXTCREATEFONTINDIRECTW = 82;
    public static final int EMR_EXTTEXTOUTW = 84;
    public static final int EN_ALIGN_LTR_EC = 1792;
    public static final int EN_ALIGN_RTL_EC = 1793;
    public static final int EN_CHANGE = 768;
    public static final int EP_EDITTEXT = 1;
    public static final int ERROR_FILE_NOT_FOUND = 2;
    public static final int ERROR_NO_MORE_ITEMS = 259;
    public static final int ESB_DISABLE_BOTH = 3;
    public static final int ESB_ENABLE_BOTH = 0;
    public static final int ES_AUTOHSCROLL = 128;
    public static final int ES_AUTOVSCROLL = 64;
    public static final int ES_CENTER = 1;
    public static final int ES_MULTILINE = 4;
    public static final int ES_NOHIDESEL = 256;
    public static final int ES_PASSWORD = 32;
    public static final int ES_READONLY = 2048;
    public static final int ES_RIGHT = 2;
    public static final int ETO_CLIPPED = 4;
    public static final int ETS_NORMAL = 1;
    public static final int ETS_HOT = 2;
    public static final int ETS_SELECTED = 3;
    public static final int ETS_DISABLED = 4;
    public static final int ETS_FOCUSED = 5;
    public static final int ETS_READONLY = 6;
    public static final int EVENT_OBJECT_FOCUS = 32773;
    public static final short FADF_FIXEDSIZE = 16;
    public static final int FALT = 16;
    public static final int FCONTROL = 8;
    public static final int FE_FONTSMOOTHINGCLEARTYPE = 2;
    public static final int FEATURE_DISABLE_NAVIGATION_SOUNDS = 21;
    public static final int FILE_ATTRIBUTE_NORMAL = 128;
    public static final int FILE_MAP_READ = 4;
    public static final int FLICKDIRECTION_RIGHT = 0;
    public static final int FLICKDIRECTION_UPRIGHT = 1;
    public static final int FLICKDIRECTION_UP = 2;
    public static final int FLICKDIRECTION_UPLEFT = 3;
    public static final int FLICKDIRECTION_LEFT = 4;
    public static final int FLICKDIRECTION_DOWNLEFT = 5;
    public static final int FLICKDIRECTION_DOWN = 6;
    public static final int FLICKDIRECTION_DOWNRIGHT = 7;
    public static final int FLICKDIRECTION_INVALID = 8;
    public static final int FOS_OVERWRITEPROMPT = 2;
    public static final int FOS_NOCHANGEDIR = 8;
    public static final int FOS_PICKFOLDERS = 32;
    public static final int FOS_FORCEFILESYSTEM = 64;
    public static final int FOS_ALLOWMULTISELECT = 512;
    public static final int FOS_FILEMUSTEXIST = 4096;
    public static final int FR_PRIVATE = 16;
    public static final int FSHIFT = 4;
    public static final int FVIRTKEY = 1;
    public static final int GCP_REORDER = 2;
    public static final int GCP_GLYPHSHAPE = 16;
    public static final int GCP_CLASSIN = 524288;
    public static final int GCP_LIGATE = 32;
    public static final int GCS_COMPSTR = 8;
    public static final int GCS_RESULTSTR = 2048;
    public static final int GCS_COMPATTR = 16;
    public static final int GCS_COMPCLAUSE = 32;
    public static final int GCS_CURSORPOS = 128;
    public static final int GET_FEATURE_FROM_PROCESS = 2;
    public static final int GF_BEGIN = 1;
    public static final int GF_INERTIA = 2;
    public static final int GF_END = 4;
    public static final int GGI_MARK_NONEXISTING_GLYPHS = 1;
    public static final int GID_BEGIN = 1;
    public static final int GID_END = 2;
    public static final int GID_ZOOM = 3;
    public static final int GID_PAN = 4;
    public static final int GID_ROTATE = 5;
    public static final int GID_TWOFINGERTAP = 6;
    public static final int GID_PRESSANDTAP = 7;
    public static final int GM_ADVANCED = 2;
    public static final int GMDI_USEDISABLED = 1;
    public static final int GMEM_FIXED = 0;
    public static final int GMEM_MOVEABLE = 2;
    public static final int GMEM_ZEROINIT = 64;
    public static final int GRADIENT_FILL_RECT_H = 0;
    public static final int GRADIENT_FILL_RECT_V = 1;
    public static final int GUI_INMENUMODE = 4;
    public static final int GUI_INMOVESIZE = 2;
    public static final int GUI_POPUPMENUMODE = 16;
    public static final int GUI_SYSTEMMENUMODE = 8;
    public static final int GWL_EXSTYLE = -20;
    public static final int GWL_ID = -12;
    public static final int GWL_HWNDPARENT = -8;
    public static final int GWL_STYLE = -16;
    public static final int GWL_USERDATA = -21;
    public static final int GWL_WNDPROC = -4;
    public static final int GWLP_ID = -12;
    public static final int GWLP_HWNDPARENT = -8;
    public static final int GWLP_USERDATA = -21;
    public static final int GWLP_WNDPROC = -4;
    public static final int GW_CHILD = 5;
    public static final int GW_HWNDFIRST = 0;
    public static final int GW_HWNDLAST = 1;
    public static final int GW_HWNDNEXT = 2;
    public static final int GW_HWNDPREV = 3;
    public static final int GW_OWNER = 4;
    public static final long HBMMENU_CALLBACK = -1L;
    public static final int HCBT_CREATEWND = 3;
    public static final int HCF_HIGHCONTRASTON = 1;
    public static final int HDF_BITMAP = 8192;
    public static final int HDF_BITMAP_ON_RIGHT = 4096;
    public static final int HDF_CENTER = 2;
    public static final int HDF_JUSTIFYMASK = 3;
    public static final int HDF_IMAGE = 2048;
    public static final int HDF_LEFT = 0;
    public static final int HDF_OWNERDRAW = 32768;
    public static final int HDF_RIGHT = 1;
    public static final int HDF_SORTUP = 1024;
    public static final int HDF_SORTDOWN = 512;
    public static final int HDI_BITMAP = 16;
    public static final int HDI_IMAGE = 32;
    public static final int HDI_ORDER = 128;
    public static final int HDI_TEXT = 2;
    public static final int HDI_WIDTH = 1;
    public static final int HDI_FORMAT = 4;
    public static final int HDM_FIRST = 4608;
    public static final int HDM_DELETEITEM = 4610;
    public static final int HDM_GETBITMAPMARGIN = 4629;
    public static final int HDM_GETITEMCOUNT = 4608;
    public static final int HDM_GETITEM = 4619;
    public static final int HDM_GETITEMRECT = 4615;
    public static final int HDM_GETORDERARRAY = 4625;
    public static final int HDM_HITTEST = 4614;
    public static final int HDM_INSERTITEM = 4618;
    public static final int HDM_LAYOUT = 4613;
    public static final int HDM_ORDERTOINDEX = 4623;
    public static final int HDM_SETIMAGELIST = 4616;
    public static final int HDM_SETITEM = 4620;
    public static final int HDM_SETORDERARRAY = 4626;
    public static final int HDN_FIRST = -300;
    public static final int HDN_BEGINDRAG = -310;
    public static final int HDN_BEGINTRACK = -326;
    public static final int HDN_DIVIDERDBLCLICK = -325;
    public static final int HDN_ENDDRAG = -311;
    public static final int HDN_ITEMCHANGED = -321;
    public static final int HDN_ITEMCHANGING = -320;
    public static final int HDN_ITEMCLICK = -322;
    public static final int HDN_ITEMDBLCLICK = -323;
    public static final int HDS_BUTTONS = 2;
    public static final int HDS_DRAGDROP = 64;
    public static final int HDS_FULLDRAG = 128;
    public static final int HDS_HIDDEN = 8;
    public static final int HEAP_ZERO_MEMORY = 8;
    public static final int HELPINFO_MENUITEM = 2;
    public static final int HHT_ONDIVIDER = 4;
    public static final int HHT_ONDIVOPEN = 8;
    public static final int HICF_ARROWKEYS = 2;
    public static final int HICF_LEAVING = 32;
    public static final int HICF_MOUSE = 1;
    public static final int HKEY_CLASSES_ROOT = Integer.MIN_VALUE;
    public static final int HKEY_CURRENT_USER = -2147483647;
    public static final int HKEY_LOCAL_MACHINE = -2147483646;
    public static final int HORZRES = 8;
    public static final int HTBORDER = 18;
    public static final int HTCAPTION = 2;
    public static final int HTCLIENT = 1;
    public static final int HTERROR = -2;
    public static final int HTHSCROLL = 6;
    public static final int HTMENU = 5;
    public static final int HTNOWHERE = 0;
    public static final int HTSYSMENU = 3;
    public static final int HTTRANSPARENT = -1;
    public static final int HTVSCROLL = 7;
    public static final int HWND_BOTTOM = 1;
    public static final int HWND_TOP = 0;
    public static final int HWND_TOPMOST = -1;
    public static final int HWND_NOTOPMOST = -2;
    public static final int ICON_BIG = 1;
    public static final int ICON_SMALL = 0;
    public static final int I_IMAGECALLBACK = -1;
    public static final int I_IMAGENONE = -2;
    public static final int IDABORT = 3;
    public static final int IDC_APPSTARTING = 32650;
    public static final int IDC_ARROW = 32512;
    public static final int IDC_CROSS = 32515;
    public static final int IDC_HAND = 32649;
    public static final int IDC_HELP = 32651;
    public static final int IDC_IBEAM = 32513;
    public static final int IDC_NO = 32648;
    public static final int IDC_SIZE = 32640;
    public static final int IDC_SIZEALL = 32646;
    public static final int IDC_SIZENESW = 32643;
    public static final int IDC_SIZENS = 32645;
    public static final int IDC_SIZENWSE = 32642;
    public static final int IDC_SIZEWE = 32644;
    public static final int IDC_UPARROW = 32516;
    public static final int IDC_WAIT = 32514;
    public static final int IDI_APPLICATION = 32512;
    public static final int IDNO = 7;
    public static final int IDOK = 1;
    public static final int IDRETRY = 4;
    public static final int IDYES = 6;
    public static final int ILC_COLOR = 0;
    public static final int ILC_COLOR16 = 16;
    public static final int ILC_COLOR24 = 24;
    public static final int ILC_COLOR32 = 32;
    public static final int ILC_COLOR4 = 4;
    public static final int ILC_COLOR8 = 8;
    public static final int ILC_MASK = 1;
    public static final int ILC_MIRROR = 8192;
    public static final int IMAGE_ICON = 1;
    public static final int IME_CMODE_FULLSHAPE = 8;
    public static final int IME_CMODE_KATAKANA = 2;
    public static final int IME_CMODE_NATIVE = 1;
    public static final int IME_CMODE_ROMAN = 16;
    public static final int IME_ESC_HANJA_MODE = 4104;
    public static final int IMEMOUSE_LDOWN = 1;
    public static final int INPUT_KEYBOARD = 1;
    public static final int INPUT_MOUSE = 0;
    public static final int INTERNET_MAX_URL_LENGTH = 2084;
    public static final int INTERNET_OPTION_END_BROWSER_SESSION = 42;
    public static final int KEY_QUERY_VALUE = 1;
    public static final int KEY_READ = 131097;
    public static final int KEY_WRITE = 131078;
    public static final int KEYEVENTF_EXTENDEDKEY = 1;
    public static final int KEYEVENTF_KEYUP = 2;
    public static final int L_MAX_URL_LENGTH = 2084;
    public static final int LANG_JAPANESE = 17;
    public static final int LANG_KOREAN = 18;
    public static final int LANG_NEUTRAL = 0;
    public static final int LAYOUT_RTL = 1;
    public static final int LBN_DBLCLK = 2;
    public static final int LBN_SELCHANGE = 1;
    public static final int LBS_EXTENDEDSEL = 2048;
    public static final int LBS_MULTIPLESEL = 8;
    public static final int LBS_NOINTEGRALHEIGHT = 256;
    public static final int LBS_NOTIFY = 1;
    public static final int LB_ADDSTRING = 384;
    public static final int LB_DELETESTRING = 386;
    public static final int LB_ERR = -1;
    public static final int LB_ERRSPACE = -2;
    public static final int LB_FINDSTRINGEXACT = 418;
    public static final int LB_GETCARETINDEX = 415;
    public static final int LB_GETCOUNT = 395;
    public static final int LB_GETCURSEL = 392;
    public static final int LB_GETHORIZONTALEXTENT = 403;
    public static final int LB_GETITEMHEIGHT = 417;
    public static final int LB_GETITEMRECT = 408;
    public static final int LB_GETSEL = 391;
    public static final int LB_GETSELCOUNT = 400;
    public static final int LB_GETSELITEMS = 401;
    public static final int LB_GETTEXT = 393;
    public static final int LB_GETTEXTLEN = 394;
    public static final int LB_GETTOPINDEX = 398;
    public static final int LB_INITSTORAGE = 424;
    public static final int LB_INSERTSTRING = 385;
    public static final int LB_RESETCONTENT = 388;
    public static final int LB_SELITEMRANGE = 411;
    public static final int LB_SELITEMRANGEEX = 387;
    public static final int LB_SETANCHORINDEX = 61852;
    public static final int LB_SETCARETINDEX = 414;
    public static final int LB_SETCURSEL = 390;
    public static final int LB_SETHORIZONTALEXTENT = 404;
    public static final int LB_SETSEL = 389;
    public static final int LB_SETTOPINDEX = 407;
    public static final int LF_FACESIZE = 32;
    public static final int LGRPID_ARABIC = 13;
    public static final int LGRPID_HEBREW = 12;
    public static final int LGRPID_INSTALLED = 1;
    public static final int LIF_ITEMINDEX = 1;
    public static final int LIF_STATE = 2;
    public static final int LIM_SMALL = 0;
    public static final int LIS_FOCUSED = 1;
    public static final int LIS_ENABLED = 2;
    public static final int LISS_HOT = 2;
    public static final int LISS_SELECTED = 3;
    public static final int LISS_SELECTEDNOTFOCUS = 5;
    public static final int LM_GETIDEALSIZE = 1793;
    public static final int LM_SETITEM = 1794;
    public static final int LM_GETITEM = 1795;
    public static final int LCID_SUPPORTED = 2;
    public static final int LOCALE_IDEFAULTANSICODEPAGE = 4100;
    public static final int LOCALE_SDECIMAL = 14;
    public static final int LOCALE_SISO3166CTRYNAME = 90;
    public static final int LOCALE_SISO639LANGNAME = 89;
    public static final int LOCALE_STIMEFORMAT = 4099;
    public static final int LOCALE_SYEARMONTH = 4102;
    public static final int LOCALE_USER_DEFAULT = 1024;
    public static final int LOGPIXELSX = 88;
    public static final int LOGPIXELSY = 90;
    public static final int LPSTR_TEXTCALLBACK = -1;
    public static final int LR_DEFAULTCOLOR = 0;
    public static final int LR_SHARED = 32768;
    public static final int LVCFMT_BITMAP_ON_RIGHT = 4096;
    public static final int LVCFMT_CENTER = 2;
    public static final int LVCFMT_IMAGE = 2048;
    public static final int LVCFMT_LEFT = 0;
    public static final int LVCFMT_RIGHT = 1;
    public static final int LVCF_FMT = 1;
    public static final int LVCF_IMAGE = 16;
    public static final int LVCFMT_JUSTIFYMASK = 3;
    public static final int LVCF_TEXT = 4;
    public static final int LVCF_WIDTH = 2;
    public static final int LVHT_ONITEM = 14;
    public static final int LVHT_ONITEMICON = 2;
    public static final int LVHT_ONITEMLABEL = 4;
    public static final int LVHT_ONITEMSTATEICON = 8;
    public static final int LVIF_IMAGE = 2;
    public static final int LVIF_INDENT = 16;
    public static final int LVIF_STATE = 8;
    public static final int LVIF_TEXT = 1;
    public static final int LVIM_AFTER = 1;
    public static final int LVIR_BOUNDS = 0;
    public static final int LVIR_ICON = 1;
    public static final int LVIR_LABEL = 2;
    public static final int LVIR_SELECTBOUNDS = 3;
    public static final int LVIS_DROPHILITED = 8;
    public static final int LVIS_FOCUSED = 1;
    public static final int LVIS_SELECTED = 2;
    public static final int LVIS_STATEIMAGEMASK = 61440;
    public static final int LVM_FIRST = 4096;
    public static final int LVM_APPROXIMATEVIEWRECT = 4160;
    public static final int LVM_CREATEDRAGIMAGE = 4129;
    public static final int LVM_DELETEALLITEMS = 4105;
    public static final int LVM_DELETECOLUMN = 4124;
    public static final int LVM_DELETEITEM = 4104;
    public static final int LVM_ENSUREVISIBLE = 4115;
    public static final int LVM_GETBKCOLOR = 4096;
    public static final int LVM_GETCOLUMN = 4191;
    public static final int LVM_GETCOLUMNORDERARRAY = 4155;
    public static final int LVM_GETCOLUMNWIDTH = 4125;
    public static final int LVM_GETCOUNTPERPAGE = 4136;
    public static final int LVM_GETEXTENDEDLISTVIEWSTYLE = 4151;
    public static final int LVM_GETHEADER = 4127;
    public static final int LVM_GETIMAGELIST = 4098;
    public static final int LVM_GETITEM = 4171;
    public static final int LVM_GETITEMCOUNT = 4100;
    public static final int LVM_GETITEMRECT = 4110;
    public static final int LVM_GETITEMSTATE = 4140;
    public static final int LVM_GETNEXTITEM = 4108;
    public static final int LVM_GETSELECTEDCOLUMN = 4270;
    public static final int LVM_GETSELECTEDCOUNT = 4146;
    public static final int LVM_GETSTRINGWIDTH = 4183;
    public static final int LVM_GETSUBITEMRECT = 4152;
    public static final int LVM_GETTEXTCOLOR = 4131;
    public static final int LVM_GETTOOLTIPS = 4174;
    public static final int LVM_GETTOPINDEX = 4135;
    public static final int LVM_HITTEST = 4114;
    public static final int LVM_INSERTCOLUMN = 4193;
    public static final int LVM_INSERTITEM = 4173;
    public static final int LVM_REDRAWITEMS = 4117;
    public static final int LVM_SCROLL = 4116;
    public static final int LVM_SETBKCOLOR = 4097;
    public static final int LVM_SETCALLBACKMASK = 4107;
    public static final int LVM_SETCOLUMN = 4192;
    public static final int LVM_SETCOLUMNORDERARRAY = 4154;
    public static final int LVM_SETCOLUMNWIDTH = 4126;
    public static final int LVM_SETEXTENDEDLISTVIEWSTYLE = 4150;
    public static final int LVM_SETIMAGELIST = 4099;
    public static final int LVM_SETINSERTMARK = 4262;
    public static final int LVM_SETITEM = 4172;
    public static final int LVM_SETITEMCOUNT = 4143;
    public static final int LVM_SETITEMSTATE = 4139;
    public static final int LVM_SETSELECTIONMARK = 4163;
    public static final int LVM_SETSELECTEDCOLUMN = 4236;
    public static final int LVM_SETTEXTBKCOLOR = 4134;
    public static final int LVM_SETTEXTCOLOR = 4132;
    public static final int LVM_SETTOOLTIPS = 4170;
    public static final int LVM_SUBITEMHITTEST = 4153;
    public static final int LVNI_FOCUSED = 1;
    public static final int LVNI_SELECTED = 2;
    public static final int LVN_BEGINDRAG = -109;
    public static final int LVN_BEGINRDRAG = -111;
    public static final int LVN_COLUMNCLICK = -108;
    public static final int LVN_FIRST = -100;
    public static final int LVN_GETDISPINFO = -177;
    public static final int LVN_ITEMACTIVATE = -114;
    public static final int LVN_ITEMCHANGED = -101;
    public static final int LVN_MARQUEEBEGIN = -156;
    public static final int LVN_ODFINDITEM = -179;
    public static final int LVN_ODSTATECHANGED = -115;
    public static final int LVP_LISTITEM = 1;
    public static final int LVSCW_AUTOSIZE = -1;
    public static final int LVSCW_AUTOSIZE_USEHEADER = -2;
    public static final int LVSICF_NOINVALIDATEALL = 1;
    public static final int LVSICF_NOSCROLL = 2;
    public static final int LVSIL_SMALL = 1;
    public static final int LVSIL_STATE = 2;
    public static final int LVS_EX_DOUBLEBUFFER = 65536;
    public static final int LVS_EX_FULLROWSELECT = 32;
    public static final int LVS_EX_GRIDLINES = 1;
    public static final int LVS_EX_HEADERDRAGDROP = 16;
    public static final int LVS_EX_LABELTIP = 16384;
    public static final int LVS_EX_ONECLICKACTIVATE = 64;
    public static final int LVS_EX_SUBITEMIMAGES = 2;
    public static final int LVS_EX_TRACKSELECT = 8;
    public static final int LVS_EX_TRANSPARENTBKGND = 0x800000;
    public static final int LVS_EX_TWOCLICKACTIVATE = 128;
    public static final int LVS_NOCOLUMNHEADER = 16384;
    public static final int LVS_NOSCROLL = 8192;
    public static final int LVS_OWNERDATA = 4096;
    public static final int LVS_OWNERDRAWFIXED = 1024;
    public static final int LVS_REPORT = 1;
    public static final int LVS_SHAREIMAGELISTS = 64;
    public static final int LVS_SHOWSELALWAYS = 8;
    public static final int LVS_SINGLESEL = 4;
    public static final int LWA_COLORKEY = 1;
    public static final int LWA_ALPHA = 2;
    public static final int MAX_LINKID_TEXT = 48;
    public static final int MAX_PATH = 260;
    public static final int MA_NOACTIVATE = 3;
    public static final int MB_ABORTRETRYIGNORE = 2;
    public static final int MB_APPLMODAL = 0;
    public static final int MB_ICONERROR = 16;
    public static final int MB_ICONINFORMATION = 64;
    public static final int MB_ICONQUESTION = 32;
    public static final int MB_ICONWARNING = 48;
    public static final int MB_OK = 0;
    public static final int MB_OKCANCEL = 1;
    public static final int MB_PRECOMPOSED = 1;
    public static final int MB_RETRYCANCEL = 5;
    public static final int MB_RIGHT = 524288;
    public static final int MB_RTLREADING = 0x100000;
    public static final int MB_SYSTEMMODAL = 4096;
    public static final int MB_TASKMODAL = 8192;
    public static final int MB_TOPMOST = 262144;
    public static final int MB_YESNO = 4;
    public static final int MB_YESNOCANCEL = 3;
    public static final int MCHT_CALENDAR = 131072;
    public static final int MCHT_CALENDARDATE = 131073;
    public static final int MCM_FIRST = 4096;
    public static final int MCM_GETCURSEL = 4097;
    public static final int MCM_GETMINREQRECT = 4105;
    public static final int MCM_HITTEST = 4110;
    public static final int MCM_SETCURSEL = 4098;
    public static final int MCN_FIRST = -750;
    public static final int MCN_SELCHANGE = -749;
    public static final int MCN_SELECT = -746;
    public static final int MCS_NOTODAY = 16;
    public static final int MCS_WEEKNUMBERS = 4;
    public static final int MDIS_ALLCHILDSTYLES = 1;
    public static final int MDT_EFFECTIVE_DPI = 0;
    public static final int MFS_CHECKED = 8;
    public static final int MFS_DISABLED = 3;
    public static final int MFS_GRAYED = 3;
    public static final int MFT_RADIOCHECK = 512;
    public static final int MFT_RIGHTJUSTIFY = 16384;
    public static final int MFT_RIGHTORDER = 8192;
    public static final int MFT_SEPARATOR = 2048;
    public static final int MFT_STRING = 0;
    public static final int MF_BYCOMMAND = 0;
    public static final int MF_BYPOSITION = 1024;
    public static final int MF_CHECKED = 8;
    public static final int MF_DISABLED = 2;
    public static final int MF_ENABLED = 0;
    public static final int MF_GRAYED = 1;
    public static final int MF_HILITE = 128;
    public static final int MF_POPUP = 16;
    public static final int MF_SEPARATOR = 2048;
    public static final int MF_SYSMENU = 8192;
    public static final int MF_UNCHECKED = 0;
    public static final int MIIM_BITMAP = 128;
    public static final int MIIM_DATA = 32;
    public static final int MIIM_FTYPE = 256;
    public static final int MIIM_ID = 2;
    public static final int MIIM_STATE = 1;
    public static final int MIIM_STRING = 64;
    public static final int MIIM_SUBMENU = 4;
    public static final int MIIM_TYPE = 16;
    public static final int MIM_BACKGROUND = 2;
    public static final int MIM_STYLE = 16;
    public static final int MK_ALT = 32;
    public static final int MK_CONTROL = 8;
    public static final int MK_LBUTTON = 1;
    public static final int MK_MBUTTON = 16;
    public static final int MK_RBUTTON = 2;
    public static final int MK_SHIFT = 4;
    public static final int MK_XBUTTON1 = 32;
    public static final int MK_XBUTTON2 = 64;
    public static final int MM_TEXT = 1;
    public static final int MNC_CLOSE = 1;
    public static final int MNS_CHECKORBMP = 0x4000000;
    public static final int MONITOR_DEFAULTTOPRIMARY = 1;
    public static final int MONITOR_DEFAULTTONEAREST = 2;
    public static final String MONTHCAL_CLASS = "SysMonthCal32";
    public static final int MOUSEEVENTF_ABSOLUTE = 32768;
    public static final int MOUSEEVENTF_LEFTDOWN = 2;
    public static final int MOUSEEVENTF_LEFTUP = 4;
    public static final int MOUSEEVENTF_MIDDLEDOWN = 32;
    public static final int MOUSEEVENTF_MIDDLEUP = 64;
    public static final int MOUSEEVENTF_MOVE = 1;
    public static final int MOUSEEVENTF_RIGHTDOWN = 8;
    public static final int MOUSEEVENTF_RIGHTUP = 16;
    public static final int MOUSEEVENTF_VIRTUALDESK = 16384;
    public static final int MOUSEEVENTF_WHEEL = 2048;
    public static final int MOUSEEVENTF_XDOWN = 128;
    public static final int MOUSEEVENTF_XUP = 256;
    public static final int MSGF_DIALOGBOX = 0;
    public static final int MSGF_COMMCTRL_BEGINDRAG = 16896;
    public static final int MSGF_COMMCTRL_SIZEHEADER = 16897;
    public static final int MSGF_COMMCTRL_DRAGSELECT = 16898;
    public static final int MSGF_COMMCTRL_TOOLBARCUST = 16899;
    public static final int MSGF_MAINLOOP = 8;
    public static final int MSGF_MENU = 2;
    public static final int MSGF_MOVE = 3;
    public static final int MSGF_MESSAGEBOX = 1;
    public static final int MSGF_NEXTWINDOW = 6;
    public static final int MSGF_SCROLLBAR = 5;
    public static final int MSGF_SIZE = 4;
    public static final int MSGF_USER = 4096;
    public static final int MWT_LEFTMULTIPLY = 2;
    public static final int NI_COMPOSITIONSTR = 21;
    public static final int NID_READY = 128;
    public static final int NID_MULTI_INPUT = 64;
    public static final int NIF_ICON = 2;
    public static final int NIF_INFO = 16;
    public static final int NIF_MESSAGE = 1;
    public static final int NIF_STATE = 8;
    public static final int NIF_TIP = 4;
    public static final int NIIF_ERROR = 3;
    public static final int NIIF_INFO = 1;
    public static final int NIIF_NONE = 0;
    public static final int NIIF_WARNING = 2;
    public static final int NIM_ADD = 0;
    public static final int NIM_DELETE = 2;
    public static final int NIM_MODIFY = 1;
    public static final int NIN_SELECT = 1024;
    public static final int NINF_KEY = 1;
    public static final int NIN_KEYSELECT = 1025;
    public static final int NIN_BALLOONSHOW = 1026;
    public static final int NIN_BALLOONHIDE = 1027;
    public static final int NIN_BALLOONTIMEOUT = 1028;
    public static final int NIN_BALLOONUSERCLICK = 1029;
    public static final int NIS_HIDDEN = 1;
    public static final int NM_FIRST = 0;
    public static final int NM_CLICK = -2;
    public static final int NM_CUSTOMDRAW = -12;
    public static final int NM_DBLCLK = -3;
    public static final int NM_RECOGNIZEGESTURE = -16;
    public static final int NM_RELEASEDCAPTURE = -16;
    public static final int NM_RETURN = -4;
    public static final int NOTIFYICONDATA_V2_SIZE;
    public static final int NULLREGION = 1;
    public static final int NULL_BRUSH = 5;
    public static final int NULL_PEN = 8;
    public static final int OBJID_WINDOW = 0;
    public static final int OBJID_SYSMENU = -1;
    public static final int OBJID_TITLEBAR = -2;
    public static final int OBJID_MENU = -3;
    public static final int OBJID_CLIENT = -4;
    public static final int OBJID_VSCROLL = -5;
    public static final int OBJID_HSCROLL = -6;
    public static final int OBJID_SIZEGRIP = -7;
    public static final int OBJID_CARET = -8;
    public static final int OBJID_CURSOR = -9;
    public static final int OBJID_ALERT = -10;
    public static final int OBJID_SOUND = -11;
    public static final int OBJID_QUERYCLASSNAMEIDX = -12;
    public static final int OBJID_NATIVEOM = -16;
    public static final int OBJ_BITMAP = 7;
    public static final int OBJ_FONT = 6;
    public static final int OBJ_PEN = 1;
    public static final int OBM_CHECKBOXES = 32759;
    public static final int ODS_SELECTED = 1;
    public static final int ODT_MENU = 1;
    public static final int OIC_BANG = 32515;
    public static final int OIC_HAND = 32513;
    public static final int OIC_INFORMATION = 32516;
    public static final int OIC_QUES = 32514;
    public static final int OIC_WINLOGO = 32517;
    public static final int OPAQUE = 2;
    public static final int PATCOPY = 15728673;
    public static final int PATINVERT = 5898313;
    public static final int PBM_GETPOS = 1032;
    public static final int PBM_GETRANGE = 1031;
    public static final int PBM_GETSTATE = 1041;
    public static final int PBM_SETBARCOLOR = 1033;
    public static final int PBM_SETBKCOLOR = 8193;
    public static final int PBM_SETMARQUEE = 1034;
    public static final int PBM_SETPOS = 1026;
    public static final int PBM_SETRANGE32 = 1030;
    public static final int PBM_SETSTATE = 1040;
    public static final int PBM_STEPIT = 1029;
    public static final int PBS_MARQUEE = 8;
    public static final int PBS_SMOOTH = 1;
    public static final int PBS_VERTICAL = 4;
    public static final int PBS_NORMAL = 1;
    public static final int PBS_HOT = 2;
    public static final int PBS_PRESSED = 3;
    public static final int PBS_DISABLED = 4;
    public static final int PBS_DEFAULTED = 5;
    public static final int PBST_NORMAL = 1;
    public static final int PBST_ERROR = 2;
    public static final int PBST_PAUSED = 3;
    public static final int PD_ALLPAGES = 0;
    public static final int PD_COLLATE = 16;
    public static final int PD_PAGENUMS = 2;
    public static final int PD_PRINTTOFILE = 32;
    public static final int PD_RETURNDEFAULT = 1024;
    public static final int PD_SELECTION = 1;
    public static final int PD_USEDEVMODECOPIESANDCOLLATE = 262144;
    public static final int PFM_TABSTOPS = 16;
    public static final int PHYSICALHEIGHT = 111;
    public static final int PHYSICALOFFSETX = 112;
    public static final int PHYSICALOFFSETY = 113;
    public static final int PHYSICALWIDTH = 110;
    public static final int PLANES = 14;
    public static final int PM_NOREMOVE = 0;
    public static final int PM_NOYIELD = 2;
    public static final int QS_HOTKEY = 128;
    public static final int QS_KEY = 1;
    public static final int QS_MOUSEMOVE = 2;
    public static final int QS_MOUSEBUTTON = 4;
    public static final int QS_MOUSE = 6;
    public static final int QS_INPUT = 7;
    public static final int QS_POSTMESSAGE = 8;
    public static final int QS_TIMER = 16;
    public static final int QS_PAINT = 32;
    public static final int QS_SENDMESSAGE = 64;
    public static final int QS_ALLINPUT = 127;
    public static final int PM_QS_INPUT = 458752;
    public static final int PM_QS_POSTMESSAGE = 0x980000;
    public static final int PM_QS_PAINT = 0x200000;
    public static final int PM_QS_SENDMESSAGE = 0x400000;
    public static final int PM_REMOVE = 1;
    public static final String PROGRESS_CLASS = "msctls_progress32";
    public static final int PRF_CHILDREN = 16;
    public static final int PRF_CLIENT = 4;
    public static final int PRF_ERASEBKGND = 8;
    public static final int PRF_NONCLIENT = 2;
    public static final int PROGRESSCHUNKSIZE = 2411;
    public static final int PROGRESSSPACESIZE = 2412;
    public static final int PS_DASH = 1;
    public static final int PS_DASHDOT = 3;
    public static final int PS_DASHDOTDOT = 4;
    public static final int PS_DOT = 2;
    public static final int PS_ENDCAP_FLAT = 512;
    public static final int PS_ENDCAP_SQUARE = 256;
    public static final int PS_ENDCAP_ROUND = 0;
    public static final int PS_ENDCAP_MASK = 3840;
    public static final int PS_GEOMETRIC = 65536;
    public static final int PS_JOIN_BEVEL = 4096;
    public static final int PS_JOIN_MASK = 61440;
    public static final int PS_JOIN_MITER = 8192;
    public static final int PS_JOIN_ROUND = 0;
    public static final int PS_SOLID = 0;
    public static final int PS_STYLE_MASK = 15;
    public static final int PS_TYPE_MASK = 983040;
    public static final int PS_USERSTYLE = 7;
    public static final int R2_COPYPEN = 13;
    public static final int R2_XORPEN = 7;
    public static final int RASTERCAPS = 38;
    public static final int RASTER_FONTTYPE = 1;
    public static final int RBBIM_CHILD = 16;
    public static final int RBBIM_CHILDSIZE = 32;
    public static final int RBBIM_COLORS = 2;
    public static final int RBBIM_HEADERSIZE = 2048;
    public static final int RBBIM_ID = 256;
    public static final int RBBIM_IDEALSIZE = 512;
    public static final int RBBIM_SIZE = 64;
    public static final int RBBIM_STYLE = 1;
    public static final int RBBIM_TEXT = 4;
    public static final int RBBS_BREAK = 1;
    public static final int RBBS_GRIPPERALWAYS = 128;
    public static final int RBBS_NOGRIPPER = 256;
    public static final int RBBS_USECHEVRON = 512;
    public static final int RBBS_VARIABLEHEIGHT = 64;
    public static final int RBN_FIRST = -831;
    public static final int RBN_BEGINDRAG = -835;
    public static final int RBN_CHILDSIZE = -839;
    public static final int RBN_CHEVRONPUSHED = -841;
    public static final int RBN_HEIGHTCHANGE = -831;
    public static final int RBS_UNCHECKEDNORMAL = 1;
    public static final int RBS_UNCHECKEDHOT = 2;
    public static final int RBS_UNCHECKEDPRESSED = 3;
    public static final int RBS_UNCHECKEDDISABLED = 4;
    public static final int RBS_CHECKEDNORMAL = 5;
    public static final int RBS_CHECKEDHOT = 6;
    public static final int RBS_CHECKEDPRESSED = 7;
    public static final int RBS_CHECKEDDISABLED = 8;
    public static final int RBS_DBLCLKTOGGLE = 32768;
    public static final int RBS_BANDBORDERS = 1024;
    public static final int RBS_VARHEIGHT = 512;
    public static final int RB_DELETEBAND = 1026;
    public static final int RB_GETBANDBORDERS = 1058;
    public static final int RB_GETBANDCOUNT = 1036;
    public static final int RB_GETBANDINFO = 1052;
    public static final int RB_GETBANDMARGINS = 1064;
    public static final int RB_GETBARHEIGHT = 1051;
    public static final int RB_GETBKCOLOR = 1044;
    public static final int RB_GETRECT = 1033;
    public static final int RB_GETTEXTCOLOR = 1046;
    public static final int RB_IDTOINDEX = 1040;
    public static final int RB_INSERTBAND = 1034;
    public static final int RB_MOVEBAND = 1063;
    public static final int RB_SETBANDINFO = 1035;
    public static final int RB_SETBKCOLOR = 1043;
    public static final int RB_SETTEXTCOLOR = 1045;
    public static final int RDW_ALLCHILDREN = 128;
    public static final int RDW_ERASE = 4;
    public static final int RDW_FRAME = 1024;
    public static final int RDW_INVALIDATE = 1;
    public static final int RDW_UPDATENOW = 256;
    public static final String REBARCLASSNAME = "ReBarWindow32";
    public static final int REG_DWORD = 4;
    public static final int REG_OPTION_VOLATILE = 1;
    public static final int RGN_AND = 1;
    public static final int RGN_COPY = 5;
    public static final int RGN_DIFF = 4;
    public static final int RGN_ERROR = 0;
    public static final int RGN_OR = 2;
    public static final int SBP_ARROWBTN = 1;
    public static final int SBS_HORZ = 0;
    public static final int SBS_VERT = 1;
    public static final int SB_BOTH = 3;
    public static final int SB_BOTTOM = 7;
    public static final int SB_NONE = 0;
    public static final int SB_CONST_ALPHA = 1;
    public static final int SB_PIXEL_ALPHA = 2;
    public static final int SB_PREMULT_ALPHA = 4;
    public static final int SB_CTL = 2;
    public static final int SB_ENDSCROLL = 8;
    public static final int SB_HORZ = 0;
    public static final int SB_LINEDOWN = 1;
    public static final int SB_LINEUP = 0;
    public static final int SB_PAGEDOWN = 3;
    public static final int SB_PAGEUP = 2;
    public static final int SB_THUMBPOSITION = 4;
    public static final int SB_THUMBTRACK = 5;
    public static final int SB_TOP = 6;
    public static final int SB_VERT = 1;
    public static final int SC_CLOSE = 61536;
    public static final int SC_MOVE = 61456;
    public static final int SC_HSCROLL = 61568;
    public static final int SC_KEYMENU = 61696;
    public static final int SC_MAXIMIZE = 61488;
    public static final int SC_MINIMIZE = 61472;
    public static final int SC_NEXTWINDOW = 61504;
    public static final int SC_RESTORE = 61728;
    public static final int SC_SIZE = 61440;
    public static final int SC_TASKLIST = 61744;
    public static final int SC_VSCROLL = 61552;
    public static final int SCRBS_NORMAL = 1;
    public static final int SCRBS_HOT = 2;
    public static final int SCRBS_PRESSED = 3;
    public static final int SCRBS_DISABLED = 4;
    public static final int SET_FEATURE_ON_PROCESS = 2;
    public static final int SHADEBLENDCAPS = 120;
    public static final int SHGFI_ICON = 256;
    public static final int SHGFI_SMALLICON = 1;
    public static final int SHGFI_USEFILEATTRIBUTES = 16;
    public static final int SIGDN_FILESYSPATH = -2147123200;
    public static final int SIF_ALL = 23;
    public static final int SIF_DISABLENOSCROLL = 8;
    public static final int SIF_PAGE = 2;
    public static final int SIF_POS = 4;
    public static final int SIF_RANGE = 1;
    public static final int SIF_TRACKPOS = 16;
    public static final int SIZE_RESTORED = 0;
    public static final int SIZE_MINIMIZED = 1;
    public static final int SIZE_MAXIMIZED = 2;
    public static final int SM_CMONITORS = 80;
    public static final int SM_CXBORDER = 5;
    public static final int SM_CXCURSOR = 13;
    public static final int SM_CXDOUBLECLK = 36;
    public static final int SM_CYDOUBLECLK = 37;
    public static final int SM_CXEDGE = 45;
    public static final int SM_CXFOCUSBORDER = 83;
    public static final int SM_CXHSCROLL = 21;
    public static final int SM_CXICON = 11;
    public static final int SM_CYICON = 12;
    public static final int SM_CXVIRTUALSCREEN = 78;
    public static final int SM_CYVIRTUALSCREEN = 79;
    public static final int SM_CXSMICON = 49;
    public static final int SM_CYSMICON = 50;
    public static final int SM_CXSCREEN = 0;
    public static final int SM_XVIRTUALSCREEN = 76;
    public static final int SM_YVIRTUALSCREEN = 77;
    public static final int SM_CXVSCROLL = 2;
    public static final int SM_CYBORDER = 6;
    public static final int SM_CYCURSOR = 14;
    public static final int SM_CYEDGE = 46;
    public static final int SM_CYFOCUSBORDER = 84;
    public static final int SM_CYHSCROLL = 3;
    public static final int SM_CYMENU = 15;
    public static final int SM_CXMINTRACK = 34;
    public static final int SM_CYMINTRACK = 35;
    public static final int SM_CXMAXTRACK = 59;
    public static final int SM_CYMAXTRACK = 60;
    public static final int SM_CMOUSEBUTTONS = 43;
    public static final int SM_CYSCREEN = 1;
    public static final int SM_CYVSCROLL = 20;
    public static final int SM_DIGITIZER = 94;
    public static final int SM_MAXIMUMTOUCHES = 95;
    public static final int SPI_GETFONTSMOOTHINGTYPE = 8202;
    public static final int SPI_GETHIGHCONTRAST = 66;
    public static final int SPI_GETWORKAREA = 48;
    public static final int SPI_GETMOUSEVANISH = 4128;
    public static final int SPI_GETNONCLIENTMETRICS = 41;
    public static final int SPI_GETWHEELSCROLLCHARS = 108;
    public static final int SPI_GETWHEELSCROLLLINES = 104;
    public static final int SPI_GETCARETWIDTH = 8198;
    public static final int SPI_SETSIPINFO = 224;
    public static final int SPI_SETHIGHCONTRAST = 67;
    public static final int SRCAND = 8913094;
    public static final int SRCCOPY = 0xCC0020;
    public static final int SRCINVERT = 0x660046;
    public static final int SRCPAINT = 15597702;
    public static final int SS_BITMAP = 14;
    public static final int SS_CENTER = 1;
    public static final int SS_CENTERIMAGE = 512;
    public static final int SS_EDITCONTROL = 8192;
    public static final int SS_ICON = 3;
    public static final int SS_LEFT = 0;
    public static final int SS_LEFTNOWORDWRAP = 12;
    public static final int SS_NOTIFY = 256;
    public static final int SS_OWNERDRAW = 13;
    public static final int SS_REALSIZEIMAGE = 2048;
    public static final int SS_RIGHT = 2;
    public static final int SSA_FALLBACK = 32;
    public static final int SSA_GLYPHS = 128;
    public static final int SSA_METAFILE = 2048;
    public static final int SSA_LINK = 4096;
    public static final int STARTF_USESHOWWINDOW = 1;
    public static final int STATE_SYSTEM_INVISIBLE = 32768;
    public static final int STATE_SYSTEM_OFFSCREEN = 65536;
    public static final int STATE_SYSTEM_UNAVAILABLE = 1;
    public static final int STD_COPY = 1;
    public static final int STD_CUT = 0;
    public static final int STD_FILENEW = 6;
    public static final int STD_FILEOPEN = 7;
    public static final int STD_FILESAVE = 8;
    public static final int STD_PASTE = 2;
    public static final int STM_GETIMAGE = 371;
    public static final int STM_SETIMAGE = 370;
    public static final int SWP_ASYNCWINDOWPOS = 16384;
    public static final int SWP_DRAWFRAME = 32;
    public static final int SWP_FRAMECHANGED = 32;
    public static final int SWP_NOACTIVATE = 16;
    public static final int SWP_NOCOPYBITS = 256;
    public static final int SWP_NOMOVE = 2;
    public static final int SWP_NOREDRAW = 8;
    public static final int SWP_NOSIZE = 1;
    public static final int SWP_NOZORDER = 4;
    public static final int SW_ERASE = 4;
    public static final int SW_HIDE = 0;
    public static final int SW_INVALIDATE = 2;
    public static final int SW_MINIMIZE = 6;
    public static final int SW_PARENTOPENING = 3;
    public static final int SW_RESTORE = 9;
    public static final int SW_SCROLLCHILDREN = 1;
    public static final int SW_SHOW = 5;
    public static final int SW_SHOWMAXIMIZED = 3;
    public static final int SW_SHOWMINIMIZED = 2;
    public static final int SW_SHOWMINNOACTIVE = 7;
    public static final int SW_SHOWNA = 8;
    public static final int SW_SHOWNOACTIVATE = 4;
    public static final int SYSRGN = 4;
    public static final int SYSTEM_FONT = 13;
    public static final int S_OK = 0;
    public static final int TABP_BODY = 10;
    public static final int TBCDRF_USECDCOLORS = 0x800000;
    public static final int TBCDRF_NOBACKGROUND = 0x400000;
    public static final int TBIF_COMMAND = 32;
    public static final int TBIF_STATE = 4;
    public static final int TBIF_IMAGE = 1;
    public static final int TBIF_LPARAM = 16;
    public static final int TBIF_SIZE = 64;
    public static final int TBIF_STYLE = 8;
    public static final int TBIF_TEXT = 2;
    public static final int TB_GETEXTENDEDSTYLE = 1109;
    public static final int TB_GETRECT = 1075;
    public static final int TBM_GETLINESIZE = 1048;
    public static final int TBM_GETPAGESIZE = 1046;
    public static final int TBM_GETPOS = 1024;
    public static final int TBM_GETRANGEMAX = 1026;
    public static final int TBM_GETRANGEMIN = 1025;
    public static final int TBM_GETTHUMBRECT = 1049;
    public static final int TBM_SETLINESIZE = 1047;
    public static final int TBM_SETPAGESIZE = 1045;
    public static final int TBM_SETPOS = 1029;
    public static final int TBM_SETRANGEMAX = 1032;
    public static final int TBM_SETRANGEMIN = 1031;
    public static final int TBM_SETTICFREQ = 1044;
    public static final int TBN_DROPDOWN = -710;
    public static final int TBN_FIRST = -700;
    public static final int TBN_HOTITEMCHANGE = -713;
    public static final int TBSTATE_CHECKED = 1;
    public static final int TBSTATE_PRESSED = 2;
    public static final int TBSTYLE_CUSTOMERASE = 8192;
    public static final int TBSTYLE_DROPDOWN = 8;
    public static final int TBSTATE_ENABLED = 4;
    public static final int TBSTYLE_AUTOSIZE = 16;
    public static final int TBSTYLE_EX_DOUBLEBUFFER = 128;
    public static final int TBSTYLE_EX_DRAWDDARROWS = 1;
    public static final int TBSTYLE_EX_HIDECLIPPEDBUTTONS = 16;
    public static final int TBSTYLE_EX_MIXEDBUTTONS = 8;
    public static final int TBSTYLE_FLAT = 2048;
    public static final int TBSTYLE_LIST = 4096;
    public static final int TBSTYLE_TOOLTIPS = 256;
    public static final int TBSTYLE_TRANSPARENT = 32768;
    public static final int TBSTYLE_WRAPABLE = 512;
    public static final int TBS_AUTOTICKS = 1;
    public static final int TBS_BOTH = 8;
    public static final int TBS_DOWNISLEFT = 1024;
    public static final int TBS_HORZ = 0;
    public static final int TBS_VERT = 2;
    public static final int TB_ADDSTRING = 1101;
    public static final int TB_AUTOSIZE = 1057;
    public static final int TB_BUTTONCOUNT = 1048;
    public static final int TB_BUTTONSTRUCTSIZE = 1054;
    public static final int TB_COMMANDTOINDEX = 1049;
    public static final int TB_DELETEBUTTON = 1046;
    public static final int TB_ENDTRACK = 8;
    public static final int TB_GETBUTTON = 1047;
    public static final int TB_GETBUTTONINFO = 1087;
    public static final int TB_GETBUTTONSIZE = 1082;
    public static final int TB_GETBUTTONTEXT = 1099;
    public static final int TB_GETDISABLEDIMAGELIST = 1079;
    public static final int TB_GETHOTIMAGELIST = 1077;
    public static final int TB_GETHOTITEM = 1095;
    public static final int TB_GETIMAGELIST = 1073;
    public static final int TB_GETITEMRECT = 1053;
    public static final int TB_GETPADDING = 1110;
    public static final int TB_GETROWS = 1064;
    public static final int TB_GETSTATE = 1042;
    public static final int TB_GETTOOLTIPS = 1059;
    public static final int TB_INSERTBUTTON = 1091;
    public static final int TB_LOADIMAGES = 1074;
    public static final int TB_MAPACCELERATOR = 1114;
    public static final int TB_SETBITMAPSIZE = 1056;
    public static final int TB_SETBUTTONINFO = 1088;
    public static final int TB_SETBUTTONSIZE = 1055;
    public static final int TB_SETDISABLEDIMAGELIST = 1078;
    public static final int TB_SETEXTENDEDSTYLE = 1108;
    public static final int TB_SETHOTIMAGELIST = 1076;
    public static final int TB_SETHOTITEM = 1096;
    public static final int TB_SETIMAGELIST = 1072;
    public static final int TB_SETPARENT = 1061;
    public static final int TB_SETROWS = 1063;
    public static final int TB_SETSTATE = 1041;
    public static final int TB_THUMBPOSITION = 4;
    public static final int TBPF_NOPROGRESS = 0;
    public static final int TBPF_INDETERMINATE = 1;
    public static final int TBPF_NORMAL = 2;
    public static final int TBPF_ERROR = 4;
    public static final int TBPF_PAUSED = 8;
    public static final int TCIF_IMAGE = 2;
    public static final int TCIF_TEXT = 1;
    public static final int TCI_SRCCHARSET = 1;
    public static final int TCI_SRCCODEPAGE = 2;
    public static final int TCM_ADJUSTRECT = 4904;
    public static final int TCM_DELETEITEM = 4872;
    public static final int TCM_GETCURSEL = 4875;
    public static final int TCM_GETITEMCOUNT = 4868;
    public static final int TCM_GETITEMRECT = 4874;
    public static final int TCM_GETTOOLTIPS = 4909;
    public static final int TCM_HITTEST = 4877;
    public static final int TCM_INSERTITEM = 4926;
    public static final int TCM_SETCURSEL = 4876;
    public static final int TCM_SETIMAGELIST = 4867;
    public static final int TCM_SETITEM = 4925;
    public static final int TCN_SELCHANGE = -551;
    public static final int TCN_SELCHANGING = -552;
    public static final int TCS_BOTTOM = 2;
    public static final int TCS_FOCUSNEVER = 32768;
    public static final int TCS_MULTILINE = 512;
    public static final int TCS_TABS = 0;
    public static final int TCS_TOOLTIPS = 16384;
    public static final int TECHNOLOGY = 2;
    public static final int TF_ATTR_INPUT = 0;
    public static final int TF_ATTR_TARGET_CONVERTED = 1;
    public static final int TF_ATTR_CONVERTED = 2;
    public static final int TF_ATTR_TARGET_NOTCONVERTED = 3;
    public static final int TF_ATTR_INPUT_ERROR = 4;
    public static final int TF_ATTR_FIXEDCONVERTED = 5;
    public static final int TF_ATTR_OTHER = -1;
    public static final int TF_CT_NONE = 0;
    public static final int TF_CT_SYSCOLOR = 1;
    public static final int TF_CT_COLORREF = 2;
    public static final int TF_LS_NONE = 0;
    public static final int TF_LS_SOLID = 1;
    public static final int TF_LS_DOT = 2;
    public static final int TF_LS_DASH = 3;
    public static final int TF_LS_SQUIGGLE = 4;
    public static final int TME_HOVER = 1;
    public static final int TME_LEAVE = 2;
    public static final int TME_QUERY = 0x40000000;
    public static final int TMPF_VECTOR = 2;
    public static final int TMT_CONTENTMARGINS = 3602;
    public static final int TOUCHEVENTF_MOVE = 1;
    public static final int TOUCHEVENTF_DOWN = 2;
    public static final int TOUCHEVENTF_UP = 4;
    public static final int TOUCHEVENTF_INRANGE = 8;
    public static final int TOUCHEVENTF_PRIMARY = 16;
    public static final int TOUCHEVENTF_NOCOALESCE = 32;
    public static final int TOUCHEVENTF_PALM = 128;
    public static final String TOOLBARCLASSNAME = "ToolbarWindow32";
    public static final String TOOLTIPS_CLASS = "tooltips_class32";
    public static final int TPM_LEFTALIGN = 0;
    public static final int TPM_LEFTBUTTON = 0;
    public static final int TPM_RIGHTBUTTON = 2;
    public static final int TPM_RIGHTALIGN = 8;
    public static final String TRACKBAR_CLASS = "msctls_trackbar32";
    public static final int TRANSPARENT = 1;
    public static final int TREIS_DISABLED = 4;
    public static final int TREIS_HOT = 2;
    public static final int TREIS_NORMAL = 1;
    public static final int TREIS_SELECTED = 3;
    public static final int TREIS_SELECTEDNOTFOCUS = 5;
    public static final int TS_TRUE = 1;
    public static final int TTDT_AUTOMATIC = 0;
    public static final int TTDT_RESHOW = 1;
    public static final int TTDT_AUTOPOP = 2;
    public static final int TTDT_INITIAL = 3;
    public static final int TTF_ABSOLUTE = 128;
    public static final int TTF_IDISHWND = 1;
    public static final int TTF_SUBCLASS = 16;
    public static final int TTF_RTLREADING = 4;
    public static final int TTF_TRACK = 32;
    public static final int TTF_TRANSPARENT = 256;
    public static final int TTI_NONE = 0;
    public static final int TTI_INFO = 1;
    public static final int TTI_WARNING = 2;
    public static final int TTI_ERROR = 3;
    public static final int TTM_ACTIVATE = 1025;
    public static final int TTM_ADDTOOL = 1074;
    public static final int TTM_ADJUSTRECT = 1055;
    public static final int TTM_GETCURRENTTOOL = 1083;
    public static final int TTM_GETDELAYTIME = 1045;
    public static final int TTM_DELTOOL = 1075;
    public static final int TTM_GETTOOLINFO = 1077;
    public static final int TTM_GETTOOLCOUNT = 1037;
    public static final int TTM_NEWTOOLRECT = 1076;
    public static final int TTM_POP = 1052;
    public static final int TTM_SETDELAYTIME = 1027;
    public static final int TTM_SETMAXTIPWIDTH = 1048;
    public static final int TTM_SETTITLE = 1057;
    public static final int TTM_TRACKPOSITION = 1042;
    public static final int TTM_TRACKACTIVATE = 1041;
    public static final int TTM_UPDATE = 1053;
    public static final int TTM_UPDATETIPTEXT = 1081;
    public static final int TTN_FIRST = -520;
    public static final int TTN_GETDISPINFO = -530;
    public static final int TTN_POP = -522;
    public static final int TTN_SHOW = -521;
    public static final int TTS_ALWAYSTIP = 1;
    public static final int TTS_BALLOON = 64;
    public static final int TTS_NOANIMATE = 16;
    public static final int TTS_NOFADE = 32;
    public static final int TTS_NOPREFIX = 2;
    public static final int TV_FIRST = 4352;
    public static final int TVE_COLLAPSE = 1;
    public static final int TVE_COLLAPSERESET = 32768;
    public static final int TVE_EXPAND = 2;
    public static final int TVGN_CARET = 9;
    public static final int TVGN_CHILD = 4;
    public static final int TVGN_DROPHILITED = 8;
    public static final int TVGN_FIRSTVISIBLE = 5;
    public static final int TVGN_LASTVISIBLE = 10;
    public static final int TVGN_NEXT = 1;
    public static final int TVGN_NEXTVISIBLE = 6;
    public static final int TVGN_PARENT = 3;
    public static final int TVGN_PREVIOUS = 2;
    public static final int TVGN_PREVIOUSVISIBLE = 7;
    public static final int TVGN_ROOT = 0;
    public static final int TVHT_ONITEM = 70;
    public static final int TVHT_ONITEMBUTTON = 16;
    public static final int TVHT_ONITEMICON = 2;
    public static final int TVHT_ONITEMINDENT = 8;
    public static final int TVHT_ONITEMRIGHT = 32;
    public static final int TVHT_ONITEMLABEL = 4;
    public static final int TVHT_ONITEMSTATEICON = 64;
    public static final int TVIF_HANDLE = 16;
    public static final int TVIF_IMAGE = 2;
    public static final int TVIF_INTEGRAL = 128;
    public static final int TVIF_PARAM = 4;
    public static final int TVIF_SELECTEDIMAGE = 32;
    public static final int TVIF_STATE = 8;
    public static final int TVIF_TEXT = 1;
    public static final int TVIS_DROPHILITED = 8;
    public static final int TVIS_EXPANDED = 32;
    public static final int TVIS_SELECTED = 2;
    public static final int TVIS_STATEIMAGEMASK = 61440;
    public static final long TVI_FIRST = -65535L;
    public static final long TVI_LAST = -65534L;
    public static final long TVI_ROOT = -65536L;
    public static final long TVI_SORT = -65533L;
    public static final int TVM_CREATEDRAGIMAGE = 4370;
    public static final int TVM_DELETEITEM = 4353;
    public static final int TVM_ENSUREVISIBLE = 4372;
    public static final int TVM_EXPAND = 4354;
    public static final int TVM_GETBKCOLOR = 4383;
    public static final int TVM_GETCOUNT = 4357;
    public static final int TVM_GETEXTENDEDSTYLE = 4397;
    public static final int TVM_GETIMAGELIST = 4360;
    public static final int TVM_GETITEM = 4414;
    public static final int TVM_GETITEMHEIGHT = 4380;
    public static final int TVM_GETITEMRECT = 4356;
    public static final int TVM_GETITEMSTATE = 4391;
    public static final int TVM_GETNEXTITEM = 4362;
    public static final int TVM_GETTEXTCOLOR = 4384;
    public static final int TVM_GETTOOLTIPS = 4377;
    public static final int TVM_GETVISIBLECOUNT = 4368;
    public static final int TVM_HITTEST = 4369;
    public static final int TVM_INSERTITEM = 4402;
    public static final int TVM_MAPACCIDTOHTREEITEM = 4394;
    public static final int TVM_MAPHTREEITEMTOACCID = 4395;
    public static final int TVM_SELECTITEM = 4363;
    public static final int TVM_SETBKCOLOR = 4381;
    public static final int TVM_SETEXTENDEDSTYLE = 4396;
    public static final int TVM_SETIMAGELIST = 4361;
    public static final int TVM_SETINDENT = 4359;
    public static final int TVM_SETINSERTMARK = 4378;
    public static final int TVM_SETITEM = 4415;
    public static final int TVM_SETITEMHEIGHT = 4379;
    public static final int TVM_SETSCROLLTIME = 4385;
    public static final int TVM_SETTEXTCOLOR = 4382;
    public static final int TVM_SORTCHILDREN = 4371;
    public static final int TVM_SORTCHILDRENCB = 4373;
    public static final int TVN_BEGINDRAG = -456;
    public static final int TVN_BEGINRDRAG = -457;
    public static final int TVN_FIRST = -400;
    public static final int TVN_GETDISPINFO = -452;
    public static final int TVN_ITEMCHANGING = -417;
    public static final int TVN_ITEMEXPANDED = -455;
    public static final int TVN_ITEMEXPANDING = -454;
    public static final int TVN_SELCHANGED = -451;
    public static final int TVN_SELCHANGING = -450;
    public static final int TVP_GLYPH = 2;
    public static final int TVP_TREEITEM = 1;
    public static final int TVSIL_NORMAL = 0;
    public static final int TVSIL_STATE = 2;
    public static final int TVS_DISABLEDRAGDROP = 16;
    public static final int TVS_EX_AUTOHSCROLL = 32;
    public static final int TVS_EX_DOUBLEBUFFER = 4;
    public static final int TVS_EX_DIMMEDCHECKBOXES = 512;
    public static final int TVS_EX_DRAWIMAGEASYNC = 1024;
    public static final int TVS_EX_EXCLUSIONCHECKBOXES = 256;
    public static final int TVS_EX_FADEINOUTEXPANDOS = 64;
    public static final int TVS_EX_MULTISELECT = 2;
    public static final int TVS_EX_NOINDENTSTATE = 8;
    public static final int TVS_EX_PARTIALCHECKBOXES = 128;
    public static final int TVS_EX_RICHTOOLTIP = 16;
    public static final int TVS_FULLROWSELECT = 4096;
    public static final int TVS_HASBUTTONS = 1;
    public static final int TVS_HASLINES = 2;
    public static final int TVS_LINESATROOT = 4;
    public static final int TVS_NOHSCROLL = 32768;
    public static final int TVS_NONEVENHEIGHT = 16384;
    public static final int TVS_NOSCROLL = 8192;
    public static final int TVS_NOTOOLTIPS = 128;
    public static final int TVS_SHOWSELALWAYS = 32;
    public static final int TVS_TRACKSELECT = 512;
    public static final int UDM_GETACCEL = 1132;
    public static final int UDM_GETRANGE32 = 1136;
    public static final int UDM_GETPOS32 = 1138;
    public static final int UDM_SETACCEL = 1131;
    public static final int UDM_SETRANGE32 = 1135;
    public static final int UDM_SETPOS32 = 1137;
    public static final int UDN_DELTAPOS = -722;
    public static final int UDS_ALIGNLEFT = 8;
    public static final int UDS_ALIGNRIGHT = 4;
    public static final int UDS_AUTOBUDDY = 16;
    public static final int UDS_WRAP = 1;
    public static final int UIS_CLEAR = 2;
    public static final int UIS_INITIALIZE = 3;
    public static final int UIS_SET = 1;
    public static final int UISF_HIDEACCEL = 2;
    public static final int UISF_HIDEFOCUS = 1;
    public static final String UPDOWN_CLASS = "msctls_updown32";
    public static final int USP_E_SCRIPT_NOT_IN_FONT = -2147220992;
    public static final int VERTRES = 10;
    public static final int VK_BACK = 8;
    public static final int VK_CANCEL = 3;
    public static final int VK_CAPITAL = 20;
    public static final int VK_CONTROL = 17;
    public static final int VK_DECIMAL = 110;
    public static final int VK_DELETE = 46;
    public static final int VK_DIVIDE = 111;
    public static final int VK_DOWN = 40;
    public static final int VK_END = 35;
    public static final int VK_ESCAPE = 27;
    public static final int VK_F1 = 112;
    public static final int VK_F10 = 121;
    public static final int VK_F11 = 122;
    public static final int VK_F12 = 123;
    public static final int VK_F13 = 124;
    public static final int VK_F14 = 125;
    public static final int VK_F15 = 126;
    public static final int VK_F16 = 127;
    public static final int VK_F17 = 128;
    public static final int VK_F18 = 129;
    public static final int VK_F19 = 130;
    public static final int VK_F20 = 131;
    public static final int VK_F2 = 113;
    public static final int VK_F3 = 114;
    public static final int VK_F4 = 115;
    public static final int VK_F5 = 116;
    public static final int VK_F6 = 117;
    public static final int VK_F7 = 118;
    public static final int VK_F8 = 119;
    public static final int VK_F9 = 120;
    public static final int VK_HANJA = 25;
    public static final int VK_HOME = 36;
    public static final int VK_INSERT = 45;
    public static final int VK_L = 76;
    public static final int VK_LBUTTON = 1;
    public static final int VK_LEFT = 37;
    public static final int VK_LCONTROL = 162;
    public static final int VK_LMENU = 164;
    public static final int VK_LSHIFT = 160;
    public static final int VK_MBUTTON = 4;
    public static final int VK_MENU = 18;
    public static final int VK_MULTIPLY = 106;
    public static final int VK_N = 78;
    public static final int VK_O = 79;
    public static final int VK_NEXT = 34;
    public static final int VK_NUMLOCK = 144;
    public static final int VK_NUMPAD0 = 96;
    public static final int VK_NUMPAD1 = 97;
    public static final int VK_NUMPAD2 = 98;
    public static final int VK_NUMPAD3 = 99;
    public static final int VK_NUMPAD4 = 100;
    public static final int VK_NUMPAD5 = 101;
    public static final int VK_NUMPAD6 = 102;
    public static final int VK_NUMPAD7 = 103;
    public static final int VK_NUMPAD8 = 104;
    public static final int VK_NUMPAD9 = 105;
    public static final int VK_PAUSE = 19;
    public static final int VK_PRIOR = 33;
    public static final int VK_RBUTTON = 2;
    public static final int VK_RETURN = 13;
    public static final int VK_RIGHT = 39;
    public static final int VK_RCONTROL = 163;
    public static final int VK_RMENU = 165;
    public static final int VK_RSHIFT = 161;
    public static final int VK_SCROLL = 145;
    public static final int VK_SEPARATOR = 108;
    public static final int VK_SHIFT = 16;
    public static final int VK_SNAPSHOT = 44;
    public static final int VK_SPACE = 32;
    public static final int VK_SUBTRACT = 109;
    public static final int VK_TAB = 9;
    public static final int VK_UP = 38;
    public static final int VK_XBUTTON1 = 5;
    public static final int VK_XBUTTON2 = 6;
    public static final int VK_ADD = 107;
    public static final int VT_BOOL = 11;
    public static final int VT_LPWSTR = 31;
    public static final short VARIANT_TRUE = -1;
    public static final short VARIANT_FALSE = 0;
    public static final short WA_CLICKACTIVE = 2;
    public static final String WC_HEADER = "SysHeader32";
    public static final String WC_LINK = "SysLink";
    public static final String WC_LISTVIEW = "SysListView32";
    public static final String WC_TABCONTROL = "SysTabControl32";
    public static final String WC_TREEVIEW = "SysTreeView32";
    public static final int WINDING = 2;
    public static final int WH_CBT = 5;
    public static final int WH_GETMESSAGE = 3;
    public static final int WH_MSGFILTER = -1;
    public static final int WH_FOREGROUNDIDLE = 11;
    public static final int WHEEL_DELTA = 120;
    public static final int WHEEL_PAGESCROLL = -1;
    public static final int WHITE_BRUSH = 0;
    public static final int WHITENESS = 16711778;
    public static final int WM_ACTIVATE = 6;
    public static final int WM_ACTIVATEAPP = 28;
    public static final int WM_APP = 32768;
    public static final int WM_DWMCOLORIZATIONCOLORCHANGED = 800;
    public static final int WM_CANCELMODE = 31;
    public static final int WM_CAPTURECHANGED = 533;
    public static final int WM_CHANGEUISTATE = 295;
    public static final int WM_CHAR = 258;
    public static final int WM_CLEAR = 771;
    public static final int WM_CLOSE = 16;
    public static final int WM_COMMAND = 273;
    public static final int WM_CONTEXTMENU = 123;
    public static final int WM_COPY = 769;
    public static final int WM_CREATE = 1;
    public static final int WM_CTLCOLORBTN = 309;
    public static final int WM_CTLCOLORDLG = 310;
    public static final int WM_CTLCOLOREDIT = 307;
    public static final int WM_CTLCOLORLISTBOX = 308;
    public static final int WM_CTLCOLORMSGBOX = 306;
    public static final int WM_CTLCOLORSCROLLBAR = 311;
    public static final int WM_CTLCOLORSTATIC = 312;
    public static final int WM_CUT = 768;
    public static final int WM_DEADCHAR = 259;
    public static final int WM_DESTROY = 2;
    public static final int WM_DPICHANGED = 736;
    public static final int WM_DRAWITEM = 43;
    public static final int WM_ENDSESSION = 22;
    public static final int WM_ENTERIDLE = 289;
    public static final int WM_ERASEBKGND = 20;
    public static final int WM_GESTURE = 281;
    public static final int WM_GETDLGCODE = 135;
    public static final int WM_GETFONT = 49;
    public static final int WM_GETOBJECT = 61;
    public static final int WM_GETMINMAXINFO = 36;
    public static final int WM_HELP = 83;
    public static final int WM_HOTKEY = 786;
    public static final int WM_HSCROLL = 276;
    public static final int WM_IME_CHAR = 646;
    public static final int WM_IME_COMPOSITION = 271;
    public static final int WM_IME_COMPOSITION_START = 269;
    public static final int WM_IME_ENDCOMPOSITION = 270;
    public static final int WM_INITDIALOG = 272;
    public static final int WM_INITMENUPOPUP = 279;
    public static final int WM_INPUTLANGCHANGE = 81;
    public static final int WM_KEYDOWN = 256;
    public static final int WM_KEYFIRST = 256;
    public static final int WM_KEYLAST = 264;
    public static final int WM_KEYUP = 257;
    public static final int WM_KILLFOCUS = 8;
    public static final int WM_LBUTTONDBLCLK = 515;
    public static final int WM_LBUTTONDOWN = 513;
    public static final int WM_LBUTTONUP = 514;
    public static final int WM_MBUTTONDBLCLK = 521;
    public static final int WM_MBUTTONDOWN = 519;
    public static final int WM_MBUTTONUP = 520;
    public static final int WM_MEASUREITEM = 44;
    public static final int WM_MENUCHAR = 288;
    public static final int WM_MENUSELECT = 287;
    public static final int WM_MOUSEACTIVATE = 33;
    public static final int WM_MOUSEFIRST = 512;
    public static final int WM_MOUSEHOVER = 673;
    public static final int WM_MOUSELEAVE = 675;
    public static final int WM_MOUSEMOVE = 512;
    public static final int WM_MOUSEWHEEL = 522;
    public static final int WM_MOUSEHWHEEL = 526;
    public static final int WM_MOUSELAST = 525;
    public static final int WM_MOVE = 3;
    public static final int WM_NCACTIVATE = 134;
    public static final int WM_NCCALCSIZE = 131;
    public static final int WM_NCHITTEST = 132;
    public static final int WM_NCLBUTTONDOWN = 161;
    public static final int WM_NCPAINT = 133;
    public static final int WM_NOTIFY = 78;
    public static final int WM_NULL = 0;
    public static final int WM_PAINT = 15;
    public static final int WM_PARENTNOTIFY = 528;
    public static final int WM_ENTERMENULOOP = 529;
    public static final int WM_EXITMENULOOP = 530;
    public static final int WM_ENTERSIZEMOVE = 561;
    public static final int WM_EXITSIZEMOVE = 562;
    public static final int WM_PASTE = 770;
    public static final int WM_PRINT = 791;
    public static final int WM_PRINTCLIENT = 792;
    public static final int WM_QUERYENDSESSION = 17;
    public static final int WM_QUERYOPEN = 19;
    public static final int WM_QUERYUISTATE = 297;
    public static final int WM_RBUTTONDBLCLK = 518;
    public static final int WM_RBUTTONDOWN = 516;
    public static final int WM_RBUTTONUP = 517;
    public static final int WM_SETCURSOR = 32;
    public static final int WM_SETFOCUS = 7;
    public static final int WM_SETFONT = 48;
    public static final int WM_SETICON = 128;
    public static final int WM_SETREDRAW = 11;
    public static final int WM_SETTEXT = 12;
    public static final int WM_SETTINGCHANGE = 26;
    public static final int WM_SHOWWINDOW = 24;
    public static final int WM_SIZE = 5;
    public static final int WM_SYSCHAR = 262;
    public static final int WM_SYSCOLORCHANGE = 21;
    public static final int WM_SYSCOMMAND = 274;
    public static final int WM_SYSKEYDOWN = 260;
    public static final int WM_SYSKEYUP = 261;
    public static final int WM_TABLET_FLICK = 715;
    public static final int WM_TIMER = 275;
    public static final int WM_THEMECHANGED = 794;
    public static final int WM_TOUCH = 576;
    public static final int WM_UNDO = 772;
    public static final int WM_UNINITMENUPOPUP = 293;
    public static final int WM_UPDATEUISTATE = 296;
    public static final int WM_USER = 1024;
    public static final int WM_VSCROLL = 277;
    public static final int WM_WINDOWPOSCHANGED = 71;
    public static final int WM_WINDOWPOSCHANGING = 70;
    public static final int WPF_RESTORETOMAXIMIZED = 2;
    public static final int WS_BORDER = 0x800000;
    public static final int WS_CAPTION = 0xC00000;
    public static final int WS_CHILD = 0x40000000;
    public static final int WS_CLIPCHILDREN = 0x2000000;
    public static final int WS_CLIPSIBLINGS = 0x4000000;
    public static final int WS_DISABLED = 0x4000000;
    public static final int WS_EX_APPWINDOW = 262144;
    public static final int WS_EX_CAPTIONOKBTN = Integer.MIN_VALUE;
    public static final int WS_EX_CLIENTEDGE = 512;
    public static final int WS_EX_COMPOSITED = 0x2000000;
    public static final int WS_EX_DLGMODALFRAME = 1;
    public static final int WS_EX_LAYERED = 524288;
    public static final int WS_EX_LAYOUTRTL = 0x400000;
    public static final int WS_EX_LEFTSCROLLBAR = 16384;
    public static final int WS_EX_MDICHILD = 64;
    public static final int WS_EX_NOINHERITLAYOUT = 0x100000;
    public static final int WS_EX_NOACTIVATE = 0x8000000;
    public static final int WS_EX_RIGHT = 4096;
    public static final int WS_EX_RTLREADING = 8192;
    public static final int WS_EX_STATICEDGE = 131072;
    public static final int WS_EX_TOOLWINDOW = 128;
    public static final int WS_EX_TOPMOST = 8;
    public static final int WS_EX_TRANSPARENT = 32;
    public static final int WS_HSCROLL = 0x100000;
    public static final int WS_MAXIMIZEBOX = 65536;
    public static final int WS_MINIMIZEBOX = 131072;
    public static final int WS_OVERLAPPED = 0;
    public static final int WS_OVERLAPPEDWINDOW = 0xCF0000;
    public static final int WS_POPUP = Integer.MIN_VALUE;
    public static final int WS_SYSMENU = 524288;
    public static final int WS_TABSTOP = 65536;
    public static final int WS_THICKFRAME = 262144;
    public static final int WS_VISIBLE = 0x10000000;
    public static final int WS_VSCROLL = 0x200000;
    public static final int WM_XBUTTONDOWN = 523;
    public static final int WM_XBUTTONUP = 524;
    public static final int WM_XBUTTONDBLCLK = 525;
    public static final int XBUTTON1 = 1;
    public static final int XBUTTON2 = 2;
    public static final int PROCESS_DUP_HANDLE = 64;
    public static final int PROCESS_VM_READ = 16;
    public static final int DUPLICATE_SAME_ACCESS = 2;

    public static int VERSION(int n, int n2) {
        return n << 16 | n2;
    }

    public static final native int ACCEL_sizeof();

    public static final native int ACTCTX_sizeof();

    public static final native int BITMAP_sizeof();

    public static final native int BITMAPINFOHEADER_sizeof();

    public static final native int BLENDFUNCTION_sizeof();

    public static final native int BP_PAINTPARAMS_sizeof();

    public static final native int BUTTON_IMAGELIST_sizeof();

    public static final native int CANDIDATEFORM_sizeof();

    public static final native int CHOOSECOLOR_sizeof();

    public static final native int CHOOSEFONT_sizeof();

    public static final native int COMBOBOXINFO_sizeof();

    public static final native int COMPOSITIONFORM_sizeof();

    public static final native int CREATESTRUCT_sizeof();

    public static final native int DEVMODE_sizeof();

    public static final native int DIBSECTION_sizeof();

    public static final native int DOCHOSTUIINFO_sizeof();

    public static final native int DOCINFO_sizeof();

    public static final native int DRAWITEMSTRUCT_sizeof();

    public static final native int DROPFILES_sizeof();

    public static final native int EMR_sizeof();

    public static final native int EMREXTCREATEFONTINDIRECTW_sizeof();

    public static final native int EXTLOGFONTW_sizeof();

    public static final native int FLICK_DATA_sizeof();

    public static final native int FLICK_POINT_sizeof();

    public static final native int GCP_RESULTS_sizeof();

    public static final native int GESTURECONFIG_sizeof();

    public static final native int GESTUREINFO_sizeof();

    public static final native int GRADIENT_RECT_sizeof();

    public static final native int GUITHREADINFO_sizeof();

    public static final native int HDITEM_sizeof();

    public static final native int HDLAYOUT_sizeof();

    public static final native int HDHITTESTINFO_sizeof();

    public static final native int HELPINFO_sizeof();

    public static final native int HIGHCONTRAST_sizeof();

    public static final native int ICONINFO_sizeof();

    public static final native int CIDA_sizeof();

    public static final native int INITCOMMONCONTROLSEX_sizeof();

    public static final native int INPUT_sizeof();

    public static final native int KEYBDINPUT_sizeof();

    public static final native int LITEM_sizeof();

    public static final native int LOGBRUSH_sizeof();

    public static final native int LOGFONT_sizeof();

    public static final native int LOGPEN_sizeof();

    public static final native int LVCOLUMN_sizeof();

    public static final native int LVHITTESTINFO_sizeof();

    public static final native int LVITEM_sizeof();

    public static final native int LVINSERTMARK_sizeof();

    public static final native int MARGINS_sizeof();

    public static final native int MCHITTESTINFO_sizeof();

    public static final native int MEASUREITEMSTRUCT_sizeof();

    public static final native int MENUBARINFO_sizeof();

    public static final native int MENUINFO_sizeof();

    public static final native int MENUITEMINFO_sizeof();

    public static final native int MINMAXINFO_sizeof();

    public static final native int MOUSEINPUT_sizeof();

    public static final native int MONITORINFO_sizeof();

    public static final native int MSG_sizeof();

    public static final native int NMCUSTOMDRAW_sizeof();

    public static final native int NMHDR_sizeof();

    public static final native int NMHEADER_sizeof();

    public static final native int NMLINK_sizeof();

    public static final native int NMLISTVIEW_sizeof();

    public static final native int NMLVCUSTOMDRAW_sizeof();

    public static final native int NMLVDISPINFO_sizeof();

    public static final native int NMLVFINDITEM_sizeof();

    public static final native int NMLVODSTATECHANGE_sizeof();

    public static final native int NMREBARCHEVRON_sizeof();

    public static final native int NMREBARCHILDSIZE_sizeof();

    public static final native int NMTBHOTITEM_sizeof();

    public static final native int NMTREEVIEW_sizeof();

    public static final native int NMTOOLBAR_sizeof();

    public static final native int NMTTDISPINFO_sizeof();

    public static final native int NMTTCUSTOMDRAW_sizeof();

    public static final native int NMTBCUSTOMDRAW_sizeof();

    public static final native int NMTVCUSTOMDRAW_sizeof();

    public static final native int NMTVDISPINFO_sizeof();

    public static final native int NMTVITEMCHANGE_sizeof();

    public static final native int NMUPDOWN_sizeof();

    public static final native int NONCLIENTMETRICS_sizeof();

    public static final native int NOTIFYICONDATA_V2_SIZE();

    public static final native int OUTLINETEXTMETRIC_sizeof();

    public static final native int OSVERSIONINFOEX_sizeof();

    public static final native int PAINTSTRUCT_sizeof();

    public static final native int POINT_sizeof();

    public static final native int PRINTDLG_sizeof();

    public static final native int PROCESS_INFORMATION_sizeof();

    public static final native int PROPVARIANT_sizeof();

    public static final native int PROPERTYKEY_sizeof();

    public static final native int REBARBANDINFO_sizeof();

    public static final native int RECT_sizeof();

    public static final native int SAFEARRAY_sizeof();

    public static final native int SAFEARRAYBOUND_sizeof();

    public static final native int SCRIPT_ANALYSIS_sizeof();

    public static final native int SCRIPT_CONTROL_sizeof();

    public static final native int SCRIPT_FONTPROPERTIES_sizeof();

    public static final native int SCRIPT_ITEM_sizeof();

    public static final native int SCRIPT_LOGATTR_sizeof();

    public static final native int SCRIPT_PROPERTIES_sizeof();

    public static final native int SCRIPT_STATE_sizeof();

    public static final native int SCRIPT_STRING_ANALYSIS_sizeof();

    public static final native int SCROLLBARINFO_sizeof();

    public static final native int SCROLLINFO_sizeof();

    public static final native int SHDRAGIMAGE_sizeof();

    public static final native int SHELLEXECUTEINFO_sizeof();

    public static final native int SHFILEINFO_sizeof();

    public static final native int SIZE_sizeof();

    public static final native int STARTUPINFO_sizeof();

    public static final native int SYSTEMTIME_sizeof();

    public static final native int TBBUTTON_sizeof();

    public static final native int TBBUTTONINFO_sizeof();

    public static final native int TCITEM_sizeof();

    public static final native int TCHITTESTINFO_sizeof();

    public static final native int TEXTMETRIC_sizeof();

    public static final native int TF_DA_COLOR_sizeof();

    public static final native int TF_DISPLAYATTRIBUTE_sizeof();

    public static final native int TOOLINFO_sizeof();

    public static final native int TOUCHINPUT_sizeof();

    public static final native int TRACKMOUSEEVENT_sizeof();

    public static final native int TRIVERTEX_sizeof();

    public static final native int TVHITTESTINFO_sizeof();

    public static final native int TVINSERTSTRUCT_sizeof();

    public static final native int TVITEM_sizeof();

    public static final native int TVSORTCB_sizeof();

    public static final native int UDACCEL_sizeof();

    public static final native int WINDOWPLACEMENT_sizeof();

    public static final native int WINDOWPOS_sizeof();

    public static final native int WNDCLASS_sizeof();

    public static final long AddFontResourceEx(TCHAR tCHAR, int n, long l2) {
        char[] cArray = tCHAR == null ? null : tCHAR.chars;
        return OS.AddFontResourceEx(cArray, n, l2);
    }

    public static final int AssocQueryString(int n, int n2, TCHAR tCHAR, TCHAR tCHAR2, TCHAR tCHAR3, int[] nArray) {
        char[] cArray = tCHAR == null ? null : tCHAR.chars;
        char[] cArray2 = tCHAR2 == null ? null : tCHAR2.chars;
        char[] cArray3 = tCHAR3 == null ? null : tCHAR3.chars;
        return OS.AssocQueryString(n, n2, cArray, cArray2, cArray3, nArray);
    }

    public static final long CreateDC(TCHAR tCHAR, TCHAR tCHAR2, long l2, long l3) {
        char[] cArray = tCHAR == null ? null : tCHAR.chars;
        char[] cArray2 = tCHAR2 == null ? null : tCHAR2.chars;
        return OS.CreateDC(cArray, cArray2, l2, l3);
    }

    public static final long CreateWindowEx(int n, TCHAR tCHAR, TCHAR tCHAR2, int n2, int n3, int n4, int n5, int n6, long l2, long l3, long l4, CREATESTRUCT cREATESTRUCT) {
        char[] cArray = tCHAR == null ? null : tCHAR.chars;
        char[] cArray2 = tCHAR2 == null ? null : tCHAR2.chars;
        return OS.CreateWindowEx(n, cArray, cArray2, n2, n3, n4, n5, n6, l2, l3, l4, cREATESTRUCT);
    }

    public static final int DocumentProperties(long l2, long l3, TCHAR tCHAR, long l4, long l5, int n) {
        char[] cArray = tCHAR == null ? null : tCHAR.chars;
        return OS.DocumentProperties(l2, l3, cArray, l4, l5, n);
    }

    public static final int DrawText(long l2, TCHAR tCHAR, int n, RECT rECT, int n2) {
        char[] cArray = tCHAR == null ? null : tCHAR.chars;
        return OS.DrawText(l2, cArray, n, rECT, n2);
    }

    public static final int ExpandEnvironmentStrings(TCHAR tCHAR, TCHAR tCHAR2, int n) {
        char[] cArray = tCHAR == null ? null : tCHAR.chars;
        char[] cArray2 = tCHAR2 == null ? null : tCHAR2.chars;
        return OS.ExpandEnvironmentStrings(cArray, cArray2, n);
    }

    public static final int ExtractIconEx(TCHAR tCHAR, int n, long[] lArray, long[] lArray2, int n2) {
        char[] cArray = tCHAR == null ? null : tCHAR.chars;
        return OS.ExtractIconEx(cArray, n, lArray, lArray2, n2);
    }

    public static final boolean GetClassInfo(long l2, TCHAR tCHAR, WNDCLASS wNDCLASS) {
        char[] cArray = tCHAR == null ? null : tCHAR.chars;
        boolean bl = OS.GetClassInfo(l2, cArray, wNDCLASS);
        wNDCLASS.lpszClassName = 0L;
        return bl;
    }

    public static final int GetLocaleInfo(int n, int n2, TCHAR tCHAR, int n3) {
        char[] cArray = tCHAR == null ? null : tCHAR.chars;
        return OS.GetLocaleInfo(n, n2, cArray, n3);
    }

    public static final int GetModuleFileName(long l2, TCHAR tCHAR, int n) {
        char[] cArray = tCHAR == null ? null : tCHAR.chars;
        return OS.GetModuleFileName(l2, cArray, n);
    }

    public static final int GetProfileString(TCHAR tCHAR, TCHAR tCHAR2, TCHAR tCHAR3, TCHAR tCHAR4, int n) {
        char[] cArray = tCHAR == null ? null : tCHAR.chars;
        char[] cArray2 = tCHAR2 == null ? null : tCHAR2.chars;
        char[] cArray3 = tCHAR3 == null ? null : tCHAR3.chars;
        char[] cArray4 = tCHAR4 == null ? null : tCHAR4.chars;
        return OS.GetProfileString(cArray, cArray2, cArray3, cArray4, n);
    }

    public static final int GetWindowText(long l2, TCHAR tCHAR, int n) {
        char[] cArray = tCHAR == null ? null : tCHAR.chars;
        return OS.GetWindowText(l2, cArray, n);
    }

    public static final int GlobalAddAtom(TCHAR tCHAR) {
        char[] cArray = tCHAR == null ? null : tCHAR.chars;
        return OS.GlobalAddAtom(cArray);
    }

    public static final long ImmEscape(long l2, long l3, int n, TCHAR tCHAR) {
        char[] cArray = tCHAR == null ? null : tCHAR.chars;
        return OS.ImmEscape(l2, l3, n, cArray);
    }

    public static final boolean InternetGetCookie(TCHAR tCHAR, TCHAR tCHAR2, TCHAR tCHAR3, int[] nArray) {
        char[] cArray = tCHAR == null ? null : tCHAR.chars;
        char[] cArray2 = tCHAR2 == null ? null : tCHAR2.chars;
        char[] cArray3 = tCHAR3 == null ? null : tCHAR3.chars;
        return OS.InternetGetCookie(cArray, cArray2, cArray3, nArray);
    }

    public static final boolean InternetSetCookie(TCHAR tCHAR, TCHAR tCHAR2, TCHAR tCHAR3) {
        char[] cArray = tCHAR == null ? null : tCHAR.chars;
        char[] cArray2 = tCHAR2 == null ? null : tCHAR2.chars;
        char[] cArray3 = tCHAR3 == null ? null : tCHAR3.chars;
        return OS.InternetSetCookie(cArray, cArray2, cArray3);
    }

    public static final int MessageBox(long l2, TCHAR tCHAR, TCHAR tCHAR2, int n) {
        char[] cArray = tCHAR == null ? null : tCHAR.chars;
        char[] cArray2 = tCHAR2 == null ? null : tCHAR2.chars;
        return OS.MessageBox(l2, cArray, cArray2, n);
    }

    public static final void MoveMemory(long l2, TCHAR tCHAR, int n) {
        char[] cArray = tCHAR == null ? null : tCHAR.chars;
        OS.MoveMemory(l2, cArray, n);
    }

    public static final void MoveMemory(TCHAR tCHAR, long l2, int n) {
        char[] cArray = tCHAR == null ? null : tCHAR.chars;
        OS.MoveMemory(cArray, l2, n);
    }

    public static final boolean OpenPrinter(TCHAR tCHAR, long[] lArray, long l2) {
        char[] cArray = tCHAR == null ? null : tCHAR.chars;
        return OS.OpenPrinter(cArray, lArray, l2);
    }

    public static final int[] readRegistryDwords(int n, String string, String string2) {
        int n2;
        int n3 = 234;
        Objects.requireNonNull("key", string);
        Objects.requireNonNull("valueName", string2);
        long[] lArray = new long[]{0L};
        TCHAR tCHAR = new TCHAR(0, string, true);
        TCHAR tCHAR2 = new TCHAR(0, string2, true);
        if (OS.RegOpenKeyEx((long)n, tCHAR, 0, 131097, lArray) != 0) {
            return null;
        }
        int n4 = 2;
        do {
            int[] nArray = new int[]{4 * n4};
            int[] nArray2 = new int[n4];
            n2 = OS.RegQueryValueEx(lArray[0], tCHAR2, 0L, null, nArray2, nArray);
            OS.RegCloseKey(lArray[0]);
            if (n2 == 0) {
                return nArray2;
            }
            n4 *= 2;
        } while (n2 == 234);
        return null;
    }

    public static final int RegCreateKeyEx(long l2, TCHAR tCHAR, int n, TCHAR tCHAR2, int n2, int n3, long l3, long[] lArray, long[] lArray2) {
        char[] cArray = tCHAR2 == null ? null : tCHAR2.chars;
        char[] cArray2 = tCHAR == null ? null : tCHAR.chars;
        return OS.RegCreateKeyEx(l2, cArray2, n, cArray, n2, n3, l3, lArray, lArray2);
    }

    public static final int RegDeleteValue(long l2, TCHAR tCHAR) {
        char[] cArray = tCHAR == null ? null : tCHAR.chars;
        return OS.RegDeleteValue(l2, cArray);
    }

    public static final int RegEnumKeyEx(long l2, int n, TCHAR tCHAR, int[] nArray, int[] nArray2, TCHAR tCHAR2, int[] nArray3, long l3) {
        char[] cArray = tCHAR == null ? null : tCHAR.chars;
        char[] cArray2 = tCHAR2 == null ? null : tCHAR2.chars;
        return OS.RegEnumKeyEx(l2, n, cArray, nArray, nArray2, cArray2, nArray3, l3);
    }

    public static final int RegisterClass(TCHAR tCHAR, WNDCLASS wNDCLASS) {
        long l2 = OS.GetProcessHeap();
        int n = tCHAR.length() * 2;
        wNDCLASS.lpszClassName = OS.HeapAlloc(l2, 8, n);
        OS.MoveMemory(wNDCLASS.lpszClassName, tCHAR, n);
        int n2 = OS.RegisterClass(wNDCLASS);
        OS.HeapFree(l2, 0, wNDCLASS.lpszClassName);
        wNDCLASS.lpszClassName = 0L;
        return n2;
    }

    public static final int RegisterClipboardFormat(TCHAR tCHAR) {
        char[] cArray = tCHAR == null ? null : tCHAR.chars;
        return OS.RegisterClipboardFormat(cArray);
    }

    public static final int RegisterWindowMessage(TCHAR tCHAR) {
        char[] cArray = tCHAR == null ? null : tCHAR.chars;
        return OS.RegisterWindowMessage(cArray);
    }

    public static final int RegOpenKeyEx(long l2, TCHAR tCHAR, int n, int n2, long[] lArray) {
        char[] cArray = tCHAR == null ? null : tCHAR.chars;
        return OS.RegOpenKeyEx(l2, cArray, n, n2, lArray);
    }

    public static final int RegQueryValueEx(long l2, TCHAR tCHAR, long l3, int[] nArray, TCHAR tCHAR2, int[] nArray2) {
        char[] cArray = tCHAR == null ? null : tCHAR.chars;
        char[] cArray2 = tCHAR2 == null ? null : tCHAR2.chars;
        return OS.RegQueryValueEx(l2, cArray, l3, nArray, cArray2, nArray2);
    }

    public static final int RegQueryValueEx(long l2, TCHAR tCHAR, long l3, int[] nArray, int[] nArray2, int[] nArray3) {
        char[] cArray = tCHAR == null ? null : tCHAR.chars;
        return OS.RegQueryValueEx(l2, cArray, l3, nArray, nArray2, nArray3);
    }

    public static final int RegSetValueEx(long l2, TCHAR tCHAR, int n, int n2, int[] nArray, int n3) {
        char[] cArray = tCHAR == null ? null : tCHAR.chars;
        return OS.RegSetValueEx(l2, cArray, n, n2, nArray, n3);
    }

    public static final long SendMessage(long l2, int n, long l3, TCHAR tCHAR) {
        char[] cArray = tCHAR == null ? null : tCHAR.chars;
        return OS.SendMessage(l2, n, l3, cArray);
    }

    public static final void setTheme(boolean bl) {
        Display display = Display.getCurrent();
        if (display == null) {
            throw new NullPointerException("Display must be already created before you call OS.setTheme()");
        }
        display.setData("org.eclipse.swt.internal.win32.useDarkModeExplorerTheme", bl);
        display.setData("org.eclipse.swt.internal.win32.useShellTitleColoring", bl);
        display.setData("org.eclipse.swt.internal.win32.menuBarForegroundColor", bl ? new Color(display, 208, 208, 208) : null);
        display.setData("org.eclipse.swt.internal.win32.menuBarBackgroundColor", bl ? new Color(display, 48, 48, 48) : null);
        display.setData("org.eclipse.swt.internal.win32.menuBarBorderColor", bl ? new Color(display, 80, 80, 80) : null);
        display.setData("org.eclipse.swt.internal.win32.Canvas.use_WS_BORDER", bl);
        display.setData("org.eclipse.swt.internal.win32.List.use_WS_BORDER", bl);
        display.setData("org.eclipse.swt.internal.win32.Table.use_WS_BORDER", bl);
        display.setData("org.eclipse.swt.internal.win32.Text.use_WS_BORDER", bl);
        display.setData("org.eclipse.swt.internal.win32.Tree.use_WS_BORDER", bl);
        display.setData("org.eclipse.swt.internal.win32.Table.headerLineColor", bl ? new Color(display, 80, 80, 80) : null);
        display.setData("org.eclipse.swt.internal.win32.Label.disabledForegroundColor", bl ? new Color(display, 128, 128, 128) : null);
        display.setData("org.eclipse.swt.internal.win32.Combo.useDarkTheme", bl);
        display.setData("org.eclipse.swt.internal.win32.ProgressBar.useColors", bl);
        display.setData("org.eclipse.swt.internal.win32.Text.useDarkThemeIcons", bl);
    }

    public static final boolean SetWindowText(long l2, TCHAR tCHAR) {
        char[] cArray = tCHAR == null ? null : tCHAR.chars;
        return OS.SetWindowText(l2, cArray);
    }

    public static final boolean UnregisterClass(TCHAR tCHAR, long l2) {
        char[] cArray = tCHAR == null ? null : tCHAR.chars;
        return OS.UnregisterClass(cArray, l2);
    }

    public static final int UrlCreateFromPath(TCHAR tCHAR, TCHAR tCHAR2, int[] nArray, int n) {
        char[] cArray = tCHAR == null ? null : tCHAR.chars;
        char[] cArray2 = tCHAR2 == null ? null : tCHAR2.chars;
        return OS.UrlCreateFromPath(cArray, cArray2, nArray, n);
    }

    public static final int GET_WHEEL_DELTA_WPARAM(long l2) {
        return (short)OS.HIWORD(l2);
    }

    public static final int GET_X_LPARAM(long l2) {
        return (short)OS.LOWORD(l2);
    }

    public static final int GET_Y_LPARAM(long l2) {
        return (short)OS.HIWORD(l2);
    }

    public static final int HIWORD(long l2) {
        return (int)l2 >>> 16;
    }

    public static final int LOWORD(long l2) {
        return (int)l2 & 0xFFFF;
    }

    public static final int MAKEWORD(int n, int n2) {
        return n & 0xFF | (n2 & 0xFF) << 8;
    }

    public static final long MAKELPARAM(int n, int n2) {
        return (long)(n & 0xFFFF | n2 << 16) & 0xFFFFFFFFL;
    }

    public static final long MAKELRESULT(int n, int n2) {
        return OS.MAKELPARAM(n, n2);
    }

    public static final long MAKEWPARAM(int n, int n2) {
        return OS.MAKELPARAM(n, n2);
    }

    public static final void POINTSTOPOINT(POINT pOINT, long l2) {
        pOINT.x = (short)OS.LOWORD(l2);
        pOINT.y = (short)OS.HIWORD(l2);
    }

    public static final int PRIMARYLANGID(int n) {
        return n & 0x3FF;
    }

    public static final int TOUCH_COORD_TO_PIXEL(int n) {
        return n / 100;
    }

    public static int HRESULT_FROM_WIN32(int n) {
        return n <= 0 ? n : n & 0xFFFF | 0x80070000;
    }

    public static final native int AbortDoc(long var0);

    public static final native boolean ActivateActCtx(long var0, long[] var2);

    public static final native long ActivateKeyboardLayout(long var0, int var2);

    public static final native int AddFontResourceEx(char[] var0, int var1, long var2);

    public static final native boolean AdjustWindowRectEx(RECT var0, int var1, boolean var2, int var3);

    public static final native boolean AllowDarkModeForWindow(long var0, boolean var2);

    public static final native boolean AllowSetForegroundWindow(int var0);

    public static final native boolean AlphaBlend(long var0, int var2, int var3, int var4, int var5, long var6, int var8, int var9, int var10, int var11, BLENDFUNCTION var12);

    public static final native boolean Arc(long var0, int var2, int var3, int var4, int var5, int var6, int var7, int var8, int var9);

    public static final native int AssocQueryString(int var0, int var1, char[] var2, char[] var3, char[] var4, int[] var5);

    public static final native long BeginBufferedPaint(long var0, RECT var2, int var3, BP_PAINTPARAMS var4, long[] var5);

    public static final native long BeginDeferWindowPos(int var0);

    public static final native long BeginPaint(long var0, PAINTSTRUCT var2);

    public static final native boolean BitBlt(long var0, int var2, int var3, int var4, int var5, long var6, int var8, int var9, int var10);

    public static final native boolean BringWindowToTop(long var0);

    public static final native int BufferedPaintInit();

    public static final native int BufferedPaintUnInit();

    public static final native long CallNextHookEx(long var0, int var2, long var3, long var5);

    public static final native long CallWindowProc(long var0, long var2, int var4, long var5, long var7);

    public static final native long CharLower(long var0);

    public static final native long CharUpper(long var0);

    public static final native long ChildWindowFromPointEx(long var0, POINT var2, int var3);

    public static final native boolean ChooseColor(CHOOSECOLOR var0);

    public static final native boolean ChooseFont(CHOOSEFONT var0);

    public static final native boolean ClientToScreen(long var0, POINT var2);

    public static final native boolean CloseClipboard();

    public static final native long CloseEnhMetaFile(long var0);

    public static final native long CloseGestureInfoHandle(long var0);

    public static final native boolean CloseHandle(long var0);

    public static final native boolean ClosePrinter(long var0);

    public static final native int CloseThemeData(long var0);

    public static final native boolean CloseTouchInputHandle(long var0);

    public static final native int CoInternetIsFeatureEnabled(int var0, int var1);

    public static final native int CoInternetSetFeatureEnabled(int var0, int var1, boolean var2);

    public static final native int CombineRgn(long var0, long var2, long var4, int var6);

    public static final native long CopyImage(long var0, int var2, int var3, int var4, int var5);

    public static final native long CoTaskMemAlloc(int var0);

    public static final native void CoTaskMemFree(long var0);

    public static final native long CreateAcceleratorTable(byte[] var0, int var1);

    public static final native long CreateActCtx(ACTCTX var0);

    public static final native long CreateBitmap(int var0, int var1, int var2, int var3, byte[] var4);

    public static final native boolean CreateCaret(long var0, long var2, int var4, int var5);

    public static final native long CreateCompatibleBitmap(long var0, int var2, int var3);

    public static final native long CreateCompatibleDC(long var0);

    public static final native long CreateCursor(long var0, int var2, int var3, int var4, int var5, byte[] var6, byte[] var7);

    public static final native long CreateDC(char[] var0, char[] var1, long var2, long var4);

    public static final native long CreateDIBSection(long var0, byte[] var2, int var3, long[] var4, long var5, int var7);

    public static final native long CreateDIBSection(long var0, long var2, int var4, long[] var5, long var6, int var8);

    public static final native long CreateEnhMetaFile(long var0, char[] var2, RECT var3, char[] var4);

    public static final native long CreateFontIndirect(long var0);

    public static final native long CreateFontIndirect(LOGFONT var0);

    public static final native long CreateIconIndirect(ICONINFO var0);

    public static final native long CreateMenu();

    public static final native long CreatePatternBrush(long var0);

    public static final native long CreatePen(int var0, int var1, int var2);

    public static final native long CreatePolygonRgn(int[] var0, int var1, int var2);

    public static final native long CreatePopupMenu();

    public static final native boolean CreateProcess(long var0, long var2, long var4, long var6, boolean var8, int var9, long var10, long var12, STARTUPINFO var14, PROCESS_INFORMATION var15);

    public static final native long CreateRectRgn(int var0, int var1, int var2, int var3);

    public static final native long CreateSolidBrush(int var0);

    public static final native int CreateStreamOnHGlobal(long var0, boolean var2, long[] var3);

    public static final native long CreateWindowEx(int var0, char[] var1, char[] var2, int var3, int var4, int var5, int var6, int var7, long var8, long var10, long var12, CREATESTRUCT var14);

    public static final native long DeferWindowPos(long var0, long var2, long var4, int var6, int var7, int var8, int var9, int var10);

    public static final native long DefMDIChildProc(long var0, int var2, long var3, long var5);

    public static final native long DefFrameProc(long var0, long var2, int var4, long var5, long var7);

    public static final native long DefWindowProc(long var0, int var2, long var3, long var5);

    public static final native boolean DeleteDC(long var0);

    public static final native boolean DeleteEnhMetaFile(long var0);

    public static final native boolean DeleteMenu(long var0, int var2, int var3);

    public static final native boolean DeleteObject(long var0);

    public static final native boolean DestroyAcceleratorTable(long var0);

    public static final native boolean DestroyCaret();

    public static final native boolean DestroyCursor(long var0);

    public static final native boolean DestroyIcon(long var0);

    public static final native boolean DestroyMenu(long var0);

    public static final native boolean DestroyWindow(long var0);

    public static final native long DispatchMessage(MSG var0);

    public static final native int DocumentProperties(long var0, long var2, char[] var4, long var5, long var7, int var9);

    public static final native boolean DragDetect(long var0, POINT var2);

    public static final native void DragFinish(long var0);

    public static final native int DragQueryFile(long var0, int var2, char[] var3, int var4);

    public static final native boolean DrawEdge(long var0, RECT var2, int var3, int var4);

    public static final native boolean DrawFocusRect(long var0, RECT var2);

    public static final native boolean DrawFrameControl(long var0, RECT var2, int var3, int var4);

    public static final native boolean DrawIconEx(long var0, int var2, int var3, long var4, int var6, int var7, int var8, long var9, int var11);

    public static final native boolean DrawMenuBar(long var0);

    public static final native int DrawText(long var0, char[] var2, int var3, RECT var4, int var5);

    public static final native int DrawThemeBackground(long var0, long var2, int var4, int var5, RECT var6, RECT var7);

    public static final native int DrawThemeText(long var0, long var2, int var4, int var5, char[] var6, int var7, int var8, int var9, RECT var10);

    public static final native boolean DwmSetWindowAttribute(long var0, int var2, int[] var3, int var4);

    public static final native boolean Ellipse(long var0, int var2, int var3, int var4, int var5);

    public static final native boolean EnableMenuItem(long var0, int var2, int var3);

    public static final native boolean EnableScrollBar(long var0, int var2, int var3);

    public static final native boolean EnableWindow(long var0, boolean var2);

    public static final native boolean EnumSystemLanguageGroups(long var0, int var2, long var3);

    public static final native boolean EnumSystemLocales(long var0, int var2);

    public static final native boolean EndDeferWindowPos(long var0);

    public static final native int EndBufferedPaint(long var0, boolean var2);

    public static final native int EndDoc(long var0);

    public static final native int EndPage(long var0);

    public static final native int EndPaint(long var0, PAINTSTRUCT var2);

    public static final native boolean EnumDisplayMonitors(long var0, RECT var2, long var3, int var5);

    public static final native boolean EnumEnhMetaFile(long var0, long var2, long var4, long var6, RECT var8);

    public static final native int EnumFontFamilies(long var0, char[] var2, long var3, long var5);

    public static final native boolean EqualRect(RECT var0, RECT var1);

    public static final native int ExcludeClipRect(long var0, int var2, int var3, int var4, int var5);

    public static final native int ExpandEnvironmentStrings(char[] var0, char[] var1, int var2);

    public static final native long ExtCreatePen(int var0, int var1, LOGBRUSH var2, int var3, int[] var4);

    public static final native long ExtCreateRegion(float[] var0, int var1, int[] var2);

    public static final native boolean ExtTextOut(long var0, int var2, int var3, int var4, RECT var5, char[] var6, int var7, int[] var8);

    public static final native int ExtractIconEx(char[] var0, int var1, long[] var2, long[] var3, int var4);

    public static final native int FillRect(long var0, RECT var2, long var3);

    public static final native int GdiSetBatchLimit(int var0);

    public static final native int GetACP();

    public static final native long GetActiveWindow();

    public static final native int GetBkColor(long var0);

    public static final native long GetCapture();

    public static final native boolean GetCaretPos(POINT var0);

    public static final native boolean GetCharABCWidths(long var0, int var2, int var3, int[] var4);

    public static final native int GetCharacterPlacement(long var0, char[] var2, int var3, int var4, GCP_RESULTS var5, int var6);

    public static final native boolean GetCharWidth(long var0, int var2, int var3, int[] var4);

    public static final native boolean GetClassInfo(long var0, char[] var2, WNDCLASS var3);

    public static final native int GetClassName(long var0, char[] var2, int var3);

    public static final native boolean GetClientRect(long var0, RECT var2);

    public static final native long GetClipboardData(int var0);

    public static final native int GetClipboardFormatName(int var0, char[] var1, int var2);

    public static final native int GetClipBox(long var0, RECT var2);

    public static final native int GetClipRgn(long var0, long var2);

    public static final native boolean GetComboBoxInfo(long var0, COMBOBOXINFO var2);

    public static final native long GetCurrentObject(long var0, int var2);

    public static final native int GetCurrentProcessId();

    public static final native int GetCurrentThreadId();

    public static final native int GetCurrentProcessExplicitAppUserModelID(long[] var0);

    public static final native long GetCursor();

    public static final native boolean GetCursorPos(POINT var0);

    public static final native long GetDC(long var0);

    public static final native long GetDCEx(long var0, long var2, int var4);

    public static final native long GetDesktopWindow();

    public static final native int GetDeviceCaps(long var0, int var2);

    public static final native int GetDialogBaseUnits();

    public static final native int GetDIBColorTable(long var0, int var2, int var3, byte[] var4);

    public static final native int GetDIBits(long var0, long var2, int var4, int var5, byte[] var6, byte[] var7, int var8);

    public static final native long GetDlgItem(long var0, int var2);

    public static final native int GetDoubleClickTime();

    public static final native int GetDpiForMonitor(long var0, int var2, int[] var3, int[] var4);

    public static final native long GetFocus();

    public static final native int GetFontLanguageInfo(long var0);

    public static final native long GetForegroundWindow();

    public static final native boolean GetGestureInfo(long var0, GESTUREINFO var2);

    public static final native int GetGraphicsMode(long var0);

    public static final native int GetGlyphIndices(long var0, char[] var2, int var3, short[] var4, int var5);

    public static final native boolean GetGUIThreadInfo(int var0, GUITHREADINFO var1);

    public static final native boolean GetIconInfo(long var0, ICONINFO var2);

    public static final native int GetKeyboardLayoutList(int var0, long[] var1);

    public static final native long GetKeyboardLayout(int var0);

    public static final native short GetKeyState(int var0);

    public static final native boolean GetKeyboardState(byte[] var0);

    public static final native long GetLastActivePopup(long var0);

    public static final native int GetLastError();

    public static final native boolean GetLayeredWindowAttributes(long var0, int[] var2, byte[] var3, int[] var4);

    public static final native int GetLayout(long var0);

    public static final native long GetLibraryHandle();

    public static final native int GetLocaleInfo(int var0, int var1, char[] var2, int var3);

    public static final native long GetMenu(long var0);

    public static final native boolean GetMenuBarInfo(long var0, int var2, int var3, MENUBARINFO var4);

    public static final native int GetMenuDefaultItem(long var0, int var2, int var3);

    public static final native boolean GetMenuInfo(long var0, MENUINFO var2);

    public static final native int GetMenuItemCount(long var0);

    public static final native boolean GetMenuItemInfo(long var0, int var2, boolean var3, MENUITEMINFO var4);

    public static final native boolean GetMenuItemRect(long var0, long var2, int var4, RECT var5);

    public static final native boolean GetMessage(MSG var0, long var1, int var3, int var4);

    public static final native int GetMessagePos();

    public static final native int GetMessageTime();

    public static final native int GetMetaRgn(long var0, long var2);

    public static final native int GetThemePartSize(long var0, long var2, int var4, int var5, RECT var6, int var7, SIZE var8);

    public static final native int GetThemeTextExtent(long var0, long var2, int var4, int var5, char[] var6, int var7, int var8, RECT var9, RECT var10);

    public static final native int GetModuleFileName(long var0, char[] var2, int var3);

    public static final native long GetModuleHandle(char[] var0);

    public static final native boolean GetMonitorInfo(long var0, MONITORINFO var2);

    public static final native int GetObject(long var0, int var2, BITMAP var3);

    public static final native int GetObject(long var0, int var2, DIBSECTION var3);

    public static final native int GetObject(long var0, int var2, LOGBRUSH var3);

    public static final native int GetObject(long var0, int var2, LOGFONT var3);

    public static final native int GetObject(long var0, int var2, long var3);

    public static final native int GetOutlineTextMetrics(long var0, int var2, OUTLINETEXTMETRIC var3);

    public static final native long GetParent(long var0);

    public static final native int GetPixel(long var0, int var2, int var3);

    public static final native int GetPolyFillMode(long var0);

    public static final native boolean OpenPrinter(char[] var0, long[] var1, long var2);

    public static final native long GetProcessHeap();

    public static final native int GetProfileString(char[] var0, char[] var1, char[] var2, char[] var3, int var4);

    public static final native long GetProp(long var0, long var2);

    public static final native int GetRandomRgn(long var0, long var2, int var4);

    public static final native int GetRegionData(long var0, int var2, int[] var3);

    public static final native int GetRgnBox(long var0, RECT var2);

    public static final native int GetROP2(long var0);

    public static final native boolean GetScrollBarInfo(long var0, int var2, SCROLLBARINFO var3);

    public static final native boolean GetScrollInfo(long var0, int var2, SCROLLINFO var3);

    public static final native void GetStartupInfo(STARTUPINFO var0);

    public static final native long GetStockObject(int var0);

    public static final native int GetSysColor(int var0);

    public static final native long GetSysColorBrush(int var0);

    public static final native long GetSystemMenu(long var0, boolean var2);

    public static final native int GetSystemMetrics(int var0);

    public static final native int GetTextColor(long var0);

    public static final native boolean GetTextExtentPoint32(long var0, char[] var2, int var3, SIZE var4);

    public static final native boolean GetTextMetrics(long var0, TEXTMETRIC var2);

    public static final native boolean GetTouchInputInfo(long var0, int var2, long var3, int var5);

    public static final native boolean GetUpdateRect(long var0, RECT var2, boolean var3);

    public static final native int GetUpdateRgn(long var0, long var2, boolean var4);

    public static final native int GetVersion();

    public static final native long GetWindow(long var0, int var2);

    public static final native int GetWindowLong(long var0, int var2);

    public static final native long GetWindowLongPtr(long var0, int var2);

    public static final native long GetWindowDC(long var0);

    public static final native boolean GetWindowOrgEx(long var0, POINT var2);

    public static final native boolean GetWindowPlacement(long var0, WINDOWPLACEMENT var2);

    public static final native boolean GetWindowRect(long var0, RECT var2);

    public static final native int GetWindowRgn(long var0, long var2);

    public static final native int GetWindowText(long var0, char[] var2, int var3);

    public static final native int GetWindowTextLength(long var0);

    public static final native int GetWindowThreadProcessId(long var0, int[] var2);

    public static final native double GID_ROTATE_ANGLE_FROM_ARGUMENT(long var0);

    public static final native int GlobalAddAtom(char[] var0);

    public static final native long GlobalAlloc(int var0, int var1);

    public static final native long GlobalFree(long var0);

    public static final native long GlobalLock(long var0);

    public static final native int GlobalSize(long var0);

    public static final native boolean GlobalUnlock(long var0);

    public static final native boolean GradientFill(long var0, long var2, int var4, long var5, int var7, int var8);

    public static final native long HeapAlloc(long var0, int var2, int var3);

    public static final native boolean HeapFree(long var0, int var2, long var3);

    public static final native boolean HideCaret(long var0);

    public static final native int IIDFromString(char[] var0, byte[] var1);

    public static final native int ILGetSize(long var0);

    public static final native int ImageList_Add(long var0, long var2, long var4);

    public static final native int ImageList_AddMasked(long var0, long var2, int var4);

    public static final native boolean ImageList_BeginDrag(long var0, int var2, int var3, int var4);

    public static final native long ImageList_Create(int var0, int var1, int var2, int var3, int var4);

    public static final native boolean ImageList_Destroy(long var0);

    public static final native boolean ImageList_DragEnter(long var0, int var2, int var3);

    public static final native boolean ImageList_DragLeave(long var0);

    public static final native boolean ImageList_DragMove(int var0, int var1);

    public static final native boolean ImageList_DragShowNolock(boolean var0);

    public static final native void ImageList_EndDrag();

    public static final native boolean ImageList_GetIconSize(long var0, int[] var2, int[] var3);

    public static final native int ImageList_GetImageCount(long var0);

    public static final native boolean ImageList_Remove(long var0, int var2);

    public static final native boolean ImageList_Replace(long var0, int var2, long var3, long var5);

    public static final native int ImageList_ReplaceIcon(long var0, int var2, long var3);

    public static final native boolean ImageList_SetIconSize(long var0, int var2, int var3);

    public static final native long ImmEscape(long var0, long var2, int var4, char[] var5);

    public static final native boolean ImmGetCompositionFont(long var0, LOGFONT var2);

    public static final native int ImmGetCompositionString(long var0, int var2, char[] var3, int var4);

    public static final native int ImmGetCompositionString(long var0, int var2, int[] var3, int var4);

    public static final native int ImmGetCompositionString(long var0, int var2, byte[] var3, int var4);

    public static final native long ImmGetContext(long var0);

    public static final native boolean ImmGetConversionStatus(long var0, int[] var2, int[] var3);

    public static final native long ImmGetDefaultIMEWnd(long var0);

    public static final native boolean ImmGetOpenStatus(long var0);

    public static final native boolean ImmNotifyIME(long var0, int var2, int var3, int var4);

    public static final native boolean ImmReleaseContext(long var0, long var2);

    public static final native boolean ImmSetCompositionFont(long var0, LOGFONT var2);

    public static final native boolean ImmSetCompositionWindow(long var0, COMPOSITIONFORM var2);

    public static final native boolean ImmSetCandidateWindow(long var0, CANDIDATEFORM var2);

    public static final native boolean ImmSetConversionStatus(long var0, int var2, int var3);

    public static final native boolean ImmSetOpenStatus(long var0, boolean var2);

    public static final native boolean InitCommonControlsEx(INITCOMMONCONTROLSEX var0);

    public static final native boolean InsertMenuItem(long var0, int var2, boolean var3, MENUITEMINFO var4);

    public static final native boolean InternetGetCookie(char[] var0, char[] var1, char[] var2, int[] var3);

    public static final native boolean InternetSetCookie(char[] var0, char[] var1, char[] var2);

    public static final native boolean InternetSetOption(long var0, int var2, long var3, int var5);

    public static final native int IntersectClipRect(long var0, int var2, int var3, int var4, int var5);

    public static final native boolean IntersectRect(RECT var0, RECT var1, RECT var2);

    public static final native boolean InvalidateRect(long var0, RECT var2, boolean var3);

    public static final native boolean InvalidateRgn(long var0, long var2, boolean var4);

    public static final native boolean IsAppThemed();

    public static final native boolean IsDarkModeAvailable();

    public static final native boolean IsHungAppWindow(long var0);

    public static final native boolean IsIconic(long var0);

    public static final native boolean IsTouchWindow(long var0, long[] var2);

    public static final native boolean IsWindowEnabled(long var0);

    public static final native boolean IsWindowVisible(long var0);

    public static final native boolean IsZoomed(long var0);

    public static final native boolean KillTimer(long var0, long var2);

    public static final native boolean LineTo(long var0, int var2, int var3);

    public static final native long LoadBitmap(long var0, long var2);

    public static final native long LoadCursor(long var0, long var2);

    public static final native long LoadIcon(long var0, long var2);

    public static final native int LoadIconMetric(long var0, long var2, int var4, long[] var5);

    public static final native long LoadImage(long var0, long var2, int var4, int var5, int var6, int var7);

    public static final native long LocalFree(long var0);

    public static final native boolean LPtoDP(long var0, POINT var2, int var3);

    public static final native int MapVirtualKey(int var0, int var1);

    public static final native int MapWindowPoints(long var0, long var2, POINT var4, int var5);

    public static final native int MapWindowPoints(long var0, long var2, RECT var4, int var5);

    public static final native boolean MessageBeep(int var0);

    public static final native int MessageBox(long var0, char[] var2, char[] var3, int var4);

    public static final native boolean ModifyWorldTransform(long var0, float[] var2, int var3);

    public static final native long MonitorFromWindow(long var0, int var2);

    public static final native void MoveMemory(char[] var0, long var1, int var3);

    public static final native void MoveMemory(byte[] var0, long var1, int var3);

    public static final native void MoveMemory(byte[] var0, ACCEL var1, int var2);

    public static final native void MoveMemory(byte[] var0, BITMAPINFOHEADER var1, int var2);

    public static final native void MoveMemory(int[] var0, long var1, int var3);

    public static final native void MoveMemory(long[] var0, long var1, int var3);

    public static final native void MoveMemory(double[] var0, long var1, int var3);

    public static final native void MoveMemory(float[] var0, long var1, int var3);

    public static final native void MoveMemory(short[] var0, long var1, int var3);

    public static final native void MoveMemory(long var0, byte[] var2, int var3);

    public static final native void MoveMemory(long var0, char[] var2, int var3);

    public static final native void MoveMemory(long var0, int[] var2, int var3);

    public static final native void MoveMemory(long var0, long var2, int var4);

    public static final native void MoveMemory(long var0, DEVMODE var2, int var3);

    public static final native void MoveMemory(long var0, DOCHOSTUIINFO var2, int var3);

    public static final native void MoveMemory(long var0, GRADIENT_RECT var2, int var3);

    public static final native void MoveMemory(long var0, LOGFONT var2, int var3);

    public static final native void MoveMemory(long var0, MEASUREITEMSTRUCT var2, int var3);

    public static final native void MoveMemory(long var0, MINMAXINFO var2, int var3);

    public static final native void MoveMemory(long var0, MSG var2, int var3);

    public static final native void MoveMemory(long var0, UDACCEL var2, int var3);

    public static final native void MoveMemory(long var0, NMTTDISPINFO var2, int var3);

    public static final native void MoveMemory(long var0, RECT var2, int var3);

    public static final native void MoveMemory(long var0, SAFEARRAY var2, int var3);

    public static final native void MoveMemory(SAFEARRAY var0, long var1, int var3);

    public static final native void MoveMemory(long var0, TRIVERTEX var2, int var3);

    public static final native void MoveMemory(long var0, WINDOWPOS var2, int var3);

    public static final native void MoveMemory(BITMAPINFOHEADER var0, byte[] var1, int var2);

    public static final native void MoveMemory(BITMAPINFOHEADER var0, long var1, int var3);

    public static final native void MoveMemory(DEVMODE var0, long var1, int var3);

    public static final native void MoveMemory(DOCHOSTUIINFO var0, long var1, int var3);

    public static final native void MoveMemory(DRAWITEMSTRUCT var0, long var1, int var3);

    public static final native void MoveMemory(FLICK_DATA var0, long[] var1, int var2);

    public static final native void MoveMemory(FLICK_POINT var0, long[] var1, int var2);

    public static final native void MoveMemory(HDITEM var0, long var1, int var3);

    public static final native void MoveMemory(HELPINFO var0, long var1, int var3);

    public static final native void MoveMemory(LOGFONT var0, long var1, int var3);

    public static final native void MoveMemory(MEASUREITEMSTRUCT var0, long var1, int var3);

    public static final native void MoveMemory(MINMAXINFO var0, long var1, int var3);

    public static final native void MoveMemory(POINT var0, long var1, int var3);

    public static final native void MoveMemory(POINT var0, long[] var1, int var2);

    public static final native void MoveMemory(NMHDR var0, long var1, int var3);

    public static final native void MoveMemory(NMCUSTOMDRAW var0, long var1, int var3);

    public static final native void MoveMemory(NMLVCUSTOMDRAW var0, long var1, int var3);

    public static final native void MoveMemory(NMTBCUSTOMDRAW var0, long var1, int var3);

    public static final native void MoveMemory(NMTBHOTITEM var0, long var1, int var3);

    public static final native void MoveMemory(NMTREEVIEW var0, long var1, int var3);

    public static final native void MoveMemory(NMTVCUSTOMDRAW var0, long var1, int var3);

    public static final native void MoveMemory(NMTVITEMCHANGE var0, long var1, int var3);

    public static final native void MoveMemory(NMUPDOWN var0, long var1, int var3);

    public static final native void MoveMemory(long var0, NMLVCUSTOMDRAW var2, int var3);

    public static final native void MoveMemory(long var0, NMTBCUSTOMDRAW var2, int var3);

    public static final native void MoveMemory(long var0, NMTVCUSTOMDRAW var2, int var3);

    public static final native void MoveMemory(long var0, NMLVDISPINFO var2, int var3);

    public static final native void MoveMemory(long var0, NMTVDISPINFO var2, int var3);

    public static final native void MoveMemory(NMLVDISPINFO var0, long var1, int var3);

    public static final native void MoveMemory(NMTVDISPINFO var0, long var1, int var3);

    public static final native void MoveMemory(NMLVODSTATECHANGE var0, long var1, int var3);

    public static final native void MoveMemory(NMHEADER var0, long var1, int var3);

    public static final native void MoveMemory(NMLINK var0, long var1, int var3);

    public static final native void MoveMemory(NMLISTVIEW var0, long var1, int var3);

    public static final native void MoveMemory(NMREBARCHILDSIZE var0, long var1, int var3);

    public static final native void MoveMemory(NMREBARCHEVRON var0, long var1, int var3);

    public static final native void MoveMemory(NMTOOLBAR var0, long var1, int var3);

    public static final native void MoveMemory(NMTTCUSTOMDRAW var0, long var1, int var3);

    public static final native void MoveMemory(NMTTDISPINFO var0, long var1, int var3);

    public static final native void MoveMemory(EMR var0, long var1, int var3);

    public static final native void MoveMemory(EMREXTCREATEFONTINDIRECTW var0, long var1, int var3);

    public static final native void MoveMemory(long var0, SHDRAGIMAGE var2, int var3);

    public static final native void MoveMemory(TEXTMETRIC var0, long var1, int var3);

    public static final native void MoveMemory(TOUCHINPUT var0, long var1, int var3);

    public static final native void MoveMemory(WINDOWPOS var0, long var1, int var3);

    public static final native void MoveMemory(MSG var0, long var1, int var3);

    public static final native void MoveMemory(UDACCEL var0, long var1, int var3);

    public static final native void MoveMemory(long var0, DROPFILES var2, int var3);

    public static final native void MoveMemory(long var0, double[] var2, int var3);

    public static final native void MoveMemory(long var0, float[] var2, int var3);

    public static final native void MoveMemory(long var0, long[] var2, int var3);

    public static final native void MoveMemory(long var0, short[] var2, int var3);

    public static final native void MoveMemory(SCRIPT_ITEM var0, long var1, int var3);

    public static final native void MoveMemory(SCRIPT_LOGATTR var0, long var1, int var3);

    public static final native void MoveMemory(SCRIPT_PROPERTIES var0, long var1, int var3);

    public static final native void MoveMemory(long var0, CIDA var2, int var3);

    public static final native void MoveMemory(CIDA var0, long var1, int var3);

    public static final native boolean MoveToEx(long var0, int var2, int var3, long var4);

    public static final native int MultiByteToWideChar(int var0, int var1, byte[] var2, int var3, char[] var4, int var5);

    public static final native int MultiByteToWideChar(int var0, int var1, long var2, int var4, char[] var5, int var6);

    public static final native void NotifyWinEvent(int var0, long var1, int var3, int var4);

    public static final native boolean OffsetRect(RECT var0, int var1, int var2);

    public static final native int OffsetRgn(long var0, int var2, int var3);

    public static final native int OleInitialize(long var0);

    public static final native void OleUninitialize();

    public static final native boolean OpenClipboard(long var0);

    public static final native long OpenThemeData(long var0, char[] var2);

    public static final native boolean PatBlt(long var0, int var2, int var3, int var4, int var5, int var6);

    public static final native boolean PathIsExe(long var0);

    public static final native boolean PeekMessage(MSG var0, long var1, int var3, int var4, int var5);

    public static final native boolean Pie(long var0, int var2, int var3, int var4, int var5, int var6, int var7, int var8, int var9);

    public static final native boolean Polygon(long var0, int[] var2, int var3);

    public static final native boolean Polyline(long var0, int[] var2, int var3);

    public static final native boolean PostMessage(long var0, int var2, long var3, long var5);

    public static final native boolean PostThreadMessage(int var0, int var1, long var2, long var4);

    public static final native boolean PrintDlg(PRINTDLG var0);

    public static final native boolean PrintWindow(long var0, long var2, int var4);

    public static final native int PSPropertyKeyFromString(char[] var0, PROPERTYKEY var1);

    public static final native boolean PtInRect(RECT var0, POINT var1);

    public static final native boolean PtInRegion(long var0, int var2, int var3);

    public static final native boolean Rectangle(long var0, int var2, int var3, int var4, int var5);

    public static final native boolean RectInRegion(long var0, RECT var2);

    public static final native boolean RedrawWindow(long var0, RECT var2, long var3, int var5);

    public static final native int RegCloseKey(long var0);

    public static final native int RegCreateKeyEx(long var0, char[] var2, int var3, char[] var4, int var5, int var6, long var7, long[] var9, long[] var10);

    public static final native int RegDeleteValue(long var0, char[] var2);

    public static final native int RegEnumKeyEx(long var0, int var2, char[] var3, int[] var4, int[] var5, char[] var6, int[] var7, long var8);

    public static final native int RegisterClass(WNDCLASS var0);

    public static final native boolean RegisterTouchWindow(long var0, int var2);

    public static final native int RegisterWindowMessage(char[] var0);

    public static final native int RegisterClipboardFormat(char[] var0);

    public static final native int RegOpenKeyEx(long var0, char[] var2, int var3, int var4, long[] var5);

    public static final native int RegQueryValueEx(long var0, char[] var2, long var3, int[] var5, char[] var6, int[] var7);

    public static final native int RegQueryValueEx(long var0, char[] var2, long var3, int[] var5, int[] var6, int[] var7);

    public static final native int RegSetValueEx(long var0, char[] var2, int var3, int var4, int[] var5, int var6);

    public static final native boolean ReleaseCapture();

    public static final native int ReleaseDC(long var0, long var2);

    public static final native boolean RemoveMenu(long var0, int var2, int var3);

    public static final native long RemoveProp(long var0, long var2);

    public static final native boolean ReplyMessage(long var0);

    public static final native boolean RestoreDC(long var0, int var2);

    public static final native boolean RoundRect(long var0, int var2, int var3, int var4, int var5, int var6, int var7);

    public static final native int RtlGetVersion(OSVERSIONINFOEX var0);

    public static final native int SaveDC(long var0);

    public static final native boolean ScreenToClient(long var0, POINT var2);

    public static final native int ScriptApplyDigitSubstitution(long var0, SCRIPT_CONTROL var2, SCRIPT_STATE var3);

    public static final native int ScriptBreak(char[] var0, int var1, SCRIPT_ANALYSIS var2, long var3);

    public static final native int ScriptGetProperties(long[] var0, int[] var1);

    public static final native int ScriptCacheGetHeight(long var0, long var2, int[] var4);

    public static final native int ScriptCPtoX(int var0, boolean var1, int var2, int var3, long var4, long var6, long var8, SCRIPT_ANALYSIS var10, int[] var11);

    public static final native int ScriptFreeCache(long var0);

    public static final native int ScriptGetFontProperties(long var0, long var2, SCRIPT_FONTPROPERTIES var4);

    public static final native int ScriptGetLogicalWidths(SCRIPT_ANALYSIS var0, int var1, int var2, long var3, long var5, long var7, int[] var9);

    public static final native int ScriptItemize(char[] var0, int var1, int var2, SCRIPT_CONTROL var3, SCRIPT_STATE var4, long var5, int[] var7);

    public static final native int ScriptJustify(long var0, long var2, int var4, int var5, int var6, long var7);

    public static final native int ScriptLayout(int var0, byte[] var1, int[] var2, int[] var3);

    public static final native int ScriptPlace(long var0, long var2, long var4, int var6, long var7, SCRIPT_ANALYSIS var9, long var10, long var12, int[] var14);

    public static final native int ScriptGetCMap(long var0, long var2, char[] var4, int var5, int var6, short[] var7);

    public static final native int ScriptShape(long var0, long var2, char[] var4, int var5, int var6, SCRIPT_ANALYSIS var7, long var8, long var10, long var12, int[] var14);

    public static final native int ScriptStringAnalyse(long var0, long var2, int var4, int var5, int var6, int var7, int var8, SCRIPT_CONTROL var9, SCRIPT_STATE var10, long var11, long var13, long var15, long var17);

    public static final native int ScriptStringOut(long var0, int var2, int var3, int var4, RECT var5, int var6, int var7, boolean var8);

    public static final native int ScriptStringFree(long var0);

    public static final native int ScriptTextOut(long var0, long var2, int var4, int var5, int var6, RECT var7, SCRIPT_ANALYSIS var8, long var9, int var11, long var12, int var14, long var15, long var17, long var19);

    public static final native int ScriptXtoCP(int var0, int var1, int var2, long var3, long var5, long var7, SCRIPT_ANALYSIS var9, int[] var10, int[] var11);

    public static final native int ScrollWindowEx(long var0, int var2, int var3, RECT var4, RECT var5, long var6, RECT var8, int var9);

    public static final native int SelectClipRgn(long var0, long var2);

    public static final native long SelectObject(long var0, long var2);

    public static final native int SendInput(int var0, INPUT var1, int var2);

    public static final native long SendMessage(long var0, int var2, int[] var3, int[] var4);

    public static final native long SendMessage(long var0, int var2, long var3, char[] var5);

    public static final native long SendMessage(long var0, int var2, long var3, int[] var5);

    public static final native long SendMessage(long var0, int var2, long var3, long var5);

    public static final native long SendMessage(long var0, int var2, long var3, LVCOLUMN var5);

    public static final native long SendMessage(long var0, int var2, long var3, LVHITTESTINFO var5);

    public static final native long SendMessage(long var0, int var2, long var3, LITEM var5);

    public static final native long SendMessage(long var0, int var2, long var3, LVITEM var5);

    public static final native long SendMessage(long var0, int var2, long var3, LVINSERTMARK var5);

    public static final native long SendMessage(long var0, int var2, long var3, MARGINS var5);

    public static final native long SendMessage(long var0, int var2, long var3, MCHITTESTINFO var5);

    public static final native long SendMessage(long var0, int var2, long var3, REBARBANDINFO var5);

    public static final native long SendMessage(long var0, int var2, long var3, RECT var5);

    public static final native long SendMessage(long var0, int var2, long var3, SYSTEMTIME var5);

    public static final native long SendMessage(long var0, int var2, long var3, SHDRAGIMAGE var5);

    public static final native long SendMessage(long var0, int var2, long var3, TBBUTTON var5);

    public static final native long SendMessage(long var0, int var2, long var3, TBBUTTONINFO var5);

    public static final native long SendMessage(long var0, int var2, long var3, TCITEM var5);

    public static final native long SendMessage(long var0, int var2, long var3, TCHITTESTINFO var5);

    public static final native long SendMessage(long var0, int var2, long var3, TOOLINFO var5);

    public static final native long SendMessage(long var0, int var2, long var3, TVHITTESTINFO var5);

    public static final native long SendMessage(long var0, int var2, long var3, TVINSERTSTRUCT var5);

    public static final native long SendMessage(long var0, int var2, long var3, TVITEM var5);

    public static final native long SendMessage(long var0, int var2, long var3, TVSORTCB var5);

    public static final native long SendMessage(long var0, int var2, long var3, UDACCEL var5);

    public static final native long SendMessage(long var0, int var2, long var3, HDHITTESTINFO var5);

    public static final native long SendMessage(long var0, int var2, long var3, HDITEM var5);

    public static final native long SendMessage(long var0, int var2, long var3, HDLAYOUT var5);

    public static final native long SendMessage(long var0, int var2, long var3, BUTTON_IMAGELIST var5);

    public static final native long SendMessage(long var0, int var2, long var3, SIZE var5);

    public static final native long SetActiveWindow(long var0);

    public static final native int SetBkColor(long var0, int var2);

    public static final native int SetBkMode(long var0, int var2);

    public static final native boolean SetBrushOrgEx(long var0, int var2, int var3, POINT var4);

    public static final native long SetCapture(long var0);

    public static final native boolean SetCaretPos(int var0, int var1);

    public static final native int SetCurrentProcessExplicitAppUserModelID(char[] var0);

    public static final native long SetCursor(long var0);

    public static final native boolean SetCursorPos(int var0, int var1);

    public static final native int SetDCBrushColor(long var0, int var2);

    public static final native int SetDIBColorTable(long var0, int var2, int var3, byte[] var4);

    public static final native long SetFocus(long var0);

    public static final native boolean SetForegroundWindow(long var0);

    public static final native boolean SetGestureConfig(long var0, int var2, int var3, GESTURECONFIG var4, int var5);

    public static final native int SetGraphicsMode(long var0, int var2);

    public static final native boolean SetLayeredWindowAttributes(long var0, int var2, byte var3, int var4);

    public static final native int SetLayout(long var0, int var2);

    public static final native boolean SetMenu(long var0, long var2);

    public static final native boolean SetMenuDefaultItem(long var0, int var2, int var3);

    public static final native boolean SetMenuInfo(long var0, MENUINFO var2);

    public static final native boolean SetMenuItemInfo(long var0, int var2, boolean var3, MENUITEMINFO var4);

    public static final native int SetMetaRgn(long var0);

    public static final native long SetParent(long var0, long var2);

    public static final native int SetPixel(long var0, int var2, int var3, int var4);

    public static final native int SetPolyFillMode(long var0, int var2);

    public static final native boolean SetProcessDPIAware();

    public static final native int SetPreferredAppMode(int var0);

    public static final native boolean SetRect(RECT var0, int var1, int var2, int var3, int var4);

    public static final native boolean SetRectRgn(long var0, int var2, int var3, int var4, int var5);

    public static final native int SetROP2(long var0, int var2);

    public static final native boolean SetScrollInfo(long var0, int var2, SCROLLINFO var3, boolean var4);

    public static final native int SetStretchBltMode(long var0, int var2);

    public static final native boolean SetProp(long var0, long var2, long var4);

    public static final native int SetTextColor(long var0, int var2);

    public static final native long SetTimer(long var0, long var2, int var4, long var5);

    public static final native int SetWindowLong(long var0, int var2, int var3);

    public static final native long SetWindowLongPtr(long var0, int var2, long var3);

    public static final native boolean SetWindowOrgEx(long var0, int var2, int var3, POINT var4);

    public static final native boolean SetWindowPlacement(long var0, WINDOWPLACEMENT var2);

    public static final native boolean SetWindowPos(long var0, long var2, int var4, int var5, int var6, int var7, int var8);

    public static final native int SetWindowRgn(long var0, long var2, boolean var4);

    public static final native boolean SetWindowText(long var0, char[] var2);

    public static final native int SetWindowTheme(long var0, char[] var2, char[] var3);

    public static final native long SetWindowsHookEx(int var0, long var1, long var3, int var5);

    public static final native boolean SetWorldTransform(long var0, float[] var2);

    public static final native long SHGetFileInfo(char[] var0, int var1, SHFILEINFO var2, int var3, int var4);

    public static final native boolean ShellExecuteEx(SHELLEXECUTEINFO var0);

    public static final native boolean Shell_NotifyIcon(int var0, NOTIFYICONDATA var1);

    public static final native boolean ShowCaret(long var0);

    public static final native boolean ShowOwnedPopups(long var0, boolean var2);

    public static final native boolean ShowScrollBar(long var0, int var2, boolean var3);

    public static final native boolean ShowWindow(long var0, int var2);

    public static final native int StartDoc(long var0, DOCINFO var2);

    public static final native int StartPage(long var0);

    public static final native boolean StretchBlt(long var0, int var2, int var3, int var4, int var5, long var6, int var8, int var9, int var10, int var11, int var12);

    public static final native boolean SystemParametersInfo(int var0, int var1, HIGHCONTRAST var2, int var3);

    public static final native boolean SystemParametersInfo(int var0, int var1, RECT var2, int var3);

    public static final native boolean SystemParametersInfo(int var0, int var1, NONCLIENTMETRICS var2, int var3);

    public static final native boolean SystemParametersInfo(int var0, int var1, int[] var2, int var3);

    public static final native int ToUnicode(int var0, int var1, byte[] var2, char[] var3, int var4, int var5);

    public static final native boolean TreeView_GetItemRect(long var0, long var2, RECT var4, boolean var5);

    public static final native boolean TrackMouseEvent(TRACKMOUSEEVENT var0);

    public static final native boolean TrackPopupMenu(long var0, int var2, int var3, int var4, int var5, long var6, RECT var8);

    public static final native int TranslateAccelerator(long var0, long var2, MSG var4);

    public static final native boolean TranslateCharsetInfo(long var0, int[] var2, int var3);

    public static final native boolean TranslateMDISysAccel(long var0, MSG var2);

    public static final native boolean TranslateMessage(MSG var0);

    public static final native boolean TransparentBlt(long var0, int var2, int var3, int var4, int var5, long var6, int var8, int var9, int var10, int var11, int var12);

    public static final native boolean UnhookWindowsHookEx(long var0);

    public static final native boolean UnregisterClass(char[] var0, long var1);

    public static final native boolean UnregisterTouchWindow(long var0);

    public static final native boolean UpdateWindow(long var0);

    public static final native int UrlCreateFromPath(char[] var0, char[] var1, int[] var2, int var3);

    public static final native boolean ValidateRect(long var0, RECT var2);

    public static final native short VkKeyScan(short var0);

    public static final native boolean WaitMessage();

    public static final native int WideCharToMultiByte(int var0, int var1, char[] var2, int var3, byte[] var4, int var5, byte[] var6, int[] var7);

    public static final native int WideCharToMultiByte(int var0, int var1, char[] var2, int var3, long var4, int var6, byte[] var7, int[] var8);

    public static final native long WindowFromDC(long var0);

    public static final native long WindowFromPoint(POINT var0);

    public static final native int wcslen(long var0);

    public static final native long MapViewOfFile(long var0, int var2, int var3, int var4, int var5);

    public static final native boolean UnmapViewOfFile(long var0);

    public static final native long OpenProcess(int var0, boolean var1, int var2);

    public static final native long GetCurrentProcess();

    public static final native boolean DuplicateHandle(long var0, long var2, long var4, long[] var6, int var7, boolean var8, int var9);

    static {
        Library.loadLibrary("swt");
        int n = OS.GetVersion();
        WIN32_VERSION = OS.VERSION(n & 0xFF, n >> 8 & 0xFF);
        OSVERSIONINFOEX oSVERSIONINFOEX = new OSVERSIONINFOEX();
        oSVERSIONINFOEX.dwOSVersionInfoSize = OSVERSIONINFOEX.sizeof;
        if (0 == OS.RtlGetVersion(oSVERSIONINFOEX)) {
            WIN32_BUILD = oSVERSIONINFOEX.dwBuildNumber;
        } else {
            System.err.println("SWT: OS: Failed to detect Windows build number");
            WIN32_BUILD = 0;
        }
        if (System.getProperty(NO_MANIFEST) == null) {
            ACTCTX aCTCTX = new ACTCTX();
            aCTCTX.cbSize = ACTCTX.sizeof;
            aCTCTX.dwFlags = 152;
            aCTCTX.hModule = OS.GetLibraryHandle();
            aCTCTX.lpResourceName = 2L;
            long l2 = OS.CreateActCtx(aCTCTX);
            long[] lArray = new long[]{0L};
            OS.ActivateActCtx(l2, lArray);
        }
        OS.SetProcessDPIAware();
        IsDBLocale = OS.GetSystemMetrics(82) != 0;
        NOTIFYICONDATA_V2_SIZE = OS.NOTIFYICONDATA_V2_SIZE();
    }
}

